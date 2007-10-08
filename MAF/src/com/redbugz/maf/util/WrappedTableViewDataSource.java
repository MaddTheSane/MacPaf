package com.redbugz.maf.util;

import org.apache.log4j.*;
import org.apache.xml.utils.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;

public class WrappedTableViewDataSource /*implements com.apple.cocoa.application.NSTableView.DataSource*/ {
	private static Logger log = Logger.getLogger(WrappedTableViewDataSource.class);

	private static final NSSelector numberOfRowsInTableView = new NSSelector("numberOfRowsInTableView", new Class[] {NSTableView.class});
	private static final NSSelector tableViewObjectValueForLocation = new NSSelector("tableViewObjectValueForLocation", new Class[] {NSTableView.class,	NSTableColumn.class, int.class});
	private static final NSSelector tableViewSetObjectValueForLocation = new NSSelector("tableViewSetObjectValueForLocation", new Class[] {NSTableView.class, Object.class, NSTableColumn.class, int.class});
	private static final NSSelector tableViewSortDescriptorsDidChange = new NSSelector("tableViewSortDescriptorsDidChange", new Class[] {NSTableView.class, NSArray.class});
	private static final NSSelector tableViewValidateDrop = new NSSelector("tableViewValidateDrop", new Class[] {NSTableView.class, NSDraggingInfo.class, int.class, int.class});
	private static final NSSelector tableViewAcceptDrop = new NSSelector("tableViewAcceptDrop", new Class[] {NSTableView.class, NSDraggingInfo.class, int.class, int.class});
//	private static final NSSelector tableViewNamesOfPromisedFilesDroppedAtDestination = new NSSelector("tableViewNamesOfPromisedFilesDroppedAtDestination", new Class[] {NSTableView.class, URL.class, NSIndexSet.class});
	private static final NSSelector tableViewWriteRowsToPasteboard = new NSSelector("tableViewWriteRowsToPasteboard", new Class[] {NSTableView.class, NSArray.class, NSPasteboard.class});
//	private static final NSSelector tableViewWriteRowsToPasteboardPanther = new NSSelector("tableViewWriteRowsToPasteboard", new Class[] {NSTableView.class, NSIndexSet.class, NSPasteboard.class});
	
	private Object dataSource;
	
	public WrappedTableViewDataSource(Object wrappedDataSource) {
		super();
		dataSource = wrappedDataSource;
	}
	
	public int numberOfRowsInTableView(NSTableView arg0) {
		if (numberOfRowsInTableView.implementedByObject(dataSource)) {
			try {
				return ((Integer) numberOfRowsInTableView.invoke(dataSource, arg0)).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrappedRuntimeException(e);
			}
		} else {
			log.warn("wrapped datasource does not implement numberOfRowsInTableView");
		}
		return 0;
	}
	
	public Object tableViewObjectValueForLocation(NSTableView arg0,	NSTableColumn arg1, int arg2) {
		if (tableViewObjectValueForLocation.implementedByObject(dataSource)) {
			try {
				return tableViewObjectValueForLocation.invoke(dataSource, new Object[] {arg0, arg1, new Integer(arg2)});
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrappedRuntimeException(e);
			}
		} else {
			log.warn("wrapped datasource does not implement numberOfRowsInTableView");
		}
		return null;
	}
	
	public void tableViewSetObjectValueForLocation(NSTableView arg0, Object arg1, NSTableColumn arg2, int arg3) {
		if (tableViewSetObjectValueForLocation.implementedByObject(dataSource)) {
			try {
				tableViewSetObjectValueForLocation.invoke(dataSource, new Object[] {arg0, arg1, arg2, new Integer(arg3)});
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrappedRuntimeException(e);
			}
		}
	}
	
	public void tableViewSortDescriptorsDidChange(NSTableView arg0, NSArray arg1) {
		if (tableViewSortDescriptorsDidChange.implementedByObject(dataSource)) {
			try {
				tableViewSortDescriptorsDidChange.invoke(dataSource, arg0, arg1);
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrappedRuntimeException(e);
			}
		}
	}
	
	public int tableViewValidateDrop(NSTableView arg0, NSDraggingInfo arg1,
			int arg2, int arg3) {
		if (tableViewValidateDrop.implementedByObject(dataSource)) {
			try {
				return ((Integer) tableViewValidateDrop.invoke(dataSource, new Object[] {arg0, arg1, new Integer(arg2), new Integer(arg3)})).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrappedRuntimeException(e);
			}
		}
		return 0;
	}
	
	public boolean tableViewAcceptDrop(NSTableView arg0,
			NSDraggingInfo arg1, int arg2, int arg3) {
		if (tableViewAcceptDrop.implementedByObject(dataSource)) {
			try {
				return ((Boolean) tableViewAcceptDrop.invoke(dataSource, new Object[] {arg0, arg1, new Integer(arg2), new Integer(arg3)})).booleanValue();
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrappedRuntimeException(e);
			}
		}
		return false;
	}
	
//	public NSArray tableViewNamesOfPromisedFilesDroppedAtDestination(NSTableView arg0, URL arg1, NSIndexSet arg2) {
//		if (tableViewNamesOfPromisedFilesDroppedAtDestination.implementedByObject(dataSource)) {
//			try {
//				return (NSArray) tableViewNamesOfPromisedFilesDroppedAtDestination.invoke(dataSource, new Object[] {arg0, arg1, arg2});
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new WrappedRuntimeException(e);
//			}
//		}
//		return new NSArray();
//	}
	
	public boolean tableViewWriteRowsToPasteboard(NSTableView arg0,
			NSArray arg1, NSPasteboard arg2) {
		if (tableViewWriteRowsToPasteboard.implementedByObject(dataSource)) {
			try {
				return ((Boolean) tableViewWriteRowsToPasteboard.invoke(dataSource, new Object[] {arg0, arg1, arg2})).booleanValue();
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrappedRuntimeException(e);
			}
		}
		return false;
	}
	
//	public boolean tableViewWriteRowsToPasteboard(NSTableView arg0, NSIndexSet arg1, NSPasteboard arg2) {
//		if (tableViewWriteRowsToPasteboardPanther.implementedByObject(dataSource)) {
//			try {
//				return ((Boolean) tableViewWriteRowsToPasteboardPanther.invoke(dataSource, new Object[] {arg0, arg1, arg2})).booleanValue();
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new WrappedRuntimeException(e);
//			}
//		}
//		return false;
//	}
	
}