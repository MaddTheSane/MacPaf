//
//  ApplicationUtilities.java
//  MacPAFTest
//
//  Created by Logan Allred on Sat Mar 22 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

import com.apple.cocoa.application.NSApplication;
import com.apple.cocoa.application.NSDocumentController;
import com.apple.cocoa.application.NSPanel;
import com.apple.cocoa.application.NSProgressIndicator;
import com.apple.cocoa.foundation.*;


public class ApplicationUtilities {
   public NSPanel splashScreen; /* IBOutlet */
   public NSProgressIndicator progress; /* IBOutlet */
   public static boolean didFinish = false;

   public void applicationWillFinishLaunching(NSNotification aNotification) {
      System.out.println("applicationWillFinishLaunching:" + aNotification);
      progress.startAnimation(this);
   }

   public void applicationDidFinishLaunching(NSNotification aNotification) {
      System.out.println("applicationDidFinishLaunching:" + aNotification+ " isreleased="+splashScreen.isReleasedWhenClosed());
      // open last opened document
      String lastOpenedDocument = NSUserDefaults.standardUserDefaults().stringForKey("com.redbugz.macpaf.lastOpenedDocument");
      if (lastOpenedDocument != null) {
      NSDocumentController.sharedDocumentController().openDocumentWithContentsOfFile(lastOpenedDocument, true);
      } else {
      NSDocumentController.sharedDocumentController().openUntitledDocumentOfType(MyDocument.MACPAF, true);
      }
      // close splash screen
      splashScreen.setReleasedWhenClosed(true);
      splashScreen.close();
      splashScreen = null;
      didFinish = true;
   }
   
   public boolean applicationShouldOpenUntitledFile(NSApplication sender) {
   	System.out.println(
		"ApplicationUtilities.applicationShouldOpenUntitledFile():"+sender);
		System.out.println("didfinish="+didFinish);
//		if (!didFinish) {
//			((NSApplication.Delegate)sender.delegate()).applicationOpenFile(sender, "/Projects/MacPAFTest/startup.macpaf");
//		}
   	 return false;// didFinish;
   }
   
//   public boolean applicationOpenFile(NSApplication theApplication, String filename) {
//   	System.out.println("ApplicationUtilities.applicationOpenFile():"+filename);
//   	return true;
//   }
}
