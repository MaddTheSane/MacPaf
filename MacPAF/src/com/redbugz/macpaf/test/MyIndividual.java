package com.redbugz.macpaf.test;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.log4j.*;
import org.jdom.*;

import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.*;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:29:46 PM
 * To change this template use Options | File Templates.
 */
public class MyIndividual implements Individual, Cloneable {
  private static final Logger log = Logger.getLogger(MyIndividual.class);
  String givens = "", surname = "", title = "";
  Gender gender = Gender.MALE;
  protected Family familyAsChild = new TestFamily();
  protected Family familyAsSpouse = new TestFamily();
  protected List familiesAsSpouse = new ArrayList();
  private String id = "";
  private URL imageURL = null;
  private List notes = Collections.EMPTY_LIST;
  private Ordinance baptism = new MyOrdinance();
  private Ordinance confirmation = new MyOrdinance();
  private Ordinance endowment = new MyOrdinance();
  private Ordinance sealingToParents = new MyOrdinance();

  //private List spouseList = new ArrayList();

  public void setFamilyAsSpouse(Family fam) {
	familyAsSpouse = fam;
	addFamilyAsSpouse(fam);
  }

  public List getSpouseList() {
	List spouseList = new ArrayList();
	for (Iterator iterator = familiesAsSpouse.iterator(); iterator.hasNext(); ) {
	  Family family = (Family) iterator.next();
	  if (gender.equals(Gender.MALE)) {
		spouseList.add(family.getMother());
	  }
	  else if (gender.equals(Gender.FEMALE)) {
		spouseList.add(family.getFather());
	  }
	}
	return spouseList;
  }

  public void setSpouseList(List spouses) {
//      spouseList = spouses;
  }

  public List getAssociations() {
	return new ArrayList();
  }

  public List getNotes() {
	if (notes == null) {
	  notes = Collections.EMPTY_LIST;
	}
	return notes;
  }

  public String getNoteText() {
	String noteString = "";
	Iterator iter = getNotes().iterator();
	while (iter.hasNext()) {
	  Note item = (Note) iter.next();
	  noteString += item.getText() + System.getProperty("line.separator");
	}
	return noteString;
  }

//   public void addSpouse(Individual newSpouse) {
//      spouseList.add(newSpouse);
//   }

  protected Event birthEvent = new MyEvent();
  private Event deathEvent = new MyEvent();
  private Event burialEvent = new MyEvent();
  private Event christeningEvent = new MyEvent();

  public MyIndividual(String givens, String surname, Gender gender) {
	this.givens = givens;
	this.surname = surname;
	this.gender = gender;
//      UnknownIndividual = new Indi();
  }

  public MyIndividual() {
	givens = "";
	surname = "Unknown";
	gender = Gender.UNKNOWN;
  }

  public MyIndividual(Element element) {
	String iName = element.getChildText("NAME");
	int surnameStart = iName.indexOf("/");
	int surnameEnd = iName.lastIndexOf("/");
	givens = iName.substring(0, surnameStart);
	surname = iName.substring(surnameStart + 1, surnameEnd);
	String eGender = element.getChildText("SEX");
	if (eGender == null) {
	  gender = Gender.UNKNOWN;
	}
	else if (eGender.equalsIgnoreCase("M")) {
	  gender = Gender.MALE;
	}
	else if (eGender.equalsIgnoreCase("F")) {
	  gender = Gender.FEMALE;
	}
	else {
	  gender = Gender.UNKNOWN;
	}
	String eIdent = element.getAttributeValue("ID");
	setId(eIdent);
	log.debug("ident:" + getId());
	if (element.getChild("BIRT") != null) {
	  setBirthEvent(new MyEvent(element.getChild("BIRT")));
	}
	if (element.getChild("BAPL") != null) {
	  baptism = new MyOrdinance(element.getChild("BAPL"));
	}
	if (element.getChild("CONL") != null) {
	  confirmation = new MyOrdinance(element.getChild("CONL"));
	}
	if (element.getChild("ENDL") != null) {
	  endowment = new MyOrdinance(element.getChild("ENDL"));
	}
	if (element.getChild("SLGC") != null) {
	  sealingToParents = new MyOrdinance(element.getChild("SLGC"));
	}
	final Element obje = element.getChild("OBJE");
	if (obje != null) {
	  log.debug("Person has multimedia object:" + obje);
	  log.debug("ref=" + obje.getAttributeValue("REF"));
	  log.debug("form=" + obje.getChildText("FORM"));
	  log.debug("title=" + obje.getChildText("TITL"));
	  log.debug("file=" + obje.getChildText("FILE"));
	  log.debug("content=" + obje.getText());
	}
  }

  public void setGivenNames(String givenNames) {
	this.givens = givenNames;
  }

  public void setGender(Gender gender) {
	this.gender = gender;
  }

  public void setSurname(String surname) {
	this.surname = surname;
  }

  public void setId(String id) {
	this.id = id;
  }

  public void setFamilyAsChild(Family family) {
	this.familyAsChild = family;
  }

  public void addFamilyAsSpouse(Family family) {
	this.familyAsSpouse = family;
	familiesAsSpouse.add(family);
//      addSpouse(family.getMother());
  }

  public String getGivenNames() {
	return givens;
  }

  public String getSurname() {
	return surname;
  }

  public String getNamePrefix() {
	return title;
  }

  public Gender getGender() {
	return gender;
  }

  public Event getBirthEvent() {
	return birthEvent;
  }

  public void setBirthEvent(Event event) {
	birthEvent = event;
  }

  public void setDeathEvent(Event event) {
	deathEvent = event;
  }

  public void setBurialEvent(Event event) {
	burialEvent = event;
  }

