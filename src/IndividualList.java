/* IndividualList */

import java.util.ArrayList;

import com.apple.cocoa.application.NSTableColumn;
import com.apple.cocoa.application.NSTableView;
import com.apple.cocoa.foundation.NSNotification;
import com.redbugz.macpaf.*;

public class IndividualList {

	public MyDocument document; /* IBOutlet */
	private Individual selectedIndividual; // = null;
	private ArrayList individuals; // = new ArrayList();
	private boolean reload = false;

	public IndividualList() {
		if (individuals == null) {
			System.out.println(
				"IndividualList.IndividualList() individuals is null, initializing *&(&^(*&(*&^%)(*&^&*(%*&^");
			individuals = new ArrayList();
			selectedIndividual = null;
		}
	}

	public Individual getSelectedIndividual() {
		return selectedIndividual;
	}

	public int numberOfRowsInTableView(NSTableView nsTableView) {
		System.out.println("IndividualList.numberOfRowsInTableView()");
		if (reload)
		{
			reload = false;
			nsTableView.reloadData();
		}
		return individuals.size();
	}

	public Object tableViewObjectValueForLocation(
		NSTableView nsTableView,
		NSTableColumn nsTableColumn,
		int i) {
		try {
			Individual individual = (Individual) individuals.get(i);
			if (nsTableColumn.headerCell().stringValue().equals("ID")) {
				return individual.getId();
			} else if (
				nsTableColumn.headerCell().stringValue().equals("Name")) {
				return individual.getFullName();
			} else if (
				nsTableColumn.headerCell().stringValue().equals(
					"Birth date")) {
				return individual.getBirthEvent().getDateString();
			} else if (
				nsTableColumn.headerCell().stringValue().equals(
					"Birth place")) {
				return individual.getBirthEvent().getPlace().getFormatString();
			}
			System.out.println(
				"IndividualList unidentified column:" + nsTableColumn);
		} catch (Exception e) {
			e.printStackTrace();
			//To change body of catch statement use Options | File Templates.
		}
		return null;
	}

	public void add(Individual individual) {
		individuals.add(individual);
		reload = true;
	}

	public void tableViewSelectionDidChange(NSNotification aNotification) {
		System.out.println(
			"IndividualList tableViewSelectionDidChange():" + aNotification);
		NSTableView nsTableView = (NSTableView) aNotification.object();
		selectedIndividual =
			(Individual) individuals.get(nsTableView.selectedRow());
		nsTableView.reloadData();
		document.setIndividual(this);
	}
}
