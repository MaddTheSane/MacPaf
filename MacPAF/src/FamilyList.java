/* FamilyList */

import java.util.ArrayList;

import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.foundation.NSNotification;
import com.redbugz.macpaf.*;

public class FamilyList {

   public MyDocument document; /* IBOutlet */
   private Family selectedFamily;// = null;
   private ArrayList families;// = new ArrayList();
   private boolean reload = false;

   public FamilyList() {
   	if (families == null) {
   		families = new ArrayList();
   		selectedFamily = null;
   	}
   }
   
   public Family getSelectedFamily() {
      return selectedFamily;
   }

   public int numberOfRowsInTableView(NSTableView nsTableView) {
   	if (reload) {
   		reload = false;
   		nsTableView.reloadData();
   	}
      return families.size();
   }

   public Object tableViewObjectValueForLocation(NSTableView nsTableView, NSTableColumn nsTableColumn, int i) {
      try {
         Family family = (Family) families.get(i);
         if (nsTableColumn.headerCell().stringValue().equals("ID")) {
            return family.getId();
         } else if (nsTableColumn.headerCell().stringValue().equals("Husband")) {
            return family.getFather().getFullName();
         } else if (nsTableColumn.headerCell().stringValue().equals("Wife")) {
            return family.getMother().getFullName();
         } else if (nsTableColumn.headerCell().stringValue().equals("Marriage Date")) {
            return family.getSealingToSpouse().getDateString();
         } else if (nsTableColumn.headerCell().stringValue().equals("Children")) {
            return String.valueOf(family.getChildren().size());
         }
         System.out.println("FamilyList unidentified column:" + nsTableColumn);
      } catch (Exception e) {
         e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      }
      return null;
   }

   public void add(Family family) {
      families.add(family);
      reload = true;
   }

   public void tableViewSelectionDidChange(NSNotification aNotification) {
      System.out.println("FamilyList tableViewSelectionDidChange():"+aNotification);
      NSTableView nsTableView = (NSTableView) aNotification.object();
      selectedFamily = (Family) families.get(nsTableView.selectedRow());
      nsTableView.reloadData();
      document.setIndividual(this);
   }
}
