/*
 * Created on Oct 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.redbugz.maf.jdom;

import org.jdom.*;

import com.redbugz.macpaf.MacPAFDocumentJDOM;
import com.redbugz.macpaf.SubmitterLink;
import com.redbugz.maf.*;

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SubmissionJDOM implements Submission {
  Element element = new Element(SUBMISSION);
private MacPAFDocumentJDOM document;

  public SubmissionJDOM(Element element, MacPAFDocumentJDOM parentDocument) {
  	if (parentDocument == null) {
  		throw new IllegalArgumentException("Cannot create SubmissionJDOM with null parentDocument");
  	}
	if (element == null) {
  		throw new IllegalArgumentException("Cannot create SubmissionJDOM with null element");
	}
	document = parentDocument;
	this.element = element;
  }

  /* (non-Javadoc)
   * @see Submission#getSubmitter()
   */
  public Submitter getSubmitter() {
	// TODO Auto-generated method stub
	return new SubmitterLink(document);
  }

  /* (non-Javadoc)
   * @see Submission#getFamilyFile()
   */
  public String getFamilyFile() {
	// TODO Auto-generated method stub
	return "";
  }

  /* (non-Javadoc)
   * @see Submission#getTempleCode()
   */
  public String getTempleCode() {
	// TODO Auto-generated method stub
	return "";
  }

  /* (non-Javadoc)
   * @see Submission#getAncestorsGenerations()
   */
  public Integer getAncestorsGenerations() {
	// TODO Auto-generated method stub
	return new Integer(0);
  }

  /* (non-Javadoc)
   * @see Submission#getDescendantsGenerations()
   */
  public Integer getDescendantsGenerations() {
	// TODO Auto-generated method stub
	return new Integer(0);
  }

  /* (non-Javadoc)
   * @see Submission#getOrdinanceProcessFlag()
   */
  public Boolean getOrdinanceProcessFlag() {
	// TODO Auto-generated method stub
	return Boolean.FALSE;
  }

  /* (non-Javadoc)
   * @see Submission#getRIN()
   */
  public Integer getRIN() {
	// TODO Auto-generated method stub
	return new Integer(0);
  }

}
