//
//  MAFDocument.h
//  MAF
//
//  Created by C.W. Betts on 10/16/13.
//
//

#import <Foundation/Foundation.h>


@interface MAFDocument : NSObject
@property (strong, readonly) NSMutableSet *peopleList;
@property (strong, readonly) NSMutableSet *familyList;

- (id)createAndInsertNewIndividual;
- (id)createAndInsertNewFamily;

@end
