package com.redbugz.maf.jdom;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.redbugz.maf.Temple;

/**
 * Created by IntelliJ IDEA. User: logan Date: Nov 9, 2003 Time: 2:53:43 PM To
 * change this template use Options | File Templates.
 */
public class TempleJDOM extends Temple {
  private static final Logger log = Logger.getLogger(TempleJDOM.class);
  public static final String TEMPLE = "TEMP";

  Element element = new Element(TEMPLE);

  public TempleJDOM() {
	element = new Element(TEMPLE);
  }

  public TempleJDOM(Element newElement) {
	if (newElement != null) {
	  element = newElement;
	}
  }

  public TempleJDOM(Temple temple) {
	if (temple != null) {
	  if (temple instanceof TempleJDOM) {
		element = ( (TempleJDOM) temple).getElement();
	  }
	  else {
		element.setText(temple.getCode());
		log.debug("TempleJDOM.TempleJDOM():" + new XMLOutputter(Format.getPrettyFormat()).outputString(element));
	  }
	}
  }

  public Element getElement() {
	return element;
  }

  /* (non-Javadoc)
   * @see com.redbugz.maf.Temple#getCode()
   */
  public String getCode() {
  	log.debug("TempleJDOM.getCode():"+element.getTextTrim());
	return element.getTextTrim();
  }
  
  public void setCode(String newCode) {
  	element.setText(newCode);
  }

}
