//
//  FamilyEditController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Apr 06 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.jdom.PlaceJDOM;
import com.redbugz.macpaf.test.TestFamily;

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

  private Family family = new TestFamily();
  private MyDocument document = null;

  public void setDocument(NSDocument document) {
	super.setDocument(document);
	System.out.println("FamilyEditController.setDocument(document):"+document.fileName());
	this.document = (MyDocument) document;
	setFamily( ( (MyDocument) document).getPrimaryIndividual().getFamilyAsSpouse());
  }

  public void setFamily(Family family) {
	System.out.println("FamilyEditController.setFamily(family):"+family);
	this.family = family;
	husbandButton.setTitle(family.getFather().getFullName());
	wifeButton.setTitle(family.getMother().getFullName());
	marriageForm.cellAtIndex(0).setStringValue(family.getMarriageEvent().getDateString());
	marriageForm.cellAtIndex(1).setStringValue(family.getMarriageEvent().getPlace().getFormatString());
	sealingDate.setStringValue(family.getSealingToSpouse().getDateString());
	sealingTemple.setStringValue(family.getSealingToSpouse().getTemple().getCode());
  }

  public void addChild(Object sender) { /* IBAction */
	int selectedRow = Math.max(0, children.numberOfRows() - 1);
	if (children.selectedRow() >= 0) {
	  selectedRow = children.selectedRow();
	}
	Individual newChild = document.createNewIndividual();
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
	NSApplication.sharedApplication().stopModal();
	NSApplication.sharedApplication().endSheet(window());
	window().orderOut(this);
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
	//nsapp.runModalForWindow(individualEditWindow);
//       nsapp.endSheet(individualEditWindow);
//       individualEditWindow.orderOut(this);
//       window().display();
  }

  public void editWife(Object sender) { /* IBAction */
//       window().close();
	NSApplication.sharedApplication().endSheet(window());
//       document.openIndividualEditSheet(husbandButton);
	NSWindow individualEditWindow = document.individualEditWindow;
	( (NSWindowController) individualEditWindow.delegate()).setDocument(document);
	( (IndividualEditController) individualEditWindow.delegate()).setIndividual(family.getMother());
	NSApplication nsapp = NSApplication.sharedApplication();
	nsapp.beginSheet(individualEditWindow, window(), null, null, null);
	//nsapp.runModalForWindow(individualEditWindow);
//       nsapp.endSheet(individualEditWindow);
//       individualEditWindow.orderOut(this);
//       window().display();
  }

  public void save(Object sender) { /* IBAction */
	if (validate()) {
	  if (family.getId() == null || family.getId().length() == 0) {
		log.debug("New family, adding to document: "+family);
		((MyDocument)document()).addFamily(family);
	  }
	  family.getSealingToSpouse().setDateString(marriageForm.cellAtIndex(0).stringValue());
	  family.getSealingToSpouse().setPlace(new PlaceJDOM(marriageForm.cellAtIndex(1).stringValue()));
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
