package com.redbugz.macpaf.jdom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.Ordinance;

/**
 * Created by IntelliJ IDEA. User: logan Date: Mar 16, 2003 Time: 3:39:21 PM To
 * change this template use Options | File Templates.
 */
public class FamilyJDOM implements Family {
	//   Individual dad, mom;
	//   List children = new ArrayList();
	//   Date weddingDate;
	//   private String id;
	//   private Event marriageEvent = EventJDOM.createMarriageEventInstance();
	private static final String FAMILY = "FAM";
	private static final String HUSBAND = "HUSB";
	private static final String WIFE = "WIFE";
	protected Element element = new Element(FAMILY);
	private Document document = null;
	//   int numberOfChildren = -1;
	private Individual father;
	private Individual mother;
	private List children;

	//   private FamilyJDOM(Individual dad, Individual mom, Individual[]
	// children, Date weddingDate) {
	//      System.out.println("fam constr dad="+dad+" mom="+mom+"
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
	//         e.printStackTrace(); //To change body of catch statement use Options |
	// File Templates.
	//      }
	//      System.out.println("dad="+this.dad);
	//      System.out.println("mom="+this.mom);
	//      System.out.println("children="+getChildren().size());
	//      System.out.println("wedding="+this.weddingDate);
	//      System.out.println("dad fam="+getFather().getFamilyAsSpouse()+" with
	// spouse="+getFather().getFamilyAsSpouse().getMother().getFullName());
	//   }

	public FamilyJDOM(Document parentDocument) {
		document = parentDocument;
		//      dad = new Individual.UnknownMaleIndividual();
		//      mom = new Individual.UnknownFemaleIndividual();
		//      weddingDate = new Date();
		element = new Element(FAMILY);
		setId("0");
	}

	public FamilyJDOM(Element element, Document parentDocument) {
		document = parentDocument;
		if (element == null) {
			element = new Element(FAMILY);
		}
		this.element = element;
	}

	public FamilyJDOM(Family oldFamily, Document parentDocument) {
		document = parentDocument;
		if (oldFamily instanceof FamilyJDOM) {
			this.element = ((FamilyJDOM) oldFamily).getElement();
		} else {
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
		if (father == null) {
		father = new IndividualJDOM(document);
		try {
			Element fatherLink = element.getChild(HUSBAND);
			if (fatherLink != null) {
				XPath xpath = XPath.newInstance("//INDI[@ID='"+fatherLink.getAttributeValue("REF")+"']");
				System.out.println("father xpath:"+xpath.getXPath());
				Element indiNode = (Element) xpath.selectSingleNode(element);
				System.out.println("indiNode: "+indiNode);
				try {
					new XMLOutputter(Format.getPrettyFormat()).output(indiNode, System.out);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				father = new IndividualJDOM(indiNode, document);
			}
		} catch (JDOMException e) {
			e.printStackTrace();  //To change body of catch statement use Options | File Templates.
		}
		}
		return father; //dad;
	}

	public void setFather(Individual father) {
		//      dad = father;
		element.removeChild(HUSBAND);
		element.addContent(
			new Element(HUSBAND).setAttribute("REF", father.getId()));
	}

	public Individual getMother() {
		if (mother == null) {
		mother = new IndividualJDOM(document);
		try {
			Element motherLink = element.getChild(WIFE);
			if (motherLink != null) {
				XPath xpath = XPath.newInstance("//INDI[@ID='"+motherLink.getAttributeValue("REF")+"']");
				System.out.println("mother xpath:"+xpath.getXPath());
				Element indiNode = (Element) xpath.selectSingleNode(element);
				System.out.println("indiNode: "+indiNode);
				try {
					new XMLOutputter(Format.getPrettyFormat()).output(indiNode, System.out);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mother = new IndividualJDOM(indiNode, document);
			}
		} catch (JDOMException e) {
			e.printStackTrace();  //To change body of catch statement use Options | File Templates.
		}
		}
		return mother; //dad;		return new IndividualJDOM(element.getChild(WIFE), document); //mom;
	}

	public void setMother(Individual mother) {
		//      mom = mother;
		element.removeChild(WIFE);
		element.addContent(
			new Element(WIFE).setAttribute("REF", mother.getId()));
	}

	public List getChildren() {
		if (children == null) {
		List childrenElements = element.getChildren("CHIL");
		if (childrenElements == null) {
			childrenElements = new ArrayList();
		}
		children = new ArrayList();
		for (Iterator iter = childrenElements.iterator(); iter.hasNext();) {
//			Individual child = new IndividualJDOM((Element) iter.next(), document);
			Individual child = new IndividualJDOM(document);
			try {
				Element childLink = (Element) iter.next();
				if (childLink != null) {
					XPath xpath = XPath.newInstance("//INDI[@ID='"+childLink.getAttributeValue("REF")+"']");
					System.out.println("child xpath:"+xpath.getXPath());
					Element childNode = (Element) xpath.selectSingleNode(element);
					System.out.println("childNode: "+childNode);
					try {
						new XMLOutputter(Format.getPrettyFormat()).output(childNode, System.out);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					child = new IndividualJDOM(childNode, document);
				}
			} catch (JDOMException e) {
				e.printStackTrace();  //To change body of catch statement use Options | File Templates.
			}
			children.add(child);
		}
		}
		return children;
	}

	public void setChildren(List children) {
		//      this.children = children;
		element.removeChildren("CHIL");
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			Individual child = (Individual) iter.next();
			element.addContent(
				new Element("CHIL").setAttribute("REF", child.getId()));
		}
	}

	public void addChild(Individual newChild) {
		//      children.add(newChild);
		element.addContent(
			new Element("CHIL").setAttribute("REF", newChild.getId()));
	}

	public void addChildAtPosition(Individual newChild, int position) {
		List children = element.getChildren("CHIL");
		children.add(position, newChild);
		setChildren(children);
	}

	public Ordinance getSealingToSpouse() {
		return OrdinanceJDOM.createSealingToSpouseInstance();
	}

	public void setSealingToSpouse(Ordinance sealing) {
		// todo implement this method
	}

	public String getId() {
		return element.getAttributeValue("ID");
	}

	public void setId(String id) {
		//      this.id = id;
		element.setAttribute("ID", id);
	}

	public void setMarriageEvent(Event marriageEvent) {
		//      this.marriageEvent = marriageEvent;
		element.addContent(new EventJDOM(marriageEvent).getElement());
	}

	public Event getMarriageEvent() {
		return new EventJDOM(element.getChild("MARR"));
	}
	/**
	 * @return Returns the numberOfChildren.
	 */
	public int getNumberOfChildren() {
		int numberOfChildren = 0;
		try {
			numberOfChildren =
				new Integer(element.getChildTextTrim("NCHI")).intValue();
		} catch (Exception e) {
			return getChildren().size();
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
		child.element.detach();
		// force list of children to reload next time
		children = null;
	}

}
