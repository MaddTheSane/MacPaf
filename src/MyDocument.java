//
//  MyDocument.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Dec 22 2002.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.*;
import com.redbugz.macpaf.jdom.*;
import org.jdom.xpath.XPath;
import org.jdom.*;
import java.util.*;

/**
* @todo This document is getting too large. I need to subclass NSDocumentController and move
* some of this functionality over there, as well as split up my MyDocument.nib file into
* several smaller nib files.
*/

public class MyDocument extends NSDocument {
  private static final Category log = Category.getInstance(MyDocument.class.getName());

  {
	log.debug(System.getProperty("user.dir"));
	log.debug(System.getProperty("user.home"));
	log.debug("><><><><><><>><><><<><><><><><><><><><><><><><><> MyDocument initializer");
	initDoc();
  }

  public static final String GEDCOM = "GEDCOM File (.ged)";
  public static final String MACPAF = "MacPAF File";

  private NSFileWrapper gedcomFile = null;

  /**
   * This is the main jdom document that holds all of the data
   */
  private Document doc; // = makeInitialGedcomDoc();

  // These dictionaries hold lookups for all of the root elements of the gedcom file
  protected NSMutableDictionary individualsButtonMap = new NSMutableDictionary();
  protected NSMutableDictionary families = new NSMutableDictionary();
  protected NSMutableDictionary notes = new NSMutableDictionary();
  protected NSMutableDictionary sources = new NSMutableDictionary();
  protected NSMutableDictionary repositories = new NSMutableDictionary();
  protected NSMutableDictionary multimedia = new NSMutableDictionary();

  /**
   * This is the main individual to whom apply all actions
   */
  private Individual primaryIndividual = new Individual.UnknownIndividual();

//  protected Map indiMap; // = new HashMap();
  protected Map famMap; // = new HashMap();
  protected Map noteMap; // = new HashMap();
  protected Map multimediaMap; // = new HashMap();
  protected Map repositoryMap; // = new HashMap();
  protected Map sourceMap; // = new HashMap();
  protected Map submitterMap; // = new HashMap();

  // This was originally to check for the constructor getting called twice
  public boolean firstconstr = true;

  // All of the outlets in the nib
  public NSWindow mainWindow; /* IBOutlet */
  public NSWindow reportsWindow; /* IBOutlet */
  public NSWindow importWindow; /* IBOutlet */
  public NSWindow familyEditWindow; /* IBOutlet */
  public FamilyList familyList; /* IBOutlet */
  public IndividualList individualList; /* IBOutlet */
  public SurnameList surnameList; /* IBOutlet */
  public LocationList locationList; /* IBOutlet */
  public NSButton fatherButton; /* IBOutlet */
  public NSButton individualButton; /* IBOutlet */
  public NSWindow individualEditWindow; /* IBOutlet */
  public NSButton individualFamilyButton; /* IBOutlet */
  public NSButton maternalGrandfatherButton; /* IBOutlet */
  public NSButton maternalGrandmotherButton; /* IBOutlet */
  public NSButton motherButton; /* IBOutlet */
  public NSButton parentFamilyButton; /* IBOutlet */
  public NSButton paternalGrandfatherButton; /* IBOutlet */
  public NSButton paternalGrandmotherButton; /* IBOutlet */
  public NSButton spouseButton; /* IBOutlet */
  public NSWindow noteWindow; /* IBOutlet */
  public NSTextView noteTextView; /* IBOutlet */
  public NSMatrix reportsRadio; /* IBOutlet */
  public NSTableView childrenTable; /* IBOutlet */
  public NSTableView spouseTable; /* IBOutlet */
  public PedigreeView pedigreeView; /* IBOutlet */
  public NSTableView individualListTableView; /* IBOutlet */
  public NSTableView familyListTableView; /* IBOutlet */
  private IndividualDetailController individualDetailController = new IndividualDetailController();
  private NSView printableView;

  /**
   * This the internal name for the gedcom file in the .macpaf file package
   */
  private static final String DEFAULT_GEDCOM_FILENAME = "data.ged";

/**
 * This method was originally started to avoid the bug that in Java-Cocoa applications,
 * constructors get called twice, so if you initialize anything in a constructor, it can get
 * nuked later by another constructor
 */
  private void initDoc() {
	log.error("MyDocument.initDoc()");
	if (doc == null) {
	  log.error("????????????????????????????????????????????????????? doc is null, making new doc");
	  Element root = new Element("GED");
	  doc = new Document(root);
	  root.addContent(new HeaderJDOM().getElement());
	  root.addContent(new SubmitterJDOM().getElement());
//	  indiMap = new HashMap();
	  famMap = new HashMap();
	  noteMap = new HashMap();
	  multimediaMap = new HashMap();
	  repositoryMap = new HashMap();
	  sourceMap = new HashMap();
	  submitterMap = new HashMap();
	  log.debug("familylist: " + familyList);
	  familyList = new FamilyList();
	  individualList = new IndividualList();
	}
  }

  public MyDocument() {
	super();
	log.error("MyDocument.MyDocument()");
	initDoc();
  }

