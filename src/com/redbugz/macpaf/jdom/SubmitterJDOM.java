/*
 * Created on Oct 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.redbugz.macpaf.jdom;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Content;
import org.jdom.Element;

import com.redbugz.macpaf.Address;
import com.redbugz.macpaf.MultimediaLink;
import com.redbugz.macpaf.Submitter;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SubmitterJDOM implements Submitter {
	Element element = new Element(SUBMITTER);

	public SubmitterJDOM() {
		element.setAttribute("ID", "S1");
		List elements = new ArrayList();
		elements.add(new Element("NAME").setText("Logan Allred"));
		elements.addAll(new AddressJDOM("579 Lambert Dr.", "", "Orem", "UT", "84097", "USA", "(801) 607-4697").getElements());
		element.setContent(elements);
		element.addContent(new Element("LANG").setText("English"));
		element.addContent(new Element("RIN").setText("1"));
		element.addContent((Content)new Element("CHAN").addContent(new Element("DATE").setText(new SimpleDateFormat("dd MMM yyyy").format(new Date()))));
	}

	public SubmitterJDOM(Element element) {
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
		return null;
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
		return null;
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
		return null;
	}

	/* (non-Javadoc)
	 * @see Submitter#setRFN()
	 */
	public String setRFN() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Submitter#getRIN()
	 */
	public Integer getRIN() {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

	/* (non-Javadoc)
	 * @see Submitter#setChangeDate(java.util.Date)
	 */
	public void setChangeDate(Date changeDate) {
		// TODO Auto-generated method stub

	}

}
