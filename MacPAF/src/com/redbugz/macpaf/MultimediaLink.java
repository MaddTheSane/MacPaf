package com.redbugz.macpaf;
import java.util.List;

/*
 * Created on Oct 5, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * @author logan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface MultimediaLink {
	public boolean isEmbedded();

	public Multimedia getMultimedia();

	public String getFormat();
	public String getTitle();
	public String getFileReference();
	public List getNotes(); // List of Note
}
