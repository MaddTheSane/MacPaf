//
//  Family.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

package com.redbugz.macpaf;
import java.util.List;

public interface Family {

    public Individual getFather();
   public void setFather(Individual father);
    public Individual getMother();
   public void setMother(Individual mother);

    public List getChildren();
   public void setChildren(List children);
   public void addChild(Individual newChild);
   public void addChildAtPosition(Individual newChild, int position);
   public void removeChildAtPosition(int position);

    public Ordinance getSealingToSpouse();
   public void setSealingToSpouse(Ordinance sealing);

   public String getId();

   public Event getMarriageEvent();
}
