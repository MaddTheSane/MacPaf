package com.redbugz.macpaf;

import java.util.*;

/*
 * Created on Oct 5, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface Multimedia {
  public static Multimedia UNKNOWN_MULTIMEDIA = new UnknownMultimedia();

  public static final String JPEG_FORMAT = "jpeg";
  public static final String GIF_FORMAT = "gif";
  public static final String BITMAP_FORMAT = "bmp";
  public static final String PICT_FORMAT = "PICT";
  public static final String OLE_FORMAT = "ole";
  public static final String PCX_FORMAT = "pcx";
  public static final String TIFF_FORMAT = "tiff";
  public static final String WAV_FORMAT = "wav";
  
  public String getId();

  public void setId(String id);

  public String getFormat();

  public void setFormat(String format);

  public String getTitle();

  public void setTitle(String title);

  public List getNotes(); // List of Note

  public void setNotes(List noteList);

  public byte[] getBytes();

  public void setBytes(byte[] bytes);

  public Integer getRIN();

  public void setRIN(Integer rin);

  public Date getChangeDate();

  public void setChangeDate(Date changeDate);

  public boolean isImage();

  static class UnknownMultimedia implements Multimedia {

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getId()
	 */
	public String getId() {
		// TODO Auto-generated method stub
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setId(java.lang.String)
	 */
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getFormat()
	 */
	public String getFormat() {
		// TODO Auto-generated method stub
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setFormat(java.lang.String)
	 */
	public void setFormat(String format) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getTitle()
	 */
	public String getTitle() {
		// TODO Auto-generated method stub
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getNotes()
	 */
	public List getNotes() {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setNotes(java.util.List)
	 */
	public void setNotes(List noteList) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getBytes()
	 */
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return new byte[0];
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setBytes(byte[])
	 */
	public void setBytes(byte[] bytes) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getRIN()
	 */
	public Integer getRIN() {
		// TODO Auto-generated method stub
		return new Integer(-1);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setRIN(java.lang.Integer)
	 */
	public void setRIN(Integer rin) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#getChangeDate()
	 */
	public Date getChangeDate() {
		// TODO Auto-generated method stub
		return new Date();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#setChangeDate(java.util.Date)
	 */
	public void setChangeDate(Date changeDate) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Multimedia#isImage()
	 */
	public boolean isImage() {
		return false;
	}
  }
}
