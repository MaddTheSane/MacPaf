package com.redbugz.macpaf.jdom;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.filter.ContentFilter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import com.apple.cocoa.foundation.NSBundle;
import com.redbugz.macpaf.*;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:29:46 PM
 * To change this template use Options | File Templates.
 */
public class IndividualJDOM implements Individual, Cloneable {
  private static final Category log = Category.getInstance(IndividualJDOM.class.getName());
  private static final String REF = "REF";
  public static final String INDIVIDUAL = "INDI";
  public static final String RESTRICTION = "RESN";
  public static final String GENDER = "SEX";
  public static final String SUBMISSION = "SUBM";
  public static final String RIN = "RIN";
  public static final String AFN = "AFN";
  public static final String RFN = "RFN";

  public static final String NAME = "NAME";
  public static final String NAME_PREFIX = "NPFX";
  public static final String NAME_GIVEN = "GIVN";
  public static final String NAME_NICKNAME = "NICK";
  public static final String NAME_SURNAME_PREFIX = "SPFX";
  public static final String NAME_SURNAME = "SURN";
  public static final String NAME_SUFFIX = "NSFX";

  private static final String ID = "ID";
  private static final String FAMILY_CHILD_LINK = "FAMC";
  private static final String FAMILY_SPOUSE_LINK = "FAMS";
  public static final String LOCKED = "locked";
  public static final String PRIVACY = "privacy";

  Element element = new Element(INDIVIDUAL);
  List events = null;

  Document document = null;
  private FamilyJDOM familyAsChild;
  private FamilyJDOM familyAsSpouse;

  public IndividualJDOM(Document parentDocument) {
	document = parentDocument;
	element = new Element(INDIVIDUAL);
  }

  public IndividualJDOM(Element element, Document parentDocument) {
	document = parentDocument;
	if (element == null) {
	  element = new Element(INDIVIDUAL);
	}
	this.element = element;
	setFullName(getNameString());
//		String iName = element.getChildText(NAME);
//		int surnameStart = iName.indexOf("/");
//		int surnameEnd = iName.lastIndexOf("/");
//		givens = iName.substring(0, surnameStart);
//		surname = iName.substring(surnameStart + 1, surnameEnd);
//		suffix = iName.substring(surnameEnd);
//		this.element = element;
//		log.debug("ident:" + getId());
//		if (element.getChild("BIRT") != null) {
//			setBirthEvent(new EventJDOM(element.getChild("BIRT")));
//		}
//		if (element.getChild("BAPL") != null) {
//			baptism = new OrdinanceJDOM(element.getChild("BAPL"));
//		}
//		if (element.getChild("CONL") != null) {
//			confirmation = new OrdinanceJDOM(element.getChild("CONL"));
//		}
//		if (element.getChild("ENDL") != null) {
//			endowment = new OrdinanceJDOM(element.getChild("ENDL"));
//		}
//		if (element.getChild("SLGC") != null) {
//			sealingToParents = new OrdinanceJDOM(element.getChild("SLGC"));
//		}
	final Element obje = element.getChild("OBJE");
	if (obje != null) {
	  log.debug("Person has multimedia object:" + obje);
	  log.debug("ref=" + obje.getAttributeValue(REF));
	  log.debug("form=" + obje.getChildText("FORM"));
	  log.debug("title=" + obje.getChildText("TITL"));
	  log.debug("file=" + obje.getChildText("FILE"));
	  log.debug("content=" + obje.getText());
	}
  }

