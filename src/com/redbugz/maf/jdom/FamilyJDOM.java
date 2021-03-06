package com.redbugz.maf.jdom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.redbugz.maf.*;
import com.redbugz.maf.util.JDOMUtils;
import com.redbugz.maf.util.StringUtils;

/**
 * Created by IntelliJ IDEA. User: logan Date: Mar 16, 2003 Time: 3:39:21 PM To
 * change this template use Options | File Templates.
 */
public class FamilyJDOM implements Family {
	private static final Logger log = Logger.getLogger(FamilyJDOM.class);

	// Individual dad, mom;
	// List children = new ArrayList();
	// Date weddingDate;
	// private String id;
	// private Event marriageEvent = EventJDOM.createMarriageEventInstance();
	protected Element element = new Element(FAMILY);
	private MAFDocumentJDOM document = null;

	// int numberOfChildren = -1;
	// private Individual father;
	// private Individual mother;
	// private List children;

	// private FamilyJDOM(Individual dad, Individual mom, Individual[]
	// children, Date weddingDate) {
	// log.debug("fam constr dad="+dad+" mom="+mom+"
	// children="+children+" date="+weddingDate);
	// try {
	// this.dad = dad;
	// this.mom = mom;
	// if (children != null) {
	// this.children = Arrays.asList(children);
	// }
	// this.weddingDate = weddingDate;
	// if (dad instanceof IndividualJDOM && mom instanceof IndividualJDOM) {
	// ((IndividualJDOM) this.dad).addFamilyAsSpouse(this);
	// ((IndividualJDOM) this.mom).addFamilyAsSpouse(this);
	// for (int i = 0; i < this.children.size(); i++) {
	// Individual child = (Individual) this.children.get(i);
	// if (child instanceof IndividualJDOM) {
	// ((IndividualJDOM) child).familyAsChild = this;
	// }
	// }
	// }
	// } catch (Exception e) {
	// log.error("Exception: ", e); //To change body of catch statement use
	// Options |
	// File Templates.
	// }
	// log.debug("dad="+this.dad);
	// log.debug("mom="+this.mom);
	// log.debug("children="+getChildren().size());
	// log.debug("wedding="+this.weddingDate);
	// log.debug("dad fam="+getFather().getFamilyAsSpouse()+" with
	// spouse="+getFather().getFamilyAsSpouse().getMother().getFullName());
	// }

	// public FamilyJDOM(MAFDocumentJDOM parentDocument) {
	// document = parentDocument;
	// // dad = new Individual.UnknownMaleIndividual();
	// // mom = new Individual.UnknownFemaleIndividual();
	// // weddingDate = new Date();
	// element = new Element(FAMILY);
	// setId("");
	// }

	public FamilyJDOM(Element newElement, MAFDocumentJDOM parentDocument) {
		if (parentDocument == null) {
			throw new IllegalArgumentException(
					"Cannot create FamilyJDOM with null parentDocument");
		}
		if (newElement == null) {
			throw new IllegalArgumentException(
					"cannot create new FamilyJDOM with null element");
			// element = new Element(FAMILY);
		}
		document = parentDocument;
		element = newElement;
		confirmUID();
	}

	public FamilyJDOM(Family oldFamily, MAFDocumentJDOM parentDocument) {
		if (parentDocument == null) {
			throw new IllegalArgumentException(
					"Cannot create FamilyJDOM with null parentDocument");
		}
		document = parentDocument;
		if (oldFamily instanceof FamilyJDOM) {
			this.element = ((FamilyJDOM) oldFamily).getElement();
		} else {
			setChildren(oldFamily.getChildren());
			setFather(oldFamily.getFather());
			setId(oldFamily.getId());
			setMarriageEvent(oldFamily.getPreferredMarriageEvent());
			setMother(oldFamily.getMother());
			// setNumberOfChildren(oldFamily.get);
			setPreferredSealingToSpouse(oldFamily.getPreferredSealingToSpouse());
		}
		confirmUID();
	}

	private void confirmUID() {
		// Confirms the existence and validity of the UUID or GUID for this
		// Family
		if (StringUtils.isEmpty(getUID())) {
			generateUID();
		}
	}

	private void generateUID() {
		// Generates a UUID or GUID per new specifications of Family Tree
		element.addContent(new Element(UID)
						.setText(StringUtils.generateUUID()));
	}

	public Element getElement() {
		return element;
	}

