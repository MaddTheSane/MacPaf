/* IndividualListController */

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;

public class IndividualListController extends NSWindowController {
	private static Logger log = Logger.getLogger(IndividualListController.class);

    public NSTextField individualCountText; /* IBOutlet */
    public IndividualList individualList; /* IBOutlet */
    public NSTableView individualListTableView; /* IBOutlet */
    public NSTextField searchField; /* IBOutlet */
    public IndividualDetailController individualDetailController; /* IBOutlet */

	public IndividualListController(IndividualList dataSource) {
		super("IndividualListWindow");
		  individualList = dataSource;
	}

    public void search(Object sender) { /* IBAction */
    		log.debug("search for "+searchField.stringValue());
    }

    public void selectIndividual(Object sender) { /* IBAction */
    		log.debug("selectIndividual: "+individualListTableView.selectedRow());
    }

	public void windowDidLoad() {
		super.windowDidLoad();
		individualListTableView.setDataSource(individualList);
		individualListTableView.setDelegate(this);
		individualCountText.setStringValue("Number of Individuals: "+individualList.size());
	}
	
	public String windowTitleForDocumentDisplayName(String displayName) {
		return displayName + ": "+"Individual List";
	}

	  
	  public void tableViewSelectionDidChange(NSNotification aNotification) {
		  log.debug("ILC.tableViewSelectionDidChange():"+aNotification);
		  individualList.tableViewSelectionDidChange(aNotification);
		  individualDetailController.setIndividual(individualList.getSelectedIndividual());
	  }

}
