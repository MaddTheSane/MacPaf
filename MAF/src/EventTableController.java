/* EventTableController */

import java.util.*;

import org.apache.log4j.*;

import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.maf.*;

public class EventTableController extends NSObject {
	private static final Logger log = Logger.getLogger(EventTableController.class);
	private static final String MACPAF_TABLE_REORDER_DRAG_TYPE = "MAFTableReorderDragType";
	private static final NSArray DRAG_TYPES_ARRAY = new NSArray(new Object[] {MACPAF_TABLE_REORDER_DRAG_TYPE});
	private Object _source = Individual.UNKNOWN;
	private String _type = "event";
	private List _events = Collections.EMPTY_LIST;

    public NSTableView eventTable; /* IBOutlet */
    
    public void awakeFromNib()
    {
    	System.out.println("EventTableController.awakeFromNib()");
    		setup();
    }
    
    public void setup() {
    	System.out.println("EventTableController.setup()");
    	eventTable.registerForDraggedTypes(DRAG_TYPES_ARRAY);

    }

    public void addEvent(Object sender) { /* IBAction */
    		MyDocument.showUserErrorMessage("Feature not yet available.", "This feature has not yet been implemented. Look for a future release to add this functionality.");
    }

    public void editEvent(Object sender) { /* IBAction */
		MyDocument.showUserErrorMessage("Feature not yet available.", "This feature has not yet been implemented. Look for a future release to add this functionality.");
    }

    public void removeEvent(Object sender) { /* IBAction */
		MyDocument.showUserErrorMessage("Feature not yet available.", "This feature has not yet been implemented. Look for a future release to add this functionality.");
    }


	  public void setEventSource(Object source) {
		  _source = source;
		  _events = getEvents();
		  eventTable.reloadData();
		  eventTable.deselectAll(this);
	  }
	  
	  public void setEventType(String type) {
		  _type = type;
		  _events = getEvents();
		  eventTable.reloadData();
		  eventTable.deselectAll(this);
	  }

	  private List getEvents() {
		  if (_source instanceof Individual) {
			  Individual individual = (Individual) _source;
			  if ("event".equals(_type)) {
				  return individual.getEvents();
			  } else if ("attribute".equals(_type)) {
				  return individual.getAttributes();
			  }
		  } else if (_source instanceof Family) {
			  Family family = (Family) _source;
			  return family.getEvents();
		  }
		  return Collections.EMPTY_LIST;
	  }
	  /**
	   * numberOfRowsInTableView
	   *
	   * @param nSTableView NSTableView
	   * @return int
	   */
	  public int numberOfRowsInTableView(NSTableView nSTableView) {
//		  log.debug("EventTableController.numberOfRowsInTableView():"+_events.size());
		return _events.size();
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
//		  System.out.println("EventTableController.tableViewObjectValueForLocation():"+nSTableView+":"+nSTableColumn.headerCell().stringValue()+":"+int2);
		  Event event = (Event) _events.get(int2);
//		  log.debug("event:"+event.getDateString()+event.getEventTypeString());
		  if ("date".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getDateString();
		  }
		  else if ("place".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getPlace().getFormatString();
		  }
		  else if ("type".equalsIgnoreCase(nSTableColumn.identifier().toString())) {
			  return event.getEventTypeString();
		  }
		  return "Unknown";
	  }

	  
	  
	  //  tableview drag reordering api	  
	  
	  public boolean disabled_tableViewWriteRowsToPasteboard(NSTableView aTable, NSArray rows, NSPasteboard pboard ) {
		  System.err.println("DNDJavaController.tableViewWriteRowsToPasteboard():"+rows+":"+pboard.types());
		   	// declare our own pasteboard types
		      NSArray typesArray = new NSArray(DRAG_TYPES_ARRAY);
		      pboard.declareTypes(typesArray, this);
		  	
		      // add rows array for local move
		      pboard.setPropertyListForType(rows, MACPAF_TABLE_REORDER_DRAG_TYPE);
		  	
		      return true;
		  }


		   public int tableViewValidateDrop(NSTableView tv, NSDraggingInfo info, int row, int operation) {
		  		  System.err.println("DNDJavaController.tableViewValidateDrop():"+info+":"+row+":"+operation);
		      
		      int dragOp = NSDraggingInfo.DragOperationNone;
		      
		      // if drag source is self, it's a move
		      if (tv.equals(info.draggingSource()))
		  	{
		  		dragOp =  NSDraggingInfo.DragOperationMove;
		      }
		      // we want to put the object at, not over,
		      // the current row (contrast NSTableViewDropOn) 
		      tv.setDropRowAndDropOperation(row, NSTableView.NSTableViewDropAbove);
		  	
		      return dragOp;
		  }



		  public boolean tableViewAcceptDrop(NSTableView tableView, NSDraggingInfo info, int row, int operation) {
		  		  System.err.println("DNDJavaController.tableViewAcceptDrop():"+info+":"+row+":"+operation);
		  		  System.out.println("dragging pboard:"+info.draggingPasteboard().types());
		      if (row < 0)
		  	{
		  		row = 0;
		  	}
		      
		      // if drag source is self, it's a move
		      if (tableView.equals(info.draggingSource()))
		      {
//		  		NSArray *rows = [[info draggingPasteboard] propertyListForType:MovedRowsType];
		    	  NSArray rows = (NSArray) info.draggingPasteboard().propertyListForType(info.draggingPasteboard().availableTypeFromArray(DRAG_TYPES_ARRAY));
		    	  log.debug(rows);
		    	  log.debug("dragging operation will move row "+rows.lastObject()+" to row "+row);
//		  		// set selected rows to those that were just moved
		    	  tableView.reloadData();
		    	  tableView.selectRow(row, false);
		  		return true;
		      }
		  	
		      return false;
		  }

		  
		  
		  
	  