	public Individual getFather() {
		// if (father == null) {
		Individual father = Individual.UNKNOWN_MALE;
		try {
			Element fatherLink = element.getChild(HUSBAND);
			if (fatherLink != null) {
				// XPath xpath = XPath.newInstance("//INDI[@ID='" +
				// fatherLink.getAttributeValue(REF) + "']");
				// log.debug("father xpath:" + xpath.getXPath());
				// Element indiNode = (Element) xpath.selectSingleNode(element);
				// log.debug("indiNode: " + indiNode);
				// try {
				// new XMLOutputter(Format.getPrettyFormat()).output(indiNode,
				// System.out);
				// }
				// catch (IOException e1) {
				// //
				// e1.printStackTrace();
				father = new IndividualLink(fatherLink.getAttributeValue(REF),
						document);
			}
			// }
		} catch (Exception e) {
			log.error("Exception: ", e); // To change body of catch statement
			// use Options | File Templates.
		}
		// }
		return father; // dad;
	}

	public void setFather(Individual father) {
		// dad = father;
		element.removeChild(HUSBAND);
		element.addContent(new Element(HUSBAND).setAttribute(REF, father
				.getId()));
	}

	public Individual getMother() {
		// if (mother == null) {
		Individual mother = Individual.UNKNOWN_FEMALE;
		try {
			Element motherLink = element.getChild(WIFE);
			if (motherLink != null) {
				// XPath xpath = XPath.newInstance("//INDI[@ID='" +
				// motherLink.getAttributeValue(REF) + "']");
				// log.debug("mother xpath:" + xpath.getXPath());
				// Element indiNode = (Element) xpath.selectSingleNode(element);
				// log.debug("indiNode: " + indiNode);
				// try {
				// new XMLOutputter(Format.getPrettyFormat()).output(indiNode,
				// System.out);
				// }
				// catch (IOException e1) {
				// //
				// e1.printStackTrace();
				// }
				mother = new IndividualLink(motherLink.getAttributeValue(REF),
						document);
			}
		} catch (Exception e) {
			log.error("Exception: ", e); // To change body of catch statement
			// use Options | File Templates.
		}
		// }
		return mother; // dad; return new
		// IndividualJDOM(element.getChild(WIFE), document);
		// //mom;
	}

	public void setMother(Individual mother) {
		// this.mother = mother;
		JDOMUtils.findOrMakeChildElement(WIFE, element).setAttribute(REF,
				mother.getId());
		// element.removeChild(WIFE);
		// element.addContent(
		// new Element(WIFE).setAttribute(REF, mother.getId()));
	}

	public List getChildren() {
		// if (children == null) {
		List childrenElements = element.getChildren("CHIL");
		if (childrenElements == null) {
			childrenElements = new ArrayList();
		}
		List children = new ArrayList();
		for (Iterator iter = childrenElements.iterator(); iter.hasNext();) {
			// Individual child = new IndividualJDOM((Element) iter.next(),
			// document);
			Individual child = Individual.UNKNOWN;
			try {
				Element childLink = (Element) iter.next();
				if (childLink != null) {
					// XPath xpath = XPath.newInstance("//INDI[@ID='" +
					// childLink.getAttributeValue(REF) + "']");
					// log.debug("child xpath:" + xpath.getXPath());
					// Element childNode = (Element)
					// xpath.selectSingleNode(element);
					// log.debug("childNode: " + childNode);
					// try {
					// new
					// XMLOutputter(Format.getPrettyFormat()).output(childNode,
					// System.out);
					// }
					// catch (IOException e1) {
					// //
					// e1.printStackTrace();
					// }
					child = new IndividualLink(
							childLink.getAttributeValue(REF), document);
				}
			} catch (Exception e) {
				log.error("Exception: ", e); // To change body of catch
				// statement use Options | File
				// Templates.
			}
			children.add(child);
		}
		// }
		return children;
	}

