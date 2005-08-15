/* FamilyListController */

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;

public class FamilyListController extends NSWindowController {

    public NSTextField familyCountText; /* IBOutlet */
    public FamilyList familyList; /* IBOutlet */
    public NSTableView familyListTableView; /* IBOutlet */
    public NSTextField searchField; /* IBOutlet */

	public FamilyListController(FamilyList dataSource) {
		super("FamilyListWindow");
		// TODO Auto-generated constructor stub
//		NSWindowController test = new NSWindowController("FamilyListWindow");
//		test.showWindow(this);
		familyList = dataSource;

	}

	public void search(Object sender) { /* IBAction */
    }

    public void selectFamily(Object sender) { /* IBAction */
    }

	public void windowDidLoad() {
		super.windowDidLoad();
		  familyListTableView.setDataSource(familyList);
//		  familyListTableView.setDelegate(familyList);
		familyCountText.setStringValue("Number of Families: "+familyList.size());
	}



}
