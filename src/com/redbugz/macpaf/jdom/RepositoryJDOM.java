/*
 * Created on Oct 3, 2004
 *
 */
package com.redbugz.macpaf.jdom;

import org.apache.log4j.Category;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.redbugz.macpaf.Repository;

/**
 * @author logan
 *
 */
public class RepositoryJDOM implements Repository {
	  private static final Category log = Category.getInstance(RepositoryJDOM.class.getName());
	  private static final String newLine = System.getProperty("line.separator");

//	  private StringBuffer text = new StringBuffer();
	  Element element = new Element(REPOSITORY);
private MacPAFDocumentJDOM document = null;
	  
	  public RepositoryJDOM(Element element, MacPAFDocumentJDOM parentDocument) {
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
	 * @see com.redbugz.macpaf.Repository#getId()
	 */
	public String getId() {
		return element.getAttributeValue(ID);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Repository#setId(java.lang.String)
	 */
	public void setId(String id) {
		element.setAttribute(ID, id);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Repository#getName()
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
