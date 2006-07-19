package com.redbugz.macpaf.test;

import java.util.*;

import org.apache.log4j.*;
import org.jdom.*;

import com.redbugz.macpaf.*;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Apr 13, 2003
 * Time: 2:43:00 PM
 * To change this template use Options | File Templates.
 */
public class MyNote implements Note {
  private static final Logger log = Logger.getLogger(MyNote.class);
  private String id = "";
  private StringBuffer text = new StringBuffer();

  public MyNote(Element element) {
	log.debug("MyNote() element=" + element);
	if (element != null) {
	  String idStr = element.getAttributeValue("ID");
	  log.debug("MyNote() idStr=" + idStr);
	  if (idStr != null && idStr.length() > 0) {
		setId(idStr);
	  }
	  else {
		// embedded NOTE
		// todo: should I create an internal random ID#?
		text.append(element.getText());
	  }
	  List children = element.getChildren();
	  for (int i = 0; i < children.size(); i++) {
		Element el = (Element) children.get(i);
//         log.debug("MyNote() el="+el);
		if (el.getName().equals("CONT")) {
		  text.append(el.getTextTrim()).append(System.getProperty("line.separator"));
		}
		else if (el.getName().equals("CONC")) {
		  text.append(el.getText());
		}
	  }
	}
	log.debug("MyNote() text:" + text);
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
