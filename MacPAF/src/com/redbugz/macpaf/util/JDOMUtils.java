package com.redbugz.macpaf.util;

import java.util.*;

import org.apache.log4j.*;
import org.jdom.*;
import org.jdom.xpath.*;
import org.joda.time.format.*;

public class JDOMUtils {
	  private static final Logger log = Logger.getLogger(JDOMUtils.class);

//	public static Element makeChildElement(String elementName) {
//		return makeChildElement(elementName, element);
//	}

	private static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormat.forPattern("dd MMM yyyy");

	public static Element makeChildElement(String elementName,	Element parentElement) {
		if (parentElement == null) {
			throw new IllegalArgumentException("JDOMUtils.makeChildElement("+ elementName + ",null): parentElement cannot be null");
		}
		Element child = parentElement.getChild(elementName);
		if (child == null) {
			child = new Element(elementName);
			parentElement.addContent(child);
		}
		return child;
	}
	
	public static Element findOrMakeChildElement(String elementName, Element parentElement) {
		if (parentElement == null) {
			throw new IllegalArgumentException("JDOMUtils.makeChildElement("+ elementName + ",null): parentElement cannot be null");
		}
		Element child = parentElement.getChild(elementName);
		if (child == null) {
			child = new Element(elementName);
			parentElement.addContent(child);
		}
		return child;		
	}
	
	/**
	 * 
	 * @param parent
	 * @param newChild
	 */
	public static void replaceFirstChildElement(Element parent, Element newChild) {
		parent.removeChild(newChild.getName());
		parent.addContent(newChild);
	}

	public static Date dateFromElementText(String childText) {
		Date result = null;
		try {
			result = SIMPLE_DATE_FORMAT.parseDateTime(childText).toDate();
		} catch (Exception e) {
			log.warn("Could not parse date from JDOM element:"+childText);
		}
		return result;
	}

	public static String getNonNullChildText(Element child) {
		if (child == null) {
			return "";
		}
		return StringUtils.nonNullString(child.getText());
	}

	public static String getNonNullXpathText(Element element, String xpathExpr) {
		String result = "";
		try {
			result = (String) XPath.selectSingleNode(element, xpathExpr);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Element getXpathElement(Element element, String xpathExpr) {
		Element result = null;
		try {
			result = (Element) XPath.selectSingleNode(element, xpathExpr);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String cleanText(String text) {
		  // clean up illegal characters and issue a warning

		// change vertical tab into newline
		text = text.replace('\u000B', '\n');
		  StringBuffer cleanedData = new StringBuffer();
		  char[] charArray = text.toCharArray();
		  for (int j = 0; j < charArray.length; j++) {
			  char c = charArray[j];
			  if (Verifier.isXMLCharacter(c)) {
				  cleanedData.append(c);
			  } else {
				  log.info("Removing illegal character "+Integer.toHexString(c)+" from JDOM Text");
			  }
		  }
		  return cleanedData.toString();

	}

}
