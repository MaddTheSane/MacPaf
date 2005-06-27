/*
 * Created on Oct 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.redbugz.macpaf.jdom;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;
import com.redbugz.macpaf.Multimedia;
import com.redbugz.macpaf.util.Base64;
import com.redbugz.macpaf.util.MultimediaUtils;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MultimediaJDOM implements Multimedia {
	private static final Logger log = Logger.getLogger(MultimediaJDOM.class);
  private static final String ID = "ID";
  private static final String FORMAT = "FORM";
  private static final String TITLE = "TITLE";
  private static final String BLOB = "BLOB";
  private static final String RIN = "RIN";
  public static final String MULTIMEDIA = "OBJE";

  Element element = new Element(MULTIMEDIA);
private MacPAFDocumentJDOM document;

  /**
   * @param element
 * @param parentDocument TODO
   */
  public MultimediaJDOM(Element element, MacPAFDocumentJDOM parentDocument) {
  	if (parentDocument == null) {
  		throw new IllegalArgumentException("Cannot create MultimediaJDOM with null parentDocument");
  	}
	if (element == null) {
  		throw new IllegalArgumentException("Cannot create MultimediaJDOM with null element");
	}
	document = parentDocument;
	this.element = element;
  }

  /* (non-Javadoc)
   * @see Multimedia#getBytes()
   */
  public byte[] getBytes() {
  	int numBytes = 0;
  	Element obje = element;
    log.debug("ref=" + obje.getAttributeValue("REF"));
    log.debug("form=" + obje.getChildText("FORM"));
    log.debug("title=" + obje.getChildText("TITL"));
    log.debug("file=" + obje.getChildText("FILE"));
    log.debug("blob=" + obje.getChildText("BLOB"));
    Element file = obje.getChild("FILE");
    if (file != null) {
    		try {
				return MultimediaUtils.getBytesFromFile(MultimediaUtils.findFile(file.getTextTrim()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    Element blob = obje.getChild("BLOB");
    if (blob != null) {
    StringBuffer buf = new StringBuffer(blob.getText());
    List conts = blob.getChildren("CONT");
    for (int j = 0; j < conts.size(); j++) {
        Element cont = (Element) conts.get(j);
        buf.append(cont.getText());
    }
    log.debug("content=" + buf.toString());
    log.debug("buflen=" + buf.length());
    
	// TODO fix Base64 to use gedcom multimedia mapping
	return Base64.decode(buf.toString());
    }
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
  	element.setAttribute(ID, id);
  }

/* (non-Javadoc)
 * @see com.redbugz.macpaf.Multimedia#isImage()
 */
public boolean isImage() {
	if ((JPEG_FORMAT+BITMAP_FORMAT+GIF_FORMAT+PCX_FORMAT+PICT_FORMAT).indexOf(getFormat()) >= 0) {
		return true;
	}
	return false;
}

/**
 * @return
 */
public Element getElement() {
	// TODO Auto-generated method stub
	return element;
}

}
