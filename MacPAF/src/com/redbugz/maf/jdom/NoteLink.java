/*
 * Created on Nov 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.maf.jdom;

import com.redbugz.maf.Note;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NoteLink implements Note {
	private Note actualNote = null;
	private MAFDocumentJDOM document = null;
	String id = "";

	NoteLink(MAFDocumentJDOM doc) {
		document = doc;
	}
	
	NoteLink(String id, MAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Note getNote() {
		if (actualNote == null) {
			actualNote = document.getNoteJDOM(id);
		}
		return actualNote;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Note#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Note#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Note#getText()
	 */
	public String getText() {
		return getNote().getText();
	}

	public void setText(String newText) {
		getNote().setText(newText);
	}

}
