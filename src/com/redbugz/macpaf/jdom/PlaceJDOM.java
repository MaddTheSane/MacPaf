package com.redbugz.macpaf.jdom;


import java.util.StringTokenizer;

import org.jdom.Element;

import com.redbugz.macpaf.Place;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:37:00 PM
 * To change this template use Options | File Templates.
 */
public class PlaceJDOM implements Place {
    public static final String PLACE = "PLAC";

   protected String level1 = "";
   protected String level2 = "";
   protected String level3 = "";
   protected String level4 = "";
   protected Element element = new Element(PLACE);

    public PlaceJDOM() {
   }

   public PlaceJDOM(String placeString) {
      setPlaceString(placeString);
      element.setText(placeString);
   }

   public PlaceJDOM(Element element) {
       if (element == null) {
           element = new Element(PLACE);
       }
       this.element = element;
       setPlaceString(this.element.getText());
      System.out.println("MyPlace() place="+getFormatString());
   }
   
   public PlaceJDOM(Place oldPlace) {
   	if (oldPlace instanceof PlaceJDOM) {
   		element = ((PlaceJDOM)oldPlace).getElement();
   	} else {
   		setPlaceString(oldPlace.getFormatString());
   	}
   }
   
   public Element getElement() {
   	return element;
   }

   private void setPlaceString(String placeString) {
	  try {
          element.setText(placeString);
		 StringTokenizer st = new StringTokenizer(placeString, ",");
		 level1 = st.nextToken().trim();
		 level2 = st.nextToken().trim();
		 level3 = st.nextToken().trim();
		 level4 = st.nextToken().trim();
	  } catch (Exception e) {
		 //e.printStackTrace();  //To change body of catch statement use Options | File Templates.
	  }
   }

   public String getLevel1() {
      return level1;
   }

   public String getLevel2() {
      return level2;
   }

   public String getLevel3() {
      return level3;
   }

   public String getLevel4() {
      return level4;
   }

   public String getFormatString() {
      String result = getLevel4();
      if (result.length() > 0) {
         result = ", " + result;
      }
      result = getLevel3() + result;
      if (result.length() > 0) {
         result = ", " + result;
      }
      result = getLevel2() + result;
      if (result.length() > 0) {
         result = ", " + result;
      }
      result = getLevel1() + result;
      return result;
   }

   public void setLevel1(String level1) {
      this.level1 = level1;
       element.setText(getFormatString());
   }

   public void setLevel2(String level2) {
      this.level2 = level2;
       element.setText(getFormatString());
   }

   public void setLevel3(String level3) {
      this.level3 = level3;
       element.setText(getFormatString());
   }

   public void setLevel4(String level4) {
      this.level4 = level4;
       element.setText(getFormatString());
   }
}
