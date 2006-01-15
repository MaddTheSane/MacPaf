/* FamilyDetailController */

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.jdom.EventJDOM;
import com.redbugz.macpaf.jdom.PlaceJDOM;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RedBugz Software</p>
 * @author Logan Allred
 * @version 1.0
 */

public class FamilyDetailController /*extends NSObject*/ {
  private static final Logger log = Logger.getLogger(FamilyDetailController.class);
  private static final String newLine = System.getProperty("line.separator");

  public NSTableView childrenTable; /* IBOutlet */
  public NSTextField childrenCountText; /* IBOutlet */
  public NSTableView eventTable; /* IBOutlet */
  public NSTextField husbandDetailsText; /* IBOutlet */
  public NSTextView husbandNotesText; /* IBOutlet */
  public NSImageView husbandPhoto; /* IBOutlet */
  public NSTextView noteText; /* IBOutlet */
  public NSTextField wifeDetailsText; /* IBOutlet */
  public NSImageView wifePhoto; /* IBOutlet */

  Family family = Family.UNKNOWN_FAMILY;

  public FamilyDetailController() {
  }

  /**
   * setFamily
   *
   * @param primaryFamily Family
   */
  public void setFamily(Family primaryFamily) {
	if (primaryFamily == null) {
	  primaryFamily = Family.UNKNOWN_FAMILY;
	}
	log.debug("FamilyDetailController.setFamily(primaryFamily) setting family to " + primaryFamily);
	family = primaryFamily;

	if (husbandDetailsText != null) {
		husbandDetailsText.setStringValue(family.getFather().getFullName() + newLine + "b: "+family.getFather().getBirthEvent().getDateString() + " " + family.getFather().getBirthEvent().getPlace().getFormatString());
	}
	if (wifeDetailsText != null) {
		wifeDetailsText.setStringValue(family.getMother().getFullName() + newLine + "b: " + family.getMother().getBirthEvent().getDateString() + " " + family.getMother().getBirthEvent().getPlace().getFormatString());
	}
	if (noteText != null) {
		noteText.setString(family.getNoteText());
	}
	if (eventTable != null) {
		eventTable.setDataSource(this);
	}
	if (eventTable != null) {
		eventTable.reloadData();
	}
	if (childrenTable != null) {
//		childrenTable.setFont(NSFont.controlContentFontOfSize(6.0F));
		childrenTable.setDataSource(this);
		childrenTable.reloadData();
	}
	if (childrenCountText != null) {
		String countText = "No Children";
		switch (family.getChildren().size()) {
			case 0:
				countText = "No Children";
				break;
			
			case 1:
				countText = "1 child";
				break;
			
			default:
				countText = family.getChildren().size() + " children";
				break;
		}
//		if (family.getChildren().size() > 0) {
//			countText = family.getChildren().size() + " children";
//		}
		childrenCountText.setStringValue(countText);
	}
	if (husbandPhoto != null) {
		husbandPhoto.setImage(new NSImage(family.getFather().getImagePath()));
	}
	if (wifePhoto != null) {
		wifePhoto.setImage(new NSImage(family.getMother().getImagePath()));
	}
  }

  /**
   * numberOfRowsInTableView
   *
   * @param nSTableView NSTableView
   * @return int
   */
  public int numberOfRowsInTableView(NSTableView nSTableView) {
	  if (eventTable == nSTableView) {
		  return 3;
	  } else if (1 == nSTableView.tag()) {
		  return family.getChildren().size();
	  }
	return 1;
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
//	  log.debug(nSTableView.tag()+nSTableColumn.identifier().toString()+int2);
	  if (eventTable == nSTableView) {
		  Event event = new EventJDOM("23 Jun 2000", new PlaceJDOM("Test,Utah,Utah"));//(Event) family.getEv().get(int2);
		  if ("date".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getDateString();
		  }
		  else if ("place".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getPlace().getFormatString();
		  } else if ("event".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getEventTypeString();
		  }
	  } else if (childrenTable == nSTableView) {
		  Individual child = (Individual) family.getChildren().get(int2);
		  if ("name".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return child.getFullName();
		  }
		  else if ("birth".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return child.getBirthEvent().getDateString() + " "+ child.getBirthEvent().getPlace().getFormatString();
		  }
		  else if ("childNumber".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return new Integer(int2+1);
		  }		  
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
