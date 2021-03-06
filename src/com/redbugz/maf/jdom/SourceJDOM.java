/*
 * Created on Oct 3, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.maf.jdom;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.redbugz.maf.Source;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SourceJDOM implements Source {
	  private static final Logger log = Logger.getLogger(SourceJDOM.class);

	   private String id = "";
	   private StringBuffer text = new StringBuffer();
	  Element element = new Element(SOURCE);
	  private static final String newLine = System.getProperty("line.separator");

	private MAFDocumentJDOM document = null;

	  public SourceJDOM(Element element, MAFDocumentJDOM parentDocument) {
	  	if (parentDocument == null) {
	  		throw new IllegalArgumentException("Cannot create SourceJDOM with null parentDocument");
	  	}
		if (element == null) {
	  		throw new IllegalArgumentException("Cannot create SourceJDOM with null element");
//		  element = new Element(SOURCE);
		}
		document = parentDocument;
		this.element = element;
		log.debug("SourceJDOM() element=\n" + new XMLOutputter(Format.getPrettyFormat()).outputString(element));
	  }

//	  public SourceJDOM(Source oldSource) {
//		if (oldSource instanceof SourceJDOM) {
//		  this.element = ( (SourceJDOM) oldSource).getElement();
//		}
//	  }

	  public Element getElement() {
		return element;
	  }

	  public String getId() {
		return element.getAttributeValue(ID);
	  }

	  public String getText() {
		if (element.getAttribute(REF) != null) {
		  try {
			XPath xpath = XPath.newInstance("//SOUR[@ID='" + element.getAttributeValue(REF) + "']");
			log.debug("source xpath:" + xpath.getXPath());
			Element sourceNode = (Element) xpath.selectSingleNode(element);
			log.debug("sourceNode: " + sourceNode);
			try {
			  new XMLOutputter(Format.getPrettyFormat()).output(sourceNode, System.out);
			}
			catch (IOException e1) {
			  // TODO Auto-generated catch block
			  e1.printStackTrace();
			}
			element = new SourceJDOM(sourceNode, null).getElement();

		  }
		  catch (JDOMException e) {
			log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
		  }
		}

		String text = element.getText();
//	     try {
		List nodes = element.getChildren(); //XPath.selectNodes(element, CONCATENATION + " | " + CONTINUATION);
		Iterator iter = nodes.iterator();
		while (iter.hasNext()) {
		  Element item = (Element) iter.next();
		  if (CONTINUATION.equalsIgnoreCase(item.getName())) {
			text += newLine;
		  }
		  else if (CONCATENATION.equalsIgnoreCase(item.getName())) {
			text += item.getText();
		  }

		}
//	   }
//	   catch (JDOMException ex) {
//	     ex.printStackTrace();
//	   }
		return text; //.trim();
	  }

	  public void setId(String id) {
		element.setAttribute(ID, id);
	  }

	  public void setText(String text) {
		element.setText(text);
	  }

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Source#getTitle()
	 */
	public String getTitle() {
		// TODO Auto-generated method stub
		return "";
	}

}
