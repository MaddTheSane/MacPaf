//
//  PAF21Data.m
//  PAFImporter
//
//  Created by Logan Allred on 12/2/06.
//  Copyright 2006 RedBugz Software. All rights reserved.
//

#import "PAF21Data.h"
#import "UKProgressPanelTask.h"
#import "MAFTempleList.h"
#include <unistd.h>

@interface PAF21Data ()
- (NSString *)hexString:(int)width;
- (NSString *)cleanNoteString:(NSString *)noteString;
- (NSString *)nameForPointer:(int)pointer;
@end

@implementation PAF21Data
@synthesize familyRecords;
@synthesize individualLinksList;
@synthesize individualList;
@synthesize individualRecords;
@synthesize marriageList;
@synthesize nameList;
@synthesize nameRecords;
@synthesize noteList;
@synthesize noteRecords;

struct OneHead_t {
    unsigned short IndivRCount;
	unsigned short IChunkCount;
	unsigned short IndivFree;
	unsigned short MarrRCount;
	unsigned short MChunkCount;
	unsigned short MarrFree;
	unsigned short NameRCount;
	unsigned short NChunkCount;
	unsigned short NoteRCount;
	unsigned short TChunkCount;
	unsigned short NameIndexRCount;
	unsigned short XChunkCount;
	unsigned short ChunkCount;
} *oneHead;

struct RegDate_t {
#ifdef __BIG_ENDIAN__
    unsigned int year:12;
    unsigned int month:5;
    unsigned int day:5;
    unsigned int modifier:2;
#else
    unsigned int modifier:2;
    unsigned int day:5;
    unsigned int month:5;
    unsigned int year:12;
#endif
};

struct DualDate_t {
#ifdef __BIG_ENDIAN__
    unsigned int year:12;
    unsigned int month:5;
    unsigned int day:5;
    unsigned int modifier:2;
#else
    unsigned int modifier:2;
    unsigned int day:5;
    unsigned int month:5;
    unsigned int year:12;
#endif
	unsigned char offset;
};

struct NameRecord_t {
	unsigned short leftLink;
	char name[17];
	unsigned short rightLink;
};


NSData *data;

NSData *signatureData;
NSData *reportTitleRecordData;
NSData *submitterRecordData;
NSData *preferenceData;
NSData *oneHeaderData;
NSData *firstMapChunkData;

NSMutableArray *indivChunks, *marrChunks, *nameChunks, *noteChunks, *mapChunks, *otherChunks;
NSDateFormatter *dateFormatter;


//- (NSString *)combineNoteString:(NSManagedObject *)note {
//	NSString *combinedString = [NSString new];
//	NSManagedObject *nextNote = nil;
//	do {
//		combinedString = [combinedString stringByAppendingString:[note valueForKey:@"noteText"]];
//		nextNote = [note valueForKey:@"nextNote"];
//		note = nextNote;
//	} while (nextNote != nil);
//	return combinedString;
//}

- (NSString *)cleanNoteString:(NSString *)noteString
{
//	NSLog(@"illegal:%@", [[NSCharacterSet illegalCharacterSet] bitmapRepresentation]);
//	NSLog(@"control:%@", [[NSCharacterSet controlCharacterSet] bitmapRepresentation]);
//	NSLog(@"white:%@", [[NSCharacterSet whitespaceCharacterSet] bitmapRepresentation]);
//	NSLog(@"white+newline:%@", [[NSCharacterSet whitespaceAndNewlineCharacterSet] bitmapRepresentation]);
//	NSLog(@"punct:%@", [[NSCharacterSet punctuationCharacterSet] bitmapRepresentation]);
//	NSLog(@"symbol:%@", [[NSCharacterSet symbolCharacterSet] bitmapRepresentation]);

	NSString *string = [[[[noteString componentsSeparatedByString:@"\0\1"] componentsJoinedByString:@"\n\n"] componentsSeparatedByString:@"\0\0"] componentsJoinedByString:@"\n"];
	NSLog(@"bytes:%@", [string dataUsingEncoding:NSUTF8StringEncoding]);
	NSLog(@"control:%@", [[string stringByTrimmingCharactersInSet:[NSCharacterSet controlCharacterSet]] dataUsingEncoding:NSUTF8StringEncoding]);
	return  [string stringByTrimmingCharactersInSet:[NSCharacterSet controlCharacterSet]];
}


- (NSCalendarDate *)dateForDualDate:(struct DualDate_t *)dualDate
{
	return [NSCalendarDate dateWithYear:dualDate->year month:dualDate->month day:dualDate->day hour:0 minute:0 second:0 timeZone:nil];
}

- (NSCalendarDate *)dateForRegDate:(struct RegDate_t *)regDate
{
	return [NSCalendarDate dateWithYear:regDate->year month:regDate->month day:regDate->day hour:0 minute:0 second:0 timeZone:nil];
}

- (NSString *)dateStringForDualDate:(struct DualDate_t *)dualDate
{
	return [dateFormatter stringFromDate:[self dateForDualDate:dualDate]];
}

- (NSString *)dateStringForRegDate:(struct RegDate_t *)regDate
{
	return [dateFormatter stringFromDate:[self dateForRegDate:regDate]];
}

