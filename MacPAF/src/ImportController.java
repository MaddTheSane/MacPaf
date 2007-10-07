//
//  ReportsController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 30 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.io.*;

import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.*;
import com.redbugz.macpaf.jdom.*;

/*
 * @deprecated use Obj-C ImportController instead
 */
class ImportController {
  private static final Logger log = Logger.getLogger(ImportController.class);

  public NSWindow importWindow; /* IBOutlet */
  public NSMatrix importRadio; /* IBOutlet */
  public NSTextField filePathField; /* IBOutlet */
  public NSProgressIndicator progress; /* IBOutlet */
  public NSObject taskView; /* IBOutlet */

private NSOpenPanel openPanel;

  
  public void cancel(Object sender) { /* IBAction */
//	NSApplication.sharedApplication().stopModal();
	NSApplication.sharedApplication().endSheet(importWindow);
	importWindow.orderOut(this);
  }

//return array of document extensions of specified document type name
  private NSArray allReadableTypesArray() 
  {
  	int i;
  	NSArray typeList = (NSArray) NSBundle.mainBundle().infoDictionary().objectForKey("CFBundleDocumentTypes");
  	NSMutableArray returnTypes = new NSMutableArray();
  	for (i=0; i<typeList.count(); i++) {
  			returnTypes.addObjectsFromArray((NSArray) ((NSDictionary) typeList.objectAtIndex(i)).objectForKey("CFBundleTypeExtensions"));
  			NSArray osTypes = (NSArray) ((NSDictionary) typeList.objectAtIndex(i)).objectForKey("CFBundleTypeOSTypes");
  			log.debug(osTypes);
  			if (osTypes != null) {
  			for (int j = 0; j < osTypes.count(); j++) {
				returnTypes.addObject("'"+osTypes.objectAtIndex(j)+"'");
			}
  			}
  	}
	  log.debug("allReadableTypeExtensions: "+returnTypes);
  	return returnTypes;
  }

  public void browse(Object sender) { /* IBAction */
	  NDC.push(((MyDocument) NSDocumentController.sharedDocumentController().currentDocument()).displayName()+" import browse");
	  try {
		  openPanel = NSOpenPanel.openPanel();
		//panther only?        panel.setMessage("Please select a GEDCOM file to import into this MacPAF file.");
//		  NSWindow mainWindow = ((MyDocument) NSDocumentController.sharedDocumentController().currentDocument()).mainWindow;
//		  log.debug("mainwindow:"+mainWindow.title());
		  NSApplication.sharedApplication().endSheet(importWindow);
		openPanel.beginSheetForDirectory(null, null, allReadableTypesArray(), null,
				  this,
				  new NSSelector("openPanelDidEnd", new Class[] {NSOpenPanel.class, int.class, Object.class}), null);
	  } catch (Exception e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  NDC.pop();
  }
  public void openPanelDidEnd(NSOpenPanel sheet, int returnCode, Object contextInfo) {
	  NDC.push(((MyDocument) NSDocumentController.sharedDocumentController().currentDocument()).displayName()+" import");
	  if (returnCode == NSPanel.OKButton) {
		  log.debug("import filename:" + sheet.filename());
		  filePathField.setStringValue(sheet.filename());
	  }
	  NDC.pop();
  }
  
  public void importFile(Object sender) { /* IBAction */
	  NDC.push(((MyDocument) NSDocumentController.sharedDocumentController().currentDocument()).displayName()+" import");
	log.debug("save sender=" + sender + " selectedTag=" + importRadio.selectedTag());
	try {
		MyDocument doc = (MyDocument) NSDocumentController.sharedDocumentController().currentDocument();
		if  (importRadio.selectedTag() == 0) { // import into existing file
			doc.startSuppressUpdates();
			try {
				if (progress == null) {
					if (taskView != null) {
						progress = (NSProgressIndicator) taskView.valueForKey("progressView");
					} else {
						progress = new NSProgressIndicator(NSRect.ZeroRect);
					}
					
				}
				doc.doc.importGedcom(new File(filePathField.stringValue()), progress);
				if (doc.getPrimaryIndividual() instanceof Individual.UnknownIndividual) {
					doc.doc.chooseNewPrimaryIndividual();
					doc.setPrimaryIndividual(doc.doc.getPrimaryIndividual());
				}
			} catch (RuntimeException e) {
				throw e;
			} finally {
				doc.endSuppressUpdates();
			}
		} else {
			// import into new file
			NSDocumentController.sharedDocumentController().openDocumentWithContentsOfFile(filePathField.stringValue(), true);
		}
	} catch (RuntimeException e) {
		e.printStackTrace();
		MyDocument.showUserErrorMessage("There was an unexpected error during import.", "An unexpected error occurred while attempting to import the file. The data may not have been imported. Please try again or report this to the MacPAF developers");
	} finally {
		NDC.pop();
		NSApplication.sharedApplication().endSheet(importWindow);
	}
	importWindow.orderOut(this);
	resetWindow();
  }
  
  private void resetWindow() {
	  // prepare window for next use, reset default values
	  filePathField.setStringValue("");
	  progress.setDoubleValue(0D);
	  progress.setMaxValue(100d);
	  progress.stopAnimation(this);
	  progress.setIndeterminate(true);
	  progress.displayIfNeeded();
  }

//  public void setDocument(NSDocument nsDocument) {
//	super.setDocument(nsDocument);
//	log.debug("setdocument:" + nsDocument);
////      log.debug("surname:"+surname);
//	Individual individual = ( (MyDocument) nsDocument).getPrimaryIndividual();
////      setIndividual(individual);
//	doc = (MyDocument) nsDocument;
//  }

//  public void openImportSheet(Object sender) { /* IBAction */
//	NSApplication nsapp = NSApplication.sharedApplication();
//	nsapp.beginSheet(importSheet, ((MyDocument)this.document()).mainWindow, null, null, null);
////	nsapp.runModalForWindow(this.window());
////	nsapp.endSheet(this.window());
////	this.window().orderOut(this);
//  }
}
