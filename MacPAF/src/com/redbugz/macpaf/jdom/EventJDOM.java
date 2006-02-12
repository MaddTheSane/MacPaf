package com.redbugz.macpaf.jdom;

//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.DateTimeDateFormat;
import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Place;
import com.redbugz.macpaf.util.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:34:57 PM
 * To change this template use Options | File Templates.
 */
public class EventJDOM implements Event {
  private static final Logger log = Logger.getLogger(EventJDOM.class);
  private Date date = new GregorianCalendar(2000, 6, 23).getTime();
//  private String dateString = "";
  private Place place =  Place.UNKNOWN_PLACE;
  DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd MMM yyyy");
//  protected DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd MMM yyyy");
  Element element = new Element("EVEN");
//   private String type = "";
//   protected String cause = "";
//   protected int age = -1;
//   protected String ageString = "";
  public static final String BIRTH = "BIRT";
  public static final String CHRISTENING = "CHR";
  public static final String DEATH = "DEAT";
  public static final String BURIAL = "BURI";
  public static final String CREMATION = "CREM";
  public static final String ADOPTION = "ADOP";
  public static final String MARRIAGE = "MARR";
  public static final String DATE = "DATE";
  public static final String EVENT = "EVEN";

  public static final String TYPE = "TYPE";
  public static final String PLACE = "PLAC";
  public static final String ADDR = "ADDR";
  public static final String AGE = "AGE";
  public static final String AGENCY = "AGNC";
  public static final String CAUSE = "CAUS";

  public Date getDate() {
	try {
		if (!StringUtils.isEmpty(getDateString())) {
			date = DateTimeDateFormat.getInstance().parse(getDateString());
//			date = dateFormat.parse(getDateString());
		}
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  log.error("Date Parse error: "+e.getMessage());
	}
	return date;
  }

  public EventJDOM(Element element) {
	if (element == null) {
	  element = new Element(EVENT);
	}
	this.element = element;
	place = new PlaceJDOM(element.getChild(PlaceJDOM.PLACE));
  }

  public EventJDOM(String dateString, Place place) {
	setDateString(dateString);
	setPlace(place);
  }

  public EventJDOM(Date date, Place place) {
	this.date = date;
	this.place = place;
  }

  public EventJDOM() {
	element = new Element(EVENT);
  }

  public EventJDOM(Event originalEvent) {
	if (originalEvent instanceof EventJDOM) {
	  element = ( (EventJDOM) originalEvent).getElement();
	}
	else {
	  setDate(originalEvent.getDate());
	  setPlace(originalEvent.getPlace());
	}
  }

  public static EventJDOM createBirthEventInstance(Family family) {
	return new EventJDOM(new Element(BIRTH));
  }

  public static EventJDOM createChristeningEventInstance(Family family) {
	return new EventJDOM(new Element(CHRISTENING));
  }

  public static EventJDOM createDeathEventInstance() {
	return new EventJDOM(new Element(DEATH));
  }

  public static EventJDOM createBurialEventInstance() {
	return new EventJDOM(new Element(BURIAL));
  }

  public static EventJDOM createCremationEventInstance() {
	return new EventJDOM(new Element(CREMATION));
  }

  public static EventJDOM createAdoptionEventInstance(Family family, boolean adoptedByHusband, boolean adoptedByWife) {
	return new EventJDOM(new Element(ADOPTION));
  }

  public static EventJDOM createMarriageEventInstance() {
	return new EventJDOM(new Element(MARRIAGE));
  }

  public Element getElement() {
	return element;
  }

  public void setDate(Date date) {
	setDateString(dateFormat.print(new DateTime(date)));
  }

  public String getDateString() {
	String dateString = element.getChildTextTrim(DATE);
	if (dateString == null) {
	  dateString = "";
	}
	return dateString;
  }

  public void setDateString(String dateString) {
//      this.dateString = dateString;
	try {
		if (!StringUtils.isEmpty(getDateString())) {
			date = dateFormat.parseDateTime(getDateString()).toDate();
		} else {
			date = null;
		}
	}
	catch (Exception e) {
	  date = null;
	  log.error("Date Parse error: "+e.getMessage());
	}
	element.removeChildren(DATE);
	if (StringUtils.notEmpty(dateString)) {
		element.addContent(new Element(DATE).setText(dateString));
	}
  }

  public Place getPlace() {
	if (place == null) {
	  place = new PlaceJDOM(element.getChild(PLACE));
	}
	return place;
  }

  public void setPlace(Place place) {
	this.place = place;
	element.removeChildren(PLACE);
	if (! (place instanceof Place.UnknownPlace)) {
		element.addContent(new PlaceJDOM(place).getElement());
	}
  }

  // todo: add address support to events
//   public Address getAddress() {
//        return address;
//    }
//
//   public void setAddress(Address address) {
//      this.address = address;
//      element.removeChildren(ADDRESS);
//      element.addContent(new AddressJDOM(address).getElement());
//   }

  /**
   * @return Returns the age.
   */
  public int getAge() {
	return Integer.parseInt(getAgeString());
  }

  /**
   * @param age The age to set.
   */
  public void setAge(int age) {
	setAgeString(String.valueOf(age));
  }

  /**
   * @return Returns the ageString.
   */
  public String getAgeString() {
	String ageString = element.getChildTextTrim(AGE);
	if (ageString == null) {
	  ageString = "";
	}
	return ageString;
  }

  /**
   * @param ageString The ageString to set.
   */
  public void setAgeString(String ageString) {
	element.removeChildren(AGE);
	if (StringUtils.notEmpty(ageString)) {
		element.addContent(new Element(AGE).setText(ageString));
	}
  }

  /**
   * @return Returns the cause.
   */
  public String getCause() {
	String cause = element.getChildTextTrim(CAUSE);
	if (cause == null) {
	  cause = "";
	}
	return cause;
  }

  /**
   * @param cause The cause to set.
   */
  public void setCause(String cause) {
	element.removeChildren(CAUSE);
	if (StringUtils.notEmpty(cause)) {
		element.addContent(new Element(CAUSE).setText(cause));
	}
  }

  /**
   * @return Returns the type.
   */
  public String getType() {
	String source = element.getChildTextTrim(TYPE);
	if (source == null) {
	  source = "";
	}
	return source;
  }
  
  public String getEventTypeString() {
	  return element.getName();
  }

  /**
   * @param type The type to set.
   */
  public void setType(String type) {
	element.removeChildren(TYPE);
	element.addContent(new Element(TYPE).setText(type));
  }

}
