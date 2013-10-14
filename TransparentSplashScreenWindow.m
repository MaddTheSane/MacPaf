//
//  TransparentSplashScreenWindow.m
//  MAF
//
//  Created by Logan Allred on 1/2/07.
//  Copyright 2007 RedBugz Software. All rights reserved.
//

#import "TransparentSplashScreenWindow.h"


@implementation TransparentSplashScreenWindow

- (id)initWithContentRect:(NSRect)contentRect styleMask:(NSUInteger)style
				  backing:(NSBackingStoreType)bufferingType defer:(BOOL)flag
{
	// Determine the center of the main screen and set the origin of the content rect
	// so that the window will be centered in the main screen.
	NSScreen* mainScreen = [NSScreen mainScreen];
	NSRect screen = [mainScreen frame];
	NSPoint center = NSMakePoint(screen.size.width / 2, screen.size.height / 2);
	contentRect.origin.x = center.x - (contentRect.size.width / 2);
	contentRect.origin.y = center.y - (contentRect.size.height / 2);
	
	// Call the inherited init function, but pass NSBorderlessWindowMask instead of
	// the normal style. This will give us a window with no title bar or edge.
	id window = [super initWithContentRect:contentRect
								 styleMask:NSBorderlessWindowMask backing:bufferingType defer:flag];
	if (window)
	{
		// Set the opacity of the window so that we can see through it
		[window setOpaque:NO];
		
		// Set the window's background color to a clear color (without this step, the
		// normal OS X background with the alternating gray bars will still draw).
		[window setBackgroundColor:[NSColor colorWithDeviceWhite:1.0 alpha:0.0]];
		
		// We don't want the window's regular (rectanglar) shadow to draw since our
		// splash image already has the shadow in it
		[window setHasShadow:NO];
		
		// Make the window sit on top of all other windows. Note that this particular
		// level will make the splash screen float above ALL windows, even of other
		// applications. As long as its displayed for less then 2 seconds most users
		// won't complain about this, but you may want to consider changing the level
		// to the highest application level window instead so that the user can
		// continue to use their computer while your program is loading. See the
		// documentation on NSWindow for descriptions of the various levels of windows.
		[window setLevel:NSModalPanelWindowLevel];
	}
	
	return window;
}


@end
