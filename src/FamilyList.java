/* FamilyList */

import java.util.ArrayList;

import org.apache.log4j.Category;
import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.foundation.NSNotification;
import com.redbugz.macpaf.Family;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Set;

public class FamilyList extends AbstractMap {
  private static final Category log = Category.getInstance(FamilyList.class.getName());

  public MyDocument document; /* IBOutlet */
  private Family selectedFamily = null;
  private ArrayList families;
  private Map familyMap;
  long ts;

  public FamilyList() {
	System.out.println("FamilyList.FamilyList()");
	if (families == null) {
	  families = new ArrayList();
	  selectedFamily = null;
	  ts = System.currentTimeMillis();
	}
	if (familyMap == null) {
	  familyMap = new HashMap();
}
  }

  public Family getSelectedFamily() {
	return selectedFamily;
  }

  public int numberOfRowsInTableView(NSTableView nsTableView) {
	System.out.println("FamilyList.numberOfRowsInTableView(nsTableView) famList:"+this);
	return families.size();
  }

  public Object tableViewObjectValueForLocation(NSTableView nsTableView, NSTableColumn nsTableColumn, int i) {
	try {
	  System.out.println("FamilyList.tableViewObjectValueForLocation(nsTableView, nsTableColumn, i) famList:"+this);
	  Family family = (Family) families.get(i);
	  if (nsTableColumn.headerCell().stringValue().equals("ID")) {
		return family.getId();
	  }
	  else if (nsTableColumn.headerCell().stringValue().equals("Husband")) {
		return family.getFather().getFullName();
	  }
	  else if (nsTableColumn.headerCell().stringValue().equals("Wife")) {
		return family.getMother().getFullName();
	  }
	  else if (nsTableColumn.headerCell().stringValue().equals("Marriage Date")) {
		return family.getSealingToSpouse().getDateString();
	  }
	  else if (nsTableColumn.headerCell().stringValue().equals("Children")) {
		return String.valueOf(family.getChildren().size());
	  }
	  log.debug("FamilyList unidentified column:" + nsTableColumn);
	}
	catch (Exception e) {
	  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	}
	return null;
  }

  public boolean add(Family family) {
	// if family has not been assigned a key, create one
	if (family.getId() == null || family.getId().length() == 0) {
	  log.debug("New family without a valid key, assigning one ....");
	  family.setId(findValidKey());
	} else {
	  // check to see if this family has a conflicting ID, if so, change it
	  if (familyMap.containsKey(family.getId())) {
		log.debug("familyMap contains key " + family.getId() + " already.");
		family.setId(findValidKey());
	  }
	}
	return (families.add(family) && familyMap.put(family.getId(), family) != null);
  }

  public void tableViewSelectionDidChange(NSNotification aNotification) {
	log.debug("FamilyList tableViewSelectionDidChange():" + aNotification);
	NSTableView nsTableView = (NSTableView) aNotification.object();
	selectedFamily = (Family) families.get(nsTableView.selectedRow());
	nsTableView.reloadData();
	document.setIndividual(this);
  }

  /**
   * size
   *
   * @return int
   */
  public int size() {
	return familyMap.size();
  }

  private String findValidKey() {
	log.debug("FamilyList.findValidKey()");
	int index = familyMap.size();
	   String newKey = "F" + index;
	   while (familyMap.containsKey(newKey)) {
		 newKey = "F" + ++index;
	   }
	   log.debug("found next valid key: "+newKey);
	return newKey;
  }

  /**
   * entrySet
   *
   * @return Set
   */
  public Set entrySet() {
	return familyMap.entrySet();
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

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   * @todo Implement this java.lang.Object method
   */
  public String toString() {
	return ts+":"+families.size()+":"+familyMap.size();
  }
}
