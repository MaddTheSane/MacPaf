/*
 * Created on Nov 22, 2004
 *
 */
package com.redbugz.maf.jdom;

import java.util.Date;
import java.util.List;

import com.redbugz.maf.Address;
import com.redbugz.maf.MultimediaLink;
import com.redbugz.maf.Submitter;

/**
 * @author logan
 *
 */
public class SubmitterLink implements Submitter {
	private SubmitterJDOM actualSubmitter = null;
	private MAFDocumentJDOM document = null;
	String id = "";
	
	SubmitterLink(MAFDocumentJDOM doc) {
		document = doc;
	}
	
	SubmitterLink(String id, MAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Submitter getSubmitter() {
		if (actualSubmitter == null) {
			actualSubmitter = document.getSubmitterJDOM(id);
		}
		return actualSubmitter;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getName()
	 */
	public String getName() {
		return getSubmitter().getName();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setName(java.lang.String)
	 */
	public void setName(String name) {

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getAddress()
	 */
	public Address getAddress() {
		return getSubmitter().getAddress();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setAddress(com.redbugz.maf.Address)
	 */
	public void setAddress(Address address) {

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getMultimediaLink()
	 */
	public MultimediaLink getMultimediaLink() {
		return getSubmitter().getMultimediaLink();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setMultimediaLink(com.redbugz.maf.jdom.MultimediaLink)
	 */
	public void setMultimediaLink(MultimediaLink multimediaLink) {
		getSubmitter().setMultimediaLink(multimediaLink);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getLanguagePreferences()
	 */
	public List getLanguagePreferences() {
		return getSubmitter().getLanguagePreferences();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setLanguagePreferences(java.util.List)
	 */
	public void setLanguagePreferences(List languagePreferences) {
		getSubmitter().setLanguagePreferences(languagePreferences);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getRFN()
	 */
	public String getRFN() {
		return getSubmitter().getRFN();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setRFN()
	 */
	public void setRFN(String rfn) {
		getSubmitter().setRFN(rfn);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getRIN()
	 */
	public Integer getRIN() {
		return getSubmitter().getRIN();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setRIN(java.lang.Integer)
	 */
	public void setRIN(Integer rin) {
		getSubmitter().setRIN(rin);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getChangeDate()
	 */
	public Date getChangeDate() {
		return getSubmitter().getChangeDate();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setChangeDate(java.util.Date)
	 */
	public void setChangeDate(Date changeDate) {
		getSubmitter().setChangeDate(changeDate);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Submitter#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

}
