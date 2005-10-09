package com.redbugz.macpaf.jdom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.Ordinance;
import com.redbugz.macpaf.util.JDOMUtils;

/**
 * Created by IntelliJ IDEA. User: logan Date: Mar 16, 2003 Time: 3:39:21 PM To
 * change this template use Options | File Templates.
 */
public class FamilyJDOM implements Family {
  private static final Logger log = Logger.getLogger(FamilyJDOM.class);

  //   Individual dad, mom;
  //   List children = new ArrayList();
  //   Date weddingDate;
  //   private String id;
  //   private Event marriageEvent = EventJDOM.createMarriageEventInstance();
  protected Element element = new Element(FAMILY);
  private MacPAFDocumentJDOM document = null;

  //   int numberOfChildren = -1;
//  private Individual father;
//  private Individual mother;
//  private List children;

  //   private FamilyJDOM(Individual dad, Individual mom, Individual[]
  // children, Date weddingDate) {
  //      log.debug("fam constr dad="+dad+" mom="+mom+"
  // children="+children+" date="+weddingDate);
  //      try {
  //         this.dad = dad;
  //         this.mom = mom;
  //         if (children != null) {
  //         this.children = Arrays.asList(children);
  //         }
  //         this.weddingDate = weddingDate;
  //         if (dad instanceof IndividualJDOM && mom instanceof IndividualJDOM) {
  //            ((IndividualJDOM) this.dad).addFamilyAsSpouse(this);
  //            ((IndividualJDOM) this.mom).addFamilyAsSpouse(this);
  //            for (int i = 0; i < this.children.size(); i++) {
  //               Individual child = (Individual) this.children.get(i);
  //               if (child instanceof IndividualJDOM) {
  //                  ((IndividualJDOM) child).familyAsChild = this;
  //               }
  //            }
  //         }
  //      } catch (Exception e) {
  //         log.error("Exception: ", e); //To change body of catch statement use Options |
  // File Templates.
  //      }
  //      log.debug("dad="+this.dad);
  //      log.debug("mom="+this.mom);
  //      log.debug("children="+getChildren().size());
  //      log.debug("wedding="+this.weddingDate);
  //      log.debug("dad fam="+getFather().getFamilyAsSpouse()+" with
  // spouse="+getFather().getFamilyAsSpouse().getMother().getFullName());
  //   }

//  public FamilyJDOM(MacPAFDocumentJDOM parentDocument) {
//	document = parentDocument;
//	//      dad = new Individual.UnknownMaleIndividual();
//	//      mom = new Individual.UnknownFemaleIndividual();
//	//      weddingDate = new Date();
//	element = new Element(FAMILY);
//	setId("");
//  }

  public FamilyJDOM(Element element, MacPAFDocumentJDOM parentDocument) {
  	if (parentDocument == null) {
  		throw new IllegalArgumentException("Cannot create FamilyJDOM with null parentDocument");
  	}
	if (element == null) {
		throw new IllegalArgumentException("cannot create new FamilyJDOM with null element");
//	  element = new Element(FAMILY);
	}
	document = parentDocument;
	this.element = element;
  }

  public FamilyJDOM(Family oldFamily, MacPAFDocumentJDOM parentDocument) {
  	if (parentDocument == null) {
  		throw new IllegalArgumentException("Cannot create FamilyJDOM with null parentDocument");
  	}
	document = parentDocument;
	if (oldFamily instanceof FamilyJDOM) {
	  this.element = ( (FamilyJDOM) oldFamily).getElement();
	}
	else {
	  setChildren(oldFamily.getChildren());
	  setFather(oldFamily.getFather());
	  setId(oldFamily.getId());
	  setMarriageEvent(oldFamily.getMarriageEvent());
	  setMother(oldFamily.getMother());
	  //            setNumberOfChildren(oldFamily.get);
	  setSealingToSpouse(oldFamily.getSealingToSpouse());
	}
  }

  public Element getElement() {
	return element;
  }

  public Individual getFather() {
//	if (father == null) {
	  Individual father = Individual.UNKNOWN_MALE;
	  try {
		Element fatherLink = element.getChild(HUSBAND);
		if (fatherLink != null) {
//		  XPath xpath = XPath.newInstance("//INDI[@ID='" + fatherLink.getAttributeValue(REF) + "']");
//		  log.debug("father xpath:" + xpath.getXPath());
//		  Element indiNode = (Element) xpath.selectSingleNode(element);
//		  log.debug("indiNode: " + indiNode);
//		  try {
//			new XMLOutputter(Format.getPrettyFormat()).output(indiNode, System.out);
//		  }
//		  catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
			  father = new IndividualLink(fatherLink.getAttributeValue(REF), document);
		  }
//		}
	  }
	  catch (Exception e) {
		log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	  }
//	}
	return father; //dad;
  }

  public void setFather(Individual father) {
	//      dad = father;
	element.removeChild(HUSBAND);
	element.addContent(
		new Element(HUSBAND).setAttribute(REF, father.getId()));
  }

  public Individual getMother() {
//	if (mother == null) {
	 Individual mother = Individual.UNKNOWN_FEMALE;
	  try {
		Element motherLink = element.getChild(WIFE);
		if (motherLink != null) {
//		  XPath xpath = XPath.newInstance("//INDI[@ID='" + motherLink.getAttributeValue(REF) + "']");
//		  log.debug("mother xpath:" + xpath.getXPath());
//		  Element indiNode = (Element) xpath.selectSingleNode(element);
//		  log.debug("indiNode: " + indiNode);
//		  try {
//			new XMLOutputter(Format.getPrettyFormat()).output(indiNode, System.out);
//		  }
//		  catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		  }
		  mother = new IndividualLink(motherLink.getAttributeValue(REF), document);
		}
	  }
	  catch (Exception e) {
		log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	  }
//	}
	return mother; //dad;		return new IndividualJDOM(element.getChild(WIFE), document); //mom;
  }