  public IndividualJDOM(Individual newIndividual) {
	if (newIndividual instanceof IndividualJDOM) {
	  element = ( (IndividualJDOM) newIndividual).getElement();
	}
	else {
	  setAFN(newIndividual.getAFN());
	  setBirthEvent(newIndividual.getBirthEvent());
	  setBurialEvent(newIndividual.getBurialEvent());
	  setChristeningEvent(newIndividual.getChristeningEvent());
	  setDeathEvent(newIndividual.getDeathEvent());
	  setFamilyAsChild(newIndividual.getFamilyAsChild());
	  setFamilyAsSpouse(newIndividual.getFamilyAsSpouse());
	  setFather(newIndividual.getFather());
	  setFullName(newIndividual.getNamePrefix(), newIndividual.getGivenNames(), newIndividual.getSurname(),
				  newIndividual.getNameSuffix());
	  setGender(newIndividual.getGender());
	  setId(newIndividual.getId());
	  setImagePath(newIndividual.getImagePath());
	  setLDSBaptism(newIndividual.getLDSBaptism());
	  setLDSConfirmation(newIndividual.getLDSConfirmation());
	  setLDSEndowment(newIndividual.getLDSEndowment());
	  setLDSSealingToParent(newIndividual.getLDSSealingToParents());
	  setLocked(newIndividual.isLocked());
	  setMother(newIndividual.getMother());
	  setNotes(newIndividual.getNotes());
	  setPrimarySpouse(newIndividual.getPrimarySpouse());
	  setPrivate(newIndividual.isPrivate());
	  setRin(newIndividual.getRin());
	  setSpouseList(newIndividual.getSpouseList());
	}
  }

  public void setGivenNames(String givenNames) {
	if (givenNames == null) {
	  givenNames = "";
	}
	Element name = makeChildElement(NAME_GIVEN, makeChildElement(NAME));
	name.removeContent(new ContentFilter(ContentFilter.TEXT));
	name.addContent(0, new Text(givenNames.trim()));
	saveName();
  }

  public void setGender(Gender gender) {
	if (gender == null) {
	  gender = Gender.UNKNOWN;
	}
	element.removeChildren(GENDER);
	if (Gender.UNKNOWN != gender) {
	  element.addContent(new Element(GENDER).setText(gender.getAbbreviation()));
	}
  }

  public void setSurname(String surname) {
	if (surname == null) {
	  surname = "";
	}
	Element name = makeChildElement(NAME_SURNAME, makeChildElement(NAME));
	name.removeContent(new ContentFilter(ContentFilter.TEXT));
	name.addContent(0, new Text(surname));
	saveName();
  }

  private String getNameString() {
	String nameString = element.getChildTextTrim(NAME);
	if (nameString == null) {
	  nameString = "";
	}
	return nameString;
  }

  private void setName(String name) {
	Element nameElement = makeChildElement(NAME);
	nameElement.removeContent(new ContentFilter(ContentFilter.TEXT));
	nameElement.addContent(0, new Text(name));
  }

  public String getFullName() {
//		String name = givens;
//		if (name.length() > 0) {
//			name += " ";
//		}
//		name += surname;
	String name = element.getChildTextTrim(NAME); //name;
	if (name == null) {
	  name = "";
	}
	// remove the slashes around the surname
	// todo: use string replacement
//	log.debug("name before slashes removed:|" + name + "|");
	StringBuffer nameBuf = new StringBuffer(name);
	while (nameBuf.toString().indexOf('/') >= 0) {
	  nameBuf.deleteCharAt(nameBuf.toString().indexOf('/'));
	}
	name = nameBuf.toString();
//	log.debug("name  after slashes removed:|" + name + "|");
	return name.trim();
  }

