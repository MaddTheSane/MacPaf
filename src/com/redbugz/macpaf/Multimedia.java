package com.redbugz.macpaf;
import java.util.Date;
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
public interface Multimedia {
	public String getId();
	public void setId(String id);
	
	public String getFormat();
	public void setFormat(String format);
	
	public String getTitle();
	public void setTitle(String title);
	
	public List getNotes(); // List of Note
	public void setNotes(List noteList);
	
	public byte[] getBytes();
	public void setBytes(byte[] bytes);
	
	public Integer getRIN();
	public void setRIN(Integer rin);
	
	public Date getChangeDate();
	public void setChangeDate(Date changeDate);
}
