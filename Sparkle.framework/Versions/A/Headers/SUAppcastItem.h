//
//  SUAppcastItem.h
//  Sparkle
//
//  Created by Andy Matuschak on 3/12/06.
//  Copyright 2006 Andy Matuschak. All rights reserved.
//

#ifndef SUAPPCASTITEM_H
#define SUAPPCASTITEM_H

@interface SUAppcastItem : NSObject
@property (copy, readonly)		NSString *title;
@property (strong, readonly)	NSDate *date;
@property (copy, readonly)		NSString *itemDescription;
@property (strong, readonly)	NSURL *releaseNotesURL;
@property (copy, readonly)		NSString *DSASignature;
@property (copy, readonly)		NSString *minimumSystemVersion;
@property (strong, readonly)	NSURL *fileURL;
@property (copy, readonly)		NSString *versionString;
@property (copy, readonly)		NSString *displayVersionString;
	
@property (strong, readonly)	NSDictionary *propertiesDictionary;


// Initializes with data from a dictionary provided by the RSS class.
- initWithDictionary:(NSDictionary *)dict;

- (NSString *)title;
- (NSString *)versionString;
- (NSString *)displayVersionString;
- (NSDate *)date;
- (NSString *)itemDescription;
- (NSURL *)releaseNotesURL;
- (NSURL *)fileURL;
- (NSString *)DSASignature;
- (NSString *)minimumSystemVersion;

// Returns the dictionary provided in initWithDictionary; this might be useful later for extensions.
- (NSDictionary *)propertiesDictionary;

@end

#endif
