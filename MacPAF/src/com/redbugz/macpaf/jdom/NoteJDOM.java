package com.redbugz.macpaf.jdom;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import com.redbugz.macpaf.Note;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Apr 13, 2003
 * Time: 2:43:00 PM
 * To change this template use Options | File Templates.
 */
public class NoteJDOM implements Note {
  private static final Category log = Category.getInstance(NoteJDOM.class.getName());

//   private String id = "";
//   private StringBuffer text = new StringBuffer();
  Element element = new Element(NOTE);
  private static final String newLine = System.getProperty("line.separator");

  public NoteJDOM(Element element, MacPAFDocumentJDOM parentDocument) {
  	if (parentDocument == null) {
  		throw new IllegalArgumentException("Cannot create NoteJDOM with null parentDocument");
  	}
	if (element == null) {
  		throw new IllegalArgumentException("Cannot create NoteJDOM with null element");
	}
	this.element = element;
	log.debug("NoteJDOM() element=\n" + new XMLOutputter(Format.getPrettyFormat()).outputString(element));

//      if (element != null) {
//      String idStr = element.getAttributeValue("ID");
//      log.debug("MyNote() idStr="+idStr);
//      if (idStr != null && idStr.length() > 0) {
//      setId(idStr);
//      } else {
//         // embedded NOTE
//         // todo: should I create an internal random ID#?
//         text.append(element.getText());
//      }
//      List children = element.getChildren();
//      for (int i = 0; i < children.size(); i++) {
//         Element el = (Element) children.get(i);
////         log.debug("MyNote() el="+el);
//         if (el.getName().equals("CONT")) {
//            text.append(el.getTextTrim()).append(System.getProperty("line.separator"));
//         } else if (el.getName().equals("CONC")) {
//            text.append(el.getText());
//         }
//      }
//      }
	log.debug("MyNote() text:" + getText());
  }

//  public NoteJDOM(Note oldNote) {
//	if (oldNote instanceof NoteJDOM) {
//	  this.element = ( (NoteJDOM) oldNote).getElement();
//	}
//  }

//  public NoteJDOM() {
//	setText("This is an empty note.");
//  }

  public Element getElement() {
	return element;
  }

  public String getId() {
	return element.getAttributeValue(ID);
  }

  public String getText() {
//	if (element.getAttribute(REF) != null) {
//	  try {
//		XPath xpath = XPath.newInstance("//NOTE[@ID='" + element.getAttributeValue(REF) + "']");
//		log.debug("note xpath:" + xpath.getXPath());
//		Element noteNode = (Element) xpath.selectSingleNode(element);
//		log.debug("noteNode: " + noteNode);
//		try {
//		  new XMLOutputter(Format.getPrettyFormat()).output(noteNode, System.out);
//		}
//		catch (IOException e1) {
//		  // TODO Auto-generated catch block
//		  e1.printStackTrace();
//		}
//		element = new NoteJDOM(noteNode).getElement();
//
//	  }
//	  catch (JDOMException e) {
//		log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
//	  }
//	}

	String text = element.getText();
//     try {
	List nodes = element.getChildren(); //XPath.selectNodes(element, CONCATENATION + " | " + CONTINUATION);
	Iterator iter = nodes.iterator();
	while (iter.hasNext()) {
	  Element item = (Element) iter.next();
	  if (CONTINUATION.equalsIgnoreCase(item.getName())) {
	  	// TODO handle whitespace issues with exactness
		text += newLine + item.getText();
	  }
	  else if (CONCATENATION.equalsIgnoreCase(item.getName())) {
		text += item.getText();
	  }

	}
//   }
//   catch (JDOMException ex) {
//     ex.printStackTrace();
//   }
	return text; //.trim();
  }

  public void setId(String id) {
	element.setAttribute(ID, id);
  }

  public void setText(String text) {
	element.setText(text);
  }

}
