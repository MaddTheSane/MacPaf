//
//  Event.java
//  MAF
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

package com.redbugz.maf;

import java.util.Date;

public interface Event {
  public static final Event UNKNOWN_EVENT = new UnknownEvent();
  
  public Date getDate();

  public void setDate(Date date);

  public String getDateString();

  public void setDateString(String dateString);

  public Place getPlace();

  public void setPlace(Place place);
  
  public String getEventTypeString();

  public static class UnknownEvent implements Event {

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Event#getDate()
	 */
	public Date getDate() {
		return new Date();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Event#setDate(java.util.Date)
	 */
	public void setDate(Date date) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Event#getDateString()
	 */
	public String getDateString() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Event#setDateString(java.lang.String)
	 */
	public void setDateString(String dateString) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Event#getPlace()
	 */
	public Place getPlace() {
		return Place.UNKNOWN_PLACE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Event#setPlace(com.redbugz.maf.Place)
	 */
	public void setPlace(Place place) {
		// TODO Auto-generated method stub
		
	}

	public String getEventTypeString() {
		return "Unknown";
	}
  	
  }
}
