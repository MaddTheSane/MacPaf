/*
 * Created on Oct 3, 2004
 *
 */
package com.redbugz.maf.jdom;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.redbugz.maf.Repository;

/**
 * @author logan
 *
 */
public class RepositoryJDOM implements Repository {
	  private static final Logger log = Logger.getLogger(RepositoryJDOM.class);
	  private static final String newLine = System.getProperty("line.separator");

//	  private StringBuffer text = new StringBuffer();
	  Element element = new Element(REPOSITORY);
private MAFDocumentJDOM document = null;
	  
	  public RepositoryJDOM(Element element, MAFDocumentJDOM parentDocument) {
	  	if (parentDocument == null) {
	  		throw new IllegalArgumentException("Cannot create RepositoryJDOM with null parentDocument");
	  	}
		if (element == null) {
			throw new IllegalArgumentException("Cannot create RepositoryJDOM with null element");
//		  element = new Element(REPOSITORY);
		}
	  	document = parentDocument;
		this.element = element;
		log.debug("RepositoryJDOM() element=\n" + new XMLOutputter(Format.getPrettyFormat()).outputString(element));
	  }

	  public RepositoryJDOM(Repository oldRepository) {
		if (oldRepository instanceof RepositoryJDOM) {
		  this.element = ( (RepositoryJDOM) oldRepository).getElement();
		}
	  }

	  public Element getElement() {
		return element;
	  }

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Repository#getId()
	 */
	public String getId() {
		return element.getAttributeValue(ID);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Repository#setId(java.lang.String)
	 */
	public void setId(String id) {
		element.setAttribute(ID, id);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Repository#getName()
	 */
	public String getName() {
		String name = "";
		Element nameElement = element.getChild(NAME);
		if (nameElement != null) {
			name = nameElement.getTextTrim();
		}
		return name;
	}

}
