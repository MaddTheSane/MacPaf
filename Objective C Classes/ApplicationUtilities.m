//
//  ApplicationUtilities.m
//  MAF
//
//  Created by C.W. Betts on 10/24/13.
//
//

#import "ApplicationUtilities.h"

@implementation ApplicationUtilities

- (id)init
{
	if (self = [super init]) {
		self.didFinish = NO;
	}
	
	return self;
}

- (BOOL)applicationShouldOpenUntitledFile:(NSApplication *)sender
{
	return NO;
}

- (void)applicationDidFinishLaunching:(NSNotification *)notification
{
	[self.splashScreen close];
	
	self.didFinish = YES;
}

@end
