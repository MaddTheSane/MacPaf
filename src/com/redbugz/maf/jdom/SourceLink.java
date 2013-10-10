/*
 * Created on Nov 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.maf.jdom;

import com.redbugz.maf.Source;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SourceLink implements Source {
	private Source actualSource = null;
	private MAFDocumentJDOM document = null;
	String id = "";

	SourceLink(MAFDocumentJDOM doc) {
		document = doc;
	}
	
	SourceLink(String id, MAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Source getSource() {
		if (actualSource == null) {
			actualSource = document.getSourceJDOM(id);
		}
		return actualSource;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Source#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Source#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Source#getText()
	 */
	public String getText() {
		return getSource().getText();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Source#getTitle()
	 */
	public String getTitle() {
		return getSource().getTitle();
	}

}
