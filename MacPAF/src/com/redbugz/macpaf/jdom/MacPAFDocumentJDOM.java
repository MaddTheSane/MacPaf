package com.redbugz.macpaf.jdom;

import java.io.*;
import java.util.*;

import org.apache.log4j.*;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.*;
import org.jdom.xpath.*;

import com.apple.cocoa.foundation.NSData;
import com.redbugz.macpaf.*;
import com.redbugz.macpaf.util.*;
import com.redbugz.macpaf.validation.*;

public class MacPAFDocumentJDOM extends Observable implements Observer {
	private static final Logger log = Logger.getLogger(MacPAFDocumentJDOM.class);

	/**
	 * This is the main jdom document that holds all of the data
	 */
	private Document doc; // = makeInitialGedcomDoc();
	
	protected Map families = new HashMap();
	protected Map individuals = new HashMap();
	protected Map multimedia = new HashMap();
	protected Map notes = new HashMap();
	protected Map sources = new HashMap();
	protected Map repositories = new HashMap();
	protected Map submitters = new HashMap();
	
	private int nextFamilyId = 0;
	private int nextIndividualId = 0;
	private int nextMultimediaId = 0;
	private int nextNoteId = 0;
	private int nextSourceId = 0;
	private int nextRepositoryId = 0;
	private int nextSubmitterId = 0;

	/**
	 * This is the main individual to whom apply all actions
	 */
	private Individual primaryIndividual = new Individual.UnknownIndividual();

	private boolean suppressUpdates;
	
	public MacPAFDocumentJDOM() {
		Element root = new Element("GED");
		doc = new Document(root);
//		Element newSubmitterElement = new Element(SubmitterJDOM.SUBMITTER);
//		newSubmitterElement.addContent(new Element(Submitter.NAME).setText(""));
		SubmitterJDOM newSubmitter = new SubmitterJDOM(this);//newSubmitterElement, this);
//		root.addContent(newSubmitter.getElement());
//		submitters.put(newSubmitter.getId(), newSubmitter);
		addDefaultSubmitter(newSubmitter);
		root.addContent(0, new HeaderJDOM(newSubmitter).getElement());
	}
	
	public void addFamily(Family newFamily) {
		log.debug("MacPAFDocumentJDOM.addFamily(newFamily)");
		// todo: figure out how to handle RIN vs ID, blank vs dups
//		if (newFamily.getRin())
		if (StringUtils.isEmpty(newFamily.getId())) {
			newFamily.setId("F"+getNextAvailableFamilyId());
			log.info("Family added with blank Id. Assigning Id: "+newFamily.getId());
//			throw new IllegalArgumentException("Cannot add a new family with a blank ID");
		}
		while (families.containsKey(newFamily.getId())) {
			String oldKey = newFamily.getId();
			newFamily.setId("F"+getNextAvailableFamilyId());
			log.warn("Attempt to insert family with duplicate key. Reassigning key from "+oldKey+" to "+newFamily.getId());
		}
		families.put(newFamily.getId(), newFamily);
		log.debug("added fam with key: " + newFamily.getId() + " fam marr date: " + newFamily.getMarriageEvent().getDateString());
		if (newFamily instanceof FamilyJDOM) {
			log.debug("adding fam to doc: " + newFamily);
			doc.getRootElement().addContent((Content) ((FamilyJDOM) newFamily).getElement());
		}
		update(this, newFamily);
	}
	
