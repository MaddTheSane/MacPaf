package com.redbugz.macpaf.jdom;

import java.util.Map;
import org.apache.log4j.Category;
import java.util.HashMap;
import com.redbugz.macpaf.Header;
import com.redbugz.macpaf.Submission;
import com.redbugz.macpaf.Individual;
import java.io.File;
import org.jdom.Document;
import org.jdom.Element;
import com.redbugz.macpaf.Family;
import org.jdom.Content;
import java.io.OutputStream;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;
import java.io.IOException;
import com.redbugz.macpaf.util.XMLTest;
import java.util.List;
import com.redbugz.macpaf.Note;
import org.jdom.xpath.XPath;
import java.util.Iterator;
import org.jdom.JDOMException;
import com.redbugz.macpaf.Multimedia;

public class MacPAFDocumentJDOM {
  private static final Category log = Category.getInstance(MacPAFDocumentJDOM.class.getName());

  protected Map families = new HashMap();
  protected Map individuals = new HashMap();
  protected Map multimedia = new HashMap();
  protected Map notes = new HashMap();
  protected Map sources = new HashMap();
  protected Map repositories = new HashMap();
  protected Map submitters = new HashMap();

  File gedcomFile = null;
  File xmlFile = null;

  /**
   * This is the main jdom document that holds all of the data
   */
  private Document doc; // = makeInitialGedcomDoc();


  // Document structures
  Header header = new HeaderJDOM();
  Submission submission = null;


  /**
   * This is the main individual to whom apply all actions
   */
  private Individual primaryIndividual = new Individual.UnknownIndividual();

  public MacPAFDocumentJDOM() {
	Element root = new Element("GED");
	doc = new Document(root);
	root.addContent(new HeaderJDOM().getElement());
	root.addContent(new SubmitterJDOM().getElement());
  }

  public void addFamily(Family newFamily) {
	System.out.println("MacPAFDocumentJDOM.addFamily(newFamily)");
	families.put(newFamily.getId(), newFamily);
	log.debug("added fam with key: "+newFamily.getId()+" fam marr date: "+newFamily.getMarriageEvent().getDateString());
	if (newFamily instanceof FamilyJDOM) {
	  log.debug("adding fam to doc: "+newFamily);
	  doc.getRootElement().addContent((Content)((FamilyJDOM)newFamily).getElement().clone());
}

}

  public void addIndividual(Individual newIndividual) {
	individuals.put(newIndividual.getId(), newIndividual);
if (newIndividual instanceof IndividualJDOM) {
  log.debug("adding indi to doc: "+newIndividual);
  doc.getRootElement().addContent((Content)((IndividualJDOM)newIndividual).getElement().clone());
}

}

  /**
   * outputToXML
   *
   * @param outputStream OutputStream
   */
  public void outputToXML(OutputStream outputStream) throws IOException {
	XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
	out.output(doc, outputStream);
  }

  /**
   * outputToXML
   *
   * @param outputStream OutputStream
   */
  public void outputToGedcom(OutputStream outputStream) {
	XMLTest.outputWithKay(doc, outputStream);
  }

