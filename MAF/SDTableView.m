//
// SDTableView.m 
//
// (C) Copyright 1989-1999 Andrew C. Stone and Stone Design Corp
// All rights reserved. You can use this code, but please leave credit.
//

#import "SDTableView.h"
#import "SDMovingRowsProtocol.h"

@interface SDTableView(Private)

- (NSImage *)imageForRow:(int)aRow;
- (void)drawTableCache:(NSRect)rowRect;
- (void)drawRowCache:(NSRect)rowRect;
- sizeCacheWindow:cacheImage to:(NSSize)windowSize;
- (void)grabTableBits;

@end


static NSString *PrivateDragPboard = @"SDTableViewPrivatePBoardType";

extern NSEvent *periodicEventWithLocationSetToPoint(NSEvent *oldEvent, NSPoint point) {
    return [NSEvent otherEventWithType:[oldEvent type] location:point modifierFlags:[oldEvent modifierFlags] timestamp:[oldEvent timestamp] windowNumber:[oldEvent windowNumber] context:[oldEvent context] subtype:[oldEvent subtype] data1:[oldEvent data1] data2:[oldEvent data2]];
}



@implementation SDTableView

- (BOOL)acceptsFirstMouse:(NSEvent *)theEvent {return YES;}


- (id)initWithFrame:(NSRect)fr
{
    [super initWithFrame:fr];
    [self registerForDraggedTypes:[NSArray arrayWithObject:PrivateDragPboard]];
    return self;
}

- (id) initWithCoder:(NSCoder *)aCoder
{
    [super initWithCoder:aCoder];
    [self registerForDraggedTypes:[NSArray arrayWithObject:PrivateDragPboard]];
    return self;
}


- (void)copy:sender
{
[[self delegate]copy:self];
}

- (void)cut:sender
{
    [[self delegate]cut:self];
}

- (void)paste:sender
{
    [[self delegate]paste:self];
}


- (BOOL)startRow:(int)start endedAt:(int)end
{
    if (end == -1) return NO;
    if (end == start) return NO;
    if ([[self delegate] respondsToSelector:@selector(tableView:willDepositRow:at:)] && ![[self delegate] tableView:self willDepositRow:start at:end])
        return NO;

    // okay to change

    if ([[self delegate] tableView:self didDepositRow:start at:end]) {
        [self reloadData];
        [self selectRow:end byExtendingSelection:NO];
        return YES;
    }

    return NO;

}

#define MOVE_MASK NSLeftMouseUpMask|NSLeftMouseDraggedMask


