//
//  ImportSheetController.m
//  MAF
//
//  Created by Logan Allred on 12/31/06.
//  Copyright 2006 RedBugz Software. All rights reserved.
//

#import "ImportSheetController.h"
#import "UKProgressPanelTask.h"
#import "ThreadWorker.h"
#import "PAF21Data.h"
#import "MAFDocument.h"

NSString * const GEDCOM_DOCUMENT_TYPE = @"GEDCOM File (.ged)";
NSString * const PAF21_DOCUMENT_TYPE = @"PAF 2.1/2.3.1 File";
NSString * const MACPAF_DOCUMENT_TYPE = @"MAF File";
NSString * const TEMPLEREADY_UPDATE_DOCUMENT_TYPE = @"TempleReady Update File";

@interface ImportSheetController ()
- (NSString *)typeForContent:(NSData *)data;

@end

@implementation ImportSheetController

// Class API

// begin import process by asking for a file to import, then importing that data
- (void)beginImportProcessNotification:(NSNotification *)notification
{
	NSLog(@"beginImportProcessNotification: %@ : userInfo=%@", notification, [notification userInfo]);
	if (!importSheet) {
		[NSBundle loadNibNamed: @"ImportSheet" owner: self];		
	}
	
//	int result;
    NSArray *fileTypes = nil;//[NSArray arrayWithObject:@"GED"];
    NSOpenPanel *oPanel = [NSOpenPanel openPanel];
	
    [oPanel setAllowsMultipleSelection:NO];
	[oPanel setMessage:@"Please choose a file to import"];
//    result = 
		[oPanel beginSheetForDirectory:nil file:nil types:fileTypes modalForWindow:[[self document] windowForSheet] modalDelegate:self didEndSelector:@selector(openPanelDidEnd:returnCode:contextInfo:) contextInfo:nil];	
}

- (void)openPanelDidEnd:(NSOpenPanel *)panel returnCode:(int)returnCode  contextInfo:(void  *)contextInfo
{
	NSLog(@"ImportSheetController.openPanelDidEnd: %@ : returncode=%d contextinfo=%@", panel, returnCode, contextInfo);
	[panel orderOut:self];
	if (returnCode == NSOKButton) {
		NSLog(@"filename:%@", [panel URL]);
		self.fileNameToImport = [[panel URL] path] ;
        NSArray *filesToOpen = [panel URLs];
        NSUInteger i, count = [filesToOpen count];
        for (NSURL *aURL in filesToOpen) {
			NSLog(@"file:%@", aURL);
        }
		[fileNameText setStringValue:self.fileNameToImport];
		
		self.importDocument = [[MAFDocument new] autorelease];
		UKProgressPanelTask *task = [[UKProgressPanelTask alloc] init];

		NSDictionary *dict = @{@"data": [NSData dataWithContentsOfFile:self.fileNameToImport], @"doc": self.importDocument, @"task": task};
		[NSThread detachNewThreadSelector:@selector(loadDataPreviewInThread:)
			toTarget:self
			withObject:dict];
	}
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
	if (!importSheet) {
		[NSBundle loadNibNamed: @"ImportSheet" owner: self];		
	}

	NSData *data = [[[notification userInfo] valueForKey:@"data"] retain];
	if (!data) {
		NSLog(@"laadDocumentDataNotification received with no data to load for document: %@", [self document]);
		return;
	}
	
	NSLog(@"self window: %@ : windowforsheet=%@", importSheet, [[self document] windowForSheet]);

	ThreadWorker *_tw = [[ThreadWorker workOn:self
                   withSelector:@selector(loadDataInThread:)
                     withObject:[notification userInfo]
                 didEndSelector:@selector(longTaskFinished:)] retain];
	
/*	[NSThread detachNewThreadSelector:@selector(loadDataInThread:)
							 toTarget:self
						   withObject:[notification userInfo]];
*/}

-(void)longTaskFinished:(id)userInfo
{
	NSLog(@"%s userInfo: %@", sel_getName(_cmd), userInfo);
	[NSApp endSheet:progressSheet];
}

/* -----------------------------------------------------------------------------
	showSheetForWindow:
		Displays the given task as a sheet for the given window
-------------------------------------------------------------------------- */
- (void)showSheetForWindow:(NSWindow *)aWindow task:(UKProgressPanelTask *)task
{
	[progressSheet setContentView:[task progressTaskView]];
	
	[NSApp beginSheet: progressSheet
	   modalForWindow: aWindow
		modalDelegate: self
	   didEndSelector: @selector(didEndSheet:returnCode:contextInfo:)
		  contextInfo: nil];
	[progressSheet makeKeyAndOrderFront:nil];
	
    // Sheet is up here.
    // Return processing to the event loop
}

