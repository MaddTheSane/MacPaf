package com.redbugz.macpaf.jdom;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.Multimedia;
import com.redbugz.macpaf.Note;
import com.redbugz.macpaf.Repository;
import com.redbugz.macpaf.Source;
import com.redbugz.macpaf.Submitter;
import com.redbugz.macpaf.util.StringUtils;
import com.redbugz.macpaf.util.XMLTest;
import com.redbugz.macpaf.validation.UnknownGedcomLinkException;

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
	
	private int nextFamilyId = 1;
	private int nextIndividualId = 1;
	private int nextMultimediaId = 1;
	private int nextNotesId = 1;
	private int nextSourcesId = 1;
	private int nextRepositoriesId = 1;
	private int nextSubmittersId = 1;

	File gedcomFile = null;
	File xmlFile = null;
	
	/**
	 * This is the main individual to whom apply all actions
	 */
	private Individual primaryIndividual = new Individual.UnknownIndividual();
	
	public MacPAFDocumentJDOM() {
		Element root = new Element("GED");
		doc = new Document(root);
		root.addContent(new HeaderJDOM().getElement());
		root.addContent(new SubmitterJDOM(this).getElement());
	}
	
	public void addFamily(Family newFamily) {
		System.out.println("MacPAFDocumentJDOM.addFamily(newFamily)");
		// todo: figure out how to handle RIN vs ID, blank vs dups
//		if (newFamily.getRin())
		if (StringUtils.isEmpty(newFamily.getId())) {
			newFamily.setId("I"+getNextAvailableFamilyId());
			log.info("Family added with blank Id. Assigning Id: "+newFamily.getId());
//			throw new IllegalArgumentException("Cannot add a new family with a blank ID");
		}
		families.put(newFamily.getId(), newFamily);
		log.debug("added fam with key: " + newFamily.getId() + " fam marr date: " + newFamily.getMarriageEvent().getDateString());
		if (newFamily instanceof FamilyJDOM) {
			log.debug("adding fam to doc: " + newFamily);
			doc.getRootElement().addContent((Content) ((FamilyJDOM) newFamily).getElement().clone());
		}
		setChanged();
	}
	
	public void addIndividual(Individual newIndividual) {
		System.out.println("MacPAFDocumentJDOM.addIndividual():"+newIndividual);
		if (StringUtils.isEmpty(newIndividual.getId())) {
			newIndividual.setId("I"+getNextAvailableIndividualId());
			log.info("Individual added with blank Id. Assigning Id: "+newIndividual.getId());
//			throw new IllegalArgumentException("Cannot add a new individual with a blank ID");
		}
		individuals.put(newIndividual.getId(), newIndividual);
		log.debug("added individual with key: " + newIndividual.getId() + " name: " + newIndividual.getFullName());
		if (newIndividual instanceof IndividualJDOM) {
			log.debug("adding individual to doc: "+newIndividual);
			doc.getRootElement().addContent((Content)((IndividualJDOM)newIndividual).getElement().clone());
		}
	}
	
	/**
	 * @param submitter
	 */
	private void addSubmitter(Submitter newSubmitter) {
		System.out.println("MacPAFDocumentJDOM.addSubmitter():"+newSubmitter);
		if (StringUtils.isEmpty(newSubmitter.getId())) {
			newSubmitter.setId("I"+getNextAvailableSubmitterId());
			log.info("Submitter added with blank Id. Assigning Id: "+newSubmitter.getId());
//			throw new IllegalArgumentException("Cannot add a new multimedia with a blank ID");
		}
		if (submitters.containsKey(newSubmitter.getId())) {
			log.warn("new submitter has same Id as existing submitter ("+newSubmitter.getId()+"). Re-assigning new Id...");
		}
		submitters.put(newSubmitter.getId(), newSubmitter);
		log.debug("added submitter with key: " + newSubmitter.getId() + " name: " + newSubmitter.getName());
		if (newSubmitter instanceof SubmitterJDOM) {
			log.debug("adding submitter to doc: "+newSubmitter);
			doc.getRootElement().addContent((Content)((SubmitterJDOM)newSubmitter).getElement().clone());
		}
	}
	
	/**
	 * @param repository
	 */
	private void addRepository(Repository newRepository) {
		System.out.println("MacPAFDocumentJDOM.addRepository():"+newRepository);
		if (StringUtils.isEmpty(newRepository.getId())) {
			newRepository.setId("I"+getNextAvailableRepositoryId());
			log.info("Repository added with blank Id. Assigning Id: "+newRepository.getId());
//		throw new IllegalArgumentException("Cannot add a new repository with a blank ID");
		}
		repositories.put(newRepository.getId(), newRepository);
		log.debug("added repository with key: " + newRepository.getId() + " name: " + newRepository.getName());
		if (newRepository instanceof RepositoryJDOM) {
			log.debug("adding repository to doc: "+newRepository);
			doc.getRootElement().addContent((Content)((RepositoryJDOM)newRepository).getElement().clone());
		}
	}
	
	/**
	 * @param source
	 */
	private void addSource(Source newSource) {
		System.out.println("MacPAFDocumentJDOM.addSource():"+newSource);
		if (StringUtils.isEmpty(newSource.getId())) {
			newSource.setId("I"+getNextAvailableSourceId());
			log.info("Source added with blank Id. Assigning Id: "+newSource.getId());
//		throw new IllegalArgumentException("Cannot add a new source with a blank ID");
		}
		sources.put(newSource.getId(), newSource);
		log.debug("added source with key: " + newSource.getId() + " title: " + newSource.getTitle());
		if (newSource instanceof SourceJDOM) {
			log.debug("adding source to doc: "+newSource);
			doc.getRootElement().addContent((Content)((SourceJDOM)newSource).getElement().clone());
		}
	}
	
	/**
	 * addNote
	 *
	 * @param newNote Note
	 */
	public void addNote(Note newNote) {
		System.out.println("MacPAFDocumentJDOM.addNote():"+newNote);
		if (StringUtils.isEmpty(newNote.getId())) {
			newNote.setId("I"+getNextAvailableNoteId());
			log.info("Note added with blank Id. Assigning Id: "+newNote.getId());
//		throw new IllegalArgumentException("Cannot add a new note with a blank ID");
		}
		notes.put(newNote.getId(), newNote);
		log.debug("added note with key: " + newNote.getId() + " text: " + newNote.getText());
		if (newNote instanceof NoteJDOM) {
			log.debug("adding note to doc: "+newNote);
			doc.getRootElement().addContent((Content)((NoteJDOM)newNote).getElement().clone());
		}
	}
	
	/**
	 * addMultimedia
	 *
	 * @param newMultimedia Multimedia
	 */
	public void addMultimedia(Multimedia newMultimedia) {
		System.out.println("MacPAFDocumentJDOM.addMultimedia():"+newMultimedia);
		if (StringUtils.isEmpty(newMultimedia.getId())) {
			newMultimedia.setId("I"+getNextAvailableMultimediaId());
			log.info("Multimedia added with blank Id. Assigning Id: "+newMultimedia.getId());
//		throw new IllegalArgumentException("Cannot add a new multimedia with a blank ID");
		}
		multimedia.put(newMultimedia.getId(), newMultimedia);
		log.debug("added multimedia with key: " + newMultimedia.getId() + " title: " + newMultimedia.getTitle());
		if (newMultimedia instanceof MultimediaJDOM) {
			log.debug("adding multimedia to doc: "+newMultimedia);
			doc.getRootElement().addContent((Content)((MultimediaJDOM)newMultimedia).getElement().clone());
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
		List familyElements = root.getChildren("FAM");
		List individualElements = root.getChildren("INDI");
		List multimediaElements = root.getChildren("OBJE");
		List noteElements = root.getChildren("NOTE");
		List repositoryElements = root.getChildren("REPO");
		List sourceElements = root.getChildren("SOUR");
		List submitterElements = root.getChildren("SUBM");
		log.debug("header: " + new HeaderJDOM(root.getChild("HEAD")));
//		log.debug("submission record: " + new SubmitterJDOM(root.getChild("SUBN"), this));
		log.debug("file has:\n\t" + individualElements.size() + " individuals\n\t" + familyElements.size() + " families\n\t" + noteElements.size() +
				" notes\n\t" + multimediaElements.size() + " multimedia objects\n\t" + sourceElements.size() + " sources\n\t" +
				repositoryElements.size() + " repositories\n\t" + submitterElements.size() + " submitters\n");
		
		boolean debug = false;
		Runtime rt = Runtime.getRuntime();
		
		log.debug("------------------------------");
		log.debug("-------------------Individuals");
		log.debug("------------------------------");
		Individual firstIndi = Individual.UNKNOWN;
		for (int i = 0; i < individualElements.size(); i++) {
			Element element = (Element) individualElements.get(i);
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
		for (int i = 0; i < familyElements.size(); i++) {
			Element element = (Element) familyElements.get(i);
			Family fam = new FamilyJDOM(element, this);
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
		log.debug("MPDJ.loadXMLFile setindividual:" + firstIndi);
		//            assignIndividualToButton(firstIndi, individualButton);
		//            setIndividual(individualButton);
		setPrimaryIndividual(firstIndi);
		//        final List objects = doc.getRootElement().getChildren("OBJE");
		
		log.debug("------------------------------");
		log.debug("--------------------Multimedia");
		log.debug("------------------------------");
		
		for (int i = 0; i < multimediaElements.size(); i++) {
			Element obje = (Element) multimediaElements.get(i);
			if (obje != null) {
				addMultimedia(new MultimediaJDOM(obje, null));
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
		for (int i = 0; i < noteElements.size(); i++) {
			Element element = (Element) noteElements.get(i);
			if (debug) {
				log.debug("element:" + element.getContent());
			}
			Note note = new NoteJDOM(element, null);
			addNote(note);
		}
		log.debug("notes: " + noteElements.size());
		
		log.debug("------------------------------");
		log.debug("-----------------------Sources");
		log.debug("------------------------------");
		for (int i = 0; i < sourceElements.size(); i++) {
			Element element = (Element) sourceElements.get(i);
			if (debug) {
				log.debug("element:" + element.getContent());
			}
			Source source = new SourceJDOM(element, null);
			addSource(source);
		}
		log.debug("sources: " + sourceElements.size());
		
		log.debug("------------------------------");
		log.debug("------------------Repositories");
		log.debug("------------------------------");
		for (int i = 0; i < repositoryElements.size(); i++) {
			Element element = (Element) repositoryElements.get(i);
			if (debug) {
				log.debug("element:" + element.getContent());
			}
			Repository repository = new RepositoryJDOM(element, null);
			addRepository(repository);
		}
		log.debug("repositories: " + repositoryElements.size());
		
		log.debug("------------------------------");
		log.debug("--------------------Submitters");
		log.debug("------------------------------");
		for (int i = 0; i < submitterElements.size(); i++) {
			Element element = (Element) submitterElements.get(i);
			if (debug) {
				log.debug("element:" + element.getContent());
			}
			Submitter submitter = new SubmitterJDOM(element, this);
			addSubmitter(submitter);
		}	
		log.debug("submitters: " + submitterElements.size());
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
	public Individual createAndInsertNewIndividual() {
		Element newIndividualElement = new Element(IndividualJDOM.INDIVIDUAL);
		doc.getRootElement().addContent(newIndividualElement);
		return new IndividualJDOM(newIndividualElement, this);
	}
	
	/**
	 * createNewFamily
	 *
	 * @return Family
	 */
	public Family createAndInsertNewFamily() {
		Element newFamilyElement = new Element(FamilyJDOM.FAMILY);
		doc.getRootElement().addContent(newFamilyElement);
		return new FamilyJDOM(newFamilyElement, this);
	}
	
	public Map getFamilyMap() {
		return families;
	}
	
	public Map getIndividualsMap() {
		return individuals;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("MacPAFDocumentJDOM.update() observable="+o+", object="+arg);
		setChanged();
		notifyObservers();
		clearChanged();
	}
	
	private void setUpdated() {
		setChanged();
		notifyObservers();
		clearChanged();		
	}

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
		return nextFamilyId++;
	}
	/**
	 * @return Returns the nextIndividualId.
	 */
	public int getNextAvailableIndividualId() {
		return nextIndividualId++;
	}
	/**
	 * @return Returns the nextMultimediaId.
	 */
	public int getNextAvailableMultimediaId() {
		return nextMultimediaId++;
	}
	/**
	 * @return Returns the nextNoteId.
	 */
	public int getNextAvailableNoteId() {
		return nextNotesId++;
	}
	/**
	 * @return Returns the nextRepositoryId.
	 */
	public int getNextAvailableRepositoryId() {
		return nextRepositoriesId++;
	}
	/**
	 * @return Returns the nextSourcesId.
	 */
	public int getNextAvailableSourceId() {
		return nextSourcesId++;
	}
	/**
	 * @return Returns the nextSubmitterId.
	 */
	public int getNextAvailableSubmitterId() {
		return nextSubmittersId++;
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
