/*
 * Created on Nov 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.maf.jdom;

import com.redbugz.macpaf.MacPAFDocumentJDOM;
import com.redbugz.maf.Repository;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepositoryLink implements Repository {
	private Repository actualRepository = null;
	private MacPAFDocumentJDOM document = null;
	String id = "";

	RepositoryLink(MacPAFDocumentJDOM doc) {
		document = doc;
	}
	
	RepositoryLink(String id, MacPAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Repository getRepository() {
		if (actualRepository == null) {
			actualRepository = document.getRepositoryJDOM(id);
		}
		return actualRepository;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Repository#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Repository#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Repository#getName()
	 */
	public String getName() {
		return getRepository().getName();
	}

}
