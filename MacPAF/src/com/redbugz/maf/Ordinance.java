package com.redbugz.maf;

import java.util.Date;

//
//  Ordinance.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

public interface Ordinance extends Event {
	public static final Ordinance UNKNOWN_ORDINANCE = new UnknownOrdinance();
  // Ordinance status codes
  public static final String BIC = "BIC"; // Born in the covenant receiving blessing of child to parent sealing.
  public static final String CHILD = "Child"; // Died before eight years old, ordinance not required
  public static final String CLEARED = "Cleared"; // Ordinance has been cleared for temple ordinance
  public static final String COMPLETED = "Completed"; // Completed but the date is not known
  public static final String DNS = "DNS"; // This record is not being submitted for this temple ordinance.
  public static final String DNS_CAN = "DNS/CAN"; // This record is not being submitted for this temple ordinance.
  public static final String INFANT = "Infant"; // Died before less than one year old, ordinance not required
  public static final String PRE_1970 = "Pre-1970"; // Ordinance is likely completed, another ordinance for this person was converted from temple records of work completed before 1970, therefore this ordinance is assumed to be complete until all records are converted
  public static final String QUALIFIED = "Qualified"; // Ordinance request qualified by authorized criteria
  public static final String STILLBORN = "Stillborn"; // Stillborn, ordinance not required
  public static final String SUBMITTED = "Submitted"; // Ordinance was previously submitted
  public static final String UNCLEARED = "Uncleared"; // Data for clearing ordinance request was insufficient

  public Temple getTemple();

  public void setTemple(Temple temple);

  public String getStatus();

  public void setStatus(String status);

  public boolean isCompleted();

  static class UnknownOrdinance implements Ordinance {

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Ordinance#getTemple()
	 */
	public Temple getTemple() {
		return Temple.UNKNOWN_TEMPLE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Ordinance#setTemple(com.redbugz.maf.Temple)
	 */
	public void setTemple(Temple temple) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Ordinance#getStatus()
	 */
	public String getStatus() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Ordinance#setStatus(java.lang.String)
	 */
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Ordinance#isCompleted()
	 */
	public boolean isCompleted() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Event#getDate()
	 */
	public Date getDate() {
		// TODO Auto-generated method stub
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
