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
//		setId(id);
		this.id=id;
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setName(java.lang.String)
	 */
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getAddress()
	 */
	public Address getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setAddress(com.redbugz.macpaf.Address)
	 */
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getMultimediaLink()
	 */
	public MultimediaLink getMultimediaLink() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setMultimediaLink(com.redbugz.macpaf.jdom.MultimediaLink)
	 */
	public void setMultimediaLink(MultimediaLink multimediaLink) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getLanguagePreferences()
	 */
	public List getLanguagePreferences() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setLanguagePreferences(java.util.List)
	 */
	public void setLanguagePreferences(List languagePreferences) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getRFN()
	 */
	public String getRFN() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setRFN()
	 */
	public String setRFN() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getRIN()
	 */
	public Integer getRIN() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setRIN(java.lang.Integer)
	 */
	public void setRIN(Integer rin) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getChangeDate()
	 */
	public Date getChangeDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setChangeDate(java.util.Date)
	 */
	public void setChangeDate(Date changeDate) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#getId()
	 */
	public String getId() {
		return getSubmitter().getId();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Submitter#setId(java.lang.String)
	 */
	public void setId(String id) {
		getSubmitter().setId(id);
	}


}
