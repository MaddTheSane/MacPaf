package com.redbugz.macpaf;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Apr 13, 2003
 * Time: 2:43:52 PM
 * To change this template use Options | File Templates.
 */
public interface Note {
  public static final String NOTE = "NOTE";
  public static final String CONCATENATION = "CONC";
  public static final String CONTINUATION = "CONT";
  public static final String ID = "ID";
  public static final String REF = "REF";

  public String getId();

  public String getText();
}
