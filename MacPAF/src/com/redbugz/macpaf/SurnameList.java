//
//  TempleList.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 09 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

package com.redbugz.macpaf;

import java.util.Vector;

import org.apache.log4j.*;

import com.apple.cocoa.application.*;

public class SurnameList implements NSComboBox.DataSource {
  private static final Logger log = Logger.getLogger(SurnameList.class);
  private Vector surnames = new Vector();
  private static SurnameList instance = null;

  public SurnameList() {
	log.debug("SurnameList(): " + this);
  }

  public int numberOfItemsInComboBox(NSComboBox comboBox) {
//        log.debug("numberOfItemsInComboBox: "+surnames.size()+comboBox);
	return surnames.size();
  }

  public Object comboBoxObjectValueForItemAtIndex(NSComboBox comboBox, int index) {
//        log.debug("comboBoxObjectValueForItemAtIndex: "+index);
	return surnames.get(index).toString();
  }

  public String comboBoxCompletedString(NSComboBox aComboBox, String uncompletedString) {
//        log.debug("comboBoxCompletedString: "+uncompletedString);
	String result = uncompletedString;
	for (int i = 0; i < surnames.size(); i++) {
	  String test = surnames.get(i).toString().toLowerCase();
	  if (test.startsWith(uncompletedString.toLowerCase())) {
		return test;
	  }
	}
	return result;
  }

  public int comboBoxIndexOfItem(NSComboBox aComboBox, String aString) {
//        log.debug("comboBoxIndexOfItem: "+aString);
	for (int i = 0; i < surnames.size(); i++) {
	  String test = surnames.get(i).toString();
	  if (test.equalsIgnoreCase(aString)) {
		return i;
	  }
	}
	return -1;
  }

  public static SurnameList getInstance() {
	if (instance == null) {
	  instance = new SurnameList();
	}
	return instance;
  }

  public void add(String surname) {
	if (!surnames.contains(surname)) {
	  surnames.add(surname);
	}
  }

}