  // uses parser from Kay (gedml)
  public void loadXMLFile(File file) {
	if (file == null) {
	  throw new IllegalArgumentException("Cannot parse XML file because file is null.");
	}
	long start = System.currentTimeMillis();
//      Document doc = XMLTest.parseGedcom(file);
	Document newDoc = XMLTest.docParsedWithKay(file);
	Element root = newDoc.getRootElement();
	long end = System.currentTimeMillis();
	log.debug("Time to parse: " + (end - start) / 1000D + " seconds.");
	List fams = root.getChildren("FAM");
	List indis = root.getChildren("INDI");
	List multimedias = root.getChildren("OBJE");
	List notes = root.getChildren("NOTE");
	List repositories = root.getChildren("REPO");
	List sources = root.getChildren("SOUR");
	List submitters = root.getChildren("SUBM");
	log.debug("header: " + new HeaderJDOM(root.getChild("HEAD")));
	log.debug("submission record: " + new SubmitterJDOM(root.getChild("SUBN")));
	log.debug("file has:\n\t" + indis.size() + " individuals\n\t" + fams.size() + " families\n\t" + notes.size() +
			  " notes\n\t" + multimedias.size() + " multimedia objects\n\t" + sources.size() + " sources\n\t" +
			  repositories.size() + " repositories\n\t" + submitters.size() + " submitters\n");

	boolean debug = false;
	Runtime rt = Runtime.getRuntime();

	log.debug("------------------------------");
	log.debug("-------------------Individuals");
	log.debug("------------------------------");
	Individual firstIndi = Individual.UNKNOWN;
	for (int i = 0; i < indis.size(); i++) {
	  Element element = (Element) indis.get(i);
	  if (debug) {
		log.debug("element:" + element.getContent());
	  }
	  if (debug) {
		log.debug("name:" + element.getChildText("NAME"));
		//log.debug("bday:" + element.getChild("BIRT").getChildText("DATE"));
	  }
	  Individual indi = new IndividualJDOM(element, doc);
//            Element noteEl = element.getChild("NOTE");
//            Note note = new MyNote();
//            if (noteEl != null && noteEl.getAttributeValue("REF") != null) {
//                note = (Note) noteMap.get(noteEl.getAttributeValue("REF"));
//            } else {
//                note = new MyNote(noteEl);
//            }
//            indi.setNote(note);
	  String oldKey = indi.getId();
	  addIndividual(indi);
	  String newKey = indi.getId();
	  // ID may change when individual is inserted if it is a duplicate
	  System.out.println("indi oldkey="+oldKey+" newkey="+newKey);
	  if (!newKey.equals(oldKey)) {
	  /** @todo change all instances of the old key to new key in import document */
	  try {
		String ref = "[@REF='" + oldKey + "']";
		List needsFixing = XPath.selectNodes(newDoc, "//HUSB"+ref+" | //WIFE"+ref+" | //CHIL"+ref+" | //ALIA"+ref);
		log.debug("needsFixing: " + needsFixing);
		Iterator iter = needsFixing.iterator();
		while (iter.hasNext()) {
		  Element item = (Element) iter.next();
		  log.debug("setting REF from " + item.getAttribute("REF") + " to " + newKey);
		  item.setAttribute("REF", newKey);
		}
	  }
	  catch (JDOMException ex1) {
		log.error("problem with xpath during duplicate key fixing", ex1);
	  }
	  }
	  if (indi.getRin() > 0 && indi.getRin() < firstIndi.getRin()) {
		log.debug("Setting 1st Individual to: " + indi);
		firstIndi = indi;
	  }
	  if (debug) {
		log.error("\n *** " + i + " mem:" + rt.freeMemory() / 1024 + " Kb\n");
	  }
	  if (rt.freeMemory() < 50000) {
		if (debug) {
		  log.error("gc");
		}
		rt.gc();
	  }
	}
	log.info("individuals: "+individuals.size());

	log.debug("------------------------------");
	log.debug("----------------------Families");
	log.debug("------------------------------");
	for (int i = 0; i < fams.size(); i++) {
	  Element element = (Element) fams.get(i);
	  Family fam = new FamilyJDOM(element, doc);
	  String oldKey = fam.getId();
		addFamily(fam);
		// ID may change when family is inserted if there is a duplicate
		String newKey = fam.getId();
		System.out.println("oldkey="+oldKey+" newkey="+newKey);
		if (!newKey.equals(oldKey)) {
		/** @todo change all instances of the old key to new key in import document */
		try {
		  String ref = "[@REF='" + oldKey + "']";
		  List needsFixing = XPath.selectNodes(newDoc, "//FAMC"+ref+" | //FAMS"+ref);
		  log.debug("needsFixing: "+needsFixing);
		  Iterator iter = needsFixing.iterator();
	while (iter.hasNext()) {
	  Element item = (Element)iter.next();
	  log.debug("setting REF from "+item.getAttribute("REF") + " to "+newKey);
	  item.setAttribute("REF", newKey);
	}
		}
		catch (JDOMException ex1) {
		  log.error("problem with xpath during duplicate key fixing", ex1);
		}
		}
	  if (debug) {
		log.error("\n *** " + i + " mem:" + rt.freeMemory() / 1024 + " Kb\n");
	  }
	  if (rt.freeMemory() < 50000) {
		if (debug) {
		  log.error("gc");
		}
		rt.gc();
	  }
	  log.info("families: "+families.size());

	}
//      log.debug("setting individuals");
//      individuals = new NSMutableDictionary(new NSDictionary(indiMap.values().toArray(), indiMap.keySet().toArray()));
//      log.debug("individuals: "+individuals.count()+"("+individuals+")");
//        for (Iterator iterator = tempFams.iterator(); iterator.hasNext();) {
//            Fam fam = (Fam) iterator.next();
//            familyList.add(fam);
//        }
//        for (Iterator iterator = indiMap.values().iterator(); iterator.hasNext();) {
//            Indi indi = (Indi) iterator.next();
//            individualList.add(indi);
//            surnameList.add(indi.getSurname());
//        }
	  log.debug("play setindividual:" + firstIndi);
//            assignIndividualToButton(firstIndi, individualButton);
//            setIndividual(individualButton);
	  setPrimaryIndividual(firstIndi);
//        final List objects = doc.getRootElement().getChildren("OBJE");
	for (int i = 0; i < multimedias.size(); i++) {
	  Element obje = (Element) multimedias.get(i);
	  if (obje != null) {
		addMultimedia(new MultimediaJDOM(obje));
//                log.debug("ref=" + obje.getAttributeValue("REF"));
//                log.debug("form=" + obje.getChildText("FORM"));
//                log.debug("title=" + obje.getChildText("TITL"));
//                log.debug("file=" + obje.getChildText("FILE"));
//                log.debug("blob=" + obje.getChildText("BLOB"));
//                Element blob = obje.getChild("BLOB");
//                StringBuffer buf = new StringBuffer(blob.getText());
//                List conts = blob.getChildren("CONT");
//                for (int j = 0; j < conts.size(); j++) {
//                    Element cont = (Element) conts.get(j);
//                    buf.append(cont.getText());
//                }
//                log.debug("content=" + buf.toString());
//                log.debug("buflen=" + buf.toString().length());
//                byte[] raw = Base64.decode(buf.toString());
//                log.debug("decoded=" + raw);
//                NSImageView nsiv = new NSImageView(new NSRect(0, 0, 100, 100));
//                nsiv.setImage(new NSImage(new NSData(raw)));
//                individualButton.superview().addSubview(nsiv);
//                nsiv.display();
	  }
	}

	log.debug("------------------------------");
	log.debug("-------------------------Notes");
	log.debug("------------------------------");
	for (int i = 0; i < notes.size(); i++) {
	  Element element = (Element) notes.get(i);
	  if (debug) {
		log.debug("element:" + element.getContent());
	  }
	  Note note = new NoteJDOM(element);
	  addNote(note);
	}
	log.debug("notes: " + notes.size());

  }

