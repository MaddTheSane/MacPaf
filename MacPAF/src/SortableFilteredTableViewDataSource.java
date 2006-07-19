import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;

public class SortableFilteredTableViewDataSource extends
		FilteredTableViewDataSource {
	private static Logger log = Logger.getLogger(SortableFilteredTableViewDataSource.class);

	public SortableFilteredTableViewDataSource(NSTableView view) {
		super(view);
	}

	public SortableFilteredTableViewDataSource(NSTableView view, Object dataSource) {
		super(view, dataSource);
	}

	private NSArray sortedIndices = new NSArray();

	private int getSortedIndex(int int2) {
//		log.debug("sortedIndices("+int2+") sortedIndices size:"+sortedIndices.count());
		if (isSorted()) {
			return ((Integer)sortedIndices.objectAtIndex(int2)).intValue();
		}
		return int2;
	}
	
	public int getCurrentSelectedIndex() {
		if (isSorted()) {
			int selectedRowIndex = tableView.selectedRow();
			if (selectedRowIndex >= 0) {
				return getSortedIndex(selectedRowIndex);
			}
			return selectedRowIndex;
		}
		return super.getCurrentSelectedIndex();
	}
	
	private boolean isSorted() {
		return sortedIndices.count() > 0;
	}

}
