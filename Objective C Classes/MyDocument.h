//
//  MyDocument.h
//  MAF
//
//  Created by C.W. Betts on 10/13/13.
//
//

#import <Cocoa/Cocoa.h>
#import "FamilyListController.h"
#import "IndividualListController.h"

@class MAFFamilyList;
@class MAFIndividualList;
@class SurnameList;
@class LocationList;
@class MAFHistoryController;
@class MAFPedigreeViewController;
@class MAFPedigreeView;
@class MAFDocument;

@interface MyDocument : NSPersistentDocument <NSToolbarDelegate>
@property (weak) IBOutlet NSTableView *childrenTable;
@property (weak) IBOutlet NSWindow *mainWindow;  
@property (weak) IBOutlet NSWindow *reportsWindow;  
@property (weak) IBOutlet NSWindow *individualEditWindow;  
@property (weak) IBOutlet NSWindow *familyEditWindow;  
@property (weak) IBOutlet NSWindow *taskProgressSheetWindow;  
@property (weak) IBOutlet FamilyListController *familyListWindowController;  
@property (weak) IBOutlet IndividualListController *individualListWindowController;  
@property (weak) IBOutlet FamilyListController *tabFamilyListController;  
@property (weak) IBOutlet IndividualListController *tabIndividualListController;  
@property (weak) IBOutlet NSObject *importController;  
@property (weak) IBOutlet MAFFamilyList *familyList;  
@property (weak) IBOutlet MAFIndividualList *individualList;  
@property (weak) IBOutlet SurnameList *surnameList;  
@property (weak) IBOutlet LocationList *locationList;  
@property (weak) IBOutlet MAFHistoryController *historyController;  
@property (weak) IBOutlet MAFPedigreeViewController *pedigreeViewController;  
@property (weak) IBOutlet NSButton *fatherButton;  
@property (weak) IBOutlet NSButton *individualButton;  
@property (weak) IBOutlet NSButton *individualFamilyButton;  
@property (weak) IBOutlet NSButton *maternalGrandfatherButton;  
@property (weak) IBOutlet NSButton *maternalGrandmotherButton;  
@property (weak) IBOutlet NSButton *motherButton;  
@property (weak) IBOutlet NSButton *paternalGrandfatherButton;  
@property (weak) IBOutlet NSButton *paternalGrandmotherButton;  
@property (weak) IBOutlet NSButton *spouseButton;  
@property (weak) IBOutlet NSButton *familyAsSpouseButton;  
@property (weak) IBOutlet NSButton *familyAsChildButton;  
@property (weak) IBOutlet NSWindow *noteWindow;  
@property (weak) IBOutlet NSMatrix *reportsRadio;  
@property (weak) IBOutlet NSTableView *spouseTable;  
@property (weak) IBOutlet MAFPedigreeView *pedigreeView;  
@property (weak) IBOutlet NSTabView *mainTabView;
@property (weak) IBOutlet NSWindow *bugReportWindow;
@property (weak) IBOutlet NSButton *bugReportFileCheckbox;
@property (unsafe_unretained) IBOutlet NSTextView *bugReportText;

@property (unsafe_unretained, readonly) MAFDocument *mafDocument;

- (IBAction)openFamilyEditSheet:(id)sender;
- (IBAction)openIndividualEditSheet:(id)sender;


- (IBAction)deletePrimaryIndividual:(id)sender;
- (IBAction)deletePrimaryFamily:(id)sender;
- (IBAction)editPrimaryIndividual:(id)sender;
- (IBAction)editCurrentFamily:(id)sender;

- (IBAction)openReportsSheet:(id)sender;
- (IBAction)selectPrintableView:(id)sender;

- (IBAction)addNewFamily:(id)sender;
- (IBAction)addNewIndividual:(id)sender;
- (IBAction)viewPedigree:(id)sender;
- (IBAction)findFamily:(id)sender;
- (IBAction)findIndividual:(id)sender;
- (IBAction)showIndividualList:(id)sender;
- (IBAction)showFamilyList:(id)sender;
- (IBAction)editNotes:(id)sender;

- (IBAction)setIndividual:(id)sender;
- (IBAction)transmitBugReport:(id)sender;

- (void)save;
- (void)startSuppressUpdates;
- (void)endSuppressUpdates;

@end
