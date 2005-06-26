/* SortableTableViewController */

import java.net.URL;

import com.apple.cocoa.application.NSDraggingInfo;
import com.apple.cocoa.application.NSPasteboard;
import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.application.NSTableView.DataSource;
import com.apple.cocoa.foundation.NSArray;
import com.apple.cocoa.foundation.NSIndexSet;
import com.apple.cocoa.foundation.NSObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RedBugz Software</p>
 * @author Logan Allred
 * @version 1.0
 */

public class SortableTableViewController extends NSObject implements NSTableView.DataSource {
  public SortableTableViewController() {
  }

    public NSTableView tableView; /* IBOutlet */
  /**
   * numberOfRowsInTableView
   *
   * @param nSTableView NSTableView
   * @return int
   */
  public int numberOfRowsInTableView(NSTableView nSTableView) {
	return dataSource().numberOfRowsInTableView(nSTableView);
  }

  /**
   * tableViewObjectValueForLocation
   *
   * @param nSTableView NSTableView
   * @param nSTableColumn NSTableColumn
   * @param int2 int
   * @return Object
   */
  public Object tableViewObjectValueForLocation(NSTableView nSTableView, NSTableColumn nSTableColumn, int int2) {
	return dataSource().tableViewObjectValueForLocation(nSTableView, nSTableColumn, int2);
  }

  /**
   * tableViewSetObjectValueForLocation
   *
   * @param nSTableView NSTableView
   * @param object Object
   * @param nSTableColumn NSTableColumn
   * @param int3 int
   */
  public void tableViewSetObjectValueForLocation(NSTableView nSTableView, Object object, NSTableColumn nSTableColumn,
												 int int3) {
	dataSource().tableViewSetObjectValueForLocation(nSTableView, object, nSTableColumn, int3);
  }

  /**
   * tableViewSortDescriptorsDidChange
   *
   * @param nSTableView NSTableView
   * @param nSArray NSArray
   */
  public void tableViewSortDescriptorsDidChange(NSTableView nSTableView, NSArray nSArray) {
	dataSource().tableViewSortDescriptorsDidChange(nSTableView, nSArray);
  }

  /**
   * tableViewWriteRowsToPasteboard
   *
   * @param nSTableView NSTableView
   * @param nSArray NSArray
   * @param nSPasteboard NSPasteboard
   * @return boolean
   */
  public boolean tableViewWriteRowsToPasteboard(NSTableView nSTableView, NSArray nSArray, NSPasteboard nSPasteboard) {
	return dataSource().tableViewWriteRowsToPasteboard(nSTableView, nSArray, nSPasteboard);
  }

  /**
   * tableViewValidateDrop
   *
   * @param nSTableView NSTableView
   * @param nSDraggingInfo NSDraggingInfo
   * @param int2 int
   * @param int3 int
   * @return int
   */
  public int tableViewValidateDrop(NSTableView nSTableView, NSDraggingInfo nSDraggingInfo, int int2, int int3) {
	return dataSource().tableViewValidateDrop(nSTableView, nSDraggingInfo, int2, int3);
  }

  /**
   * tableViewAcceptDrop
   *
   * @param nSTableView NSTableView
   * @param nSDraggingInfo NSDraggingInfo
   * @param int2 int
   * @param int3 int
   * @return boolean
   */
  public boolean tableViewAcceptDrop(NSTableView nSTableView, NSDraggingInfo nSDraggingInfo, int int2, int int3) {
	return dataSource().tableViewAcceptDrop(nSTableView, nSDraggingInfo, int2, int3);
  }

  /**
   * dataSource
   *
   * @return DataSource
   */
  private NSTableView.DataSource dataSource() {
	return (DataSource) tableView.dataSource();
  }

/* (non-Javadoc)
 * @see com.apple.cocoa.application.NSTableView.DataSource#tableViewWriteRowsToPasteboard(com.apple.cocoa.application.NSTableView, com.apple.cocoa.foundation.NSIndexSet, com.apple.cocoa.application.NSPasteboard)
 */
public boolean tableViewWriteRowsToPasteboard(NSTableView arg0, NSIndexSet arg1, NSPasteboard arg2) {
	return dataSource().tableViewWriteRowsToPasteboard(arg0, arg1, arg2);
}

/* (non-Javadoc)
 * @see com.apple.cocoa.application.NSTableView.DataSource#tableViewNamesOfPromisedFilesDroppedAtDestination(com.apple.cocoa.application.NSTableView, java.net.URL, com.apple.cocoa.foundation.NSIndexSet)
 */
public NSArray tableViewNamesOfPromisedFilesDroppedAtDestination(NSTableView arg0, URL arg1, NSIndexSet arg2) {
	return dataSource().tableViewNamesOfPromisedFilesDroppedAtDestination(arg0, arg1, arg2);
}

}
