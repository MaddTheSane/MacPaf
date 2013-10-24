//
//  IndividualDetailController.h
//  MAF
//
//  Created by C.W. Betts on 10/24/13.
//
//

#import <Cocoa/Cocoa.h>
#import "MAFPerson.h"

@interface IndividualDetailController : NSObject
@property (weak) IBOutlet NSTextField *detailsText;  
@property (weak) IBOutlet NSTableView *eventTable;  
@property (weak) IBOutlet NSTextField *infoText;  
@property (weak) IBOutlet NSTextField *marriageText;  
@property (unsafe_unretained) IBOutlet NSTextView *noteText;  
@property (weak) IBOutlet NSImageView *photo;  
@property (weak) IBOutlet NSButtonCell *locked;  
@property (weak) IBOutlet NSButtonCell *privacy;  
@property (weak) MAFPerson *individual;

@end
