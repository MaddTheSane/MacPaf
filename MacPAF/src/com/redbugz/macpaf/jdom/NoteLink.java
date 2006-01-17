/*
 * Created on Nov 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.macpaf.jdom;

import com.redbugz.macpaf.Note;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NoteLink implements Note {
	private Note actualNote = null;
	private MacPAFDocumentJDOM document = null;
	String id = "";

	NoteLink(MacPAFDocumentJDOM doc) {
		document = doc;
	}
	
	NoteLink(String id, MacPAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Note getNote() {
		if (actualNote == null) {
			actualNote = document.getNote(id);
		}
		return actualNote;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Note#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Note#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Note#getText()
	 */
	public String getText() {
		// TODO Auto-generated method stub
		return getNote().getText();
	}

}