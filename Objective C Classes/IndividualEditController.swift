//
//  IndividualEditController.swift
//  MAF
//
//  Created by C.W. Betts on 3/3/15.
//
//

import Cocoa

class IndividualEditController: NSWindowController {
	@IBOutlet weak var afn: NSTextField!
	@IBOutlet weak var baptismDate: NSTextField!
	@IBOutlet weak var baptismTemple: NSComboBox!
	@IBOutlet weak var birthForm: NSForm!
	@IBOutlet weak var burialForm: NSForm!
	@IBOutlet weak var christeningForm: NSForm!
	@IBOutlet weak var deathForm: NSForm!
	@IBOutlet weak var endowmentDate: NSTextField!
	@IBOutlet weak var endowmentTemple: NSComboBox!
	@IBOutlet weak var gender: NSPopUpButton!
	@IBOutlet weak var givenNames: NSTextField!
	@IBOutlet weak var photo: NSImageView!
	@IBOutlet weak var rin: NSTextField!
	@IBOutlet weak var tabView: NSTabView!
	@IBOutlet weak var prefix: NSComboBox!
	@IBOutlet weak var suffix: NSComboBox!
	@IBOutlet weak var surname: NSComboBox!
	@IBOutlet weak var sealingToParentDate: NSTextField!
	@IBOutlet weak var sealingToParentTemple: NSComboBox!
	@IBOutlet weak var notesIndividualName: NSTextField!
	@IBOutlet weak var notesScrollView: NSScrollView!
	
	@IBOutlet weak var attributeTableController: EventTableController!
	@IBOutlet weak var eventTableController: EventTableController!

    override func windowDidLoad() {
        super.windowDidLoad()
    
        // Implement this method to handle any initialization after your window controller's window has been loaded from its nib file.
    }

	@IBAction func cancel(_ sender: AnyObject?) {
		
	}
	
	@IBAction func save(_ sender: AnyObject?) {
		
	}
	
	/*
private Individual individual;

public void windowDidLoad() {
super.windowDidLoad();
log.debug("windowdidload doc=" + document() + " surname=" + surname);
//eventTableController.setup();
attributeTableController.setup();
}

public void cancel(Object sender) { /* IBAction */
//      NSApplication.sharedApplication().stopModal();
//	NSApplication.sharedApplication().endSheet(window());
//	window().orderOut(this);
//	NSApplication.sharedApplication().stopModalWithCode(0);
NSApplication.sharedApplication().endSheet(window());
}

public void showWindow(Object o) {
super.showWindow(o);
log.debug("showWindow o=" + o + " surname=" + surname);
}

public void save(Object sender) { /* IBAction */
try {
if (validate()) {
log.debug("IndividualEditController.save() individual b4:" + individual);
MyDocument myDocument = ( (MyDocument) document());
if (individual instanceof Individual.UnknownIndividual) {
individual = myDocument.createAndInsertNewIndividual();
}
individual.setSurname(surname.stringValue());
individual.setGivenNames(givenNames.stringValue());
individual.setNamePrefix(prefix.stringValue());
individual.setNameSuffix(suffix.stringValue());
log.debug("IndividualEditController.save() gendercode:"+gender.titleOfSelectedItem());
log.debug("IndividualEditController.save() gender:"+Gender.genderWithCode(gender.titleOfSelectedItem()));
individual.setGender(Gender.genderWithCode(gender.titleOfSelectedItem()));
individual.setAFN(afn.stringValue());
individual.getBirthEvent().setDateString(birthForm.cellAtIndex(0).stringValue());
individual.getBirthEvent().setPlace(new PlaceJDOM(birthForm.cellAtIndex(1).stringValue()));
individual.getChristeningEvent().setDateString(christeningForm.cellAtIndex(0).stringValue());
individual.getChristeningEvent().setPlace(new PlaceJDOM(christeningForm.cellAtIndex(1).stringValue()));
individual.getDeathEvent().setDateString(deathForm.cellAtIndex(0).stringValue());
individual.getDeathEvent().setPlace(new PlaceJDOM(deathForm.cellAtIndex(1).stringValue()));
individual.getBurialEvent().setDateString(burialForm.cellAtIndex(0).stringValue());
individual.getBurialEvent().setPlace(new PlaceJDOM(burialForm.cellAtIndex(1).stringValue()));
individual.getLDSBaptism().setDateString(baptismDate.stringValue());
individual.getLDSBaptism().setTemple(CocoaUtils.templeForComboBox(baptismTemple));
individual.getLDSEndowment().setDateString(endowmentDate.stringValue());
individual.getLDSEndowment().setTemple(CocoaUtils.templeForComboBox(endowmentTemple));
individual.getLDSSealingToParents().setDateString(sealingToParentDate.stringValue());
individual.getLDSSealingToParents().setTemple(CocoaUtils.templeForComboBox(sealingToParentTemple));

myDocument.setPrimaryIndividual(individual);
log.debug("IndividualEditController.save() individual aft:" + individual);
myDocument.save();
}
}
catch (Exception e) {
// TODO Auto-generated catch block
log.error("Exception: ", e);
}
//	NSApplication.sharedApplication().stopModalWithCode(0);
NSApplication.sharedApplication().endSheet(window());
}

