/* PedigreeViewController */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Gender;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.jdom.IndividualJDOM;
import com.redbugz.macpaf.util.CocoaUtils;
import com.redbugz.macpaf.util.CocoaUtils.KeyValueComparator;

public class PedigreeViewController extends NSObject {
	private static final KeyValueComparator TAG_COMPARATOR = new CocoaUtils.KeyValueComparator("tag");


	private static final Logger log = Logger.getLogger(PedigreeViewController.class);


	private static final NSSelector SELECT_RELATED_INDIVIDUAL_SELECTOR = new NSSelector("selectRelatedIndividual", new Class[] {Object.class});
	private static final NSSelector SELECT_INDIVIDUAL_SELECTOR = new NSSelector("selectIndividual", new Class[] {Object.class});
	
	
//	public NSMatrix gggrandparentsMatrix; /* IBOutlet */
	// since this NSMatrix overlaps with others, clicking doesn't work, so I have to replace it with individual buttons
//	public NSMatrix grandparentsMatrix; /* IBOutlet */
//	public NSButton paternalGrandfatherButton; /* IBOutlet */
//	public NSButton paternalGrandmotherButton; /* IBOutlet */
//	public NSButton maternalGrandfatherButton; /* IBOutlet */
//	public NSButton maternalGrandmotherButton; /* IBOutlet */
//	public NSBox greatgrandparentsMatrix; /* IBOutlet */
//	public List parentsMatrix; /* IBOutlet */
	public NSButton primaryIndividualButton; /* IBOutlet */
	public NSPopUpButton relatedPopup; /* IBOutlet */
	public NSArray buttons = new NSArray();
	
	public void selectIndividual(Object sender) { /* IBAction */
		log.debug("PedigreeViewController.selectIndividual():"+sender);
		log.debug("sender class:"+sender.getClass());
		if (sender instanceof NSControl) {
			NSCell cell = (NSCell) ((NSControl)sender).selectedCell();
			if (cell != null) {
				Object object = cell.representedObject();
				log.debug("cell rep obj:"+object);
				if (object instanceof Individual) {
					Individual individual = (Individual) object;
					((MyDocument) CocoaUtils.getCurrentDocument()).setPrimaryIndividual(individual);
				}
			}
		}
	}
	
	public void selectRelatedIndividual(Object sender) { /* IBAction */
		log.debug("PedigreeViewController.selectRelatedIndividual():"+sender);
		log.debug("sender class:"+sender.getClass());
		if (sender instanceof NSMenuItem) {
			NSMenuItem item = (NSMenuItem) sender;
			if (item != null) {
				Object object = item.representedObject();
				log.debug("menu item rep obj:"+object);
				if (object instanceof Individual) {
					Individual individual = (Individual) object;
					((MyDocument) CocoaUtils.getCurrentDocument()).setPrimaryIndividual(individual);
				}
			}
		}
	}	
	