	public void setChildren(List children) {
		// this.children = children;
		element.removeChildren("CHIL");
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			Individual child = (Individual) iter.next();
			element.addContent(new Element("CHIL").setAttribute(REF, child
					.getId()));
		}
	}

	public void addChild(Individual newChild) {
		// children.add(newChild);
		element.addContent(new Element("CHIL").setAttribute(REF, newChild
				.getId()));
	}

	public void addChildAtPosition(Individual newChild, int position) {
		List children = getChildren();
		IndividualLink newChildLink = new IndividualLink(newChild.getId(),
				document);
		if (position > children.size()) {
			children.add(newChildLink);
		} else {
			children.add(position, newChildLink);
		}
		setChildren(children);
	}

	public Ordinance getPreferredSealingToSpouse() {
		return new OrdinanceJDOM(JDOMUtils.findOrMakeChildElement(
				OrdinanceJDOM.LDS_SEALING_SPOUSE, element));
	}

	public void setPreferredSealingToSpouse(Ordinance sealing) {
		if (sealing instanceof Ordinance.UnknownOrdinance) {
			// do nothing
			return;
		} else {
			JDOMUtils.replaceFirstChildElement(element, new OrdinanceJDOM(
					sealing).getElement());
		}

	}

	public String getId() {
		return element.getAttributeValue(ID, "");
	}

	public void setId(String id) {
		// this.id = id;
		element.setAttribute(ID, id);
	}

	public void setMarriageEvent(Event marriageEvent) {
		if (marriageEvent instanceof Event.UnknownEvent) {
			// do nothing
			return;
		} else {
			JDOMUtils.replaceFirstChildElement(element, new EventJDOM(
					marriageEvent).getElement());
		}
	}

	public Event getPreferredMarriageEvent() {
		return new EventJDOM(JDOMUtils.findOrMakeChildElement(Family.MARRIAGE,
				element));
	}

	/**
	 * @return Returns the numberOfChildren.
	 */
	public int getNumberOfChildren() {
		int numberOfChildren = 0;
		try {
			numberOfChildren = new Integer(element.getChildTextTrim("NCHI"))
					.intValue();
		} catch (Exception e) {
			numberOfChildren = getChildren().size();
		}
		return numberOfChildren;
	}

	/**
	 * @param numberOfChildren
	 *            The numberOfChildren to set.
	 */
	public void setNumberOfChildren(int numberOfChildren) {
		element.addContent(new Element("NCHI").setText(String
				.valueOf(numberOfChildren)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redbugz.maf.Family#removeChildAtPosition(int)
	 */
	public void removeChildAtPosition(int position) {
		List children = getChildren();
		Individual removedChild = (Individual) children.remove(position);
		removedChild.removeFamilyAsChild(this);
		setChildren(children);
		// force list of children to reload next time
		// children = null;
	}

	/**
	 * getRin
	 * 
	 * @return int
	 */
	public int getRin() {
		try {
			return Integer.parseInt(element.getChildText(RIN));
		} catch (Exception ex) {
			return -1;
		}
	}

	/**
	 * setRin
	 * 
	 * @param newRin
	 *            int
	 */
	public void setRin(int newRin) {
		Element rin = JDOMUtils.findOrMakeChildElement(RIN, element);
		// if (rin == null) {
		// rin = new Element(RIN);
		// element.addContent(rin);
		// }
		rin.setText(String.valueOf(newRin));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "FamilyJDOM element:"
				+ new XMLOutputter(Format.getPrettyFormat())
						.outputString(element); // super.toString();
	}

	public void addEvent(Event event) {
		element.addContent(new EventJDOM(event).getElement());
	}

	public List getEvents() {
		// log.debug("FamilyJDOM.getEvents()");
		// if (events == null) {
		log.debug("FamilyJDOM.getEvents() creating event list");
		List events = new ArrayList();
		try {
			XPath xpath = XPath.newInstance(eventNodeNames);
			log.debug("xpath = " + xpath);
			List eventNodes = xpath.selectNodes(element); // ,
			// eventNodeNames);
			log.debug("XPath events: " + eventNodes.size());
			Iterator iter = eventNodes.iterator();
			while (iter.hasNext()) {
				EventJDOM item = new EventJDOM((Element) iter.next());
				events.add(item);
			}
		} catch (JDOMException ex) {
			ex.printStackTrace();
		}
		log.debug("FamilyJDOM.getEvents() creating event list2");
		List events2 = new ArrayList();
		try {

			List eventNodes = element.getChildren();// xpath.selectNodes(element);
			// //, eventNodeNames);
			log.debug("child events: " + eventNodes.size());
			Iterator iter = eventNodes.iterator();
			while (iter.hasNext()) {

				Element next = (Element) iter.next();
				if (eventNodeNames.indexOf(next.getName()) >= 0) {
					EventJDOM item = new EventJDOM(next);
					events2.add(item);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// }
		log.debug("returning events: " + events.size());
		log.debug("not returning events2: " + events2.size());
		return events;
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

	private List getNotes() {
		List notes = new ArrayList();
		List children = element.getChildren(NoteJDOM.NOTE);
		Iterator iter = children.iterator();
		while (iter.hasNext()) {
			Element item = (Element) iter.next();
			notes.add(new NoteJDOM(item, document));
		}
		return notes;
	}

	public String getUID() {
		return StringUtils.nonNullString(element.getChildText(UID));
	}

	public static final String eventNodeNames = "ANUL | CENS | DIV | DIVF | ENGA | MARR | MARB | MARC | MARL | MARS | EVEN";

	public void reorderChildToPosition(Individual child, int newPosition) {
		// TODO Auto-generated method stub

	}

	public List getMarriageEvents() {
		List eventElements = element.getChildren(Family.MARRIAGE);
		if (eventElements == null) {
			eventElements = new ArrayList();
		}
		List marriageEvents = new ArrayList();
		for (Iterator iter = eventElements.iterator(); iter.hasNext();) {
			marriageEvents.add(new EventJDOM((Element) iter.next()));
		}
		return marriageEvents;
	}

}
