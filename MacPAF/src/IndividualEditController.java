//
//  IndividualEditController.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 30 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

import com.redbugz.macpaf.jdom.PlaceJDOM;
import com.redbugz.macpaf.jdom.TempleJDOM;

import com.apple.cocoa.application.NSApplication;
import com.apple.cocoa.application.NSComboBox;
import com.apple.cocoa.application.NSDocument;
import com.apple.cocoa.application.NSForm;
import com.apple.cocoa.application.NSImage;
import com.apple.cocoa.application.NSImageView;
import com.apple.cocoa.application.NSPopUpButton;
import com.apple.cocoa.application.NSTextField;
import com.apple.cocoa.application.NSWindowController;
import com.apple.cocoa.application.NSComboBox.DataSource;
import com.redbugz.macpaf.*;

public class IndividualEditController extends NSWindowController {

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
      System.out.println("windowdidload doc="+document()+" surname="+surname);
   }

   public void cancel(Object sender) { /* IBAction */
//      NSApplication.sharedApplication().stopModal();
      NSApplication.sharedApplication().endSheet(window());
      window().orderOut(this);
    }

   public void showWindow(Object o) {
      super.showWindow(o);
      System.out.println("showWindow o="+o+" surname="+surname);
   }

    public void save(Object sender) { /* IBAction */
    	try {
			if (validate()) {
				System.out.println("IndividualEditController.save() individual b4:"+individual);
				individual.setSurname(surname.stringValue());
				individual.setGivenNames(givenNames.stringValue());
				individual.setNamePrefix(prefix.stringValue());
				individual.setNameSuffix(suffix.stringValue());
				System.out.println(gender.titleOfSelectedItem());
				System.out.println(Gender.genderWithCode(gender.titleOfSelectedItem()));
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
				individual.getLDSBaptism().setTemple(templeForComboBox(baptismTemple));
				individual.getLDSEndowment().setDateString(endowmentDate.stringValue());
				individual.getLDSEndowment().setTemple(templeForComboBox(endowmentTemple));
				individual.getLDSSealingToParents().setDateString(sealingToParentDate.stringValue());
				individual.getLDSSealingToParents().setTemple(templeForComboBox(sealingToParentTemple));
				
				MyDocument document = ((MyDocument)document());
				document.setPrimaryIndividual(individual);
				System.out.println("IndividualEditController.save() individual aft:"+individual);
				document.save();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      cancel(sender);
    }

   /**
	 * @param comboBox
	 * @return
	 */
	private Temple templeForComboBox(NSComboBox comboBox) {
		TempleJDOM temple = new TempleJDOM();
		NSComboBox.DataSource dataSource = (DataSource) comboBox.dataSource();
		System.out.println("IndividualEditController.templeForComboBox() stringValue:"+comboBox.stringValue());
		int index = dataSource.comboBoxIndexOfItem(comboBox, comboBox.stringValue());
		System.out.println("IndividualEditController.templeForComboBox() index:"+index);
		if (index >= 0 && index < dataSource.numberOfItemsInComboBox(comboBox)) {
			Temple selection = (Temple) dataSource.comboBoxObjectValueForItemAtIndex(comboBox, index);
			System.out.println("IndividualEditController.templeForComboBox() selection:"+selection);
			temple = new TempleJDOM(selection);
		} else {
			System.out.println("IndividualEditController.templeForComboBox() setCode stringValue:"+comboBox.stringValue());
			temple.setCode(comboBox.stringValue());
		}
//		temple = new TempleJDOM(TempleList.templeWithCode(((Temple)sealingToParentTemple.objectValueOfSelectedItem()).getCode()));
		System.out.println("IndividualEditController.templeForComboBox() returning temple:"+temple);
		return temple;
	}

/**
	 * @return
	 */
	private boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

public void setDocument(NSDocument nsDocument) {
      super.setDocument(nsDocument);
      System.out.println("setdocument:"+nsDocument);
      System.out.println("surname:"+surname);
      Individual individual = ((MyDocument)nsDocument).getPrimaryIndividual();
      setIndividual(individual);
   }

   public void setIndividual(Individual individual) {
   	this.individual = individual;
      surname.setStringValue(individual.getSurname());
      givenNames.setStringValue(individual.getGivenNames());
      prefix.setStringValue(individual.getNamePrefix());
      suffix.setStringValue(individual.getNameSuffix());
      System.out.println("IndividualEdit gender="+gender.selectedItem().title()+" longstr="+individual.getGender().getLongString());
      gender.selectItemWithTitle(individual.getGender().getLongString());
      birthForm.cellAtIndex(0).setStringValue(individual.getBirthEvent().getDateString());
      birthForm.cellAtIndex(1).setStringValue(individual.getBirthEvent().getPlace().getFormatString());
      christeningForm.cellAtIndex(0).setStringValue(individual.getChristeningEvent().getDateString());
      christeningForm.cellAtIndex(1).setStringValue(individual.getChristeningEvent().getPlace().getFormatString());
      deathForm.cellAtIndex(0).setStringValue(individual.getDeathEvent().getDateString());
      deathForm.cellAtIndex(1).setStringValue(individual.getDeathEvent().getPlace().getFormatString());
      burialForm.cellAtIndex(0).setStringValue(individual.getBurialEvent().getDateString());
      burialForm.cellAtIndex(1).setStringValue(individual.getBurialEvent().getPlace().getFormatString());
      photo.setImage(new NSImage(individual.getImagePath()));
      baptismDate.setStringValue(individual.getLDSBaptism().getDateString());
      baptismTemple.setStringValue(individual.getLDSBaptism().getTemple().getCode());
      endowmentDate.setStringValue(individual.getLDSEndowment().getDateString());
      endowmentTemple.setStringValue(individual.getLDSEndowment().getTemple().getCode());
      sealingToParentDate.setStringValue(individual.getLDSSealingToParents().getDateString());
      sealingToParentTemple.setStringValue(individual.getLDSSealingToParents().getTemple().getCode());
      afn.setStringValue(individual.getAFN());
      rin.setStringValue(String.valueOf(individual.getRin()));
   }

   public void openIndividualEditSheet(Object sender) { /* IBAction */
      NSApplication nsapp = NSApplication.sharedApplication();
      nsapp.beginSheet(this.window(), this.window().parentWindow(), null, null, null);
      nsapp.runModalForWindow(this.window());
      nsapp.endSheet(this.window());
      this.window().orderOut(this);
   }

   public void windowWillLoad() {
      super.windowWillLoad();
      System.out.println("windowWillLoad surname="+surname);
      System.out.println("windowwillload doc="+document());
   }
}
