//
//  SUAppcast.h
//  Sparkle
//
//  Created by Andy Matuschak on 3/12/06.
//  Copyright 2006 Andy Matuschak. All rights reserved.
//

#ifndef SUAPPCAST_H
#define SUAPPCAST_H

@protocol SUAppcastDelegate;

@class SUAppcastItem;
@interface SUAppcast : NSObject {
	NSArray *items;
	NSMutableData *incrementalData;
}
@property (copy) NSString *userAgentString;
@property (unsafe_unretained) id<SUAppcastDelegate> delegate;

- (void)fetchAppcastFromURL:(NSURL *)url;
- (NSArray *)items;

@end

@protocol SUAppcastDelegate <NSObject>
- (void)appcastDidFinishLoading:(SUAppcast *)appcast;
- (void)appcast:(SUAppcast *)appcast failedToLoadWithError:(NSError *)error;
@end

#endif
