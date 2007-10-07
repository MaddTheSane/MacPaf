//
//  IndividualDetailView.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Apr 27 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.maf.*;

public class IndividualSummaryView extends NSView {
  private static final Logger log = Logger.getLogger(IndividualSummaryView.class);
  private Individual individual;

  public IndividualSummaryView(NSRect frame, Individual individual) {
	super(frame);
	// Initialization code here.
	this.individual = individual;
  }

  public IndividualSummaryView(Individual individual) {
	super(new NSRect(0, 0, 1500, 1500)); // todo: make this Letter sized
	// Initialization code here.
	this.individual = individual;
  }

  public void drawRect(NSRect rect) {
	// Drawing code here.
	log.debug("IndividualSummaryView.drawRect(" + rect + ")");
	NSAttributedString nameStr = new NSAttributedString(individual.getFullName());
	lockFocus();
	drawPageBorderWithSize(rect.size());
	NSGraphics.drawWindowBackground(new NSRect(400, 400, 50, 50));
	NSGraphics.drawWhiteBezel(new NSRect(300, 300, 30, 30), new NSRect(300, 300, 30, 30));
	NSGraphics.drawGrayBezel(new NSRect(200, 200, 30, 30), new NSRect(200, 200, 30, 30));
	NSGraphics.drawDarkBezel(new NSRect(150, 150, 30, 30), new NSRect(150, 150, 30, 30));
	NSGraphics.drawLightBezel(new NSRect(0, 300, 30, 30), new NSRect(0, 300, 30, 30));
	NSGraphics.drawGroove(new NSRect(0, 200, 30, 30), new NSRect(0, 200, 30, 30));
	NSGraphics.drawButton(new NSRect(0, 150, 30, 30), new NSRect(0, 150, 30, 30));
	NSGraphics.dottedFrameRect(rect);
	NSGraphics.dottedFrameRect(new NSRect(0, 0, 1500, 1500));
	NSGraphics.drawAttributedString(nameStr, new NSPoint(10, 100));
//       NSGraphics.drawDarkBezel(rect, rect);
//       NSGraphics.drawDarkBezel(rect, rect);
//       NSGraphics.drawDarkBezel(rect, rect);
//       NSGraphics.drawDarkBezel(rect, rect);
	unlockFocus();
  }

}
