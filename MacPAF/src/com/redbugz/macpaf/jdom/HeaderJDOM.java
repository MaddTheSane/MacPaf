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
import org.jdom.Text;

import com.redbugz.macpaf.Header;
import com.redbugz.macpaf.Note;
import com.redbugz.macpaf.Submission;
import com.redbugz.macpaf.Submitter;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class HeaderJDOM implements Header {
	Element element = new Element(HEADER);

	public HeaderJDOM() {
		Element source = new Element(SOURCE).setText(APPROVED_SYSTEM_ID);
		source.addContent(new Element(VERSION).setText(VERSION_NUMBER));
		source.addContent(new Element(NAME).setText(NAME_OF_PRODUCT));
		AddressJDOM addr = new AddressJDOM(BUSINESS_ADDR1,BUSINESS_ADDR2,BUSINESS_CITY,BUSINESS_STATE,BUSINESS_ZIP,BUSINESS_COUNTRY,BUSINESS_PHONE);
		List list = new ArrayList();
		list.add(new Text(NAME_OF_BUSINESS));
		list.addAll(addr.getElements());
		System.out.println("headerjdom list:"+list);
		source.addContent((Content)new Element(CORPORATION).setContent(list));

		element.addContent(source);
		element.addContent(new Element(DATE).setText(new SimpleDateFormat("dd MMM yyyy").format(new Date())));
		element.addContent(new Element(SUBMITTER).setAttribute(ID, "S1"));
		element.addContent((Content)new Element(GEDCOM).addContent(new Element(VERSION).setText(GEDCOM_VERSION_55)).addContent(new Element(FORM).setText(FORM_LINEAGE_LINKED)));
		element.addContent(new Element(CHARACTER).setText(ANSEL));
		element.addContent(new Element(LANGUAGE).setText(LANG_ENGLISH));
	}

	public HeaderJDOM(Element element) {
		this.element = element;
	}

	public Element getElement() {
		return element;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceId()
	 */
	public String getSourceId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceVersion()
	 */
	public String getSourceVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceName()
	 */
	public String getSourceName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceCorporation()
	 */
	public String getSourceCorporation() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceCorporationAddress()
	 */
	public String getSourceCorporationAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceData()
	 */
	public String getSourceData() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceDataDate()
	 */
	public Date getSourceDataDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSourceDataCopyright()
	 */
	public String getSourceDataCopyright() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getDestination()
	 */
	public String getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getCreationDate()
	 */
	public Date getCreationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSubmitter()
	 */
	public Submitter getSubmitter() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getSubmission()
	 */
	public Submission getSubmission() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getFileName()
	 */
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getCopyright()
	 */
	public String getCopyright() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getGedcomVersion()
	 */
	public String getGedcomVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getGedcomForm()
	 */
	public String getGedcomForm() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getCharacterSet()
	 */
	public String getCharacterSet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getCharacterVersionNumber()
	 */
	public String getCharacterVersionNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getLanguage()
	 */
	public String getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getPlaceFormat()
	 */
	public String getPlaceFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Header#getNote()
	 */
	public Note getNote() {
		// TODO Auto-generated method stub
		return null;
	}

}
