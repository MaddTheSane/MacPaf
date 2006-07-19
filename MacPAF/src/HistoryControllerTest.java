import junit.framework.*;

import com.redbugz.macpaf.*;
import com.redbugz.macpaf.test.*;

public class HistoryControllerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRecentIndividualsList() throws Exception {
		HistoryController hc = new HistoryController();
		assertTrue("Recent List is empty", hc.getRecentIndividualList().size() == 0);
		assertTrue("invalid index -1 on empty list returns Unknown", hc.getRecentIndividual(-1) instanceof Individual.UnknownIndividual);
		assertTrue("invalid index 0 on empty list returns Unknown", hc.getRecentIndividual(0) instanceof Individual.UnknownIndividual);
		assertTrue("invalid index 1 on empty list returns Unknown", hc.getRecentIndividual(1) instanceof Individual.UnknownIndividual);
		
		Individual newGuy = createAndAddIndividualToRecentList(hc, "Some Dude", "Test", 1);
	}

	public void testRecentIndividualsListSize() throws Exception {
		HistoryController hc = new HistoryController();
		assertEquals(0, hc.getRecentIndividualList().size());
		
		hc.setRecentIndividualListSize(10);

		Individual guy1 = createAndAddIndividualToRecentList(hc, "First New", "Guy", 1);
		Individual guy2 = createAndAddIndividualToRecentList(hc, "Second New", "Guy", 2);
		Individual guy3 = createAndAddIndividualToRecentList(hc, "Third New", "Guy", 3);
		Individual guy4 = createAndAddIndividualToRecentList(hc, "Fourth New", "Guy", 4);
		Individual guy5 = createAndAddIndividualToRecentList(hc, "Fifth New", "Guy", 5);
		Individual guy6 = createAndAddIndividualToRecentList(hc, "Sixth New", "Guy", 6);
		assertEquals(6, hc.getRecentIndividualList().size());
		hc.setRecentIndividualListSize(5);
		assertEquals(5, hc.getRecentIndividualList().size());
		assertEquals(guy2, hc.getRecentIndividual(4));
		Individual guy7 = createAndAddIndividualToRecentList(hc, "Seventh New", "Guy", 5);
		Individual guy8 = createAndAddIndividualToRecentList(hc, "Eighth New", "Guy", 5);
		assertEquals(guy8, hc.getRecentIndividual(0));
		assertEquals(guy7, hc.getRecentIndividual(1));
		assertEquals(guy6, hc.getRecentIndividual(2));
		assertEquals(guy5, hc.getRecentIndividual(3));
		assertEquals(guy4, hc.getRecentIndividual(4));
//		System.out.println("HistoryControllerTest.testRecentIndividualsListSize() "+hc.getRecentIndividualList());

}

	private Individual createAndAddIndividualToRecentList(HistoryController hc, String givenNames, String surname, int listCount) {
		Individual newGuy = new MyIndividual(givenNames, surname, Gender.UNKNOWN);
		hc.addToRecentIndividualList(newGuy);
		assertEquals(listCount, hc.getRecentIndividualList().size());
		assertEquals(hc.getRecentIndividual(0), newGuy);
		return newGuy;
	}
}
