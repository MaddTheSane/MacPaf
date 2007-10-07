package com.redbugz.maf;

import java.util.List;

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
public interface Address {
  public static final String ADDRESS = "ADDR";
  public static final String CONTINUATION = "CONT";
  public static final String ADDRESS_LINE1 = "ADR1";
  public static final String ADDRESS_LINE2 = "ADR2";
  public static final String CITY = "CITY";
  public static final String STATE = "STAE";
  public static final String POSTAL_CODE = "POST";
  public static final String COUNTRY = "CTRY";
  public static final String PHONE = "PHON";

  public List getAddressLines(); // List of String

  public void setAddressLines(List addressLines);

  public String getAddressLine1();

  public void setAddressLine1(String line1);

  public String getAddressLine2();

  public void setAddressLine2(String line2);

  public String getCity();

  public void setCity(String city);

  public String getState();

  public void setState(String state);

  public String getPostalCode();

  public void setPostalCode(String postalCode);

  public String getCountry();

  public void setCountry(String country);

  public String getPhoneNumber();

  public void setPhoneNumber(String phoneNumber);

  public List getPhoneNumbers(); // List of String

  public void setPhoneNumbers(List phoneNumbers);
}
