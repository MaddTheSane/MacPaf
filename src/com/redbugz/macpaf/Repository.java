package com.redbugz.macpaf;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Apr 13, 2003
 * Time: 2:43:52 PM
 * To change this template use Options | File Templates.
 */
public interface Repository {
	public static final Repository UNKNOWN_REPOSITORY = new UnknownRepository();
  public static final String REPOSITORY = "REPO";
  public static final String NAME = "NAME";
  public static final String REFERENCE = "REFN";
  public static final String REFERENCE_TYPE = "TYPE";
  public static final String ID = "ID";
  public static final String REF = "REF";
  public static final String RIN = "RIN";

  public String getId();
  public void setId(String string);

  public String getName();
  
  static class UnknownRepository implements Repository {

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Repository#getId()
	 */
	public String getId() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Repository#getName()
	 */
	public String getName() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Repository#setId(java.lang.String)
	 */
	public void setId(String string) {
		// TODO Auto-generated method stub
		
	}
  	
  }
}
