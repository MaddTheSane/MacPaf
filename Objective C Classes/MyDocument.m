//
//  MyDocument.m
//  MAF
//
//  Created by C.W. Betts on 10/13/13.
//
//

#import "MyDocument.h"
#import "MAFDocument.h"
#import "MAFPerson.h"
#import "MAFFamily.h"
#import "MAFLocation.h"
#import "IndividualListController.h"
#import "MAF-Swift.h"

#define ADD_FAMILY_IDENTIFIER @"AddFamily"
#define ADD_INDIVIDUAL_IDENTIFIER @"AddIndividual"
#define VIEW_PEDIGREE_IDENTIFIER @"ViewPedigree"
#define FIND_INDIVIDUAL_IDENTIFIER @"FindIndividual"
#define FIND_FAMILY_IDENTIFIER @"FindFamily"
#define PRINT_REPORTS_IDENTIFIER @"PrintReports"
#define IMPORT_GEDCOM_IDENTIFIER @"ImportGEDCOM"
#define EDIT_NOTES_IDENTIFIER @"EditNotes"
#define MULTIMEDIA_CENTER_IDENTIFIER @"MultimediaCenter"
#define SOUCE_CENTER_IDENTIFIER @"SourceCenter"
#define REPOSITORY_CENTER_IDENTIFIER @"RepositoryCenter"
#define LOCATION_CENTER_IDENTIFIER @"LocationCenter"
#define INDIVIDUAL_LIST_IDENTIFIER @"IndividualList"
#define FAMILY_LIST_IDENTIFIER @"FamilyList"

@implementation MyDocument
{
	NSMutableDictionary *toolbarItemCache;
}


- (MAFDocument*)mafDocument
{
	
}

- (void)save
{
	[self saveDocument:nil];
}

- (void)startSuppressUpdates
{
	
}

- (void)endSuppressUpdates
{
	
}

- (id)init
{
    self = [super init];
    if (self) {
		//self.mafDocument = [[MAFDocument alloc] init];
        // Add your subclass-specific initialization here.
    }
    return self;
}

- (NSString *)windowNibName
{
    // Override returning the nib file name of the document
    // If you need to use a subclass of NSWindowController or if your document supports multiple NSWindowControllers, you should remove this method and override -makeWindowControllers instead.
    return @"MyDocument";
}

- (void)windowControllerDidLoadNib:(NSWindowController *)aController
{
    [super windowControllerDidLoadNib:aController];
    // Add any code here that needs to be executed once the windowController has loaded the document's window.
	
}

+ (NSToolbarItem*)newToolbarItem:(NSString*)identifier label:(NSString*)label imageName:(NSString*)imageName action:(SEL)selector
{
	return [self newToolbarItem:identifier label:label image:[NSImage imageNamed:imageName] action:selector];
}

+ (NSToolbarItem*)newToolbarItem:(NSString*)identifier label:(NSString *)label image:(NSImage *)imageName action:(SEL)selector
{
	NSToolbarItem *item = [[NSToolbarItem alloc] initWithItemIdentifier:identifier];
	item.label = label;
	item.image = imageName;
	item.action = selector;
	
	return item;
}

+ (NSImage*)badgeImageNamed:(NSString*)imageName badgeName:(NSString*)badgeName
{
	NSImage *badge = [NSImage imageNamed:badgeName];
	NSImage *iconImage = [NSImage imageNamed:imageName];
	NSImage *newImage = [[NSImage alloc] initWithSize:NSMakeSize(48, 48)];
	[newImage lockFocus];
	[iconImage drawAtPoint:NSZeroPoint fromRect:NSZeroRect operation:NSCompositeSourceOver fraction:1.0];
	[badge drawAtPoint:NSMakePoint(0, 16) fromRect:NSZeroRect operation:NSCompositeSourceOver fraction:1.0];
	return newImage;
}

