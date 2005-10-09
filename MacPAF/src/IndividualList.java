/* IndividualList */

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.foundation.NSNotification;
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
  private Map individualMap = new HashMap();

  public IndividualList() {
	System.out.println("IndividualList.IndividualList()");
	Thread.dumpStack();
  }

  public Individual getSelectedIndividual() {
	if (selectedIndividual == null) {
	  if (individualMap.size() > 0) {
		selectedIndividual = (Individual) individualMap.values().toArray()[0];
	  } else {
		selectedIndividual = Individual.UNKNOWN;
}
}
	return selectedIndividual;
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
  public Object tableViewObjectValueForLocation(
	  NSTableView nsTableView,
	  NSTableColumn nsTableColumn,
	  int i) {
	try {
	  Individual individual = (Individual) individualMap.values().toArray()[i];
	  if (nsTableColumn.headerCell().stringValue().equals("ID")) {
		return individual.getId();
	  }
	  else if (
		  nsTableColumn.headerCell().stringValue().equals("Name")) {
		return individual.getFullName();
	  }
	  else if (
		  nsTableColumn.headerCell().stringValue().equals(
		  "Birth date")) {
		return individual.getBirthEvent().getDateString();
	  }
	  else if (
		  nsTableColumn.headerCell().stringValue().equals(
		  "Birth place")) {
		return individual.getBirthEvent().getPlace().getFormatString();
	  }
	  log.warn(
		  "IndividualList unidentified column:" + nsTableColumn);
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
	NSTableView nsTableView = (NSTableView) aNotification.object();
	selectedIndividual =
		(Individual) individualMap.values().toArray()[nsTableView.selectedRow()];
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
    this.individualMap = individualMap;
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
