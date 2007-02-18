//
//  ImportSheetController.m
//  MacPAF
//
//  Created by Logan Allred on 12/31/06.
//  Copyright 2006 RedBugz Software. All rights reserved.
//

#import "ImportSheetController.h"
#import <unistd.h>
#import "UKProgressPanelTask.h"

@implementation ImportSheetController

// Class API

// begin import process by asking for a file to import, then importing that data
- (void)beginImportProcessNotification:(NSNotification *)notification
{
	NSLog(@"beginImportProcessNotification: %@ : userInfo=%@", notification, [notification userInfo]);
	if (!importSheet) {
		[NSBundle loadNibNamed: @"ImportSheet" owner: self];		
	}
	
	int result;
    NSArray *fileTypes = nil;//[NSArray arrayWithObject:@"td"];
    NSOpenPanel *oPanel = [NSOpenPanel openPanel];
	
    [oPanel setAllowsMultipleSelection:NO];
	[oPanel setMessage:@"Please choose a file to import"];
//    result = 
		[oPanel beginSheetForDirectory:nil file:nil types:nil modalForWindow:[[self document] windowForSheet] modalDelegate:self didEndSelector:@selector(openPanelDidEnd:returnCode:contextInfo:) contextInfo:nil];
	
}

- (void)openPanelDidEnd:(NSOpenPanel *)panel returnCode:(int)returnCode  contextInfo:(void  *)contextInfo
{
	NSLog(@"ImportSheetController.openPanelDidEnd: %@ : returncode=%d contextinfo=%@", panel, returnCode, contextInfo);
	if (returnCode == NSOKButton) {
		NSLog(@"filename:%@", [panel filename]);
		fileNameToImport = [[panel filename] retain];
        NSArray *filesToOpen = [panel filenames];
        int i, count = [filesToOpen count];
        for (i=0; i<count; i++) {
            NSString *aFile = [filesToOpen objectAtIndex:i];
			NSLog(@"file:%@", aFile);
        }
    }
	[panel orderOut:self];
	NSLog(@"doc:%@", NSClassFromString(@"com.redbugz.macpaf.jdom.MacPAFDocumentJDOM"));
	id doc = [[NSClassFromString(@"com.redbugz.macpaf.jdom.MacPAFDocumentJDOM") alloc] init];
	[filePreview setString:[NSString stringWithContentsOfFile:[panel filename]]];
	NSDictionary *dict = [NSDictionary dictionaryWithObjectsAndKeys: [NSData dataWithContentsOfFile:[panel filename]], @"data", doc, @"doc", nil];
	[NSThread detachNewThreadSelector:@selector(loadDataInThread:)
							 toTarget:self
						   withObject:dict];

	return;
	[NSApp beginSheet: importSheet
	   modalForWindow: [[self document] windowForSheet]
		modalDelegate: self
	   didEndSelector: @selector(didEndSheet:returnCode:contextInfo:)
		  contextInfo: dict];
	//[importSheet makeKeyAndOrderFront:nil];
//	sleep(10);
//	[NSApp endSheet:importSheet];
}

- (void)didEndSheet:(NSWindow *)sheet returnCode:(int)returnCode  contextInfo:(void  *)contextInfo
{
	NSLog(@"ImportSheetController.didEndSheet: %@ : returncode=%d contextinfo=%@", sheet, returnCode, contextInfo);
	
	[sheet orderOut:self];
}

	// import data that has already been loaded into a new or existing document
- (void)importDataNotification:(NSNotification *)notification
{
	NSLog(@"importDataNotification: %@ : userInfo=%@", notification, [notification userInfo]);
	// for now, treat just like the loadDocumentDataNotification
	[self loadDocumentDataNotification:notification];
//	NSDictionary *loaderDict = [NSDictionary dictionaryWithObjectsAndKeys:[[notification userInfo] valueForKey:@"data"], @"data", [[self document] valueForKey:@"doc"], @"doc", nil];
//	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(processTaskDoneNotification:) name:NSThreadWillExitNotification object:nil];
//	[NSThread detachNewThreadSelector:@selector(loadDataInThread:)
//							 toTarget:self
//						   withObject:loaderDict];
}

