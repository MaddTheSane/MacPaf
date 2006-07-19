//
//  ReportsController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 30 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.redbugz.macpaf.*;

public class ReportsController extends NSWindowController {
  private static final Logger log = Logger.getLogger(ReportsController.class);

  public NSMatrix reportsRadio; /* IBOutlet */
  public NSTextField personField; /* IBOutlet */
  MyDocument doc;

  public void windowDidLoad() {
	super.windowDidLoad();
//      log.debug("windowdidload doc="+document()+" surname="+surname);
  }

  public void cancel(Object sender) { /* IBAction */
//	NSApplication.sharedApplication().stopModal();
	NSApplication.sharedApplication().endSheet(window());
	window().orderOut(this);
  }

  public void showWindow(Object o) {
	super.showWindow(o);
	log.debug("showWindow o=" + o);
  }

  public void save(Object sender) { /* IBAction */
	log.debug("save sender=" + sender + " selectedTag=" + reportsRadio.selectedTag());
	doc.setPrintableView(sender);
	NSApplication.sharedApplication().endSheet(window());
	window().orderOut(this);
	doc.printShowingPrintPanel(true);
//       reportsRadio.selectedTag()
	cancel(sender);
  }

  public void setDocument(NSDocument nsDocument) {
	super.setDocument(nsDocument);
	log.debug("setdocument:" + nsDocument);
	doc = (MyDocument) nsDocument;
	  try {
		  log.debug("setting reports person field");
		  Individual individual = doc.getPrimaryIndividual();
		  personField.setStringValue(individual.getFullName());
	  } catch (RuntimeException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
  }

  public void openReportsSheet(Object sender) { /* IBAction */
	NSApplication nsapp = NSApplication.sharedApplication();
	nsapp.beginSheet(this.window(), ((MyDocument)this.document()).mainWindow, null, null, null);
//	nsapp.runModalForWindow(this.window());
//	nsapp.endSheet(this.window());
//	this.window().orderOut(this);
  }

  public void windowWillLoad() {
	super.windowWillLoad();
//      log.debug("windowWillLoad surname="+surname);
	log.debug("windowwillload doc=" + document());
  }
}
