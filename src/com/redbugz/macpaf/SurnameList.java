//
//  TempleList.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 09 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

package com.redbugz.macpaf;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;

import com.apple.cocoa.application.NSComboBox;
import com.apple.cocoa.foundation.NSBundle;


public class SurnameList implements NSComboBox.DataSource {
    private Vector surnames = new Vector();
   private static SurnameList instance = null;

   public SurnameList() {
        System.out.println("SurnameList(): "+this);
    }

    public int numberOfItemsInComboBox(NSComboBox comboBox) {
//        System.out.println("numberOfItemsInComboBox: "+surnames.size()+comboBox);
        return surnames.size();
    }
    public Object comboBoxObjectValueForItemAtIndex(NSComboBox comboBox, int index) {
//        System.out.println("comboBoxObjectValueForItemAtIndex: "+index);
        return surnames.get(index).toString();
    }

    public String comboBoxCompletedString(NSComboBox aComboBox, String uncompletedString) {
//        System.out.println("comboBoxCompletedString: "+uncompletedString);
        String result = uncompletedString;
        for (int i=0; i< surnames.size(); i++) {
            String test = surnames.get(i).toString().toLowerCase();
            if (test.startsWith(uncompletedString.toLowerCase())) {
                return test;
            }
        }
        return result;
    }

    public int comboBoxIndexOfItem(NSComboBox aComboBox, String aString) {
//        System.out.println("comboBoxIndexOfItem: "+aString);
        for (int i=0; i< surnames.size(); i++) {
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
