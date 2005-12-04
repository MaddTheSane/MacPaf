/* FamilyListController */

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Individual;

public class FamilyListController extends NSWindowController {
	private static Logger log = Logger.getLogger(FamilyListController.class);

    public NSTextField familyCountText; /* IBOutlet */
    public FamilyList familyList; /* IBOutlet */
    public NSTableView familyListTableView; /* IBOutlet */
    public NSTextField searchField; /* IBOutlet */
    public FamilyDetailController familyDetailController; /* IBOutlet */

	private SortableFilteredTableViewDataSource sortableFilteredTableViewDataSource;

	public FamilyListController(FamilyList dataSource) {
		super("FamilyListWindow");
		familyList = dataSource;
	}
	
	public void search(Object sender) { /* IBAction */
		log.debug("search for "+searchField.stringValue());
		sortableFilteredTableViewDataSource.setFilterString(searchField.stringValue());
		refreshData();
	}

	public void selectFamily(Object sender) { /* IBAction */
		log.debug("selectFamily: "+familyListTableView.selectedRow());
		if (familyListTableView.selectedRow() >= 0) {
			Family selectedFamily = familyList.getSelectedFamily();
			if (!(selectedFamily instanceof Family.UnknownFamily)) {
				Individual newPrimaryIndiv = selectedFamily.getFather();
				if (newPrimaryIndiv instanceof Individual.UnknownIndividual) {
					newPrimaryIndiv = selectedFamily.getMother();
				}
				log.debug("selectFamily Document:"+document());
				((MyDocument) document()).setPrimaryIndividual(newPrimaryIndiv);
				((MyDocument) document()).mainWindow.makeKeyAndOrderFront(this);
			}
		}
	}

	public void windowDidLoad() {
		super.windowDidLoad();
		sortableFilteredTableViewDataSource = new SortableFilteredTableViewDataSource(familyListTableView, familyList);
		familyListTableView.setDataSource(sortableFilteredTableViewDataSource);
		familyListTableView.setDelegate(this);
		familyList.setDataSource(sortableFilteredTableViewDataSource);
		searchField.setDelegate(this);
		refreshData();
		if (familyListTableView.numberOfRows() > 0) {
			tableViewSelectionDidChange(new NSNotification(NSTableView.TableViewSelectionDidChangeNotification, familyListTableView));
		}
//		refreshSelection(familyList.getSelectedFamily());
	}

	public String windowTitleForDocumentDisplayName(String displayName) {
		return displayName + ": "+"Family List";
	}
	
	  public void tableViewSelectionDidChange(NSNotification aNotification) {
			log.debug("FamilyListController tableViewSelectionDidChange():" + aNotification);
			familyList.tableViewSelectionDidChange(aNotification);
			familyDetailController.setFamily(familyList.getSelectedFamily());
		  }

	  public void controlTextDidChange(NSNotification aNotification) {
		System.out.println("FamilyListController.controlTextDidChange()");
		System.out.println(aNotification);
		System.out.println(aNotification.valueForKey("object"));
		System.out.println(aNotification.object());
		NSTextField field = (NSTextField) aNotification.object();
		// hold on to selection so we can find it in the filtered list
		Family selectedFamily = familyList.getSelectedFamily();
		System.out.println("search value:"+field.stringValue());
		sortableFilteredTableViewDataSource.setFilterString(searchField.stringValue());
		refreshData();
		refreshSelection(selectedFamily);
	  }

	private void refreshSelection(Family selectedFamily) {
		// TODO implement this to keep selection if still in filtered list
		// for now, just change selection to update selected family view
//		int currentSelectedIndex = sortableFilteredTableViewDataSource.getCurrentSelectedIndex();
		if (familyListTableView.numberOfRows() > 0) {
			tableViewSelectionDidChange(new NSNotification(NSTableView.TableViewSelectionDidChangeNotification, familyListTableView));
		}
	}

	public void refreshData() {
		familyListTableView.reloadData();
		familyCountText.setStringValue("Displaying "+familyListTableView.numberOfRows()+" out of "+familyList.size()+" Families");
	}

	  // SDMovingRowsProtocol
//	- (unsigned int)dragReorderingMask:(int)forColumn;
	public int dragReorderingMaskForColumn(int column) {
		return 0; // drag always2
	}
	  //- (BOOL)tableView:(NSTableView *)tv didDepositRow:(int)rowToMove at:(int)newPosition;
	  public boolean tableViewDidDepositRowToMoveAt(NSTableView nSTableView, int rowToMove, int newPosition) {
		  log.debug("FamilyList.tableViewDidDepositRowToMoveAt()");
		  return true;
	  }

//	This gives you a chance to decline to drop particular rows on other particular
//	row. Return YES if you don't care
//	- (BOOL) tableView:(SDTableView *)tableView draggingRow:(int)draggedRow overRow:(int) targetRow;
	  public boolean tableViewDraggingRowOverRow(NSTableView tableView, int draggedRow, int targetRow) {
		  log.debug("FamilyList.tableViewDraggingRowOverRow()");
		  return true;
	  }
}
