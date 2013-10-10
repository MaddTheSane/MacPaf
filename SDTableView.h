//
// SDTableView.h 
//
// (C) Copyright 1989-1999 Andrew C. Stone and Stone Design Corp
// All rights reserved. You can use this code, but please leave credit.
//

#import <AppKit/AppKit.h>

@interface SDTableView : NSTableView
{
    BOOL needsControlKey;    // YES if the user needs to hold the control key
// to instigate dragging
    BOOL constrainedMove;    // YES if the user wants to constrain the row to
// the view
    int draggedRow;
    NSImage *rowCache;
    NSImage *tableCache;
}

- (BOOL)startRow:(int)start endedAt:(int)end;
- (void)setConstrainedMove:(BOOL)flag;
- (void)setNeedsControlKey:(BOOL)flag;

@end


@interface NSObject(SDTableViewDelegate)

- (BOOL) tableView:(SDTableView *)tableView rowWillMove:(int)draggedRow;
- (NSImage *)tableView:(SDTableView *)tableView imageForRow:(int)aRow;
- (BOOL) tableView:(SDTableView *)tableView willDepositRow:(int) rowToMove at:(int) newPosition;

@end
