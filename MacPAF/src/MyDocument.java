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

import org.apache.log4j.Category;
import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.LocationList;
import com.redbugz.macpaf.SurnameList;
import com.redbugz.macpaf.jdom.MacPAFDocumentJDOM;

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

  MacPAFDocumentJDOM doc = null;//new MacPAFDocumentJDOM();

  // Maps the buttons to the individuals they represent
  protected NSMutableDictionary individualsButtonMap = new NSMutableDictionary();

  /**
   * This is the main individual to whom apply all actions
   */
  private Individual primaryIndividual = new Individual.UnknownIndividual();

  // This was originally to check for the constructor getting called twice
  public boolean firstconstr = true;

  // All of the outlets in the nib
  public NSWindow mainWindow; /* IBOutlet */
  public NSWindow reportsWindow; /* IBOutlet */
  public NSWindow importWindow; /* IBOutlet */
  public NSWindow familyEditWindow; /* IBOutlet */
  public NSWindow familyListWindow; /* IBOutlet */
  public NSWindow individualListWindow; /* IBOutlet */
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
  public NSButton familyAsSpouseButton; /* IBOutlet */
  public NSButton familyAsChildButton; /* IBOutlet */
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

//  private IndividualList startupIndividualList;
//  private FamilyList startupFamilyList;
  /**
   * This the internal name for the gedcom file in the .macpaf file package
   */
  private static final String DEFAULT_GEDCOM_FILENAME = "data.ged";
  /**
   * This the internal name for the data xml file in the .macpaf file package
   */
  private static final String DEFAULT_XML_FILENAME = "data.xml";

  /**
 * This method was originally started to avoid the bug that in Java-Cocoa applications,
 * constructors get called twice, so if you initialize anything in a constructor, it can get
 * nuked later by another constructor
 */
  private void initDoc() {
	log.error("MyDocument.initDoc()");
	if (doc == null) {
	  log.error("????????????????????????????????????????????????????? doc is null, making new doc");
	  doc = new MacPAFDocumentJDOM();
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
	} else {
	  ( (FamilyEditController) familyEditWindow.delegate()).setFamily(primaryIndividual.getFamilyAsSpouse());
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
	  case 3:
		printableView = new PocketPedigreeView(new NSRect(0, 0,
			printInfo().paperSize().width() - printInfo().leftMargin() - printInfo().rightMargin(),
			printInfo().paperSize().height() - printInfo().topMargin() - printInfo().bottomMargin()), primaryIndividual,
										 6);
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
	// if primary individual is unknown, change button back to enabled
	individualButton.setEnabled(true);
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
	familyAsSpouseButton.setTitle("Family: "+primaryIndividual.getFamilyAsSpouse().getId());
	familyAsChildButton.setTitle("Family: "+primaryIndividual.getFamilyAsChild().getId());
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
	  log.info("tableview selectedRow = "+tv.selectedRow());
	  if (tv.selectedRow() >= 0) {
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
	  mainWindow.setToolbar(new MainToolbar());
	  log.debug("mainWindow:" + mainWindow);
	  log.debug("indivEditWindow:" + individualEditWindow);
	  //myAction(this);
	  NSAttributedString text = individualButton.attributedTitle();
	  log.debug("indivButton attrTitle: " + text);
//	  log.debug("indivButton attr at index 5:" + text.attributesAtIndex(5, null));
	  log.debug("individualList: " + individualsButtonMap.count() + "(" + individualsButtonMap + ")");
	  System.out.println(" individualList="+individualList);
	  log.debug("individualList: " + individualList.size()); // + "(" + indiMap + ")");
	  individualList.document = this;
	  familyList.document = this;
	  individualList.setIndividualMap(doc.getIndividualsMap());
	  familyList.setFamilyMap(doc.getFamilyMap());
	  individualListTableView.setDataSource(individualList);
	  individualListTableView.setDelegate(individualList);
	  System.out.println(" familyList="+familyList);
	  familyListTableView.setDataSource(familyList);
	  familyListTableView.setDelegate(familyList);
	  if (individualList.size() > 0) {
		log.debug("indimap");
		assignIndividualToButton( (Individual) individualList.getFirstIndividual(), individualButton);
		setIndividual(individualButton);
	  }
	  else {
		log.info("!!! No individuals in file");
		setPrimaryIndividual(Individual.UNKNOWN);
	  }
	  // add famMap to FamilyList
//	  for (Iterator iter = famMap.values().iterator(); iter.hasNext(); ) {
//		Family fam = (Family) iter.next();
//		familyList.add(fam);
//	  }
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
		  doc.loadXMLFile(new File(filename));
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayOutputStream baosGed = new ByteArrayOutputStream();
		try {
//					out.output(
//						doc,
//						new FileWriter("/Projects/MacPAFTest/savetest.xml"));
		  doc.outputToXML(baos);
//					XMLTest.outputWithKay(
//						doc,
//						new FileOutputStream("/Projects/MacPAFTest/savetest.ged"));
		  doc.outputToGedcom(baosGed);
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
//						"/Projects/Mcreenshot.gif");
		String dataXmlFilename = DEFAULT_XML_FILENAME;
		String dataGedcomFilename = DEFAULT_GEDCOM_FILENAME;
//		if (fileName() != null && fileName().length() > 0) {
//		  dataXmlFilename = fileName()+".xml";
//		  dataGedcomFilename = fileName()+".ged";
//}
		String xml1 =
			mainFile.addRegularFileWithContents(
			new NSData(baos.toByteArray()),
			dataXmlFilename);
		String ged1 =
			mainFile.addRegularFileWithContents(
			new NSData(baosGed.toByteArray()),
			dataGedcomFilename);
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
			doc.loadXMLFile(new File(fullPath));
			// save individualList in a temporary so I can restore it when the
			// Cocoa startup sequence calls the constructor twice and clobbers it
//			startupIndividualList = individualList;
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
	  log.debug("setFilename setting lastOpenedDocument to "+fileName());
	  NSUserDefaults.standardUserDefaults().setObjectForKey(fileName(), "com.redbugz.macpaf.lastOpenedDocument");
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

  public void addFamily(Family newFamily) {
	try {
	  doc.addFamily(newFamily);
	  if (familyListTableView != null) {
		familyListTableView.reloadData();
	  }
	}
	catch (Exception ex) {
	  ex.printStackTrace();
	}
  }

  public void addIndividual(Individual newIndividual) {
	try {
	  doc.addIndividual(newIndividual);
	  log.debug("added newIndividual with key: "+newIndividual.getId()+" newIndividual name: "+newIndividual.getFullName());
	  if (individualListTableView != null) {
		individualListTableView.reloadData();
	  }
	}
	catch (Exception ex) {
	  ex.printStackTrace(); /** @todo figure out what to do here */
	}
  }

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
	button.setEnabled(true);
	if (indiv instanceof Individual.UnknownIndividual) {
	  nameText = new NSMutableAttributedString("Unknown");
	  button.setEnabled(false);
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
	doc.loadXMLFile(importFile);
	if (primaryIndividual.equals(Individual.UNKNOWN)) {
	  // set first individual in imported file to primary individual
	  setPrimaryIndividual(individualList.getSelectedIndividual());
  }
  individualListTableView.reloadData();
  familyListTableView.reloadData();
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
		doc.outputToGedcom(new FileOutputStream(sheet.filename()));
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
	return doc.createNewIndividual();//new IndividualJDOM(doc);
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

  public void addNewIndividual(Object sender) { /* IBAction */
	log.debug("addNewIndividual: " + sender);
	Individual newIndividual = createNewIndividual();
	addIndividual(newIndividual);
	setPrimaryIndividual(newIndividual);
	save();
	openIndividualEditSheet(this);
  }

  public void addNewChild(Object sender) { /* IBAction */
	log.debug("addNewChild: " + sender);
	Individual newChild = createNewIndividual();
	addIndividual(newChild);
	primaryIndividual.getFamilyAsSpouse().addChild(newChild);
	newChild.setFamilyAsChild(primaryIndividual.getFamilyAsSpouse());
	setPrimaryIndividual(newChild);
	save();
	openIndividualEditSheet(this);
  }

  public void addNewSpouse(Object sender) { /* IBAction */
	log.debug("addNewSpouse: " + sender);
	Individual newSpouse = createNewIndividual();
	addIndividual(newSpouse);
	primaryIndividual.addSpouse(newSpouse);
	newSpouse.addSpouse(primaryIndividual);
	setPrimaryIndividual(newSpouse);
	save();
	openIndividualEditSheet(this);
  }

  public void addNewFather(Object sender) { /* IBAction */
	log.debug("addNewFather: " + sender);
	Individual newFather = createNewIndividual();
	addIndividual(newFather);
	primaryIndividual.setFather(newFather);
	newFather.getFamilyAsSpouse().addChild(primaryIndividual);
	setPrimaryIndividual(newFather);
	save();
	openIndividualEditSheet(this);
  }

  public void addNewMother(Object sender) { /* IBAction */
	log.debug("addNewMother: " + sender);
	Individual newMother = createNewIndividual();
	addIndividual(newMother);
	primaryIndividual.setMother(newMother);
	newMother.getFamilyAsSpouse().addChild(primaryIndividual);
	setPrimaryIndividual(newMother);
	save();
	openIndividualEditSheet(this);
  }

  public void addNewFamily(Object sender) { /* IBAction */
	log.debug("addNewFamily: " + sender);
  }

  public void addNewFamilyAsSpouse(Object sender) { /* IBAction */
	log.debug("addNewFamilyAsSpouse: " + sender);
  }

  public void addNewFamilyAsChild(Object sender) { /* IBAction */
	log.debug("addNewFamilyAsChild: " + sender);
  }

  public void showFamilyList(Object sender) { /* IBAction */
	log.debug("showFamilyList: " + sender);
	familyListWindow.setDelegate(this);
	familyListWindow.makeKeyAndOrderFront(sender);
  }

  public void showIndividualList(Object sender) { /* IBAction */
	log.debug("showIndividualList: " + sender);
	individualListWindow.setDelegate(this);
	individualListWindow.makeKeyAndOrderFront(sender);
  }
}
