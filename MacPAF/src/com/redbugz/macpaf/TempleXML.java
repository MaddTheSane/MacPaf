//
//  TempleList.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Mar 09 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

package com.redbugz.macpaf;
import java.util.Iterator;
import java.util.Vector;

import com.apple.cocoa.application.NSComboBox;


public class TempleXML implements NSComboBox.DataSource {
    private String[] templeStrings = {"SLAKE", "MTIMP", "SGEORG", "NAUVO", "OTHER"};
    private Vector temples = new Vector();
   private static TempleXML instance;

   public TempleXML() {
    }

    public int numberOfItemsInComboBox(NSComboBox comboBox) {
//        System.out.println("numberOfItemsInComboBox: "+temples.size()+comboBox);
        return temples.size();
    }
    public Object comboBoxObjectValueForItemAtIndex(NSComboBox comboBox, int index) {
//        System.out.println("comboBoxObjectValueForItemAtIndex: "+index);
        return temples.get(index).toString();
    }

    public String comboBoxCompletedString(NSComboBox aComboBox, String uncompletedString) {
//        System.out.println("comboBoxCompletedString: "+uncompletedString);
        String result = uncompletedString;
        for (int i=0; i< temples.size(); i++) {
            String test = temples.get(i).toString().toLowerCase();
            if (test.startsWith(uncompletedString.toLowerCase())) {
                return test;
            }
        }
        return result;
    }

    public int comboBoxIndexOfItem(NSComboBox aComboBox, String aString) {
//        System.out.println("comboBoxIndexOfItem: "+aString);
        for (int i=0; i< temples.size(); i++) {
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
      for (Iterator iterator = tl.getTempleList().iterator(); iterator.hasNext();) {
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
