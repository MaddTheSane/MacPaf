//
//  IndividualEditController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 30 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import org.apache.log4j.Category;
import org.apache.xalan.processor.StopParseException;

import com.apple.cocoa.application.*;
import com.apple.cocoa.application.NSComboBox.*;
import com.apple.cocoa.foundation.NSData;
import com.apple.cocoa.foundation.NSSelector;
import com.apple.cocoa.foundation.NSSystem;
import com.redbugz.macpaf.Gender;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.jdom.PlaceJDOM;
import com.redbugz.macpaf.util.CocoaUtils;
import com.redbugz.macpaf.util.MultimediaUtils;
import com.redbugz.macpaf.util.StringUtils;

public class IndividualEditController extends NSWindowController {
  private static final Category log = Category.getInstance(IndividualEditController.class.getName());

  public NSTextField baptismDate; /* IBOutlet */
  public NSComboBox baptismTemple; /* IBOutlet */
  public NSForm birthForm; /* IBOutlet */
  public NSForm burialForm; /* IBOutlet */
  public NSForm christeningForm; /* IBOutlet */
  public NSForm deathForm; /* IBOutlet */
  public NSTextField endowmentDate; /* IBOutlet */
  public NSComboBox endowmentTemple; /* IBOutlet */
  public NSPopUpButton gender; /* IBOutlet */
  public NSTextField givenNames; /* IBOutlet */
  public NSImageView photo; /* IBOutlet */
  public NSTextField sealingToParentDate; /* IBOutlet */
  public NSComboBox sealingToParentTemple; /* IBOutlet */
  public NSComboBox surname; /* IBOutlet */
  public NSComboBox prefix; /* IBOutlet */
  public NSComboBox suffix; /* IBOutlet */
  public NSTextField afn; /* IBOutlet */
  public NSTextField rin; /* IBOutlet */
  private Individual individual;

  public void windowDidLoad() {
	super.windowDidLoad();
	log.debug("windowdidload doc=" + document() + " surname=" + surname);
  }

  public void cancel(Object sender) { /* IBAction */
//      NSApplication.sharedApplication().stopModal();
//	NSApplication.sharedApplication().endSheet(window());
//	window().orderOut(this);
	NSApplication.sharedApplication().stopModalWithCode(0);
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
		log.debug(gender.titleOfSelectedItem());
		log.debug(Gender.genderWithCode(gender.titleOfSelectedItem()));
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
	NSApplication.sharedApplication().stopModalWithCode(0);
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
	Individual individual = ( (MyDocument) nsDocument).getPrimaryIndividual();
	setIndividual(individual);
  }

  public void setIndividual(Individual individual) {
	this.individual = individual;
	surname.setStringValue(individual.getSurname());
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
