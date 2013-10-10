import junit.framework.*;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for default package");
		//$JUnit-BEGIN$
		suite.addTestSuite(HistoryControllerTest.class);
		//$JUnit-END$
		return suite;
	}

}
