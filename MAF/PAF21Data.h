//
//  PAF21Data.h
//  PAFImporter
//
//  Created by Logan Allred on 12/2/06.
//  Copyright 2006 RedBugz Software. All rights reserved.
//

#import <Cocoa/Cocoa.h>


@interface PAF21Data : NSObject {
NSArray *individualRecords;
NSArray *familyRecords;
NSArray *nameRecords;
NSArray *noteRecords;
NSMutableArray *nameList;
NSMutableArray *individualList;
NSMutableArray *marriageList;
NSMutableArray *noteList;
NSMutableArray *individualLinksList;
}
- (id)initWithData:(NSData *)data;

- (void)importData;
- (void)importDataIntoDocument:(NSDocument *)document;

+ (void)importFromData:(NSData *)data intoDocument:(NSDocument *)document;

@end