  public MyDocument(String fileName, String fileType) {
	super(fileName, fileType);
	log.error("MyDocument.MyDocument(" + fileName + ", " + fileType + ")");
	try {
	  initDoc();
	}
	catch (Exception e) {
	  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	}
  }

//   public void closeFamilyEditSheet(Object sender) { /* IBAction */
//      NSApplication.sharedApplication().stopModal();
//      NSApplication.sharedApplication().endSheet(familyEditWindow);
//      familyEditWindow.orderOut(this);
//   }

//   public void closeIndividualEditSheet(Object sender) { /* IBAction */
//      NSApplication.sharedApplication().stopModal();
//   }

  public void cancel(Object sender) { /* IBAction */
//      NSApplication.sharedApplication().stopModal();
	NSApplication.sharedApplication().endSheet(reportsWindow);
	reportsWindow.orderOut(sender);
  }

  public void openFamilyEditSheet(Object sender) { /* IBAction */
	( (NSWindowController) familyEditWindow.delegate()).setDocument(this);
	if ( ( (NSButton) sender).tag() == 1) {
	  ( (FamilyEditController) familyEditWindow.delegate()).setFamily(primaryIndividual.getFamilyAsChild());
	}
	NSApplication nsapp = NSApplication.sharedApplication();
	nsapp.beginSheet(familyEditWindow, mainWindow, this, new NSSelector("sheetDidEndShouldClose2", new Class[] {}), null);
	//nsapp.runModalForWindow(familyEditWindow);
	//nsapp.endSheet(familyEditWindow);
	//familyEditWindow.orderOut(this);
  }

  public void sheetDidEndShouldClose2() {
	log.debug("sheetDidEndShouldClose2");
  }

  public void openIndividualEditSheet(Object sender) { /* IBAction */
	( (NSWindowController) individualEditWindow.delegate()).setDocument(this);
	NSApplication nsapp = NSApplication.sharedApplication();
	nsapp.beginSheet(individualEditWindow, mainWindow, null, null, null);
	//nsapp.runModalForWindow(individualEditWindow);
//      nsapp.endSheet(individualEditWindow);
//      individualEditWindow.orderOut(this);
  }

  public void openReportsSheet(Object sender) { /* IBAction */
	( (NSWindowController) reportsWindow.delegate()).setDocument(this);
//      if (!myCustomSheet)
//          [NSBundle loadNibNamed: @"MyCustomSheet" owner: self];

	NSApplication nsapp = NSApplication.sharedApplication();
	log.debug("openReportsSheet mainWindow:" + mainWindow);
//      reportsWindow.makeKeyAndOrderFront(this);
	nsapp.beginSheet(reportsWindow, mainWindow, this, null, null);
	//nsapp.runModalForWindow(reportsWindow);
	//nsapp.endSheet(individualEditWindow);
	//individualEditWindow.orderOut(this);
  }

  public void setPrintableView(Object sender) { /* IBAction */
	log.debug("setPrintableView selected=" + reportsRadio.selectedTag());
	printInfo().setTopMargin(36);
	printInfo().setBottomMargin(36);
	printInfo().setLeftMargin(72);
	printInfo().setRightMargin(36);
	setPrintInfo(printInfo());
	switch (reportsRadio.selectedTag()) {
	  case 0:
		printableView = new PedigreeView(new NSRect(0, 0,
			printInfo().paperSize().width() - printInfo().leftMargin() - printInfo().rightMargin(),
			printInfo().paperSize().height() - printInfo().topMargin() - printInfo().bottomMargin()), primaryIndividual,
										 4);
		break;
	  case 1:
		printableView = new FamilyGroupSheetView(new NSRect(0, 0,
			printInfo().paperSize().width() - printInfo().leftMargin() - printInfo().rightMargin(),
			printInfo().paperSize().height() - printInfo().topMargin() - printInfo().bottomMargin()), primaryIndividual);
		break;
	  default:
		printableView = new PedigreeView(new NSRect(0, 0,
			printInfo().paperSize().width() - printInfo().leftMargin() - printInfo().rightMargin(),
			printInfo().paperSize().height() - printInfo().topMargin() - printInfo().bottomMargin()), primaryIndividual,
										 4);
	}
//      printableView = new PedigreeView(new NSRect(0,0,printInfo().paperSize().width()-printInfo().leftMargin()-printInfo().rightMargin(),printInfo().paperSize().height()-printInfo().topMargin()-printInfo().bottomMargin()), primaryIndividual, 4);
//      printableView = new FamilyGroupSheetView(new NSRect(0,0,printInfo().paperSize().width()-printInfo().leftMargin()-printInfo().rightMargin(),printInfo().paperSize().height()-printInfo().topMargin()-printInfo().bottomMargin()), primaryIndividual);
//      printableView = new IndividualSummaryView(new NSRect(0,0,printInfo().paperSize().width()-printInfo().leftMargin()-printInfo().rightMargin(),printInfo().paperSize().height()-printInfo().topMargin()-printInfo().bottomMargin()), primaryIndividual);
  }

//   public void saveFamily(Object sender) { /* IBAction */
//// save family info
//      closeFamilyEditSheet(sender);
//   }

//   public void saveIndividual(Object sender) { /* IBAction */
//// save individual info
//      closeIndividualEditSheet(sender);
//   }

