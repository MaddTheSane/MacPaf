package com.redbugz.macpaf.util;

import org.jdom.Content;
import org.jdom.Element;

import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.jdom.EventJDOM;

public class JDOMUtils {
//	public static Element makeChildElement(String elementName) {
//		return makeChildElement(elementName, element);
//	}

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

}