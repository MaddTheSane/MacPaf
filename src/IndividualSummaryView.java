//
//  IndividualDetailView.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Apr 27 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

import com.apple.cocoa.application.NSGraphics;
import com.apple.cocoa.application.NSView;
import com.apple.cocoa.foundation.NSAttributedString;
import com.apple.cocoa.foundation.NSPoint;
import com.apple.cocoa.foundation.NSRect;
import com.redbugz.macpaf.*;


public class IndividualSummaryView extends NSView {
   private Individual individual;

   public IndividualSummaryView(NSRect frame, Individual individual) {
       super(frame);
       // Initialization code here.
      this.individual = individual;
   }

   public IndividualSummaryView(Individual individual) {
       super(new NSRect(0,0,1500,1500)); // todo: make this Letter sized
       // Initialization code here.
      this.individual = individual;
   }

    public void drawRect(NSRect rect) {
        // Drawing code here.
       System.out.println("IndividualSummaryView.drawRect("+rect+")");
       NSAttributedString nameStr = new NSAttributedString(individual.getFullName());
       lockFocus();
       drawPageBorderWithSize(rect.size());
       NSGraphics.drawWindowBackground(new NSRect(400,400,50,50));
       NSGraphics.drawWhiteBezel(new NSRect(300,300,30,30), new NSRect(300,300,30,30));
       NSGraphics.drawGrayBezel(new NSRect(200,200,30,30), new NSRect(200,200,30,30));
       NSGraphics.drawDarkBezel(new NSRect(150,150,30,30), new NSRect(150,150,30,30));
       NSGraphics.drawLightBezel(new NSRect(0,300,30,30), new NSRect(0,300,30,30));
       NSGraphics.drawGroove(new NSRect(0,200,30,30), new NSRect(0,200,30,30));
       NSGraphics.drawButton(new NSRect(0,150,30,30), new NSRect(0,150,30,30));
       NSGraphics.dottedFrameRect(rect);
       NSGraphics.dottedFrameRect(new NSRect(0,0,1500,1500));
       NSGraphics.drawAttributedString(nameStr, new NSPoint(10,100));
//       NSGraphics.drawDarkBezel(rect, rect);
//       NSGraphics.drawDarkBezel(rect, rect);
//       NSGraphics.drawDarkBezel(rect, rect);
//       NSGraphics.drawDarkBezel(rect, rect);
       unlockFocus();
    }
    
}
