package com.redbugz.macpaf.test;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.redbugz.macpaf.Header;
import com.redbugz.macpaf.jdom.HeaderJDOM;
import com.redbugz.macpaf.jdom.MacPAFDocumentJDOM;

import junit.framework.TestCase;

public class HeaderJDOMTest extends TestCase {

	private static final Logger log = Logger.getLogger(HeaderJDOMTest.class.getName());
	private HeaderJDOM emptyHeader;
	private HeaderJDOM standardHeader;

	protected void setUp() throws Exception {
		super.setUp();
		MacPAFDocumentJDOM doc = new MacPAFDocumentJDOM();
		standardHeader = new HeaderJDOM(((HeaderJDOM) doc.getHeader()).getElement(), doc);
		emptyHeader = new HeaderJDOM(new Element(Header.HEADER), new MacPAFDocumentJDOM());
	}
	
	public void testFields() {
		HeaderJDOM header = standardHeader;
		log.debug("header: " + header);
		log.debug("Header charset:"+header.getCharacterSet());
		log.debug("Header charversion:"+header.getCharacterVersionNumber());
		log.debug("Header copyright:"+header.getCopyright());
		log.debug("Header destination:"+header.getDestination());
		log.debug("Header filename:"+header.getFileName());
		log.debug("Header gedcomform:"+header.getGedcomForm());
		log.debug("Header gedcomversion:"+header.getGedcomVersion());
		log.debug("Header language:"+header.getLanguage());
		log.debug("Header placeformat:"+header.getPlaceFormat());
		log.debug("Header sourcecorp:"+header.getSourceCorporation());
		log.debug("Header sourcecorpaddr:"+header.getSourceCorporationAddress());
		log.debug("Header sourcedata:"+header.getSourceData());
		log.debug("Header sourcedatacopyright:"+header.getSourceDataCopyright());
		log.debug("Header sourceid:"+header.getSourceId());
		log.debug("Header sourcename:"+header.getSourceName());
		log.debug("Header sourceversion:"+header.getSourceVersion());
		log.debug("Header sourcedatadate:"+header.getSourceDataDate());
		log.debug("Header submission:"+header.getSubmission());
//		log.debug("Header submitter:"+header.getSubmitter());
		log.debug("Header creationdate:"+header.getCreationDate());
		log.debug("Header note:"+header.getNote());
		log.debug("Header element:"+header.getElement());

		header = emptyHeader;
		log.debug("header: " + header);
		log.debug("Header charset:"+header.getCharacterSet());
		log.debug("Header charversion:"+header.getCharacterVersionNumber());
		log.debug("Header copyright:"+header.getCopyright());
		log.debug("Header destination:"+header.getDestination());
		log.debug("Header filename:"+header.getFileName());
		log.debug("Header gedcomform:"+header.getGedcomForm());
		log.debug("Header gedcomversion:"+header.getGedcomVersion());
		log.debug("Header language:"+header.getLanguage());
		log.debug("Header placeformat:"+header.getPlaceFormat());
		log.debug("Header sourcecorp:"+header.getSourceCorporation());
		log.debug("Header sourcecorpaddr:"+header.getSourceCorporationAddress());
		log.debug("Header sourcedata:"+header.getSourceData());
		log.debug("Header sourcedatacopyright:"+header.getSourceDataCopyright());
		log.debug("Header sourceid:"+header.getSourceId());
		log.debug("Header sourcename:"+header.getSourceName());
		log.debug("Header sourceversion:"+header.getSourceVersion());
		log.debug("Header sourcedatadate:"+header.getSourceDataDate());
		log.debug("Header submission:"+header.getSubmission());
//		log.debug("Header submitter:"+header.getSubmitter());
		log.debug("Header creationdate:"+header.getCreationDate());
		log.debug("Header note:"+header.getNote());
		log.debug("Header element:"+header.getElement());		
	}

	/*
	 * Test method for 'com.redbugz.macpaf.jdom.HeaderJDOM.getGedcomVersion()'
	 */
	public void testGetGedcomVersion() {
		assertNotNull(emptyHeader.getGedcomVersion());
		assertNotNull(standardHeader.getGedcomVersion());
		assertEquals(Header.GEDCOM_VERSION_55, standardHeader.getGedcomVersion());
	}
	
}
