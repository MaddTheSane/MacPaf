//
//  IndividualListController.h
//  MAF
//
//  Created by C.W. Betts on 10/9/13.
//
//

#import <Cocoa/Cocoa.h>

@class IndividualDetailController;

@interface IndividualListController : NSWindowController

- (IBAction)search:(id)sender;
- (IBAction)selectIndividual:(id)sender;

@property (weak) IBOutlet NSTextField *searchField;
@property (weak) IBOutlet NSTableView *individualListTableView;
@property (weak) IBOutlet NSTextField *individualCountText;
@property (unsafe_unretained) IBOutlet IndividualDetailController *individualDetailController;

@end
