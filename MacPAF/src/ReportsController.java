//
//  ReportsController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 30 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

import com.apple.cocoa.application.NSApplication;
import com.apple.cocoa.application.NSDocument;
import com.apple.cocoa.application.NSMatrix;
import com.apple.cocoa.application.NSWindowController;
import com.redbugz.macpaf.*;

public class ReportsController extends NSWindowController {

    public NSMatrix reportsRadio; /* IBOutlet */
   MyDocument doc;

   public void windowDidLoad() {
      super.windowDidLoad();
//      System.out.println("windowdidload doc="+document()+" surname="+surname);
   }

   public void cancel(Object sender) { /* IBAction */
      NSApplication.sharedApplication().stopModal();
      NSApplication.sharedApplication().endSheet(window());
      window().orderOut(this);
    }

   public void showWindow(Object o) {
      super.showWindow(o);
      System.out.println("showWindow o="+o);
   }

    public void save(Object sender) { /* IBAction */
       System.out.println("save sender="+sender+" selectedTag="+reportsRadio.selectedTag());
       doc.setPrintableView(sender);
       NSApplication.sharedApplication().endSheet(window());
       window().orderOut(this);
       doc.printShowingPrintPanel(true);
//       reportsRadio.selectedTag()
      cancel(sender);
    }

   public void setDocument(NSDocument nsDocument) {
      super.setDocument(nsDocument);
      System.out.println("setdocument:"+nsDocument);
//      System.out.println("surname:"+surname);
      Individual individual = ((MyDocument)nsDocument).getPrimaryIndividual();
//      setIndividual(individual);
      doc = (MyDocument)nsDocument;
   }

   public void openReportsSheet(Object sender) { /* IBAction */
      NSApplication nsapp = NSApplication.sharedApplication();
      nsapp.beginSheet(this.window(), this.window().parentWindow(), null, null, null);
      nsapp.runModalForWindow(this.window());
      nsapp.endSheet(this.window());
      this.window().orderOut(this);
   }

   public void windowWillLoad() {
      super.windowWillLoad();
//      System.out.println("windowWillLoad surname="+surname);
      System.out.println("windowwillload doc="+document());
   }
}
