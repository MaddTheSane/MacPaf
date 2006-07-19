/*
 * Created on Nov 22, 2004
 *
 */
package com.redbugz.macpaf.jdom;

import java.net.*;
import java.util.*;

import com.redbugz.macpaf.*;

/**
 * @author logan
 *
 */
public class IndividualLink implements Individual {
	private Individual actualIndividual = null;
	private MacPAFDocumentJDOM document = null;
	String id = "";
	
	IndividualLink(MacPAFDocumentJDOM doc) {
		document = doc;
	}
	
	IndividualLink(String id, MacPAFDocumentJDOM doc) {
		this(doc);
		setId(id);
	}

	/**
	 * @return
	 */
	private Individual getIndividual() {
		if (actualIndividual == null) {
			actualIndividual = document.getIndividual(id);
		}
		return actualIndividual;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getGivenNames()
	 */
	public String getGivenNames() {
		return getIndividual().getGivenNames();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setGivenNames(java.lang.String)
	 */
	public void setGivenNames(String givenNames) {
		getIndividual().setGivenNames(givenNames);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getSurname()
	 */
	public String getSurname() {
		return getIndividual().getSurname();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setSurname(java.lang.String)
	 */
	public void setSurname(String surname) {
		getIndividual().setSurname(surname);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getFullName()
	 */
	public String getFullName() {
		return getIndividual().getFullName();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setFullName(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setFullName(String prefix, String givenNames, String surname,
			String suffix) {
		getIndividual().setFullName(prefix, givenNames, surname, suffix);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getNamePrefix()
	 */
	public String getNamePrefix() {
		return getIndividual().getNamePrefix();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setNamePrefix(java.lang.String)
	 */
	public void setNamePrefix(String prefix) {
		getIndividual().setNamePrefix(prefix);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getNameSuffix()
	 */
	public String getNameSuffix() {
		return getIndividual().getNameSuffix();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setNameSuffix(java.lang.String)
	 */
	public void setNameSuffix(String suffix) {
		getIndividual().setNameSuffix(suffix);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getGender()
	 */
	public Gender getGender() {
		return getIndividual().getGender();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setGender(com.redbugz.macpaf.Gender)
	 */
	public void setGender(Gender gender) {
		getIndividual().setGender(gender);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getBirthEvent()
	 */
	public Event getBirthEvent() {
		return getIndividual().getBirthEvent();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setBirthEvent(com.redbugz.macpaf.Event)
	 */
	public void setBirthEvent(Event birthEvent) {
		getIndividual().setBirthEvent(birthEvent);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getChristeningEvent()
	 */
	public Event getChristeningEvent() {
		return getIndividual().getChristeningEvent();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setChristeningEvent(com.redbugz.macpaf.Event)
	 */
	public void setChristeningEvent(Event christeningEvent) {
		getIndividual().setChristeningEvent(christeningEvent);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getDeathEvent()
	 */
	public Event getDeathEvent() {
		return getIndividual().getDeathEvent();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setDeathEvent(com.redbugz.macpaf.Event)
	 */
	public void setDeathEvent(Event deathEvent) {
		getIndividual().setDeathEvent(deathEvent);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getBurialEvent()
	 */
	public Event getBurialEvent() {
		return getIndividual().getBurialEvent();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setBurialEvent(com.redbugz.macpaf.Event)
	 */
	public void setBurialEvent(Event burialEvent) {
		getIndividual().setBurialEvent(burialEvent);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getLDSBaptism()
	 */
	public Ordinance getLDSBaptism() {
		return getIndividual().getLDSBaptism();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setLDSBaptism(com.redbugz.macpaf.Ordinance)
	 */
	public void setLDSBaptism(Ordinance ldsBaptism) {
		getIndividual().setLDSBaptism(ldsBaptism);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getLDSConfirmation()
	 */
	public Ordinance getLDSConfirmation() {
		return getIndividual().getLDSConfirmation();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setLDSConfirmation(com.redbugz.macpaf.Ordinance)
	 */
	public void setLDSConfirmation(Ordinance ldsConfirmation) {
		getIndividual().setLDSConfirmation(ldsConfirmation);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getLDSEndowment()
	 */
	public Ordinance getLDSEndowment() {
		return getIndividual().getLDSEndowment();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setLDSEndowment(com.redbugz.macpaf.Ordinance)
	 */
	public void setLDSEndowment(Ordinance ldsEndowment) {
		getIndividual().setLDSEndowment(ldsEndowment);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getLDSSealingToParents()
	 */
	public Ordinance getLDSSealingToParents() {
		return getIndividual().getLDSSealingToParents();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setLDSSealingToParent(com.redbugz.macpaf.Ordinance)
	 */
	public void setLDSSealingToParent(Ordinance sealingToParent) {
		getIndividual().setLDSSealingToParent(sealingToParent);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#childrensOrdinancesAreCompleted()
	 */
	public boolean childrensOrdinancesAreCompleted() {
		return getIndividual().childrensOrdinancesAreCompleted();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getRin()
	 */
	public int getRin() {
		return getIndividual().getRin();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setRin(int)
	 */
	public void setRin(int rin) {
		getIndividual().setRin(rin);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getAFN()
	 */
	public String getAFN() {
		return getIndividual().getAFN();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setAFN(java.lang.String)
	 */
	public void setAFN(String afn) {
		getIndividual().setAFN(afn);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#isLocked()
	 */
	public boolean isLocked() {
		return getIndividual().isLocked();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setLocked(boolean)
	 */
	public void setLocked(boolean lockedFlag) {
		getIndividual().setLocked(lockedFlag);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#isPrivate()
	 */
	public boolean isPrivate() {
		return getIndividual().isPrivate();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setPrivate(boolean)
	 */
	public void setPrivate(boolean privateFlag) {
		getIndividual().setPrivate(privateFlag);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getImagePath()
	 */
	public URL getImagePath() {
		return getIndividual().getImagePath();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setImagePath(java.net.URL)
	 */
	public void setImagePath(URL path) {
		getIndividual().setImagePath(path);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getPrimarySpouse()
	 */
	public Individual getPreferredSpouse() {
		return getIndividual().getPreferredSpouse();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setPrimarySpouse(com.redbugz.macpaf.Individual)
	 */
	public void setPrimarySpouse(Individual primarySpouse) {
		getIndividual().setPrimarySpouse(primarySpouse);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getSpouseList()
	 */
	public List getSpouseList() {
		return getIndividual().getSpouseList();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setSpouseList(java.util.List)
	 */
	public void setSpouseList(List spouses) {
		getIndividual().setSpouseList(spouses);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#addSpouse(com.redbugz.macpaf.Individual)
	 */
	public void addSpouse(Individual newSpouse) {
		getIndividual().addSpouse(newSpouse);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#removeSpouse(com.redbugz.macpaf.Individual)
	 */
	public void removeSpouse(Individual removedSpouse) {
		getIndividual().removeSpouse(removedSpouse);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getFather()
	 */
	public Individual getFather() {
		return getIndividual().getFather();
	}

//	/* (non-Javadoc)
//	 * @see com.redbugz.macpaf.Individual#setFather(com.redbugz.macpaf.Individual)
//	 */
//	public void setFather(Individual father) {
//		getIndividual().setFather(father);
//	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getMother()
	 */
	public Individual getMother() {
		return getIndividual().getMother();
	}

//	/* (non-Javadoc)
//	 * @see com.redbugz.macpaf.Individual#setMother(com.redbugz.macpaf.Individual)
//	 */
//	public void setMother(Individual mother) {
//		getIndividual().setMother(mother);
//	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getFamilyAsChild()
	 */
	public Family getFamilyAsChild() {
		return getIndividual().getFamilyAsChild();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setFamilyAsChild(com.redbugz.macpaf.Family)
	 */
	public void setFamilyAsChild(Family fam) {
		getIndividual().setFamilyAsChild(fam);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getFamilyAsSpouse()
	 */
	public Family getPreferredFamilyAsSpouse() {
		return getIndividual().getPreferredFamilyAsSpouse();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#setFamilyAsSpouse(com.redbugz.macpaf.Family)
	 */
	public void setFamilyAsSpouse(Family fam) {
		getIndividual().setFamilyAsSpouse(fam);
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getAssociations()
	 */
	public List getAssociations() {
		return getIndividual().getAssociations();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getNotes()
	 */
	public List getNotes() {
		return getIndividual().getNotes();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getNoteText()
	 */
	public String getNoteText() {
		return getIndividual().getNoteText();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getEvents()
	 */
	public List getEvents() {
		return getIndividual().getEvents();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getPreferredImage()
	 */
	public Multimedia getPreferredImage() {
		return getIndividual().getPreferredImage();
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Individual#getAllMultimedia()
	 */
	public List getAllMultimedia() {
		return getIndividual().getAllMultimedia();
	}

	public List getFamiliesAsSpouse() {
		return getIndividual().getFamiliesAsSpouse();
	}

	public List getAttributes() {
		return getIndividual().getAttributes();
	}

	public String getUID() {
		return getIndividual().getUID();
	}

}