	public void setPrimaryIndividual(Individual primaryIndividual) {
		log.debug("PedigreeViewController.setPrimaryIndividual()");
		if (buttons.count() == 0) {
			setup();
		}
		assignIndividualToButton(primaryIndividual, primaryIndividualButton);
		assignIndividualToButtonWithTag(primaryIndividual.getFather(), 2);
		assignIndividualToButtonWithTag(primaryIndividual.getMother(), 3);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getFather(), 4);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getMother(), 5);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getFather(), 6);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getMother(), 7);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getFather().getFather(), 8);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getFather().getMother(), 9);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getMother().getFather(), 10);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getMother().getMother(), 11);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getFather().getFather(), 12);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getFather().getMother(), 13);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getMother().getFather(), 14);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getMother().getMother(), 15);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getFather().getFather().getFather(), 16);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getFather().getFather().getMother(), 17);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getFather().getMother().getFather(), 18);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getFather().getMother().getMother(), 19);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getMother().getFather().getFather(), 20);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getMother().getFather().getMother(), 21);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getMother().getMother().getFather(), 22);
		assignIndividualToButtonWithTag(primaryIndividual.getFather().getMother().getMother().getMother(), 23);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getFather().getFather().getFather(), 24);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getFather().getFather().getMother(), 25);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getFather().getMother().getFather(), 26);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getFather().getMother().getMother(), 27);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getMother().getFather().getFather(), 28);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getMother().getFather().getMother(), 29);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getMother().getMother().getFather(), 30);
		assignIndividualToButtonWithTag(primaryIndividual.getMother().getMother().getMother().getMother(), 31);
		makeRelatedPopupForIndividualAndSpouse(primaryIndividual);
	}
	
	private void makeRelatedPopupForIndividualAndSpouse(Individual individual) {
		relatedPopup.removeAllItems();
		NSMenu menu = relatedPopup.menu();
		for (Iterator iter1 = individual.getFamiliesAsSpouse().iterator(); iter1.hasNext();) {
			Family family = (Family) iter1.next();	
				Individual spouse = family.getMother();
				if (Gender.FEMALE.equals(individual.getGender())) {
					spouse = family.getFather();
				}
				if (!(spouse instanceof Individual.UnknownIndividual)) {
					if (menu.itemArray().count() > 0) {
						menu.addItem(new NSMenuItem().separatorItem());
					} else {
						menu.addItem(new NSMenuItem(spouse.getFullName(), null, ""));
						menu.setTitle(spouse.getFullName());
					}
					menu.addItem(menuItemForIndividual(spouse));			
				} else {
					menu.addItem(menuItemForIndividual(spouse));
				}
				int i = 0;
			for (Iterator iter = family.getChildren().iterator(); iter.hasNext();) {
				Individual child = (Individual) iter.next();
				if (!(child instanceof Individual.UnknownIndividual)) {
					menu.addItem(menuItemForIndividualWithPrefix(child, ++i + ". "));
					if (menu.itemArray().count() == 1) {
						menu.setTitle(child.getFullName());
					}
				}
			}
		}
		relatedPopup.setMenu(menu);
		if (menu.itemArray().count() > 0) {
			relatedPopup.setEnabled(true);
		} else {
			relatedPopup.setEnabled(false);
		}
	}

	private NSMenuItem menuItemForIndividualWithPrefix(Individual individual, String prefix) {
		NSMenuItem item = new NSMenuItem();
		item.setTitle(prefix + individual.getFullName());
		item.setRepresentedObject(individual);
		item.setAction(SELECT_RELATED_INDIVIDUAL_SELECTOR);
		item.setTarget(this);
		return item;
	}

	private NSMenuItem menuItemForIndividual(Individual individual) {
		return menuItemForIndividualWithPrefix(individual, "");
	}

	private void assignIndividualToButton(Individual individual, NSButton button) {
		if (button != null) {
			button.setAction(SELECT_INDIVIDUAL_SELECTOR);
			button.setTarget(this);
			NSCell cell = button.cell();
			setCellProperties(individual, cell);
		}
	}

	private void assignIndividualToButtonWithTag(Individual individual, int tag) {
		assignIndividualToButton(individual, (NSButton) buttons.objectAtIndex(tag-1));
	}

	private void setCellProperties(Individual individual, NSCell cell) {
		if (individual instanceof Individual.UnknownIndividual) {
			cell.setTitle("");
			cell.setEnabled(false);
//			((NSButtonCell) cell).setTransparent(true);
		} else {
			cell.setEnabled(true);
//			((NSButtonCell) cell).setTransparent(false);
			cell.setTitle(individual.getFullName());
		}
		cell.setRepresentedObject(individual);
	}
	
	public void setup() {
		// run after windowControllerDidLoadNib
		List buttonArray = new ArrayList();
		NSArray subviews = primaryIndividualButton.superview().subviews();
//		log.debug("subviews:"+subviews);
		Enumeration viewsEnumerator = subviews.objectEnumerator();
		while (viewsEnumerator.hasMoreElements()) {
			NSView view = (NSView) viewsEnumerator.nextElement();
			if (view instanceof NSButton) {
				NSButton button = (NSButton) view;
				log.debug("button tag:"+button.tag());
				if (button.tag() > 0) {
					buttonArray.add(button);
				}
			}
		}
//		this.buttons = buttonArray;
		log.debug("buttonArray b4:"+buttonArray);
		Collections.sort(buttonArray, TAG_COMPARATOR);
		buttons = new NSArray(buttonArray.toArray());
		log.debug("buttons aft:"+buttons.valueForKey("tag"));
	}
	
//	private void assignIndividualToCellLocationForMatrix(Individual individual, int location, NSMatrix matrix) {
//	NSCell cell = matrix.cellAtLocation(location, 0);
//	setCellProperties(individual, cell);
//}
//
//private void assignIndividualToCellLocationForMatrix(Individual individual, int location, List matrix) {
//	log.debug("subviews:"+primaryIndividualButton.superview().subviews());
////	assignIndividualToButton(individual, (NSButton) matrix.get(location));
//}
//
//private void assignIndividualToCellLocationForMatrix(Individual individual, int location, NSBox matrix) {
//	log.debug("box subviews:"+matrix.contentView().subviews());
////	assignIndividualToButton(individual, (NSButton) matrix.get(location));
//}

}
