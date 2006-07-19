/*
 * Created on Nov 22, 2004
 *
 */
package com.redbugz.macpaf.jdom;

import java.util.*;

import com.redbugz.macpaf.*;

/**
 * @author logan
 *
 */
public class FamilyLink implements Family {
	private Family actualFamily = null;
	private MacPAFDocumentJDOM document = null;
	String id = "";

	FamilyLink(MacPAFDocumentJDOM doc) {
		document = doc;
	}
	
	FamilyLink(String id, MacPAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Family getFamily() {
		if (actualFamily == null) {
			actualFamily = document.getFamily(id);
		}
		return actualFamily;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getFather()
	 */
	public Individual getFather() {
		return getFamily().getFather();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setFather(com.redbugz.macpaf.Individual)
	 */
	public void setFather(Individual father) {
		getFamily().setFather(father);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getMother()
	 */
	public Individual getMother() {
		return getFamily().getMother();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setMother(com.redbugz.macpaf.Individual)
	 */
	public void setMother(Individual mother) {
		getFamily().setMother(mother);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getChildren()
	 */
	public List getChildren() {
		return getFamily().getChildren();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setChildren(java.util.List)
	 */
	public void setChildren(List children) {
		getFamily().setChildren(children);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#addChild(com.redbugz.macpaf.Individual)
	 */
	public void addChild(Individual newChild) {
		getFamily().addChild(newChild);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#addChildAtPosition(com.redbugz.macpaf.Individual, int)
	 */
	public void addChildAtPosition(Individual newChild, int position) {
		getFamily().addChildAtPosition(newChild, position);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#removeChildAtPosition(int)
	 */
	public void removeChildAtPosition(int position) {
		getFamily().removeChildAtPosition(position);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getSealingToSpouse()
	 */
	public Ordinance getSealingToSpouse() {
		return getFamily().getSealingToSpouse();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setSealingToSpouse(com.redbugz.macpaf.Ordinance)
	 */
	public void setSealingToSpouse(Ordinance sealing) {
		getFamily().setSealingToSpouse(sealing);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getRin()
	 */
	public int getRin() {
		return getFamily().getRin();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#setRin(int)
	 */
	public void setRin(int newRin) {
		getFamily().setRin(newRin);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Family#getMarriageEvent()
	 */
	public Event getMarriageEvent() {
		return getFamily().getMarriageEvent();
	}

	public List getEvents() {
		return getFamily().getEvents();
	}

	public String getNoteText() {
		return getFamily().getNoteText();
	}

	public String getUID() {
		return getFamily().getUID();
	}

}