  public Individual getPrimaryIndividual() {
	return primaryIndividual;
  }

  public void setPrimaryIndividual(Individual newIndividual) {
	primaryIndividual = newIndividual;
	assignIndividualToButton(primaryIndividual, individualButton);
	assignIndividualToButton(primaryIndividual.getPrimarySpouse(), spouseButton);
	assignIndividualToButton(primaryIndividual.getFather(), fatherButton);
	assignIndividualToButton(primaryIndividual.getMother(), motherButton);
	assignIndividualToButton(primaryIndividual.getFather().getFather(), paternalGrandfatherButton);
	assignIndividualToButton(primaryIndividual.getFather().getMother(), paternalGrandmotherButton);
	assignIndividualToButton(primaryIndividual.getMother().getFather(), maternalGrandfatherButton);
	assignIndividualToButton(primaryIndividual.getMother().getMother(), maternalGrandmotherButton);
	noteTextView.setString(primaryIndividual.getNoteText());
//        pedigreeView.setIndividual(primaryIndividual);
	individualDetailController.setIndividual(primaryIndividual);
	spouseTable.reloadData();
	childrenTable.reloadData();
  }

  public void setIndividual(Object sender) { /* IBAction */
	log.debug("setIndividual to " + sender);
	if (sender instanceof NSButton) {
	  try {
		log.debug("individuals=" + individualsButtonMap.objectForKey(sender.toString()));
		Individual newIndividual = (Individual) individualsButtonMap.objectForKey(sender.toString());
		NSButton animateButton = (NSButton) sender;

		NSPoint individualButtonOrigin = individualButton.frame().origin();
		NSPoint animateButtonOrigin = animateButton.frame().origin();
		log.debug("animating from " + animateButtonOrigin + " to " + individualButtonOrigin);
		if (!animateButtonOrigin.equals(individualButtonOrigin)) {
		  float stepx = (individualButtonOrigin.x() - animateButtonOrigin.x()) / 10;
		  float stepy = (individualButtonOrigin.y() - animateButtonOrigin.y()) / 10;
		  NSImage image = new NSImage();
		  log.debug("animatebutton.bounds:" + animateButton.bounds());
		  image.addRepresentation(new NSBitmapImageRep(animateButton.bounds()));
		  NSImageView view = new NSImageView(animateButton.frame());
		  view.setImage(image);
		  animateButton.superview().addSubview(view);
		  for (int steps = 0; steps < 10; steps++) {
			animateButtonOrigin = new NSPoint(animateButtonOrigin.x() + stepx, animateButtonOrigin.y() + stepy);
			view.setFrameOrigin(animateButtonOrigin);
			view.display();
		  }
		  view.removeFromSuperview();
		  animateButton.superview().setNeedsDisplay(true);
		}
		setPrimaryIndividual(newIndividual);
	  }
	  catch (Exception e) {
		log.error("Exception: ", e);
	  }
	}
	else if (sender instanceof NSTableView) {
	  NSTableView tv = (NSTableView) sender;
	  NSView superview = individualButton.superview();
	  log.debug("individualList=" + individualsButtonMap.objectForKey("child" + tv.selectedRow()));
	  NSPoint individualButtonOrigin = individualButton.frame().origin();
	  Individual newIndividual = (Individual) individualsButtonMap.objectForKey("child" + tv.selectedRow());
	  if (tv.tag() == 2) {
		newIndividual = (Individual) individualsButtonMap.objectForKey("spouse" + tv.selectedRow());
		individualButtonOrigin = spouseButton.frame().origin();
	  }
	  NSRect rowRect = tv.convertRectToView(tv.rectOfRow(tv.selectedRow()), superview);
	  NSPoint tvOrigin = rowRect.origin();
	  log.debug("animating from " + tvOrigin + " to " + individualButtonOrigin);
	  float stepx = (individualButtonOrigin.x() - tvOrigin.x()) / 10;
	  float stepy = (individualButtonOrigin.y() - tvOrigin.y()) / 10;
	  NSImage image = new NSImage();
	  log.debug("rowrect:" + rowRect);
	  image.addRepresentation(new NSBitmapImageRep(rowRect));
	  NSImageView view = new NSImageView(rowRect);
	  view.setImage(image);
	  superview.addSubview(view);
	  for (int steps = 0; steps < 10; steps++) {
		tvOrigin = new NSPoint(tvOrigin.x() + stepx, tvOrigin.y() + stepy);
		view.setFrameOrigin(tvOrigin);
		view.display();
	  }
	  view.removeFromSuperview();
	  superview.setNeedsDisplay(true);
	  if (tv.tag() == 2) {
		assignIndividualToButton(newIndividual, spouseButton);
	  }
	  else {
		setPrimaryIndividual(newIndividual);
	  }
	}
	else if (sender instanceof IndividualList) {
	  IndividualList iList = (IndividualList) sender;
	  setPrimaryIndividual(iList.getSelectedIndividual());
	}
	else if (sender instanceof FamilyList) {
	  FamilyList iList = (FamilyList) sender;
	  setPrimaryIndividual(iList.getSelectedFamily().getFather());
	}

  }

