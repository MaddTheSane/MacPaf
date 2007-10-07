//
//  FamilyEditController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Apr 06 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.util.*;

import org.apache.log4j.*;
import org.jdom.Element;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.CocoaUtils;
import com.redbugz.macpaf.EventJDOM;
import com.redbugz.macpaf.FamilyJDOM;
import com.redbugz.macpaf.OrdinanceJDOM;
import com.redbugz.macpaf.PlaceJDOM;
import com.redbugz.macpaf.ValidationException;
import com.redbugz.maf.*;
import com.redbugz.maf.Event.UnknownEvent;
import com.redbugz.maf.jdom.*;
import com.redbugz.maf.util.*;
import com.redbugz.maf.validation.*;

public class FamilyEditController extends NSWindowController {
  private static final Logger log = Logger.getLogger(FamilyEditController.class);

  public NSTableView children; /* IBOutlet */
  public NSButton husbandButton; /* IBOutlet */
  public NSForm marriageForm; /* IBOutlet */
  public NSTextField sealingDate; /* IBOutlet */
  public NSComboBox sealingTemple; /* IBOutlet */
  public NSButton wifeButton; /* IBOutlet */
  public NSButton saveButton; /* IBOutlet */
  public EventTableController eventTableController; /* IBOutlet */
  public NSTabView tabView; /* IBOutlet */

  private Family family = Family.UNKNOWN_FAMILY;
  private MyDocument document = null;
  
  private NSMutableArray undoStack = new NSMutableArray();

private NSSelector editChildSelector = new NSSelector("editChild", new Class[] {Object.class});

private static final String EDIT_HUSBAND_KEY = "EditHusband";

private static final String EDIT_WIFE_KEY = "EditWife";

private static final String EDIT_CHILD_KEY = "EditChild";

  public void setDocument(NSDocument document) {
	super.setDocument(document);
	log.debug("FamilyEditController.setDocument(document):"+document.fileName());
	this.document = (MyDocument) document;
	children.registerForDraggedTypes(DRAG_TYPES_ARRAY);
	children.setTarget(this);
	children.setDoubleAction(editChildSelector );
	//setFamily( ( (MyDocument) document).getPrimaryIndividual().getFamilyAsSpouse());
  }

  public void setFamily(Family newFamily) {
	log.debug("FamilyEditController.setFamily(family):"+newFamily);
	  family = newFamily;
	if (family instanceof Family.UnknownFamily) {
		saveButton.setTitle("Add Family");
		family = document.createAndInsertNewFamily();
		undoStack.addObject(family);
		if (!(document.getPrimaryIndividual() instanceof Individual.UnknownIndividual) && Gender.UNKNOWN.equals(document.getPrimaryIndividual().getGender())) {
			// unknown gender, prompt user
			int response = NSAlertPanel.runAlert(document.getPrimaryIndividual().getFullName() + " does not have a gender specified. Should this person be added to this family as the Husband or Wife?", "This person must be added to a family as either Husband or Wife. By clicking Husband, the gender of this person will be set to Male and they will be added as the Husband on this family. By clicking Wife, their gender will be set to Female and they will be added as a Wife. If you are not sure what gender this person should be, you may click Cancel to return to the main screen without making any changes.", "Husband", "Cancel", "Wife");
			switch (response) {
			case NSAlertPanel.DefaultReturn:
				document.getPrimaryIndividual().setGender(Gender.MALE);
				break;
			case NSAlertPanel.OtherReturn:
				document.getPrimaryIndividual().setGender(Gender.FEMALE);
				break;
			default:
				undoChanges();
				throw MyDocument.USER_CANCELLED_OPERATION_EXCEPTION;
			}			
		}
		if (Gender.MALE.equals(document.getPrimaryIndividual().getGender())) {
			family.setFather(document.getPrimaryIndividual());
		} else if (Gender.FEMALE.equals(document.getPrimaryIndividual().getGender())) {
			family.setMother(document.getPrimaryIndividual());
		}
	} else {
		saveButton.setTitle("Save Family");
	}
	husbandButton.setTitle(family.getFather().getFullName());
	wifeButton.setTitle(family.getMother().getFullName());
	marriageForm.cellAtIndex(0).setStringValue(family.getPreferredMarriageEvent().getDateString());
	marriageForm.selectTextAtIndex(0);
	marriageForm.cellAtIndex(1).setStringValue(family.getPreferredMarriageEvent().getPlace().getFormatString());
	sealingDate.setStringValue(family.getPreferredSealingToSpouse().getDateString());
	sealingTemple.setStringValue(family.getPreferredSealingToSpouse().getTemple().getCode());
	children.reloadData();
	undoStack.removeAllObjects();
	eventTableController.setEventSource(family);
	tabView.selectFirstTabViewItem(this);
  }

