//
//  PAF21Data.m
//  PAFImporter
//
//  Created by Logan Allred on 12/2/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

#import "PAF21Data.h"
#import "UKProgressPanelTask.h"
#include "unistd.h"

@implementation PAF21Data

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

//@end
//
//@interface PAF21Data (Private)
//- (NSString *)hexString:(int)width;
//- (NSString *)cleanNoteString:(NSString *)noteString;
//- (NSString *)nameForPointer:(int)pointer;
//@end
//
//@implementation PAF21Data (Private)

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

- (NSString *)cleanNoteString:(NSString *)noteString {
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


- (NSCalendarDate *)dateForDualDate:(struct DualDate_t *)dualDate {
	return [NSCalendarDate dateWithYear:dualDate->year month:dualDate->month day:dualDate->day hour:0 minute:0 second:0 timeZone:nil];
}

- (NSCalendarDate *)dateForRegDate:(struct RegDate_t *)regDate {
	return [NSCalendarDate dateWithYear:regDate->year month:regDate->month day:regDate->day hour:0 minute:0 second:0 timeZone:nil];
}

- (NSString *)dateStringForDualDate:(struct DualDate_t *)dualDate {
	return [dateFormatter stringForDate:[self dateForDualDate:dualDate]];
}

- (NSString *)dateStringForRegDate:(struct RegDate_t *)regDate {
	return [dateFormatter stringForDate:[self dateStringForRegDate:regDate]];
}

- (struct RegDate_t *)regDateFromData:(NSData *)data atOffset:(int)offset {
	struct RegDate_t *value = [[data subdataWithRange:NSMakeRange(offset,3)] bytes];
	NSLog(@"regDateFromData:atOffset:%i returns: y=%i m=%i d=%i mod=%i", offset, value->year, value->month, value->day, value->modifier);
	return value;
}

- (struct DualDate_t *)dualDateFromData:(NSData *)data atOffset:(int)offset {
	struct DualDate_t *value = [[data subdataWithRange:NSMakeRange(offset,4)] bytes];
	NSLog(@"dualDateFromData:atOffset:%i returns: y=%i m=%i d=%i mod=%i dual=%i", offset, value->year, value->month, value->day, value->modifier, value->offset);
	return value;
}

- (NSString *)nameForPointer:(int)pointer {
	if (pointer > [nameList count]) {
		NSLog(@"invalid name pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return [nameList objectAtIndex:pointer-1];
}

- (id)individualForPointer:(int)pointer {
	if (pointer > [individualList count]) {
		NSLog(@"invalid individual pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return [individualList objectAtIndex:pointer-1];
}

- (id)marriageForPointer:(int)pointer {
	if (pointer > [marriageList count]) {
		NSLog(@"invalid marriage pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return [marriageList objectAtIndex:pointer-1];
}

- (id)noteForPointer:(int)pointer {
	if (pointer > [noteList count]) {
		NSLog(@"invalid note pointer %i", pointer);
		return nil;
	}
	if (pointer == 0) {
		return nil;
	}
	return [noteList objectAtIndex:pointer-1];
}


- (unsigned short)shortFromData:(NSData *)data {
	unsigned short value;
	[data getBytes:&value length:sizeof(value)];
	value = NSSwapLittleShortToHost(value);
	return value;
}

- (unsigned short)shortFromData:(NSData *)data withRange:(NSRange)range {
	unsigned short value;
	[data getBytes:&value range:range];
	value = NSSwapLittleShortToHost(value);
	return value;
}

- (unsigned short)shortFromData:(NSData *)data atOffset:(int)offset{
	return [self shortFromData:data withRange:NSMakeRange(offset,sizeof(unsigned short))];
}

//- (NSString *)stringFromASCIIBytes:(const void *)bytes length:(int)length {
//	return  [[NSString alloc] initWithBytes:bytes length:length encoding:NSASCIIStringEncoding]; // convert NSData -> NSString
//}

- (NSString *)stringFromASCIIData:(NSData *)data {
	return  [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding]; // convert NSData -> NSString
}

- (NSString *)stringFromMacRomanBytes:(const void *)bytes length:(int)length {
	return  [[NSString alloc] initWithBytes:bytes length:length encoding:NSMacOSRomanStringEncoding]; // convert NSData -> NSString
}

- (NSString *)stringFromMacRomanData:(NSData *)data {
	return  [[NSString alloc] initWithData:data encoding:NSMacOSRomanStringEncoding]; // convert NSData -> NSString
}

- (NSString *)hexRepresentation:(NSData *)data length:(int)width
{
	int currentByte = 0, dataLength = [data length], bytesPerRow = width;
	int rows = (dataLength / bytesPerRow) + ((dataLength % bytesPerRow)? 1:0);
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
		[representation appendString:[NSString stringWithCString:buffer]];
	}
	
	return representation;
}

- (NSString *)hexString:(int)width {
	return [self hexRepresentation:data length:width];
}

- (NSString *)hexRepresentation:(NSData *)data {
	return [self hexRepresentation:data	length:40];
}

- (BOOL)loadDataRepresentation:(NSData *)data ofType:(NSString *)aType
{
    // Insert code here to read your document from the given data.  You can also choose to override -loadFileWrapperRepresentation:ofType: or -readFromFile:ofType: instead.
    NSLog(@"%i, chunks %i", [data length], ([data length]-512)/1024);
	PAF21Data *pafData = [[PAF21Data alloc] initWithData:data];
//	[self setString: [[NSAttributedString alloc] initWithString:[pafData hexString:40] attributes:attrsDictionary]];
	[pafData importData];
//	[textField setStringValue:[pafData hexString:40]];
    // For applications targeted for Tiger or later systems, you should use the new Tiger API readFromData:ofType:error:.  In this case you can also choose to override -readFromURL:ofType:error: or -readFromFileWrapper:ofType:error: instead.
    
    return YES;
}

- (id) init
{
}

- (id) initWithData: (NSData *) newData
{
    self = [super init];
	data = [newData retain];
	dateFormatter = [[NSDateFormatter alloc] initWithDateFormat:@"%d %b %Y" allowNaturalLanguage:NO];
	
	NSLog(@"version: %@", [self stringFromASCIIData:[data subdataWithRange:NSMakeRange(0,4)]]);
    NSLog(@"%s, %@ ", "ascii", [self hexRepresentation:[data subdataWithRange:NSMakeRange(0,4)]]);
    NSLog(@"%s, %@ ", "ascii", [self hexRepresentation:[data subdataWithRange:NSMakeRange(121,9)]]);
	NSString *apple = [self stringFromMacRomanData:[data subdataWithRange:NSMakeRange(121,9)]];
    NSLog(@"%s, %@ ", "macroman", [self hexRepresentation:[apple dataUsingEncoding:NSMacOSRomanStringEncoding]]);
    NSLog(@"%s, %@ ", "utf8    ", [self hexRepresentation:[apple dataUsingEncoding:NSUTF8StringEncoding]]);
    NSLog(@"%s, %@ ", "apple:", apple);
	
	signatureData = [[data subdataWithRange:NSMakeRange(0,4)] retain];
	reportTitleRecordData = [[data subdataWithRange:NSMakeRange(4,92)] retain];
	submitterRecordData = [[data subdataWithRange:NSMakeRange(96,224)] retain];
	preferenceData = [[data subdataWithRange:NSMakeRange(320,146)] retain];
	oneHeaderData = [[data subdataWithRange:NSMakeRange(466,26)] retain];
	firstMapChunkData = [[data subdataWithRange:NSMakeRange(512,1024)] retain];
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
	
	indivChunks = [[NSMutableArray new] retain];
	marrChunks = [[NSMutableArray new] retain];
	nameChunks = [[NSMutableArray new] retain];
	noteChunks = [[NSMutableArray new] retain];
	mapChunks = [[NSMutableArray new] retain];
	otherChunks = [[NSMutableArray new] retain];
	
	nameList = [[NSMutableArray new] retain];
	individualList = [[NSMutableArray new] retain];
	marriageList = [[NSMutableArray new] retain];
	noteList = [[NSMutableArray new] retain];
	
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
				NSLog(@"default: %@", type);
				[otherChunks addObject:chunk];
				break;
		}
	}
	NSLog(@"mapChunks: %i", [mapChunks count]);
	NSLog(@"indivChunks: %i", [indivChunks count]);
	NSLog(@"marrChunks: %i", [marrChunks count]);
	NSLog(@"nameChunks: %i", [nameChunks count]);
	NSLog(@"noteChunks: %i", [noteChunks count]);
	NSLog(@"otherChunks: %i", [otherChunks count]);
	
    return self;
}

- (void)importData {
	NSLog(@"importData");
	
	int							x, xmax = 25;							// Just some vars so we can fake a lengthy operation
	UKProgressPanelTask*		task = [UKProgressPanelTask newProgressPanelTask];	// Create a progress bar etc. in our progress panel, showing and creating a panel if necessary.
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
				
				NSData * obj = [nameChunks objectAtIndex:chunkIndex];
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
		for (i = 0; i < recCount; i++) {
			int chunkIndex = i / recsPerChunk;
			int recIndex = i % recsPerChunk;
			int offset = recSize * recIndex;
			NSLog(@"i=%i chunkIndex=%i recIndex=%i offset=%i", i, chunkIndex, recIndex, offset);
			
			NSData * obj = [noteChunks objectAtIndex:chunkIndex];
			[noteRecordList addObject:[obj subdataWithRange:NSMakeRange(offset,recSize)]];
		}
		for (i = 0; i < [noteRecordList count]; i++) {
			int currRec = i;
			NSData *obj = [noteRecordList objectAtIndex:i];
			NSMutableData *data = [NSMutableData data];
			while (![obj isEqualTo:[NSNull null]]) {
				unsigned short nextNote = [self shortFromData:obj atOffset:0]; // one-based index
				nextNote = (nextNote == 0 ? 0 : nextNote-1);// convert to zero-based index
					[data appendData:[obj subdataWithRange:NSMakeRange(2,254)]];
					NSLog(@"note curr=%i nextNote=%i noteData=%@", currRec, nextNote, data);
					
					[noteRecordList replaceObjectAtIndex:currRec withObject:[NSNull null]];
					
					//NSLog(@"combined:\n%@", [self combineNoteString:[noteList objectAtIndex:i]]);
					obj = [noteRecordList objectAtIndex:nextNote];
					currRec = nextNote;
			}
			[noteDataList addObject:data];
		}
		for (i = 0; i < [noteDataList count]; i++) {
			NSData *data = [noteDataList objectAtIndex:i];
			if ([data length] > 0) {
				id newNote = [[[NSDocumentController sharedDocumentController] currentDocument] performSelector:@selector(createAndInsertNewNote)];
				NSString *text = [self cleanNoteString:[self stringFromMacRomanData:data]];
				[newNote takeValue:text	forKey:@"text"];
				
				[noteList addObject:newNote];
			}
		}
		NSLog(@"notelist: %@", [noteList valueForKeyPath:@"text"]);	
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
				
				NSData * obj = [indivChunks objectAtIndex:chunkIndex];
				
				unsigned short Surname = [self shortFromData:obj atOffset:offset+0];
				unsigned short GivenName1 = [self shortFromData:obj atOffset:offset+2];
				unsigned short GivenName2 = [self shortFromData:obj atOffset:offset+4];
				unsigned short GivenName3 = [self shortFromData:obj atOffset:offset+6];
				unsigned short Title = [self shortFromData:obj atOffset:offset+8];
				NSLog(@"swapped indiv surname=%i givenname1=%i givenname2=%i givenname3=%i", Surname, GivenName1, GivenName2, GivenName3);
				NSLog(@"surname=%@ givenname1=%@ givenname2=%@ givenname3=%@", [self nameForPointer:Surname], [self nameForPointer:GivenName1], [self nameForPointer:GivenName2], [self nameForPointer:GivenName3]);
				
				NSString *sex = [self stringFromMacRomanData:[obj subdataWithRange:NSMakeRange(offset+10,1)]];
				NSLog(@"indiv gender=%@", sex);
				struct DualDate_t *birthDate = [self dualDateFromData:obj atOffset:offset+11];
				unsigned short BirthPlace1 = [self shortFromData:obj atOffset:offset+15];
				unsigned short BirthPlace2 = [self shortFromData:obj atOffset:offset+17];
				unsigned short BirthPlace3 = [self shortFromData:obj atOffset:offset+19];
				unsigned short BirthPlace4 = [self shortFromData:obj atOffset:offset+21];
				struct DualDate_t *ChristeningDate = [self dualDateFromData:obj atOffset:offset+23];
				unsigned short ChristeningPlace1 = [self shortFromData:obj atOffset:offset+27];
				unsigned short ChristeningPlace2 = [self shortFromData:obj atOffset:offset+29];
				unsigned short ChristeningPlace3 = [self shortFromData:obj atOffset:offset+31];
				unsigned short ChristeningPlace4 = [self shortFromData:obj atOffset:offset+33];
				struct DualDate_t *DeathDate = [self dualDateFromData:obj atOffset:offset+35];
				unsigned short DeathPlace1 = [self shortFromData:obj atOffset:offset+39];
				unsigned short DeathPlace2 = [self shortFromData:obj atOffset:offset+41];
				unsigned short DeathPlace3 = [self shortFromData:obj atOffset:offset+43];
				unsigned short DeathPlace4 = [self shortFromData:obj atOffset:offset+45];
				struct DualDate_t *BurialDate = [self dualDateFromData:obj atOffset:offset+47];
				unsigned short BurialPlace1 = [self shortFromData:obj atOffset:offset+51];
				unsigned short BurialPlace2 = [self shortFromData:obj atOffset:offset+53];
				unsigned short BurialPlace3 = [self shortFromData:obj atOffset:offset+55];
				unsigned short BurialPlace4 = [self shortFromData:obj atOffset:offset+57];
				struct RegDate_t *BaptismDate = [self regDateFromData:obj atOffset:offset+59];
				unsigned short BaptismTemple = [self shortFromData:obj atOffset:offset+62];
				struct RegDate_t *EndowmentDate = [self regDateFromData:obj atOffset:offset+64];
				unsigned short EndowmentTemple = [self shortFromData:obj atOffset:offset+67];
				struct RegDate_t *ChildToParentSealingDate = [self regDateFromData:obj atOffset:offset+69];
				unsigned short ChildToParentSealingTemple = [self shortFromData:obj atOffset:offset+72];
				unsigned short OlderSibling = [self shortFromData:obj atOffset:offset+74];
				unsigned short IndividualMarriageRecord = [self shortFromData:obj atOffset:offset+76];
				unsigned short ParentsMarriageRecord = [self shortFromData:obj atOffset:offset+78];
				NSString *ID = [NSString stringWithCString:[[obj subdataWithRange:NSMakeRange(offset+80,10)] bytes] encoding:NSMacOSRomanStringEncoding];
				unsigned short NotePadRecord = [self shortFromData:obj atOffset:offset+90];
				//	if (NotePadRecord != 0) {
				//		[notepadLinks addObject:[self noteForPointer:NotePadRecord]];
				//	}
				
				
				
				id newIndividual = [[[NSDocumentController sharedDocumentController] currentDocument] performSelector:@selector(createAndInsertNewIndividual)];
				//	[newIndividual takeValue:@"Created From ObjC" forKey:@"fullName"];
				
				[newIndividual takeValue:[self nameForPointer:Surname]	forKey:@"surname"];
				NSString *givenNames = [[NSArray arrayWithObjects:
					[self nameForPointer:GivenName1],
					[self nameForPointer:GivenName2],
					[self nameForPointer:GivenName3], nil] componentsJoinedByString:@" "];
NSLog(@"givenNames:%@", givenNames);
				[newIndividual takeValue:givenNames forKey:@"givenNames"];
				[newIndividual takeValue:[self dateStringForDualDate:birthDate]	forKeyPath:@"birthEvent.dateString"];
//				[newIndividual takeValue:[self noteForPointer:NotePadRecord]	forKey:@"noteText"];
				[newIndividual takeValue:sex	forKey:@"gender"];
				/*
				 individual.setSurname(surname.stringValue());
				 individual.setGivenNames(givenNames.stringValue());
				 individual.setNamePrefix(prefix.stringValue());
				 individual.setNameSuffix(suffix.stringValue());
				 log.debug("IndividualEditController.save() gendercode:"+gender.titleOfSelectedItem());
				 log.debug("IndividualEditController.save() gender:"+Gender.genderWithCode(gender.titleOfSelectedItem()));
				 individual.setGender(Gender.genderWithCode(gender.titleOfSelectedItem()));
				 individual.setAFN(afn.stringValue());
				 individual.getBirthEvent().setDateString(birthForm.cellAtIndex(0).stringValue());
				 individual.getBirthEvent().setPlace(new PlaceJDOM(birthForm.cellAtIndex(1).stringValue()));
				 individual.getChristeningEvent().setDateString(christeningForm.cellAtIndex(0).stringValue());
				 individual.getChristeningEvent().setPlace(new PlaceJDOM(christeningForm.cellAtIndex(1).stringValue()));
				 individual.getDeathEvent().setDateString(deathForm.cellAtIndex(0).stringValue());
				 individual.getDeathEvent().setPlace(new PlaceJDOM(deathForm.cellAtIndex(1).stringValue()));
				 individual.getBurialEvent().setDateString(burialForm.cellAtIndex(0).stringValue());
				 individual.getBurialEvent().setPlace(new PlaceJDOM(burialForm.cellAtIndex(1).stringValue()));
				 individual.getLDSBaptism().setDateString(baptismDate.stringValue());
				 individual.getLDSBaptism().setTemple(CocoaUtils.templeForComboBox(baptismTemple));
				 individual.getLDSEndowment().setDateString(endowmentDate.stringValue());
				 individual.getLDSEndowment().setTemple(CocoaUtils.templeForComboBox(endowmentTemple));
				 individual.getLDSSealingToParents().setDateString(sealingToParentDate.stringValue());
				 individual.getLDSSealingToParents().setTemple(CocoaUtils.templeForComboBox(sealingToParentTemple));
				 */	
				[individualList addObject:newIndividual];
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
				
				NSData * obj = [marrChunks objectAtIndex:chunkIndex];
				
				unsigned short husband = [self shortFromData:obj atOffset:offset+0];
				unsigned short wife = [self shortFromData:obj atOffset:offset+2];
				unsigned short youngestChild = [self shortFromData:obj atOffset:offset+4];
				NSLog(@"marr husb=%i wife=%i youngestchild=%i", husband, wife, youngestChild);
				//		NSLog(@"surname=%@ givenname1=%@ givenname2=%@ givenname3=%@", [self nameForPointer:Surname], [self nameForPointer:GivenName1], [self nameForPointer:GivenName2], [self nameForPointer:GivenName3]);
				struct DualDate_t *marriageDate = [self dualDateFromData:obj atOffset:offset+6];
				unsigned short marriagePlace1 = [self shortFromData:obj atOffset:offset+10];
				unsigned short marriagePlace2 = [self shortFromData:obj atOffset:offset+12];
				unsigned short marriagePlace3 = [self shortFromData:obj atOffset:offset+14];
				unsigned short marriagePlace4 = [self shortFromData:obj atOffset:offset+16];
				NSLog(@"marr place1=%i place2=%i place3=%i place4=%i", marriagePlace1, marriagePlace2, marriagePlace3, marriagePlace4);
				struct RegDate_t *sealingToSpouseDate = [self regDateFromData:obj atOffset:offset+18];
				unsigned short sealingToSpouseTemple = [self shortFromData:obj atOffset:offset+21];
				unsigned short husbandOtherMarriageRecord = [self shortFromData:obj atOffset:offset+23];
				unsigned short wifeOtherMarriageRecord = [self shortFromData:obj atOffset:offset+25];
				NSLog(@"marr sealtemple=%i husbothmarr=%i wifeothmarr=%i", sealingToSpouseTemple, husbandOtherMarriageRecord, wifeOtherMarriageRecord);
				NSString *divorceFlag = [self stringFromMacRomanData:[obj subdataWithRange:NSMakeRange(offset+27,1)]];
				NSLog(@"marr divorce=%@", divorceFlag);
				
				id newMarriage = [[[NSDocumentController sharedDocumentController] currentDocument] performSelector:@selector(createAndInsertNewFamily)];
				
				//	[newMarriage takeValue:[self nameForPointer:Surname]	forKey:@"surname"];
				//	[newMarriage takeValue:givenNames forKey:@"givenNames"];
				//	[newMarriage takeValue:[self dateForDualDate:birthDate]	forKeyPath:@"birthEvent.date"];
				//	[newMarriage takeValue:[self noteForPointer:NotePadRecord]	forKey:@"notepad"];
				//	[newMarriage takeValue:sex	forKey:@"gender"];
				
				if (husband > 0) {
					[newMarriage setValue:[self individualForPointer:husband]	forKey:@"father"];
					[[self individualForPointer:husband] setValue:newMarriage	forKey:@"familyAsSpouse"];
				}
				if (wife > 0) {
					[newMarriage setValue:[self individualForPointer:wife]	forKey:@"mother"];
					[[self individualForPointer:wife] setValue:newMarriage	forKey:@"familyAsSpouse"];
				}
					//	[newMarriage setValue:[self individualForPointer:youngestChild]	forKey:@"youngestChild"];
				[newMarriage setValue:[self dateStringForDualDate:marriageDate]	forKeyPath:@"marriageEvent.dateString"];
				//	[newMarriage setValue:[self nameForPointer:marriagePlace1]	forKey:@"marriagePlace1"];
				//	[newMarriage setValue:[self nameForPointer:marriagePlace2]	forKey:@"marriagePlace2"];
				//	[newMarriage setValue:[self nameForPointer:marriagePlace3]	forKey:@"marriagePlace3"];
				//	[newMarriage setValue:[self nameForPointer:marriagePlace4]	forKey:@"marriagePlace4"];
				[newMarriage setValue:[self dateStringForRegDate:sealingToSpouseDate]	forKey:@"sealingToSpouse.dateString"];
				//	[newMarriage setValue:[self nameForPointer:sealingToSpouseTemple]	forKey:@"sealingToSpouseTemple"];
				//	[newMarriage setValue:[divorceFlag isCaseInsensitiveLike:@"Y"]	forKey:@"divorced"];
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
	
	//	NSArray *orphans = [noteList mutableCopy];
	//	[orphans removeObjectsInArray:[internalLinks arrayByAddingObjectsFromArray:notepadLinks]];
	//	NSLog(@"notelinks internal=%i notepad=%i common=%@ orphans=%@", [internalLinks count], [notepadLinks count], [internalLinks firstObjectCommonWithArray:notepadLinks],
	//		orphans);
	//[context save:nil];
}
	
	+ (id)importFromData:(NSData *)data {
	    NSLog(@"%i, chunks %i", [data length], ([data length]-512)/1024);
		PAF21Data *pafData = [[PAF21Data alloc] initWithData:data];
		[pafData importData];
		return pafData;
	}

@end
