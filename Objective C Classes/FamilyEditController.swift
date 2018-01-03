//
//  FamilyEditController.swift
//  MAF
//
//  Created by C.W. Betts on 3/3/15.
//
//

import Cocoa

class FamilyEditController: NSWindowController {
	@IBOutlet weak var children: NSTableView!
	@IBOutlet weak var eventTableController: EventTableController!
	@IBOutlet weak var husbandButton: NSButton!
	@IBOutlet weak var marriageForm: NSForm!
	@IBOutlet weak var saveButton: NSButton!
	@IBOutlet weak var sealingDate: NSTextField!
	@IBOutlet weak var sealingTemple: NSComboBox!
	@IBOutlet weak var tabView: NSTabView!
	@IBOutlet weak var wifeButton: NSButton!
	
    override func windowDidLoad() {
        super.windowDidLoad()
    
        // Implement this method to handle any initialization after your window controller's window has been loaded from its nib file.
    }

	@IBAction func addChild(_ sender: AnyObject?) {
		
	}
	@IBAction func cancel(_ sender: AnyObject?) {
		
	}
	@IBAction func editHusband(_ sender: AnyObject?) {
		
	}
	@IBAction func editWife(_ sender: AnyObject?) {
		
	}
	@IBAction func removeChild(_ sender: AnyObject?) {
		
	}
	@IBAction func save(_ sender: AnyObject?) {
		
	}
}