- (void)loadDocumentDataNotification:(NSNotification *)notification
{
	NSLog(@"loadDocumentDataNotification: %@ : userInfo=%@", notification, [notification userInfo]);

	NSData *data = [[[notification userInfo] valueForKey:@"data"] retain];
	if (!data) {
		NSLog(@"laadDocumentDataNotification received with no data to load for document: %@", [self document]);
		return;
	}
	
	NSLog(@"self window: %@ : windowforsheet=%@", importSheet, [[self document] windowForSheet]);
//	NSDictionary *dict = [NSDictionary dictionaryWithObjectsAndKeys:self, @"delegate", data, @"data", [[notification userInfo] valueForKey:@"doc"], @"document", nil];
	//loader = [NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") retain];
//	loader = [[NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") loadDataForDocumentWithUpdateDelegate:dict] retain];

//	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(processUpdateNotification:) name:@"com.redbugz.macpaf.UpdateProgressNotification" object:loader];
//	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(processTaskDoneNotification:) name:NSThreadWillExitNotification object:nil];
	[NSThread detachNewThreadSelector:@selector(loadDataInThread:)
							 toTarget:self
						   withObject:[notification userInfo]];
	NSLog(@"done");
}

- (void) loadDataInThread:(id)dict {
	NSAutoreleasePool* myAutoreleasePool = [[NSAutoreleasePool alloc] init];
	//document = [[dict valueForKey:@"document"] retain];
	//data = [dict valueForKey:@"data"];
	int							x, xmax = 10 + (rand() % 20 +1);
	UKProgressPanelTask* 
	task = [UKProgressPanelTask newProgressSheetTask];
	NSLog(@"task rc:%d",[task retainCount] );
	
	[task setMaxValue: xmax];	// Set the maximum value of the scroll bar.
	[task setTitle: @"Inventing my own programming language"];
	[task setStatus: @"Not much to do here."];
	
	[task showSheetForWindow:[[self document] windowForSheet]];
	
	for( x = 0; x <= xmax && ![task stopped]; x++ )
	{
		[task setDoubleValue: x];	// Change the value of the progress bar to indicate our progress.
		sleep(1);
	}
	
	/*
	 
	 if (!loader) {
		 loader = [[NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") alloc] init];
	 }
	 //	NSLog(@"static loader:%@",[NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") testStatic:notification] );
	 //	NSLog(@" loader:%@",[loader test:notification] );
	 //NSLog(@"loaded loader:%@",[loader test] );
	 //	[NSThread detachNewThreadSelector:@selector(doIt:)//loadDataForDocumentWithUpdateDelegate)
	 //							 toTarget:self
	 //						   withObject:notification];
	 */

	//UKProgressPanelTask *task = [[UKProgressPanelTask newProgressSheetTask] retain];
	[task setIndeterminate:YES];
	[task setTitle:@"Load Document Data"];
	[task setStatus:@"Preparing to load data..."];
	[task setStopAction:@selector(cancel:)];
	[task setStopDelegate:self];
	//	[taskProgressView addSubview:[task progressTaskView]];
	//	[taskProgressView setNeedsDisplay:YES];
//	[task showSheetForWindow:[document windowForSheet]];
	
//	if (!loader) {
//		loader = [[NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") alloc] init];
//	}
	//	NSLog(@"static loader:%@",[NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") testStatic:notification] );
	//	NSLog(@" loader:%@",[loader test:notification] );
	//NSLog(@"loaded loader:%@",[loader test] );
	//	[NSThread detachNewThreadSelector:@selector(doIt:)//loadDataForDocumentWithUpdateDelegate)
	//							 toTarget:self
	//						   withObject:notification];
	NSDictionary *loaderDict = [NSDictionary dictionaryWithObjectsAndKeys:task, @"delegate", [dict valueForKey:@"data"], @"data", [dict valueForKey:@"doc"], @"document", self, @"controller", nil];
	//loader = [NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") retain];
	//	loader = [[NSClassFromString(@"com.redbugz.macpaf.jdom.GedcomLoaderJDOM") loadDataForDocumentWithUpdateDelegate:dict] retain];
	id newLoader = [[loader class] loadDataForDocumentWithUpdateDelegate:loaderDict];
//	[task stop:[task progressTaskView]];
	//[newLoader release];
//	[loaderDict release];
	NSLog(@"task rc:%d",[task retainCount] );
	[task release];
	NSLog(@"task rc:%d",[task retainCount] );
	[myAutoreleasePool release];	
}

