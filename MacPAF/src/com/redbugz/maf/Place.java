package com.redbugz.maf;

//
//  Place.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

public interface Place {
	  public static final Place UNKNOWN_PLACE = new UnknownPlace();
  public static final int MAX_SEVERITY = 7;

  public String getLevel1();

  public String getLevel2();

  public String getLevel3();

  public String getLevel4();

  public String getFormatString();

  public String getAbbreviatedFormatString(int severity);

  static class UnknownPlace implements Place {

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Place#getLevel1()
	 */
	public String getLevel1() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Place#getLevel2()
	 */
	public String getLevel2() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Place#getLevel3()
	 */
	public String getLevel3() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Place#getLevel4()
	 */
	public String getLevel4() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Place#getFormatString()
	 */
	public String getFormatString() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Place#getAbbreviatedFormatString(int)
	 */
	public String getAbbreviatedFormatString(int severity) {
		return "";
	}
  	
  }
}
