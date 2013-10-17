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
@property (strong, readonly) NSMutableArray *peopleList;
@property (strong, readonly) NSMutableArray *familyList;
@property (strong, readonly) NSMutableArray *notesList;
@property (strong, readonly) NSMutableArray *locationList;

- (MAFPerson*)createAndInsertNewIndividual NS_RETURNS_RETAINED;
- (MAFFamily*)createAndInsertNewFamily NS_RETURNS_RETAINED;
- (NSMutableString*)createAndInsertNewNote NS_RETURNS_RETAINED;

- (int64_t)locationFromString:(NSString*)placeName;

- (void)importFromDocument:(MAFDocument*)toImport;

@end
