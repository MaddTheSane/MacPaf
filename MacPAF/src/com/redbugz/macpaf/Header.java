package com.redbugz.macpaf;

import java.util.Date;

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
public interface Header {
  public static final String HEADER = "HEAD";
  public static final String SOURCE = "SOUR";
  public static final String VERSION = "VERS";
  public static final String NAME = "NAME";
  public static final String CORPORATION = "CORP";
  public static final String DESTINATION = "DEST";
  public static final String DATA = "DATA";
  public static final String DATE = "DATE";
  public static final String SUBMITTER = "SUBM";
  public static final String SUBMISSION = "SUBN";
  public static final String FILENAME = "FILE";
  public static final String COPYRIGHT = "COPR";
  public static final String GEDCOM = "GEDC";
  public static final String LANGUAGE = "LANG";
  public static final String CHARACTER = "CHAR";
  public static final String FORM = "FORM";
  public static final String PLACE = "PLAC";
  public static final String ID = "ID";
  public static final String REF = "REF";

  public static final String ASCII = "ASCII";
  public static final String ANSEL = "ANSEL";
  public static final String UNICODE = "UNICODE";
  public static final String FORM_LINEAGE_LINKED = "LINEAGE-LINKED";
  public static final String DEST_ANCESTRAL_FILE = "ANSTFILE";
  public static final String DEST_TEMPLE_READY = "TempleReady";
  public static final String GEDCOM_VERSION_55 = "5.5";
  public static final String LANG_ENGLISH = "English";

  public static final String MACPAF_APPROVED_SYSTEM_ID = "MacPAF";
  public static final String MACPAF_NAME_OF_PRODUCT = "MacPAF";
  public static final String MACPAF_VERSION_NUMBER = "1.0";
  public static final String MACPAF_NAME_OF_BUSINESS = "RedBugz Software";
  public static final String MACPAF_BUSINESS_ADDR1 = "258 E 900 N";
  public static final String MACPAF_BUSINESS_ADDR2 = "";
  public static final String MACPAF_BUSINESS_CITY = "American Fork";
  public static final String MACPAF_BUSINESS_STATE = "UT";
  public static final String MACPAF_BUSINESS_ZIP = "84003";
  public static final String MACPAF_BUSINESS_COUNTRY = "USA";
  public static final String MACPAF_BUSINESS_PHONE = "(801) 756-7568";

  /* OTHER LANGUAGES TO SUPPORT
	  Afrikaans | Albanian | Anglo-Saxon | Catalan | Catalan_Spn | Czech | Danish | Dutch | English | Esperanto | Estonian | Faroese | Finnish | French | German | Hawaiian | Hungarian | Icelandic | Indonesian | Italian | Latvian | Lithuanian | Navaho | Norwegian | Polish | Portuguese | Romanian | Serbo_Croa | Slovak | Slovene | Spanish | Swedish | Turkish | Wendic ]

   Other languages not supported until UNICODE
   [Amharic | Arabic | Armenian | Assamese | Belorusian | Bengali | Braj | Bulgarian | Burmese | Cantonese | Church-Slavic | Dogri | Georgian | Greek | Gujarati | Hebrew | Hindi | Japanese | Kannada | Khmer | Konkani | Korean | Lahnda | Lao | Macedonian | Maithili | Malayalam | Mandrin | Manipuri | Marathi | Mewari | Nepali | Oriya | Pahari | Pali | Panjabi | Persian | Prakrit | Pusto | Rajasthani | Russian | Sanskrit | Serb | Tagalog | Tamil | Telugu | Thai | Tibetan | Ukrainian | Urdu | Vietnamese | Yiddish ]
   */

  public String getSourceId();

  public String getSourceVersion();

  public String getSourceName();

  public String getSourceCorporation();

  public Address getSourceCorporationAddress();

  public String getSourceData();

  public Date getSourceDataDate();

  public String getSourceDataCopyright();

  public String getDestination();
  
  public void setDestination(String destinationString);

  public Date getCreationDate();

  public Submitter getSubmitter();

  public Submission getSubmission();

  public String getFileName();

  public String getCopyright();

  public String getGedcomVersion();

  public String getGedcomForm();

  public String getCharacterSet();

  public String getCharacterVersionNumber();

  public String getLanguage();

  public String getPlaceFormat();

  public Note getNote();

}