  public void setMother(Individual mother) {
//	this.mother = mother;
	  JDOMUtils.findOrMakeChildElement(WIFE, element).setAttribute(REF, mother.getId());
//	element.removeChild(WIFE);
//	element.addContent(
//		new Element(WIFE).setAttribute(REF, mother.getId()));
  }

  public List getChildren() {
//	if (children == null) {
	  List childrenElements = element.getChildren("CHIL");
	  if (childrenElements == null) {
		childrenElements = new ArrayList();
	  }
	  List children = new ArrayList();
	  for (Iterator iter = childrenElements.iterator(); iter.hasNext(); ) {
//			Individual child = new IndividualJDOM((Element) iter.next(), document);
		Individual child = Individual.UNKNOWN;
		try {
		  Element childLink = (Element) iter.next();
		  if (childLink != null) {
//			XPath xpath = XPath.newInstance("//INDI[@ID='" + childLink.getAttributeValue(REF) + "']");
//			log.debug("child xpath:" + xpath.getXPath());
//			Element childNode = (Element) xpath.selectSingleNode(element);
//			log.debug("childNode: " + childNode);
//			try {
//			  new XMLOutputter(Format.getPrettyFormat()).output(childNode, System.out);
//			}
//			catch (IOException e1) {
//			  // TODO Auto-generated catch block
//			  e1.printStackTrace();
//			}
			child = new IndividualLink(childLink.getAttributeValue(REF), document);
		  }
		}
		catch (Exception e) {
		  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
		}
		children.add(child);
	  }
//	}
	return children;
  }

  public void setChildren(List children) {
	//      this.children = children;
	element.removeChildren("CHIL");
	for (Iterator iter = children.iterator(); iter.hasNext(); ) {
	  Individual child = (Individual) iter.next();
	  element.addContent(
		  new Element("CHIL").setAttribute(REF, child.getId()));
	}
  }

  public void addChild(Individual newChild) {
	//      children.add(newChild);
	element.addContent(
		new Element("CHIL").setAttribute(REF, newChild.getId()));
  }

  public void addChildAtPosition(Individual newChild, int position) {
	List children = getChildren();
	children.add(position, new IndividualLink(newChild.getId(), document));
	setChildren(children);
  }

  public Ordinance getSealingToSpouse() {
	return new OrdinanceJDOM(JDOMUtils.findOrMakeChildElement(OrdinanceJDOM.LDS_SEALING_SPOUSE, element));
  }

  public void setSealingToSpouse(Ordinance sealing) {
	  Ordinance sealingToSpouse = getSealingToSpouse();
	  if (sealingToSpouse instanceof Ordinance.UnknownOrdinance) {
		  if (sealing instanceof OrdinanceJDOM) {
			  sealingToSpouse = new OrdinanceJDOM(((OrdinanceJDOM)sealing).getElement());
		  }
		  element.addContent(((OrdinanceJDOM) sealingToSpouse).getElement());
	  }
	  // TODO not sure what to do here yet
  }

  public String getId() {
	return element.getAttributeValue(ID, "");
  }

  public void setId(String id) {
	//      this.id = id;
	element.setAttribute(ID, id);
  }

  public void setMarriageEvent(Event marriageEvent) {
	element.addContent(new EventJDOM(marriageEvent).getElement());
  }

  public Event getMarriageEvent() {
	return new EventJDOM(JDOMUtils.findOrMakeChildElement(Family.MARRIAGE, element));
  }

  /**
   * @return Returns the numberOfChildren.
   */
  public int getNumberOfChildren() {
	int numberOfChildren = 0;
	try {
	  numberOfChildren =
		  new Integer(element.getChildTextTrim("NCHI")).intValue();
	}
	catch (Exception e) {
	  numberOfChildren = getChildren().size();
	}
	return numberOfChildren;
  }

  /**
   * @param numberOfChildren
   *            The numberOfChildren to set.
   */
  public void setNumberOfChildren(int numberOfChildren) {
	element.addContent(
		new Element("NCHI").setText(String.valueOf(numberOfChildren)));
  }

  /* (non-Javadoc)
   * @see com.redbugz.macpaf.Family#removeChildAtPosition(int)
   */
  public void removeChildAtPosition(int position) {
	IndividualJDOM child = (IndividualJDOM) getChildren().get(position);
	child.getElement().detach();
	// force list of children to reload next time
//	children = null;
  }

  /**
   * getRin
   *
   * @return int
   */
  public int getRin() {
	try {
	  return Integer.parseInt(element.getChildText(RIN));
	}
	catch (Exception ex) {
	  return -1;
	}
  }

  /**
   * setRin
   *
   * @param newRin int
   */
  public void setRin(int newRin) {
	Element rin = JDOMUtils.findOrMakeChildElement(RIN, element);
//	if (rin == null) {
//	  rin = new Element(RIN);
//	  element.addContent(rin);
//	}
	rin.setText(String.valueOf(newRin));
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
	return "FamilyJDOM element:" + new XMLOutputter(Format.getPrettyFormat()).outputString(element); //super.toString();
  }

public List getEvents() {
	// TODO: get all the events
	List list = new ArrayList();
	list.add(getMarriageEvent());
	return list;
}

}
