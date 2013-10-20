//
// SDMovingRowsProtocol.h 
//
// (C) Copyright 1989-1999 Andrew C. Stone and Stone Design Corp
// All rights reserved. You can use this code, but please leave credit.
//

@class SDTableView;

@protocol SDMovingRowsProtocol

// Which key modifiers enable reordering? OR them together
// exp: return (NSCommandKeyMask|NSAlternateKeyMask);
// if you want the default behaviour to alas drag cells
// pass this pack for

#define DRAG_ALWAYS    0
- (NSUInteger)dragReorderingMask:(NSInteger)forColumn;

// Delegate called after the reordering of cells, you must reorder your data.
// Returning YES will cause the table to be reloaded.
- (BOOL)tableView:(NSTableView *)tv didDepositRow:(NSInteger)rowToMove at:(NSInteger)newPosition;

// This gives you a chance to decline to drop particular rows on other particular
// row. Return YES if you don't care
- (BOOL) tableView:(SDTableView *)tableView draggingRow:(NSInteger)draggedRow overRow:(NSInteger) targetRow;


@optional

// This is optional: the delegate can decide whether it wants to allow a
// particular row to be moved

- (BOOL) tableView:(SDTableView *)tableView rowWillMove:(NSInteger)draggedRow;

// This is optional: the delegate can return an image representing a row

- (NSImage *)tableView:(SDTableView *)tableView imageForRow:(NSInteger)aRow;

// This is optional: the delegate can refuse to drop a row over another row

 - (BOOL) tableView:(SDTableView *)tableView willDepositRow:(NSInteger) rowToMove at:(NSInteger) newPosition;

@end
