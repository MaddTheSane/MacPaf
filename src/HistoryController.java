/* HistoryController */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Individual;

public class HistoryController implements NSMenu.MenuValidation {
	private static Logger log = Logger.getLogger(HistoryController.class);
	
	private List rememberedIndividuals = new ArrayList(10);
	private LinkedList recentIndividuals = new LinkedList();

	private MyDocument document;

	private int recentIndividualsListMax = 10;
	
    public HistoryController(MyDocument doc) {
    	document = doc;
			for (int i = 0; i < 10; i++) {
	    		rememberedIndividuals.add(i, Individual.UNKNOWN);
	    	}
	}

	public HistoryController() {
		// TODO Auto-generated constructor stub
	}

	public void forgetIndividual(Individual individual) {
    	log.debug("HistoryController.forgetIndividual():"+individual);
    }

    public void recallIndividual(int index) {
    	log.debug("HistoryController.recallIndividual():"+index);
    }

    public void recallLastFoundIndividual() {
    	log.debug("HistoryController.recallLastFoundIndividual():");
    }

    public void recallLastSavedIndividual() {
    	log.debug("HistoryController.recallLastSavedIndividual():");
    }

    public void rememberIndividual(Individual individual) {
    	log.debug("HistoryController.rememberIndividual():"+individual);
    	int availIndex = getNextAvailableIndex();
    	if (availIndex >= 0) {
    		rememberedIndividuals.set(availIndex, individual);
    	} else {
    		log.warn("HistoryController.rememberIndividual(): Could not rememberIndividual because list is full. Ind:"+individual);
    	}
	}
    
	private int getNextAvailableIndex() {
		for (int i = 0; i < rememberedIndividuals.size(); i++) {			
			if (rememberedIndividuals.get(i) instanceof Individual.UnknownIndividual) {
				return i;
			}
		}
		return -1;
	}
	
	public void addToRecentIndividualList(Individual individual) {
		if (!(individual instanceof Individual.UnknownIndividual)) {
			recentIndividuals.addFirst(individual);
			trimRecentIndividualList();
		}
	}

	private void trimRecentIndividualList() {
		while (recentIndividuals.size() > recentIndividualsListMax) {
			recentIndividuals.removeLast();
		}
	}
	
	public Individual getRecentIndividual(int index) {
		Individual recent = Individual.UNKNOWN;
		try {
			recent = (Individual) recentIndividuals.get(index);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recent;
	}

	public boolean validateMenuItem(_NSObsoleteMenuItemProtocol menuItem) {
		return false;
//		log.debug("HistoryController.validateMenuItem():"+menuItem);
//		boolean result = false;
//		switch (menuItem.tag()) {
//		case 101:
//			result = true;
//			if (getNextAvailableIndex() < 0) {
//				result = false;
//			} //else if (NSDocumentController.sharedDocumentController().currentDocument())
//				//menuItem.
//			break;
//		case 102:
//		case 110:
//		case 111:
//			result = true;
//			break;
//		default:
//			result = true;
//			if (menuItem.tag() > 0 && (rememberedIndividuals.get(menuItem.tag()-1) instanceof Individual.UnknownIndividual)) {
//				result = false;
//			}
//			break;
//		}
//		log.debug("validateMenuItem done, tag:"+menuItem.tag()+" result:"+result);
//		return result;
	}

	public List getRecentIndividualList() {
		return recentIndividuals;
	}

	public void setRecentIndividualListSize(int newSize) {
		recentIndividualsListMax = newSize;
		trimRecentIndividualList();
	}
}
