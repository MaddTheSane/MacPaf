package com.redbugz.macpaf;

import java.util.Date;
import java.util.List;

import com.redbugz.macpaf.jdom.MultimediaLink;

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
public interface Submitter {
  public static final String SUBMITTER = "SUBM";
  public static final String NAME = "NAME";
  public static final String LANGUAGE = "LANG";
  public static final String RFN = "RFN";
  public static final String RIN = "RIN";
  public static final String ID = "ID";


  public String getName();

  public void setName(String name);

  public Address getAddress();

  public void setAddress(Address address);

  public MultimediaLink getMultimediaLink();

  public void setMultimediaLink(MultimediaLink multimediaLink);

  public List getLanguagePreferences();

  public void setLanguagePreferences(List languagePreferences);

  public String getRFN();

  public void setRFN(String rfn);

  public Integer getRIN();

  public void setRIN(Integer rin);

  public Date getChangeDate();

  public void setChangeDate(Date changeDate);

  public String getId();
  
  public void setId(String id);
}

