package com.redbugz.macpaf;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Apr 13, 2003
 * Time: 2:43:52 PM
 * To change this template use Options | File Templates.
 */
public interface Note {
	public static final Note UNKNOWN_NOTE = new UnknownNote();
	
  public static final String NOTE = "NOTE";
  public static final String CONCATENATION = "CONC";
  public static final String CONTINUATION = "CONT";
  public static final String ID = "ID";
  public static final String REF = "REF";

  public String getId();
  public void setId(String string);

  public String getText();
  
  static class UnknownNote implements Note {

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Note#getId()
	 */
	public String getId() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Note#getText()
	 */
	public String getText() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Note#setId(java.lang.String)
	 */
	public void setId(String string) {
		// TODO Auto-generated method stub
		
	}
  	
  }
}
