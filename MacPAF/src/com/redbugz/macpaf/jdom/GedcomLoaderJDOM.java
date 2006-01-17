package com.redbugz.macpaf.jdom;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.apple.cocoa.application.NSProgressIndicator;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.Note;
import com.redbugz.macpaf.Repository;
import com.redbugz.macpaf.Source;
import com.redbugz.macpaf.Submitter;
import com.redbugz.macpaf.util.XMLTest;

public class GedcomLoaderJDOM {
	private static final Logger log = Logger.getLogger(GedcomLoaderJDOM.class);

	/**
	 * This is the main jdom document that holds all of the data
	 */
	private MacPAFDocumentJDOM _doc;
	NSProgressIndicator _progress;
		
	public GedcomLoaderJDOM(MacPAFDocumentJDOM document, NSProgressIndicator progress) {
		_doc = document;
		_progress = progress;
	}
	
	// uses parser from Kay (gedml)
	public void loadXMLFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException("Cannot parse XML file because file is null.");
		}
		try {
			_progress.setIndeterminate(true);
			_progress.startAnimation(this);
			long start = System.currentTimeMillis();
			//      Document doc = XMLTest.parseGedcom(file);
			Document newDoc = XMLTest.docParsedWithKay(file);
			Element root = newDoc.getRootElement();
			long end = System.currentTimeMillis();
			log.debug("Time to parse: " + (end - start) / 1000D + " seconds.");
			List familyElements = root.getChildren("FAM");
			List individualElements = root.getChildren("INDI");
			List multimediaElements = root.getChildren("OBJE");
			List noteElements = root.getChildren("NOTE");
			List repositoryElements = root.getChildren("REPO");
			List sourceElements = root.getChildren("SOUR");
			List submitterElements = root.getChildren("SUBM");
			// detach the root from the children nodes so they can be imported into the new document
//		root.detach();
			
			long totalElements = familyElements.size() + individualElements.size() + multimediaElements.size() + noteElements.size() + repositoryElements.size() + sourceElements.size() + submitterElements.size()	;
			_progress.setIndeterminate(false);
			_progress.setDoubleValue(0.0D);
			_progress.setMaxValue(totalElements);
			_progress.displayIfNeeded();
			
			log.debug("header: " + new HeaderJDOM(root.getChild("HEAD")));
//		log.debug("submission record: " + new SubmitterJDOM(root.getChild("SUBN"), this));
			log.debug("file has:\n\t" + individualElements.size() + " individuals\n\t" + familyElements.size() + " families\n\t" + noteElements.size() +
					" notes\n\t" + multimediaElements.size() + " multimedia objects\n\t" + sourceElements.size() + " sources\n\t" +
					repositoryElements.size() + " repositories\n\t" + submitterElements.size() + " submitters\n");
			
//		   Executor executor = new ThreadedExecutor();
//		     try {
//		       FutureResult familyList = new FutureResult();
//		       Runnable command = familyList.setter(new Callable() {
//		          public Object call() { return getFamilies(); }
//		       });
//		       executor.execute(command);
//		       drawBorders();             // do other things while executing
//		       drawCaption();
//		       drawImage((Image)(familyList.get())); // use future
//		     }
//		     catch (InterruptedException ex) { return; }
//		     catch (InvocationTargetException ex) { cleanup(); return; }
			   
			
			boolean debug = false;
			Runtime rt = Runtime.getRuntime();
			
			log.debug("------------------------------");
			log.debug("-------------------Individuals");
			log.debug("------------------------------");
			Individual firstIndi = Individual.UNKNOWN;
			for (Iterator iterator = individualElements.iterator(); iterator.hasNext();) {
//			type element = (type) iter.next();
				
//		}
//		for (int i = 0; i < individualElements.size(); i++) {
				Element element = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
				if (debug) {
					log.debug("element:" + element.getContent());
				}
				if (debug) {
					log.debug("name:" + element.getChildText("NAME"));
					//log.debug("bday:" + element.getChild("BIRT").getChildText("DATE"));
				}
				Individual indi = new IndividualJDOM(element, _doc);
				String oldKey = indi.getId();
				_doc.addIndividual(indi);
				incrementAndUpdateProgress();
				String newKey = indi.getId();
				// ID may change when individual is inserted if it is a duplicate
				System.out.println("indi oldkey="+oldKey+" newkey="+newKey);
				if (!newKey.equals(oldKey)) {
					/** @todo change all instances of the old key to new key in import document */
					try {
						String ref = "[@REF='" + oldKey + "']";
						try {
							System.err.println("dumping newDoc:"+newDoc);
							System.err.println("dumping newDoc:"+newDoc.getRootElement());
							new XMLOutputter().output(newDoc, System.out);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
					log.error("\n *** " + " mem:" + rt.freeMemory() / 1024 + " Kb\n");
				}
				if (rt.freeMemory() < 50000) {
					if (debug) {
						log.error("gc");
					}
					rt.gc();
				}
			}
			log.info("individuals: "+_doc.individuals.size());
			
			log.debug("------------------------------");
			log.debug("----------------------Families");
			log.debug("------------------------------");
			for (Iterator iterator = familyElements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
//		}
//		for (int i = 0; i < familyElements.size(); i++) {
//			Element element = (Element) familyElements.get(i);
				Family fam = new FamilyJDOM(element, _doc);
				String oldKey = fam.getId();
				_doc.addFamily(fam);
				incrementAndUpdateProgress();
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
					log.error("\n *** " + " mem:" + rt.freeMemory() / 1024 + " Kb\n");
				}
				if (rt.freeMemory() < 50000) {
					if (debug) {
						log.error("gc");
					}
					rt.gc();
				}
				log.info("families: "+_doc.families.size());			
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
			log.debug("MPDJ.loadXMLFile setindividual:" + firstIndi);
			//            assignIndividualToButton(firstIndi, individualButton);
			//            setIndividual(individualButton);
			_doc.setPrimaryIndividual(firstIndi);
			//        final List objects = doc.getRootElement().getChildren("OBJE");
			
			log.debug("------------------------------");
			log.debug("--------------------Multimedia");
			log.debug("------------------------------");
			for (Iterator iterator = multimediaElements.iterator(); iterator.hasNext();) {
				Element obje = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
			
//		for (int i = 0; i < multimediaElements.size(); i++) {
//			Element obje = (Element) multimediaElements.get(i);
				if (obje != null) {
					_doc.addMultimedia(new MultimediaJDOM(obje, _doc));
					incrementAndUpdateProgress();
//			    byte[] raw = Base64.decode(buf.toString());
//			    log.debug("decoded=" + raw);
//			    NSImageView nsiv = new NSImageView(new NSRect(0, 0, 100, 100));
//			    nsiv.setImage(new NSImage(new NSData(raw)));
//			    individualButton.superview().addSubview(nsiv);
//			    nsiv.display();
				}
			}
			log.debug("multimedia: " + multimediaElements.size());
			
			log.debug("------------------------------");
			log.debug("-------------------------Notes");
			log.debug("------------------------------");
			for (Iterator iterator = noteElements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
//		for (int i = 0; i < noteElements.size(); i++) {
//			Element element = (Element) noteElements.get(i);
				if (debug) {
					log.debug("element:" + element.getContent());
				}
				Note note = new NoteJDOM(element, _doc);
				_doc.addNote(note);
			}
			log.debug("notes: " + noteElements.size());
			
			log.debug("------------------------------");
			log.debug("-----------------------Sources");
			log.debug("------------------------------");
			for (Iterator iterator = sourceElements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
//		for (int i = 0; i < sourceElements.size(); i++) {
//			Element element = (Element) sourceElements.get(i);
				if (debug) {
					log.debug("element:" + element.getContent());
				}
				Source source = new SourceJDOM(element, _doc);
				_doc.addSource(source);
				incrementAndUpdateProgress();
			}
			log.debug("sources: " + sourceElements.size());
			
			log.debug("------------------------------");
			log.debug("------------------Repositories");
			log.debug("------------------------------");
			for (Iterator iterator = repositoryElements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
//		for (int i = 0; i < repositoryElements.size(); i++) {
//			Element element = (Element) repositoryElements.get(i);
				if (debug) {
					log.debug("element:" + element.getContent());
				}
				Repository repository = new RepositoryJDOM(element, _doc);
				_doc.addRepository(repository);
				incrementAndUpdateProgress();
			}
			log.debug("repositories: " + repositoryElements.size());
			
			log.debug("------------------------------");
			log.debug("--------------------Submitters");
			log.debug("------------------------------");
			for (Iterator iterator = submitterElements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
//		for (int i = 0; i < submitterElements.size(); i++) {
//			Element element = (Element) submitterElements.get(i);
				if (debug) {
					log.debug("element:" + element.getContent());
				}
				Submitter submitter = new SubmitterJDOM(element, _doc);
				_doc.addSubmitter(submitter);
				incrementAndUpdateProgress();
			}	
			log.debug("submitters: " + submitterElements.size());
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementAndUpdateProgress() {
		_progress.incrementBy(1);
		log.info("progress: "+_progress.doubleValue() + " out of "+_progress.maxValue());
		_progress.displayIfNeeded();
	}
}