- (void) processTaskDoneNotification:(NSNotification *)notification
{
	NSLog(@"processTaskDoneNotification: %@", notification);
	UKProgressPanelTask *task = [notification object];
	[task stop:[task progressTaskView]];
}

//- (void) updateProgressNotification:(NSNotification *)notification
//{
//	NSLog(@"updateProgressNotification: %@", notification);
//	[self performSelectorOnMainThread:@selector(update:)
//						   withObject:notification
//						waitUntilDone:false];
//	/*
//	[task setMaxValue:[[[notification userInfo] valueForKey:@"maxValue"] doubleValue]];
//	[task setDoubleValue:[[[notification userInfo] valueForKey:@"currentValue"] doubleValue]];
//	[task setStatus:[[notification userInfo] valueForKey:@"status"]];
//	[task animate:self];
//	 */
//}

//- (void) update:(NSNotification *)notification
//{
//	NSLog(@"update: %@ task:%@", notification, task);
//	[task setIndeterminate:NO];
//	[task setMaxValue:[[[notification userInfo] valueForKey:@"maxValue"] doubleValue]];
//	[task setDoubleValue:[[[notification userInfo] valueForKey:@"currentValue"] doubleValue]];
//	[task setStatus:[[notification userInfo] valueForKey:@"status"]];
//	[task animate:self];
//}

//- (void)importFileIntoDocumentNotification:(NSNotification *)notification
//{
//	NSLog(@"importFileIntoDocumentNotification: %@", notification);
//	NSDocument *aDocument = [notification object];
//	ImportSheetController *importSheetController = [[self alloc] _initWithDocument:aDocument];
//	
//	[NSApp beginSheet:[importSheetController window]
//	   modalForWindow:[aDocument windowForSheet] modalDelegate:importSheetController
//	   didEndSelector:@selector(_sheetDidEnd:returnCode:contextInfo:) contextInfo:nil];
//	[[importSheetController window] makeKeyAndOrderFront:nil]; // redundant but cleaner
//}

// actions

- (IBAction)import:(id)sender;
{
	NSLog(@"import");
	NSLog(@"%d", [self respondsToSelector:@selector(openPanelDidEnd:returnCode:contextInfo:)]);
    
	[NSApp endSheet:importSheet returnCode:0]; // stop the app's modality
	
	//
	// actually import the data, based on the user's settings on the sheet
	//
	NSData *data = [[filePreview string] dataUsingEncoding:NSUTF8StringEncoding];
	[[NSNotificationCenter defaultCenter] postNotificationName:@"com.redbugz.macpaf.ImportDataNotification" object:[self document] userInfo:[NSDictionary dictionaryWithObjectsAndKeys:data, @"data", nil]];
//	[[self document] loadDataRepresentation:data ofType:[[NSDocumentController sharedDocumentController] typeFromFileExtension:[fileNameToImport pathExtension]]];
}

- (IBAction)cancel:(id)sender;
{
	NSLog(@"cancel:%@", sender);
	[NSApp endSheet:[sender window] returnCode:0]; // stop the app's modality
}

//- (void)openPanelDidEnd:(NSOpenPanel *)panel returnCode:(int)returnCode {
//	NSLog(@"openpaneldidend");
//}
@end

@implementation ImportSheetController (Private)

// Init and dealloc

- (id)_initWithDocument:(NSDocument *)aDocument;
{
	if (![self initWithWindowNibName:@"ImportSheet"])
		return nil;
    
	[self setDocument:[aDocument retain]];
	return self;
}

- (void)dealloc;
{
	[fileNameToImport release];
	[super dealloc];
}

// private callbacks

- (void)_sheetDidEnd:(NSWindow *)sheetWindow returnCode:(int)returnCode
		 contextInfo:(void  *)contextInfo; // [NSApp beginSheet:...]
{
	NSLog(@"_sheetDidEnd");
	[sheetWindow orderOut:nil]; // hide sheet
//	[self autorelease];
}

@end
