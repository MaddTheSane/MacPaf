/**
 * 
 */
package com.redbugz.maf.gdbi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

//import org.gdbi.db.pgvcgi.PgvcgiDatabase;

import com.apple.cocoa.foundation.NSObject;
import com.redbugz.maf.Family;
import com.redbugz.maf.Header;
import com.redbugz.maf.Individual;
import com.redbugz.maf.MafDocument;
import com.redbugz.maf.Multimedia;
import com.redbugz.maf.Note;
import com.redbugz.maf.Repository;
import com.redbugz.maf.Source;
import com.redbugz.maf.Submission;
import com.redbugz.maf.Submitter;

/**
 * @author logan
 *
 */
public class GdbiDocument extends Observable implements Observer, MafDocument{
	
	

	public GdbiDocument() {
		super();
	 try {
//		PgvcgiDatabase.udebug.debug_println("test");
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addFamily(com.redbugz.maf.Family)
	 */
	public void addFamily(Family newFamily) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addIndividual(com.redbugz.maf.Individual)
	 */
	public void addIndividual(Individual newIndividual) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addMultimedia(com.redbugz.maf.Multimedia)
	 */
	public void addMultimedia(Multimedia newMultimedia) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addNote(com.redbugz.maf.Note)
	 */
	public void addNote(Note newNote) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addObserver(java.util.Observer)
	 */
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addRepository(com.redbugz.maf.Repository)
	 */
	public void addRepository(Repository newRepository) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addSource(com.redbugz.maf.Source)
	 */
	public void addSource(Source newSource) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#addSubmitter(com.redbugz.maf.Submitter)
	 */
	public void addSubmitter(Submitter newSubmitter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#chooseNewPrimaryIndividual()
	 */
	public void chooseNewPrimaryIndividual() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#createAndInsertNewFamily()
	 */
	public Family createAndInsertNewFamily() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#createAndInsertNewIndividual()
	 */
	public Individual createAndInsertNewIndividual() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#createAndInsertNewMultimedia()
	 */
	public Multimedia createAndInsertNewMultimedia() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#createAndInsertNewNote()
	 */
	public Note createAndInsertNewNote() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#createAndInsertNewRepository()
	 */
	public Repository createAndInsertNewRepository() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#createAndInsertNewSource()
	 */
	public Source createAndInsertNewSource() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#createAndInsertNewSubmitter()
	 */
	public Submitter createAndInsertNewSubmitter() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#endSuppressUpdates()
	 */
	public void endSuppressUpdates() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getFamiliesMap()
	 */
	public Map getFamiliesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getFamily(java.lang.String)
	 */
	public Family getFamily(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getHeader()
	 */
	public Header getHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getIndividual(java.lang.String)
	 */
	public Individual getIndividual(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getIndividualsMap()
	 */
	public Map getIndividualsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getMultimedia(java.lang.String)
	 */
	public Multimedia getMultimedia(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getMultimediaMap()
	 */
	public Map getMultimediaMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNextAvailableFamilyId()
	 */
	public int getNextAvailableFamilyId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNextAvailableIndividualId()
	 */
	public int getNextAvailableIndividualId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNextAvailableMultimediaId()
	 */
	public int getNextAvailableMultimediaId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNextAvailableNoteId()
	 */
	public int getNextAvailableNoteId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNextAvailableRepositoryId()
	 */
	public int getNextAvailableRepositoryId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNextAvailableSourceId()
	 */
	public int getNextAvailableSourceId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNextAvailableSubmitterId()
	 */
	public int getNextAvailableSubmitterId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNote(java.lang.String)
	 */
	public Note getNote(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getNotesMap()
	 */
	public Map getNotesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getPrimaryIndividual()
	 */
	public Individual getPrimaryIndividual() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getRepositoriesMap()
	 */
	public Map getRepositoriesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getRepository(java.lang.String)
	 */
	public Repository getRepository(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getSource(java.lang.String)
	 */
	public Source getSource(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getSourcesMap()
	 */
	public Map getSourcesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getSubmission()
	 */
	public Submission getSubmission() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getSubmitter(java.lang.String)
	 */
	public Submitter getSubmitter(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#getSubmittersMap()
	 */
	public Map getSubmittersMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#importFromDocument(com.redbugz.maf.MafDocument)
	 */
	public void importFromDocument(MafDocument importDocument) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#importGedcom(java.io.File, com.apple.cocoa.foundation.NSObject)
	 */
	public void importGedcom(File importFile, NSObject progress) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#outputToGedcom(java.io.OutputStream)
	 */
	public void outputToGedcom(OutputStream outputStream) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#outputToTempleReady(java.io.FileOutputStream)
	 */
	public void outputToTempleReady(FileOutputStream stream) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#outputToXML(java.io.OutputStream)
	 */
	public void outputToXML(OutputStream outputStream) throws IOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#removeFamily(com.redbugz.maf.Family)
	 */
	public void removeFamily(Family familyToRemove) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#removeIndividual(com.redbugz.maf.Individual)
	 */
	public void removeIndividual(Individual individualToRemove) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#removeSubmitter(com.redbugz.maf.Submitter)
	 */
	public void removeSubmitter(Submitter submitterToRemove) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#setPrimaryIndividual(com.redbugz.maf.Individual)
	 */
	public void setPrimaryIndividual(Individual newPrimaryIndividual) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.maf.MafDocument#startSuppressUpdates()
	 */
	public void startSuppressUpdates() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
