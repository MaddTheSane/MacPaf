package com.redbugz.macpaf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Category;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:39:21 PM
 * To change this template use Options | File Templates.
 */
public class Fam implements Family, Cloneable {
  Individual dad, mom;
  List children = new ArrayList();
  Date weddingDate;
  private String id;
  private Event marriageEvent = new MyEvent();
  private static final Category log = Category.getInstance(Fam.class.getName());

  public Fam(Individual dad, Individual mom, Individual[] children, Date weddingDate) {
	log.debug("fam constr dad=" + dad + " mom=" + mom + " children=" + children + " date=" + weddingDate);
	try {
	  this.dad = dad;
	  this.mom = mom;
	  if (children != null) {
		this.children = Arrays.asList(children);
	  }
	  this.weddingDate = weddingDate;
	  if (dad instanceof Indi && mom instanceof Indi) {
		( (Indi)this.dad).addFamilyAsSpouse(this);
		( (Indi)this.mom).addFamilyAsSpouse(this);
		for (int i = 0; i < this.children.size(); i++) {
		  Individual child = (Individual)this.children.get(i);
		  if (child instanceof Indi) {
			( (Indi) child).familyAsChild = this;
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
	log.debug("dad fam=" + getFather().getFamilyAsSpouse() + " with spouse=" +
			  getFather().getFamilyAsSpouse().getMother().getFullName());
  }

  public Fam() {
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

  public Ordinance getSealingToSpouse() {
	return new MyOrdinance();
  }

  public void setSealingToSpouse(Ordinance sealing) {
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

  public Event getMarriageEvent() {
	return marriageEvent;
  }

  /* (non-Javadoc)
   * @see com.redbugz.macpaf.Family#removeChildAtPosition(int)
   */
  public void removeChildAtPosition(int position) {
	// TODO Auto-generated method stub

  }
}