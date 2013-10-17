//
//  MAFDocument.m
//  MAF
//
//  Created by C.W. Betts on 10/16/13.
//
//

#import "MAFDocument.h"

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

- (id)createAndInsertNewIndividual
{
	return nil;
}

- (id)createAndInsertNewFamily
{
	
}

@end