  public String windowNibName() {
	return "MyDocument";
  }

  public void windowControllerDidLoadNib(NSWindowController aController) {
	try {
	  super.windowControllerDidLoadNib(aController);

	  // Add any code here that need to be executed once the windowController has loaded the document's window.
	  mainWindow = aController.window();
	  mainWindow.setToolbar(new NSToolbar("main"));
	  log.debug("mainWindow:" + mainWindow);
	  log.debug("indivEditWindow:" + individualEditWindow);
	  //myAction(this);
	  NSAttributedString text = individualButton.attributedTitle();
	  log.debug("indivButton attrTitle: " + text);
	  log.debug("indivButton attr at index 5:" + text.attributesAtIndex(5, null));
	  log.debug("individualList: " + individualsButtonMap.count() + "(" + individualsButtonMap + ")");
	  log.debug("individualList: " + individualList.size()); // + "(" + indiMap + ")");
	  if (individualList.size() > 0) {
		log.debug("indimap");
		assignIndividualToButton( (Individual) individualList.entrySet().toArray()[0], individualButton);
		setIndividual(individualButton);
	  }
	  else {
		setPrimaryIndividual(Individual.UNKNOWN);
	  }
	  // add famMap to FamilyList
	  for (Iterator iter = famMap.values().iterator(); iter.hasNext(); ) {
		Family fam = (Family) iter.next();
		familyList.add(fam);
	  }
//         NSImage testImage = new NSImage("/Users/logan/Pictures/iPhoto Library/Albums/Proposal/GQ.jpg", false);
//         testImage.setSize(new NSSize(50f, 50f));
//         testImage.setScalesWhenResized(true);
	  log.debug("indivButton cell type: " + individualButton.cell().type());
//         individualButton.setImage(testImage);
//         save();
	  // save newly opened document as preference
	  log.debug("setting lastOpenedDocument to: " + fileName());
	  NSUserDefaults.standardUserDefaults().setObjectForKey(fileName(), "com.redbugz.macpaf.lastOpenedDocument");
	}
	catch (Exception e) {
	  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	}
  }

  public boolean readFromFile(String filename, String aType) {
	log.debug("readfromfile type:" + aType + ":" + filename);
	log.debug("********* readFromFile");
	try {
	  if (GEDCOM.equals(aType)) {
		// import GEDCOM into this file
		boolean success = super.readFromFile(filename, aType);
		if (success) {
		  importGEDCOM(new File(filename));
		  setFileType(MACPAF);
		  setFileName(null); //"ThisIsATest.macpaf");
//						save();
		}
		return success;
	  }
	  if (!MACPAF.equals(aType)) {
		try {
//			            docFile = new File(filename);
		  loadXMLFile(new File(filename));
		}
		catch (Throwable e) {
		  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
		  return false;
		}
		return true; //super.readFromFile(filename, aType);
	  }
	  else {
		return super.readFromFile(filename, aType);
	  }
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  log.error("Exception: ", e);
	  return false;
	}
  }

  public NSFileWrapper fileWrapperRepresentationOfType(String s) {
	log.debug("MyDocument.fileWrapperRepresentationOfType:" + s);
	try {
	  if (MACPAF.equals(s)) {
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayOutputStream baosGed = new ByteArrayOutputStream();
		try {
//					out.output(
//						doc,
//						new FileWriter("/Projects/MacPAFTest/savetest.xml"));
		  out.output(doc, baos);
//					XMLTest.outputWithKay(
//						doc,
//						new FileOutputStream("/Projects/MacPAFTest/savetest.ged"));
		  XMLTest.outputWithKay(doc, baosGed);
		}
		catch (IOException e) {
		  log.error("Exception: ", e);
		}
		NSFileWrapper mainFile = new NSFileWrapper(new NSDictionary());
		NSFileWrapper imagesFolder =
			new NSFileWrapper(new NSDictionary());
		imagesFolder.setPreferredFilename("multimedia");
		String images1 = mainFile.addFileWrapper(imagesFolder);
		//      NSData data = new NSAttributedString("some data goes
		// here").RTFFromRange(new NSRange(0, 15), new NSDictionary());
		//      mainFile.addRegularFileWithContents(NSPropertyListSerialization.dataFromPropertyList(individuals),
		// "individuals.plist");
		//         for (int i=0; i< 10000; i++) {
		//            families.takeValueForKey("familyvalue"+i, ""+i);
		//         }

		//         notes.takeValueForKey("notevalue", "notekey");
		//         log.debug("start serialize");
		//      String plist1 =
		// mainFile.addRegularFileWithContents(NSPropertyListSerialization.XMLDataFromPropertyList(families),
		// "families.plist");
		//      String plist2 =
		// mainFile.addRegularFileWithContents(NSPropertyListSerialization.XMLDataFromPropertyList(notes),
		// "notes.plist");
		//         log.debug("end serialize");
//				String file1 =
//					imagesFolder.addFileWithPath(
//						"/Projects/MacPAFTest/macpaf-screenshot.png");
//				String file2 =
//					imagesFolder.addFileWithPath(
//						"/Projects/MacPAFTest/macpaf-screenshot.tiff");
//				String file3 =
//					imagesFolder.addFileWithPath(
//						"/Projects/MacPAFTest/macpaf-screenshot.jpg");
//				String file4 =
//					imagesFolder.addFileWithPath(
//						"/Projects/MacPAFTest/macpaf-screenshot.gif");
		String xml1 =
			mainFile.addRegularFileWithContents(
			new NSData(baos.toByteArray()),
			"data.xml");
		String ged1 =
			mainFile.addRegularFileWithContents(
			new NSData(baosGed.toByteArray()),
			DEFAULT_GEDCOM_FILENAME);
		log.debug(""
//					"file1="
//						+ file1
//						+ ", file2="
//						+ file2
//						+ ", file3="
//						+ file3
//						+ ", file4="
//						+ file4
				  + ", images1="
				  + images1
				  + ", xml1="
				  + xml1
				  + ", ged1="
				  + ged1);
		return mainFile; //super.fileWrapperRepresentationOfType(s);
	  }
	  // unknown file type
	  log.error("fileWrapperRep unknown file type " + s);
	  return null;
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  log.error("Exception: ", e);
	  return null;
	}
  }