	public void addIndividual(Individual newIndividual) {
		log.debug("MacPAFDocumentJDOM.addIndividual():"+newIndividual);
		if (newIndividual instanceof Individual.UnknownIndividual) {
			throw new IllegalArgumentException("Expected IndividualJDOM, received: "+newIndividual.getClass().getName());
		}
		if (StringUtils.isEmpty(newIndividual.getId())) {
			newIndividual.setId("I"+getNextAvailableIndividualId());
			log.info("Individual added with blank Id. Assigning Id: "+newIndividual.getId());
//			throw new IllegalArgumentException("Cannot add a new individual with a blank ID");
		}
		while (individuals.containsKey(newIndividual.getId())) {
			String oldId = newIndividual.getId();
			newIndividual.setId("I"+getNextAvailableIndividualId());
			log.warn("Attempt to insert individual with duplicate key, reassigning key from "+oldId+" to "+newIndividual.getId());
		}
		individuals.put(newIndividual.getId(), newIndividual);
		log.debug("added individual with key: " + newIndividual.getId() + " name: " + newIndividual.getFullName());
//		log.debug("adding individual to doc: "+newIndividual);
		doc.getRootElement().addContent((Content)((IndividualJDOM)newIndividual).getElement());
		update(this, newIndividual);
	}
	
	/**
	 * @param submitter
	 */
	public void addSubmitter(Submitter newSubmitter) {
		log.debug("MacPAFDocumentJDOM.addSubmitter():"+newSubmitter);
		// for now, don't add default submitters, they should already exist
		if (newSubmitter instanceof SubmitterJDOM) {
			SubmitterJDOM submitterJDOM = (SubmitterJDOM) newSubmitter;
			if (submitters.size() == 1 && submitterJDOM.isDefault()) {
				log.debug("Not adding submitter because is default submitter");
				
				return;
			}
 		}
		if (StringUtils.isEmpty(newSubmitter.getId())) {
			newSubmitter.setId("T"+getNextAvailableSubmitterId());
			log.info("Submitter added with blank Id. Assigning Id: "+newSubmitter.getId());
//			throw new IllegalArgumentException("Cannot add a new multimedia with a blank ID");
		}
		if (submitters.containsKey(newSubmitter.getId())) {
			log.warn("new submitter has same Id as existing submitter ("+newSubmitter.getId()+"). Re-assigning new Id...");
			newSubmitter.setId("T"+getNextAvailableSubmitterId());
		}
		submitters.put(newSubmitter.getId(), newSubmitter);
		log.debug("added submitter with key: " + newSubmitter.getId() + " name: " + newSubmitter.getName());
		if (newSubmitter instanceof SubmitterJDOM) {
			log.debug("adding submitter to doc: "+newSubmitter);
			// replace default submitter if it is the only one
			if (submitters.size() == 1) {
				Submitter submitter = (Submitter) submitters.values().toArray()[0];
				// check to make sure it is the default submitter with default 
				if (submitter instanceof SubmitterJDOM) {
					SubmitterJDOM submitterJDOM = (SubmitterJDOM) submitter;
					if (submitterJDOM.isDefault()) {
						log.debug("Removing default submitter");
						removeSubmitter(submitterJDOM);
						log.debug("root children:"+doc.getRootElement().getChildren());
						log.debug("subm to remove:"+submitterJDOM.getElement()+":parent:"+submitterJDOM.getElement().getParent()+":"+submitterJDOM.getElement().getParentElement());
						boolean removed = doc.getRootElement().removeContent(submitterJDOM.getElement());
						if (!removed) {
							log.error("Failed to remove default submitter");
						}
					}
				}
			}
			doc.getRootElement().addContent((Content)((SubmitterJDOM)newSubmitter).getElement());
		}
		update(this, newSubmitter);
	}

	/**
	 * @param submitter
	 */
	private void addDefaultSubmitter(Submitter newSubmitter) {
		log.debug("MacPAFDocumentJDOM.addDefaultSubmitter():"+newSubmitter);
		if (StringUtils.isEmpty(newSubmitter.getId())) {
			newSubmitter.setId("T"+getNextAvailableSubmitterId());
			log.info("Submitter added with blank Id. Assigning Id: "+newSubmitter.getId());
		}
		if (submitters.containsKey(newSubmitter.getId())) {
			log.warn("new submitter has same Id as existing submitter ("+newSubmitter.getId()+"). Re-assigning new Id...");
			newSubmitter.setId("T"+getNextAvailableSubmitterId());
		}
		submitters.put(newSubmitter.getId(), newSubmitter);
		log.debug("added default submitter with key: " + newSubmitter.getId() + " name: " + newSubmitter.getName());
		if (newSubmitter instanceof SubmitterJDOM) {
			log.debug("adding default submitter to doc: "+newSubmitter);
					SubmitterJDOM submitterJDOM = (SubmitterJDOM) newSubmitter;
			doc.getRootElement().addContent((Content)submitterJDOM.getElement());
		}
		update(this, newSubmitter);
	}

