//
//  MyDocument.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun Dec 22 2002.
//  Copyright (c) 2002 __MyCompanyName__. All rights reserved.
//

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.redbugz.macpaf.jdom.*;
import com.redbugz.macpaf.jdom.EventJDOM;
import com.redbugz.macpaf.jdom.HeaderJDOM;
import com.redbugz.macpaf.jdom.IndividualJDOM;
import com.redbugz.macpaf.jdom.PlaceJDOM;
import com.redbugz.macpaf.jdom.SubmitterJDOM;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.*;

public class MyDocument extends NSDocument {
{
	System.err.println("><><><><><><>><><><<><><><><><><><><><><><><><><> MyDocument initializer");
	initDoc();
}
    public static final String GEDCOM = "GEDCOM File (.ged)";
    public static final String MACPAF = "MacPAF File";

    private NSFileWrapper gedcomFile = null;
    private Document doc;// = makeInitialGedcomDoc();

    protected NSMutableDictionary individuals = new NSMutableDictionary();
    protected NSMutableDictionary families = new NSMutableDictionary();
    protected NSMutableDictionary notes = new NSMutableDictionary();
    protected NSMutableDictionary sources = new NSMutableDictionary();
    protected NSMutableDictionary repositories = new NSMutableDictionary();
    protected NSMutableDictionary multimedia = new NSMutableDictionary();

    private Individual primaryIndividual = new Individual.UnknownIndividual();
//    File docFile = null;

    protected Map indiMap;// = new HashMap();
    protected Map famMap;// = new HashMap();
    protected Map noteMap;// = new HashMap();
    protected Map multimediaMap;// = new HashMap();
    protected Map repositoryMap;// = new HashMap();
    protected Map sourceMap;// = new HashMap();
    protected Map submitterMap;// = new HashMap();

    public boolean firstconstr = true;
//    private ArrayList tempFams = new ArrayList();

    public NSWindow mainWindow; /* IBOutlet */
    public NSWindow reportsWindow; /* IBOutlet */
    public NSWindow importWindow; /* IBOutlet */
    public NSWindow familyEditWindow; /* IBOutlet */
    public FamilyList familyList;// = new FamilyList(); /* IBOutlet */
    public IndividualList individualList;// = new IndividualList(); /* IBOutlet */
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
    private IndividualDetailController individualDetailController = new IndividualDetailController();
    private NSView printableView;
	private static final String DEFAULT_GEDCOM_FILENAME = "data.ged";

  private void initDoc() {
		System.err.println("MyDocument.initDoc()");
		if (doc == null) {
			System.err.println("????????????????????????????????????????????????????? doc is null, making new doc");
			doc = new Document(new Element("GED"));
			indiMap = new HashMap();
			famMap = new HashMap();
			noteMap = new HashMap();
			multimediaMap = new HashMap();
			repositoryMap = new HashMap();
			Map sourceMap = new HashMap();
			submitterMap = new HashMap();
			System.out.println("familylist: "+familyList);
			familyList = new FamilyList();
			individualList = new IndividualList();
			}
	}

	public MyDocument() {
        super();
        System.err.println("MyDocument.MyDocument()");
        initDoc();
    }

