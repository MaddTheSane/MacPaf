/* IndividualDetailController */
import org.apache.log4j.Logger;
import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.NSNotification;
import com.apple.cocoa.foundation.NSObject;
import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.Individual;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RedBugz Software</p>
 * @author Logan Allred
 * @version 1.0
 */

public class IndividualDetailController extends NSObject {
  private static final Logger log = Logger.getLogger(IndividualDetailController.class);
  private static final String newLine = System.getProperty("line.separator");

  Individual individual = Individual.UNKNOWN;

  public NSTextField detailsText; /* IBOutlet */
  public NSTableView eventTable; /* IBOutlet */
  public NSTextField infoText; /* IBOutlet */
  public NSTextField marriageText; /* IBOutlet */
  public NSTextView noteText; /* IBOutlet */
  public NSImageView photo; /* IBOutlet */
  public NSButtonCell locked; /* IBOutlet */
  public NSButtonCell privacy; /* IBOutlet */

  public IndividualDetailController() {
//	  Thread.dumpStack();
//	  System.out.println(this);
  }

  /**
   * setIndividual
   *
   * @param primaryIndividual Individual
   */
  public void setIndividual(Individual primaryIndividual) {
	  if (primaryIndividual == null) {
		  primaryIndividual = Individual.UNKNOWN;
	  }
	  log.debug("IndividualDetailController.setIndividual(primaryIndividual) setting individual to " + primaryIndividual);
	  individual = primaryIndividual;
	  
	  if (infoText != null) {
		  infoText.setStringValue(individual.getFullName() 
				  + newLine + individual.getBirthEvent().getDateString()
				  + newLine 
				  + newLine + "Father: "+individual.getFather().getFullName()
				  + newLine + "Mother: "+individual.getMother().getFullName());
	  }
	  if (detailsText != null) {
		  detailsText.setStringValue("Gender: " + individual.getGender().getLongString());
//		  System.out.println("itext:"+infoText);
//		  System.out.println("mtext:"+marriageText);
//		  System.out.println("spouselist:"+individual.getSpouseList());
	  }
	  if (marriageText != null) {
			  marriageText.setStringValue("Marriages: "+individual.getSpouseList().size());
	  }
	  if (locked != null) {
		  locked.setState(individual.isLocked() ? NSCell.OnState : NSCell.OffState);
	  }
	  if (privacy != null) {
		  privacy.setState(individual.isPrivate() ? NSCell.OnState : NSCell.OffState);
	  }
	  if (noteText != null) {
		  noteText.setString(individual.getNoteText());
	  }
	  if (eventTable != null) {
		  eventTable.setDataSource(this);
		  eventTable.reloadData();
	  }
	  if (photo != null) {
		  photo.setImage(new NSImage(individual.getImagePath()));
	  }
  }

  /**
   * numberOfRowsInTableView
   *
   * @param nSTableView NSTableView
   * @return int
   */
  public int numberOfRowsInTableView(NSTableView nSTableView) {
	return individual.getEvents().size();
  }

  /**
   * tableViewObjectValueForLocation
   *
   * @param nSTableView NSTableView
   * @param nSTableColumn NSTableColumn
   * @param int2 int
   * @return Object
   */
  public Object tableViewObjectValueForLocation(NSTableView nSTableView,
												NSTableColumn nSTableColumn,
												int int2) {
	Event event = (Event) individual.getEvents().get(int2);
	if ("date".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
	  return event.getDateString();
	}
	else if ("place".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
	  return event.getPlace().getFormatString();
	} else if ("type".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
		return event.getEventTypeString();
	}
	return "Unknown";
  }

  /**
   * tableViewSetObjectValueForLocation
   *
   * @param nSTableView NSTableView
   * @param object Object
   * @param nSTableColumn NSTableColumn
   * @param int3 int
   */
  public void tableViewSetObjectValueForLocation(NSTableView nSTableView,
												 Object object,
												 NSTableColumn nSTableColumn,
												 int int3) {
  }
}