  public void setChristeningEvent(Event event) {
	christeningEvent = event;
  }

  public Event getChristeningEvent() {
	return christeningEvent;
  }

  public Event getDeathEvent() {
	return deathEvent;
  }

  public Event getBurialEvent() {
	return burialEvent;
  }

  public Ordinance getLDSBaptism() {
	return baptism;
  }

  public Ordinance getLDSConfirmation() {
	return confirmation;
  }

  public Ordinance getLDSEndowment() {
	return endowment;
  }

  public Ordinance getLDSSealingToParents() {
	return sealingToParents;
  }

  public boolean childrensOrdinancesAreCompleted() {
	if (familyAsSpouse.getChildren().size() == 0) {
	  // todo: determine if no children == true
	  return false;
	}
	boolean complete = true;
	for (int i = 0; i < familyAsSpouse.getChildren().size(); i++) {
	  complete = true;
	  Individual child = (Individual) familyAsSpouse.getChildren().get(i);
	  if (! (child.getLDSBaptism().isCompleted() && child.getLDSEndowment().isCompleted() &&
			 child.getLDSSealingToParents().isCompleted() && child.getPreferredFamilyAsSpouse().getPreferredSealingToSpouse().isCompleted())) {
		return false;
	  }
	}
	return complete;
  }

  public String getId() {
	return id;
  }

  public int getRin() {
	return 0;
  }

  public boolean isLocked() {
	return false;
  }

  public boolean isPrivate() {
	return false;
  }

  public String getFullName() {
	return getGivenNames() + " " + getSurname();
  }

  public URL getImagePath() {
	if (imageURL != null) {
	  return imageURL;
	}
	try {
	  return new File(NSBundle.mainBundle().pathForResource("tree", "gif")).toURL();
	}
	catch (MalformedURLException e) {
	  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	}
	return null;
  }

  public void setImagePath(URL path) {
	imageURL = path;
  }

  public Individual getPreferredSpouse() {
	if (gender.equals(Gender.MALE)) {
	  return familyAsSpouse.getMother();
	}
	else if (gender.equals(Gender.FEMALE)) {
	  return familyAsSpouse.getFather();
	}
	return new Individual.UnknownIndividual();
  }

  public Individual getFather() {
	return familyAsChild.getFather();
  }

  public Individual getMother() {
	return familyAsChild.getMother();
  }

  public Family getFamilyAsChild() {
	return familyAsChild;
  }

  public Family getPreferredFamilyAsSpouse() {
	return familyAsSpouse;
  }

  public void setNotes(List newNotes) {
	if (newNotes == null) {
	  newNotes = Collections.EMPTY_LIST;
	}
	this.notes = newNotes;
  }

  public void addNote(Note newNote) {
	notes.add(newNote);
  }

  /* (non-Javadoc)
   * @see Individual#addSpouse(Individual)
   */
  public void addSpouse(Individual newSpouse) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#getNameSuffix()
   */
  public String getNameSuffix() {
	// TODO Auto-generated method stub
	return null;
  }

  /* (non-Javadoc)
   * @see Individual#removeSpouse(Individual)
   */
  public void removeSpouse(Individual removedSpouse) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setFather(Individual)
   */
  public void setFather(Individual father) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setFullName(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public void setFullName(
	  String prefix,
	  String givenNames,
	  String surname,
	  String suffix) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setLDSBaptism(Ordinance)
   */
  public void setLDSBaptism(Ordinance ldsBaptism) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setLDSConfirmation(Ordinance)
   */
  public void setLDSConfirmation(Ordinance ldsConfirmation) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setLDSEndowment(Ordinance)
   */
  public void setLDSEndowment(Ordinance ldsEndowment) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setLDSSealingToParent(Ordinance)
   */
  public void setLDSSealingToParent(Ordinance sealingToParent) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setLocked(boolean)
   */
  public void setLocked(boolean lockedFlag) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setMother(Individual)
   */
  public void setMother(Individual mother) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setNamePrefix(java.lang.String)
   */
  public void setNamePrefix(String prefix) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setNameSuffix(java.lang.String)
   */
  public void setNameSuffix(String suffix) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setPrimarySpouse(Individual)
   */
  public void setPrimarySpouse(Individual primarySpouse) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setPrivate(boolean)
   */
  public void setPrivate(boolean privateFlag) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#setRin(int)
   */
  public void setRin(int rin) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see Individual#getAFN()
   */
  public String getAFN() {
	// TODO Auto-generated method stub
	return "";
  }

  /* (non-Javadoc)
   * @see Individual#setAFN(java.lang.String)
   */
  public void setAFN(String afn) {
	// TODO Auto-generated method stub

  }

  /**
   * getEvents
   *
   * @return List
   */
  public List getEvents() {
	return Collections.EMPTY_LIST;
  }

/* (non-Javadoc)
 * @see com.redbugz.macpaf.Individual#getPreferredImage()
 */
public Multimedia getPreferredImage() {
	// TODO Auto-generated method stub
	return null;
}

/* (non-Javadoc)
 * @see com.redbugz.macpaf.Individual#getAllMultimedia()
 */
public List getAllMultimedia() {
	// TODO Auto-generated method stub
	return null;
}

public String toString() {
	// TODO Auto-generated method stub
	return super.toString()+"{"+getFullName()+"}";
}

public List getFamiliesAsSpouse() {
	return Collections.EMPTY_LIST;
}

public List getAttributes() {
	return Collections.EMPTY_LIST;
}

public String getUID() {
	return "";
}

public void addFamilyAsChild(Family family) {
	// TODO Auto-generated method stub
	
}

public void removeFamilyAsChild(Family familyToRemove) {
	// TODO Auto-generated method stub
	
}

}
