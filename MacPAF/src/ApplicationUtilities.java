//
//  ApplicationUtilities.java
//  MacPAFTest
//
//  Created by Logan Allred on Sat Mar 22 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import org.apache.log4j.Category;
import com.apple.cocoa.application.NSApplication;
import com.apple.cocoa.application.NSDocumentController;
import com.apple.cocoa.application.NSPanel;
import com.apple.cocoa.application.NSProgressIndicator;
import com.apple.cocoa.foundation.NSNotification;
import com.apple.cocoa.foundation.NSUserDefaults;

public class ApplicationUtilities {
  public NSPanel splashScreen; /* IBOutlet */
  public NSProgressIndicator progress; /* IBOutlet */
  public static boolean didFinish = false;
  private static final Category log = Category.getInstance(ApplicationUtilities.class.getName());

  public void applicationWillFinishLaunching(NSNotification aNotification) {
	log.debug("applicationWillFinishLaunching:" + aNotification);
	if (progress != null) {
	  progress.startAnimation(this);
	}
  }

  public void applicationDidFinishLaunching(NSNotification aNotification) {
	log.debug("applicationDidFinishLaunching:" + aNotification + " isreleased=" + splashScreen.isReleasedWhenClosed());
	// open last opened document
	String lastOpenedDocument = NSUserDefaults.standardUserDefaults().stringForKey(
		"com.redbugz.macpaf.lastOpenedDocument");
	if (lastOpenedDocument != null) {
	  NSDocumentController.sharedDocumentController().openDocumentWithContentsOfFile(lastOpenedDocument, true);
	}
	else {
	  NSDocumentController.sharedDocumentController().openUntitledDocumentOfType(MyDocument.MACPAF, true);
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
