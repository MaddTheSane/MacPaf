/* IndividualList */

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Category;
import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.foundation.NSNotification;
import com.redbugz.macpaf.Individual;

/**
* Provides a data source for the NSTableView displaying the list of Individuals in the file
*
 * Partially implements the following interfaces:
 * @see NSTableView.DataSource
 * @see NSTableView.Delegate
 * @see NSTableView.Notifications
 */
public class IndividualList extends AbstractMap {
  private static final Category log = Category.getInstance(IndividualList.class.getName());

  public MyDocument document; /* IBOutlet */
  private Individual selectedIndividual; // = null;
  private ArrayList individuals = new ArrayList();
  private Map individualMap = new HashMap();

  public IndividualList() {
	System.out.println("IndividualList.IndividualList()");
	Thread.dumpStack();
  }

  public Individual getSelectedIndividual() {
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
	  Individual individual = (Individual) individuals.get(i);
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
	  log.debug(
		  "IndividualList unidentified column:" + nsTableColumn);
	}
	catch (Exception e) {
	  log.error("Exception: ", e);
	  //To change body of catch statement use Options | File Templates.
	}
	return null;
  }

  public boolean add(Individual individual) throws IndividualList.DuplicateKeyException {
	// if individual has not been assigned a key, create one
	if (individual.getId() == null || individual.getId().length() == 0) {
	  log.debug("New individual without a valid key, assigning one ....");
	  individual.setId(findValidKey());
	} else {
	  // check to see if this individual has a conflicting ID, if so, change it
	  if (individualMap.containsKey(individual.getId())) {
		log.debug("individualMap contains key " + individual.getId() + " already.");
		String newKey = findValidKey();
		throw new DuplicateKeyException(newKey);
	  }
	}
	return (individuals.add(individual) && individualMap.put(individual.getId(), individual) != null);
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
	individuals.remove(individual);
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
		(Individual) individuals.get(nsTableView.selectedRow());
	document.setIndividual(this);
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
