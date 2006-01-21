/* PedigreeViewController */

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.util.CocoaUtils;

public class PedigreeViewController extends NSObject {
	private static final Logger log = Logger.getLogger(PedigreeViewController.class);


	private static final NSSelector SELECT_RELATED_INDIVIDUAL_SELECTOR = new NSSelector("selectRelatedIndividual", new Class[] {Object.class});
	
	
	public NSMatrix gggrandparentsMatrix; /* IBOutlet */
	// since this NSMatrix overlaps with others, clicking doesn't work, so I have to replace it with individual buttons
//	public NSMatrix grandparentsMatrix; /* IBOutlet */
	public NSButton paternalGrandfatherButton; /* IBOutlet */
	public NSButton paternalGrandmotherButton; /* IBOutlet */
	public NSButton maternalGrandfatherButton; /* IBOutlet */
	public NSButton maternalGrandmotherButton; /* IBOutlet */
	public NSMatrix greatgrandparentsMatrix; /* IBOutlet */
	public NSMatrix parentsMatrix; /* IBOutlet */
	public NSButton primaryIndividualButton; /* IBOutlet */
	public NSPopUpButton relatedPopup; /* IBOutlet */
	
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
		assignIndividualToButton(primaryIndividual, primaryIndividualButton);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather(), 0, parentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother(), 1, parentsMatrix);
		assignIndividualToButton(primaryIndividual.getFather().getFather(), paternalGrandfatherButton);
		assignIndividualToButton(primaryIndividual.getFather().getMother(), paternalGrandmotherButton);
		assignIndividualToButton(primaryIndividual.getMother().getFather(), maternalGrandfatherButton);
		assignIndividualToButton(primaryIndividual.getMother().getMother(), maternalGrandmotherButton);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getFather().getFather(), 0, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getFather().getMother(), 1, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getMother().getFather(), 2, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getMother().getMother(), 3, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getFather().getFather(), 4, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getFather().getMother(), 5, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getMother().getFather(), 6, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getMother().getMother(), 7, greatgrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getFather().getFather().getFather(), 0, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getFather().getFather().getMother(), 1, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getFather().getMother().getFather(), 2, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getFather().getMother().getMother(), 3, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getMother().getFather().getFather(), 4, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getMother().getFather().getMother(), 5, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getMother().getMother().getFather(), 6, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getMother().getMother().getMother(), 7, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getFather().getFather().getFather(), 8, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getFather().getFather().getMother(), 9, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getFather().getMother().getFather(), 10, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getFather().getMother().getMother(), 11, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getMother().getFather().getFather(), 12, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getMother().getFather().getMother(), 13, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getMother().getMother().getFather(), 14, gggrandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getMother().getMother().getMother(), 15, gggrandparentsMatrix);
		makeRelatedPopupForIndividual(primaryIndividual);
	}
	
	private void makeRelatedPopupForIndividual(Individual individual) {
		relatedPopup.removeAllItems();
		NSMenu menu = relatedPopup.menu();
		for (Iterator iter1 = individual.getFamiliesAsSpouse().iterator(); iter1.hasNext();) {
			Family family = (Family) iter1.next();	
				Individual spouse = family.getMother();
				if (!(spouse instanceof Individual.UnknownIndividual)) {
					if (menu.itemArray().count() > 0) {
						menu.addItem(new NSMenuItem().separatorItem());
					} else {
						menu.addItem(new NSMenuItem(spouse.getFullName(), null, ""));
						menu.setTitle(spouse.getFullName());
					}
					menu.addItem(menuItemForIndividual(spouse));			
				}
				int i = 0;
			for (Iterator iter = family.getChildren().iterator(); iter.hasNext();) {
				Individual child = (Individual) iter.next();
				if (!(child instanceof Individual.UnknownIndividual)) {
					menu.addItem(menuItemForIndividualWithPrefix(child, ++i + ". "));			
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

	private void assignIndividualToCellLocationForMatrix(Individual individual, int location, NSMatrix matrix) {
		NSCell cell = matrix.cellAtLocation(location, 0);
		setCellProperties(individual, cell);
	}

	private void assignIndividualToButton(Individual individual, NSButton button) {
		NSCell cell = button.cell();
		setCellProperties(individual, cell);
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
	
}
