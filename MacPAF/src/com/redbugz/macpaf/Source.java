package com.redbugz.macpaf;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Apr 13, 2003
 * Time: 2:43:52 PM
 * To change this template use Options | File Templates.
 */
public interface Source {
	public static final Source UNKNOWN_SOURCE = new UnknownSource();
	
  public static final String SOURCE = "SOUR";
  public static final String DATA = "DATA";
  public static final String SOURCE_ORIGINATOR = "AUTH";
  public static final String CONCATENATION = "CONC";
  public static final String CONTINUATION = "CONT";
  public static final String ID = "ID";
  public static final String REF = "REF";
  public static final String REFERENCE = "REFN";
  public static final String REFERENCE_TYPE = "TYPE";
  public static final String AGENCY = "AGNC";
  public static final String TITLE = "TITL";
  public static final String FILED = "ABBR";
  public static final String PUBLICATION = "PUBL";
  public static final String TEXT = "TEXT";

//  +1 DATA        {0:1}
//  +2 EVEN <EVENTS_RECORDED>  {0:M}
//    +3 DATE <DATE_PERIOD>  {0:1}
//    +3 PLAC <SOURCE_JURISDICTION_PLACE>  {0:1}
//  +2 AGNC <RESPONSIBLE_AGENCY>  {0:1}
//  +2 <<NOTE_STRUCTURE>>  {0:M}
//+1 AUTH <SOURCE_ORIGINATOR>  {0:1}
//  +2 [CONT|CONC] <SOURCE_ORIGINATOR>  {0:M}
//+1 TITL <SOURCE_DESCRIPTIVE_TITLE>  {0:1}
//  +2 [CONT|CONC] <SOURCE_DESCRIPTIVE_TITLE>  {0:M}
//+1 ABBR <SOURCE_FILED_BY_ENTRY>  {0:1}
//+1 PUBL <SOURCE_PUBLICATION_FACTS>  {0:1}
//  +2 [CONT|CONC] <SOURCE_PUBLICATION_FACTS>  {0:M}
//+1 TEXT <TEXT_FROM_SOURCE>  {0:1}
//  +2 [CONT|CONC] <TEXT_FROM_SOURCE>  {0:M}
//+1 <<SOURCE_REPOSITORY_CITATION>>  {0:1}
//+1 <<MULTIMEDIA_LINK>>  {0:M}
//+1 <<NOTE_STRUCTURE>>  {0:M}
//+1 REFN <USER_REFERENCE_NUMBER>  {0:M}
//  +2 TYPE <USER_REFERENCE_TYPE>  {0:1}
//+1 RIN <AUTOMATED_RECORD_ID>  {0:1}
//+1 <<CHANGE_DATE>>  {0:1}

  public String getId();

  public String getText();
  public String getTitle();
  
  static class UnknownSource implements Source {

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Source#getId()
	 */
	public String getId() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Source#getText()
	 */
	public String getText() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.redbugz.macpaf.Source#getTitle()
	 */
	public String getTitle() {
		return "";
	}
  	
  }
}
