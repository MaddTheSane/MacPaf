/**
 * 
 */
package com.redbugz.macpaf.gdbi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

//import org.gdbi.db.pgvcgi.PgvcgiDatabase;

import com.apple.cocoa.foundation.NSObject;
import com.redbugz.macpaf.Family;
import com.redbugz.macpaf.Header;
import com.redbugz.macpaf.Individual;
import com.redbugz.macpaf.MafDocument;
import com.redbugz.macpaf.Multimedia;
import com.redbugz.macpaf.Note;
import com.redbugz.macpaf.Repository;
import com.redbugz.macpaf.Source;
import com.redbugz.macpaf.Submission;
import com.redbugz.macpaf.Submitter;

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
	 * @see com.redbugz.macpaf.MafDocument#addFamily(com.redbugz.macpaf.Family)
	 */
	public void addFamily(Family newFamily) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#addIndividual(com.redbugz.macpaf.Individual)
	 */
	public void addIndividual(Individual newIndividual) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#addMultimedia(com.redbugz.macpaf.Multimedia)
	 */
	public void addMultimedia(Multimedia newMultimedia) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#addNote(com.redbugz.macpaf.Note)
	 */
	public void addNote(Note newNote) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#addObserver(java.util.Observer)
	 */
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#addRepository(com.redbugz.macpaf.Repository)
	 */
	public void addRepository(Repository newRepository) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#addSource(com.redbugz.macpaf.Source)
	 */
	public void addSource(Source newSource) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#addSubmitter(com.redbugz.macpaf.Submitter)
	 */
	public void addSubmitter(Submitter newSubmitter) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#chooseNewPrimaryIndividual()
	 */
	public void chooseNewPrimaryIndividual() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#createAndInsertNewFamily()
	 */
	public Family createAndInsertNewFamily() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#createAndInsertNewIndividual()
	 */
	public Individual createAndInsertNewIndividual() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#createAndInsertNewMultimedia()
	 */
	public Multimedia createAndInsertNewMultimedia() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#createAndInsertNewNote()
	 */
	public Note createAndInsertNewNote() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#createAndInsertNewRepository()
	 */
	public Repository createAndInsertNewRepository() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#createAndInsertNewSource()
	 */
	public Source createAndInsertNewSource() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#createAndInsertNewSubmitter()
	 */
	public Submitter createAndInsertNewSubmitter() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#endSuppressUpdates()
	 */
	public void endSuppressUpdates() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getFamiliesMap()
	 */
	public Map getFamiliesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getFamily(java.lang.String)
	 */
	public Family getFamily(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getHeader()
	 */
	public Header getHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getIndividual(java.lang.String)
	 */
	public Individual getIndividual(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getIndividualsMap()
	 */
	public Map getIndividualsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getMultimedia(java.lang.String)
	 */
	public Multimedia getMultimedia(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getMultimediaMap()
	 */
	public Map getMultimediaMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNextAvailableFamilyId()
	 */
	public int getNextAvailableFamilyId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNextAvailableIndividualId()
	 */
	public int getNextAvailableIndividualId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNextAvailableMultimediaId()
	 */
	public int getNextAvailableMultimediaId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNextAvailableNoteId()
	 */
	public int getNextAvailableNoteId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNextAvailableRepositoryId()
	 */
	public int getNextAvailableRepositoryId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNextAvailableSourceId()
	 */
	public int getNextAvailableSourceId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNextAvailableSubmitterId()
	 */
	public int getNextAvailableSubmitterId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNote(java.lang.String)
	 */
	public Note getNote(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getNotesMap()
	 */
	public Map getNotesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getPrimaryIndividual()
	 */
	public Individual getPrimaryIndividual() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getRepositoriesMap()
	 */
	public Map getRepositoriesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getRepository(java.lang.String)
	 */
	public Repository getRepository(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getSource(java.lang.String)
	 */
	public Source getSource(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getSourcesMap()
	 */
	public Map getSourcesMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getSubmission()
	 */
	public Submission getSubmission() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getSubmitter(java.lang.String)
	 */
	public Submitter getSubmitter(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#getSubmittersMap()
	 */
	public Map getSubmittersMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#importFromDocument(com.redbugz.macpaf.MafDocument)
	 */
	public void importFromDocument(MafDocument importDocument) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#importGedcom(java.io.File, com.apple.cocoa.foundation.NSObject)
	 */
	public void importGedcom(File importFile, NSObject progress) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#outputToGedcom(java.io.OutputStream)
	 */
	public void outputToGedcom(OutputStream outputStream) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#outputToTempleReady(java.io.FileOutputStream)
	 */
	public void outputToTempleReady(FileOutputStream stream) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#outputToXML(java.io.OutputStream)
	 */
	public void outputToXML(OutputStream outputStream) throws IOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#removeFamily(com.redbugz.macpaf.Family)
	 */
	public void removeFamily(Family familyToRemove) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#removeIndividual(com.redbugz.macpaf.Individual)
	 */
	public void removeIndividual(Individual individualToRemove) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#removeSubmitter(com.redbugz.macpaf.Submitter)
	 */
	public void removeSubmitter(Submitter submitterToRemove) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#setPrimaryIndividual(com.redbugz.macpaf.Individual)
	 */
	public void setPrimaryIndividual(Individual newPrimaryIndividual) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.MafDocument#startSuppressUpdates()
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
