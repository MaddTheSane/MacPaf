package com.redbugz.macpaf;
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
