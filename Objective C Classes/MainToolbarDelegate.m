//
//  MainToolbarDelegate.m
//  MAF
//
//  Created by C.W. Betts on 10/13/13.
//
//

#import "MainToolbarDelegate.h"

@interface MainToolbarDelegate ()
@property (strong) NSDictionary *toolbarItems;
@property (strong) NSArray *defaultItemIdentifiers;
+ (NSImage*)newBadgedImage:(NSString*)baseName badge:(NSString*)badgeName;
+ (NSToolbarItem*)newToolbarItem:(NSString*)identifier label:(NSString*)label imageName:(NSString*)imageName action:(SEL)selector;
+ (NSToolbarItem*)newToolbarItem:(NSString*)identifier label:(NSString*)label image:(NSImage*)image action:(SEL)selector;
@end

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

@implementation MainToolbarDelegate

+ (NSImage *)newBadgedImage:(NSString*)baseName badge:(NSString*)badgeName
{
	NSImage *newImage = [[NSImage alloc] initWithSize:NSMakeSize(48, 48)];
	@try {
		NSImage *badge = [NSImage imageNamed:badgeName];
		NSImage *iconImage = [NSImage imageNamed:baseName];
		[newImage lockFocus];
		[iconImage compositeToPoint:NSZeroPoint operation:NSCompositeSourceOver];
		[badge compositeToPoint:NSMakePoint(0, 16) operation:NSCompositeSourceOver];
		[newImage unlockFocus];
	}
	@catch (NSException *exception) {
		NSLog(@"%s: %@", __PRETTY_FUNCTION__, exception);
		newImage = nil;
	}
	@finally {
		return newImage;
	}
}

+ (NSToolbarItem*)newToolbarItem:(NSString*)identifier label:(NSString*)label image:(NSImage*)image action:(SEL)selector
{
	NSToolbarItem *toolbarItem = [[NSToolbarItem alloc] initWithItemIdentifier:identifier];
	toolbarItem.label = label;
	toolbarItem.image = image;
	toolbarItem.action = selector;
	toolbarItem.target = nil;
	
	return toolbarItem;
}

+ (NSToolbarItem*)newToolbarItem:(NSString*)identifier label:(NSString*)label imageName:(NSString*)imageName action:(SEL)selector
{
	return [self newToolbarItem:identifier label:label image:[NSImage imageNamed:imageName] action:selector];
}

- (id)init
{
	if (self = [super init]) {
		self.delegate = self;
		self.allowsUserCustomization = NO;
		self.autosavesConfiguration = YES;
		self.defaultItemIdentifiers = @[ADD_INDIVIDUAL_IDENTIFIER,
										ADD_FAMILY_IDENTIFIER,
										EDIT_NOTES_IDENTIFIER,
										INDIVIDUAL_LIST_IDENTIFIER,
										FAMILY_LIST_IDENTIFIER,
										NSToolbarFlexibleSpaceItemIdentifier,
										NSToolbarPrintItemIdentifier];
		self.toolbarItems = @{NSToolbarSpaceItemIdentifier : [[NSToolbarItem alloc] initWithItemIdentifier:NSToolbarSpaceItemIdentifier],
							  NSToolbarFlexibleSpaceItemIdentifier : [[NSToolbarItem alloc] initWithItemIdentifier:NSToolbarFlexibleSpaceItemIdentifier],
							  NSToolbarSeparatorItemIdentifier : [[NSToolbarItem alloc] initWithItemIdentifier:NSToolbarSeparatorItemIdentifier],
							  
							  ADD_FAMILY_IDENTIFIER : [MainToolbarDelegate newToolbarItem:ADD_FAMILY_IDENTIFIER label:@"Add Family" image:[MainToolbarDelegate newBadgedImage:@"Family" badge:@"Badge Add"] action:@selector(addNewFamily:)],
							  ADD_INDIVIDUAL_IDENTIFIER : [MainToolbarDelegate newToolbarItem:ADD_INDIVIDUAL_IDENTIFIER label:@"Add Individual" image:[MainToolbarDelegate newBadgedImage:@"Individual" badge:@"Badge Add"] action:@selector(addNewIndividual:)],
							  VIEW_PEDIGREE_IDENTIFIER : [MainToolbarDelegate newToolbarItem:VIEW_PEDIGREE_IDENTIFIER label:@"View Pedigree" imageName:@"Pedigree" action:@selector(viewPedigree:)],
							  FIND_FAMILY_IDENTIFIER : [MainToolbarDelegate newToolbarItem:FIND_FAMILY_IDENTIFIER label:@"Find Family" imageName:@"Magnifying Glass" action:@selector(findFamily:)],
							  FIND_INDIVIDUAL_IDENTIFIER : [MainToolbarDelegate newToolbarItem:FIND_INDIVIDUAL_IDENTIFIER label:@"Find Individual" imageName:@"Magnifying Glass" action:@selector(findIndividual:)],
							  INDIVIDUAL_LIST_IDENTIFIER : [MainToolbarDelegate newToolbarItem:INDIVIDUAL_LIST_IDENTIFIER label:@"Individual List" imageName:@"Metal Window" action:@selector(showIndividualList:)],
							  FAMILY_LIST_IDENTIFIER : [MainToolbarDelegate newToolbarItem:FAMILY_LIST_IDENTIFIER label:@"Family List" imageName:@"Metal Window" action:@selector(showFamilyList:)],
							  EDIT_NOTES_IDENTIFIER : [MainToolbarDelegate newToolbarItem:EDIT_NOTES_IDENTIFIER label:@"Edit Notes" imageName:@"notepad" action:@selector(editNotes:)],
							  NSToolbarPrintItemIdentifier : [[NSToolbarItem alloc] initWithItemIdentifier:NSToolbarPrintItemIdentifier],
							  };
	}
	return self;
}

- (id)initWithIdentifier:(NSString *)identifier
{
	return self = [self init];
}

- (NSToolbarItem*)toolbar:(NSToolbar *)toolbar itemForItemIdentifier:(NSString *)itemIdentifier willBeInsertedIntoToolbar:(BOOL)flag
{
	NSAssert(toolbar == self, @"We should only be handling ourself");
	return self.toolbarItems[itemIdentifier];
}

- (NSArray*)toolbarDefaultItemIdentifiers:(NSToolbar *)toolbar
{
	NSAssert(toolbar == self, @"We should only be handling ourself");
	return self.defaultItemIdentifiers;
}

- (NSArray*)toolbarAllowedItemIdentifiers:(NSToolbar *)toolbar
{
	NSAssert(toolbar == self, @"We should only be handling ourself");
	return [self.toolbarItems allKeys];
}

- (NSArray*)toolbarSelectableItemIdentifiers:(NSToolbar *)toolbar
{
	NSAssert(toolbar == self, @"We should only be handling ourself");
	return @[];
}

@end
