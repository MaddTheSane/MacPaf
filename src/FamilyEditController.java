//
//  FamilyEditController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Apr 06 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.util.Enumeration;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.NSMutableArray;
import com.apple.cocoa.foundation.NSSelector;
import com.apple.cocoa.foundation.NSSystem;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.jdom.FamilyJDOM;
import com.redbugz.macpaf.jdom.PlaceJDOM;
import com.redbugz.macpaf.test.TestFamily;
import com.redbugz.macpaf.util.CocoaUtils;

import org.apache.log4j.Category;

public class FamilyEditController extends NSWindowController {
  private static final Category log = Category.getInstance(FamilyEditController.class.getName());

  public NSTableView children; /* IBOutlet */
  public NSButton divorcedSwitch; /* IBOutlet */
  public NSButton husbandButton; /* IBOutlet */
  public NSForm marriageForm; /* IBOutlet */
  public NSTextField sealingDate; /* IBOutlet */
  public NSComboBox sealingTemple; /* IBOutlet */
  public NSButton wifeButton; /* IBOutlet */
  public NSButton saveButton; /* IBOutlet */

  private Family family = Family.UNKNOWN_FAMILY;
  private MyDocument document = null;
  
  private NSMutableArray undoStack = new NSMutableArray();

  public void setDocument(NSDocument document) {
	super.setDocument(document);
	System.out.println("FamilyEditController.setDocument(document):"+document.fileName());
	this.document = (MyDocument) document;
	setFamily( ( (MyDocument) document).getPrimaryIndividual().getFamilyAsSpouse());
  }

  public void setFamily(Family family) {
	System.out.println("FamilyEditController.setFamily(family):"+family);
	if (family instanceof Family.UnknownFamily) {
		saveButton.setTitle("Add Family");
	} else {
		saveButton.setTitle("Save Family");
	}
	this.family = family;
	husbandButton.setTitle(family.getFather().getFullName());
	wifeButton.setTitle(family.getMother().getFullName());
	marriageForm.cellAtIndex(0).setStringValue(family.getMarriageEvent().getDateString());
	marriageForm.cellAtIndex(1).setStringValue(family.getMarriageEvent().getPlace().getFormatString());
	sealingDate.setStringValue(family.getSealingToSpouse().getDateString());
	sealingTemple.setStringValue(family.getSealingToSpouse().getTemple().getCode());
//	divorcedSwitch.setState(family.getMarriageEvent().?NSCell.OnState:NSCell.OffState);
  }

  public void addChild(Object sender) { /* IBAction */
	int selectedRow = Math.max(0, children.numberOfRows() - 1);
	if (children.selectedRow() >= 0) {
	  selectedRow = children.selectedRow();
	}
	Individual newChild = document.createAndInsertNewIndividual();
	undoStack.addObject(newChild);
	document.addIndividual(newChild);
	newChild.setFamilyAsChild(family);
	family.addChildAtPosition(newChild, selectedRow);
	children.reloadData();
	// todo: alternate code for panther here
	children.selectRow(selectedRow, false);
	editChild(children);
  }

  public void removeChild(Object sender) { /* IBAction */
	if (children.selectedRow() >= 0) {
	  family.removeChildAtPosition(children.selectedRow());
	}
  }

  public void cancel(Object sender) { /* IBAction */
//	NSApplication.sharedApplication().stopModal();
  	boolean doCancel = true;
  	if (undoStack.count() > 0) {
  		doCancel = ! MyDocument.confirmCriticalActionMessage("Discard the changes you made to this family?", "Details", "Yes", "No");
  	}
  	if (doCancel) {
		// undo the changes made
  		Enumeration enumeration = undoStack.objectEnumerator();
  		while (enumeration.hasMoreElements()) {
			Individual indiv = (Individual) enumeration.nextElement();
			log.info("Undoing creation of individual:"+indiv.getFullName());
//			document.deleteIndividual(indiv);
		}
  		undoStack.removeAllObjects();
  		NSApplication.sharedApplication().endSheet(window());
  		window().orderOut(this);  		
  	}
  }

