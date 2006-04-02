package com.redbugz.macpaf.util;

//
//  CocoaUtils.java
//  MacPAF
//
//  Created by Logan Allred on 2/6/05.
//  Copyright 2005 __MyCompanyName__. All rights reserved.
//

import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.apple.cocoa.application.NSComboBox;
import com.apple.cocoa.application.NSDocument;
import com.apple.cocoa.application.NSDocumentController;
import com.apple.cocoa.application.NSImage;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.application.NSWindow;
import com.apple.cocoa.application.NSComboBox.DataSource;
import com.apple.cocoa.foundation.NSKeyValue;
import com.apple.cocoa.foundation.NSKeyValueCoding;
import com.apple.cocoa.foundation.NSSelector;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Temple;
import com.redbugz.macpaf.jdom.TempleJDOM;


public class CocoaUtils {
	  public static final NSSelector defaultTableHeaderSortImageSelector = new NSSelector("_defaultTableHeaderSortImage", new Class[] {});
	  public static final NSSelector defaultTableHeaderReverseSortImageSelector = new NSSelector("_defaultTableHeaderReverseSortImage", new Class[] {});


	private static final Logger log = Logger.getLogger(CocoaUtils.class);


	// These constants are normally in NSPathUtilities, but not on OS X 10.1.5, so I put them here
	public static final java.lang.String FileType = "NSFileType";
	public static final java.lang.String FileTypeDirectory = "NSFileTypeDirectory";
	public static final java.lang.String FileTypeRegular = "NSFileTypeRegular";
	public static final java.lang.String FileTypeSymbolicLink = "NSFileTypeSymbolicLink";
	public static final java.lang.String FileTypeSocket = "NSFileTypeSocket";
	public static final java.lang.String FileTypeCharacterSpecial = "NSFileTypeCharacterSpecial";
	public static final java.lang.String FileTypeBlockSpecial = "NSFileTypeBlockSpecial";
	public static final java.lang.String FileTypeUnknown = "NSFileTypeUnknown";
	public static final java.lang.String FileSize = "NSFileSize";
	public static final java.lang.String FileModificationDate = "NSFileModificationDate";
	public static final java.lang.String FileReferenceCount = "NSFileReferenceCount";
	public static final java.lang.String FileDeviceIdentifier = "NSFileDeviceIdentifier";
	public static final java.lang.String FileOwnerAccountName = "NSFileOwnerAccountName";
	public static final java.lang.String FileGroupOwnerAccountName = "NSFileGroupOwnerAccountName";
	public static final java.lang.String FilePosixPermissions = "NSFilePosixPermissions";
	public static final java.lang.String FileSystemNumber = "NSFileSystemNumber";
	public static final java.lang.String FileSystemFileNumber = "NSFileSystemFileNumber";
	public static final java.lang.String FileExtensionHidden = "NSFileExtensionHidden";
	public static final java.lang.String FileHFSCreatorCode = "NSFileHFSCreatorCode";
	public static final java.lang.String FileHFSTypeCode = "NSFileHFSTypeCode";
	public static final java.lang.String FileImmutable = "NSFileImmutable";
	public static final java.lang.String FileAppendOnly = "NSFileAppendOnly";
	public static final java.lang.String FileCreationDate = "NSFileCreationDate";
	public static final java.lang.String FileOwnerAccountID = "NSFileOwnerAccountID";
	public static final java.lang.String FileGroupOwnerAccountID = "NSFileGroupOwnerAccountID";
	public static final java.lang.String FileSystemSize = "NSFileSystemSize";
	public static final java.lang.String FileSystemFreeSize = "NSFileSystemFreeSize";
	public static final java.lang.String FileSystemNodes = "NSFileSystemNodes";
	public static final java.lang.String FileSystemFreeNodes = "NSFileSystemFreeNodes";
	  
	
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

	/**
	 * @return
	 */
	public static NSSelector didEndSelector() {
		return new NSSelector("sheetDidEnd", new Class[] {NSWindow.class, int.class, Object.class} );
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
			if (null == result && defaultTableHeaderSortImageSelector.implementedByClass(NSTableView.class)) {
				result = (NSImage) defaultTableHeaderSortImageSelector.invoke(NSTableView.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static NSImage descendingSortIndicator() {
		NSImage result = NSImage.imageNamed("NSDescendingSortIndicator");
		try {
			if (null == result && defaultTableHeaderSortImageSelector.implementedByClass(NSTableView.class)) {
				result = (NSImage) defaultTableHeaderSortImageSelector.invoke(NSTableView.class);
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
}
