package com.redbugz.macpaf;

//
//  Ordinance.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

public interface Ordinance extends Event {
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

}
