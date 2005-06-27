//
//  Family.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

package com.redbugz.macpaf;

import java.util.Collections;
import java.util.List;

import com.redbugz.macpaf.jdom.FamilyJDOM;

public interface Family {
  public static final Family UNKNOWN_FAMILY = new UnknownFamily();

  public static final String REF = "REF";
  public static final String FAMILY = "FAM";
  public static final String HUSBAND = "HUSB";
  public static final String WIFE = "WIFE";
  public static final String RIN = "RIN";
  public static final String ID = "ID";
  public static final String MARRIAGE = "MARR";


  public Individual getFather();

  public void setFather(Individual father);

  public Individual getMother();

  public void setMother(Individual mother);

  public List getChildren();

  public void setChildren(List children);

  public void addChild(Individual newChild);

  public void addChildAtPosition(Individual newChild, int position);

  public void removeChildAtPosition(int position);

  public Ordinance getSealingToSpouse();

  public void setSealingToSpouse(Ordinance sealing);

  public String getId();

  public void setId(String newId);

  public int getRin();

  public void setRin(int newRin);

  public Event getMarriageEvent();
  
  public static class UnknownFamily implements Family {

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getFather()
	 */
	public Individual getFather() {
		return Individual.UNKNOWN_MALE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setFather(com.redbugz.macpaf.Individual)
	 */
	public void setFather(Individual father) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getMother()
	 */
	public Individual getMother() {
		return Individual.UNKNOWN_FEMALE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setMother(com.redbugz.macpaf.Individual)
	 */
	public void setMother(Individual mother) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getChildren()
	 */
	public List getChildren() {
		return Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setChildren(java.util.List)
	 */
	public void setChildren(List children) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#addChild(com.redbugz.macpaf.Individual)
	 */
	public void addChild(Individual newChild) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#addChildAtPosition(com.redbugz.macpaf.Individual, int)
	 */
	public void addChildAtPosition(Individual newChild, int position) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#removeChildAtPosition(int)
	 */
	public void removeChildAtPosition(int position) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getSealingToSpouse()
	 */
	public Ordinance getSealingToSpouse() {
		return Ordinance.UNKNOWN_ORDINANCE;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setSealingToSpouse(com.redbugz.macpaf.Ordinance)
	 */
	public void setSealingToSpouse(Ordinance sealing) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getId()
	 */
	public String getId() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setId(java.lang.String)
	 */
	public void setId(String newId) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getRin()
	 */
	public int getRin() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setRin(int)
	 */
	public void setRin(int newRin) {
		throw new UnsupportedOperationException("Cannot modify an UnknownFamily");
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getMarriageEvent()
	 */
	public Event getMarriageEvent() {
		return Event.UNKNOWN_EVENT;
	}
  	
  }
}
