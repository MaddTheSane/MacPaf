//
//  IndividualEditController.swift
//  MAF
//
//  Created by C.W. Betts on 3/3/15.
//
//

import Cocoa

class IndividualEditController: NSWindowController {
	@IBOutlet weak var afn: NSTextField!
	@IBOutlet weak var baptismDate: NSTextField!
	@IBOutlet weak var baptismTemple: NSComboBox!
	@IBOutlet weak var birthForm: NSForm!
	@IBOutlet weak var burialForm: NSForm!
	@IBOutlet weak var christeningForm: NSForm!
	@IBOutlet weak var deathForm: NSForm!
	@IBOutlet weak var endowmentDate: NSTextField!
	@IBOutlet weak var endowmentTemple: NSComboBox!
	@IBOutlet weak var gender: NSPopUpButton!
	@IBOutlet weak var givenNames: NSTextField!
	@IBOutlet weak var photo: NSImageView!
	@IBOutlet weak var rin: NSTextField!
	@IBOutlet weak var tabView: NSTabView!
	@IBOutlet weak var prefix: NSComboBox!
	@IBOutlet weak var suffix: NSComboBox!
	@IBOutlet weak var surname: NSComboBox!
	@IBOutlet weak var sealingToParentDate: NSTextField!
	@IBOutlet weak var sealingToParentTemple: NSComboBox!
	@IBOutlet weak var notesIndividualName: NSTextField!
	@IBOutlet weak var notesScrollView: NSScrollView!
	
	@IBOutlet weak var attributeTableController: EventTableController!
	@IBOutlet weak var eventTableController: EventTableController!

    override func windowDidLoad() {
        super.windowDidLoad()
    
        // Implement this method to handle any initialization after your window controller's window has been loaded from its nib file.
    }

	@IBAction func cancel(sender: AnyObject?) {
		
	}
	
	@IBAction func save(sender: AnyObject?) {
		
	}
}
