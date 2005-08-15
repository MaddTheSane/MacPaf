//
//  FamilyEditController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Apr 06 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.apple.cocoa.application.NSApplication;
import com.apple.cocoa.application.NSButton;
import com.apple.cocoa.application.NSComboBox;
import com.apple.cocoa.application.NSDocument;
import com.apple.cocoa.application.NSForm;
import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.application.NSTextField;
import com.apple.cocoa.application.NSWindow;
import com.apple.cocoa.application.NSWindowController;
import com.apple.cocoa.foundation.NSDictionary;
import com.apple.cocoa.foundation.NSMutableArray;
import com.apple.cocoa.foundation.NSSelector;
import com.apple.cocoa.foundation.NSSystem;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Gender;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.jdom.PlaceJDOM;
import com.redbugz.macpaf.util.CocoaUtils;

public class FamilyEditController extends NSWindowController {
  private static final Logger log = Logger.getLogger(FamilyEditController.class);

  public NSTableView children; /* IBOutlet */
  public NSButton husbandButton; /* IBOutlet */
  public NSForm marriageForm; /* IBOutlet */
  public NSTextField sealingDate; /* IBOutlet */
  public NSComboBox sealingTemple; /* IBOutlet */
  public NSButton wifeButton; /* IBOutlet */
  public NSButton saveButton; /* IBOutlet */

  private Family family = Family.UNKNOWN_FAMILY;
  private MyDocument document = null;
  
  private NSMutableArray undoStack = new NSMutableArray();

private static final String EDIT_HUSBAND_KEY = "EditHusband";

private static final String EDIT_WIFE_KEY = "EditWife";

private static final String EDIT_CHILD_KEY = "EditChild";

  public void setDocument(NSDocument document) {
	super.setDocument(document);
	System.out.println("FamilyEditController.setDocument(document):"+document.fileName());
	this.document = (MyDocument) document;
	//setFamily( ( (MyDocument) document).getPrimaryIndividual().getFamilyAsSpouse());
  }

