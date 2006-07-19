//
//  TempleList.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 09 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

package com.redbugz.macpaf;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.*;

import com.apple.cocoa.application.*;

public class TempleXML implements NSComboBox.DataSource {
  private static final Logger log = Logger.getLogger(TempleXML.class);
  private String[] templeStrings = {"SLAKE", "MTIMP", "SGEORG", "NAUVO", "OTHER"};
  private Vector temples = new Vector();
  private static TempleXML instance;

  public TempleXML() {
  }

  public int numberOfItemsInComboBox(NSComboBox comboBox) {
//        log.debug("numberOfItemsInComboBox: "+temples.size()+comboBox);
	return temples.size();
  }

  public Object comboBoxObjectValueForItemAtIndex(NSComboBox comboBox, int index) {
//        log.debug("comboBoxObjectValueForItemAtIndex: "+index);
	return temples.get(index).toString();
  }

  public String comboBoxCompletedString(NSComboBox aComboBox, String uncompletedString) {
//        log.debug("comboBoxCompletedString: "+uncompletedString);
	String result = uncompletedString;
	for (int i = 0; i < temples.size(); i++) {
	  String test = temples.get(i).toString().toLowerCase();
	  if (test.startsWith(uncompletedString.toLowerCase())) {
		return test;
	  }
	}
	return result;
  }

  public int comboBoxIndexOfItem(NSComboBox aComboBox, String aString) {
//        log.debug("comboBoxIndexOfItem: "+aString);
	for (int i = 0; i < temples.size(); i++) {
	  String test = temples.get(i).toString();
	  if (test.equalsIgnoreCase(aString)) {
		return i;
	  }
	}
	return -1;
  }

  public void setTempleList(Vector temples) {
	this.temples = temples;
  }

  public Vector getTempleList() {
	return temples;
  }

  public static Temple templeWithCode(String templeCode) {
	TempleXML tl = getInstance();
	for (Iterator iterator = tl.getTempleList().iterator(); iterator.hasNext(); ) {
	  Temple temple = (Temple) iterator.next();
	  if (temple.getCode().equals(templeCode)) {
		return temple;
	  }
	}
	return new Temple();
  }

  private static TempleXML getInstance() {
	if (instance == null) {
	  instance = new TempleXML();
	}
	return instance;
  }

}
