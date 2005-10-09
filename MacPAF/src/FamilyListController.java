/* FamilyListController */

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Family;

public class FamilyListController extends NSWindowController {
	private static Logger log = Logger.getLogger(FamilyListController.class);

    public NSTextField familyCountText; /* IBOutlet */
    public FamilyList familyList; /* IBOutlet */
    public NSTableView familyListTableView; /* IBOutlet */
    public NSTextField searchField; /* IBOutlet */
    public FamilyDetailController familyDetailController; /* IBOutlet */

	public FamilyListController(FamilyList dataSource) {
		super("FamilyListWindow");
		familyList = dataSource;
	}

	public void search(Object sender) { /* IBAction */
		log.debug("search for "+searchField.stringValue());
    }

    public void selectFamily(Object sender) { /* IBAction */
		log.debug("selectFamily: "+familyListTableView.selectedRow());
		((MyDocument) document()).setPrimaryIndividual(familyList.getSelectedFamily().getFather());
		((MyDocument) document()).mainWindow.makeKeyAndOrderFront(this);
    }

	public void windowDidLoad() {
		super.windowDidLoad();
		  familyListTableView.setDataSource(familyList);
			familyListTableView.setDelegate(this);
		familyCountText.setStringValue("Number of Families: "+familyList.size());
	}

	public String windowTitleForDocumentDisplayName(String displayName) {
		return displayName + ": "+"Family List";
	}
	
	  public void tableViewSelectionDidChange(NSNotification aNotification) {
			log.debug("FamilyListController tableViewSelectionDidChange():" + aNotification);
			familyList.tableViewSelectionDidChange(aNotification);
			familyDetailController.setFamily(familyList.getSelectedFamily());
		  }



}
