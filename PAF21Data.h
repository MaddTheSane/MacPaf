//
//  PAF21Data.h
//  PAFImporter
//
//  Created by Logan Allred on 12/2/06.
//  Copyright 2006 RedBugz Software. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "MAFDocument.h"

@interface PAF21Data : NSObject
@property (strong) NSArray *individualRecords;
@property (strong) NSArray *familyRecords;
@property (strong) NSArray *nameRecords;
@property (strong) NSArray *noteRecords;
@property (strong) NSMutableArray *nameList;
@property (strong) NSMutableArray *individualList;
@property (strong) NSMutableArray *marriageList;
@property (strong) NSMutableArray *noteList;
@property (strong) NSMutableArray *individualLinksList;

- (id)initWithData:(NSData *)data;

- (void)importData;
- (void)importDataIntoDocument:(MAFDocument *)document;

+ (void)importFromData:(NSData *)data intoDocument:(MAFDocument *)document;

@end