    public MyDocument(String fileName, String fileType) {
        super(fileName, fileType);
        System.err.println("MyDocument.MyDocument("+fileName+", "+fileType+")");
        try {
        	initDoc();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
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
        ((NSWindowController) familyEditWindow.delegate()).setDocument(this);
        if (((NSButton) sender).tag() == 1) {
            ((FamilyEditController) familyEditWindow.delegate()).setFamily(primaryIndividual.getFamilyAsChild());
        }
        NSApplication nsapp = NSApplication.sharedApplication();
        nsapp.beginSheet(familyEditWindow, mainWindow, this, new NSSelector("sheetDidEndShouldClose2", new Class[]{}), null);
        //nsapp.runModalForWindow(familyEditWindow);
        //nsapp.endSheet(familyEditWindow);
        //familyEditWindow.orderOut(this);
    }

    public void sheetDidEndShouldClose2() {
        System.out.println("sheetDidEndShouldClose2");
    }

    public void openIndividualEditSheet(Object sender) { /* IBAction */
        ((NSWindowController) individualEditWindow.delegate()).setDocument(this);
        NSApplication nsapp = NSApplication.sharedApplication();
        nsapp.beginSheet(individualEditWindow, mainWindow, null, null, null);
        //nsapp.runModalForWindow(individualEditWindow);
//      nsapp.endSheet(individualEditWindow);
//      individualEditWindow.orderOut(this);
    }

    public void openReportsSheet(Object sender) { /* IBAction */
        ((NSWindowController) reportsWindow.delegate()).setDocument(this);
//      if (!myCustomSheet)
//          [NSBundle loadNibNamed: @"MyCustomSheet" owner: self];

        NSApplication nsapp = NSApplication.sharedApplication();
        System.out.println("openReportsSheet mainWindow:" + mainWindow);
//      reportsWindow.makeKeyAndOrderFront(this);
        nsapp.beginSheet(reportsWindow, mainWindow, this, null, null);
        //nsapp.runModalForWindow(reportsWindow);
        //nsapp.endSheet(individualEditWindow);
        //individualEditWindow.orderOut(this);
    }

    public void setPrintableView(Object sender) { /* IBAction */
        System.out.println("setPrintableView selected=" + reportsRadio.selectedTag());
        printInfo().setTopMargin(36);
        printInfo().setBottomMargin(36);
        printInfo().setLeftMargin(72);
        printInfo().setRightMargin(36);
        setPrintInfo(printInfo());
        switch (reportsRadio.selectedTag()) {
            case 0:
                printableView = new PedigreeView(new NSRect(0, 0, printInfo().paperSize().width() - printInfo().leftMargin() - printInfo().rightMargin(), printInfo().paperSize().height() - printInfo().topMargin() - printInfo().bottomMargin()), primaryIndividual, 4);
                break;
            case 1:
                printableView = new FamilyGroupSheetView(new NSRect(0, 0, printInfo().paperSize().width() - printInfo().leftMargin() - printInfo().rightMargin(), printInfo().paperSize().height() - printInfo().topMargin() - printInfo().bottomMargin()), primaryIndividual);
                break;
            default:
                printableView = new PedigreeView(new NSRect(0, 0, printInfo().paperSize().width() - printInfo().leftMargin() - printInfo().rightMargin(), printInfo().paperSize().height() - printInfo().topMargin() - printInfo().bottomMargin()), primaryIndividual, 4);
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
        System.out.println("setIndividual to " + sender);
        if (sender instanceof NSButton) {
            try {
                System.out.println("indivmap=" + individuals.objectForKey(sender.toString()));
                Individual newIndividual = (Individual) individuals.objectForKey(sender.toString());
                NSButton animateButton = (NSButton) sender;

                NSPoint individualButtonOrigin = individualButton.frame().origin();
                NSPoint animateButtonOrigin = animateButton.frame().origin();
                System.out.println("animating from " + animateButtonOrigin + " to " + individualButtonOrigin);
                if (!animateButtonOrigin.equals(individualButtonOrigin)) {
                    float stepx = (individualButtonOrigin.x() - animateButtonOrigin.x()) / 10;
                    float stepy = (individualButtonOrigin.y() - animateButtonOrigin.y()) / 10;
                    NSImage image = new NSImage();
                    System.out.println("animatebutton.bounds:" + animateButton.bounds());
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (sender instanceof NSTableView) {
            NSTableView tv = (NSTableView) sender;
            NSView superview = individualButton.superview();
            System.out.println("indivmap=" + individuals.objectForKey("child" + tv.selectedRow()));
            NSPoint individualButtonOrigin = individualButton.frame().origin();
            Individual newIndividual = (Individual) individuals.objectForKey("child" + tv.selectedRow());
            if (tv.tag() == 2) {
                newIndividual = (Individual) individuals.objectForKey("spouse" + tv.selectedRow());
                individualButtonOrigin = spouseButton.frame().origin();
            }
            NSRect rowRect = tv.convertRectToView(tv.rectOfRow(tv.selectedRow()), superview);
            NSPoint tvOrigin = rowRect.origin();
            System.out.println("animating from " + tvOrigin + " to " + individualButtonOrigin);
            float stepx = (individualButtonOrigin.x() - tvOrigin.x()) / 10;
            float stepy = (individualButtonOrigin.y() - tvOrigin.y()) / 10;
            NSImage image = new NSImage();
            System.out.println("rowrect:" + rowRect);
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
            } else {
                setPrimaryIndividual(newIndividual);
            }
        } else if (sender instanceof IndividualList) {
            IndividualList iList = (IndividualList) sender;
            setPrimaryIndividual(iList.getSelectedIndividual());
        } else if (sender instanceof FamilyList) {
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
            System.out.println("mainWindow:" + mainWindow);
            System.out.println("indivEditWindow:" + individualEditWindow);
            //myAction(this);
            NSAttributedString text = individualButton.attributedTitle();
            System.out.println("indivButton attrTitle: " + text);
            System.out.println("indivButton attr at index 5:" + text.attributesAtIndex(5, null));
            System.out.println("individuals: " + individuals.count() + "(" + individuals + ")");
            System.out.println("indimap: " + indiMap.size());// + "(" + indiMap + ")");
            if (indiMap.size() > 0) {
                System.out.println("indimap");
                assignIndividualToButton((Individual) indiMap.values().toArray()[0], individualButton);
                setIndividual(individualButton);
            } else {
//            System.out.println("test data");
//            test.createTestData();
                setPrimaryIndividual(Individual.UNKNOWN);
            }
            // add indiMap to IndividualsList
            for (Iterator iter = indiMap.values().iterator(); iter.hasNext();) {
				Individual indi = (Individual) iter.next();
				individualList.add(indi);
			}
            // add famMap to FamilyList
            for (Iterator iter = famMap.values().iterator(); iter.hasNext();) {
            	Family fam = (Family) iter.next();
            	familyList.add(fam);
            }
//         NSImage testImage = new NSImage("/Users/logan/Pictures/iPhoto Library/Albums/Proposal/GQ.jpg", false);
//         testImage.setSize(new NSSize(50f, 50f));
//         testImage.setScalesWhenResized(true);
            System.out.println("indivButton cell type: " + individualButton.cell().type());
//         individualButton.setImage(testImage);
//         save();
            // save newly opened document as preference
            System.out.println("setting lastOpenedDocument to: "+fileName());
            NSUserDefaults.standardUserDefaults().setObjectForKey(fileName(), "com.redbugz.macpaf.lastOpenedDocument");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }



    public boolean readFromFile(String filename, String aType) {
        System.out.println("readfromfile type:" + aType + ":" + filename);
        System.out.println("********* readFromFile");
		try {
				if (GEDCOM.equals(aType)) {
					// import GEDCOM into this file
					boolean success = super.readFromFile(filename, aType);
					if (success) {
						importGEDCOM(new File(filename));
						setFileType(MACPAF);
						setFileName(null);//"ThisIsATest.macpaf");
//						save();
					}
					return success;
			   }
			    if (!MACPAF.equals(aType)) {
			        try {
//			            docFile = new File(filename);
			            playWithXML(new File(filename));
			        } catch (Throwable e) {
			            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
			            return false;
			        }
			        return true;//super.readFromFile(filename, aType);
			    } else {
			        return super.readFromFile(filename, aType);
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }

    public NSFileWrapper fileWrapperRepresentationOfType(String s) {
		System.out.println("MyDocument.fileWrapperRepresentationOfType:" + s);
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
				} catch (IOException e) {
					e.printStackTrace();
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
				//         System.out.println("start serialize");
				//      String plist1 =
				// mainFile.addRegularFileWithContents(NSPropertyListSerialization.XMLDataFromPropertyList(families),
				// "families.plist");
				//      String plist2 =
				// mainFile.addRegularFileWithContents(NSPropertyListSerialization.XMLDataFromPropertyList(notes),
				// "notes.plist");
				//         System.out.println("end serialize");
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
				System.out.println(""
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
			System.err.println("fileWrapperRep unknown file type " + s);
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

    public boolean loadFileWrapperRepresentation(NSFileWrapper nsFileWrapper, String s) {
        System.out.println("MyDocument.loadFileWrapperRepresentation:" + nsFileWrapper + ":" + s);
		try {
			    System.out.println("wrapper isDir:" + nsFileWrapper.isDirectory());
			    System.out.println("attributes:" + nsFileWrapper.fileAttributes());
			    System.out.println("filename: " + fileName() + " type:" + fileType());
			    System.out.println("setFileAttr result=" + NSPathUtilities.setFileAttributes(fileName() + "/"+DEFAULT_GEDCOM_FILENAME, new NSDictionary(new Integer(NSHFSFileTypes.hfsTypeCodeFromFileType("TEXT")), NSPathUtilities.FileHFSTypeCode)));
			    String[] errors = new String[1];
			   if (MACPAF.equals(s)) {
			        if (nsFileWrapper.isDirectory()) {
			            System.out.println("wrappers:" + nsFileWrapper.fileWrappers());
			        }
//			        NSFileWrapper familiesPlist = (NSFileWrapper) nsFileWrapper.fileWrappers().valueForKey("families.plist");
//			        System.err.println("start extract");
//			        families = (NSMutableDictionary) NSPropertyListSerialization.propertyListFromData(familiesPlist.regularFileContents(), NSPropertyListSerialization.PropertyListMutableContainersAndLeaves, new int[]{NSPropertyListSerialization.PropertyListXMLFormat}, errors);
//			        System.err.println("end extract");
			    Enumeration en = nsFileWrapper.fileWrappers().objectEnumerator();
			    while (en.hasMoreElements()) {
			        NSFileWrapper wrapper = ((NSFileWrapper) en.nextElement());
			        System.err.println(wrapper.filename() + " subattr:" + wrapper.fileAttributes());
			        if (NSPathUtilities.pathExtension(wrapper.filename()).equalsIgnoreCase("ged")) {
			        	String fullPath = fileName()+"/"+wrapper.filename();
			        	System.out.println("..................Loading gedcom: "+fullPath);
			        	playWithXML(new File(fullPath));
			        }
			    }
			   }
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }

    /* (non-Javadoc)
     * @see com.apple.cocoa.application.NSDocument#setFileName(java.lang.String)
     */
    public void setFileName(String arg0) {
		try {
			super.setFileName(arg0);
			System.out.println("mydoc.setfilename() done." + arg0);
			System.out.println("setfilename set file attrs result=" + NSPathUtilities.setFileAttributes(arg0 + "/"+DEFAULT_GEDCOM_FILENAME, new NSDictionary(new Integer(NSHFSFileTypes.hfsTypeCodeFromFileType("'TEXT'")), NSPathUtilities.FileHFSTypeCode)));
			System.out.println("setfilename " + arg0 + "/"+DEFAULT_GEDCOM_FILENAME+"attr aft:" + NSPathUtilities.fileAttributes(arg0 + "/"+DEFAULT_GEDCOM_FILENAME, false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // uses parser from Kay (gedml)
    private void playWithXML(File file) {
        if (file == null) {
            file = new File(NSBundle.mainBundle().pathForResource("example", "ged"));
        }
        if (indiMap == null) {
            indiMap = new HashMap();
        }
        long start = System.currentTimeMillis();
//      Document doc = XMLTest.parseGedcom(file);
        doc = XMLTest.docParsedWithKay(file);
        Element root = doc.getRootElement();
        if (root.getChild(Header.HEADER) == null) {
          root.addContent(new HeaderJDOM().getElement());
        }
        if (root.getChild(Submitter.SUBMITTER) == null) {
          root.addContent(new SubmitterJDOM().getElement());
        }
        long end = System.currentTimeMillis();
        System.out.println("Time to parse: " + (end - start) / 1000 + " seconds.");
        List fams = root.getChildren("FAM");
        List indis = root.getChildren("INDI");
        List multimedias = root.getChildren("OBJE");
        List notes = root.getChildren("NOTE");
        List repositories = root.getChildren("REPO");
        List sources = root.getChildren("SOUR");
        List submitters = root.getChildren("SUBM");
        System.out.println("file has:\n\t" + indis.size() + " individuals\n\t" + fams.size() + " families\n\t" + notes.size() + " notes\n\t" + multimedias.size() + " multimedia objects\n\t" + sources.size() + " sources\n\t" + repositories.size() + " repositories\n\t" + submitters.size() + " submitters\n");
        System.out.println("header: "+new HeaderJDOM(root.getChild("HEAD")));
        System.out.println("submission record: "+new SubmitterJDOM(root.getChild("SUBN")));
//        System.out.println("getting indis");
//        NSMutableArray names = new NSMutableArray();
//        for (int i = 0; i < indis.size(); i++) {
//            Element element = (Element) indis.get(i);
//            names.addObject(element.getChildText("NAME"));
//        }
//        System.out.println("got indi names, now getting fams");
//        for (int i = 0; i < fams.size(); i++) {
//            Element element = (Element) fams.get(i);
//            names.addObject(element.getAttributeValue("ID"));
//        }

        boolean debug = false;

        for (int i = 0; i < notes.size(); i++) {
            Element element = (Element) notes.get(i);
            if (debug) System.out.println("element:" + element.getContent());
            Note note = new NoteJDOM(element);
//         List concs = element.getChildren();
//         for (int j=0; j < concs.size(); j++) {
//            Element el = (Element) concs.get(j);
//            System.out.println(el.getName()+": <"+el.getText()+">");
//            if (!el.getText().equals(el.getTextTrim())) System.out.println(el.getName()+" trim: <"+el.getTextTrim()+">");
//            if (!el.getTextTrim().equals(el.getTextNormalize())) System.out.println(el.getName()+" normalize: <"+el.getTextNormalize()+">");
//         }
            noteMap.put(note.getId(), note);
        }
        System.out.println("noteMap: " + noteMap.size());


        Runtime rt = Runtime.getRuntime();
        System.out.println("------------------------------");
        System.out.println("-------------------Individuals");
        System.out.println("------------------------------");
        Individual firstIndi = Individual.UNKNOWN;
        for (int i = 0; i < indis.size(); i++) {
            Element element = (Element) indis.get(i);
            if (debug) System.out.println("element:" + element.getContent());
            if (debug) System.out.println("name:" + element.getChildText("NAME"));
            //System.out.println("bday:" + element.getChild("BIRT").getChildText("DATE"));
            Individual indi = new IndividualJDOM(element, doc);
//            Element noteEl = element.getChild("NOTE");
//            Note note = new MyNote();
//            if (noteEl != null && noteEl.getAttributeValue("REF") != null) {
//                note = (Note) noteMap.get(noteEl.getAttributeValue("REF"));
//            } else {
//                note = new MyNote(noteEl);
//            }
//            indi.setNote(note);
            individualList.add(indi);
            indiMap.put(indi.getId(), indi);
            System.out.println("indiMap added indi: "+indi);
            if (indi.getRin() > 0 && indi.getRin() < firstIndi.getRin()) {
                System.out.println("Setting 1st Individual to: "+indi);
                firstIndi = indi;
            }
            if (debug) System.err.println("\n *** " + i + " mem:" + rt.freeMemory() / 1024 + " Kb\n");
            if (rt.freeMemory() < 50000) {
                if (debug) System.err.println("gc");
                rt.gc();
            }
        }

        System.out.println("------------------------------");
        System.out.println("----------------------Families");
        System.out.println("------------------------------");
        for (int i = 0; i < fams.size(); i++) {
            Element element = (Element) fams.get(i);
            Family fam = new FamilyJDOM(element, doc);
            familyList.add(fam);
            famMap.put(fam.getId(), fam);
            System.out.println("famMap added fam: "+fam);
//            if (debug) System.out.println("element:" + element.getContent());
//            if (debug) System.out.println("husb:" + element.getChildText("HUSB"));
//            if (debug) System.out.println("wife:" + element.getChildText("WIFE"));
//            if (debug) System.out.println("child:" + element.getChildText("CHIL"));
//            if (debug) System.out.println("marr:" + element.getChildText("MARR"));
//            String idStr = element.getAttributeValue("ID");
//         idStr = idStr.substring(idStr.indexOf("@")+1, idStr.lastIndexOf("@"));
//         String husbIdStr = element.getChildText("HUSB");
//         String wifeIdStr = element.getChildText("WIFE");
//         if (husbIdStr != null) {
//            husbIdStr = husbIdStr.substring(husbIdStr.indexOf("@") + 1, husbIdStr.lastIndexOf("@"));
//         }
//         if (wifeIdStr != null) {
//            wifeIdStr = wifeIdStr.substring(wifeIdStr.indexOf("@") + 1, wifeIdStr.lastIndexOf("@"));
//         }
//            String husbIdStr = "";
//            String wifeIdStr = "";
//            if (element.getChild("HUSB") != null) {
//                husbIdStr = element.getChild("HUSB").getAttributeValue("REF");
//            }
//            if (element.getChild("WIFE") != null) {
//                wifeIdStr = element.getChild("WIFE").getAttributeValue("REF");
//            }
//            if (debug) System.out.println("husb=" + husbIdStr + " wife=" + wifeIdStr);
//            String marrDateText = "";
//            String marrPlaceText = "";
//            if (element.getChild("MARR") != null) {
//                marrDateText = element.getChild("MARR").getChildText("DATE");
//                marrPlaceText = element.getChild("MARR").getChildText("PLAC");
//            }
//            MyEvent marrEvent = new MyEvent(element.getChild("MARR"));
//            Fam fam = new Fam();
//            fam.setId(idStr);
//            fam.setMarriageEvent(marrEvent);
//            Individual father = (Individual) indiMap.get(husbIdStr);
//            if (father != null) {
//                fam.setFather(father);
//                father.setFamilyAsSpouse(fam);
//            }
//            Individual mother = (Individual) indiMap.get(wifeIdStr);
//            if (mother != null) {
//                fam.setMother(mother);
//                mother.setFamilyAsSpouse(fam);
//            }
//            List kids = element.getChildren("CHIL");
//            for (Iterator iterator = kids.iterator(); iterator.hasNext();) {
//                if (debug) System.out.println("child element of family " + fam);
//                Element childElement = (Element) iterator.next();
//                String childIdText = childElement.getAttributeValue("REF");
//                Individual newChild = (Individual) indiMap.get(childIdText);
//                if (newChild != null) {
//                    if (debug) System.out.println("adding child " + newChild + " to family " + fam);
//                    fam.addChild(newChild);
//                    newChild.setFamilyAsChild(fam);
//                }
//            }
//            if (debug) System.out.println("fam (" + fam + ") dad=" + fam.getFather() + " mom=" + fam.getMother() + " children=" + fam.getChildren().size());
//            tempFams.add(fam);
//            Individual indihusb = (Individual) indiMap.get(husbIdStr);
//            if (debug) System.out.println("indimap dad=" + indihusb);
//            if (indihusb != null) if (debug) System.out.println("indimap dad fam=" + indihusb.getFamilyAsSpouse());
            if (debug) System.err.println("\n *** " + i + " mem:" + rt.freeMemory() / 1024 + " Kb\n");
            if (rt.freeMemory() < 50000) {
                if (debug) System.err.println("gc");
                rt.gc();
            }

        }
//      System.out.println("setting individuals");
//      individuals = new NSMutableDictionary(new NSDictionary(indiMap.values().toArray(), indiMap.keySet().toArray()));
//      System.out.println("individuals: "+individuals.count()+"("+individuals+")");
        System.out.println("play indimap: " + indiMap.size());// + "(" + indiMap + ")");
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
            System.out.println("play setindividual:"+firstIndi);
//            assignIndividualToButton(firstIndi, individualButton);
//            setIndividual(individualButton);
            setPrimaryIndividual(firstIndi);
        }
//        final List objects = doc.getRootElement().getChildren("OBJE");
        for (int i = 0; i < multimedias.size(); i++) {
            Element obje = (Element) multimedias.get(i);
            if (obje != null) {
                System.out.println("Root multimedia object:" + obje);
                Multimedia media = new MultimediaJDOM(obje);
                multimediaMap.put(media.getId(), media);
//                System.out.println("id=" + obje.getAttributeValue("ID"));
//                System.out.println("ref=" + obje.getAttributeValue("REF"));
//                System.out.println("form=" + obje.getChildText("FORM"));
//                System.out.println("title=" + obje.getChildText("TITL"));
//                System.out.println("file=" + obje.getChildText("FILE"));
//                System.out.println("blob=" + obje.getChildText("BLOB"));
//                Element blob = obje.getChild("BLOB");
//                StringBuffer buf = new StringBuffer(blob.getText());
//                List conts = blob.getChildren("CONT");
//                for (int j = 0; j < conts.size(); j++) {
//                    Element cont = (Element) conts.get(j);
//                    buf.append(cont.getText());
//                }
//                System.out.println("content=" + buf.toString());
//                System.out.println("buflen=" + buf.toString().length());
//                byte[] raw = Base64.decode(buf.toString());
//                System.out.println("decoded=" + raw);
//                NSImageView nsiv = new NSImageView(new NSRect(0, 0, 100, 100));
//                nsiv.setImage(new NSImage(new NSData(raw)));
//                individualButton.superview().addSubview(nsiv);
//                nsiv.display();
            }
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
      System.out.println("Time to parse: "+(end - start)/1000+" seconds.");
      //HashMap famMap = new HashMap();
      HashMap noteMap = new HashMap();
      List notes = doc.getRootElement().getChildren("Note");
      for (int i = 0; i < notes.size(); i++) {
         Element element = (Element) notes.get(i);
         System.out.println("element:" + element.getContent());
         Note note = new MyNote(element);
//         List concs = element.getChildren();
//         for (int j=0; j < concs.size(); j++) {
//            Element el = (Element) concs.get(j);
//            System.out.println(el.getName()+": <"+el.getText()+">");
//            if (!el.getText().equals(el.getTextTrim())) System.out.println(el.getName()+" trim: <"+el.getTextTrim()+">");
//            if (!el.getTextTrim().equals(el.getTextNormalize())) System.out.println(el.getName()+" normalize: <"+el.getTextNormalize()+">");
//         }
         noteMap.put(note.getId(), note);
      }
      System.out.println("noteMap: "+noteMap.size());
      List indis = doc.getRootElement().getChildren("Individual");
      for (int i = 0; i < indis.size(); i++) {
         Element element = (Element) indis.get(i);
         System.out.println("element:" + element.getContent());
         System.out.println("name:" + element.getChildText("NAME"));
         //System.out.println("bday:" + element.getChild("BIRT").getChildText("DATE"));
         Indi indi = new Indi(element);
         Element noteEl = element.getChild("NOTE");
         if (noteEl != null) {
            String idStr = noteEl.getText();
            System.out.println("idStr="+idStr);
            if (idStr != null && idStr.startsWith("@")) {
               idStr = idStr.substring(idStr.indexOf("@")+1, idStr.lastIndexOf("@"));
               indi.setNote((Note)noteMap.get(idStr));
               System.out.println("added note "+idStr+"("+noteMap.get(idStr)+") to indi "+indi.getFullName());
            } else {
               indi.setNote(new MyNote(noteEl));
            }
         }
         indiMap.put(indi.getId(), indi);
      }
      List fams = doc.getRootElement().getChildren("Family");
      for (int i = 0; i < fams.size(); i++) {
         Element element = (Element) fams.get(i);
         System.out.println("element:" + element.getContent());
         System.out.println("husb:" + element.getChildText("HUSB"));
         System.out.println("wife:" + element.getChildText("WIFE"));
         System.out.println("child:" + element.getChildText("CHIL"));
         System.out.println("marr:" + element.getChildText("MARR"));
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
         System.out.println("husb=" + husbIdStr + " wife=" + wifeIdStr);
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
            System.out.println("child element of family " + fam);
            Element childElement = (Element) iterator.next();
            String childIdText = childElement.getText();
            childIdText = childIdText.substring(childIdText.indexOf("@") + 1, childIdText.lastIndexOf("@"));
            Individual newChild = (Individual) indiMap.get(childIdText);
            if (newChild != null) {
               System.out.println("adding child " + newChild + " to family " + fam);
               fam.addChild(newChild);
               newChild.setFamilyAsChild(fam);
            }
         }
         System.out.println("fam (" + fam + ") dad=" + fam.getFather() + " mom=" + fam.getMother() + " children=" + fam.getChildren().size());
         tempFams.add(fam);
         Individual indihusb = (Individual) indiMap.get(husbIdStr);
         System.out.println("indimap dad=" + indihusb);
         if (indihusb != null) System.out.println("indimap dad fam=" + indihusb.getFamilyAsSpouse());
      }
//      System.out.println("setting individuals");
//      individuals = new NSMutableDictionary(new NSDictionary(indiMap.values().toArray(), indiMap.keySet().toArray()));
//      System.out.println("individuals: "+individuals.count()+"("+individuals+")");
      System.out.println("play indimap: " + indiMap.size());// + "(" + indiMap + ")");
      if (individualButton != null) {
         System.out.println("play setindividual");
         assignIndividualToButton((Individual) indiMap.values().toArray()[0], individualButton);
         setIndividual(individualButton);
      }
   }
*/
    public NSData dataRepresentationOfType(String aType) {
        // Insert code here to create and return the data for your document.
        System.out.println("MyDocument.dataRepresentationOfType():"+aType);
        return new NSAttributedString("some data goes here").RTFFromRange(new NSRange(0, 15), new NSDictionary());
    }

    public boolean loadDataRepresentation(NSData data, String aType) {
        // Insert code here to read your document from the given data.
        System.out.println("MyDocument.loadDataRepresentation():"+aType);
        System.out.println("load data:" + aType + ":" + new NSStringReference(data, NSStringReference.ASCIIStringEncoding));
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
        individuals.setObjectForKey(indiv, button.toString());
    }

    public int numberOfRowsInTableView(NSTableView nsTableView) {
    	System.out.println("MyDocument.numberOfRowsInTableView():"+nsTableView.tag());
        if (nsTableView.tag() == 1) {
            if (primaryIndividual.getFamilyAsSpouse() != null) {
                int numChildren = primaryIndividual.getFamilyAsSpouse().getChildren().size();
                System.out.println("numberOfRowsInTableView children: "+numChildren);
				return numChildren;
            }
        } else if (nsTableView.tag() == 2) {
			int numSpouses = primaryIndividual.getSpouseList().size();
			System.out.println("numberOfRowsInTableView spouses: "+numSpouses);
            return numSpouses;
        }
        return 0;
    }

    public Object tableViewObjectValueForLocation(NSTableView nsTableView, NSTableColumn nsTableColumn, int i) {
        try {
//			System.out.println("nsTableColumn=" + nsTableColumn.identifier()+"; i="+i+"; tag="+nsTableView.tag());
			if (nsTableView.tag() == 1) {
			    if (nsTableColumn.identifier() != null && nsTableColumn.identifier().equals("num")) {
			        nsTableColumn.setWidth(5.0f);
			        return String.valueOf(i + 1);
			    }
			    Individual individual = (Individual) primaryIndividual.getFamilyAsSpouse().getChildren().get(i);
			    individuals.setObjectForKey(individual, "child" + i);
			    return individual.getFullName();
			} else if (nsTableView.tag() == 2) {
			    Individual individual = (Individual) primaryIndividual.getSpouseList().get(i);
			    individuals.setObjectForKey(individual, "spouse" + i);
			    return individual.getFullName();
			}
			return "Unknown";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "An Error Has Occurred";
		}
    }

    public void printShowingPrintPanel(boolean showPanels) {
        System.out.println("printshowingprintpanel:" + showPanels);
        // Obtain a custom view that will be printed
        printInfo().setTopMargin(36);
        printInfo().setBottomMargin(36);
        printInfo().setLeftMargin(72);
        printInfo().setRightMargin(36);
        setPrintInfo(printInfo());
//       NSView printView = printableView;

        // Construct the print operation and setup Print panel
        NSPrintOperation op = NSPrintOperation.printOperationWithView(printableView, printInfo());
        System.out.println("papersize: " + printInfo().paperSize());
        System.out.println("left margin: " + printInfo().leftMargin());
        System.out.println("right margin: " + printInfo().rightMargin());
        System.out.println("top margin: " + printInfo().topMargin());
        System.out.println("bottom margin: " + printInfo().bottomMargin());
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
        System.out.println("___ save()");
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
//		System.out.println("root="+root);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        saveDocument(this);
        System.out.println("MyDocument.save() end, filename="+fileName());
    }



    public void importFile(Object sender) { /* IBAction */
        System.out.println("importFile: " + sender);
//        NSApplication nsapp = NSApplication.sharedApplication();
//        nsapp.beginSheet(importWindow, mainWindow, this, null, null);
        NSOpenPanel panel = NSOpenPanel.openPanel();
//panther only?        panel.setMessage("Please select a GEDCOM file to import into this MacPAF file.");
        panel.beginSheetForDirectory(null, null, new NSArray(new Object[]{"GED"}), mainWindow,
                this, new NSSelector("openPanelDidEnd", new Class[]{NSOpenPanel.class, int.class, Object.class}), null);
    }

    public void openPanelDidEnd(NSOpenPanel sheet, int returnCode, Object contextInfo) {
        if (returnCode == NSPanel.OKButton) {
            System.out.println("import filename:"+sheet.filename());
            importGEDCOM(new File(sheet.filename()));
        }
    }

    private void importGEDCOM(File importFile) {
    	System.out.println("MyDocument.importGEDCOM():"+importFile);
    	playWithXML(importFile);
    }

    public void exportFile(Object sender) { /* IBAction */
        System.out.println("exportFile: " + sender);
//        save();
        NSSavePanel panel = NSSavePanel.savePanel();
        panel.setCanSelectHiddenExtension(true);
        panel.setExtensionHidden(false);
//panther only?        panel.setMessage("Choose the name and location for the exported GEDCOM file.\n\nThe file name should end with .ged");
//        panel.setNameFieldLabel("Name Field Label:");
//        panel.setPrompt("Prompt:");
        panel.setRequiredFileType("ged");
//        panel.setTitle("Title");
        panel.beginSheetForDirectory(null, null, mainWindow, this, new NSSelector("savePanelDidEndReturnCode", new Class[]{NSSavePanel.class, int.class, Object.class}), null);
    }

    public void savePanelDidEndReturnCode (NSSavePanel sheet, int returnCode, Object contextInfo) {
      System.out.println("MyDocument.savePanelDidEndReturnCode(sheet, returnCode, contextInfo):"+sheet+":"+returnCode+":"+contextInfo);
        if (returnCode == NSPanel.OKButton) {
            System.out.println("export filename:"+sheet.filename());
            try {
                XMLTest.outputWithKay(doc, new FileOutputStream(sheet.filename()));
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        } else if (returnCode == NSPanel.CancelButton) {
        System.out.println("save panel cancelled, sheet filename="+sheet.filename()+", doc filename="+fileName());
        if (fileName() == null || fileName().length() == 0) {
          System.out.println("cancel with null filename, should I close the document?");
//          close();
        }
        }
    }

	public void windowDidBecomeMain(NSNotification aNotification) {
		System.out.println("MyDocument.windowDidBecomeMain()");
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
    System.out.println("MyDocument.prepareSavePanel(nSSavePanel):"+nSSavePanel);
    nSSavePanel.setDelegate(this);
    return true;
  }

  public boolean panelIsValidFilename(Object sender,
                                    String filename) {
  System.out.println("MyDocument.panelIsValidFilename(sender, filename):"+sender+":"+filename);
	  return true;
}

}