	  public boolean tableViewWriteRowsToPasteboard2(NSTableView aTable, NSArray anArray, NSPasteboard aPastboard ) {
		  System.err.println("EventTableController.tableViewWriteRowsToPasteboard():"+anArray+":"+aPastboard.types());
//		  When I click and drag a row in the table, the strings from that row move around with the mouse arrow.
		  NSArray newArray = new NSArray();
		  newArray = newArray.arrayByAddingObject("DragData");
		  
		  String dataString = new String("DragData");
//		  String[][] defaultMatrix;
//		  defaultMatrix = getMatrix(); //returns a String[][] that holds the strings that are displayed in the table
//		  for (int i=0; i<numberOfRows; i++) {
//			  for (int j=0; j<numberOfColumns; j++) {
//				  String f = defaultMatrix[i][j];
//				  dataString = dataString + f; //put all of the strings together
//			  }
//		  }
		
		  
		  aPastboard.declareTypes(newArray,null);
		  aPastboard.setStringForType(dataString, "DragData");
		  aPastboard.setDataForType(new NSData(new byte[] {'a'}),"DragData");
		  return true;
	  }

	  public boolean tableViewAcceptDrop2(NSTableView tableView, NSDraggingInfo info, int row, int operation) {
		  System.err.println("EventTableController.tableViewAcceptDrop()");
//		  don't know why this method doesn't get called
		  String data = info.draggingPasteboard().stringForType("DragData" );
//		  not sure how to make it insert it where appropriate
		  System.out.println(data);
		  return true;		  
	  }

	  public int tableViewValidateDrop2(NSTableView tableView, NSDraggingInfo info, int row, int operation) {
		  System.err.println("EventTableController.tableViewValidateDrop()");
//		  if(operation==NSDraggingInfo.DragOperationMove) return operation;
//		  else return NSDraggingInfo.DragOperationNone;
		  return NSDraggingInfo.DragOperationAll;
	  }
	  
	  
	  public int draggingEntered(NSDraggingInfo sender) {
		  System.err.println("EventTableController.draggingEntered():"+sender);
		  return NSDraggingInfo.DragOperationAll;
	  }
	  
	  public void draggingEnded(NSDraggingInfo sender) {
		  System.err.println("EventTableController.draggingEnded()");
	  }
	  
	  public boolean prepareForDragOperation(NSDraggingInfo sender) {
		  System.err.println("EventTableController.prepareForDragOperation()");
		  return true;
	  }

	/*  
//	 NEW API for Dragging in TableView:
//	 typedef enum { NSTableViewDropOn, NSTableViewDropAbove } NSTableViewDropOperation;
//	 In drag and drop, used to specify a dropOperation. For example, given a table with N rows (numbered with row 0 at the top visually), a row of N-1 and operation of NSTableViewDropOn would specify a drop on the last row. To specify a drop below the last row, one would use a row of N and NSTableViewDropAbove for the operation.

	static int _moveRow = 0;

	- (BOOL)tableView:(NSTableView *)tv writeRows:(NSArray*)rows toPasteboard:(NSPasteboard*)pboard 
//	 This method is called after it has been determined that a drag should begin, but before the drag has been started. To refuse the drag, return NO. To start a drag, return YES and place the drag data onto the pasteboard (data, owner, etc...). The drag image and other drag related information will be set up and provided by the table view once this call returns with YES. The rows array is the list of row numbers that will be participating in the drag.
	{
	    int count = [gifFileArray count];
	    int rowCount = [rows count];
	    if (count < 2) return NO;  // except they might want to copy to another window?
	    // we should allow group selection and copy between windows: PENDING
	    [pboard declareTypes:[NSArray arrayWithObject:GifInfoPasteBoard] owner:self];
	    [pboard setPropertyList:rows forType:GifInfoPasteBoard];
	    if (rowCount == 1) _moveRow = [[rows objectAtIndex:0]intValue];
	    return YES;
	}
	- (unsigned int)tableView:(NSTableView*)tv validateDrop:(id <NSDraggingInfo>)info proposedRow:(int)row proposedDropOperation:(NSTableViewDropOperation)op 
//	 This method is used by NSTableView to determine a valid drop target. Based on the mouse position, the table view will suggest a proposed drop location. This method must return a value that indicates which dragging operation the data source will perform. The data source may "re-target" a drop if desired by calling setDropRow:dropOperation: and returning something other than NSDragOperationNone. One may choose to re-target for various reasons (eg. for better visual feedback when inserting into a sorted position).
	{
	    if (row != _moveRow) {
	        if (op==NSTableViewDropAbove / *NSTableViewDropOn* /) {
	            return NSDragOperationAll;
	        }
	        return NSDragOperationNone;
	    }
	    return NSDragOperationNone;
	}

	- (BOOL)tableView:(NSTableView*)tv acceptDrop:(id <NSDraggingInfo>)info row:(int)row dropOperation:(NSTableViewDropOperation)op 
//	 This method is called when the mouse is released over an outline view that previously decided to allow a drop via the validateDrop method. The data source should incorporate the data from the dragging pasteboard at this time. 
	{
	   BOOL result = [self tableView:tableView didDepositRow:_moveRow at:(int)row];
	   [tableView reloadData];
	   return result;
	}

//	 end TableView dragging API
*/
}
