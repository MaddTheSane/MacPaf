//
//  TempleList.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 09 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

package com.redbugz.macpaf;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;

import com.apple.cocoa.application.NSComboBox;
import com.apple.cocoa.foundation.NSBundle;

public class LocationList implements NSComboBox.DataSource {
  private static final Logger log = Logger.getLogger(LocationList.class);
  private Vector temples = new Vector();
  private static LocationList instance;

  public LocationList() {
	log.debug("LocationList(): " + this);
//      loadListFromFile();

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

  private void loadListFromFile() {
	log.debug("Loading TempleList...");

	Mapping mapping = new Mapping();

	try {
	  // 1. Load the mapping information from the file
	  mapping.loadMapping(NSBundle.mainBundle().resourcePath() + "/temple-mapping.xml");

	  // 2. Unmarshal the data
	  Unmarshaller unmar = new Unmarshaller(mapping);
	  TempleXML tempList = (TempleXML) unmar.unmarshal(new InputSource(new FileReader(NSBundle.mainBundle().
		  resourcePath() + "/TempleList.xml")));
//           instance = (TempleList)unmar.unmarshal(new InputSource(new FileReader(NSBundle.mainBundle().resourcePath()+"/TempleList.xml")));
	  setTempleList(tempList.getTempleList());

	  // 3. Do some processing on the data
	  // 4. marshal the data with the total price back and print the XML in the console
//            Marshaller marshaller = new Marshaller(new OutputStreamWriter(System.out));
//            marshaller.setMapping(mapping);
//            marshaller.marshal(order);

	}
	catch (Exception e) {
	  log.debug(e);
	  return;
	}
  }

  public static Temple templeWithCode(String templeCode) {
	LocationList tl = getInstance();
	for (Iterator iterator = tl.getTempleList().iterator(); iterator.hasNext(); ) {
	  Temple temple = (Temple) iterator.next();
	  if (temple.getCode().equals(templeCode)) {
		return temple;
	  }
	}
	return new Temple();
  }

  private static LocationList getInstance() {
	if (instance == null) {
	  instance = new LocationList();
	}
	return instance;
  }

}
