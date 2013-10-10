//
//  FamilyListController.h
//  MAF
//
//  Created by C.W. Betts on 10/9/13.
//
//

#import <Cocoa/Cocoa.h>

@interface FamilyListController : NSWindowController
@property (weak) IBOutlet NSTextField *searchField;
@property (weak) IBOutlet NSTableView *familyListTableView;
@property (weak) IBOutlet NSTextField *familyCountText;

- (IBAction)search:(id)sender;
- (IBAction)selectFamily:(id)sender;



@end
