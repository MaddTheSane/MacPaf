//
//  Event.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Feb 16 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

package com.redbugz.macpaf;
import java.util.Date;


public interface Event {

    public Date getDate();
   public void setDate(Date date);

   public String getDateString();
   public void setDateString(String dateString);

    public Place getPlace();
   public void setPlace(Place place);

}
