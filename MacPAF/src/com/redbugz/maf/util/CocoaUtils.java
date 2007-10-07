package com.redbugz.maf.util;


//CocoaUtils.java
//MacPAF

//Created by Logan Allred on 2/6/05.
//Copyright 2005 RedBugz Software. All rights reserved.


import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.application.NSComboBox.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.CocoaUtils;
import com.redbugz.macpaf.TempleJDOM;
import com.redbugz.macpaf.WrappedTableViewDataSource;
import com.redbugz.maf.*;
import com.redbugz.maf.jdom.*;


public class CocoaUtils {
	private static final Logger log = Logger.getLogger(CocoaUtils.class);

	public static final NSSelector DEFAULT_TABLEHEADER_SORT_IMAGE_SELECTOR = new NSSelector("_defaultTableHeaderSortImage", new Class[] {});
	public static final NSSelector DEFAULT_TABLEHEADER_REVERSE_SORT_IMAGE_SELECTOR = new NSSelector("_defaultTableHeaderReverseSortImage", new Class[] {});

	public static final NSSelector SHEET_DID_END_SELECTOR = new NSSelector("sheetDidEnd", new Class[] {NSWindow.class, int.class, Object.class} );
	public static final NSSelector OPEN_PANEL_DID_END_SELECTOR = new NSSelector("openPanelDidEnd", new Class[] {NSOpenPanel.class, int.class, Object.class} );
	public static final NSSelector CANCEL_SHEETS_SELECTOR = new NSSelector("cancelSheets", new Class[] {NSObject.class} );

	public static final String 		BEGIN_IMPORT_PROCESS_NOTIFICATION = "com.redbugz.maf.BeginImportProcessNotification";
	public static final NSSelector 	BEGIN_IMPORT_PROCESS_SELECTOR = new NSSelector("beginImportProcessNotification", new Class[] {NSNotification.class});

	public static final String 		IMPORT_DATA_NOTIFICATION = "com.redbugz.maf.ImportDataNotification";
	public static final NSSelector 	IMPORT_DATA_SELECTOR = new NSSelector("importDataNotification", new Class[] {NSNotification.class});

	public static final String 		LOAD_DOCUMENT_NOTIFICATION = "com.redbugz.maf.LoadDocumentDataNotification";
	public static final NSSelector 	LOAD_DOCUMENT_SELECTOR = new NSSelector("loadDocumentDataNotification", new Class[] {NSNotification.class});

//	public static final String 		UPDATE_PROGRESS_NOTIFICATION = "com.redbugz.maf.UpdateProgressNotification";
//	public static final NSSelector 	UPDATE_PROGRESS_SELECTOR = new NSSelector("updateProgressNotification", new Class[] {NSNotification.class});

	public static final String 		TASK_DONE_NOTIFICATION = "com.redbugz.maf.TaskDoneNotification";
	public static final NSSelector 	TASK_DONE_SELECTOR = new NSSelector("processTaskDoneNotification", new Class[] {NSNotification.class});
	
	public static final NSSelector 	STOP_TASK_SELECTOR = new NSSelector("stop", new Class[] {Object.class});
	
	/**
	 * @param comboBox
	 * @return
	 */
	public static Temple templeForComboBox(NSComboBox comboBox) {
		Temple temple = TempleJDOM.UNKNOWN_TEMPLE;
		NSComboBox.DataSource dataSource = (DataSource) comboBox.dataSource();
		log.debug("CocoaUtils.templeForComboBox() stringValue:" + comboBox.stringValue());
		int index = dataSource.comboBoxIndexOfItem(comboBox, comboBox.stringValue());
		log.debug("CocoaUtils.templeForComboBox() index:" + index);
		if (index >= 0 && index < dataSource.numberOfItemsInComboBox(comboBox)) {
			Temple selection = (Temple) dataSource.comboBoxObjectValueForItemAtIndex(comboBox, index);
			log.debug("CocoaUtils.templeForComboBox() selection:" + selection);
			temple = new TempleJDOM(selection);
		}
		else {
			log.debug("CocoaUtils.templeForComboBox() setCode stringValue:" + comboBox.stringValue());
			temple.setCode(comboBox.stringValue());
		}
		//		temple = new TempleJDOM(TempleList.templeWithCode(((Temple)sealingToParentTemple.objectValueOfSelectedItem()).getCode()));
		log.debug("CocoaUtils.templeForComboBox() returning temple:" + temple);
		return temple;
	}