  /**
   * addNote
   *
   * @param newNote Note
   */
  public void addNote(Note newNote) {
	notes.put(newNote.getId(), newNote);
  }

  /**
   * addMultimedia
   *
   * @param newMultimedia Multimedia
   */
  public void addMultimedia(Multimedia newMultimedia) {
	multimedia.put(newMultimedia.getId(), newMultimedia);
  }

  /**
   * setPrimaryIndividual
   *
   * @param firstIndi Individual
   */
  public void setPrimaryIndividual(Individual firstIndi) {
	primaryIndividual = firstIndi;
  }

  /**
   * createNewIndividual
   *
   * @return Individual
   */
  public Individual createNewIndividual() {
	return new IndividualJDOM(doc);
  }

  public Map getFamilyMap() {
	return families;
  }

  public Map getIndividualsMap() {
	return individuals;
  }
}




/*
   // uses self-parsing code from Lee
   private void playWithXML2(File file) {
   if (file == null) {
   file =  new File(NSBundle.mainBundle().pathForResource("example","ged"));
   }
   long start = System.currentTimeMillis();
   Document doc = XMLTest.parseGedcom(file);
   long end = System.currentTimeMillis();
   log.debug("Time to parse: "+(end - start)/1000+" seconds.");
   //HashMap famMap = new HashMap();
   HashMap noteMap = new HashMap();
   List notes = doc.getRootElement().getChildren("Note");
   for (int i = 0; i < notes.size(); i++) {
   Element element = (Element) notes.get(i);
   log.debug("element:" + element.getContent());
   Note note = new MyNote(element);
//         List concs = element.getChildren();
//         for (int j=0; j < concs.size(); j++) {
//            Element el = (Element) concs.get(j);
//            log.debug(el.getName()+": <"+el.getText()+">");
//            if (!el.getText().equals(el.getTextTrim())) log.debug(el.getName()+" trim: <"+el.getTextTrim()+">");
//            if (!el.getTextTrim().equals(el.getTextNormalize())) log.debug(el.getName()+" normalize: <"+el.getTextNormalize()+">");
//         }
   noteMap.put(note.getId(), note);
   }
   log.debug("noteMap: "+noteMap.size());
   List indis = doc.getRootElement().getChildren("Individual");
   for (int i = 0; i < indis.size(); i++) {
   Element element = (Element) indis.get(i);
   log.debug("element:" + element.getContent());
   log.debug("name:" + element.getChildText("NAME"));
   //log.debug("bday:" + element.getChild("BIRT").getChildText("DATE"));
   Indi indi = new Indi(element);
   Element noteEl = element.getChild("NOTE");
   if (noteEl != null) {
   String idStr = noteEl.getText();
   log.debug("idStr="+idStr);
   if (idStr != null && idStr.startsWith("@")) {
   idStr = idStr.substring(idStr.indexOf("@")+1, idStr.lastIndexOf("@"));
   indi.setNote((Note)noteMap.get(idStr));
   log.debug("added note "+idStr+"("+noteMap.get(idStr)+") to indi "+indi.getFullName());
   } else {
   indi.setNote(new MyNote(noteEl));
   }
   }
   indiMap.put(indi.getId(), indi);
   }
   List fams = doc.getRootElement().getChildren("Family");
   for (int i = 0; i < fams.size(); i++) {
   Element element = (Element) fams.get(i);
   log.debug("element:" + element.getContent());
   log.debug("husb:" + element.getChildText("HUSB"));
   log.debug("wife:" + element.getChildText("WIFE"));
   log.debug("child:" + element.getChildText("CHIL"));
   log.debug("marr:" + element.getChildText("MARR"));
   String idStr = element.getText();
   idStr = idStr.substring(idStr.indexOf("@")+1, idStr.lastIndexOf("@"));
   String husbIdStr = element.getChildText("HUSB");
   String wifeIdStr = element.getChildText("WIFE");
   if (husbIdStr != null) {
   husbIdStr = husbIdStr.substring(husbIdStr.indexOf("@") + 1, husbIdStr.lastIndexOf("@"));
   }
   if (wifeIdStr != null) {
   wifeIdStr = wifeIdStr.substring(wifeIdStr.indexOf("@") + 1, wifeIdStr.lastIndexOf("@"));
   }
   log.debug("husb=" + husbIdStr + " wife=" + wifeIdStr);
   String marrDateText = "";
   String marrPlaceText = "";
   if (element.getChild("MARR") != null) {
   marrDateText = element.getChild("MARR").getChildText("DATE");
   marrPlaceText = element.getChild("MARR").getChildText("PLAC");
   }
   MyEvent marrEvent = new MyEvent(marrDateText, new MyPlace(marrPlaceText));
   Fam fam = new Fam();
   fam.setId(idStr);
   fam.setMarriageEvent(marrEvent);
   Individual father = (Individual) indiMap.get(husbIdStr);
   if (father != null) {
   fam.setFather(father);
   father.setFamilyAsSpouse(fam);
   }
   Individual mother = (Individual) indiMap.get(wifeIdStr);
   if (mother != null) {
   fam.setMother(mother);
   mother.setFamilyAsSpouse(fam);
   }
   List kids = element.getChildren("CHIL");
   for (Iterator iterator = kids.iterator(); iterator.hasNext();) {
   log.debug("child element of family " + fam);
   Element childElement = (Element) iterator.next();
   String childIdText = childElement.getText();
   childIdText = childIdText.substring(childIdText.indexOf("@") + 1, childIdText.lastIndexOf("@"));
   Individual newChild = (Individual) indiMap.get(childIdText);
   if (newChild != null) {
   log.debug("adding child " + newChild + " to family " + fam);
   fam.addChild(newChild);
   newChild.setFamilyAsChild(fam);
   }
   }
   log.debug("fam (" + fam + ") dad=" + fam.getFather() + " mom=" + fam.getMother() + " children=" + fam.getChildren().size());
   tempFams.add(fam);
   Individual indihusb = (Individual) indiMap.get(husbIdStr);
   log.debug("indimap dad=" + indihusb);
   if (indihusb != null) log.debug("indimap dad fam=" + indihusb.getFamilyAsSpouse());
   }
//      log.debug("setting individuals");
//      individuals = new NSMutableDictionary(new NSDictionary(indiMap.values().toArray(), indiMap.keySet().toArray()));
//      log.debug("individuals: "+individuals.count()+"("+individuals+")");
   log.debug("play indimap: " + indiMap.size());// + "(" + indiMap + ")");
   if (individualButton != null) {
   log.debug("play setindividual");
   assignIndividualToButton((Individual) indiMap.values().toArray()[0], individualButton);
   setIndividual(individualButton);
   }
   }
 */
