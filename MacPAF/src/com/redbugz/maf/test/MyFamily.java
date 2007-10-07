package com.redbugz.maf.test;

import java.util.*;

import org.apache.log4j.*;

import com.redbugz.macpaf.MyEvent;
import com.redbugz.macpaf.MyFamily;
import com.redbugz.macpaf.MyIndividual;
import com.redbugz.macpaf.MyOrdinance;
import com.redbugz.maf.*;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:39:21 PM
 * To change this template use Options | File Templates.
 */
public class MyFamily implements Family, Cloneable {
  Individual dad, mom;
  List children = new ArrayList();
  Date weddingDate;
  private String id;
  private Event marriageEvent = new MyEvent();
  private static final Logger log = Logger.getLogger(MyFamily.class);

  public MyFamily(Individual dad, Individual mom, Individual[] children, Date weddingDate) {
	log.debug("fam constr dad=" + dad + " mom=" + mom + " children=" + children + " date=" + weddingDate);
	try {
	  this.dad = dad;
	  this.mom = mom;
	  if (children != null) {
		this.children = Arrays.asList(children);
	  }
	  this.weddingDate = weddingDate;
	  if (dad instanceof MyIndividual && mom instanceof MyIndividual) {
		( (MyIndividual)this.dad).addFamilyAsSpouse(this);
		( (MyIndividual)this.mom).addFamilyAsSpouse(this);
		for (int i = 0; i < this.children.size(); i++) {
		  Individual child = (Individual)this.children.get(i);
		  if (child instanceof MyIndividual) {
			( (MyIndividual) child).familyAsChild = this;
		  }
		}
	  }
	}
	catch (Exception e) {
	  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	}
	log.debug("dad=" + this.dad);
	log.debug("mom=" + this.mom);
	log.debug("children=" + getChildren().size());
	log.debug("wedding=" + this.weddingDate);
	log.debug("dad fam=" + getFather().getPreferredFamilyAsSpouse() + " with spouse=" +
			  getFather().getPreferredFamilyAsSpouse().getMother().getFullName());
  }

  public MyFamily() {
	dad = new Individual.UnknownMaleIndividual();
	mom = new Individual.UnknownFemaleIndividual();
	weddingDate = new Date();
  }

  public Individual getFather() {
	return dad;
  }

  public void setFather(Individual father) {
	dad = father;
  }

  public Individual getMother() {
	return mom;
  }

  public void setMother(Individual mother) {
	mom = mother;
  }

  public List getChildren() {
	if (children == null) {
	  return new ArrayList();
	}
	return children;
  }

  public void setChildren(List children) {
	this.children = children;
  }

  public void addChild(Individual newChild) {
	children.add(newChild);
  }

  public void addChildAtPosition(Individual newChild, int position) {
	children.add(position, newChild);
  }

  public Ordinance getPreferredSealingToSpouse() {
	return new MyOrdinance();
  }

  public void setPreferredSealingToSpouse(Ordinance sealing) {
  }

  public String getId() {
	return id;
  }

  public void setId(String id) {
	this.id = id;
  }

  public int getRin() {
	return -1; /** @todo implement */
  }

  public void setRin(int newRin) {
/** @todo  implement me*/
  }

  public void setMarriageEvent(MyEvent marrEvent) {
	marriageEvent = marrEvent;
  }

  public Event getPreferredMarriageEvent() {
	return marriageEvent;
  }

  /* (non-Javadoc)
   * @see com.redbugz.maf.Family#removeChildAtPosition(int)
   */
  public void removeChildAtPosition(int position) {
	// TODO Auto-generated method stub

  }

public List getEvents() {
	List list = new ArrayList();
	list.add(getPreferredMarriageEvent());
	return list;
}

public String getNoteText() {
	return "Notes not implemented yet";
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