  public boolean loadFileWrapperRepresentation(NSFileWrapper nsFileWrapper, String s) {
	log.debug("MyDocument.loadFileWrapperRepresentation:" + nsFileWrapper + ":" + s);
	try {
	  log.debug("wrapper isDir:" + nsFileWrapper.isDirectory());
	  log.debug("attributes:" + nsFileWrapper.fileAttributes());
	  log.debug("filename: " + fileName() + " type:" + fileType());
	  log.debug("setFileAttr result=" +
				NSPathUtilities.setFileAttributes(fileName() + "/" + DEFAULT_GEDCOM_FILENAME,
												  new
												  NSDictionary(new Integer(NSHFSFileTypes.hfsTypeCodeFromFileType(
		  "TEXT")),
		  NSPathUtilities.FileHFSTypeCode)));
	  String[] errors = new String[1];
	  if (MACPAF.equals(s)) {
		if (nsFileWrapper.isDirectory()) {
		  log.debug("wrappers:" + nsFileWrapper.fileWrappers());
		}
//			        NSFileWrapper familiesPlist = (NSFileWrapper) nsFileWrapper.fileWrappers().valueForKey("families.plist");
//			        log.error("start extract");
//			        families = (NSMutableDictionary) NSPropertyListSerialization.propertyListFromData(familiesPlist.regularFileContents(), NSPropertyListSerialization.PropertyListMutableContainersAndLeaves, new int[]{NSPropertyListSerialization.PropertyListXMLFormat}, errors);
//			        log.error("end extract");
		Enumeration en = nsFileWrapper.fileWrappers().objectEnumerator();
		while (en.hasMoreElements()) {
		  NSFileWrapper wrapper = ( (NSFileWrapper) en.nextElement());
		  log.error(wrapper.filename() + " subattr:" + wrapper.fileAttributes());
		  if (NSPathUtilities.pathExtension(wrapper.filename()).equalsIgnoreCase("ged")) {
			String fullPath = fileName() + "/" + wrapper.filename();
			log.debug("..................Loading gedcom: " + fullPath);
			loadXMLFile(new File(fullPath));
		  }
		}
	  }
	  return true;
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  log.error("Exception: ", e);
	}
	return false;
  }

  /* (non-Javadoc)
   * @see com.apple.cocoa.application.NSDocument#setFileName(java.lang.String)
   */
  public void setFileName(String arg0) {
	try {
	  super.setFileName(arg0);
	  log.debug("mydoc.setfilename() done." + arg0);
	  log.debug("setfilename set file attrs result=" +
				NSPathUtilities.setFileAttributes(arg0 + "/" + DEFAULT_GEDCOM_FILENAME,
												  new NSDictionary(new Integer(
		  NSHFSFileTypes.hfsTypeCodeFromFileType("'TEXT'")), NSPathUtilities.FileHFSTypeCode)));
	  log.debug("setfilename " + arg0 + "/" + DEFAULT_GEDCOM_FILENAME + "attr aft:" +
				NSPathUtilities.fileAttributes(arg0 + "/" + DEFAULT_GEDCOM_FILENAME, false));
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  log.error("Exception: ", e);
	}
  }

