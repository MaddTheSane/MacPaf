/* PedigreeViewController */

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Individual;

public class PedigreeViewController extends NSObject {
	private static final Logger log = Logger.getLogger(PedigreeViewController.class);
	
	
	public NSMatrix gggrandparentsMatrix; /* IBOutlet */
	public NSMatrix grandparentsMatrix; /* IBOutlet */
	public NSMatrix greatgrandparentsMatrix; /* IBOutlet */
	public NSMatrix parentsMatrix; /* IBOutlet */
	public NSButton primaryIndividualButton; /* IBOutlet */
	
	public void selectIndividual(Object sender) { /* IBAction */
		log.debug("PedigreeViewController.selectIndividual():"+sender);
		log.debug("sender class:"+sender.getClass());
		if (sender instanceof NSCell) {
			NSCell cell = (NSCell) sender;
			log.debug("cell rep obj:"+cell.representedObject());
		}
	}
	
	public void setPrimaryIndividual(Individual primaryIndividual) {
		log.debug("PedigreeViewController.setPrimaryIndividual()");
		primaryIndividualButton.setTitle(primaryIndividual.getFullName());
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather(), 0, parentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother(), 1, parentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getFather(), 0, grandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getFather().getMother(), 1, grandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getFather(), 2, grandparentsMatrix);
		assignIndividualToCellLocationForMatrix(primaryIndividual.getMother().getMother(), 3, grandparentsMatrix);
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
//		primaryIndividualButton.setStringValue(primaryIndividual.getFullName());
//		parentsMatrix.setStringValue("parents");
//		parentsMatrix.cellAtLocation(1,0).setStringValue("parents2");
//		grandparentsMatrix.cellAtLocation(0, 0).setStringValue("paternal gpa");
//		grandparentsMatrix.cellAtLocation(1, 0).setStringValue("paternal gma");
//		grandparentsMatrix.cellAtLocation(2, 0).setTitle("maternal gpa");
//		grandparentsMatrix.cellAtLocation(3, 0).setTitle("maternal gma");
//		grandparentsMatrix.cellAtLocation(0, 0).setEnabled(false);
//		((NSButtonCell) grandparentsMatrix.cellAtLocation(1, 0)).setAttributedTitle(new NSAttributedString("attributed", new NSDictionary(NSAttributedString.UnderlineStyleAttributeName, new Integer(NSAttributedString.UnderlineStrikethroughMask))));
//		for (int i = 0; i < 16; i++) {
//			gggrandparentsMatrix.cellAtLocation(i, 0).setIntValue(i);
//			gggrandparentsMatrix.cellAtLocation(i, 0).setFont(NSFont.userFixedPitchFontOfSize(6.0F));
//			if (i % 2 == 0) {
//				gggrandparentsMatrix.cellAtLocation(i, 0).setFont(NSFont.boldSystemFontOfSize(14.0F));					
//				gggrandparentsMatrix.cellAtLocation(i, 0).setTitle(""+i);					
//			}
//		}
	}
	
	private void assignIndividualToCellLocationForMatrix(Individual individual, int location, NSMatrix matrix) {
		NSCell cell = matrix.cellAtLocation(location, 0);
		if (individual instanceof Individual.UnknownIndividual) {
			cell.setTitle("");
			cell.setEnabled(false);
			((NSButtonCell) cell).setTransparent(true);
		} else {
			cell.setEnabled(true);
			((NSButtonCell) cell).setTransparent(false);
			cell.setTitle(individual.getFullName());
		}
		cell.setRepresentedObject(individual);
	}
}