- (BOOL)constrainedMove:(NSEvent *)theEvent
{
    NSPoint        mouseDownLocation, mouseUpLocation, mouseLocation;
    int            newRow;
    NSRect        visibleRect, rowRect;
    NSImage         *image = nil;
    NSWindow        *wind = [self window];

    float        dy, dx;
    NSEvent         *peek,*event;
    BOOL        scrolled = NO;
    BOOL inTimerLoop = NO;

// find the cell that got clicked on and select it
    mouseDownLocation = [theEvent locationInWindow];
    mouseDownLocation = [self convertPoint:mouseDownLocation fromView:nil];
    draggedRow = [self rowAtPoint:mouseDownLocation];

    // this forces text editing to end
    [wind makeFirstResponder:wind];


    [self selectRow:draggedRow byExtendingSelection:NO];

    // this makes sure we get the whole row into the image
    [self scrollRowToVisible:draggedRow];

    // this makes sure the display is up to date
    [self display];


    // copy what's currently visible into the table cache
    tableCache = [self sizeCacheWindow:tableCache to:[self visibleRect].size];
    [self grabTableBits];

    // get the image of the table row
    // first, give the delegate a chance to supply an image
    if ([[self delegate] respondsToSelector:@selector(tableView:imageForRow:)])
        image = [[self delegate] tableView:self imageForRow:draggedRow];

    // otherwise, get our own image (the image of the whole row)
    if (!image) image = [self imageForRow:draggedRow];
    rowCache = image;

    rowRect = [self rectOfRow:draggedRow];

    // save the mouse's location relative to the cell's origin */
    dy = mouseDownLocation.y - rowRect.origin.y;
    dx = mouseDownLocation.x - rowRect.origin.x;

    // we're now interested in mouse dragged events
    [wind setAcceptsMouseMovedEvents:YES];

// from now on we'll be drawing into ourself
//    [[NSApp handCursor] push];
    [self lockFocus];

    event = theEvent;

    // START LOOP

    while ([event type] != NSLeftMouseUp) {

     // erase the active cell using the image in the matrix cache
        visibleRect = [self visibleRect];

        [self drawTableCache:rowRect];


     // move the active row
        mouseLocation = [event locationInWindow];
        mouseLocation = [self convertPoint:mouseLocation fromView:nil];
        rowRect.origin.y = mouseLocation.y - dy;
        rowRect.origin.x = mouseLocation.x - dx;

     // constrain the row's location to our bounds
        if (NSMinY(rowRect) < NSMinX([self bounds]) ) {
            rowRect.origin.y = NSMinX([self bounds]);
        } else if (NSMaxY(rowRect) > NSMaxY([self bounds])) {
            rowRect.origin.y = NSHeight([self bounds]) - NSHeight(rowRect);
        }
        if (NSMinX(rowRect) < NSMinY([self bounds]) ) {
            rowRect.origin.x = NSMinY([self bounds]);
        } else if (NSMaxX(rowRect) > NSMaxX([self bounds])) {
            rowRect.origin.x = NSWidth([self bounds]) - NSWidth(rowRect);
        }


     // make sure the cell will be entirely visible in its new location (if
     // we're in a scrollView, it may not be)

        if (!NSContainsRect(visibleRect , rowRect)) {    

         // the cell won't be entirely visible, so scroll, dood, scroll, but
         // don't display on-screen yet

            [[self window] disableFlushWindow];
            [self scrollRectToVisible:rowRect];
            [[self window] enableFlushWindow];

         // copy the new image to the matrix cache
            [self grabTableBits];


         // note that we scrolled and start generating timer events for
         // autoscrolling

            scrolled = YES;
            if(!inTimerLoop){
                [NSEvent startPeriodicEventsAfterDelay:0.1 withPeriod:0.1];
                inTimerLoop = YES;
            }

        } else {
            // don't need to scroll
            if(inTimerLoop){
                [NSEvent stopPeriodicEvents];
                inTimerLoop = NO;
            }
        }

     // composite the active cell's image on top of ourself

        [self drawRowCache:rowRect];

     // now show what we've done
        [[self window] flushWindow];


     // if we autoscrolled, flush any lingering window server events to make
     // the scrolling smooth

     // save the current mouse location, just in case we need it again
        mouseLocation = [event locationInWindow];

        if (!(peek = [[self window] nextEventMatchingMask:MOVE_MASK untilDate:[NSDate date] inMode:NSEventTrackingRunLoopMode dequeue:NO])) {
            event = [[self window] nextEventMatchingMask:MOVE_MASK|NSPeriodicMask];
        } else {
            event = [[self window] nextEventMatchingMask:MOVE_MASK];
        }    
     // if a timer event, mouse location isn't valid, so we'll set it
        if ([event type] == NSPeriodic) {
            event = periodicEventWithLocationSetToPoint(event, mouseLocation);

        }
    }

    // END LOOP

// mouseUp, so stop any timer and unlock focus
    if (scrolled && inTimerLoop) {
        [NSEvent stopPeriodicEvents];
        inTimerLoop = NO;
        PSWait();
        scrolled = NO;
    }
    [self unlockFocus];

//    [[NSApp handCursor] pop];

// find the cell under the mouse's location
    mouseUpLocation = [event locationInWindow];
    mouseUpLocation = [self convertPoint:mouseUpLocation fromView:nil];
    newRow = [self rowAtPoint:mouseUpLocation];
    if (newRow == -1) {
     // mouse is out of bounds, so find the row the dragged row covers
        newRow = [self rowAtPoint:rowRect.origin];
    }

// we need to shuffle cells if the active cell's going to a new location
    if (![self startRow:draggedRow endedAt:newRow]) {
     // go back to the way we were
        [self display];
    }

// now redraw ourself
//    [self display];

// set the event mask to normal
    [wind setAcceptsMouseMovedEvents:NO];
    return YES;
}