/*
 private NSToolbarItem createToolbarItem(String identifier, String label, String imageName, NSSelector action) {
	return createToolbarItem(identifier, label, NSImage.imageNamed(imageName), action);
 }
 
private NSToolbarItem createToolbarItem(String identifier, String label, NSImage image, NSSelector action) {
	NSToolbarItem toolbarItem = new NSToolbarItem(identifier);
	toolbarItem.setLabel(label);
	toolbarItem.setImage(image);
	toolbarItem.setAction(action);
	return toolbarItem;
}

private NSImage createBadgedImage(String imageName, String badgeName) {
	NSImage newImage = new NSImage(new NSSize(48,48));
	try {
		NSImage badge = NSImage.imageNamed(badgeName);
		NSImage iconImage = NSImage.imageNamed(imageName);
		newImage.lockFocus();
		iconImage.compositeToPoint(new NSPoint(0,0), NSImage.CompositeSourceOver);
		badge.compositeToPoint(new NSPoint(0,16), NSImage.CompositeSourceOver);
		//	 NSAttributedString T = CocoaCommon.makeAttributedString("TEXT","small-bold-white",20f);
		//	 NSGraphics.drawAttributedString(T, new NSPoint(95,84));
		
		newImage.unlockFocus();
	} catch (RuntimeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//	 NSApplication.sharedApplication().setApplicationIconImage(NewImage);
	return newImage;
}

public NSToolbarItem toolbarItemForItemIdentifier(NSToolbar nSToolbar, String string, boolean boolean2) {
	return (NSToolbarItem) toolbarItems.objectForKey(string);
}
*/

- (NSData *)dataOfType:(NSString *)typeName error:(NSError **)outError
{
    // Insert code here to write your document to data of the specified type. If outError != NULL, ensure that you create and set an appropriate error when returning nil.
    // You can also choose to override -fileWrapperOfType:error:, -writeToURL:ofType:error:, or -writeToURL:ofType:forSaveOperation:originalContentsURL:error: instead.
    if (outError) {
        *outError = [NSError errorWithDomain:NSOSStatusErrorDomain code:unimpErr userInfo:nil];
    }
    return nil;
}

- (BOOL)readFromData:(NSData *)data ofType:(NSString *)typeName error:(NSError **)outError
{
    // Insert code here to read your document from the given data of the specified type. If outError != NULL, ensure that you create and set an appropriate error when returning NO.
    // You can also choose to override -readFromFileWrapper:ofType:error: or -readFromURL:ofType:error: instead.
    // If you override either of these, you should also override -isEntireFileLoaded to return NO if the contents are lazily loaded.
    if (outError) {
        *outError = [NSError errorWithDomain:NSOSStatusErrorDomain code:unimpErr userInfo:nil];
    }
    return YES;
}

#if 0
- (void)makeWindowControllers
{
	[super makeWindowControllers];
	[self addWindowController:[[IndividualListController alloc] initWithWindowNibName:@"IndividualListWindow"]];
}
#endif

+ (BOOL)autosavesInPlace
{
    return YES;
}

- (IBAction)openFamilyEditSheet:(id)sender
{
	
}

- (IBAction)openIndividualEditSheet:(id)sender
{
	
}

- (IBAction)printDocument:(id)sender
{
	[self openReportsSheet:sender];
}

- (IBAction)deletePrimaryIndividual:(id)sender
{
	
}

- (IBAction)deletePrimaryFamily:(id)sender
{
	
}

- (IBAction)editPrimaryIndividual:(id)sender
{
	
}

- (IBAction)editCurrentFamily:(id)sender
{
	
}

- (IBAction)openReportsSheet:(id)sender
{
	
}

- (IBAction)selectPrintableView:(id)sender
{
	
}

- (IBAction)addNewFamily:(id)sender
{
	
}

- (IBAction)addNewIndividual:(id)sender
{
	
}

- (IBAction)viewPedigree:(id)sender
{
	
}

- (IBAction)findFamily:(id)sender
{
	
}

- (IBAction)findIndividual:(id)sender
{
	
}

- (IBAction)showIndividualList:(id)sender
{
	
}

- (IBAction)showFamilyList:(id)sender
{
	
}

- (IBAction)editNotes:(id)sender
{
	
}

- (IBAction)setIndividual:(id)sender
{
	
}

- (IBAction)transmitBugReport:(id)sender
{
	
}

