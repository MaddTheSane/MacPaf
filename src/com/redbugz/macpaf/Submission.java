package com.redbugz.macpaf;

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
public interface Submission {
  public Submitter getSubmitter();

  public String getFamilyFile();

  public String getTempleCode();

  public Integer getAncestorsGenerations();

  public Integer getDescendantsGenerations();

  public Boolean getOrdinanceProcessFlag();

  public Integer getRIN();
}
