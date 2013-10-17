//
//  ImportSheetController.h
//  MAF
//
//  Created by Logan Allred on 12/31/06.
//  Copyright 2006 RedBugz Software. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@protocol MAFJDOM <NSObject>

- (void)loadDataForDocumentWithUpdateDelegate:(NSDictionary*)theDict;

@end

@class UKProgressPanelTask;	
@class MAFDocument;

@interface ImportSheetController : NSWindowController {
	IBOutlet NSWindow *importSheet;
	IBOutlet NSWindow *progressSheet;
	IBOutlet id<MAFJDOM> loader; // GedcomLoaderJDOM
	IBOutlet NSTextView *filePreview;
	IBOutlet NSTextField *fileNameText;
//	IBOutlet NSView *progressView;
	IBOutlet NSButton *importButton;
	
//	UKProgressPanelTask *task;
//	NSData *data;
}

@property (retain) NSString *fileNameToImport;
@property (retain) MAFDocument *importDocument;


// Notifications to be sent by Java code

// begin import process by asking for a file to import, then importing that data
- (void)beginImportProcessNotification:(NSNotification *)notification;

// import data that has already been loaded into a new or existing document
- (void)importDataNotification:(NSNotification *)notification;

// show import progress while we load existing data
- (void)loadDocumentDataNotification:(NSNotification *)notification;

// notifications
//- (void)updateProgressNotification:(NSNotification *)notification;

// actions
- (IBAction)import:(id)sender;
- (IBAction)cancel:(id)sender;

//- (void)openPanelDidEnd:(NSOpenPanel *)panel returnCode:(int)returnCode;
@end
