package com.redbugz.maf.jdom;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.redbugz.maf.Place;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:37:00 PM
 * To change this template use Options | File Templates.
 */
public class PlaceJDOM implements Place {
  private static final Logger log = Logger.getLogger(PlaceJDOM.class);
  public static final String PLACE = "PLAC";

  private static final Map stateAbbreviations = new HashMap(60);
  static {
	stateAbbreviations.put("Alabama", "AL");
	stateAbbreviations.put("Alaska", "AK");
	stateAbbreviations.put("Arizona", "AZ");
	stateAbbreviations.put("Arkansas", "AR");
	stateAbbreviations.put("California", "CA");
	stateAbbreviations.put("Colorado", "CO");
	stateAbbreviations.put("Connecticut", "CT");
	stateAbbreviations.put("Delaware", "DE");
	stateAbbreviations.put("District of Columbia", "DC");
	stateAbbreviations.put("Florida", "FL");
	stateAbbreviations.put("Georgia", "GA");
	stateAbbreviations.put("Hawaii", "HI");
	stateAbbreviations.put("Idaho", "ID");
	stateAbbreviations.put("Illinois", "IL");
	stateAbbreviations.put("Indiana", "IN");
	stateAbbreviations.put("Iowa", "IA");
	stateAbbreviations.put("Kansas", "KS");
	stateAbbreviations.put("Kentucky", "KY");
	stateAbbreviations.put("Louisiana", "LA");
	stateAbbreviations.put("Maine", "ME");
	stateAbbreviations.put("Maryland", "MD");
	stateAbbreviations.put("Massachusetts", "MA");
	stateAbbreviations.put("Michigan", "MI");
	stateAbbreviations.put("Minnesota", "MN");
	stateAbbreviations.put("Mississippi", "MS");
	stateAbbreviations.put("Missouri", "MO");
	stateAbbreviations.put("Montana", "MT");
	stateAbbreviations.put("Nebraska", "NE");
	stateAbbreviations.put("Nevada", "NV");
	stateAbbreviations.put("New Hampshire", "NH");
	stateAbbreviations.put("New Jersey", "NJ");
	stateAbbreviations.put("New Mexico", "NM");
	stateAbbreviations.put("New York", "NY");
	stateAbbreviations.put("North Carolina", "NC");
	stateAbbreviations.put("North Dakota", "ND");
	stateAbbreviations.put("Ohio", "OH");
	stateAbbreviations.put("Oklahoma", "OK");
	stateAbbreviations.put("Oregon", "OR");
	stateAbbreviations.put("Pennsylvania", "PA");
	stateAbbreviations.put("Rhode Island", "RI");
	stateAbbreviations.put("South Carolina", "SC");
	stateAbbreviations.put("South Dakota", "SD");
	stateAbbreviations.put("Tennessee", "TN");
	stateAbbreviations.put("Texas", "TX");
	stateAbbreviations.put("Utah", "UT");
	stateAbbreviations.put("Vermont", "VT");
	stateAbbreviations.put("Virginia", "VA");
	stateAbbreviations.put("Washington", "WA");
	stateAbbreviations.put("West Virginia", "WV");
	stateAbbreviations.put("Wisconsin", "WI");
	stateAbbreviations.put("Wyoming", "WY");
  }

  protected String level1 = "";
  protected String level2 = "";
  protected String level3 = "";
  protected String level4 = "";
  protected Element element = new Element(PLACE);

  public PlaceJDOM() {
  }

  public PlaceJDOM(String placeString) {
	setPlaceString(placeString);
	element.setText(placeString);
  }

  public PlaceJDOM(Element element) {
	if (element == null) {
	  element = new Element(PLACE);
	}
	this.element = element;
	setPlaceString(this.element.getText());
//	log.debug("MyPlace() place=" + getFormatString());
  }

  public PlaceJDOM(Place oldPlace) {
	if (oldPlace instanceof PlaceJDOM) {
	  element = ( (PlaceJDOM) oldPlace).getElement();
	}
	else {
	  setPlaceString(oldPlace.getFormatString());
	}
  }

  public Element getElement() {
	return element;
  }

  private void setPlaceString(String placeString) {
	try {
	  element.setText(placeString);
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
	String result = getLevel4().trim();
	if (result.length() > 0) {
	  result = ", " + result;
	}
	result = getLevel3().trim() + result;
	if (result.length() > 0) {
	  result = ", " + result;
	}
	result = getLevel2().trim() + result;
	if (result.length() > 0) {
	  result = ", " + result;
	}
	result = getLevel1().trim() + result;
	return result;
  }

  public String getAbbreviatedFormatString(int severity) {
	if (severity <= 0) {
	  return getFormatString();
}
	String level1Str = getLevel1().trim();
	String level2Str = getLevel2().trim();
	String level3Str = getLevel3().trim();
	String level4Str = getLevel4().trim();
//	int size = abbrevString.length();
	if (severity >= 1) {
	  // abbreviate level 2
	  level2Str = "";//getAbbreviation(level2Str);
}
	if (severity >= 2) {
	  // remove level 2
	  level2Str = "";
}
	if (severity >= 3) {
	  // abbreviate level 3
	  level3Str = getAbbreviation(level3Str);
}
	if (severity >= 4) {
	  // abbreviate level 4
	  level4Str = getAbbreviation(level4Str);
}
	if (severity >= 5) {
	  // remove level 3
	  level3Str = "";
}
	if (severity >= 6) {
	  // abbreviate level 1
	  level1Str = getAbbreviation(level1Str);
}
	if (severity >= MAX_SEVERITY) {
	  // remove level 1
	  level1Str = "";
}

	String result = "";
	if (level1Str.trim().length() > 0) {
	  result += level1Str.trim() + ", ";
	}
	if (level2Str.trim().length() > 0) {
	  result += level2Str.trim() + ", ";
	}
	if (level3Str.trim().length() > 0) {
	  result += level3Str.trim() + ", ";
	}
	if (level4Str.trim().length() > 0) {
	  result += level4Str.trim() + ", ";
	}
	result = result.trim();
	if (result.endsWith(",")) {
	  result = result.substring(0, result.length()-1);
}
	return result.trim();
}

  /**
   * getAbbreviation
   *
   * @param level2Str String
   * @return String
   */
  private String getAbbreviation(String string) {
	if (string == null || string.length() == 0) {
	  return "";
	}
	String result = "";
	// check for common state abbreviations first
	if (stateAbbreviations.containsKey(string)) {
	  result = (String) stateAbbreviations.get(string);
	} else {
	  StringTokenizer tok = new StringTokenizer(string, " ");
	  while (tok.hasMoreElements()) {
		result += tok.nextElement().toString().substring(0, 1);
	  }
	}
	if (result.length() == 1) {
	  result = "";
	}
	return result;
  }

  public void setLevel1(String level1) {
	if (level1 == null) {
		level1 = "";
	}
	this.level1 = level1;
	element.setText(getFormatString());
  }

  public void setLevel2(String level2) {
		if (level2 == null) {
			level2 = "";
		}
	this.level2 = level2;
	element.setText(getFormatString());
  }

  public void setLevel3(String level3) {
		if (level3 == null) {
			level3 = "";
		}
	this.level3 = level3;
	element.setText(getFormatString());
  }

  public void setLevel4(String level4) {
		if (level4 == null) {
			level4 = "";
		}
	this.level4 = level4;
	element.setText(getFormatString());
  }
}