/////////////////////////////////////////////////////////////////////////////
//    Using the drag/drop mechanism
/////////////////////////////////////////////////////////////////////////////

- (BOOL)copyRow:(int)hitRow toPasteboard:pb
{
    [pb declareTypes:[NSArray arrayWithObject:PrivateDragPboard] owner:self];
return [pb setString:[NSString stringWithFormat:@"%d",hitRow] forType:PrivateDragPboard];
}


- (BOOL)unconstrainedMove:(NSEvent *)e
{
    NSPoint mouseDownLocation = [e locationInWindow];
    NSWindow *wind = [self window];
    NSPasteboard *pb;
    NSImage *image = nil;
    NSRect rect;
    NSPoint lowerLeftCorner;

    mouseDownLocation = [self convertPoint:mouseDownLocation fromView:nil];
    draggedRow = [self rowAtPoint:mouseDownLocation];

    pb = [NSPasteboard pasteboardWithName:NSDragPboard];

    // this forces text editing to end
    [wind makeFirstResponder:wind];


    [self selectRow:draggedRow byExtendingSelection:NO];

    // this makes sure we get the whole row into the image
    [self scrollRowToVisible:draggedRow];

    // this makes sure the display is up to date
    [self display];

    // give the delegate a chance to supply an image
    if ([[self delegate] respondsToSelector:@selector(tableView:imageForRow:)])
        image = [[self delegate] tableView:self imageForRow:draggedRow];

    // otherwise, get our own image (the image of the whole row)
    if (!image) image = [self imageForRow:draggedRow];

    rect = [self rectOfRow:draggedRow];
    lowerLeftCorner = NSMakePoint(rect.origin.x,rect.origin.y + rect.size.height);
    if ([self copyRow:draggedRow toPasteboard:pb]) {
        [self dragImage:image at:lowerLeftCorner offset:NSZeroSize event:(NSEvent *)e pasteboard:pb source:self slideBack:YES];
     return YES;
    }

    return NO;

}


- (BOOL) move:(NSEvent *)e
{
    unsigned int flags = [e modifierFlags];
    NSPoint mouseDownLocation = [e locationInWindow];
    int draggedCol;
    NSEvent *peek = nil;
    NSWindow *wind = [self window];

    // if the next event is a mouse up, don't drag the row
    peek = [wind nextEventMatchingMask:MOVE_MASK untilDate:[NSDate dateWithTimeIntervalSinceNow:0.1] inMode:NSEventTrackingRunLoopMode dequeue:NO];
    if ([peek type] == NSLeftMouseUp) return NO;

    mouseDownLocation = [self convertPoint:mouseDownLocation fromView:nil];
    draggedCol = [self columnAtPoint:mouseDownLocation];
    draggedRow = [self rowAtPoint:mouseDownLocation];

    // if the Control key isn't down, show normal behavior
    if (needsControlKey && !([e modifierFlags] & NSControlKeyMask)) {
        return NO;
    }

    // see if the delegate wants to allow moving the row
    if (![[self delegate] conformsToProtocol:@protocol(SDMovingRowsProtocol)])
        return NO;
    if (!(flags & [[self delegate]dragReorderingMask:draggedCol]) &&
        ([[self delegate]dragReorderingMask:draggedCol] != DRAG_ALWAYS))
        return NO;
    if ([[self delegate] respondsToSelector:@selector(tableView:rowWillMove:)] && ![[self delegate]tableView:self rowWillMove:draggedRow])
        return NO;

    // if we get here, we can move the row

    if (constrainedMove)
        return [self constrainedMove:e];
    else
        return [self unconstrainedMove:e];


}