- (NSToolbarItem *)toolbar:(NSToolbar *)toolbar itemForItemIdentifier:(NSString *)itemIdentifier willBeInsertedIntoToolbar:(BOOL)flag
{
	if(!toolbarItemCache) {
		toolbarItemCache = [[NSMutableDictionary alloc] initWithCapacity: 5];
	}
	NSToolbarItem *item = toolbarItemCache[itemIdentifier];

	if (!item) {
		if ([itemIdentifier isEqualToString:ADD_FAMILY_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:ADD_FAMILY_IDENTIFIER label:@"Add Family" image:[MyDocument badgeImageNamed:@"Family" badgeName:@"Badge Add"] action:@selector(addNewFamily:)];
			item.target = self;
			toolbarItemCache[ADD_FAMILY_IDENTIFIER] = item;
		} else if ([itemIdentifier isEqualToString:ADD_INDIVIDUAL_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:ADD_INDIVIDUAL_IDENTIFIER label:@"Add Individual" image:[MyDocument badgeImageNamed:@"Family" badgeName:@"Individual"] action:@selector(addNewIndividual:)];
			item.target = self;
			toolbarItemCache[ADD_INDIVIDUAL_IDENTIFIER] = item;
		} else if ([itemIdentifier isEqualToString:VIEW_PEDIGREE_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:VIEW_PEDIGREE_IDENTIFIER label:@"View Pedigree" imageName:@"Pedigree" action:@selector(viewPedigree:)];
			item.target = self;
			toolbarItemCache[VIEW_PEDIGREE_IDENTIFIER] = item;
		} else if ([itemIdentifier isEqualToString:FIND_FAMILY_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:FIND_FAMILY_IDENTIFIER label:@"Find Family" imageName:@"Magnifying Glass" action:@selector(findFamily:)];
			item.target = self;
			toolbarItemCache[FIND_FAMILY_IDENTIFIER] = item;
		} else if ([itemIdentifier isEqualToString:FIND_INDIVIDUAL_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:FIND_INDIVIDUAL_IDENTIFIER label:@"Find Individual" imageName:@"Magnifying Glass" action:@selector(findIndividual:)];
			item.target = self;
			toolbarItemCache[FIND_INDIVIDUAL_IDENTIFIER] = item;
		} else if ([itemIdentifier isEqualToString:INDIVIDUAL_LIST_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:INDIVIDUAL_LIST_IDENTIFIER label:@"Individual List" imageName:@"Metal Window" action:@selector(showIndividualList:)];
			item.target = self;
			toolbarItemCache[INDIVIDUAL_LIST_IDENTIFIER] = item;
		} else if ([itemIdentifier isEqualToString:FAMILY_LIST_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:FAMILY_LIST_IDENTIFIER label:@"Family List" imageName:@"Metal Window" action:@selector(showFamilyList:)];
			item.target = self;
			toolbarItemCache[FAMILY_LIST_IDENTIFIER] = item;
		} else if ([itemIdentifier isEqualToString:EDIT_NOTES_IDENTIFIER]) {
			item = [MyDocument newToolbarItem:EDIT_NOTES_IDENTIFIER label:@"Edit Notes" imageName:@"notepad" action:@selector(editNotes:)];
			item.target = self;
			toolbarItemCache[EDIT_NOTES_IDENTIFIER] = item;
		}
	}
	return item;
}

- (NSArray *)toolbarDefaultItemIdentifiers:(NSToolbar *)toolbar
{
	return @[ADD_INDIVIDUAL_IDENTIFIER,
			 ADD_FAMILY_IDENTIFIER,
			 EDIT_NOTES_IDENTIFIER,
			 INDIVIDUAL_LIST_IDENTIFIER,
			 FAMILY_LIST_IDENTIFIER,
			 NSToolbarFlexibleSpaceItemIdentifier,
			 NSToolbarPrintItemIdentifier];
	
}

- (NSArray *)toolbarAllowedItemIdentifiers:(NSToolbar *)toolbar
{
	return @[ADD_FAMILY_IDENTIFIER,
			 ADD_INDIVIDUAL_IDENTIFIER,
			 VIEW_PEDIGREE_IDENTIFIER,
			 FIND_INDIVIDUAL_IDENTIFIER,
			 FIND_FAMILY_IDENTIFIER,
			 PRINT_REPORTS_IDENTIFIER,
			 IMPORT_GEDCOM_IDENTIFIER,
			 EDIT_NOTES_IDENTIFIER,
			 MULTIMEDIA_CENTER_IDENTIFIER,
			 SOUCE_CENTER_IDENTIFIER,
			 REPOSITORY_CENTER_IDENTIFIER,
			 LOCATION_CENTER_IDENTIFIER,
			 INDIVIDUAL_LIST_IDENTIFIER,
			 FAMILY_LIST_IDENTIFIER,
			 NSToolbarFlexibleSpaceItemIdentifier,
			 NSToolbarPrintItemIdentifier];
}


@end