- (void) loadDataInThread:(id)dict
{
	NSAutoreleasePool* myAutoreleasePool = [[NSAutoreleasePool alloc] init];
	
	UKProgressPanelTask* task = [dict valueForKey:@"task"];
	if (!task) {
		task = [[UKProgressPanelTask alloc] init];
	}

	[task setIndeterminate:YES];
	[task setTitle:@"Load Document Data"];
	[task setStatus:@"Preparing to load data..."];
	[task setStopAction:@selector(cancel:)];
	[task setStopDelegate:self];

	[self showSheetForWindow:[[self document] windowForSheet] task:task];
	
	NSDictionary *loaderDict = @{@"delegate": task, @"data": [dict valueForKey:@"data"], @"document": [dict valueForKey:@"doc"], @"controller": self};

	//id newLoader = 
	[[loader class] loadDataForDocumentWithUpdateDelegate:loaderDict];

	[task release];
	
	[myAutoreleasePool release];	
}

- (void) loadDataPreviewInThread:(id)dict
{
	@autoreleasepool {
		@try {
			UKProgressPanelTask* task = [dict valueForKey:@"task"];
			if (!task) {
				task = [[UKProgressPanelTask alloc] init];
			}
			
			[task setIndeterminate:YES];
			[task setTitle:@"Loading Document Preview"];
			[task setStatus:@"Preparing to load data for preview..."];
			[task setStopAction:@selector(cancel:)];
			[task setStopDelegate:self];
			
			[self showSheetForWindow:[[self document] windowForSheet] task:task];
			
			NSData *data = [dict valueForKey:@"data"];
			NSString *fileTypeByExtension = [[NSDocumentController sharedDocumentController] typeFromFileExtension:[self.fileNameToImport pathExtension]];
			NSLog(@"type of file by ext: %@", fileTypeByExtension);
			NSString *fileTypeByContent = [self typeForContent:data];
			NSLog(@"type of file by content: %@", fileTypeByContent);
			NSString *fileType = fileTypeByExtension;
			if (!fileType) {
				fileType = fileTypeByContent;
			}
			if (![fileType isEqualToString:fileTypeByContent]) {
				NSLog(@"%s mismatched file types %@ (extension) and %@ (content)", sel_getName(_cmd), fileTypeByExtension, fileTypeByContent);
			}
			
			if ([fileType isEqualToString:GEDCOM_DOCUMENT_TYPE]) {
				NSLog(@"%s loading preview for document type %@", sel_getName(_cmd), GEDCOM_DOCUMENT_TYPE);
				NSDictionary *loaderDict = @{@"delegate": task, @"data": data, @"document": [dict valueForKey:@"doc"], @"controller": self};
				
				//id newLoader =
				[[loader class] loadDataForDocumentWithUpdateDelegate:loaderDict];
			} else if ([fileType isEqualToString:PAF21_DOCUMENT_TYPE]) {
				NSLog(@"%s loading preview for document type %@", sel_getName(_cmd), PAF21_DOCUMENT_TYPE);
				[PAF21Data importFromData:data intoDocument:self.importDocument];
			} else if ([fileType isEqualToString:MACPAF_DOCUMENT_TYPE]) {
				NSLog(@"%s loading preview for document type %@", sel_getName(_cmd), MACPAF_DOCUMENT_TYPE);
				
			} else if ([fileType isEqualToString:TEMPLEREADY_UPDATE_DOCUMENT_TYPE]) {
				NSLog(@"%s loading preview for document type %@", sel_getName(_cmd), TEMPLEREADY_UPDATE_DOCUMENT_TYPE);
				
			} else {
				// unknown type
				NSLog(@"%s !!! unknown document type for data %@", sel_getName(_cmd), data);
				[task dealloc];
				return;
			}
			
			id doc = [dict valueForKey:@"doc"];
			NSLog(@"%s importDoc:%@ doc:%@ selfdoc:%@ currdoc:%@", sel_getName(_cmd), self.importDocument, doc, [self document], [[NSDocumentController sharedDocumentController] currentDocument]);
			
			[NSApp endSheet:progressSheet];
			
			NSString *string = [NSString stringWithFormat:@"This file contains:\n\t%@ Individuals\t\t%@ Sources\t\t%@ Repositories\n\t%@ Families\t\t%@ Notes\t\t%@ Multimedia\t\t%@ Submitters\n\n%@",
								[self.importDocument valueForKeyPath:@"individualsMap.size"],
								[self.importDocument valueForKeyPath:@"sourcesMap.size"],
								[self.importDocument valueForKeyPath:@"repositoriesMap.size"],
								[self.importDocument valueForKeyPath:@"familiesMap.size"],
								[self.importDocument valueForKeyPath:@"notesMap.size"],
								[self.importDocument valueForKeyPath:@"multimediaMap.size"],
								[self.importDocument valueForKeyPath:@"submittersMap.size"],
								@""];
			[filePreview setString:string];//[NSString stringWithContentsOfFile:fileNameToImport]]];
			
			
			[NSApp beginSheet: importSheet
			   modalForWindow: [[self document] windowForSheet]
				modalDelegate: self
			   didEndSelector: @selector(didEndSheet:returnCode:contextInfo:)
				  contextInfo: nil];
		}
		@catch ( NSException *e ) {
			NSLog(@"%s exception %@", sel_getName(_cmd), e);
			[NSApp endSheet:[[[self document] windowForSheet] attachedSheet]];
		}
		
		@finally {
		}
	}
}