  /* (non-Javadoc)
   * @see Individual#setFullName(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public void setFullName(
	  String prefix,
	  String givenNames,
	  String surname,
	  String suffix) {
//			this.prefix = prefix;
//			this.givens = givenNames;
//			this.surname = surname;
//			this.suffix = suffix;
//			saveName();
	setNamePrefix(prefix);
	setGivenNames(givenNames);
	setSurname(surname);
	setNameSuffix(suffix);
	saveName();
  }

  public void setFullName(String name) {
	log.debug("--------------------------IndividualJDOM.setFullName(name):" + name);
	if (name == null) {
	  name = "";
	}
	if (name.equals(getFullName())) {
	  log.debug("IndividualJDOM.setFullName(name) name is already same as full name, returning...");
	  return;
	}
	int firstSlashIndex = name.indexOf("/");
	int lastSlashIndex = name.lastIndexOf("/"); //, firstSlashIndex + 1);
	log.error("name:" + name);
	log.error("surname:" + firstSlashIndex + "-" + lastSlashIndex);
	if (firstSlashIndex >= 0) {
	  setGivenNames(name.substring(0, firstSlashIndex));
	  if (lastSlashIndex >= 0 && lastSlashIndex > firstSlashIndex) {
		setSurname(name.substring(firstSlashIndex + 1, lastSlashIndex));
		setNameSuffix(name.substring(lastSlashIndex + 1).trim());
	  }
	  else {
		setSurname(name.substring(firstSlashIndex + 1));
	  }
	}
	else {
//            element.removeChildren(NAME);
	  setName(name);
	}
  }

  private void saveName() {
	String name = getNamePrefix();
	if (name.length() > 0) {
	  name += " ";
	}
	name += getGivenNames();
	if (name.length() > 0) {
	  name += " ";
	}
	if (getSurname().length() > 0) {
	  name += "/" + getSurname() + "/";
	}
	if (name.length() > 0) {
	  name += " ";
	}
	name += getNameSuffix();
	log.debug("saveName name=" + name.trim());
	setName(name.trim());
  }

  public String getGivenNames() {
	String givens = ""; //getNameString();
	Element name = element.getChild(NAME);
	if (name != null) {
	  Element givenNames = name.getChild(NAME_GIVEN);
	  if (givenNames != null) {
		givens = givenNames.getTextTrim(); //givens;
	  }
	  if (givens.indexOf("/") >= 0) {
		givens = givens.substring(0, givens.indexOf("/"));
	  }
	}
	return givens;
  }

  public String getSurname() {
	String surname = "";
	Element name = element.getChild(NAME);
	if (name != null) {
	  surname = name.getChildTextTrim(NAME_SURNAME);
	  if (surname == null) {
		surname = "";
	  }
	}
	return surname;
  }

  public String getNamePrefix() {
	String prefix = "";
	Element name = element.getChild(NAME);
	if (name != null) {
	  prefix = name.getChildTextTrim(NAME_PREFIX);
	  if (prefix == null) {
		prefix = "";
	  }
	}
	return prefix;
  }

  /* (non-Javadoc)
   * @see Individual#getNameSuffix()
   */
  public String getNameSuffix() {
	String suffix = "";
	Element name = element.getChild(NAME);
	if (name != null) {
	  suffix = name.getChildTextTrim(NAME_SUFFIX);
	  if (suffix == null) {
		suffix = "";
	  }
	}
	return suffix;
  }

  /* (non-Javadoc)
   * @see Individual#setNamePrefix(java.lang.String)
   */
  public void setNamePrefix(String prefix) {
	if (prefix == null) {
	  prefix = "";
	}
	Element name = makeChildElement(NAME_PREFIX, makeChildElement(NAME));
	name.removeContent(new ContentFilter(ContentFilter.TEXT));
	name.addContent(0, new Text(prefix));
	saveName();
  }

  /* (non-Javadoc)
   * @see Individual#setNameSuffix(java.lang.String)
   */
  public void setNameSuffix(String suffix) {
	if (suffix == null) {
	  suffix = "";
	}
	Element name = makeChildElement(NAME_SUFFIX, makeChildElement(NAME));
	name.removeContent(new ContentFilter(ContentFilter.TEXT));
	name.addContent(0, new Text(suffix));
	saveName();
  }

  /* (non-Javadoc)
   * @see Individual#addSpouse(Individual)
   */
  public void addSpouse(Individual newSpouse) {
	throw new UnsupportedOperationException("addSpouse not implemented in IndividualJDOM");
  }

  public void setId(String id) {
	if (id == null) {
	  id = "0";
	}
	element.setAttribute(ID, id);
  }

  public void setFamilyAsChild(Family family) {
//		this.familyAsChild = family;
	element.removeChildren(FAMILY_CHILD_LINK);
	element.addContent(new Element(FAMILY_CHILD_LINK).setAttribute(REF, family.getId()));
  }

