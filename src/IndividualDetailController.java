/* IndividualDetailController */
import com.redbugz.macpaf.*;


import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Event;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RedBugz Software</p>
 * @author Logan Allred
 * @version 1.0
 */

public class IndividualDetailController extends NSObject {
  private static final String newLine = System.getProperty("line.separator");

  Individual individual = Individual.UNKNOWN;

  public NSTextField detailsText; /* IBOutlet */
  public NSTableView eventTable; /* IBOutlet */
  public NSTextField infoText; /* IBOutlet */
  public NSTextView noteText; /* IBOutlet */
  public NSImageView photo; /* IBOutlet */
  public NSButtonCell locked; /* IBOutlet */
  public NSButtonCell privacy; /* IBOutlet */

  public IndividualDetailController() {
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
    System.out.println("IndividualDetailController.setIndividual(primaryIndividual) setting individual to "+primaryIndividual);
    individual = primaryIndividual;

    infoText.setStringValue(individual.getFullName()+newLine+individual.getBirthEvent().getDateString());
    detailsText.setStringValue("Gender: "+individual.getGender().getLongString());
    locked.setState(individual.isLocked()?NSCell.OnState:NSCell.OffState);
    privacy.setState(individual.isPrivate()?NSCell.OnState:NSCell.OffState);
    noteText.setString(individual.getNoteText());
    eventTable.reloadData();
    photo.setImage(new NSImage(individual.getImagePath()));
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
    } else if ("place".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
      return event.getPlace().getFormatString();
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
