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
    		((SortableFilteredTableViewDataSource) individualListTableView.dataSource()).setFilterString(searchField.stringValue());
    		individualListTableView.reloadData();
    }

    public void selectIndividual(Object sender) { /* IBAction */
	    	log.debug("selectIndividual: "+individualListTableView.selectedRow());
	    	if (individualListTableView.selectedRow() >= 0) {
	    		MyDocument currentDocument = (MyDocument) NSDocumentController.sharedDocumentController().currentDocument();
	    		if (currentDocument == null) {
	    			currentDocument = (MyDocument) ((NSWindowController) window().windowController()).document();
	    		}
	    		System.out.println("IndividualListController.selectIndividual() currdoc:"+currentDocument);
	//  		System.out.println("IndividualListController.selectIndividual() individuallist:"+individualList);
	//  		System.out.println("IndividualListController.selectIndividual() selected indi:"+individualList.getSelectedIndividual());
	    		currentDocument.setPrimaryIndividual(individualList.getSelectedIndividual()); // todo fix NPE on indiList
	    		currentDocument.mainWindow.makeKeyAndOrderFront(this);
	    	}
    }

	public void windowDidLoad() {
		super.windowDidLoad();
		System.out.println("IndividualListController.windowdidload() individuallist:"+individualList);
		individualListTableView.setDataSource(new SortableFilteredTableViewDataSource(individualListTableView, individualList));
		individualListTableView.setDelegate(this);
		refreshData();
	}
	
	public String windowTitleForDocumentDisplayName(String displayName) {
		return displayName + ": "+"Individual List";
	}

	  
	  public void tableViewSelectionDidChange(NSNotification aNotification) {
		  log.debug("ILC.tableViewSelectionDidChange():"+aNotification);
		  individualList.tableViewSelectionDidChange(aNotification);
		  individualDetailController.setIndividual(individualList.getSelectedIndividual());
	  }

	public void refreshData() {
		individualListTableView.reloadData();
		individualCountText.setStringValue("Number of Individuals: "+individualList.size());
	}

}