- (void)mouseDown:(NSEvent *)e
{
    if (![self move:e]) [super mouseDown:e];
}


- (unsigned int)draggingSourceOperationMaskForLocal:(BOOL)flag
{
    return NSDragOperationCopy|NSDragOperationGeneric|NSDragOperationLink;
}

/*
- (void)draggedImage:(NSImage *)image endedAt:(NSPoint)screenPoint deposited:(BOOL)flag
{
    int endedAt;
    screenPoint = [[self window] convertScreenToBase:screenPoint];
    screenPoint = [self convertPoint:screenPoint fromView:nil];
// now the point is in our local coordinates....
    endedAt = [self rowAtPoint:screenPoint];
    if (flag && endedAt != -1 && [[self delegate] tableView:self didDepositRow:draggedRow at:endedAt]) {
            [self reloadData];
            [self selectRow:endedAt byExtendingSelection:NO];
        }

}
*/

extern BOOL IncludesType(NSArray *types, NSString *type)
{
    return types ? ([types indexOfObject:type] != NSNotFound) : NO;
}



- (BOOL) acceptsTypes:(NSArray *)types forSender:sender
{
    if (IncludesType(types, PrivateDragPboard)) return YES;    
    return NO;
}

- (unsigned int)draggingEntered:(id <NSDraggingInfo>)sender
{
    NSPasteboard *pboard = [sender draggingPasteboard];

    if ([self acceptsTypes:[pboard types] forSender:sender])
        return NSDragOperationGeneric;
    return NSDragOperationNone;
}


- (unsigned int)draggingUpdated:(id <NSDraggingInfo>)sender
{
    NSPoint point = [sender draggingLocation];
    NSRect visRect = [self visibleRect];
    NSRect scrollRect;
    float rowHeight = [self rowHeight];
    int aRow;

    point = [self convertPoint:point fromView:nil];
    aRow = [self rowAtPoint:point];

    if ((aRow == 0) || (aRow == [self numberOfRows]-1)) {
        [self scrollRowToVisible:aRow];
    } else if (point.y < visRect.origin.y + rowHeight/4) {
        // we need to scroll at top
        scrollRect = NSMakeRect(visRect.origin.x,(visRect.origin.y-rowHeight),visRect.size.width,rowHeight);
        [self scrollRectToVisible:scrollRect];
    } else if (point.y > ((visRect.origin.y + visRect.size.height) - rowHeight/4)) {
        // we need to scroll at bottom
        scrollRect = NSMakeRect(visRect.origin.x,visRect.origin.y+visRect.size.height,visRect.size.width,rowHeight);
        [self scrollRectToVisible:scrollRect];
    }
    if (![[self delegate] tableView:self draggingRow:draggedRow overRow:aRow])
        return NSDragOperationNone;
    else
        return NSDragOperationGeneric;
}


- (BOOL)performDragOperation:(id <NSDraggingInfo>)sender
{
    int endedAt;
    NSPoint screenPoint = [self convertPoint:[sender draggingLocation] fromView:nil];
    // now the point is in our local coordinates....
    endedAt = [self rowAtPoint:screenPoint];
    return [self startRow:draggedRow endedAt:endedAt];
}


- (void)grabTableBits
{
    // copy what's currently visible into the matrix cache
    NSRect visibleRect = [self visibleRect];
    NSCachedImageRep *tableRep = [[tableCache representations]objectAtIndex:0];

    visibleRect = [self convertRect:visibleRect toView:nil];

    [tableCache lockFocusOnRepresentation:tableRep];
#if USING_DPS
    PScomposite(NSMinX(visibleRect), NSMinY(visibleRect),
                NSWidth(visibleRect), NSHeight(visibleRect),
            [[self window] gState], 0.0, 0.0, NSCompositeCopy);
#else
    [self drawRect:visibleRect];
#endif

    [tableCache unlockFocus];
}


