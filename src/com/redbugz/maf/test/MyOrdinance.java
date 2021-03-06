package com.redbugz.maf.test;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.redbugz.maf.Ordinance;
import com.redbugz.maf.Temple;
import com.redbugz.maf.TempleList;

/**
 * Created by IntelliJ IDEA.
 * User: logan
 * Date: Mar 16, 2003
 * Time: 3:40:34 PM
 * To change this template use Options | File Templates.
 */
public class MyOrdinance extends MyEvent implements Ordinance {
  private static final Logger log = Logger.getLogger(MyOrdinance.class);
  String status = "Incomplete";
  private Temple temple = new Temple();

  public MyOrdinance() {
  }

//   public MyOrdinance(Date ordinanceDate, Temple temple) {
//      this.ordinanceDate = ordinanceDate;
//      this.temple = temple;
//   }

  public MyOrdinance(Element element) {
	super(element);
	if (element != null) {
	  status = "Complete";
	  if (element.getChild("TEMP") != null) {
		temple = TempleList.templeWithCode(element.getChildText("TEMP"));
	  }
	}
	log.debug("MyOrdinance status=" + status + " temple=" + temple.getName());
  }

  public Temple getTemple() {
	return temple;
  }

  public boolean isCompleted() {
	return status.equals("Complete");
  }

  /* (non-Javadoc)
   * @see com.redbugz.maf.Ordinance#setTemple(com.redbugz.maf.Temple)
   */
  public void setTemple(Temple temple) {
	// TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see com.redbugz.maf.Ordinance#getStatus()
   */
  public String getStatus() {
	// TODO Auto-generated method stub
	return null;
  }

  /* (non-Javadoc)
   * @see com.redbugz.maf.Ordinance#setStatus(java.lang.String)
   */
  public void setStatus(String status) {
	// TODO Auto-generated method stub

  }
}
