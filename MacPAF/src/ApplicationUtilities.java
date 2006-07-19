//
//  ApplicationUtilities.java
//  MacPAFTest
//
//  Created by Logan Allred on Sat Mar 22 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.io.*;

import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;

public class ApplicationUtilities {
  public NSPanel splashScreen; /* IBOutlet */
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
  }

  public void applicationDidFinishLaunching(NSNotification aNotification) {
	log.debug("applicationDidFinishLaunching:" + aNotification + " isreleased=" + splashScreen.isReleasedWhenClosed());
	try {
	  // open last opened document
	  String lastOpenedDocument = NSUserDefaults.standardUserDefaults().stringForKey(
		  "com.redbugz.macpaf.lastOpenedDocument");
	  log.debug("lastOpenedDocument=" + lastOpenedDocument);
	  if (lastOpenedDocument != null) {log.debug(" exists:"+new File(lastOpenedDocument).exists());}
	  if (lastOpenedDocument != null && new File(lastOpenedDocument).exists()) {
		NSDocumentController.sharedDocumentController().openDocumentWithContentsOfFile(lastOpenedDocument, true);
	  }
	  else {
		NSDocumentController.sharedDocumentController().openUntitledDocumentOfType(MyDocument.MACPAF, true);
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
	log.debug(
		"ApplicationUtilities.applicationShouldOpenUntitledFile():" + sender);
	log.debug("didfinish=" + didFinish);
//		if (!didFinish) {
//			((NSApplication.Delegate)sender.delegate()).applicationOpenFile(sender, "/Projects/MacPAFTest/startup.macpaf");
//		}
	return false; // didFinish;
  }

//   public boolean applicationOpenFile(NSApplication theApplication, String filename) {
//   	log.debug("ApplicationUtilities.applicationOpenFile():"+filename);
//   	return true;
//   }
  }
