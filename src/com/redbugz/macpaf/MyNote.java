package com.redbugz.macpaf;
import java.util.List;

import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Apr 13, 2003
 * Time: 2:43:00 PM
 * To change this template use Options | File Templates.
 */
public class MyNote implements Note {
   private String id = "";
   private StringBuffer text = new StringBuffer();

   public MyNote(Element element) {
      System.out.println("MyNote() element="+element);
      if (element != null) {
      String idStr = element.getAttributeValue("ID");
      System.out.println("MyNote() idStr="+idStr);
      if (idStr != null && idStr.length() > 0) {
      setId(idStr);
      } else {
         // embedded NOTE
         // todo: should I create an internal random ID#?
         text.append(element.getText());
      }
      List children = element.getChildren();
      for (int i = 0; i < children.size(); i++) {
         Element el = (Element) children.get(i);
//         System.out.println("MyNote() el="+el);
         if (el.getName().equals("CONT")) {
            text.append(el.getTextTrim()).append(System.getProperty("line.separator"));
         } else if (el.getName().equals("CONC")) {
            text.append(el.getText());
         }
      }
      }
      System.out.println("MyNote() text:"+text);
   }

   public MyNote() {
      text = new StringBuffer("This is an empty note.");
   }

   public String getId() {
      return id;
   }

   public String getText() {
      return text.toString();
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setText(String text) {
      this.text = new StringBuffer(text);
   }
}