  public void addFamilyAsSpouse(Family family) {
//		this.familyAsSpouse = family;
//		familiesAsSpouse.add(family);
	element.addContent(new Element(FAMILY_SPOUSE_LINK).setAttribute(REF, family.getId()));
	//      addSpouse(family.getMother());
  }

  public Gender getGender() {
	log.debug("IndividualJDOM.getGender() returns:" + element.getChildTextTrim(GENDER));
	return Gender.genderWithCode(element.getChildTextTrim(GENDER));
  }

  public Event getBirthEvent() {
	return new EventJDOM(makeChildElement(EventJDOM.BIRTH));
  }

  public void setBirthEvent(Event event) {
	element.addContent(new EventJDOM(event).getElement());
  }

  public void setDeathEvent(Event event) {
	element.addContent(new EventJDOM(event).getElement());
  }

  public void setBurialEvent(Event event) {
	element.addContent(new EventJDOM(event).getElement());
  }

  public void setChristeningEvent(Event event) {
	element.addContent(new EventJDOM(event).getElement());
  }

  public Event getChristeningEvent() {
	return new EventJDOM(makeChildElement(EventJDOM.CHRISTENING));
  }

  public Event getDeathEvent() {
	return new EventJDOM(makeChildElement(EventJDOM.DEATH));
  }

  public Event getBurialEvent() {
	return new EventJDOM(makeChildElement(EventJDOM.BURIAL));
  }

  public Ordinance getLDSBaptism() {
	return new OrdinanceJDOM(makeChildElement(OrdinanceJDOM.LDS_BAPTISM));
  }

  public Ordinance getLDSConfirmation() {
	return new OrdinanceJDOM(makeChildElement(OrdinanceJDOM.LDS_CONFIRMATION));
  }

  public Ordinance getLDSEndowment() {
	return new OrdinanceJDOM(makeChildElement(OrdinanceJDOM.LDS_ENDOWMENT));
  }

  public Ordinance getLDSSealingToParents() {
	return new OrdinanceJDOM(makeChildElement(OrdinanceJDOM.LDS_SEALING_PARENTS));
  }

  public boolean childrensOrdinancesAreCompleted() {
	if (getFamilyAsSpouse().getChildren().size() == 0) {
	  // todo: determine if no children == true
	  return false;
	}
	boolean complete = true;
	for (int i = 0; i < getFamilyAsSpouse().getChildren().size(); i++) {
	  complete = true;
	  Individual child = (Individual) getFamilyAsSpouse().getChildren().get(i);
	  if (! (child.getLDSBaptism().isCompleted()
			 && child.getLDSEndowment().isCompleted()
			 && child.getLDSSealingToParents().isCompleted()
			 && child
			 .getFamilyAsSpouse()
			 .getSealingToSpouse()
			 .isCompleted())) {
		return false;
	  }
	}
	return complete;
  }

  public String getId() {
	return element.getAttributeValue(ID);
  }

  public int getRin() {
	try {
	  return Integer.parseInt(element.getChildTextTrim(RIN));
	}
	catch (NumberFormatException e) {
	  return 0;
	}
  }

  public boolean isLocked() {
	return LOCKED.equalsIgnoreCase(element.getChildTextTrim(RESTRICTION));
  }

  public boolean isPrivate() {
	return PRIVACY.equalsIgnoreCase(element.getChildTextTrim(RESTRICTION));
  }

  public URL getImagePath() {
	try {
	  //		if (imageURL != null) {
//			return imageURL;
//		}
//		try {
//			return new File(
//				NSBundle.mainBundle().pathForResource("tree", "gif"))
//				.toURL();
//		} catch (MalformedURLException e) {
//			log.error("Exception: ", e);
//			//To change body of catch statement use Options | File Templates.
//		}
// todo implement
//        return null;
	  return new File(
		  NSBundle.mainBundle().pathForResource("tree", "gif"))
		  .toURL();
	}
	catch (MalformedURLException e) {
	  // TODO Auto-generated catch block
	  log.error("Exception: ", e);
	}
	return null;
  }

