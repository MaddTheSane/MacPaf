package com.redbugz.macpaf;
//
//  Temple.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//


public class Temple implements Cloneable {

    private String name = "";
    private String code = "";
    private String oldCode = "";
    private int number = 0;
//    private Date openDate;
 //   private Date closeDate;
    private String openDate = "";
    private String closeDate = "";
    
    public Temple() {}
    
    public String getCode() {
        return code;
    }

    public void setCode(String newCode) {
        code = newCode;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setOldCode(String code) {
        oldCode = code;
    }

    public void setNumber(int num) {
        number = num;
    }

    public void setOpenDate(String date) {
        this.openDate = date;
    }

    public void setCloseDate(String date) {
        closeDate = date;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public String getName() {
        return name;
    }

    public String getOldCode() {
        return oldCode;
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        return code + " - " + name;
    }

}
