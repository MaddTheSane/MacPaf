/*
 * Created on Nov 22, 2004
 *
 */
package com.redbugz.maf.jdom;

import java.util.*;

import com.redbugz.macpaf.MacPAFDocumentJDOM;
import com.redbugz.maf.*;

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
			actualFamily = document.getFamilyJDOM(id);
		}
		return actualFamily;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getFather()
	 */
	public Individual getFather() {
		return getFamily().getFather();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setFather(com.redbugz.maf.Individual)
	 */
	public void setFather(Individual father) {
		getFamily().setFather(father);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getMother()
	 */
	public Individual getMother() {
		return getFamily().getMother();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setMother(com.redbugz.maf.Individual)
	 */
	public void setMother(Individual mother) {
		getFamily().setMother(mother);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getChildren()
	 */
	public List getChildren() {
		return getFamily().getChildren();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setChildren(java.util.List)
	 */
	public void setChildren(List children) {
		getFamily().setChildren(children);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#addChild(com.redbugz.maf.Individual)
	 */
	public void addChild(Individual newChild) {
		getFamily().addChild(newChild);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#addChildAtPosition(com.redbugz.maf.Individual, int)
	 */
	public void addChildAtPosition(Individual newChild, int position) {
		getFamily().addChildAtPosition(newChild, position);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#removeChildAtPosition(int)
	 */
	public void removeChildAtPosition(int position) {
		getFamily().removeChildAtPosition(position);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getSealingToSpouse()
	 */
	public Ordinance getPreferredSealingToSpouse() {
		return getFamily().getPreferredSealingToSpouse();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setSealingToSpouse(com.redbugz.maf.Ordinance)
	 */
	public void setPreferredSealingToSpouse(Ordinance sealing) {
		getFamily().setPreferredSealingToSpouse(sealing);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getRin()
	 */
	public int getRin() {
		return getFamily().getRin();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#setRin(int)
	 */
	public void setRin(int newRin) {
		getFamily().setRin(newRin);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.Family#getMarriageEvent()
	 */
	public Event getPreferredMarriageEvent() {
		return getFamily().getPreferredMarriageEvent();
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

	public void reorderChildToPosition(Individual child, int newPosition) {
		getFamily().reorderChildToPosition(child, newPosition);
	}

	public List getMarriageEvents() {
		return getFamily().getMarriageEvents();
	}

}