  public void setFamily(Family family) {
	System.out.println("FamilyEditController.setFamily(family):"+family);
	if (family instanceof Family.UnknownFamily) {
		saveButton.setTitle("Add Family");
		family = document.createAndInsertNewFamily();
		undoStack.addObject(family);
		if (Gender.MALE.equals(document.getPrimaryIndividual().getGender())) {
			family.setFather(document.getPrimaryIndividual());
		} else if (Gender.FEMALE.equals(document.getPrimaryIndividual().getGender())) {
			family.setMother(document.getPrimaryIndividual());
		} else {
			// unknown gender, prompt user
		}
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
  }

  public void addChild(Object sender) { /* IBAction */
	int selectedRow = Math.max(0, children.numberOfRows() - 1);
	if (children.selectedRow() >= 0) {
	  selectedRow = children.selectedRow();
	}
	Individual newChild = document.createAndInsertNewIndividual();
	undoStack.addObject(newChild);
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
  		doCancel = MyDocument.confirmCriticalActionMessage("Discard the changes you made to this family?", "Details", "Yes", "No");
  	}
  	if (doCancel) {
		// undo the changes made
  		Enumeration enumeration = undoStack.objectEnumerator();
  		while (enumeration.hasMoreElements()) {
  			Object objectToUndo = enumeration.nextElement();
  			if (objectToUndo instanceof Individual) {
				Individual indiv = (Individual) objectToUndo;
  				log.info("Undoing creation of individual:"+indiv.getFullName());
  				document.doc.removeIndividual(indiv);  				
  			}
  			if (objectToUndo instanceof Family) {
				Family familyToUndo = (Family) objectToUndo;
				log.info("Undoing creation of family: "+family.toString());
				document.doc.removeFamily(familyToUndo);
			}
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
	Individual childToEdit = (Individual) family.getChildren().get(
		tv.selectedRow());
	( (IndividualEditController) individualEditWindow.delegate()).setIndividual( childToEdit);
	NSApplication.sharedApplication().beginSheet(individualEditWindow, window(), this, CocoaUtils.didEndSelector(), new NSDictionary(childToEdit, EDIT_CHILD_KEY));
	// sheet up here, control will pass to didEndSelector after closed
  }

  public void editHusband(Object sender) { /* IBAction */
	NSApplication.sharedApplication().endSheet(window());
	NSWindow individualEditWindow = document.individualEditWindow;
	( (NSWindowController) individualEditWindow.delegate()).setDocument(document);
	Individual father = family.getFather();
	if (father instanceof Individual.UnknownIndividual) {
		father = document.createAndInsertNewIndividual();
		// todo: decide if undoing invidual creation here is appropriate, since it's different from FR 2.3.1
		undoStack.addObject(father);
		
		father.setGender(Gender.MALE);
		father.setFamilyAsSpouse(family);
		family.setFather(father);
	}
	( (IndividualEditController) individualEditWindow.delegate()).setIndividual(father);
	NSApplication.sharedApplication().beginSheet(individualEditWindow, window(), this, CocoaUtils.didEndSelector(), new NSDictionary(father, EDIT_HUSBAND_KEY));
	// sheet up here, control will pass to didEndSelector after closed
  }
  
  public void editWife(Object sender) { /* IBAction */
	NSApplication.sharedApplication().endSheet(window());
	NSWindow individualEditWindow = document.individualEditWindow;
	( (NSWindowController) individualEditWindow.delegate()).setDocument(document);
	Individual mother = family.getMother();
	if (mother instanceof Individual.UnknownIndividual) {
		mother = document.createAndInsertNewIndividual();
		// todo: decide if undoing individual creation here is appropriate, since it's different from FR 2.3.1
		undoStack.addObject(mother);
		
		mother.setGender(Gender.FEMALE);
		mother.setFamilyAsSpouse(family);
		family.setMother(mother);
	}
	( (IndividualEditController) individualEditWindow.delegate()).setIndividual(family.getMother());
	NSApplication.sharedApplication().beginSheet(individualEditWindow, window(), this, CocoaUtils.didEndSelector(), new NSDictionary(family.getMother(), EDIT_WIFE_KEY));
	// sheet up here, control will pass to didEndSelector after closed
  }
  
//  public void editIndividualInSheet(Individual individual, NSWindow window, Object contextInfo) {
//	( (NSWindowController) window.delegate()).setDocument(document);
//	( (IndividualEditController) window.delegate()).setIndividual(family.getMother());
//	NSApplication nsapp = NSApplication.sharedApplication();
////	nsapp.beginSheet(window, window(), null, null, null);
////  	setIndividual(individual);
////	NSApplication nsapp = NSApplication.sharedApplication();
//	nsapp.endSheet(window());
//	nsapp.beginSheet(window(), window, this, new NSSelector("sheetDidEnd::", new Class[] {getClass()}), null);
//  }
  
  public void sheetDidEnd(NSWindow sheet, int returnCode, Object contextInfo) {
    NSSystem.log("Called did-end selector");
    log.debug("sheetdidend context:"+contextInfo);
    if (contextInfo != null) {
	    	NSDictionary info = (NSDictionary)contextInfo;
	    	String key = (String) info.allKeys().lastObject();
		Individual individual = (Individual) info.allValues().lastObject();
	    	log.debug("after sheetdidend, do we have the right individual?:"+individual.getFullName());
	    	if (EDIT_WIFE_KEY.equals(key)) {
				family.setMother(individual);
	    	} else if (EDIT_HUSBAND_KEY.equals(key)) {
	    		family.setFather(individual);
	    	} else if (EDIT_CHILD_KEY.equals(key)) {
//	    		family.set
	    	}
    }
	sheet.orderOut(this);
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
	  
	  undoStack.removeAllObjects();
//	  NSApplication.sharedApplication().stopModal();
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
