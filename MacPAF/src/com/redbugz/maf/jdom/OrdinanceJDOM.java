package com.redbugz.maf.jdom;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.redbugz.maf.Ordinance;
import com.redbugz.maf.Temple;
import com.redbugz.maf.util.JDOMUtils;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:40:34 PM
 * To change this template use Options | File Templates.
 */
public class OrdinanceJDOM extends EventJDOM implements Ordinance {
  private static final Logger log = Logger.getLogger(OrdinanceJDOM.class);
  public static final String LDS_BAPTISM = "BAPL";
  public static final String LDS_CONFIRMATION = "CONL";
  public static final String LDS_ENDOWMENT = "ENDL";
  public static final String LDS_SEALING_SPOUSE = "SLGS";
  public static final String LDS_SEALING_PARENTS = "SLGC";
  public static final String STATUS = "STAT";

  public OrdinanceJDOM(Element newElement) {
	super(newElement);
//	log.debug("OrdinanceJDOM status=" + getStatus() + " temple=" + getTemple().getName());
  }

  public OrdinanceJDOM(Ordinance originalOrdinance) {
	  super(originalOrdinance);
	  if (originalOrdinance instanceof OrdinanceJDOM) {
		  element = ( (OrdinanceJDOM) originalOrdinance).getElement();
	  }
	  else {
		  setStatus(originalOrdinance.getStatus());
		  setTemple(originalOrdinance.getTemple());
	  }
  }

public static OrdinanceJDOM createBaptismInstance() {
	return new OrdinanceJDOM(new Element(LDS_BAPTISM));
  }

  public static OrdinanceJDOM createConfirmationInstance() {
	return new OrdinanceJDOM(new Element(LDS_CONFIRMATION));
  }

  public static OrdinanceJDOM createEndowmentInstance() {
	return new OrdinanceJDOM(new Element(LDS_ENDOWMENT));
  }

  public static OrdinanceJDOM createSealingToSpouseInstance() {
	return new OrdinanceJDOM(new Element(LDS_SEALING_SPOUSE));
  }

  public static OrdinanceJDOM createSealingToParentsInstance() {
	return new OrdinanceJDOM(new Element(LDS_SEALING_PARENTS));
  }

  public Temple getTemple() {
	return new TempleJDOM(JDOMUtils.findOrMakeChildElement(TempleJDOM.TEMPLE, element));
  }

  public String getStatus() {
	String status = element.getChildTextTrim(STATUS);
	if (status == null) {
	  status = "";
	}
	return status;
  }

  public void setTemple(Temple temple) {
	log.debug("OrdinanceJDOM.setTemple():" + temple);
	JDOMUtils.findOrMakeChildElement(TempleJDOM.TEMPLE, element).setText(temple.getCode());
  }

  public boolean isCompleted() {
	return COMPLETED.equals(getStatus());
  }

  public void setStatus(String ordinanceStatus) {
	JDOMUtils.findOrMakeChildElement(STATUS, element).setText(ordinanceStatus);
  }
}
