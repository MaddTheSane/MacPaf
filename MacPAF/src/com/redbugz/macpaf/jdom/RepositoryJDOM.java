/*
 * Created on Oct 3, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositoryJDOM implements Repository {
	  private static final Category log = Category.getInstance(RepositoryJDOM.class.getName());

	   private String id = "";
	   private StringBuffer text = new StringBuffer();
	  Element element = new Element(REPOSITORY);
	  private static final String newLine = System.getProperty("line.separator");

	  public RepositoryJDOM(Element element) {
		if (element == null) {
		  element = new Element(REPOSITORY);
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Repository#getName()
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
