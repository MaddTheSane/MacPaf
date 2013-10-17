//
//  MAFDocument.m
//  MAF
//
//  Created by C.W. Betts on 10/16/13.
//
//

#import "MAFDocument.h"
#import "MAFPerson.h"
#import "MAFFamily.h"
#import "MAFLocation.h"

@interface MAFDocument ()
@property (strong, readwrite) NSMutableArray *peopleList;
@property (strong, readwrite) NSMutableArray *familyList;
@property (strong, readwrite) NSMutableArray *notesList;
@property (strong, readwrite) NSMutableArray *locationList;
@end

@implementation MAFDocument

- (void)importFromDocument:(MAFDocument*)toImport
{
	
}

- (id)init
{
	if (self = [super init]) {
		self.peopleList = [NSMutableArray new];
		self.familyList = [NSMutableArray new];
		self.notesList = [NSMutableArray new];
		self.locationList = [NSMutableArray new];
	}
	return self;
}

- (MAFPerson*)createAndInsertNewIndividual
{
	MAFPerson *newval = [[MAFPerson alloc] init];
	[self.peopleList addObject:newval];
	return newval;
}

- (MAFFamily*)createAndInsertNewFamily
{
	MAFFamily *newval = [[MAFFamily alloc] init];
	[self.familyList addObject:newval];
	return newval;
}

- (NSMutableString*)createAndInsertNewNote
{
	NSMutableString *newStr = [[NSMutableString alloc] init];
	[self.notesList addObject:newStr];
	return newStr;
}

- (int64_t)locationFromString:(NSString*)placeName
{
	int64_t openSlot = 1, prevNumber = 0;
	for (MAFLocation *loc in self.locationList) {
		if ([loc.locationName isEqualToString:placeName]) {
			return loc.locationNumber;
		}
		if (prevNumber + 1 < loc.locationNumber) {
			openSlot = prevNumber + 1;
		}

		prevNumber = loc.locationNumber;
	}
	
	if (!(openSlot == 1 && prevNumber == 0)) {
		if (openSlot == 1) {
			openSlot = prevNumber + 1;
		}
	}
	MAFLocation *newloc = [[MAFLocation alloc] init];
	newloc.locationName = placeName;
	newloc.locationNumber = openSlot;
	[self.locationList addObject:newloc];
	return newloc.locationNumber;
}

@end