  public void setImagePath(URL path) {
//		imageURL = path;
// todo implement
  }

  public Individual getPrimarySpouse() {
	if (getGender().equals(Gender.MALE)) {
	  return getFamilyAsSpouse().getMother();
	}
	else if (getGender().equals(Gender.FEMALE)) {
	  return getFamilyAsSpouse().getFather();
	}
	// todo determine primary spouse
	return new Individual.UnknownIndividual();
  }

  public Individual getFather() {
	return getFamilyAsChild().getFather();
  }

  public Individual getMother() {
	return getFamilyAsChild().getMother();
  }

  public Family getFamilyAsChild() {
	if (familyAsChild == null) {
	  familyAsChild = new FamilyJDOM(document);
	  try {
		Element famLink = element.getChild(FAMILY_CHILD_LINK);
		if (famLink != null) {
		  XPath xpath = XPath.newInstance("//FAM[@ID='" + famLink.getAttributeValue(REF) + "']");
		  log.debug("child xpath:" + xpath.getXPath());
		  Element famNode = (Element) xpath.selectSingleNode(element);
		  log.debug("famNode: " + famNode);
		  try {
			new XMLOutputter(Format.getPrettyFormat()).output(famNode, System.out);
		  }
		  catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
		  familyAsChild = new FamilyJDOM(famNode, document);
		}
	  }
	  catch (JDOMException e) {
		log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	  }
	}
	return familyAsChild;
  }

  public Family getFamilyAsSpouse() {
	if (familyAsSpouse == null) {
	  familyAsSpouse = new FamilyJDOM(document);
	  try {
		Element famLink = element.getChild(FAMILY_SPOUSE_LINK);
		if (famLink != null) {
		  XPath xpath = XPath.newInstance("//FAM[@ID='" + famLink.getAttributeValue(REF) + "']");
		  log.debug("spouse xpath:" + xpath.getXPath());
		  Element famNode = (Element) xpath.selectSingleNode(element);
		  log.debug("famNode: " + famNode);
		  familyAsSpouse = new FamilyJDOM(famNode, document);
		}
	  }
	  catch (JDOMException e) {
		log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	  }
	}
	return familyAsSpouse;
  }

  public void setNotes(List newNotes) {
//		if (note == null) {
//			this.note = new NoteJDOM();
//		} else {
//			this.note = note;
//		}
// todo implement
  }

  public void setFamilyAsSpouse(Family fam) {
//		familyAsSpouse = fam;
//		addFamilyAsSpouse(fam);
// todo implement
  }

  public List getSpouseList() {
	List spouseList = new ArrayList();
	if (getGender().equals(Gender.FEMALE)) {
	  spouseList.add(getFamilyAsSpouse().getFather());
	}
	else {
	  spouseList.add(getFamilyAsSpouse().getMother());
	}
	/*
			 try {
		Element famLink = element.getChild(FAMILY_SPOUSE_LINK);
		if (famLink != null) {
		XPath xpath = XPath.newInstance("//FAM[@ID='"+famLink.getAttributeValue(REF)+"']");
		log.debug("spouse xpath:"+xpath.getXPath());
		Element famNode = (Element) xpath.selectSingleNode(element);
		log.debug("famNode: "+famNode);
		fam = new FamilyJDOM(famNode, document);
		}
			 } catch (JDOMException e) {
		log.error("Exception: ", e);  //To change body of catch statement use Options | File Templates.
			 }
	 */
//		for (Iterator iterator = familiesAsSpouse.iterator();
//			iterator.hasNext();
//			) {
//			Family family = (Family) iterator.next();
//			if (gender.equals(Gender.MALE)) {
//				spouseList.add(family.getMother());
//			} else if (gender.equals(Gender.FEMALE)) {
//				spouseList.add(family.getFather());
//			}
//		}
// todo implement
	return spouseList;
  }

  public void setSpouseList(List spouses) {
//		for (Iterator iter = spouses.iterator(); iter.hasNext();) {
//			Individual element = (Individual) iter.next();
//
//		}
  }

