/* IndividualListController */

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;

public class IndividualListController extends NSWindowController {

    public NSTextField individualCountText; /* IBOutlet */
    public IndividualList individualList; /* IBOutlet */
    public NSTableView individualListTableView; /* IBOutlet */
    public NSTextField searchField; /* IBOutlet */

    public void search(Object sender) { /* IBAction */
    }

    public void selectIndividual(Object sender) { /* IBAction */
    }

	public void windowDidLoad() {
		super.windowDidLoad();
		individualCountText.setStringValue("Number of Individuals: "+individualList.size());
	}

	public IndividualListController() {
		super("IndividualListWindow");
		// TODO Auto-generated constructor stub	individualListWindow.setDelegate(this);
//		individualListWindow.makeKeyAndOrderFront(sender);
		  individualListTableView.setDataSource(individualList);
		  individualListTableView.setDelegate(individualList);

	}

}
