

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RedBugz Software</p>
 * @author Logan Allred
 * @version 1.0
 */
import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.NSToolbarItem;

public class MainToolbar extends NSToolbar implements NSToolbar.Delegate {
  public static final String ADD_FAMILY_IDENTIFIER = "AddFamily";
  public static final String ADD_INDIVIDUAL_IDENTIFIER = "AddIndividual";

  NSMutableDictionary toolbarItems = new NSMutableDictionary();

  public MainToolbar() {
	super("main");
	setDelegate(this);
	setAllowsUserCustomization(true);
	setAutosavesConfiguration(true);
	toolbarItems.setObjectForKey(new NSToolbarItem(NSToolbarItem.CustomizeToolbarItemIdentifier), NSToolbarItem.CustomizeToolbarItemIdentifier);
	toolbarItems.setObjectForKey(new NSToolbarItem(NSToolbarItem.FlexibleSpaceItemIdentifier), NSToolbarItem.FlexibleSpaceItemIdentifier);
	NSToolbarItem addIndividualItem = new NSToolbarItem(ADD_INDIVIDUAL_IDENTIFIER);
	addIndividualItem.setLabel("Add Individual");
	addIndividualItem.setImage(NSImage.imageNamed("Disc Add"));
	toolbarItems.setObjectForKey(addIndividualItem, ADD_INDIVIDUAL_IDENTIFIER);
	NSToolbarItem addFamilyItem = new NSToolbarItem(ADD_FAMILY_IDENTIFIER);
	addFamilyItem.setLabel("Add Family");
	addFamilyItem.setImage(NSImage.imageNamed("Badge Add"));
	toolbarItems.setObjectForKey(addFamilyItem, ADD_FAMILY_IDENTIFIER);
  }

  /**
   * toolbarItemForItemIdentifier
   *
   * @param nSToolbar NSToolbar
   * @param string String
   * @param boolean2 boolean
   * @return NSToolbarItem
   */
  public NSToolbarItem toolbarItemForItemIdentifier(NSToolbar nSToolbar, String string, boolean boolean2) {
	return (NSToolbarItem) toolbarItems.objectForKey(string);
  }

  /**
   * toolbarDefaultItemIdentifiers
   *
   * @param nSToolbar NSToolbar
   * @return NSArray
   */
  public NSArray toolbarDefaultItemIdentifiers(NSToolbar nSToolbar) {
	return new NSArray(new String[] {
	  ADD_INDIVIDUAL_IDENTIFIER,
	  ADD_FAMILY_IDENTIFIER,
	  NSToolbarItem.FlexibleSpaceItemIdentifier,
	  NSToolbarItem.PrintItemIdentifier,
	  NSToolbarItem.CustomizeToolbarItemIdentifier
	});
  }

  /**
   * toolbarAllowedItemIdentifiers
   *
   * @param nSToolbar NSToolbar
   * @return NSArray
   */
  public NSArray toolbarAllowedItemIdentifiers(NSToolbar nSToolbar) {
	return new NSArray(new String[] {
	  ADD_INDIVIDUAL_IDENTIFIER,
	  ADD_FAMILY_IDENTIFIER,
	  NSToolbarItem.SeparatorItemIdentifier,
	  NSToolbarItem.PrintItemIdentifier,
	  NSToolbarItem.FlexibleSpaceItemIdentifier,
	  NSToolbarItem.CustomizeToolbarItemIdentifier
	});
  }

  /**
   * toolbarSelectableItemIdentifiers
   *
   * @param nSToolbar NSToolbar
   * @return NSArray
   */
  public NSArray toolbarSelectableItemIdentifiers(NSToolbar nSToolbar) {
	return null;
  }

}
