//
//  Individual.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

package com.redbugz.macpaf;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.apple.cocoa.foundation.NSBundle;
import java.util.Collection;
import java.util.Arrays;

public interface Individual {
   public static Individual UNKNOWN = new UnknownIndividual();
   public static Individual UNKNOWN_MALE = new UnknownMaleIndividual();
   public static Individual UNKNOWN_FEMALE = new UnknownFemaleIndividual();

   public String getGivenNames();
   public void setGivenNames(String givenNames);

   public String getSurname();
   public void setSurname(String surname);

   public String getFullName();
   public void setFullName(String prefix, String givenNames, String surname, String suffix);

   public String getNamePrefix();
   public void setNamePrefix(String prefix);

   public String getNameSuffix();
   public void setNameSuffix(String suffix);

   public Gender getGender();
   public void setGender(Gender gender);

   public Event getBirthEvent();
   public void setBirthEvent(Event birthEvent);

   public Event getChristeningEvent();
   public void setChristeningEvent(Event christeningEvent);

   public Event getDeathEvent();
   public void setDeathEvent(Event deathEvent);

   public Event getBurialEvent();
   public void setBurialEvent(Event burialEvent);

   public Ordinance getLDSBaptism();
   public void setLDSBaptism(Ordinance ldsBaptism);

   public Ordinance getLDSConfirmation();
   public void setLDSConfirmation(Ordinance ldsConfirmation);

   public Ordinance getLDSEndowment();
   public void setLDSEndowment(Ordinance ldsEndowment);

   public Ordinance getLDSSealingToParents();
   public void setLDSSealingToParent(Ordinance sealingToParent);

   public boolean childrensOrdinancesAreCompleted();

   public String getId();
   public void setId(String id);

   public int getRin();
   public void setRin(int rin);

   public String getAFN();
   public void setAFN(String afn);

   public boolean isLocked();
   public void setLocked(boolean lockedFlag);

   public boolean isPrivate();
   public void setPrivate(boolean privateFlag);

   public URL getImagePath();
   public void setImagePath(URL path);

   public Individual getPrimarySpouse();
   public void setPrimarySpouse(Individual primarySpouse);

   public List getSpouseList();
   public void setSpouseList(List spouses);
   public void addSpouse(Individual newSpouse);
   public void removeSpouse(Individual removedSpouse);

   public Individual getFather();
   public void setFather(Individual father);

   public Individual getMother();
   public void setMother(Individual mother);

   public Family getFamilyAsChild();
   public void setFamilyAsChild(Family fam);

   public Family getFamilyAsSpouse();
   public void setFamilyAsSpouse(Family fam);

   public List getAssociations();
   public List getNotes();
   public String getNoteText();

  public List getEvents();

  static public class UnknownIndividual implements Individual{
      public String getGivenNames() {
         return "";
      }

      public String getSurname() {
         return "";//"Unknown";
      }

      public String getFullName() {
         return "";//"Unknown";
      }

      public String getNamePrefix() {
         return "";
      }

      public Gender getGender() {
         return Gender.UNKNOWN;
      }

      public Event getBirthEvent() {
         return new MyEvent();
      }

      public Event getChristeningEvent() {
         return new MyEvent();
      }

      public Event getDeathEvent() {
         return new MyEvent();
      }

      public Event getBurialEvent() {
         return new MyEvent();
      }

      public Ordinance getLDSBaptism() {
         return new MyOrdinance();
      }

      public Ordinance getLDSConfirmation() {
         return new MyOrdinance();
      }

      public Ordinance getLDSEndowment() {
         return new MyOrdinance();
      }

      public Ordinance getLDSSealingToParents() {
         return new MyOrdinance();
      }

      public boolean childrensOrdinancesAreCompleted() {
         return false;
      }

      public String getId() {
         return "";
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

      public URL getImagePath() {
         try {
            return new File(NSBundle.mainBundle().pathForResource("tree","gif")).toURL();
         } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
         }
         return null;
      }

      public void setImagePath(URL path) {
      }

      public Individual getPrimarySpouse() {
         return this;
      }

      public Individual getFather() {
         return this;
      }

      public Individual getMother() {
         return this;
      }

      public Family getFamilyAsChild() {
         return new Fam();
      }

      public Family getFamilyAsSpouse() {
         return new Fam();
      }

      public void setFamilyAsSpouse(Family fam) {
      }

      public void setFamilyAsChild(Family fam) {
      }

      public List getSpouseList() {
         return new ArrayList();
      }

      public void setSpouseList(List spouses) {
      }

      public List getAssociations() {
         return Collections.EMPTY_LIST;
      }

      public List getNotes() {
         return Arrays.asList(new MyNote[] {new MyNote()});
      }

      public String getNoteText() {
        return "";
      }

//      public void addSpouse(Individual newSpouse) {
//      }
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
		return "";
	}

	/* (non-Javadoc)
	 * @see Individual#removeSpouse(Individual)
	 */
	public void removeSpouse(Individual removedSpouse) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Individual#setBirthEvent(Event)
	 */
	public void setBirthEvent(Event birthEvent) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Individual#setburialEvent(Event)
	 */
	public void setBurialEvent(Event burialEvent) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Individual#setChristeningEvent(Event)
	 */
	public void setChristeningEvent(Event christeningEvent) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Individual#setDeathEvent(Event)
	 */
	public void setDeathEvent(Event deathEvent) {
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
	 * @see Individual#setGender(Gender)
	 */
	public void setGender(Gender gender) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Individual#setGivenNames(java.lang.String)
	 */
	public void setGivenNames(String givenNames) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Individual#setId(java.lang.String)
	 */
	public void setId(String id) {
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
	 * @see Individual#setSurname(java.lang.String)
	 */
	public void setSurname(String surname) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see Individual#getAFN()
	 */
	public String getAFN() {
		// TODO Auto-generated method stub
		return null;
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

  }

   public class UnknownMaleIndividual extends UnknownIndividual {
      public Gender getGender() {
         return Gender.MALE;
      }

      public Individual getPrimarySpouse() {
         return UNKNOWN_FEMALE;
      }

      public Individual getFather() {
         return UNKNOWN_MALE;
      }

      public Individual getMother() {
         return UNKNOWN_FEMALE;
      }
   }

   public class UnknownFemaleIndividual extends UnknownIndividual {
      public Gender getGender() {
         return Gender.FEMALE;
      }

      public Individual getPrimarySpouse() {
         return UNKNOWN_FEMALE;
      }

      public Individual getFather() {
         return UNKNOWN_MALE;
      }

      public Individual getMother() {
         return UNKNOWN_FEMALE;
      }
   }
}
