//
//  ApplicationUtilities.java
//  MacPAFTest
//
//  Created by Logan Allred on Sat Mar 22 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.maf.util.CocoaUtils;

public class ApplicationUtilities extends NSObject {
  private static final String DEFAULTS_KEY_OPENED_DOCUMENTS = "com.redbugz.maf.OpenedDocuments";
  public NSWindow splashScreen; /* IBOutlet */
  public NSProgressIndicator progress; /* IBOutlet */
  public static boolean didFinish = false;
  private static final Logger log = Logger.getLogger(ApplicationUtilities.class);

  public void applicationWillFinishLaunching(NSNotification aNotification) {
	log.debug("applicationWillFinishLaunching:" + aNotification);
	  String javaVersion = System.getProperty("java.version");
	log.debug("java.version:"+javaVersion);
	  if (javaVersion.startsWith("1.3")) {
		  MyDocument.showUserErrorMessage("System Not Supported", "MacPAF requires that you update your system. It requires Mac OS X 10.2.8 with Java Update 1.4.1, which can be obtained here:\nhttp://www.apple.com/support/downloads/java141update1formacosx.html");
		  NSApplication.sharedApplication().terminate(this);
	  }
	if (progress != null) {
	  progress.startAnimation(this);
	}
	  log.debug("splash:"+splashScreen);
	  if (splashScreen != null) {
		  splashScreen.center();
	  }
  }

  public void applicationDidFinishLaunching(NSNotification aNotification) {
	log.debug("applicationDidFinishLaunching:" + aNotification + " splashisreleasedwhenclosed=" + splashScreen.isReleasedWhenClosed());
	try {
	  // open last opened document
	  NSArray lastOpenedDocuments = NSUserDefaults.standardUserDefaults().arrayForKey(DEFAULTS_KEY_OPENED_DOCUMENTS);
	  log.debug("OpenedDocuments=" + lastOpenedDocuments);
	  if (lastOpenedDocuments == null || lastOpenedDocuments.count() == 0) {
			NSDocumentController.sharedDocumentController().openUntitledDocumentOfType(MyDocument.MACPAF_DOCUMENT_TYPE, true);		  
	  } else {
		  // open documents that were open last time the user quit if there are any
		  for (Enumeration documentsToOpen = lastOpenedDocuments.objectEnumerator(); documentsToOpen.hasMoreElements();) {
			String documentPath = (String) documentsToOpen.nextElement();
			log.debug(documentPath+" exists:"+new File(documentPath).exists());
			if (documentPath != null && new File(documentPath).exists()) {
				NSDocumentController.sharedDocumentController().openDocumentWithContentsOfFile(documentPath, true);
			}
		  }
	  }
	}
	catch (Exception ex) {
	  ex.printStackTrace();
	}
	  // close splash screen
	  splashScreen.setReleasedWhenClosed(true);
	  splashScreen.close();
	  splashScreen = null;
	  didFinish = true;
  }

  public boolean applicationShouldOpenUntitledFile(NSApplication sender) {
//	log.debug("ApplicationUtilities.applicationShouldOpenUntitledFile():" + sender);
//	log.debug("didfinish=" + didFinish);
//		if (!didFinish) {
//			((NSApplication.Delegate)sender.delegate()).applicationOpenFile(sender, "/Projects/MacPAFTest/startup.macpaf");
//		}
	return false; // didFinish;
  }
  
  public void applicationWillTerminate(NSNotification aNotification) {
	  log.debug("ApplicationUtilities.applicationWillTerminate():"+aNotification);
	  // save opened documents as preference
	  log.debug("setting lastOpenedDocuments to: " + NSDocumentController.sharedDocumentController().documents().valueForKey("fileName"));
	  NSUserDefaults.standardUserDefaults().setObjectForKey(NSDocumentController.sharedDocumentController().documents().valueForKey("fileName"), DEFAULTS_KEY_OPENED_DOCUMENTS);

  }
  
	public void terminate(Object sender) { /* IBAction */
		log.debug("MyDocument.terminate():"+sender+NSDocumentController.sharedDocumentController().documents());
		// close document loading sheets before closing
//		CocoaUtils.makeObjectsPerformSelector(NSDocumentController.sharedDocumentController().documents(), CocoaUtils.CANCEL_SHEETS_SELECTOR, this);
		NSApplication.sharedApplication().terminate(sender);
	}

}