- (void)grabRowBits
{
    // copy what's currently visible into the row cache
    NSRect rowRect = [self rectOfRow:draggedRow];
    NSCachedImageRep *rowRep = [[rowCache representations]objectAtIndex:0];

    rowRect = [self convertRect:rowRect toView:nil];

    [rowCache lockFocusOnRepresentation:rowRep];
#if USING_DPS
    PScomposite(NSMinX(rowRect), NSMinY(rowRect),
                NSWidth(rowRect), NSHeight(rowRect),
            [[self window] gState], 0.0, 0.0, NSCompositeCopy);
#else
    [self drawRect:rowRect];
#endif

    [rowCache unlockFocus];
}

- (NSImage *) imageForRow:(int)aRow
{
    NSSize old = rowCache? [rowCache size] : NSZeroSize;
    NSRect rowRect = [self rectOfRow:draggedRow];
    NSSize rowSize = rowRect.size;

    if (rowSize.width != old.width ||
        rowSize.height != old.height) {
     // create the cache window if it doesn't exist
        NSCachedImageRep *rep;
        if (rowCache) [rowCache release];
        rowCache = [[NSImage allocWithZone:(NSZone *)[(NSObject *)self zone]] initWithSize:rowSize];
        rep = [[[NSCachedImageRep allocWithZone:(NSZone *)[(NSObject *)self zone]] initWithSize:rowSize depth:[NSWindow defaultDepthLimit] separate:YES alpha:YES]autorelease];

        [rowCache addRepresentation:rep];
    }
    [self grabRowBits];

    return rowCache;
}


- sizeCacheWindow:cacheImage to:(NSSize)windowSize
{
    NSSize old = cacheImage? [cacheImage size] : NSZeroSize;

    if (windowSize.width != old.width ||
                windowSize.height != old.height) {
     // create the cache window if it doesn't exist
        NSCachedImageRep *rep;
        if (cacheImage) [cacheImage release];
        cacheImage = [[NSImage allocWithZone:(NSZone *)[(NSObject *)self zone]] initWithSize:windowSize];
        rep = [[[NSCachedImageRep allocWithZone:(NSZone *)[(NSObject *)self zone]] initWithSize:windowSize
            depth:[NSWindow defaultDepthLimit] separate:YES alpha:YES]autorelease];

        [cacheImage addRepresentation:rep];
    }

    return cacheImage;
}

- (void)drawTableCache:(NSRect)rowRect
{
    NSRect visibleRect = [self visibleRect];
    NSRect sourceRect = rowRect;
    NSPoint origin = rowRect.origin;


    // adjust composite point: seems to need this!
    origin.y += rowRect.size.height;        

    // now the cache is just the visibleRect, so we need to adjust the sourceRect:

    sourceRect.origin.y = NSHeight(visibleRect) + NSMinY(visibleRect) - origin.y;
    [tableCache compositeToPoint:origin fromRect:sourceRect operation:NSCompositeCopy];
    {
        NSRect myRow = [self rectOfRow:draggedRow];
        myRow.size.height=myRow.size.height+1;
        myRow.origin.y=myRow.origin.y-1;
        NSDrawGrayBezel(myRow,myRow);
        [[NSColor controlShadowColor/*darkGrayColor*/] set];
        myRow.origin.x=myRow.origin.x+2.0;
        myRow.size.width=myRow.size.width-4.0;

        myRow.origin.y=myRow.origin.y+2.0;
        myRow.size.height=myRow.size.height-4.0;

        NSRectFill(myRow);
    }

}

- (void)drawRowCache:(NSRect)rowRect
{
    NSRect myRect=rowRect;

    myRect.origin.y += myRect.size.height;
    [rowCache dissolveToPoint:myRect.origin fraction:0.75];
}




- (void)setConstrainedMove:(BOOL)flag
{
    constrainedMove = flag;
}

- (void)setNeedsControlKey:(BOOL)flag
{
    needsControlKey = flag;
}

@end