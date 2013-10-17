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
@class MAFSurnameList;
@class MAFLocationList;
@class MAFHistoryController;
@class MAFPedigreeViewController;
@class MAFPedigreeView;
@class MAFDocument;

@interface MyDocument : NSDocument
@property (weak) IBOutlet NSTableView *childrenTable;
@property (weak) IBOutlet NSWindow *mainWindow; /* IBOutlet */
@property (weak) IBOutlet NSWindow *reportsWindow; /* IBOutlet */
@property (weak) IBOutlet NSWindow *individualEditWindow; /* IBOutlet */
@property (weak) IBOutlet NSWindow *familyEditWindow; /* IBOutlet */
@property (weak) IBOutlet NSWindow *taskProgressSheetWindow; /* IBOutlet */
@property (weak) IBOutlet FamilyListController *familyListWindowController; /* IBOutlet */
@property (weak) IBOutlet IndividualListController *individualListWindowController; /* IBOutlet */
@property (weak) IBOutlet FamilyListController *tabFamilyListController; /* IBOutlet */
@property (weak) IBOutlet IndividualListController *tabIndividualListController; /* IBOutlet */
@property (weak) IBOutlet NSObject *importController; /* IBOutlet */
@property (weak) IBOutlet MAFFamilyList *familyList; /* IBOutlet */
@property (weak) IBOutlet MAFIndividualList *individualList; /* IBOutlet */
@property (weak) IBOutlet MAFSurnameList *surnameList; /* IBOutlet */
@property (weak) IBOutlet MAFLocationList *locationList; /* IBOutlet */
@property (weak) IBOutlet MAFHistoryController *historyController; /* IBOutlet */
@property (weak) IBOutlet MAFPedigreeViewController *pedigreeViewController; /* IBOutlet */
@property (weak) IBOutlet NSButton *fatherButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *individualButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *individualFamilyButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *maternalGrandfatherButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *maternalGrandmotherButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *motherButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *paternalGrandfatherButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *paternalGrandmotherButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *spouseButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *familyAsSpouseButton; /* IBOutlet */
@property (weak) IBOutlet NSButton *familyAsChildButton; /* IBOutlet */
@property (weak) IBOutlet NSWindow *noteWindow; /* IBOutlet */
@property (weak) IBOutlet NSMatrix *reportsRadio; /* IBOutlet */
@property (weak) IBOutlet NSTableView *spouseTable; /* IBOutlet */
@property (weak) IBOutlet MAFPedigreeView *pedigreeView; /* IBOutlet */
@property (weak) IBOutlet NSTabView *mainTabView; /* IBOutlet */
@property (weak) IBOutlet NSWindow *bugReportWindow; /* IBOutlet */
@property (weak) IBOutlet NSButton *bugReportFileCheckbox; /* IBOutlet */
@property (unsafe_unretained) IBOutlet NSTextView *bugReportText; /* IBOutlet */

@property (strong, readonly) MAFDocument *mafDocument;

- (IBAction)openFamilyEditSheet:(id)sender;
- (IBAction)openIndividualEditSheet:(id)sender;


- (IBAction)deletePrimaryIndividual:(id)sender;
- (IBAction)deletePrimaryFamily:(id)sender;
- (IBAction)editPrimaryIndividual:(id)sender;
- (IBAction)editCurrentFamily:(id)sender;

- (IBAction)openReportsSheet:(id)sender;
- (IBAction)selectPrintableView:(id)sender;

@end
