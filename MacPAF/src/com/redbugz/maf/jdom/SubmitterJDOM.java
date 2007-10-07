/*
 * Created on Oct 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.redbugz.maf.jdom;

import java.text.*;
import java.util.*;

import org.jdom.*;

import com.redbugz.macpaf.AddressJDOM;
import com.redbugz.macpaf.MacPAFDocumentJDOM;
import com.redbugz.macpaf.MultimediaLink;
import com.redbugz.macpaf.StringUtils;
import com.redbugz.maf.*;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SubmitterJDOM implements Submitter {
	private MacPAFDocumentJDOM document = null;
	private Element element = new Element(SUBMITTER);

  public SubmitterJDOM(MacPAFDocumentJDOM parentDocument) {
  	if (parentDocument == null) {
  		throw new IllegalArgumentException("Cannot create SubmitterJDOM with null parentDocument");
  	}
  	document = parentDocument;
	element.setAttribute("ID", "T1");
	List elements = new ArrayList();
	elements.add(new Element(NAME).setText(""));
	element.setContent(elements);
	element.addContent(new Element(LANGUAGE).setText("English"));
	element.addContent(new Element(RIN).setText("1"));
//	element.addContent( (Content)new Element("CHAN").addContent(new Element("DATE").setText(new SimpleDateFormat(
//		"dd MMM yyyy").format(new Date()))));
  }
  
  public boolean isDefault() {
		boolean result = false;
		// default structure is same as the constructor
		if (getElement().getContentSize() == 3) {
			result = true;
			Element nameElement = getElement().getChild(Submitter.NAME);
			if (nameElement != null) {
				if ( ! StringUtils.isEmpty(nameElement.getTextTrim())) {
					result = false;
				}
			}
			Element langElement = getElement().getChild(Submitter.LANGUAGE);
			if (langElement != null) {
				if ( ! "English".equals(langElement.getTextTrim())) {
					result = false;
				}
			}
			Element rinElement = getElement().getChild(Submitter.RIN);
			if (rinElement != null) {
				if ( ! "1".equals(rinElement.getTextTrim())) {
					result = false;
				}
			}
		}
		return result;
  }


  public SubmitterJDOM(Element element, MacPAFDocumentJDOM parentDocument) {
  	if (parentDocument == null) {
  		throw new IllegalArgumentException("Cannot create SubmitterJDOM with null parentDocument");
  	}
  	if (element == null) {
  		throw new IllegalArgumentException("Cannot create SubmitterJDOM with null element");
  	}
  	document = parentDocument;
	this.element = element;
  }

  public Element getElement() {
	return element;
  }

  /* (non-Javadoc)
   * @see Submitter#getName()
   */
  public String getName() {
	return element.getChildTextTrim("NAME");
  }

  /* (non-Javadoc)
   * @see Submitter#setName(java.lang.String)
   */
  public void setName(String name) {
	element.removeChildren("NAME");
	element.addContent(new Element("NAME").setText(name));
  }

  /* (non-Javadoc)
   * @see Submitter#getAddress()
   */
  public Address getAddress() {
	// TODO Auto-generated method stub
	return new AddressJDOM(element.getChild("ADDR"));
  }

  /* (non-Javadoc)
   * @see Submitter#setAddress(Address)
   */
  public void setAddress(Address address) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Submitter#getMultimediaLink()
   */
  public MultimediaLink getMultimediaLink() {
	// TODO Auto-generated method stub
	return new MultimediaLink(document);
  }

  /* (non-Javadoc)
   * @see Submitter#setMultimediaLink(MultimediaLink)
   */
  public void setMultimediaLink(MultimediaLink multimediaLink) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Submitter#getLanguagePreferences()
   */
  public List getLanguagePreferences() {
	// TODO Auto-generated method stub
	return Collections.EMPTY_LIST;
  }

  /* (non-Javadoc)
   * @see Submitter#setLanguagePreferences(java.util.List)
   */
  public void setLanguagePreferences(List languagePreferences) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Submitter#getRFN()
   */
  public String getRFN() {
	// TODO Auto-generated method stub
	return "";
  }

  /* (non-Javadoc)
   * @see Submitter#setRFN()
   */
  public void setRFN(String rfn) {

  }

  /* (non-Javadoc)
   * @see Submitter#getRIN()
   */
  public Integer getRIN() {
	// TODO Auto-generated method stub
	return new Integer(0);
  }

  /* (non-Javadoc)
   * @see Submitter#setRIN(java.lang.Integer)
   */
  public void setRIN(Integer rin) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Submitter#getChangeDate()
   */
  public Date getChangeDate() {
	// TODO Auto-generated method stub
	return new Date();
  }

  /* (non-Javadoc)
   * @see Submitter#setChangeDate(java.util.Date)
   */
  public void setChangeDate(Date changeDate) {
	// TODO Auto-generated method stub

  }

/* (non-Javadoc)
 * @see com.redbugz.maf.Submitter#getId()
 */
public String getId() {
	return element.getAttributeValue(ID);
}

/* (non-Javadoc)
 * @see com.redbugz.maf.Submitter#setId(java.lang.String)
 */
public void setId(String id) {
	element.setAttribute(ID, id);
}

}