	public static WrappedTableViewDataSource wrappedTableViewDataSource(Object object) {
		return new WrappedTableViewDataSource(object);
	}

	/*
	Get Ascending and Descending Sort indicator images, 10.1 and 10.2 compatible
	Original Source: <http://cocoa.karelia.com/AppKit_Categories/NSTableView/Get_Ascending_and_D.m>
	(See copyright notice at <http://cocoa.karelia.com>)
	 */

	/*"	Return the sorting indicator image; works on 10.1 and 10.2.
"*/
	public static NSImage ascendingSortIndicator() {
		NSImage result = NSImage.imageNamed("NSAscendingSortIndicator");
		try {
			if (null == result && DEFAULT_TABLEHEADER_SORT_IMAGE_SELECTOR.implementedByClass(NSTableView.class)) {
				result = (NSImage) DEFAULT_TABLEHEADER_SORT_IMAGE_SELECTOR.invoke(NSTableView.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static NSImage descendingSortIndicator() {
		NSImage result = NSImage.imageNamed("NSDescendingSortIndicator");
		try {
			if (null == result && DEFAULT_TABLEHEADER_SORT_IMAGE_SELECTOR.implementedByClass(NSTableView.class)) {
				result = (NSImage) DEFAULT_TABLEHEADER_SORT_IMAGE_SELECTOR.invoke(NSTableView.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Object firstObjectFromList(List list) {
		Object result = null;
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	public static NSDocument getCurrentDocument() {
		return NSDocumentController.sharedDocumentController().currentDocument();
	}

	public static final class KeyValueComparator implements Comparator {
		private String _key;

		public KeyValueComparator(String key) {
			_key = key;
		}

		public int compare(Object o1, Object o2) {
			Object value1 = NSKeyValue.valueForKeyPath(o1, _key);
			Object value2 = NSKeyValue.valueForKeyPath(o2, _key);
			if (value1 instanceof Comparable) {
				Comparable comparableValue1 = (Comparable) value1;
				return comparableValue1.compareTo(value2);
			} else if (value2 instanceof Comparable) {
				Comparable comparableValue2 = (Comparable) value2;
				return comparableValue2.compareTo(value1);
			}
			return String.valueOf(value1).compareTo(String.valueOf(value2));
		}
	}

	public static List arrayAsList(NSArray array) {
		List result = new ArrayList();
		Enumeration enumeration = array.objectEnumerator();
		while (enumeration.hasMoreElements()) {
			result.add(enumeration.nextElement());
		}
		return result;
	}

	public static void makeObjectsPerformSelector(NSArray array, NSSelector selector, Object[] arguments) {
		Enumeration enumeration = array.objectEnumerator();
		while (enumeration .hasMoreElements()) {
			NSObject element = (NSObject) enumeration.nextElement();
			try {
				selector.invoke(element, arguments);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void makeObjectsPerformSelector(NSArray array, NSSelector selector, Object argument) {
		CocoaUtils.makeObjectsPerformSelector(array, selector, new Object[] {argument});
	}
	
	public static List javaListFromNSArray(NSArray array) {
		List list = new ArrayList();
		Enumeration enumeration = array.objectEnumerator();
		while (enumeration.hasMoreElements()) {
			list.add(enumeration.nextElement());			
		}
		return list;
	}
}