  public void setFamilyAsChild(Family newFamily) {
		log.debug("FamilyEditController.setFamilyAsChild(family):"+newFamily);
		  family = newFamily;
		if (family instanceof Family.UnknownFamily) {
			saveButton.setTitle("Add Family");
			family = document.createAndInsertNewFamily();
			undoStack.addObject(family);
			Individual primaryIndividual = document.getPrimaryIndividual();
			family.addChild(primaryIndividual);
			primaryIndividual.setFamilyAsChild(family);
		} else {
			saveButton.setTitle("Save Family");
		}
		husbandButton.setTitle(family.getFather().getFullName());
		wifeButton.setTitle(family.getMother().getFullName());
		Event preferredMarriageEvent = family.getPreferredMarriageEvent();
//		if (preferredMarriageEvent != null) {
		marriageForm.cellAtIndex(0).setStringValue(preferredMarriageEvent.getDateString());
		marriageForm.selectTextAtIndex(0);
		marriageForm.cellAtIndex(1).setStringValue(preferredMarriageEvent.getPlace().getFormatString());
//		}
		Ordinance sealingToSpouse = family.getPreferredSealingToSpouse();
		if (preferredMarriageEvent != null) {
		sealingDate.setStringValue(sealingToSpouse.getDateString());
		sealingTemple.setStringValue(sealingToSpouse.getTemple().getCode());
		}		children.reloadData();
		undoStack.removeAllObjects();
		eventTableController.setEventSource(family);
		tabView.selectFirstTabViewItem(this);
	  }

  public void addChild(Object sender) { /* IBAction */
	int selectedRow = Math.max(0, children.numberOfRows() - 1);
	if (children.selectedRow() >= 0) {
	  selectedRow = children.selectedRow();
	}
	Individual newChild = document.createAndInsertNewIndividual();
	undoStack.addObject(newChild);
	newChild.setFamilyAsChild(family);
	  if (children.numberOfRows() == 0) {
		  selectedRow = -1;
	  }
	newChild.setSurname(family.getFather().getSurname());
	family.addChildAtPosition(newChild, selectedRow+1);
	children.reloadData();
	// todo: alternate code for panther here since selectRow is deprecated
	children.selectRow(selectedRow+1, false);
	editChild(children);
  }

  public void removeChild(Object sender) { /* IBAction */
	  if (children.selectedRow() >= 0) {
		  family.removeChildAtPosition(children.selectedRow());
	  }
	  children.reloadData();
  }
  
  public void moveChildUp(Object sender) { /* IBAction */
	  int selectedRow = children.selectedRow();
	  if (selectedRow >= 0) {
		  Individual child = (Individual) family.getChildren().get(selectedRow);
		  family.removeChildAtPosition(selectedRow);
		  family.addChildAtPosition(child, selectedRow-1);
	  }
	  children.reloadData();
  }
  
  public void moveChildDown(Object sender) { /* IBAction */
	  int selectedRow = children.selectedRow();
	  if (selectedRow >= 0) {
		  Individual child = (Individual) family.getChildren().get(selectedRow);
		  family.removeChildAtPosition(selectedRow);
		  family.addChildAtPosition(child, selectedRow+1);
	  }
	  children.reloadData();
  }
  
