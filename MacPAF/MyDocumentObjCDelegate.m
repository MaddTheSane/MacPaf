//
//  MyDocumentObjCDelegate.m
//  MacPAF
//
//  Created by Logan Allred on 12/24/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

#import "MyDocumentObjCDelegate.h"
#import "PAF21Data.h"


@implementation MyDocumentObjCDelegate

-(void)importPAF21Data:(NSData *)data withSheet:(NSWindow *)task
{
	NSLog(@"MyDocumentObjCDelegate:%i, chunks %i", [data length], ([data length]-512)/1024);
	PAF21Data *pafData = [[PAF21Data alloc] initWithData:data];
	[pafData importData];
	
}

+(void)test:(NSData *)data withSheet:(NSWindow *)task
{
	NSLog(@"test MyDocumentObjCDelegate:%i, chunks %i", [data length], ([data length]-512)/1024);
}
@end
