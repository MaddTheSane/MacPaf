/*
 * Created on Oct 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.redbugz.macpaf.jdom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Element;
import com.redbugz.macpaf.Multimedia;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MultimediaJDOM implements Multimedia {
  private static final String ID = "ID";
  private static final String FORMAT = "FORM";
  private static final String TITLE = "TITLE";
  private static final String BLOB = "BLOB";
  private static final String RIN = "RIN";

  Element element = new Element("OBJE");

  /**
   * @param element
   */
  public MultimediaJDOM(Element element) {
	this.element = element;
  }

  /* (non-Javadoc)
   * @see Multimedia#getBytes()
   */
  public byte[] getBytes() {
	// TODO Auto-generated method stub
	return new byte[0];
  }

  /* (non-Javadoc)
   * @see Multimedia#getChangeDate()
   */
  public Date getChangeDate() {
	// TODO Auto-generated method stub
	return new Date();
  }

  /* (non-Javadoc)
   * @see Multimedia#getFormat()
   */
  public String getFormat() {
	return element.getChildTextTrim(FORMAT);
  }

  /* (non-Javadoc)
   * @see Multimedia#getNotes()
   */
  public List getNotes() {
	// TODO Auto-generated method stub
	return new ArrayList();
  }

  /* (non-Javadoc)
   * @see Multimedia#getRIN()
   */
  public Integer getRIN() {
	return new Integer(element.getChildTextTrim(RIN));
  }

  /* (non-Javadoc)
   * @see Multimedia#getTitle()
   */
  public String getTitle() {
	return element.getChildTextTrim(TITLE);
  }

  /* (non-Javadoc)
   * @see Multimedia#setBytes(byte[])
   */
  public void setBytes(byte[] bytes) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Multimedia#setChangeDate(java.util.Date)
   */
  public void setChangeDate(Date changeDate) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Multimedia#setFormat(java.lang.String)
   */
  public void setFormat(String format) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Multimedia#setNotes(java.util.List)
   */
  public void setNotes(List noteList) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Multimedia#setRIN(java.lang.Integer)
   */
  public void setRIN(Integer rin) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Multimedia#setTitle(java.lang.String)
   */
  public void setTitle(String title) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see com.redbugz.macpaf.Multimedia#getId()
   */
  public String getId() {
	return element.getAttributeValue(ID);
  }

  /* (non-Javadoc)
   * @see com.redbugz.macpaf.Multimedia#setId(java.lang.String)
   */
  public void setId(String id) {
	// TODO Auto-generated method stub

  }

}
