package com.redbugz.macpaf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Category;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:34:57 PM
 * To change this template use Options | File Templates.
 */
public class MyEvent implements Event {
  private static final Category log = Category.getInstance(MyEvent.class.getName());
  private Date date = new GregorianCalendar(2000, 6, 23).getTime();
  private String dateString = "";
  private Place place = new MyPlace();
  protected DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

  public Date getDate() {
	return date;
  }

  public MyEvent(Element element) {
	if (element != null) {
	  if (element.getChild("DATE") != null) {
		setDateString(element.getChildText("DATE"));
	  }
	  place = new MyPlace(element.getChild("PLAC"));
	}
	log.debug("MyEvent() dateStr=" + dateString + " place=" + place.getFormatString());
  }

  public MyEvent(String dateString, Place place) {
	this.dateString = dateString;
	this.place = place;
  }

  public MyEvent(Date date, Place place) {
	this.date = date;
	this.place = place;
  }

  public MyEvent() {
  }

  public void setDate(Date date) {
	this.date = date;
	dateString = dateFormat.format(date);
  }

  public String getDateString() {
	return dateString;
  }

  public void setDateString(String dateString) {
	this.dateString = dateString;
	try {
	  date = dateFormat.parse(dateString);
	}
	catch (ParseException e) {
	  date = null;
	  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	}
  }

  public Place getPlace() {
	return place;
  }

  public void setPlace(Place place) {
	this.place = place;
  }
}
