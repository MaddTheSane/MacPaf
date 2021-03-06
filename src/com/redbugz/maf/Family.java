//
//  Family.java
//  MAF
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

package com.redbugz.maf;

import java.util.Collections;
import java.util.List;

public interface Family {
  public static final Family UNKNOWN_FAMILY = new UnknownFamily();

  public static final String REF = "REF";
  public static final String FAMILY = "FAM";
  public static final String HUSBAND = "HUSB";
  public static final String WIFE = "WIFE";
  public static final String RIN = "RIN";
  public static final String ID = "ID";
  public static final String MARRIAGE = "MARR";
  public static final String UID = "_UID";


  public Individual getFather();

  public void setFather(Individual father);

  public Individual getMother();

  public void setMother(Individual mother);

  public List getChildren();

  public void setChildren(List children);

  public void addChild(Individual newChild);

  public void addChildAtPosition(Individual newChild, int position);

  public void removeChildAtPosition(int position);

  public void reorderChildToPosition(Individual child, int newPosition);

  public Ordinance getPreferredSealingToSpouse();

  public void setPreferredSealingToSpouse(Ordinance sealing);

  public String getId();

  public void setId(String newId);

  public int getRin();

  public void setRin(int newRin);
  
  public List getEvents();
  
//  public List getOrdinances();

  public Event getPreferredMarriageEvent();
  public List getMarriageEvents();

  public String getNoteText();
  
  public String getUID();

  public static class UnknownFamily implements Family {

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getFather()
	 */
	public Individual getFather() {
		return Individual.UNKNOWN_MALE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setFather(com.redbugz.maf.Individual)
	 */
	public void setFather(Individual father) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getMother()
	 */
	public Individual getMother() {
		return Individual.UNKNOWN_FEMALE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setMother(com.redbugz.maf.Individual)
	 */
	public void setMother(Individual mother) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getChildren()
	 */
	public List getChildren() {
		return Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setChildren(java.util.List)
	 */
	public void setChildren(List children) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#addChild(com.redbugz.maf.Individual)
	 */
	public void addChild(Individual newChild) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#addChildAtPosition(com.redbugz.maf.Individual, int)
	 */
	public void addChildAtPosition(Individual newChild, int position) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#removeChildAtPosition(int)
	 */
	public void removeChildAtPosition(int position) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getSealingToSpouse()
	 */
	public Ordinance getPreferredSealingToSpouse() {
		return Ordinance.UNKNOWN_ORDINANCE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setSealingToSpouse(com.redbugz.maf.Ordinance)
	 */
	public void setPreferredSealingToSpouse(Ordinance sealing) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getId()
	 */
	public String getId() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setId(java.lang.String)
	 */
	public void setId(String newId) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getRin()
	 */
	public int getRin() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setRin(int)
	 */
	public void setRin(int newRin) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getMarriageEvent()
	 */
	public Event getPreferredMarriageEvent() {
		return Event.UNKNOWN_EVENT;
	}

	public List getEvents() {
		return Collections.EMPTY_LIST;
	}

	public String getNoteText() {
		return "";
	}

	public String getUID() {
		return "";
	}

	public void reorderChildToPosition(Individual child, int newPosition) {
		// TODO Auto-generated method stub
		
	}

	public List getMarriageEvents() {
		return Collections.EMPTY_LIST;
	}
  	
  }

}