  public void editChild(Object sender) { /* IBAction */
	NSTableView tv = (NSTableView) sender;
//       window().close();
	NSApplication.sharedApplication().endSheet(window());
//       document.openIndividualEditSheet(husbandButton);
	NSWindow individualEditWindow = document.individualEditWindow;
	( (NSWindowController) individualEditWindow.delegate()).setDocument(document);
	( (IndividualEditController) individualEditWindow.delegate()).setIndividual( (Individual) family.getChildren().get(
		tv.selectedRow()));
	NSApplication nsapp = NSApplication.sharedApplication();
	nsapp.beginSheet(individualEditWindow, window(), null, null, null);
	//nsapp.runModalForWindow(individualEditWindow);
//       nsapp.endSheet(individualEditWindow);
//       individualEditWindow.orderOut(this);
//       window().display();
  }

  public void editHusband(Object sender) { /* IBAction */
//       window().close();
	NSApplication.sharedApplication().endSheet(window());
//       document.openIndividualEditSheet(husbandButton);
	NSWindow individualEditWindow = document.individualEditWindow;
	( (NSWindowController) individualEditWindow.delegate()).setDocument(document);
	( (IndividualEditController) individualEditWindow.delegate()).setIndividual(family.getFather());
	NSApplication nsapp = NSApplication.sharedApplication();
	nsapp.beginSheet(individualEditWindow, window(), null, null, null);
	try {
		Thread.sleep(15000);
		//nsapp.runModalForWindow(individualEditWindow);
//       nsapp.endSheet(individualEditWindow);
//       individualEditWindow.orderOut(this);
//       window().display();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  public void editWife(Object sender) { /* IBAction */
  	editIndividualInSheet(family.getMother(), document.individualEditWindow, "wife");
  }
  
  public void editIndividualInSheet(Individual individual, NSWindow window, Object contextInfo) {
	( (NSWindowController) window.delegate()).setDocument(document);
	( (IndividualEditController) window.delegate()).setIndividual(family.getMother());
	NSApplication nsapp = NSApplication.sharedApplication();
//	nsapp.beginSheet(window, window(), null, null, null);
//  	setIndividual(individual);
//	NSApplication nsapp = NSApplication.sharedApplication();
	nsapp.endSheet(window());
	nsapp.beginSheet(window(), window, this, new NSSelector("sheetDidEnd::", new Class[] {getClass()}), null);
  }
  
  public void sheetDidEnd(NSWindow sheet, int returnCode, Object contextInfo) {
    NSSystem.log("Called did-end selector");
    log.debug("sheetdidend context:"+contextInfo);
	log.debug("after beginsheet, do we have the right individual?:"+document.getPrimaryIndividual());
//	if ("wife".equals(contextInfo)) {
//	family.setMother(document.getPrimaryIndividual());
//	} else if ("husband")
//    if (returnCode == NSAlertPanel.DefaultReturn) {
//        NSSystem.log("Rows are to be deleted");
//    }
  }  	



  public void save(Object sender) { /* IBAction */
	if (validate()) {
	  if (family instanceof Family.UnknownFamily) {
	  	family = document.createAndInsertNewFamily();
		log.debug("New family, added to document: "+family);
	  }
	  family.getMarriageEvent().setDateString(marriageForm.cellAtIndex(0).stringValue());
	  family.getMarriageEvent().setPlace(new PlaceJDOM(marriageForm.cellAtIndex(1).stringValue()));
	  family.getSealingToSpouse().setDateString(sealingDate.stringValue());
	  family.getSealingToSpouse().setTemple(CocoaUtils.templeForComboBox(sealingTemple));
	  NSApplication.sharedApplication().stopModal();
	  NSApplication.sharedApplication().endSheet(window());
	  window().orderOut(this);
	}
  }

  /**
   * validate
   *
   * @return boolean
   */
  private boolean validate() {
	return true;
  }

  public void toggleDivorced(Object sender) { /* IBAction */
	System.out.println("FamilyEditController.toggleDivorced(sender):"+sender);
//	if (sender instanceof NSCh
  }

  public int numberOfRowsInTableView(NSTableView nsTableView) {
	return family.getChildren().size();
  }

  public Object tableViewObjectValueForLocation(NSTableView nsTableView, NSTableColumn nsTableColumn, int i) {
	Object identifier = nsTableColumn.identifier();
	if (identifier == null) {
	  return null;
	}
	Individual child = (Individual) family.getChildren().get(i);
	if (identifier.equals("num")) {
	  return String.valueOf(i + 1);
	}
	else if (identifier.equals("name")) {
	  return child.getFullName();
	}
	else if (identifier.equals("birthdate")) {
	  return child.getBirthEvent().getDateString();
	}
	else if (identifier.equals("birthplace")) {
	  return child.getBirthEvent().getPlace().getFormatString();
	}
	return null;
  }

}