- (struct RegDate_t *)regDateFromData:(NSData *)data atOffset:(int)offset
{
	struct RegDate_t *value = [[data subdataWithRange:NSMakeRange(offset,3)] bytes];
	NSLog(@"regDateFromData:atOffset:%i returns: y=%i m=%i d=%i mod=%i data:%@", offset, value->year, value->month, value->day, value->modifier, [data subdataWithRange:NSMakeRange(offset,3)]);
	return value;
}

- (struct DualDate_t *)dualDateFromData:(NSData *)data atOffset:(int)offset
{
	struct DualDate_t *value = [[data subdataWithRange:NSMakeRange(offset,4)] bytes];
	NSLog(@"dualDateFromData:atOffset:%i returns: y=%i m=%i d=%i mod=%i dual=%i data:%@", offset, value->year, value->month, value->day, value->modifier, value->offset, [data subdataWithRange:NSMakeRange(offset,4)]);
	return value;
}

- (NSString *)nameForPointer:(int)pointer
{
	if (pointer > [nameList count]) {
		NSLog(@"invalid name pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return nameList[pointer-1];
}

- (id)individualForPointer:(int)pointer
{
	if (pointer > [individualList count]) {
		NSLog(@"invalid individual pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return individualList[pointer-1];
}

- (id)marriageForPointer:(int)pointer
{
	if (pointer > [marriageList count]) {
		NSLog(@"invalid marriage pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return marriageList[pointer-1];
}

- (id)noteForPointer:(int)pointer
{
	if (pointer > [noteList count]) {
		NSLog(@"invalid note pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return noteList[pointer-1];
}


- (unsigned short)shortFromData:(NSData *)data
{
	unsigned short value;
	[data getBytes:&value length:sizeof(value)];
	value = NSSwapLittleShortToHost(value);
	return value;
}

- (unsigned short)shortFromData:(NSData *)data withRange:(NSRange)range
{
	unsigned short value;
	[data getBytes:&value range:range];
	value = NSSwapLittleShortToHost(value);
	return value;
}

- (unsigned short)shortFromData:(NSData *)data atOffset:(int)offset
{
	return [self shortFromData:data withRange:NSMakeRange(offset,sizeof(unsigned short))];
}

- (NSString *)placeStringFromData:(NSData *)data atOffset:(int)offset
{
	NSLog(@"%s place data: %@", sel_getName(_cmd), [data subdataWithRange:NSMakeRange(offset, 8)]);
	unsigned short place1 = [self shortFromData:data atOffset:offset];
	unsigned short place2 = [self shortFromData:data atOffset:offset+2];
	unsigned short place3 = [self shortFromData:data atOffset:offset+4];
	unsigned short place4 = [self shortFromData:data atOffset:offset+6];
	NSLog(@"place1=(%i) %@ place2=(%i) %@ place3=(%i) %@ place4=(%i) %@",
	 	place1, [self nameForPointer:place1],
		place2, [self nameForPointer:place2],
		place3, [self nameForPointer:place3],
		place4, [self nameForPointer:place4]);
	
	NSString* place1String = [self nameForPointer:place1];
	if (!place1String) {
		place1String = @"";
	}
	NSString* place2String = [self nameForPointer:place2];
	if (!place2String) {
		place2String = @"";
	}
	NSString* place3String = [self nameForPointer:place3];
	if (!place3String) {
		place3String = @"";
	}
	NSString* place4String = [self nameForPointer:place4];
	if (!place4String) {
		place4String = @"";
	}
	NSString *placeString = [@[place1String,
						place2String,
						place3String,
						place4String] componentsJoinedByString:@","];
	NSLog(@"placeString:%@", placeString);
	return placeString;
}

- (NSString *)stringFromASCIIBytes:(const void *)bytes length:(int)length {
	return  [[NSString alloc] initWithBytes:bytes length:length encoding:NSASCIIStringEncoding]; // convert NSData -> NSString
}

- (NSString *)stringFromASCIIData:(NSData *)data
{
	return  [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding]; // convert NSData -> NSString
}

- (NSString *)stringFromMacRomanBytes:(const void *)bytes length:(int)length
{
	return  [[NSString alloc] initWithBytes:bytes length:length encoding:NSMacOSRomanStringEncoding]; // convert NSData -> NSString
}

- (NSString *)stringFromMacRomanData:(NSData *)data
{
	return  [[NSString alloc] initWithData:data encoding:NSMacOSRomanStringEncoding]; // convert NSData -> NSString
}

- (NSString *)hexRepresentation:(NSData *)data length:(NSInteger)width
{
	NSInteger currentByte = 0, dataLength = [data length], bytesPerRow = width;
	NSInteger rows = (dataLength / bytesPerRow) + ((dataLength % bytesPerRow)? 1:0);
	char buffer[bytesPerRow*3 +1], hex1, hex2;
	char *bytes = (char *) [data bytes];
	NSMutableString *representation = [NSMutableString string];
	int	row;
	
	// calculate bytes
	for( row = 0; row < rows; row++ )
	{
		int	addr;
		
		for( addr = 0; addr < bytesPerRow; addr++ )
		{
			if( currentByte < dataLength )
			{
				hex1 = bytes[currentByte];
				hex2 = bytes[currentByte];
				hex1 >>= 4;
				hex1 &= 0x0F;
				hex2 &= 0x0F;
				hex1 += (hex1 < 10)? 0x30 : 0x37;
				hex2 += (hex2 < 10)? 0x30 : 0x37;
				
				buffer[addr*3]		= hex1;
				buffer[addr*3 +1]	= hex2;
				buffer[addr*3 +2]	= 0x20;
				
				// advance current byte
				currentByte++;
			}
			else
			{
				buffer[addr*3] = 0x00;
				break;
			}
		}
		
		// clear last byte on line
		buffer[bytesPerRow*3] = 0x00;
		
		// append buffer to representation
		[representation appendString:@(buffer)];
	}
	
	return representation;
}

- (NSString *)hexString:(int)width
{
	return [self hexRepresentation:data length:width];
}

- (NSString *)hexRepresentation:(NSData *)data
{
	return [self hexRepresentation:data	length:40];
}

- (BOOL)loadDataRepresentation:(NSData *)data ofType:(NSString *)aType
{
    // Insert code here to read your document from the given data.  You can also choose to override -loadFileWrapperRepresentation:ofType: or -readFromFile:ofType: instead.
    NSLog(@"%lu, chunks %lu", (unsigned long)[data length], (unsigned long)(([data length]-512)/1024));
	PAF21Data *pafData = [[PAF21Data alloc] initWithData:data];
//	[self setString: [[NSAttributedString alloc] initWithString:[pafData hexString:40] attributes:attrsDictionary]];
	[pafData importData];
//	[textField setStringValue:[pafData hexString:40]];
    // For applications targeted for Tiger or later systems, you should use the new Tiger API readFromData:ofType:error:.  In this case you can also choose to override -readFromURL:ofType:error: or -readFromFileWrapper:ofType:error: instead.
    
    return YES;
}

/*- (id) init
{
	NSLog(@"%s", _cmd);
}
*/
- (id) initWithData: (NSData *) newData
{
    if(self = [super init])
	{
		data = newData;
		dateFormatter = [[NSDateFormatter alloc] initWithDateFormat:@"%d %b %Y" allowNaturalLanguage:NO];
		
		NSLog(@"version: %@", [self stringFromASCIIData:[data subdataWithRange:NSMakeRange(0,4)]]);
		NSLog(@"%s, %@ ", "ascii", [self hexRepresentation:[data subdataWithRange:NSMakeRange(0,4)]]);
		NSLog(@"%s, %@ ", "ascii", [self hexRepresentation:[data subdataWithRange:NSMakeRange(121,9)]]);
		NSString *apple = [self stringFromMacRomanData:[data subdataWithRange:NSMakeRange(121,9)]];
		NSLog(@"%s, %@ ", "macroman", [self hexRepresentation:[apple dataUsingEncoding:NSMacOSRomanStringEncoding]]);
		NSLog(@"%s, %@ ", "utf8    ", [self hexRepresentation:[apple dataUsingEncoding:NSUTF8StringEncoding]]);
		NSLog(@"%s, %@ ", "apple:", apple);
		
		signatureData = [data subdataWithRange:NSMakeRange(0,4)];
		reportTitleRecordData = [data subdataWithRange:NSMakeRange(4,92)];
		submitterRecordData = [data subdataWithRange:NSMakeRange(96,224)];
		preferenceData = [data subdataWithRange:NSMakeRange(320,146)];
		oneHeaderData = [data subdataWithRange:NSMakeRange(466,26)];
		firstMapChunkData = [data subdataWithRange:NSMakeRange(512,1024)];
		NSLog(@"%s: %@ ", "signature", [self stringFromASCIIData:signatureData]);
		NSLog(@"%s: %@ ", "reportTitle", [self hexRepresentation:reportTitleRecordData]);
		NSLog(@"%s: %@ ", "submitter", [self hexRepresentation:submitterRecordData]);
		NSLog(@"%s: %@ ", "prefs", [self hexRepresentation:preferenceData]);
		NSLog(@"%s: %@ ", "oneHeader", [self hexRepresentation:oneHeaderData]);
		NSLog(@"%s: %@ ", "firstmap", [self stringFromASCIIData:firstMapChunkData]);
		oneHead = [oneHeaderData bytes];
		NSLog(@"Struct: indiv: rcount = %i, chunks = %i, free=%i", oneHead->IndivRCount, oneHead->IChunkCount, oneHead->IndivFree);
		NSLog(@"Struct: marr: rcount = %i, chunks = %i, free=%i", oneHead->MarrRCount, oneHead->MChunkCount, oneHead->MarrFree);
		NSLog(@"Struct: name: rcount = %i, chunks = %i", oneHead->NameRCount, oneHead->NChunkCount);
		NSLog(@"Struct: note: rcount = %i, chunks = %i", oneHead->NoteRCount, oneHead->TChunkCount);
		NSLog(@"Struct: name index: rcount = %i, chunks = %i", oneHead->NameIndexRCount, oneHead->XChunkCount, oneHead->ChunkCount);
		NSLog(@"Struct: total chunks = %i", oneHead->ChunkCount);
		
		indivChunks = [NSMutableArray new];
		marrChunks = [NSMutableArray new];
		nameChunks = [NSMutableArray new];
		noteChunks = [NSMutableArray new];
		mapChunks = [NSMutableArray new];
		otherChunks = [NSMutableArray new];
		
		nameList = [NSMutableArray new];
		individualList = [NSMutableArray new];
		marriageList = [NSMutableArray new];
		noteList = [NSMutableArray new];
		individualLinksList = [NSMutableArray new];
		
		int totalChunks = oneHead->ChunkCount, i;
		for (i=0; i < totalChunks; i++) {
			NSData *chunk = [data subdataWithRange:NSMakeRange(1536+(1024*i),1024)];
			NSString *type = [self stringFromMacRomanData:[firstMapChunkData subdataWithRange:NSMakeRange(i,1)]];
			switch ([type characterAtIndex:0] ) {
				case 'X':
					//NSLog(@"X");
					[mapChunks addObject:chunk];
					break;
				case 'I':
					//NSLog(@"I");
					[indivChunks addObject:chunk];
					break;
				case 'M':
					//NSLog(@"M");
					[marrChunks addObject:chunk];
					break;
				case 'N':
					//NSLog(@"N");
					[nameChunks addObject:chunk];
					break;
				case 'T':
					//NSLog(@"T");
					[noteChunks addObject:chunk];
					break;
				default:
					NSLog(@"unknown chunk type: %@", type);
					[otherChunks addObject:chunk];
					break;
			}
		}
		NSLog(@"mapChunks: %lu", (unsigned long)[mapChunks count]);
		NSLog(@"indivChunks: %lu", (unsigned long)[indivChunks count]);
		NSLog(@"marrChunks: %lu", (unsigned long)[marrChunks count]);
		NSLog(@"nameChunks: %lu", (unsigned long)[nameChunks count]);
		NSLog(@"noteChunks: %lu", (unsigned long)[noteChunks count]);
		NSLog(@"otherChunks: %lu", (unsigned long)[otherChunks count]);
	}
    return self;
}

- (void)importData
{
	NSLog(@"importData");
	[self importDataIntoDocument:[[NSDocumentController sharedDocumentController] currentDocument]];
}

- (void)importDataIntoDocument:(NSObject<MAFDocumentDelegate> *)document
{
	NSLog(@"%s document: %@", sel_getName(_cmd), document);
	
//	int							x, xmax = 25;							// Just some vars so we can fake a lengthy operation
	UKProgressPanelTask*		task = [[UKProgressPanelTask alloc] init];	// Create a progress bar etc. in our progress panel, showing and creating a panel if necessary.
/*	
	// Set up the progress bar and title/status message to be shown for this task:
	[task setIndeterminate: YES];										// By default, you get a determinate scrollbar, but we want barber-pole style.
	[task setTitle: @"Inviting folks to the party"];					// Title should describe the action the user triggered, so she knows what progress bar belongs to what operation
	[task setStatus: @"The Witnesses of TeachText are everywhere..."];	// Status is the display that changes and gives some more information than the progress bar would.
	[NSBundle loadNibNamed: @"TaskProgressSheet" owner: self];      
    [NSApp beginSheet: taskProgressSheet
	   modalForWindow: [[[NSDocumentController sharedDocumentController] currentDocument] window]
		modalDelegate: self
	   didEndSelector: @selector(didEndSheet:returnCode:contextInfo:)
		  contextInfo: nil];
	
	
	for( x = 0; x <= xmax && ![task stopped]; x++ )		// Loop until we have xmax iterations or the user clicked the "Stop" button.
	{
		[task animate: nil];	// Keep the progress bar spinning.
		sleep(1);				// short delay so user can see the tasks in the task panel. Otherwise this loop would be over before the user even notices.
	}
	
	[task release];		// Remove the progress bar, status fields etc. from the progress panel, we're finished!
*/	
	//		int datetest = 8061098 << 8;
	//	NSData *dateData = [[NSData alloc] initWithBytes:&datetest+1 length:3];
	//    NSLog(@"%s, %@ ", "datedata", [self hexRepresentation:dateData]);
	//	struct RegDate_t *rdate = &datetest;
	//	NSLog(@"regdate %i %i %i %i", rdate->year, rdate->month, rdate->day, rdate->modifier);
	//	struct RegDate_t rdate2;
	//	rdate2.year = 1968;
	//	rdate2.month = 1;
	//	rdate2.day = 10;
	//	rdate2.modifier = 2;
	//	NSLog(@"regdate %i %i %i %i", rdate2.year, rdate2.month, rdate2.day, rdate2.modifier);
	//    NSLog(@"%s, %@ ", "rdate2", [self hexRepresentation:[[NSData alloc] initWithBytes:&rdate2 length:4]]);
	//	NSManagedObjectContext *context = [[[NSDocumentController sharedDocumentController] currentDocument] managedObjectContext];
	// names
	{
		[nameList removeAllObjects];
		int recCount = oneHead->NameRCount, recSize = 21, recsPerChunk = 1024 / recSize;
		unsigned int i;//, count = [nameChunks count], curr=0;
			for (i = 0; i < recCount; i++) {
				int chunkIndex = i / recsPerChunk;
				int recIndex = i % recsPerChunk;
				int offset = recSize * recIndex;
				NSLog(@"i=%i chunkIndex=%i recIndex=%i offset=%i", i, chunkIndex, recIndex, offset);
				
				NSData * obj = nameChunks[chunkIndex];
				struct NameRecord_t *nameRec = [[obj subdataWithRange:NSMakeRange(offset,recSize)] bytes];
				int leftLink = EndianU16_LtoN(nameRec->leftLink);
				int rightLink = EndianU16_LtoN(nameRec->rightLink);
				NSString *name = [NSString stringWithCString:nameRec->name encoding:NSMacOSRomanStringEncoding];
				NSLog(@"namerec bytes=%@ name=%@ leftLink=%i rightLink=%i", [self hexRepresentation:[name dataUsingEncoding:NSUnicodeStringEncoding]], name, leftLink, rightLink);
				
				[nameList addObject:name];
			}
			NSLog(@"namelist: %@", nameList);
	}	
	
	//	NSMutableArray *notepadLinks = [NSMutableArray new];
	//	NSMutableArray *internalLinks = [NSMutableArray new];
	{	
		// notes
		int recCount = oneHead->NoteRCount, recSize = 256, recsPerChunk = 1024 / recSize;
		unsigned int i;
		NSMutableArray *noteRecordList = [NSMutableArray new];
		NSMutableArray *noteDataList = [NSMutableArray new];
		// the first record of the notes is a header record that we can ignore
		[noteRecordList addObject:[NSNull null]];
		for (i = 1; i < recCount; i++) {
			int chunkIndex = i / recsPerChunk;
			int recIndex = i % recsPerChunk;
			int offset = recSize * recIndex;
			NSLog(@"i=%i chunkIndex=%i recIndex=%i offset=%i", i, chunkIndex, recIndex, offset);
			
			NSData * obj = noteChunks[chunkIndex];
			[noteRecordList addObject:[obj subdataWithRange:NSMakeRange(offset,recSize)]];
		}
		for (i = 0; i < [noteRecordList count]; i++) {
			int currRec = i;
			NSData *obj = noteRecordList[i];
			NSMutableData *data = [NSMutableData data];
			while (![obj isEqualTo:[NSNull null]]) {
				unsigned short nextNote = [self shortFromData:obj atOffset:0]; // one-based index
				nextNote = (nextNote == 0 ? 0 : nextNote-1);// convert to zero-based index
					[data appendData:[obj subdataWithRange:NSMakeRange(2,254)]];
					NSLog(@"note curr=%i nextNoteIndex=%i noteData=%@", currRec, nextNote, data);
					
					noteRecordList[currRec] = [NSNull null];
					
					//NSLog(@"combined:\n%@", [self combineNoteString:[noteList objectAtIndex:i]]);
					obj = noteRecordList[nextNote];
					currRec = nextNote;
			}
			[noteDataList addObject:data];
		}
		NSLog(@"noterecordlist: %@\n@notedatalist: %@", noteRecordList, noteDataList);	
		for (i = 0; i < [noteDataList count]; i++) {
			NSData *data = noteDataList[i];
			if ([data length] > 0) {
				NSLog(@"doc %@ ctrl %@ currdoc %@", document, [NSDocumentController sharedDocumentController], [[NSDocumentController sharedDocumentController] currentDocument]);
				id newNote = [document performSelector:@selector(createAndInsertNewNote)];
				NSString *text = [self cleanNoteString:[self stringFromMacRomanData:data]];
				[newNote takeValue:text	forKey:@"text"];
				
				[noteList addObject:newNote];
			} else {
				[noteList addObject:[NSNull null]];
			}
		}
		NSLog(@"notelist: %@", [noteList valueForKeyPath:@"description"]);	
	}
	{	
		// individuals
		int recCount = oneHead->IndivRCount, recSize = 92, recsPerChunk = 1024 / recSize;
		unsigned int i;//, count = [nameChunks count], curr=0;
			for (i = 0; i < recCount; i++) {
				int chunkIndex = i / recsPerChunk;
				int recIndex = i % recsPerChunk;
				int offset = recSize * recIndex;
				NSLog(@"i=%i chunkIndex=%i recIndex=%i offset=%i", i, chunkIndex, recIndex, offset);
				
				NSData * obj = indivChunks[chunkIndex];
				
				NSData *genderData = [obj subdataWithRange:NSMakeRange(offset+10,1)];
				NSString *gender = [self stringFromMacRomanData:genderData];
				NSLog(@"indiv gender=%@", gender);
				if ([gender isEqualTo:@"D"]) {
					NSLog(@"%s skipping deleted individual %i", sel_getName(_cmd), i);
					[individualList addObject:[NSNull null]];
					[individualLinksList addObject:[NSNull null]];
				} else {

				unsigned short surname = [self shortFromData:obj atOffset:offset+0];
				unsigned short givenName1 = [self shortFromData:obj atOffset:offset+2];
				unsigned short givenName2 = [self shortFromData:obj atOffset:offset+4];
				unsigned short givenName3 = [self shortFromData:obj atOffset:offset+6];
				unsigned short title = [self shortFromData:obj atOffset:offset+8];
				NSLog(@"swapped indiv surname=%i givenname1=%i givenname2=%i givenname3=%i", surname, givenName1, givenName2, givenName3);
				NSLog(@"surname=%@ givenname1=%@ givenname2=%@ givenname3=%@", [self nameForPointer:surname], [self nameForPointer:givenName1], [self nameForPointer:givenName2], [self nameForPointer:givenName3]);
				NSString *givenNames = [@[[self nameForPointer:givenName1],
									[self nameForPointer:givenName2],
									[self nameForPointer:givenName3]] componentsJoinedByString:@" "];
				NSLog(@"givenNames:%@", givenNames);
				
				struct DualDate_t *birthDate = [self dualDateFromData:obj atOffset:offset+11];
				NSString *birthPlaceString = [self placeStringFromData:obj atOffset:offset+15];
				struct DualDate_t *christeningDate = [self dualDateFromData:obj atOffset:offset+23];
				NSString *christeningPlaceString = [self placeStringFromData:obj atOffset:offset+27];
				struct DualDate_t *deathDate = [self dualDateFromData:obj atOffset:offset+35];
				NSString *deathPlaceString = [self placeStringFromData:obj atOffset:offset+39];
				struct DualDate_t *burialDate = [self dualDateFromData:obj atOffset:offset+47];
				NSString *burialPlaceString = [self placeStringFromData:obj atOffset:offset+51];
				
				struct RegDate_t *baptismDate = [self regDateFromData:obj atOffset:offset+59];
				NSString *baptismTempleString = [self nameForPointer:[self shortFromData:obj atOffset:offset+62]];
				struct RegDate_t *endowmentDate = [self regDateFromData:obj atOffset:offset+64];
				NSString *endowmentTempleString = [self nameForPointer:[self shortFromData:obj atOffset:offset+67]];
				struct RegDate_t *childToParentSealingDate = [self regDateFromData:obj atOffset:offset+69];
				NSString *childToParentSealingTempleString = [self nameForPointer:[self shortFromData:obj atOffset:offset+72]];
				unsigned short olderSibling = [self shortFromData:obj atOffset:offset+74];
				unsigned short individualMarriageRecord = [self shortFromData:obj atOffset:offset+76];
				unsigned short parentsMarriageRecord = [self shortFromData:obj atOffset:offset+78];
				NSMutableDictionary *individualLinksDict = [NSMutableDictionary dictionary];
				individualLinksDict[@"olderSibling"] = @(olderSibling);
				individualLinksDict[@"individualMarriageRecord"] = @(individualMarriageRecord);
				individualLinksDict[@"parentsMarriageRecord"] = @(parentsMarriageRecord);
				NSString *ID = [NSString stringWithCString:[[obj subdataWithRange:NSMakeRange(offset+80,10)] bytes] encoding:NSMacOSRomanStringEncoding];
				unsigned short notePadRecord = [self shortFromData:obj atOffset:offset+90];
				//	if (NotePadRecord != 0) {
				//		[notepadLinks addObject:[self noteForPointer:NotePadRecord]];
				//	}
				
/*				NSLog(@"%s Surname: %d GivenName1: %d GivenName2: %d GivenName3: %d Title: %d gender: %@", _cmd, Surname, GivenName1, GivenName2, GivenName3, Title, gender);
				NSLog(@"%s birthDate: %@ BirthPlace1: %d BirthPlace2: %d BirthPlace3: %d BirthPlace4: %d", _cmd, [self dateStringForDualDate:birthDate], BirthPlace1, BirthPlace2, BirthPlace3, BirthPlace4);
*/				
				
				id newIndividual = [document performSelector:@selector(createAndInsertNewIndividual)];
				//	[newIndividual takeValue:@"Created From ObjC" forKey:@"fullName"];
				
				[newIndividual takeValue:[self nameForPointer:surname]	forKey:@"surname"];
				[newIndividual takeValue:givenNames forKey:@"givenNames"];
				[newIndividual takeValue:[self nameForPointer:title] forKey:@"namePrefix"];

				[newIndividual takeValue:gender	forKey:@"genderAsString"];
				[newIndividual takeValue:ID forKey:@"AFN"];

				if (birthDate->year >= 100) {
					[newIndividual takeValue:[self dateStringForDualDate:birthDate]	forKeyPath:@"birthEvent.dateString"];
				}
				if ([birthPlaceString length] > 0) {
					id birthPlace=[NSClassFromString(@"com.redbugz.maf.jdom.PlaceJDOM") newWithSignature:@"(Ljava/lang/String;)",birthPlaceString];
					[newIndividual takeValue:birthPlace	forKeyPath:@"birthEvent.place"];					
				}
				if (christeningDate->year >= 100) {
					[newIndividual takeValue:[self dateStringForDualDate:christeningDate]	forKeyPath:@"christeningEvent.dateString"];
				}
				if ([christeningPlaceString length] > 0) {
					id christeningPlace=[NSClassFromString(@"com.redbugz.maf.jdom.PlaceJDOM") newWithSignature:@"(Ljava/lang/String;)",christeningPlaceString];
					[newIndividual takeValue:christeningPlace	forKeyPath:@"christeningEvent.place"];
				}
				if (deathDate->year >= 100) {
					[newIndividual takeValue:[self dateStringForDualDate:deathDate]	forKeyPath:@"deathEvent.dateString"];
				}
				if ([deathPlaceString length] > 0) {
					id deathPlace=[NSClassFromString(@"com.redbugz.maf.jdom.PlaceJDOM") newWithSignature:@"(Ljava/lang/String;)",deathPlaceString];
					[newIndividual takeValue:deathPlace	forKeyPath:@"deathEvent.place"];
				}
				if (burialDate->year >= 100) {
					[newIndividual takeValue:[self dateStringForDualDate:burialDate]	forKeyPath:@"burialEvent.dateString"];
				}
				if ([burialPlaceString length] > 0) {
					id burialPlace=[NSClassFromString(@"com.redbugz.maf.jdom.PlaceJDOM") newWithSignature:@"(Ljava/lang/String;)",burialPlaceString];
					[newIndividual takeValue:burialPlace	forKeyPath:@"burialEvent.place"];
				}
				
				if (baptismDate->year >= 100) {
					[newIndividual takeValue:[self dateStringForRegDate:baptismDate]	forKeyPath:@"LDSBaptism.dateString"];
				}
				if ([baptismTempleString length] > 0) {
					id baptismTemple=[MAFTempleList templeWithCode:baptismTempleString];
					if (baptismTemple) {
						[newIndividual takeValue:baptismTemple	forKeyPath:@"LDSBaptism.temple"];
					}
				}
				if (endowmentDate->year >= 100) {
					[newIndividual takeValue:[self dateStringForRegDate:endowmentDate]	forKeyPath:@"LDSEndowment.dateString"];
				}
				if ([endowmentTempleString length] > 0) {
					id endowmentTemple=[MAFTempleList templeWithCode:endowmentTempleString];
					if (endowmentTemple) {
						[newIndividual takeValue:endowmentTemple	forKeyPath:@"LDSEndowment.temple"];
					}
				}
				if (childToParentSealingDate->year >= 100) {
					[newIndividual takeValue:[self dateStringForRegDate:childToParentSealingDate]	forKeyPath:@"LDSSealingToParents.dateString"];
				}
				if ([childToParentSealingTempleString length] > 0) {
					id childToParentSealingTemple=[MAFTempleList templeWithCode:childToParentSealingTempleString];
					if (childToParentSealingTemple) {
						[newIndividual takeValue:childToParentSealingTemple	forKeyPath:@"LDSSealingToParents.temple"];
					}
				}

				id noteLink = [NSClassFromString(@"com.redbugz.maf.jdom.NoteLink") newWithSignature:@"(Ljava/lang/String;Lcom/redbugz/maf/jdom/MAFDocumentJDOM;)",[[self noteForPointer:notePadRecord] valueForKey:@"id"], document];
				[newIndividual addNoteLink:noteLink];
				[individualList addObject:newIndividual];
				[individualLinksList addObject:individualLinksDict];
			}
		}
	}
	
	
	{	
		// marriages
		int recCount = oneHead->MarrRCount, recSize = 28, recsPerChunk = 1024 / recSize;
		unsigned int i;//, count = [nameChunks count], curr=0;
			for (i = 0; i < recCount; i++) {
				int chunkIndex = i / recsPerChunk;
				int recIndex = i % recsPerChunk;
				int offset = recSize * recIndex;
				NSLog(@"i=%i chunkIndex=%i recIndex=%i offset=%i", i, chunkIndex, recIndex, offset);
				
				NSData * obj = marrChunks[chunkIndex];

				NSString *divorceFlag = [self stringFromMacRomanData:[obj subdataWithRange:NSMakeRange(offset+27,1)]];
				NSLog(@"marr divorce=%@", divorceFlag);
				if ([divorceFlag isEqualTo:@"D"]) {
					NSLog(@"%s skipping deleted family %i", sel_getName(_cmd), i);
					[marriageList addObject:[NSNull null]];
				} else {
				
				unsigned short husband = [self shortFromData:obj atOffset:offset+0];
				unsigned short wife = [self shortFromData:obj atOffset:offset+2];
				unsigned short youngestChild = [self shortFromData:obj atOffset:offset+4];
				NSLog(@"marr husb=%i wife=%i youngestchild=%i", husband, wife, youngestChild);
				//		NSLog(@"surname=%@ givenname1=%@ givenname2=%@ givenname3=%@", [self nameForPointer:Surname], [self nameForPointer:GivenName1], [self nameForPointer:GivenName2], [self nameForPointer:GivenName3]);
				struct DualDate_t *marriageDate = [self dualDateFromData:obj atOffset:offset+6];
				NSString *marriagePlaceString = [self placeStringFromData:obj atOffset:offset+10];
				NSLog(@"marr place=%@", marriagePlaceString);
				struct RegDate_t *sealingToSpouseDate = [self regDateFromData:obj atOffset:offset+18];
				NSString *sealingToSpouseTempleString = [self nameForPointer:[self shortFromData:obj atOffset:offset+21]];
				
				unsigned short husbandOtherMarriageRecord = [self shortFromData:obj atOffset:offset+23];
				unsigned short wifeOtherMarriageRecord = [self shortFromData:obj atOffset:offset+25];
				NSLog(@"marr sealtemple=%@ husbothmarr=%i wifeothmarr=%i", sealingToSpouseTempleString, husbandOtherMarriageRecord, wifeOtherMarriageRecord);
				
				id newMarriage = [document performSelector:@selector(createAndInsertNewFamily)];
				
				if (husband > 0) {
					[newMarriage setValue:[self individualForPointer:husband]	forKey:@"father"];
					[[self individualForPointer:husband] addFamilyAsSpouse:newMarriage];
				}
				if (wife > 0) {
					[newMarriage setValue:[self individualForPointer:wife]	forKey:@"mother"];
					[[self individualForPointer:wife] addFamilyAsSpouse:newMarriage];
				}
				if (youngestChild > 0) {
					NSMutableArray *children = [NSMutableArray array];
					[children insertObject:[self individualForPointer:youngestChild] atIndex:0];
					[[self individualForPointer:youngestChild] takeValue:newMarriage forKey:@"familyAsChild"];
					int nextChild = [[individualLinksList[youngestChild-1] valueForKeyPath:@"olderSibling"] intValue];
					while (nextChild) {
						[[self individualForPointer:nextChild] takeValue:newMarriage forKey:@"familyAsChild"];
						[children insertObject:[self individualForPointer:nextChild] atIndex:0];
						NSLog(@"children: %@", children);
						nextChild = [[individualLinksList[nextChild-1] valueForKeyPath:@"olderSibling"] intValue];
					}
					[newMarriage setValue:[NSClassFromString(@"com.redbugz.maf.util.CocoaUtils") javaListFromNSArray:children]	forKey:@"children"];
				}
				
				if (marriageDate->year >= 100) {
					[newMarriage takeValue:[self dateStringForDualDate:marriageDate]	forKeyPath:@"marriageEvent.dateString"];
				}
				if ([marriagePlaceString length] > 0) {
					id marriagePlace=[NSClassFromString(@"com.redbugz.maf.jdom.PlaceJDOM") newWithSignature:@"(Ljava/lang/String;)",marriagePlaceString];
					[newMarriage takeValue:marriagePlace	forKeyPath:@"marriageEvent.place"];					
				}
				
				if (sealingToSpouseDate->year >= 100) {
					[newMarriage takeValue:[self dateStringForRegDate:sealingToSpouseDate]	forKeyPath:@"sealingToSpouse.dateString"];
				}
				if ([sealingToSpouseTempleString length] > 0) {
					id sealingToSpouseTemple=[MAFTempleList templeWithCode:sealingToSpouseTempleString];
					if (sealingToSpouseTemple) {
						[newMarriage takeValue:sealingToSpouseTemple	forKeyPath:@"sealingToSpouse.temple"];
					}
				}

				if ([divorceFlag isEqualTo:@"Y"]) {
					[newMarriage addEvent:[NSClassFromString(@"com.redbugz.maf.jdom.EventJDOM") createDivorceEventInstance]];
				}
				/*
				 father.setGender(Gender.MALE);
				 father.setFamilyAsSpouse(family);
				 family.setFather(father);
				 
				 mother.setFamilyAsSpouse(family);
				 family.setMother(mother);
				 
				 family.getMarriageEvent().setDateString(marriageForm.cellAtIndex(0).stringValue());
				 family.getMarriageEvent().setPlace(new PlaceJDOM(marriageForm.cellAtIndex(1).stringValue()));
				 family.getSealingToSpouse().setDateString(sealingDate.stringValue());
				 family.getSealingToSpouse().setTemple(CocoaUtils.templeForComboBox(sealingTemple));
				 */	
				[marriageList addObject:newMarriage];
			
			}
		}
	}
	
	//	NSArray *orphans = [noteList mutableCopy];
	//	[orphans removeObjectsInArray:[internalLinks arrayByAddingObjectsFromArray:notepadLinks]];
	//	NSLog(@"notelinks internal=%i notepad=%i common=%@ orphans=%@", [internalLinks count], [notepadLinks count], [internalLinks firstObjectCommonWithArray:notepadLinks],
	//		orphans);
	//[context save:nil];
}
	
+ (void)importFromData:(NSData *)data intoDocument:(NSObject<MAFDocumentDelegate> *)document
{
	NSLog(@"%s document: %@", sel_getName(_cmd), document);
	NSLog(@"%lu, chunks %lu", (unsigned long)[data length], (unsigned long)(([data length]-512)/1024));
	PAF21Data *pafData = [[PAF21Data alloc] initWithData:data];
	[pafData importDataIntoDocument:document];
	[pafData release];
}

@end
