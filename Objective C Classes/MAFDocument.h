//
//  MAFDocument.h
//  MAF
//
//  Created by C.W. Betts on 10/16/13.
//
//

#import <Foundation/Foundation.h>

@class MAFPerson;
@class MAFFamily;

@interface MAFDocument : NSObject
@property (strong, readonly) NSMutableSet *peopleList;
@property (strong, readonly) NSMutableSet *familyList;

- (MAFPerson*)createAndInsertNewIndividual NS_RETURNS_RETAINED;
- (MAFFamily*)createAndInsertNewFamily NS_RETURNS_RETAINED;

@end
