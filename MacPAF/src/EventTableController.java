/* EventTableController */

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.jdom.EventJDOM;
import com.redbugz.macpaf.jdom.PlaceJDOM;
import com.redbugz.macpaf.util.CocoaUtils;

public class EventTableController extends NSObject {
	  private static final Logger log = Logger.getLogger(EventTableController.class);
	private Object _source = Individual.UNKNOWN;
	private String _type = "event";
	private List _events = Collections.EMPTY_LIST;

    public NSTableView eventTable; /* IBOutlet */

    public void addEvent(Object sender) { /* IBAction */
    		MyDocument.showUserErrorMessage("Feature not yet available.", "This feature has not yet been implemented. Look for a future release to add this functionality.");
    }

    public void editEvent(Object sender) { /* IBAction */
		MyDocument.showUserErrorMessage("Feature not yet available.", "This feature has not yet been implemented. Look for a future release to add this functionality.");
    }

    public void removeEvent(Object sender) { /* IBAction */
		MyDocument.showUserErrorMessage("Feature not yet available.", "This feature has not yet been implemented. Look for a future release to add this functionality.");
    }


	  public void setEventSource(Object source) {
		  _source = source;
		  _events = getEvents();
		  eventTable.reloadData();
		  eventTable.deselectAll(this);
	  }
	  
	  public void setEventType(String type) {
		  _type = type;
		  _events = getEvents();
		  eventTable.reloadData();
		  eventTable.deselectAll(this);
	  }

	  private List getEvents() {
		  if (_source instanceof Individual) {
			  Individual individual = (Individual) _source;
			  if ("event".equals(_type)) {
				  return individual.getEvents();
			  } else if ("attribute".equals(_type)) {
				  return individual.getAttributes();
			  }
		  } else if (_source instanceof Family) {
			  Family family = (Family) _source;
			  return family.getEvents();
		  }
		  return Collections.EMPTY_LIST;
	  }
	  /**
	   * numberOfRowsInTableView
	   *
	   * @param nSTableView NSTableView
	   * @return int
	   */
	  public int numberOfRowsInTableView(NSTableView nSTableView) {
//		  log.debug("EventTableController.numberOfRowsInTableView():"+_events.size());
		return _events.size();
	  }

	  /**
	   * tableViewObjectValueForLocation
	   *
	   * @param nSTableView NSTableView
	   * @param nSTableColumn NSTableColumn
	   * @param int2 int
	   * @return Object
	   */
	  public Object tableViewObjectValueForLocation(NSTableView nSTableView, NSTableColumn nSTableColumn, int int2) {
//		  System.out.println("EventTableController.tableViewObjectValueForLocation():"+nSTableView+":"+nSTableColumn.headerCell().stringValue()+":"+int2);
		  Event event = (Event) _events.get(int2);
//		  log.debug("event:"+event.getDateString()+event.getEventTypeString());
		  if ("date".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getDateString();
		  }
		  else if ("place".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getPlace().getFormatString();
		  }
		  else if ("type".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getEventTypeString();
		  }
		  return "Unknown";
	  }

}
