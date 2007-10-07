/*
 * Created on Oct 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.redbugz.macpaf.jdom;

import java.text.*;
import java.util.*;

import org.apache.log4j.*;
import org.jdom.*;

import com.redbugz.macpaf.*;
import com.redbugz.macpaf.util.*;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class HeaderJDOM implements Header {
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");
private static final Logger log = Logger.getLogger(HeaderJDOM.class);
private MacPAFDocumentJDOM document = null;
  Element element = new Element(HEADER);

  public HeaderJDOM(Submitter submitter) {
	Element source = new Element(SOURCE).setText(MACPAF_APPROVED_SYSTEM_ID);
	source.addContent(new Element(VERSION).setText(MACPAF_VERSION_NUMBER));
	source.addContent(new Element(NAME).setText(MACPAF_NAME_OF_PRODUCT));
	AddressJDOM addr = new AddressJDOM(MACPAF_BUSINESS_ADDR1, MACPAF_BUSINESS_ADDR2, MACPAF_BUSINESS_CITY, MACPAF_BUSINESS_STATE, MACPAF_BUSINESS_ZIP,
									   MACPAF_BUSINESS_COUNTRY, MACPAF_BUSINESS_PHONE);
	List list = new ArrayList();
	list.add(new Text(MACPAF_NAME_OF_BUSINESS));
	list.addAll(addr.getElements());
	log.debug("headerjdom list:" + list);
	source.addContent( (Content)new Element(CORPORATION).setContent(list));

	element.addContent(source);
	element.addContent(new Element(DATE).setText(SIMPLE_DATE_FORMAT.format(new Date())));
	element.addContent(new Element(SUBMITTER).setAttribute(REF, submitter.getId()));
	element.addContent( (Content)new Element(GEDCOM).addContent(new Element(VERSION).setText(GEDCOM_VERSION_55)).
					   addContent(new Element(FORM).setText(FORM_LINEAGE_LINKED)));
	element.addContent(new Element(CHARACTER).setText(ANSEL));
	element.addContent(new Element(LANGUAGE).setText(LANG_ENGLISH));
  }

  public HeaderJDOM(Element element, MacPAFDocumentJDOM doc) {
	this.element = element;
	document = doc;
  }

  public Element getElement() {
	return element;
  }

  /* (non-Javadoc)
   * @see Header#getSourceId()
   */
  public String getSourceId() {
	return JDOMUtils.getNonNullChildText(element.getChild(SOURCE));
  }

  /* (non-Javadoc)
   * @see Header#getSourceVersion()
   */
  public String getSourceVersion() {
		return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {SOURCE,VERSION})+")");
  }

  /* (non-Javadoc)
   * @see Header#getSourceName()
   */
  public String getSourceName() {
		return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {SOURCE,NAME})+")");
  }

  /* (non-Javadoc)
   * @see Header#getSourceCorporation()
   */
  public String getSourceCorporation() {
		return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {SOURCE,CORPORATION})+")");
  }

  /* (non-Javadoc)
   * @see Header#getSourceCorporationAddress()
   */
  public Address getSourceCorporationAddress() {
	return new AddressJDOM(JDOMUtils.getXpathElement(element, StringUtils.join("/", new String[] {SOURCE,CORPORATION,Address.ADDRESS})));
  }

  /* (non-Javadoc)
   * @see Header#getSourceData()
   */
  public String getSourceData() {
	return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {SOURCE,DATA})+")");
  }

  /* (non-Javadoc)
   * @see Header#getSourceDataDate()
   */
  public Date getSourceDataDate() {
	return JDOMUtils.dateFromElementText(JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {SOURCE,DATA,DATE})+")"));
  }

  /* (non-Javadoc)
   * @see Header#getSourceDataCopyright()
   */
  public String getSourceDataCopyright() {
		return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {SOURCE,DATA,COPYRIGHT})+")");
  }

  /* (non-Javadoc)
   * @see Header#getDestination()
   */
  public String getDestination() {
	return JDOMUtils.getNonNullChildText(element.getChild(DESTINATION));
  }

  /* (non-Javadoc)
   * @see Header#getCreationDate()
   */
  public Date getCreationDate() {
	return JDOMUtils.dateFromElementText(JDOMUtils.getNonNullChildText(element.getChild(DATE)));
  }

  /* (non-Javadoc)
   * @see Header#getSubmitter()
   */
  public Submitter getSubmitter() {
	return document.getSubmitterJDOM(JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {SUBMITTER,"@"+REF})+")"));
  }

  /* (non-Javadoc)
   * @see Header#getSubmission()
   */
  public Submission getSubmission() {
	return document.getSubmission();
  }

  /* (non-Javadoc)
   * @see Header#getFileName()
   */
  public String getFileName() {
	return JDOMUtils.getNonNullChildText(element.getChild(DESTINATION));
  }

  /* (non-Javadoc)
   * @see Header#getCopyright()
   */
  public String getCopyright() {
	return JDOMUtils.getNonNullChildText(element.getChild(COPYRIGHT));
  }

  /* (non-Javadoc)
   * @see Header#getGedcomVersion()
   */
  public String getGedcomVersion() {
	  return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {GEDCOM,VERSION})+")");
  }

  /* (non-Javadoc)
   * @see Header#getGedcomForm()
   */
  public String getGedcomForm() {
	  return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {GEDCOM,FORM})+")");
  }

  /* (non-Javadoc)
   * @see Header#getCharacterSet()
   */
  public String getCharacterSet() {
	return JDOMUtils.getNonNullChildText(element.getChild(CHARACTER));
  }

  /* (non-Javadoc)
   * @see Header#getCharacterVersionNumber()
   */
  public String getCharacterVersionNumber() {
	  return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {CHARACTER,VERSION})+")");
  }

  /* (non-Javadoc)
   * @see Header#getLanguage()
   */
  public String getLanguage() {
		return JDOMUtils.getNonNullChildText(element.getChild(LANGUAGE));
  }

  /* (non-Javadoc)
   * @see Header#getPlaceFormat()
   */
  public String getPlaceFormat() {
	  return JDOMUtils.getNonNullXpathText(element, "string("+StringUtils.join("/", new String[] {PLACE,FORM})+")");
  }

  /* (non-Javadoc)
   * @see Header#getNote()
   */
  public Note getNote() {
	  Note result = Note.UNKNOWN_NOTE;
	  Element noteElement = element.getChild(Note.NOTE);
	  if (noteElement != null) {
		  new NoteJDOM(noteElement, document);
	  }
	return result;
  }

public void setDestination(String destinationString) {
	Element destElement = element.getChild(DESTINATION);
	destElement.setText(destinationString);
}

}
