package com.redbugz.macpaf.jdom;

import org.apache.log4j.Category;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import com.redbugz.macpaf.Temple;

/**
 * Created by IntelliJ IDEA. User: logan Date: Nov 9, 2003 Time: 2:53:43 PM To
 * change this template use Options | File Templates.
 */
public class TempleJDOM extends Temple {
  private static final Category log = Category.getInstance(TempleJDOM.class.getName());
  public static final String TEMPLE = "TEMP";

  Element element = new Element(TEMPLE);

  public TempleJDOM() {
	element = new Element(TEMPLE);
  }

  public TempleJDOM(Element element) {
	if (element != null) {
	  this.element = element;
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
   * @see com.redbugz.macpaf.Temple#getCode()
   */
  public String getCode() {
	return element.getTextTrim();
  }

}