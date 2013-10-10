//
//  ApplicationStartupHelper.m
//  MAF
//
//  Created by Logan Allred on 5/21/06.
//  Copyright 2006 RedBugz Software. All rights reserved.
//

#import "ApplicationStartupHelper.h"
#import "UKCrashReporter.h"

@implementation ApplicationStartupHelper
- init
{
	if(self =[super init]);
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(applicationDidFinishLaunching:) name:@"NSApplicationDidFinishLaunchingNotification" object:NSApp];
	return self;
}

- (void)applicationDidFinishLaunching:(NSNotification *)note
{
	NSLog(@"ApplicationStartupHelper.applicationDidFinishLaunching");
	UKCrashReporterCheckForCrash();
}

@end