	public void removeSubmitter(Submitter submitterToRemove) {
		log.warn("removeSubmitter() instructed to remove submitter from document: "+submitterToRemove.getName());
		SubmitterJDOM jdomSubmitter = getSubmitter(submitterToRemove.getId());
		jdomSubmitter.getElement().detach();
		submitters.remove(submitterToRemove.getId());
		// invalidate links to this submitter
		try {
			List references = XPath.selectNodes(doc, "//*[@REF=\""+submitterToRemove.getId()+"\"]");
			log.debug("while removing submitter("+submitterToRemove.getId()+"), found these references:"+references);
			for (Iterator iter = references.iterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				element.detach();
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update(this, submitterToRemove);
	}

	/**
	 * @param repository
	 */
	public void addRepository(Repository newRepository) {
		log.debug("MacPAFDocumentJDOM.addRepository():"+newRepository);
		if (StringUtils.isEmpty(newRepository.getId())) {
			newRepository.setId("R"+getNextAvailableRepositoryId());
			log.info("Repository added with blank Id. Assigning Id: "+newRepository.getId());
//		throw new IllegalArgumentException("Cannot add a new repository with a blank ID");
		}
		repositories.put(newRepository.getId(), newRepository);
		log.debug("added repository with key: " + newRepository.getId() + " name: " + newRepository.getName());
		if (newRepository instanceof RepositoryJDOM) {
			log.debug("adding repository to doc: "+newRepository);
			doc.getRootElement().addContent((Content)((RepositoryJDOM)newRepository).getElement());
		}
		update(this, newRepository);
	}
	
	/**
	 * @param source
	 */
	public void addSource(Source newSource) {
		log.debug("MacPAFDocumentJDOM.addSource():"+newSource);
		if (StringUtils.isEmpty(newSource.getId())) {
			newSource.setId("S"+getNextAvailableSourceId());
			log.info("Source added with blank Id. Assigning Id: "+newSource.getId());
//		throw new IllegalArgumentException("Cannot add a new source with a blank ID");
		}
		sources.put(newSource.getId(), newSource);
		log.debug("added source with key: " + newSource.getId() + " title: " + newSource.getTitle());
		if (newSource instanceof SourceJDOM) {
			log.debug("adding source to doc: "+newSource);
			doc.getRootElement().addContent((Content)((SourceJDOM)newSource).getElement());
		}
		update(this, newSource);
	}
	
	/**
	 * addNote
	 *
	 * @param newNote Note
	 */
	public void addNote(Note newNote) {
		log.debug("MacPAFDocumentJDOM.addNote():"+newNote);
		if (StringUtils.isEmpty(newNote.getId())) {
			newNote.setId("N"+getNextAvailableNoteId());
			log.info("Note added with blank Id. Assigning Id: "+newNote.getId());
//		throw new IllegalArgumentException("Cannot add a new note with a blank ID");
		}
		notes.put(newNote.getId(), newNote);
		log.debug("added note with key: " + newNote.getId() + " text: " + newNote.getText());
		if (newNote instanceof NoteJDOM) {
			log.debug("adding note to doc: "+newNote);
			doc.getRootElement().addContent((Content)((NoteJDOM)newNote).getElement());
		}
		update(this, newNote);
	}
	
	/**
	 * addMultimedia
	 *
	 * @param newMultimedia Multimedia
	 */
	public void addMultimedia(Multimedia newMultimedia) {
		log.debug("MacPAFDocumentJDOM.addMultimedia():"+newMultimedia);
		if (StringUtils.isEmpty(newMultimedia.getId())) {
			newMultimedia.setId("M"+getNextAvailableMultimediaId());
			log.info("Multimedia added with blank Id. Assigning Id: "+newMultimedia.getId());
//		throw new IllegalArgumentException("Cannot add a new multimedia with a blank ID");
		}
		multimedia.put(newMultimedia.getId(), newMultimedia);
		log.debug("added multimedia with key: " + newMultimedia.getId() + " title: " + newMultimedia.getTitle());
		if (newMultimedia instanceof MultimediaJDOM) {
			log.debug("adding multimedia to doc: "+newMultimedia);
			doc.getRootElement().addContent((Content)((MultimediaJDOM)newMultimedia).getElement());
		}
		update(this, newMultimedia);
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
	
//	public void loadXMLFile(File file) {
//		if (file == null) {
//			throw new IllegalArgumentException("Cannot parse XML file because file is null.");
//		}
//		try {
//			loadXMLData(new NSData(file));
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
	
	// uses parser from Kay (gedml)
	public void loadXMLData_USEGEDCOMLOADERINSTEAD(NSData data) {
		try {
			long start = System.currentTimeMillis();
			SAXBuilder builder = new SAXBuilder("gedml.GedcomParser");
			Document doc = null;
			try {
			  doc = builder.build(new ByteArrayInputStream(data.bytes(0, data.length())));
			}
			catch (JDOMException e2) {
			  log.error("Exception: ", e2);
			  throw new IllegalArgumentException("Cannot parse GEDCOM data "+data+". Cause:"+e2.getLocalizedMessage());
			}
			catch (IOException e1) {
			  log.error("Exception: ", e1); //To change body of catch statement use Options | File Templates.
			  throw new IllegalArgumentException("Cannot parse GEDCOM data "+data+". Cause:"+e1.getLocalizedMessage());
			}
//			if (XMLTest.debugging) {
//			  log.debug("^*^*^* Doc parsed with gedml:");
//			  XMLTest.outputDocToConsole(doc);
//			}
			//      Document doc = XMLTest.parseGedcom(file);
			Document newDoc = doc;
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
			root.detach();
			
			try {
				HeaderJDOM header = new HeaderJDOM(root.getChild("HEAD"), this);
				log.debug("header: " + header);
				log.debug("Header charset:"+header.getCharacterSet());
				log.debug("Header charversion:"+header.getCharacterVersionNumber());
				log.debug("Header copyright:"+header.getCopyright());
				log.debug("Header destination:"+header.getDestination());
				log.debug("Header filename:"+header.getFileName());
				log.debug("Header gedcomform:"+header.getGedcomForm());
				log.debug("Header gedcomversion:"+header.getGedcomVersion());
				log.debug("Header language:"+header.getLanguage());
				log.debug("Header placeformat:"+header.getPlaceFormat());
				log.debug("Header sourcecorp:"+header.getSourceCorporation());
				log.debug("Header sourcecorpaddr:"+header.getSourceCorporationAddress());
				log.debug("Header sourcedata:"+header.getSourceData());
				log.debug("Header sourcedatacopyright:"+header.getSourceDataCopyright());
				log.debug("Header sourceid:"+header.getSourceId());
				log.debug("Header sourcename:"+header.getSourceName());
				log.debug("Header sourceversion:"+header.getSourceVersion());
				log.debug("Header sourcedatadate:"+header.getSourceDataDate());
				log.debug("Header creationdate:"+header.getCreationDate());
				log.debug("Header note:"+header.getNote());
				log.debug("Header element:"+header.getElement());

				log.debug("Header submission:"+header.getSubmission());
				log.debug("Header submitter:"+header.getSubmitter());
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.debug("file has:\n\t" + individualElements.size() + " individuals\n\t" + familyElements.size() + " families\n\t" + noteElements.size() +
					" notes\n\t" + multimediaElements.size() + " multimedia objects\n\t" + sourceElements.size() + " sources\n\t" +
					repositoryElements.size() + " repositories\n\t" + submitterElements.size() + " submitters\n");
			
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
				Individual indi = new IndividualJDOM(element, this);
				String oldKey = indi.getId();
				addIndividual(indi);
				String newKey = indi.getId();
				// ID may change when individual is inserted if it is a duplicate
				log.debug("indi oldkey="+oldKey+" newkey="+newKey);
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
					log.error("\n *** " + " mem:" + rt.freeMemory() / 1024 + " Kb\n");
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
			for (Iterator iterator = familyElements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				// this removes this element from it's parent so it can be inserted into the new doc
				iterator.remove();
//		}
//		for (int i = 0; i < familyElements.size(); i++) {
//			Element element = (Element) familyElements.get(i);
				Family fam = new FamilyJDOM(element, this);
				String oldKey = fam.getId();
				addFamily(fam);
				// ID may change when family is inserted if there is a duplicate
				String newKey = fam.getId();
				log.debug("oldkey="+oldKey+" newkey="+newKey);
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
			log.debug("MPDJ.loadXMLFile setindividual:" + firstIndi);
			//            assignIndividualToButton(firstIndi, individualButton);
			//            setIndividual(individualButton);
			setPrimaryIndividual(firstIndi);
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
					addMultimedia(new MultimediaJDOM(obje, this));
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
				Note note = new NoteJDOM(element, this);
				addNote(note);
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
				Source source = new SourceJDOM(element, this);
				addSource(source);
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
				Repository repository = new RepositoryJDOM(element, this);
				addRepository(repository);
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
				Submitter submitter = new SubmitterJDOM(element, this);
				addSubmitter(submitter);
			}	
			log.debug("submitters: " + submitterElements.size());
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * setPrimaryIndividual
	 *
	 * @param newPrimaryIndividual Individual
	 */
	public void setPrimaryIndividual(Individual newPrimaryIndividual) {
		primaryIndividual = newPrimaryIndividual;
	}
	
	/**
	 * createNewIndividual
	 *
	 * @return Individual
	 */
	public Individual createAndInsertNewIndividual() {
		Element newIndividualElement = new Element(IndividualJDOM.INDIVIDUAL);
		IndividualJDOM newIndividual = new IndividualJDOM(newIndividualElement, this);
		addIndividual(newIndividual);
		return newIndividual;
	}
	
	/**
	 * createNewFamily
	 *
	 * @return Family
	 */
	public Family createAndInsertNewFamily() {
		Element newFamilyElement = new Element(FamilyJDOM.FAMILY);
		FamilyJDOM newFamily = new FamilyJDOM(newFamilyElement, this);
		addFamily(newFamily);
		return newFamily;
	}
	
	public void removeIndividual(Individual individualToRemove) {
		log.warn("removeIndividual() instructed to remove individual from document: "+individualToRemove.getFullName());
		IndividualJDOM jdomIndividual = getIndividual(individualToRemove.getId());
		jdomIndividual.getElement().detach();
		individuals.remove(individualToRemove.getId());
		if (primaryIndividual.getId().equals(individualToRemove.getId())) {
			chooseNewPrimaryIndividual();
		}
		// invalidate links to this person
		// the only links that should exist are in family records
		try {
			List references = XPath.selectNodes(doc, "//*[@REF=\""+individualToRemove.getId()+"\"]");
			log.debug("while removing individual("+individualToRemove.getId()+"), found these references:"+references);
			for (Iterator iter = references.iterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				element.detach();
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update(this, individualToRemove);
	}
	
	public void chooseNewPrimaryIndividual() {
		// TODO probably should revert to last person in the history if exists, or other heuristic
		primaryIndividual = Individual.UNKNOWN;
		Collection indiList = individuals.values();
		if (indiList.size() > 0) {
			primaryIndividual = (Individual) indiList.toArray()[0];			
		}
	}

	public void removeFamily (Family familyToRemove) {
		log.warn("removeFamily() instructed to remove family from document: "+familyToRemove.toString());
		FamilyJDOM jdomFamily = getFamily(familyToRemove.getId());
		jdomFamily.getElement().detach();
		families.remove(familyToRemove.getId());
		// invalidate links to this person
		// the only links that should exist are in family records
		try {
			List references = XPath.selectNodes(doc, "//*[@REF=\""+familyToRemove.getId()+"\"]");
			log.debug("while removing family("+familyToRemove.getId()+"), found these references:"+references);
			for (Iterator iter = references.iterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				element.detach();
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update(this, familyToRemove);
	}
	
	public Map getFamiliesMap() {
		return families;
	}
	
	public Map getIndividualsMap() {
		return individuals;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		log.debug("MacPAFDocumentJDOM.update() observable="+o+", object="+arg);
		if (!suppressUpdates) {
			setChanged();
			notifyObservers();
			clearChanged();
		}
	}
	
//	private void setUpdated() {
//		setChanged();
//		notifyObservers();
//		clearChanged();		
//	}

	/**
	 * @param id
	 * @return
	 */
	public FamilyJDOM getFamily(String id) {
		FamilyJDOM family = (FamilyJDOM) families.get(id);
		if (family == null) {
			throw new UnknownGedcomLinkException("Cannot find family with Id="+id+". There are "+families.size()+" families in this file.");
//			throw new IllegalStateException("No family with that ID in the document.");
//			family = Family.UNKNOWN_FAMILY;
		}
		return family;
	}

	/**
	 * @param id
	 * @return
	 */
	public IndividualJDOM getIndividual(String id) {
		IndividualJDOM individual = (IndividualJDOM) individuals.get(id);
		if (individual == null) {
			throw new UnknownGedcomLinkException("Cannot find individual with Id="+id+". There are "+individuals.size()+" individuals in this file.");
//			individual = Individual.UNKNOWN;
		}
		return individual;
	}

	/**
	 * @param id
	 * @return
	 */
	public MultimediaJDOM getMultimedia(String id) {
		MultimediaJDOM media = (MultimediaJDOM) multimedia.get(id);
		if (media == null) {
			throw new UnknownGedcomLinkException("Cannot find multimedia with Id="+id+". There are "+multimedia.size()+" multimedia in this file.");
//			media = Multimedia.UNKNOWN_MULTIMEDIA;
		}
		return media;
	}

	/**
	 * @param id
	 * @return
	 */
	public NoteJDOM getNote(String id) {
		NoteJDOM note = (NoteJDOM) notes.get(id);
		if (note == null) {
//			note = Note.UNKNOWN_MULTIMEDIA;
			throw new UnknownGedcomLinkException("Cannot find note with Id="+id+". There are "+notes.size()+" notes in this file.");
		}
		return note;
	}

	/**
	 * @param id
	 * @return
	 */
	public SourceJDOM getSource(String id) {
		SourceJDOM source = (SourceJDOM) sources.get(id);
		if (source == null) {
			throw new UnknownGedcomLinkException("Cannot find source with Id="+id+". There are "+sources.size()+" sources in this file.");
//			source = Source.UNKNOWN_MULTIMEDIA;
		}
		return source;
	}

	/**
	 * @param id
	 * @return
	 */
	public RepositoryJDOM getRepository(String id) {
		RepositoryJDOM repository = (RepositoryJDOM) repositories.get(id);
		if (repository == null) {
			throw new UnknownGedcomLinkException("Cannot find repository with Id="+id+". There are "+repositories.size()+" repositories in this file.");
//			repository = Repository.UNKNOWN_MULTIMEDIA;
		}
		return repository;
	}

	/**
	 * @param id
	 * @return
	 */
	public SubmitterJDOM getSubmitter(String id) {
		SubmitterJDOM submitter = (SubmitterJDOM) submitters.get(id);
		if (submitter == null) {
//			submitter = Submitter.UNKNOWN_MULTIMEDIA;
			throw new UnknownGedcomLinkException("Cannot find submitter with Id="+id+". There are "+submitters.size()+" submitters in this file.");
		}
		return submitter;
	}
	/**
	 * @return Returns the nextFamilyId.
	 */
	public int getNextAvailableFamilyId() {
		return ++nextFamilyId;
	}
	/**
	 * @return Returns the nextIndividualId.
	 */
	public int getNextAvailableIndividualId() {
		return ++nextIndividualId;
	}
	/**
	 * @return Returns the nextMultimediaId.
	 */
	public int getNextAvailableMultimediaId() {
		return ++nextMultimediaId;
	}
	/**
	 * @return Returns the nextNoteId.
	 */
	public int getNextAvailableNoteId() {
		return ++nextNoteId;
	}
	/**
	 * @return Returns the nextRepositoryId.
	 */
	public int getNextAvailableRepositoryId() {
		return ++nextRepositoryId;
	}
	/**
	 * @return Returns the nextSourcesId.
	 */
	public int getNextAvailableSourceId() {
		return ++nextSourceId;
	}
	/**
	 * @return Returns the nextSubmitterId.
	 */
	public int getNextAvailableSubmitterId() {
		return ++nextSubmitterId;
	}

	/**
	 * @return
	 */
	public Individual getPrimaryIndividual() {
		return primaryIndividual;
	}

	/**
	 * @return
	 */
	public Note createAndInsertNewNote() {
		Element newNoteElement = new Element(NoteJDOM.NOTE);
		NoteJDOM newNote = new NoteJDOM(newNoteElement, this);
		addNote(newNote);
		return newNote;
	}

	/**
	 * @return
	 */
	public Submitter createAndInsertNewSubmitter() {
		Element newSubmitterElement = new Element(SubmitterJDOM.SUBMITTER);
		newSubmitterElement.addContent(new Element(Submitter.NAME).setText(""));
		SubmitterJDOM newSubmitter = new SubmitterJDOM(this);
		addSubmitter(newSubmitter);
		return newSubmitter;
	}

	/**
	 * @return
	 */
	public Multimedia createAndInsertNewMultimedia() {
		Element newMultimediaElement = new Element(MultimediaJDOM.MULTIMEDIA);
		MultimediaJDOM newMultimedia = new MultimediaJDOM(newMultimediaElement, this);
		addMultimedia(newMultimedia);
		return newMultimedia;
	}

	/**
	 * @return
	 */
	public Source createAndInsertNewSource() {
		Element newSourceElement = new Element(SourceJDOM.SOURCE);
		SourceJDOM newSource = new SourceJDOM(newSourceElement, this);
		addSource(newSource);
		return newSource;
	}

	/**
	 * @return
	 */
	public Repository createAndInsertNewRepository() {
		Element newRepositoryElement = new Element(RepositoryJDOM.REPOSITORY);
		RepositoryJDOM newRepository = new RepositoryJDOM(newRepositoryElement, this);
		addRepository(newRepository);
		return newRepository;
	}

	public void outputToTempleReady(FileOutputStream stream) {
		getHeader().setDestination(Header.DEST_TEMPLE_READY);
		XMLTest.outputWithKay(doc, stream);
	}

	public Submission getSubmission() {
		// TODO decide if this should be a Submission.EmptySubmission or similar
		SubmissionJDOM result = null;
		Element submission = doc.getRootElement().getChild(Submission.SUBMISSION);
		if (submission != null) {
			result = new SubmissionJDOM(submission, this);
		}
		return result;
	}

	public Header getHeader() {
		return new HeaderJDOM(doc.getRootElement().getChild(Header.HEADER), this);
	}

	public void startSuppressUpdates() {
		suppressUpdates = true;
	}

	public void endSuppressUpdates() {
		suppressUpdates = false;
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
