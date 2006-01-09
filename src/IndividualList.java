/* IndividualList */

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.foundation.NSNotification;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import java.util.List;

/**
* Provides a data source for the NSTableView displaying the list of Individuals in the file
*
 * Partially implements the following interfaces:
 * @see NSTableView.DataSource
 * @see NSTableView.Delegate
 * @see NSTableView.Notifications
 */
public class IndividualList extends AbstractMap {
  private static final Logger log = Logger.getLogger(IndividualList.class);

  public MyDocument document; /* IBOutlet */
  private Individual selectedIndividual; // = null;
//  private List individuals = new ArrayList();
  private SortedMap individualMap = new TreeMap();
  
  private SortableFilteredTableViewDataSource dataSource;// = new SortableFilteredTableViewDataSource();


  public IndividualList() {
	System.out.println("IndividualList.IndividualList()");
//	Thread.dumpStack();
  }

  public Individual getSelectedIndividual() {
	if (selectedIndividual == null) {
		selectedIndividual = Individual.UNKNOWN;
	}
	return selectedIndividual;
  }

  public void setDataSource(SortableFilteredTableViewDataSource newDataSource) {
		dataSource = newDataSource;
	}

/**
 * @see NSTableView.Delegate
 */
  public int numberOfRowsInTableView(NSTableView nsTableView) {
	log.debug("IndividualList.numberOfRowsInTableView()");
	return size();
  }

  /**
   * @see NSTableView.DataSource
   */
  public Object tableViewObjectValueForLocation(NSTableView nsTableView, NSTableColumn nsTableColumn, int i) {
	try {
	  Individual individual = (Individual) individualMap.values().toArray()[i];
	  String headerTitle = nsTableColumn.headerCell().stringValue();
	if (headerTitle.equals("ID")) {
		return individual.getId();
	  }
	  else if (headerTitle.equals("Name")) {
		return individual.getFullName();
	  }
	  else if (headerTitle.equals("Birth date")) {
		return individual.getBirthEvent().getDateString();
	  }
	  else if (headerTitle.equals("Birth place")) {
		return individual.getBirthEvent().getPlace().getFormatString();
	  } else {
		  log.warn("IndividualList unidentified column:" + headerTitle);
	  }
	}
	catch (Exception e) {
	  log.error("Exception: ", e);
	  //To change body of catch statement use Options | File Templates.
	}
	return null;
  }

  public boolean add(Individual individual) {
	// if individual has not been assigned a key, create one
	if (individual.getId() == null || individual.getId().length() == 0) {
	  log.debug("New individual without a valid key, assigning one ....");
	  individual.setId(findValidKey());
	} else {
	  // check to see if this individual has a conflicting ID, if so, change it
	  if (individualMap.containsKey(individual.getId())) {
		log.debug("individualMap contains key " + individual.getId() + " already.");
		individual.setId(findValidKey());
	  }
	}
	return (/*individuals.add(individual) && */individualMap.put(individual.getId(), individual) != null);
  }

  private String findValidKey() {
	log.debug("IndividualList.findValidKey()");
	int index = individualMap.size();
	   String newKey = "I" + index;
	   while (individualMap.containsKey(newKey)) {
		 newKey = "I" + ++index;
	   }
	   log.debug("found next valid key: "+newKey);
	return newKey;
  }

  public void remove(Individual individual) {
//	individuals.remove(individual);
	individualMap.remove(individual.getId());
  }

  /**
   * @see NSTableView.Notifications
   */
  public void tableViewSelectionDidChange(NSNotification aNotification) {
	log.debug(
		"IndividualList tableViewSelectionDidChange():" + aNotification);
	try {
		NSTableView nsTableView = (NSTableView) aNotification.object();
		selectedIndividual =
			(Individual) individualMap.values().toArray()[dataSource.getCurrentSelectedIndex()];
		nsTableView.reloadData();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	document.setIndividual(this);
  }

  /**
   * size
   *
   * @return int
   */
  public int size() {
	return individualMap.size();
  }

  /**
   * entrySet
   *
   * @return Set
   */
  public Set entrySet() {
	return individualMap.entrySet();
  }

  /**
   * getFirstIndividual
   *
   * @return Individual
   */
  public Individual getFirstIndividual() {
	if (individualMap != null && individualMap.size() > 0) {
	  return (Individual) individualMap.values().toArray()[0];
	} else {
	  return Individual.UNKNOWN;
}
  }
  public void setIndividualMap(Map individualMap) {
    this.individualMap = new TreeMap(individualMap);
//	individuals = new ArrayList(individualMap.values());
  }

  public static class DuplicateKeyException extends Exception {
	String validKey = null;
	public DuplicateKeyException(String newKey) {
	  validKey = newKey;
	}
	public String validKey() {
	  return validKey;
	}
  }
}
