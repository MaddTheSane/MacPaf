package com.redbugz.macpaf.util;

//
//  CocoaUtils.java
//  MacPAF
//
//  Created by Logan Allred on 2/6/05.
//  Copyright 2005 __MyCompanyName__. All rights reserved.
//

import org.apache.log4j.Logger;

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.apple.cocoa.application.NSComboBox.DataSource;
import com.redbugz.macpaf.Temple;
import com.redbugz.macpaf.jdom.TempleJDOM;


public class CocoaUtils {
	  private static final Logger log = Logger.getLogger(CocoaUtils.class);

	/**
	   * @param comboBox
	   * @return
	   */
	  public static Temple templeForComboBox(NSComboBox comboBox) {
		TempleJDOM temple = new TempleJDOM();
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

}
