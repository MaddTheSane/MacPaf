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

@interface MAFDocument ()
@property (strong, readwrite) NSMutableSet *peopleList;
@property (strong, readwrite) NSMutableSet *familyList;
@end

@implementation MAFDocument

- (id)init
{
	if (self = [super init]) {
		self.peopleList = [NSMutableSet new];
		self.familyList = [NSMutableSet new];
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

@end
