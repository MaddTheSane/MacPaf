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

	
	func numberOfItems(in aComboBox: NSComboBox) -> Int {
		return surnames.count
	}
	
	func comboBox(_ aComboBox: NSComboBox, objectValueForItemAt index: Int) -> Any? {
		return surnames[index]
	}
	
	func comboBox(_ aComboBox: NSComboBox, indexOfItemWithStringValue string: String) -> Int {
		return surnames.index(of: string) ?? NSNotFound
	}
	
	func add(name: String) {
		if surnames.index(of: name) == nil {
			surnames.append(name)
		}
	}
}
