package com.redbugz.macpaf.jdom;

import java.util.Date;
import java.util.List;

import com.redbugz.macpaf.Multimedia;

/*
 * Created on Oct 5, 2003
 *
 * @author logan
 *
 */
public class MultimediaLink implements Multimedia {
	private Multimedia actualMultimedia = null;
	private MacPAFDocumentJDOM document = null;
	String id = "";

	MultimediaLink(MacPAFDocumentJDOM doc) {
		document = doc;
	}
	
	MultimediaLink(String id, MacPAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Multimedia getMultimedia() {
		if (actualMultimedia == null) {
			actualMultimedia = document.getMultimedia(id);
		}
		return actualMultimedia;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getFormat()
	 */
	public String getFormat() {
		return getMultimedia().getFormat();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setFormat(java.lang.String)
	 */
	public void setFormat(String format) {
		getMultimedia().setFormat(format);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getTitle()
	 */
	public String getTitle() {
		return getMultimedia().getTitle();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		getMultimedia().setTitle(title);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getNotes()
	 */
	public List getNotes() {
		return getMultimedia().getNotes();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setNotes(java.util.List)
	 */
	public void setNotes(List noteList) {
		getMultimedia().setNotes(noteList);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getBytes()
	 */
	public byte[] getBytes() {
		return getMultimedia().getBytes();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setBytes(byte[])
	 */
	public void setBytes(byte[] bytes) {
		getMultimedia().setBytes(bytes);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getRIN()
	 */
	public Integer getRIN() {
		return getMultimedia().getRIN();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setRIN(java.lang.Integer)
	 */
	public void setRIN(Integer rin) {
		getMultimedia().setRIN(rin);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getChangeDate()
	 */
	public Date getChangeDate() {
		return getMultimedia().getChangeDate();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setChangeDate(java.util.Date)
	 */
	public void setChangeDate(Date changeDate) {
		getMultimedia().setChangeDate(changeDate);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#isImage()
	 */
	public boolean isImage() {
		return getMultimedia().isImage();
	}

}
