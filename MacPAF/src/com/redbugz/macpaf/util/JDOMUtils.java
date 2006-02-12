package com.redbugz.macpaf.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.jdom.EventJDOM;

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

}