- (void)didEndSheet:(NSWindow *)sheet returnCode:(int)returnCode  contextInfo:(void  *)contextInfo
{
	NSLog(@"ImportSheetController.didEndSheet: %@ : returncode=%d contextinfo=%p", sheet, returnCode, contextInfo);
	
	[sheet orderOut:self];
}

- (void) processTaskDoneNotification:(NSNotification *)notification
{
	NSLog(@"processTaskDoneNotification: %@", notification);
	UKProgressPanelTask *task = [notification object];
	[task stop:[task progressTaskView]];
}

// actions

- (IBAction)import:(id)sender;
{
	NSLog(@"%s", sel_getName(_cmd));
	
/*	NSRect contentFrameInWindowCoordinates = [[self window] contentRectForFrameRect:[[self window] frame]];
	float heightAdjustment = 100;//newHeight - NSHeight(contentFrameInWindowCoordinates);
		contentFrameInWindowCoordinates.origin.y -= heightAdjustment;
		contentFrameInWindowCoordinates.size.height += heightAdjustment;
		[[self window] setFrame:[[self window] frameRectForContentRect:contentFrameInWindowCoordinates] display:[[self window] isVisible] animate:[[self window] isVisible]];
*/

		[NSApp endSheet:importSheet returnCode:0]; // stop the app's modality

		
	//
	// actually import the data, based on the user's settings on the sheet
	//
		@try {
			UKProgressPanelTask *task = [[UKProgressPanelTask alloc] init];
			[task setIndeterminate:YES];
			[task setTitle:@"Importing Document Data"];
			[task setStatus:@"Preparing to import data..."];
			[task setStopAction:@selector(cancel:)];
			[task setStopDelegate:self];

			[self showSheetForWindow:[[self document] windowForSheet] task:task];

			[[self document] startSuppressUpdates];
			[[[self document] valueForKey:@"mafDocument"] importFromDocument:self.importDocument];
			[[self document] endSuppressUpdates];
			[[self document] save];

			[NSApp endSheet:[[self document] windowForSheet] returnCode:0]; // stop the app's modality
			[NSApp endSheet:importSheet returnCode:0]; // stop the app's modality
			[NSApp endSheet:progressSheet returnCode:0]; // stop the app's modality
//			[NSApp endSheet:[[self document] windowForSheet] returnCode:0]; // stop the app's modality
//			NSData *data = [NSData dataWithContentsOfFile:fileNameToImport];//[[filePreview string] dataUsingEncoding:NSUTF8StringEncoding];
//			[[NSNotificationCenter defaultCenter] postNotificationName:@"com.redbugz.maf.ImportDataNotification" object:[self document] userInfo:[NSDictionary dictionaryWithObjectsAndKeys:data, @"data", nil]];
		}
		@catch (NSException * e) {
			NSLog(@"exception loading data: %@", e);
		}
		@finally {
			NSLog(@"%s finally", sel_getName(_cmd));
		}
//	[[self document] loadDataRepresentation:data ofType:[[NSDocumentController sharedDocumentController] typeFromFileExtension:[fileNameToImport pathExtension]]];
}

- (IBAction)cancel:(id)sender;
{
	NSLog(@"cancel:%@ win:%@", sender, [sender window]);
	[NSApp endSheet:[sender window] returnCode:0]; // stop the app's modality
}

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
	self.fileNameToImport = nil;
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

// other private methods

- (NSString *)typeForContent:(NSData *)data
{
	NSLog(@"typeForContent: %@", data);
	if ([[data subdataWithRange:NSMakeRange(0, 6)] isEqualToData:[@"0 HEAD" dataUsingEncoding:NSASCIIStringEncoding]]) {
		NSLog(@"typeForContent: This is a GEDCOM file");
		return GEDCOM_DOCUMENT_TYPE;
	}
	if ([[data subdataWithRange:NSMakeRange(0, 4)] isEqualToData:[@"<xml" dataUsingEncoding:NSASCIIStringEncoding]]) {
		NSLog(@"typeForContent: This is a GEDCOM XML file");
		return GEDCOM_DOCUMENT_TYPE;
	}
	char header[4] = {0x32,0x2E,0x31,0x30};
	if ([[data subdataWithRange:NSMakeRange(0, 4)] isEqualToData:[NSData dataWithBytes:header length:4]]) {
		NSLog(@"typeForContent: This is a PAF file");
		return PAF21_DOCUMENT_TYPE;
	}
	return nil;
}

@end
