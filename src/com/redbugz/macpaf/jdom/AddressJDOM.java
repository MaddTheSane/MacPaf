/*
 * Created on Oct 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.redbugz.macpaf.jdom;


import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.jdom.Element;
import org.jdom.Text;
import org.jdom.filter.ContentFilter;

import com.redbugz.macpaf.Address;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AddressJDOM implements Address {
    private final String ADDRESS = "ADDR";
    // address and phone are at the same level, so maintain separately
	Element element = new Element(ADDRESS);
	ArrayList phones = new ArrayList();//Element("PHON");
    private final String CONT = "CONT";
    private final String ADDRESS_LINE_1 = "ADR1";
    private final String ADDRESS_LINE_2 = "ADR2";
    private final String CITY = "CITY";
    private final String STATE = "STAE";
    private final String POSTAL_CODE = "POST";
    private final String COUNTRY = "CTRY";
    private final String PHONE = "PHON";

    public AddressJDOM(String addressLine1, String addressLine2, String city, String state, String postalCode, String country, String phoneNumber) {
		element.setText(addressLine1);
		element.addContent(new Element(CONT).setText(addressLine2));
		element.addContent(new Element(ADDRESS_LINE_1).setText(addressLine1));
		element.addContent(new Element(ADDRESS_LINE_2).setText(addressLine2));
		element.addContent(new Element(CITY).setText(city));
		element.addContent(new Element(STATE).setText(state));
		element.addContent(new Element(POSTAL_CODE).setText(postalCode));
		element.addContent(new Element(COUNTRY).setText(country));
        phones.clear();
        phones.add(new Element(PHONE).setText(phoneNumber));
	}
	
	public AddressJDOM(Element element, List phoneElements) {
        if (element == null) {
            element = new Element(ADDRESS);
        }
        this.element = element;
        phones.clear();
        Iterator iter = phoneElements.iterator();
        while(iter.hasNext()) {
            phones.add(new Element(PHONE).setText(phoneElements.toString()));
        }
	}

    public AddressJDOM(Element element) {
        this.element = element;
    }

    public Element getAddressElement() {
		return element;
	}
	
	public List getPhoneElements() {
		return phones;
	}

    public Element getFirstPhoneElement() {
        return (Element) phones.get(0);
    }

	public List getElements() {
		List elements = new ArrayList();
		elements.add(element);
        Iterator iter = phones.iterator();
        while(iter.hasNext()) {
            elements.add(iter.next());
        }
		return elements;
	}

	/* (non-Javadoc)
	 * @see Address#getAddressLines()
	 */
	public List getAddressLines() {
        ArrayList lines = new ArrayList();
        lines.add(element.getTextTrim());
        Iterator iter = element.getChildren(CONT).iterator();
        while (iter.hasNext()) {
            lines.add(iter.next().toString());
        }
		return lines;
	}

	/* (non-Javadoc)
	 * @see Address#setAddressLines(java.util.List)
	 */
	public void setAddressLines(List addressLines) {
        Iterator iter = addressLines.iterator();
        if (iter.hasNext()) {
            setAddressLine1(iter.next().toString());
        }
	}

	/* (non-Javadoc)
	 * @see Address#getAddressLine1()
	 */
	public String getAddressLine1() {
		return element.getChildTextTrim(ADDRESS_LINE_1);
	}

	/* (non-Javadoc)
	 * @see Address#setAddressLine1(java.lang.String)
	 */
	public void setAddressLine1(String line1) {
        element.removeChildren(ADDRESS_LINE_1);
        element.addContent(new Element(ADDRESS_LINE_1).setText(line1));
        element.removeContent(new ContentFilter(ContentFilter.TEXT));
        element.addContent(new Text(line1));
	}

	/* (non-Javadoc)
	 * @see Address#getAddressLine2()
	 */
	public String getAddressLine2() {
        return element.getChildTextTrim(ADDRESS_LINE_2);
	}

	/* (non-Javadoc)
	 * @see Address#setAddressLine2(java.lang.String)
	 */
	public void setAddressLine2(String line2) {
        element.removeChildren(ADDRESS_LINE_2);
        element.addContent(new Element(ADDRESS_LINE_2).setText(line2));
        element.removeChildren(CONT);
        element.addContent(new Element(CONT).setText(line2));
	}

	/* (non-Javadoc)
	 * @see Address#getCity()
	 */
	public String getCity() {
        return element.getChildTextTrim(CITY);
	}

	/* (non-Javadoc)
	 * @see Address#setCity(java.lang.String)
	 */
	public void setCity(String city) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Address#getState()
	 */
	public String getState() {
        return element.getChildTextTrim(STATE);
	}

	/* (non-Javadoc)
	 * @see Address#setState(java.lang.String)
	 */
	public void setState(String state) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Address#getPostalCode()
	 */
	public String getPostalCode() {
        return element.getChildTextTrim(POSTAL_CODE);
	}

	/* (non-Javadoc)
	 * @see Address#setPostalCode(java.lang.String)
	 */
	public void setPostalCode(String postalCode) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Address#getCountry()
	 */
	public String getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Address#setCountry(java.lang.String)
	 */
	public void setCountry(String country) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Address#getPhoneNumber()
	 */
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Address#setPhoneNumber(java.lang.String)
	 */
	public void setPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Address#getPhoneNumbers()
	 */
	public List getPhoneNumbers() {
        ArrayList phoneNumbers = new ArrayList();
        Iterator iter = element.getChildren(PHONE).iterator();
        while(iter.hasNext()) {
            phoneNumbers.add(((Element)iter.next()).getTextTrim());
        }
        return phoneNumbers;
	}

	/* (non-Javadoc)
	 * @see Address#setPhoneNumbers(java.util.List)
	 */
	public void setPhoneNumbers(List phoneNumbers) {
        // There is a limit of 3 phone numbers
        if (phoneNumbers.size() > 3) {
            throw new IllegalArgumentException("Only 3 phone numbers are allowed. Received "+phoneNumbers.size()+" phone numbers.");
        }
        for (int i=0; i < 3; i++) {

        }
	}

}
