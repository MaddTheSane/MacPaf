package com.redbugz.macpaf;

//
//  Gender.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

public final class Gender {
  private String _gender;
  private String _abbrev;

  public static final Gender MALE = new Gender("Male", "M");
  public static final Gender FEMALE = new Gender("Female", "F");
  public static final Gender UNKNOWN = new Gender("Unknown", "");

  private Gender() {
  }

  private Gender(String gender, String abbreviation) {
	_gender = gender;
	_abbrev = abbreviation;
  }

  public static Gender genderWithCode(String code) {
	if (MALE.getAbbreviation().equalsIgnoreCase(code) || MALE.getLongString().equalsIgnoreCase(code)) {
	  return MALE;
	}
	if (FEMALE.getAbbreviation().equalsIgnoreCase(code) || FEMALE.getLongString().equalsIgnoreCase(code)) {
	  return FEMALE;
	}
	return UNKNOWN;
  }

  public String getLongString() {
	return _gender;
  }

  public String getAbbreviation() {
	return _abbrev;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
	return "Gender[" + getLongString() + "(" + getAbbreviation() + ")]";
  }

}
