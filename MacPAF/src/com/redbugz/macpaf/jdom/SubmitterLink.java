/*
 * Created on Nov 22, 2004
 *
 */
package com.redbugz.macpaf.jdom;

import java.util.Date;
import java.util.List;

import com.redbugz.macpaf.Address;
import com.redbugz.macpaf.Submitter;

/**
 * @author logan
 *
 */
public class SubmitterLink implements Submitter {
	private SubmitterJDOM actualSubmitter = null;
	private MacPAFDocumentJDOM document = null;
	String id = "";
	
	SubmitterLink(MacPAFDocumentJDOM doc) {
		document = doc;
	}
	
	SubmitterLink(String id, MacPAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Submitter getSubmitter() {
		if (actualSubmitter == null) {
			actualSubmitter = document.getSubmitter(id);
		}
		return actualSubmitter;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getName()
	 */
	public String getName() {
		return getSubmitter().getName();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setName(java.lang.String)
	 */
	public void setName(String name) {

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getAddress()
	 */
	public Address getAddress() {
		return getSubmitter().getAddress();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setAddress(com.redbugz.macpaf.Address)
	 */
	public void setAddress(Address address) {

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getMultimediaLink()
	 */
	public MultimediaLink getMultimediaLink() {
		return getSubmitter().getMultimediaLink();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setMultimediaLink(com.redbugz.macpaf.jdom.MultimediaLink)
	 */
	public void setMultimediaLink(MultimediaLink multimediaLink) {
		getSubmitter().setMultimediaLink(multimediaLink);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getLanguagePreferences()
	 */
	public List getLanguagePreferences() {
		return getSubmitter().getLanguagePreferences();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setLanguagePreferences(java.util.List)
	 */
	public void setLanguagePreferences(List languagePreferences) {
		getSubmitter().setLanguagePreferences(languagePreferences);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getRFN()
	 */
	public String getRFN() {
		return getSubmitter().getRFN();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setRFN()
	 */
	public void setRFN(String rfn) {
		getSubmitter().setRFN(rfn);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getRIN()
	 */
	public Integer getRIN() {
		return getSubmitter().getRIN();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setRIN(java.lang.Integer)
	 */
	public void setRIN(Integer rin) {
		getSubmitter().setRIN(rin);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getChangeDate()
	 */
	public Date getChangeDate() {
		return getSubmitter().getChangeDate();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setChangeDate(java.util.Date)
	 */
	public void setChangeDate(Date changeDate) {
		getSubmitter().setChangeDate(changeDate);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

}
