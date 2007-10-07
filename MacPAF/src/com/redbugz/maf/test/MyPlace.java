package com.redbugz.maf.test;

import java.util.*;

import org.apache.log4j.*;
import org.jdom.*;

import com.redbugz.macpaf.MyPlace;
import com.redbugz.maf.*;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:37:00 PM
 * To change this template use Options | File Templates.
 */
public class MyPlace implements Place {
  private static final Logger log = Logger.getLogger(MyPlace.class);
  protected String level1 = "";
  protected String level2 = "";
  protected String level3 = "";
  protected String level4 = "";

  public MyPlace(String placeString) {
	setPlaceString(placeString);
  }

  private void setPlaceString(String placeString) {
	try {
	  StringTokenizer st = new StringTokenizer(placeString, ",");
	  level1 = st.nextToken().trim();
	  level2 = st.nextToken().trim();
	  level3 = st.nextToken().trim();
	  level4 = st.nextToken().trim();
	}
	catch (Exception e) {
	  //log.error("Exception: ", e);  //To change body of catch statement use Options | File Templates.
	}
  }

  public MyPlace() {
  }

  public MyPlace(Element element) {
	if (element != null) {
	  setPlaceString(element.getText());
	}
	log.debug("MyPlace() place=" + getFormatString());
  }

  public String getLevel1() {
	return level1;
  }

  public String getLevel2() {
	return level2;
  }

  public String getLevel3() {
	return level3;
  }

  public String getLevel4() {
	return level4;
  }

  public String getFormatString() {
	String result = getLevel4();
	if (result.length() > 0) {
	  result = ", " + result;
	}
	result = getLevel3() + result;
	if (result.length() > 0) {
	  result = ", " + result;
	}
	result = getLevel2() + result;
	if (result.length() > 0) {
	  result = ", " + result;
	}
	result = getLevel1() + result;
	return result;
  }

  public void setLevel1(String level1) {
	this.level1 = level1;
  }

  public void setLevel2(String level2) {
	this.level2 = level2;
  }

  public void setLevel3(String level3) {
	this.level3 = level3;
  }

  public void setLevel4(String level4) {
	this.level4 = level4;
  }

  /**
   * getAbbreviatedFormatString
   *
   * @param severity int
   * @return String
   */
  public String getAbbreviatedFormatString(int severity) {
	return getFormatString();
  }
}
