package com.redbugz.macpaf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.apple.cocoa.foundation.NSObject;

public interface MafDocument extends Observer {

	public void addFamily(Family newFamily);

	public void addIndividual(Individual newIndividual);

	/**
	 * @param submitter
	 */
	public void addSubmitter(Submitter newSubmitter);

	public void removeSubmitter(Submitter submitterToRemove);

	/**
	 * @param repository
	 */
	public void addRepository(Repository newRepository);

	/**
	 * @param source
	 */
	public void addSource(Source newSource);

	/**
	 * addNote
	 *
	 * @param newNote Note
	 */
	public void addNote(Note newNote);

	/**
	 * addMultimedia
	 *
	 * @param newMultimedia Multimedia
	 */
	public void addMultimedia(Multimedia newMultimedia);

	/**
	 * outputToXML
	 *
	 * @param outputStream OutputStream
	 */
	public void outputToXML(OutputStream outputStream)
			throws IOException;

	/**
	 * outputToXML
	 *
	 * @param outputStream OutputStream
	 */
	public void outputToGedcom(OutputStream outputStream);

	/**
	 * setPrimaryIndividual
	 *
	 * @param newPrimaryIndividual Individual
	 */
	public void setPrimaryIndividual(Individual newPrimaryIndividual);

	/**
	 * createNewIndividual
	 *
	 * @return Individual
	 */
	public Individual createAndInsertNewIndividual();

	/**
	 * createNewFamily
	 *
	 * @return Family
	 */
	public Family createAndInsertNewFamily();

	public void removeIndividual(Individual individualToRemove);

	public void chooseNewPrimaryIndividual();

	public void removeFamily(Family familyToRemove);

	public Map getFamiliesMap();

	public Map getIndividualsMap();

	public Map getNotesMap();

	public Map getSourcesMap();

	public Map getRepositoriesMap();

	public Map getMultimediaMap();

	public Map getSubmittersMap();


	/**
	 * @param id
	 * @return
	 */
	public Family getFamily(String id);

	/**
	 * @param id
	 * @return
	 */
	public Individual getIndividual(String id);

	/**
	 * @param id
	 * @return
	 */
	public Multimedia getMultimedia(String id);

	/**
	 * @param id
	 * @return
	 */
	public Note getNote(String id);

	/**
	 * @param id
	 * @return
	 */
	public Source getSource(String id);

	/**
	 * @param id
	 * @return
	 */
	public Repository getRepository(String id);

	/**
	 * @param id
	 * @return
	 */
	public Submitter getSubmitter(String id);

	/**
	 * @return Returns the nextFamilyId.
	 */
	public int getNextAvailableFamilyId();

	/**
	 * @return Returns the nextIndividualId.
	 */
	public int getNextAvailableIndividualId();

	/**
	 * @return Returns the nextMultimediaId.
	 */
	public int getNextAvailableMultimediaId();

	/**
	 * @return Returns the nextNoteId.
	 */
	public int getNextAvailableNoteId();

	/**
	 * @return Returns the nextRepositoryId.
	 */
	public int getNextAvailableRepositoryId();

	/**
	 * @return Returns the nextSourcesId.
	 */
	public int getNextAvailableSourceId();

	/**
	 * @return Returns the nextSubmitterId.
	 */
	public int getNextAvailableSubmitterId();

	/**
	 * @return
	 */
	public Individual getPrimaryIndividual();

	/**
	 * @return
	 */
	public Note createAndInsertNewNote();

	/**
	 * @return
	 */
	public Submitter createAndInsertNewSubmitter();

	/**
	 * @return
	 */
	public Multimedia createAndInsertNewMultimedia();

	/**
	 * @return
	 */
	public Source createAndInsertNewSource();

	/**
	 * @return
	 */
	public Repository createAndInsertNewRepository();

	public void outputToTempleReady(FileOutputStream stream);

	public Submission getSubmission();

	public Header getHeader();

	public void startSuppressUpdates();

	public void endSuppressUpdates();

	public void importFromDocument(MafDocument importDocument);

	public void addObserver(Observer observer);

	public void importGedcom(File importFile, NSObject progress);

}