/**
* @return
*/
private boolean validate() {
if (StringUtils.isEmpty(""+surname.stringValue()+givenNames.stringValue()+suffix.stringValue())) {
MyDocument.showUserErrorMessage("Please enter at least one name.", "For this Individual to be saved, at least one name (Given, Middle, or Surname) must be entered.");
return false;
}
return true;
}

public void setDocument(NSDocument nsDocument) {
super.setDocument(nsDocument);
log.debug("setdocument:" + nsDocument);
log.debug("surname:" + surname);
Individual primaryIndividual = ( (MyDocument) nsDocument).getPrimaryIndividual();
setIndividual(primaryIndividual);
}

public void setIndividual(Individual newIndividual) {
individual = newIndividual;
surname.setStringValue(individual.getSurname());
surname.selectText(surname);
givenNames.setStringValue(individual.getGivenNames());
prefix.setStringValue(individual.getNamePrefix());
suffix.setStringValue(individual.getNameSuffix());
log.debug("IndividualEdit gender=" + gender.selectedItem().title() + " longstr=" +
individual.getGender().getLongString());
gender.selectItemWithTitle(individual.getGender().getLongString());
birthForm.cellAtIndex(0).setStringValue(individual.getBirthEvent().getDateString());
birthForm.cellAtIndex(1).setStringValue(individual.getBirthEvent().getPlace().getFormatString());
christeningForm.cellAtIndex(0).setStringValue(individual.getChristeningEvent().getDateString());
christeningForm.cellAtIndex(1).setStringValue(individual.getChristeningEvent().getPlace().getFormatString());
deathForm.cellAtIndex(0).setStringValue(individual.getDeathEvent().getDateString());
deathForm.cellAtIndex(1).setStringValue(individual.getDeathEvent().getPlace().getFormatString());
burialForm.cellAtIndex(0).setStringValue(individual.getBurialEvent().getDateString());
burialForm.cellAtIndex(1).setStringValue(individual.getBurialEvent().getPlace().getFormatString());
photo.setImage(MultimediaUtils.makeImageFromMultimedia(individual.getPreferredImage()));
baptismDate.setStringValue(individual.getLDSBaptism().getDateString());
baptismTemple.setStringValue(individual.getLDSBaptism().getTemple().getCode());
endowmentDate.setStringValue(individual.getLDSEndowment().getDateString());
endowmentTemple.setStringValue(individual.getLDSEndowment().getTemple().getCode());
sealingToParentDate.setStringValue(individual.getLDSSealingToParents().getDateString());
sealingToParentTemple.setStringValue(individual.getLDSSealingToParents().getTemple().getCode());
afn.setStringValue(individual.getAFN());
rin.setStringValue(String.valueOf(individual.getRin()));
//eventTableController.setEventSource(newIndividual);
attributeTableController.setEventSource(individual);
attributeTableController.setEventType("attribute");
notesIndividualName.setStringValue(individual.getFullName());
notesTextView.textStorage().setAttributedString(new NSAttributedString(individual.getNoteText()));
tabView.selectFirstTabViewItem(this);
}

//  public void openIndividualEditSheet(Object sender) { /* IBAction */
//	NSApplication nsapp = NSApplication.sharedApplication();
//	nsapp.beginSheet(this.window(), this.window().parentWindow(), null, null, null);
//	nsapp.runModalForWindow(this.window());
//	nsapp.endSheet(this.window());
//	this.window().orderOut(this);
//  }

public void windowWillLoad() {
super.windowWillLoad();
log.debug("IEC.windowWillLoad surname=" + surname);
log.debug("IEC.windowwillload doc=" + document());
}
/**
* @return Returns the individual.
*/
public Individual getIndividual() {
return individual;
}
}
*/
}
