//
//  FamilyDetailController.swift
//  MAF
//
//  Created by C.W. Betts on 3/3/15.
//
//

import Cocoa

class FamilyDetailController: NSObject {
	@IBOutlet weak var childrenTable: NSTableView!
	@IBOutlet weak var childrenCountText: NSTextField!
	@IBOutlet weak var eventTable: NSTableView!
	@IBOutlet weak var husbandDetailsText: NSTextField!
	@IBOutlet weak var husbandNotesScroll: NSScrollView!
	@IBOutlet weak var husbandPhoto: NSImageView!
	@IBOutlet weak var noteScroll: NSScrollView!
	@IBOutlet weak var wifeDetailsText: NSTextField!
	@IBOutlet weak var wifePhoto: NSImageView!
	
	@IBOutlet weak var familyListController: FamilyListController!
}
