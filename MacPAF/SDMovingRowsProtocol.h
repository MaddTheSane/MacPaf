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
- (unsigned int)dragReorderingMask:(int)forColumn;

// Delegate called after the reordering of cells, you must reorder your data.
// Returning YES will cause the table to be reloaded.
- (BOOL)tableView:(NSTableView *)tv didDepositRow:(int)rowToMove at:(int)newPosition;

// This gives you a chance to decline to drop particular rows on other particular
// row. Return YES if you don't care
- (BOOL) tableView:(SDTableView *)tableView draggingRow:(int)draggedRow overRow:(int) targetRow;



// This is optional: the delegate can decide whether it wants to allow a
// particular row to be moved

//- (BOOL) tableView:(SDTableView *)tableView rowWillMove:(int)draggedRow;

// This is optional: the delegate can return an image representing a row

//- (NSImage *)tableView:(SDTableView *)tableView imageForRow:(int)aRow;

// This is optional: the delegate can refuse to drop a row over another row

// - (BOOL) tableView:(SDTableView *)tableView willDepositRow:(int) rowToMove at:(int) newPosition;

@end