  public List getAssociations() {
// todo implement
	return new ArrayList();
  }

  public List getNotes() {
// todo implement
//		if (note == null) {
	List notes = new ArrayList();
	List children = element.getChildren(NoteJDOM.NOTE);
	Iterator iter = children.iterator();
	while (iter.hasNext()) {
	  Element item = (Element) iter.next();
	  notes.add(new NoteJDOM(item));
	}
	return notes;
//		}
//		return note;
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

  //	 public void addSpouse(Individual newSpouse) {
  //		spouseList.add(newSpouse);
  //	 }

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
	makeChildElement(RESTRICTION).setText(LOCKED);
  }

  /* (non-Javadoc)
   * @see Individual#setMother(Individual)
   */
  public void setMother(Individual mother) {
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
	makeChildElement(RESTRICTION).setText(PRIVACY);
  }

  /* (non-Javadoc)
   * @see Individual#setRin(int)
   */
  public void setRin(int rin) {
	makeChildElement(RIN).setText("" + rin);
  }

  /* (non-Javadoc)
   * @see Individual#getAFN()
   */
  public String getAFN() {
	String afn = element.getChildTextTrim(AFN);
	if (afn == null) {
	  afn = "";
	}
	return afn;
  }

  /* (non-Javadoc)
   * @see Individual#setAFN(java.lang.String)
   */
  public void setAFN(String afn) {
	if (afn == null) {
	  afn = "";
	}
	makeChildElement(AFN).setText(afn);
  }

  private Element makeChildElement(String elementName) {
	return makeChildElement(elementName, element);
  }

  private Element makeChildElement(String elementName, Element parentElement) {
	if (parentElement == null) {
	  throw new IllegalArgumentException("IndividualJDOM.makeChildElement(): parentElement cannot be null");
	}
	Element child = parentElement.getChild(elementName);
	if (child == null) {
	  child = new Element(elementName);
	  parentElement.addContent(child);
	}
	return child;
  }

  public Element getElement() {
	return element;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
	// TODO Auto-generated method stub
	return "IndividualJDOM elemnt:" + new XMLOutputter(Format.getPrettyFormat()).outputString(element); //super.toString();
  }

  /**
   * getEvents
   *
   * @return List
   */
  public List getEvents() {
//    log.debug("IndividualJDOM.getEvents()");
	if (events == null) {
	  log.debug("IndividualJDOM.getEvents() creating event list");
	  events = new ArrayList();
	  try {
		XPath xpath = XPath.newInstance(eventNodeNames);
		log.debug("xpath = " + xpath);
		List eventNodes = xpath.selectNodes(element); //, eventNodeNames);
		log.debug("XPath events: " + eventNodes.size());
		Iterator iter = eventNodes.iterator();
		while (iter.hasNext()) {
		  EventJDOM item = new EventJDOM( (Element) iter.next());
		  events.add(item);
		}
	  }
	  catch (JDOMException ex) {
		ex.printStackTrace();
	  }
	}

	return events;
  }

  public static final String eventNodeNames = "BIRT | CHR | DEAT | BURI | CREM | ADOP | BAPM | BARM | BASM | BLES | CHRA | CONF | FCOM | ORDN | NATU | EMIG | IMMI | CENS | PROB | WILL | GRAD | RETI | BAPL | CONL | ENDL | SLGC | EVEN";

//  public static final String eventNodeNames = "/BIRT | /CHR | /DEAT | /BURI | /CREM | /ADOP | /BAPM | /BARM | /BASM | /BLES | /CHRA | /CONF | /FCOM | /ORDN | /NATU | /EMIG | /IMMI | /CENS | /PROB | /WILL | /GRAD | /RETI | /EVEN";
//  public static final String eventNodeNames = "//BIRT | //CHR | //DEAT | //BURI | //CREM | //ADOP | //BAPM | //BARM | //BASM | //BLES | //CHRA | //CONF | //FCOM | //ORDN | //NATU | //EMIG | //IMMI | //CENS | //PROB | //WILL | //GRAD | //RETI | //EVEN";
}
