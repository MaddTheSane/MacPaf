//
//  SurnameList.swift
//  MAF
//
//  Created by C.W. Betts on 3/3/15.
//
//

import Cocoa

class SurnameList: NSObject, NSComboBoxDataSource {
	var surnames = [String]()

	
	func numberOfItemsInComboBox(aComboBox: NSComboBox) -> Int {
		return surnames.count
	}
	
	func comboBox(aComboBox: NSComboBox, objectValueForItemAtIndex index: Int) -> AnyObject {
		return surnames[index]
	}
	
	func comboBox(aComboBox: NSComboBox, indexOfItemWithStringValue string: String) -> Int {
		return find(surnames, string) ?? NSNotFound
	}
	
	func add(name: String) {
		if find(surnames, name) == nil {
			surnames.append(name)
		}
	}
}