  // uses parser from Kay (gedml)
  private void loadXMLFile(File file) {
	if (file == null) {
	  throw new IllegalArgumentException("Cannot parse XML file because file is null.");
	}
	long start = System.currentTimeMillis();
//      Document doc = XMLTest.parseGedcom(file);
	Document newDoc = XMLTest.docParsedWithKay(file);
	Element root = newDoc.getRootElement();
	long end = System.currentTimeMillis();
	log.debug("Time to parse: " + (end - start) / 1000 + " seconds.");
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
	  try {
		addIndividual(indi);
	  }
	  catch (IndividualList.DuplicateKeyException ex) {
		String newKey = ex.validKey();
		String oldKey = indi.getId();
		/** @todo change all instances of the old key to new key in import document */
		try {
		  List needsFixing = XPath.selectNodes(newDoc, "//*[@REF='" + oldKey + "']");
		  log.debug("needsFixing: "+needsFixing);
		  Iterator iter = needsFixing.iterator();
	while (iter.hasNext()) {
	  Element item = (Element)iter.next();
	  log.debug("setting REF from "+item.getAttribute("REF") + " to "+newKey);
	  item.setAttribute("REF", newKey);
	}
	indi.setId(newKey);
	try {
	  addIndividual(indi);
	}
	catch (Exception ex2) {
	  ex2.printStackTrace();
	}
		}
		catch (JDOMException ex1) {
		  log.error("problem with xpath during duplicate key fixing", ex);
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

	log.debug("------------------------------");
	log.debug("----------------------Families");
	log.debug("------------------------------");
	for (int i = 0; i < fams.size(); i++) {
	  Element element = (Element) fams.get(i);
	  Family fam = new FamilyJDOM(element, doc);
	  try {
		addFamily(fam);
	  }
	  catch (FamilyList.DuplicateKeyException ex) {
		String newKey = ex.validKey();
		String oldKey = fam.getId();
		/** @todo change all instances of the old key to new key in import document */
		try {
		  List needsFixing = XPath.selectNodes(newDoc, "//*[@REF='" + oldKey + "']");
		  log.debug("needsFixing: "+needsFixing);
		  Iterator iter = needsFixing.iterator();
	while (iter.hasNext()) {
	  Element item = (Element)iter.next();
	  log.debug("setting REF from "+item.getAttribute("REF") + " to "+newKey);
	  item.setAttribute("REF", newKey);
	}
	fam.setId(newKey);
	try {
	  addFamily(fam);
	}
	catch (Exception ex3) {
	  ex3.printStackTrace();
	}
		}
		catch (JDOMException ex1) {
		  log.error("problem with xpath during duplicate key fixing", ex);
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
	if (individualButton != null) {
	  log.debug("play setindividual:" + firstIndi);
//            assignIndividualToButton(firstIndi, individualButton);
//            setIndividual(individualButton);
	  setPrimaryIndividual(firstIndi);
	}
//        final List objects = doc.getRootElement().getChildren("OBJE");
	for (int i = 0; i < multimedias.size(); i++) {
	  Element obje = (Element) multimedias.get(i);
	  if (obje != null) {
		addMultimedia(obje);
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
	log.debug("noteMap: " + noteMap.size());

  }

  public void addNote(Note note) {
	noteMap.put(note.getId(), note);
  }

  public void addMultimedia(Element obje) {
	log.debug("Root multimedia object:" + obje);
		  Multimedia media = new MultimediaJDOM(obje);
		  multimediaMap.put(media.getId(), media);
//                log.debug("id=" + obje.getAttributeValue("ID"));
  }

  public void addFamily(Family fam) throws FamilyList.DuplicateKeyException {
	familyList.add(fam);
	log.debug("added fam with key: "+fam.getId()+" fam marr date: "+fam.getMarriageEvent().getDateString());
	familyListTableView.reloadData();
  }

  public void addIndividual(Individual indi) throws IndividualList.DuplicateKeyException {
	individualList.add(indi);
	log.debug("added indi with key: "+indi.getId()+" indi name: "+indi.getFullName());
	individualListTableView.reloadData();
  }

  public void removeNote(Note note) {
	/** @todo implement me */
  }

  public void removeMultimedia(Element obje) {
	/** @todo implement me */
  }

  public void removeFamily(Family fam) {
	/** @todo implement me */
  }

  public void removeIndividual(Individual indi) {
	/** @todo implement me */
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
  public NSData dataRepresentationOfType(String aType) {
	// Insert code here to create and return the data for your document.
	log.debug("MyDocument.dataRepresentationOfType():" + aType);
	return new NSAttributedString("some data goes here").RTFFromRange(new NSRange(0, 15), new NSDictionary());
  }

  public boolean loadDataRepresentation(NSData data, String aType) {
	// Insert code here to read your document from the given data.
	log.debug("MyDocument.loadDataRepresentation():" + aType);
	log.debug("load data:" + aType + ":" + new NSStringReference(data, NSStringReference.ASCIIStringEncoding));
	return true;
  }

  private void assignIndividualToButton(Individual indiv, NSButton button) {
	NSAttributedString newLine = new NSAttributedString("\n");
	NSMutableAttributedString nameText = new NSMutableAttributedString(indiv.getFullName());
	if (indiv instanceof Individual.UnknownIndividual) {
	  nameText = new NSMutableAttributedString("Unknown");
	}
	NSMutableAttributedString birthdateText = new NSMutableAttributedString("");
	NSMutableAttributedString birthplaceText = new NSMutableAttributedString("");
	NSMutableAttributedString ordinancesText = new NSMutableAttributedString("bepsc");
	if (indiv.getBirthEvent() != null) {
	  birthdateText = new NSMutableAttributedString(indiv.getBirthEvent().getDateString());
	  if (indiv.getBirthEvent().getPlace() != null) {
		birthplaceText = new NSMutableAttributedString(indiv.getBirthEvent().getPlace().getFormatString());
	  }
	}
	NSMutableAttributedString text = nameText;
	text.appendAttributedString(newLine);
	text.appendAttributedString(birthdateText);
	text.appendAttributedString(newLine);
	text.appendAttributedString(birthplaceText);
	text.appendAttributedString(newLine);
	text.appendAttributedString(ordinancesText);
	button.setAttributedTitle(text);
	URL imageURL = indiv.getImagePath();
	if (imageURL != null && imageURL.toString().length() > 0) {
	  NSImage testImage = new NSImage(imageURL);
	  testImage.setSize(new NSSize(50f, 50f));
	  testImage.setScalesWhenResized(true);
	  button.setImage(testImage);
	}
	individualsButtonMap.setObjectForKey(indiv, button.toString());
  }

  public int numberOfRowsInTableView(NSTableView nsTableView) {
	log.debug("MyDocument.numberOfRowsInTableView():" + nsTableView.tag());
	if (nsTableView.tag() == 1) {
	  if (primaryIndividual.getFamilyAsSpouse() != null) {
		int numChildren = primaryIndividual.getFamilyAsSpouse().getChildren().size();
		log.debug("numberOfRowsInTableView children: " + numChildren);
		return numChildren;
	  }
	}
	else if (nsTableView.tag() == 2) {
	  int numSpouses = primaryIndividual.getSpouseList().size();
	  log.debug("numberOfRowsInTableView spouses: " + numSpouses);
	  return numSpouses;
	}
	return 0;
  }

  public Object tableViewObjectValueForLocation(NSTableView nsTableView, NSTableColumn nsTableColumn, int i) {
	try {
//			log.debug("nsTableColumn=" + nsTableColumn.identifier()+"; i="+i+"; tag="+nsTableView.tag());
	  if (nsTableView.tag() == 1) {
		if (nsTableColumn.identifier() != null && nsTableColumn.identifier().equals("num")) {
		  nsTableColumn.setWidth(5.0f);
		  return String.valueOf(i + 1);
		}
		Individual individual = (Individual) primaryIndividual.getFamilyAsSpouse().getChildren().get(i);
		individualsButtonMap.setObjectForKey(individual, "child" + i);
		return individual.getFullName();
	  }
	  else if (nsTableView.tag() == 2) {
		Individual individual = (Individual) primaryIndividual.getSpouseList().get(i);
		individualsButtonMap.setObjectForKey(individual, "spouse" + i);
		return individual.getFullName();
	  }
	  return "Unknown";
	}
	catch (Exception e) {
	  // TODO Auto-generated catch block
	  log.error("Exception: ", e);
	  return "An Error Has Occurred";
	}
  }

  public void printShowingPrintPanel(boolean showPanels) {
	log.debug("printshowingprintpanel:" + showPanels);
	// Obtain a custom view that will be printed
	printInfo().setTopMargin(36);
	printInfo().setBottomMargin(36);
	printInfo().setLeftMargin(72);
	printInfo().setRightMargin(36);
	setPrintInfo(printInfo());
//       NSView printView = printableView;

	// Construct the print operation and setup Print panel
	NSPrintOperation op = NSPrintOperation.printOperationWithView(printableView, printInfo());
	log.debug("papersize: " + printInfo().paperSize());
	log.debug("left margin: " + printInfo().leftMargin());
	log.debug("right margin: " + printInfo().rightMargin());
	log.debug("top margin: " + printInfo().topMargin());
	log.debug("bottom margin: " + printInfo().bottomMargin());
	op.setShowPanels(showPanels);
	if (showPanels) {
	  // Add accessory view, if needed
	}

	// Run operation, which shows the Print panel if showPanels was YES
	runModalPrintOperation(op, null, null, null);
  }

//   private NSView printableView() {
//      return printableView;
//      return new PedigreeView(new NSRect(0,0,printInfo().paperSize().width()-printInfo().leftMargin()-printInfo().rightMargin(),printInfo().paperSize().height()-printInfo().topMargin()-printInfo().bottomMargin()), primaryIndividual, 4);
//      return new FamilyGroupSheetView(new NSRect(0,0,printInfo().paperSize().width()-printInfo().leftMargin()-printInfo().rightMargin(),printInfo().paperSize().height()-printInfo().topMargin()-printInfo().bottomMargin()), primaryIndividual);
//      return new IndividualSummaryView(new NSRect(0,0,printInfo().paperSize().width()-printInfo().leftMargin()-printInfo().rightMargin(),printInfo().paperSize().height()-printInfo().topMargin()-printInfo().bottomMargin()), primaryIndividual);
//   }

  /**
   * Saves this document to disk with all files in the file package
   */
  public void save() {
	log.debug("___ save()");
	// call the standard Cocoa save action
	try {
//            Element root = doc.getRootElement();
//            root.addContent(new HeaderJDOM().getElement());
//            root.addContent(new SubmitterJDOM().getElement());
//            FamilyJDOM fam = new FamilyJDOM(doc);
//            fam.setId("F11");
//            FamilyJDOM fam2 = new FamilyJDOM(doc);
//            fam2.setId("F25");
//            IndividualJDOM indiv = new IndividualJDOM(doc);
//            indiv.setId("I13");
//            indiv.setGender(Gender.FEMALE);
//            indiv.setFullName("", "Jayden Kathleen", "Allred", "");
//            indiv.setAFN("AFN3734-473");
//            EventJDOM birthEvt = EventJDOM.createBirthEventInstance(fam);
//            birthEvt.setDateString("6 Aug 2002");
//            birthEvt.setPlace(new PlaceJDOM("Orem, Utah, Utah, USA"));
//            indiv.setBirthEvent(birthEvt);
//            indiv.setRin(2);
//            indiv.setFamilyAsChild(fam);
//            indiv.setFamilyAsSpouse(fam2);
//            fam.addChild(indiv);
//            fam2.setMother(indiv);
//            root.addContent(indiv.getElement());
//            root.addContent(fam.getElement());
//            root.addContent(fam2.getElement());
//		log.debug("root="+root);
	}
	catch (RuntimeException e) {
	  log.error("Exception: ", e);
	}
	saveDocument(this);
	log.debug("MyDocument.save() end, filename=" + fileName());
  }

  public void importFile(Object sender) { /* IBAction */
	log.debug("importFile: " + sender);
//        NSApplication nsapp = NSApplication.sharedApplication();
//        nsapp.beginSheet(importWindow, mainWindow, this, null, null);
	NSOpenPanel panel = NSOpenPanel.openPanel();
//panther only?        panel.setMessage("Please select a GEDCOM file to import into this MacPAF file.");
	panel.beginSheetForDirectory(null, null, new NSArray(new Object[] {"GED"}), mainWindow,
								 this,
								 new NSSelector("openPanelDidEnd", new Class[] {NSOpenPanel.class, int.class, Object.class}), null);
  }

  public void openPanelDidEnd(NSOpenPanel sheet, int returnCode, Object contextInfo) {
	if (returnCode == NSPanel.OKButton) {
	  log.debug("import filename:" + sheet.filename());
	  importGEDCOM(new File(sheet.filename()));
	}
  }

  private void importGEDCOM(File importFile) {
	log.debug("MyDocument.importGEDCOM():" + importFile);
	loadXMLFile(importFile);
  }

  public void exportFile(Object sender) { /* IBAction */
	log.debug("exportFile: " + sender);
//        save();
	NSSavePanel panel = NSSavePanel.savePanel();
	panel.setCanSelectHiddenExtension(true);
	panel.setExtensionHidden(false);
//panther only?        panel.setMessage("Choose the name and location for the exported GEDCOM file.\n\nThe file name should end with .ged");
//        panel.setNameFieldLabel("Name Field Label:");
//        panel.setPrompt("Prompt:");
	panel.setRequiredFileType("ged");
//        panel.setTitle("Title");
	panel.beginSheetForDirectory(null, null, mainWindow, this,
								 new NSSelector("savePanelDidEndReturnCode", new Class[] {NSSavePanel.class, int.class,
												Object.class}), null);
  }

  public void savePanelDidEndReturnCode(NSSavePanel sheet, int returnCode, Object contextInfo) {
	log.debug("MyDocument.savePanelDidEndReturnCode(sheet, returnCode, contextInfo):" + sheet + ":" + returnCode + ":" +
			  contextInfo);
	if (returnCode == NSPanel.OKButton) {
	  log.debug("export filename:" + sheet.filename());
	  try {
		XMLTest.outputWithKay(doc, new FileOutputStream(sheet.filename()));
	  }
	  catch (Exception e) {
		log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	  }
	}
	else if (returnCode == NSPanel.CancelButton) {
	  log.debug("save panel cancelled, sheet filename=" + sheet.filename() + ", doc filename=" + fileName());
	  if (fileName() == null || fileName().length() == 0) {
		log.debug("cancel with null filename, should I close the document?");
//          close();
	  }
	}
  }

  public void windowDidBecomeMain(NSNotification aNotification) {
	log.debug("MyDocument.windowDidBecomeMain()");
	save();
  }

  public Individual createNewIndividual() {
	return new IndividualJDOM(doc);
  }

  /**
   * prepareSavePanel
   *
   * @param nSSavePanel NSSavePanel
   * @return boolean
   * @todo Implement this com.apple.cocoa.application.NSDocument method
   */
  public boolean prepareSavePanel(NSSavePanel nSSavePanel) {
	log.debug("MyDocument.prepareSavePanel(nSSavePanel):" + nSSavePanel);
	nSSavePanel.setDelegate(this);
	return true;
  }

  public boolean panelIsValidFilename(Object sender,
									  String filename) {
	log.debug("MyDocument.panelIsValidFilename(sender, filename):" + sender + ":" + filename);
	return true;
  }

}