  public void cancel(Object sender) { /* IBAction */
//	NSApplication.sharedApplication().stopModal();
  	boolean doCancel = true;
  	if (undoStack.count() > 0) {
  		doCancel = MyDocument.confirmCriticalActionMessage("Discard the changes you made to this family?", "", "Yes", "No");
  	}
  	if (doCancel) {
		// undo the changes made
  		undoChanges();  		
  	}
  }

private void undoChanges() {
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

  public void editChild(Object sender) { /* IBAction */
	NSTableView tv = (NSTableView) sender;
//       window().close();
	NSApplication.sharedApplication().endSheet(window());
//       document.openIndividualEditSheet(husbandButton);
	NSWindow individualEditWindow = document.individualEditWindow;
	( (NSWindowController) individualEditWindow.delegate()).setDocument(document);
	Individual childToEdit = (Individual) family.getChildren().get(	tv.selectedRow());
	( (IndividualEditController) individualEditWindow.delegate()).setIndividual( childToEdit);
	NSApplication.sharedApplication().beginSheet(individualEditWindow, window(), this, CocoaUtils.SHEET_DID_END_SELECTOR, new NSDictionary(childToEdit, EDIT_CHILD_KEY));
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
	NSApplication.sharedApplication().beginSheet(individualEditWindow, window(), this, CocoaUtils.SHEET_DID_END_SELECTOR, new NSDictionary(father, EDIT_HUSBAND_KEY));
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
	NSApplication.sharedApplication().beginSheet(individualEditWindow, window(), this, CocoaUtils.SHEET_DID_END_SELECTOR, new NSDictionary(family.getMother(), EDIT_WIFE_KEY));
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
				wifeButton.setTitle(individual.getFullName());
	    	} else if (EDIT_HUSBAND_KEY.equals(key)) {
	    		family.setFather(individual);
	    		husbandButton.setTitle(individual.getFullName());
	    	} else if (key.startsWith(EDIT_CHILD_KEY)) {
//	    		family.getChildren().get()
	    		children.reloadData();
	    	}
    }
	sheet.orderOut(this);
  }  	

  public void save(Object sender) { /* IBAction */
	try {
		if (validate()) {
		  if (family instanceof Family.UnknownFamily) {
		  	family = document.createAndInsertNewFamily();
			log.debug("New family, added to document: "+family);
		  }
		  
		  Event preferredMarriageEvent = family.getPreferredMarriageEvent();
		  if (preferredMarriageEvent instanceof UnknownEvent) {
			  preferredMarriageEvent = EventJDOM.createMarriageEventInstance();
			  ((FamilyJDOM)family).getElement().addContent(((EventJDOM)preferredMarriageEvent).getElement());
		  }
		  preferredMarriageEvent.setDateString(marriageForm.cellAtIndex(0).stringValue());
		  preferredMarriageEvent.setPlace(new PlaceJDOM(marriageForm.cellAtIndex(1).stringValue()));
		  
		  Ordinance sealingToSpouse = family.getPreferredSealingToSpouse();
		  if (sealingToSpouse instanceof UnknownEvent) {
			  sealingToSpouse = OrdinanceJDOM.createSealingToSpouseInstance();
			  ((FamilyJDOM)family).getElement().addContent(((OrdinanceJDOM)sealingToSpouse).getElement());			  
		  }
		  sealingToSpouse.setDateString(sealingDate.stringValue());
		  sealingToSpouse.setTemple(CocoaUtils.templeForComboBox(sealingTemple));
		  
		  undoStack.removeAllObjects();

		  NSApplication.sharedApplication().endSheet(window());
		  window().orderOut(this);
		}
	} catch (ValidationException e) {
		e.printStackTrace();
		MyDocument.showUserErrorMessage(e.getMessage(), e.getDetails());
	}
  }

  /**
   * validate
   *
   * @return boolean
   */
  private boolean validate() {
	if (family.getFather() instanceof Individual.UnknownIndividual && family.getMother() instanceof Individual.UnknownIndividual
			&& family.getChildren().size() == 0) {
		throw new ValidationException("A family with no members cannot be saved.","You need to add either a father, mother, or child to this family before you can save the changes to this family.");
	}
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
  
  //  tableview drag reordering api	  
	private static final String MACPAF_TABLE_REORDER_DRAG_TYPE = "MacPAFTableReorderDragType";
	private static final NSArray DRAG_TYPES_ARRAY = new NSArray(new Object[] {MACPAF_TABLE_REORDER_DRAG_TYPE});
 
  public boolean tableViewWriteRowsToPasteboard(NSTableView aTable, NSArray rows, NSPasteboard pboard ) {
	  System.err.println("DNDJavaController.tableViewWriteRowsToPasteboard():"+rows+":"+pboard.types());
	   	// declare our own pasteboard types
	      NSArray typesArray = new NSArray(DRAG_TYPES_ARRAY);
	      pboard.declareTypes(typesArray, this);
	  	
	      // add rows array for local move
	      pboard.setPropertyListForType(rows, MACPAF_TABLE_REORDER_DRAG_TYPE);
	  	
	      return true;
	  }


	   public int tableViewValidateDrop(NSTableView tv, NSDraggingInfo info, int row, int operation) {
	  		  System.err.println("DNDJavaController.tableViewValidateDrop():"+info+" row:"+row+" op:"+operation);
	      
	      int dragOp = NSDraggingInfo.DragOperationNone;
	      int sourceRow = Integer.parseInt(((NSArray) info.draggingPasteboard().propertyListForType(MACPAF_TABLE_REORDER_DRAG_TYPE)).lastObject().toString());
	      // if drag source is self, it's a move
	      if (tv.equals(info.draggingSource()) && row != sourceRow && row != sourceRow+1)
	  	{
	  		dragOp =  NSDraggingInfo.DragOperationMove;
	      }
	      // we want to put the object at, not over,
	      // the current row (contrast NSTableViewDropOn) 
	      tv.setDropRowAndDropOperation(row, NSTableView.NSTableViewDropAbove);
	  	
	      return dragOp;
	  }



	  public boolean tableViewAcceptDrop(NSTableView tableView, NSDraggingInfo info, int row, int operation) {
	  		  System.err.println("DNDJavaController.tableViewAcceptDrop():"+info+": row"+row+" op:"+operation);
	  		  System.out.println("dragging pboard:"+info.draggingPasteboard().types());
	      if (row < 0)
	  	{
	  		row = 0;
	  	}
	      
	      // if drag source is self, it's a move
	      if (tableView.equals(info.draggingSource()))
	      {
//	  		NSArray *rows = [[info draggingPasteboard] propertyListForType:MovedRowsType];
	    	  NSArray rows = (NSArray) info.draggingPasteboard().propertyListForType(info.draggingPasteboard().availableTypeFromArray(DRAG_TYPES_ARRAY));
	    	  log.debug(rows);
	    	  int fromRow;
			try {
				fromRow = Integer.parseInt(rows.lastObject().toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return false;
			}
	    	  log.debug("dragging operation will move index "+rows.lastObject()+" to index "+row);
	    	  Individual child = (Individual) family.getChildren().get(fromRow);
	    	  family.reorderChildToPosition(child, row);
//	    	  family.addChildAtPosition(child, row);
//	  		// set selected rows to those that were just moved
	    	  tableView.reloadData();
	    	  tableView.selectRow(row, false);
	  		return true;
	      }
	  	
	      return false;
	  }

	  
	  
	  
  
  public boolean tableViewWriteRowsToPasteboard2(NSTableView aTable, NSArray anArray, NSPasteboard aPastboard ) {
	  System.err.println("EventTableController.tableViewWriteRowsToPasteboard():"+anArray+":"+aPastboard.types());
//	  When I click and drag a row in the table, the strings from that row move around with the mouse arrow.
	  NSArray newArray = new NSArray();
	  newArray = newArray.arrayByAddingObject("DragData");
	  
	  String dataString = new String("DragData");
//	  String[][] defaultMatrix;
//	  defaultMatrix = getMatrix(); //returns a String[][] that holds the strings that are displayed in the table
//	  for (int i=0; i<numberOfRows; i++) {
//		  for (int j=0; j<numberOfColumns; j++) {
//			  String f = defaultMatrix[i][j];
//			  dataString = dataString + f; //put all of the strings together
//		  }
//	  }
	
	  
	  aPastboard.declareTypes(newArray,null);
	  aPastboard.setStringForType(dataString, "DragData");
	  aPastboard.setDataForType(new NSData(new byte[] {'a'}),"DragData");
	  return true;
  }


public void windowDidLoad() {
	// TODO Auto-generated method stub
	System.out.println("FamilyEditController.windowDidLoad()");
	super.windowDidLoad();
	try {
		eventTableController.setup();
	} catch (RuntimeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
