package com.redbugz.macpaf;

//
//  Place.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

public interface Place {
  public static final int MAX_SEVERITY = 7;

  public String getLevel1();

  public String getLevel2();

  public String getLevel3();

  public String getLevel4();

  public String getFormatString();

  public String getAbbreviatedFormatString(int severity);

}
