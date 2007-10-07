/*
 * Created on Jun 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.macpaf.jdom.test;

import java.io.*;

import junit.framework.*;

import com.redbugz.macpaf.*;
import com.redbugz.macpaf.jdom.*;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MacPAFDocumentJDOMTest extends TestCase {

	MacPAFDocumentJDOM doc = new MacPAFDocumentJDOM();
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNewDocumentInitialValues() {
		assertEquals(0, doc.getFamiliesMap().size());
		assertEquals(0, doc.getIndividualsMap().size());
		assertEquals(1, doc.getNextAvailableFamilyId());
		assertEquals(1, doc.getNextAvailableIndividualId());
		assertEquals(1, doc.getNextAvailableNoteId());
		assertEquals(1, doc.getNextAvailableSourceId());
		assertEquals(1, doc.getNextAvailableSubmitterId());
		assertEquals(1, doc.getNextAvailableRepositoryId());
		assertEquals(1, doc.getNextAvailableMultimediaId());
		assertTrue("Initial Primary Individual is an UnknownIndividual", doc.getPrimaryIndividual() instanceof Individual.UnknownIndividual);
	}
	
	public void testSimpleAddAndRemove() {
		Family f1 = doc.createAndInsertNewFamily();
		Individual i1 = doc.createAndInsertNewIndividual();
		Note n1 = doc.createAndInsertNewNote();
		Repository r1 = doc.createAndInsertNewRepository();
		Multimedia m1 = doc.createAndInsertNewMultimedia();
		Source s1 = doc.createAndInsertNewSource();
		Submitter t1 = doc.createAndInsertNewSubmitter();
		
		assertEquals(1, doc.getFamiliesMap().size());
		assertEquals(1, doc.getIndividualsMap().size());
		assertEquals(2, doc.getNextAvailableFamilyId());
		assertEquals(2, doc.getNextAvailableIndividualId());
		assertEquals(2, doc.getNextAvailableNoteId());
		assertEquals(2, doc.getNextAvailableSourceId());
		assertEquals(2, doc.getNextAvailableSubmitterId());
		assertEquals(2, doc.getNextAvailableRepositoryId());
		assertEquals(2, doc.getNextAvailableMultimediaId());
		
		assertEquals("F1", f1.getId());
		assertEquals("I1", i1.getId());
		assertEquals("N1", n1.getId());
		assertEquals("M1", m1.getId());
		assertEquals("R1", r1.getId());
		assertEquals("S1", s1.getId());
		assertEquals("T1", t1.getId());
		
		assertSame(f1, doc.getFamilyJDOM("F1"));
		assertSame(i1, doc.getIndividualJDOM("I1"));		
//		assertSame(i1, doc.getPrimaryIndividual());
		assertSame(n1, doc.getNoteJDOM("N1"));
		assertSame(m1, doc.getMultimediaJDOM("M1"));
		assertSame(r1, doc.getRepositoryJDOM("R1"));
		assertSame(s1, doc.getSourceJDOM("S1"));
		assertSame(t1, doc.getSubmitterJDOM("T1"));
		
		// now remove
		doc.removeFamily(f1);
		doc.removeIndividual(i1);
//		doc.removeNote(n1);
//		doc.removeMultimedia(m1);
//		doc.removeRepository(r1);
//		doc.removeSource(s1);
//		doc.removeSubmitter(t1);

		assertEquals(0, doc.getFamiliesMap().size());
		assertEquals(0, doc.getIndividualsMap().size());
}
	
	public void testCreateNewFamily() {
		Individual father = doc.createAndInsertNewIndividual();
		father.setGender(Gender.MALE);
		Individual mother = doc.createAndInsertNewIndividual();
		mother.setGender(Gender.FEMALE);
		Individual child1 = doc.createAndInsertNewIndividual();
		child1.setGender(Gender.MALE);
		Individual child2 = doc.createAndInsertNewIndividual();
		child2.setGender(Gender.FEMALE);
		// child with unknown gender
		Individual child3 = doc.createAndInsertNewIndividual();
		
		Family fam = doc.createAndInsertNewFamily();
		fam.setFather(father);
		fam.setMother(mother);
		fam.addChild(child1);
		fam.addChild(child2);
		fam.addChild(child3);
		
		assertEquals(1, doc.getFamiliesMap().size());
		assertEquals(5, doc.getIndividualsMap().size());
		assertEquals(3, fam.getChildren().size());
//		assertSame(father, fam.getFather());
//		assertSame(mother, fam.getMother());
//		assertSame(child1, fam.getChildren().get(0));
//		assertSame(child2, fam.getChildren().get(1));
//		assertSame(child3, fam.getChildren().get(2));
		try {
			doc.outputToXML(System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
