//
//  FamilyGroupSheetView.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun May 18 2003.
//  Copyright (c) 2002-2004 RedBugz Software. All rights reserved.
//

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import com.apple.cocoa.application.*;
import com.apple.cocoa.foundation.*;
import com.redbugz.macpaf.*;
import com.redbugz.macpaf.test.MyEvent;
import com.redbugz.macpaf.test.MyIndividual;
import com.redbugz.macpaf.test.MyOrdinance;

public class FamilyGroupSheetView extends NSView {
  private static final Logger log = Logger.getLogger(FamilyGroupSheetView.class);

//   private NSMutableAttributedString nameText;
  private Individual individual;
  private static final NSFont times8 = NSFont.fontWithNameAndSize("Times-Roman", 8.0f);
  private static final NSFont times9 = NSFont.fontWithNameAndSize("Times-Roman", 9.0f);
  private static final NSFont times10 = NSFont.fontWithNameAndSize("Times-Roman", 10.0f);
  private static final NSFont times9Bold = NSFont.fontWithNameAndSize("Times-Bold", 9.0f);
  private static final NSFont times10Bold = NSFont.fontWithNameAndSize("Times-Bold", 10.0f);
  private static final NSFont times12 = NSFont.fontWithNameAndSize("Times-Roman", 12.0f);
  private static final NSFont times12Bold = NSFont.fontWithNameAndSize("Times-Bold", 12.0f);
  private static final NSDictionary times8Dict = new NSDictionary(times8, NSAttributedString.FontAttributeName);
  private static final NSDictionary times9Dict = new NSDictionary(times9, NSAttributedString.FontAttributeName);
  private static final NSDictionary times10Dict = new NSDictionary(times10, NSAttributedString.FontAttributeName);
  private static final NSDictionary times9BoldDict = new NSDictionary(times9Bold, NSAttributedString.FontAttributeName);
  private static final NSDictionary times10BoldDict = new NSDictionary(times10Bold,
	  NSAttributedString.FontAttributeName);
  private static final NSDictionary times12Dict = new NSDictionary(times12, NSAttributedString.FontAttributeName);
  private static final NSDictionary times12BoldDict = new NSDictionary(times12Bold,
	  NSAttributedString.FontAttributeName);
  private static final NSColor[] colors = {NSColor.blueColor(), NSColor.redColor(), NSColor.yellowColor(),
										  NSColor.brownColor(), NSColor.greenColor(), NSColor.cyanColor(),
										  NSColor.magentaColor(), NSColor.grayColor(),
										  NSColor.orangeColor(), NSColor.purpleColor(), NSColor.lightGrayColor(),
										  NSColor.blueColor(), NSColor.greenColor(),
										  NSColor.redColor(), NSColor.blackColor()};
  private int currColor = 0;
  private static final NSDictionary LABEL_FONT = times9Dict;
  private static final NSDictionary DATA_FONT = times10Dict;
  private static final NSDictionary DATA_BOLD_FONT = times10BoldDict;

  public FamilyGroupSheetView(NSRect frame, Individual individual) {
	super(frame);
	log.debug("FamilyGroupSheetView(frame=" + frame + " indi=" + individual.getFullName());
	// Initialization code here.
	this.individual = individual;
	//log.debug("Fonts: "+NSFontManager.sharedFontManager().availableFonts());
	//log.debug("Font families: "+NSFontManager.sharedFontManager().availableFontFamilies());
  }

  public boolean knowsPageRange(NSMutableRange nsMutableRange) {
	log.debug("knowsPageRange(range=" + nsMutableRange + ")");
	final int numChildren = individual.getPreferredFamilyAsSpouse().getChildren().size();
	if (numChildren > 5) {
	  nsMutableRange.setLength(1 + numChildren / 5);
	  log.debug("pagerange set to " + nsMutableRange + " for " + numChildren + " children.");
	  return true;
	}
	return super.knowsPageRange(nsMutableRange);
  }

  public NSRect rectForPage(int i) {
	log.debug("rectForPage(page#=" + i + ")");
	NSRect pageRect = bounds(); //NSPrintOperation.currentOperation().printInfo().imageablePageBounds();//new NSRect(0,0,600,800);//100*(i-1),800+i,100*i);
	log.debug("rectForPage rect=" + pageRect);
	return pageRect;
  }

//   public void drawPageBorderWithSize(NSSize nsSize) {
//      log.debug("drawPageBorderWithSize(size="+nsSize+")");
//      super.drawPageBorderWithSize(nsSize);
////      NSGraphics.frameRectWithWidth(new NSRect(0,0,nsSize.width(), nsSize.height()), 5);
////      NSGraphics.frameRectWithWidth(this.bounds().rectByInsettingRect(50,50), 15);
//   }

  public void drawRect(NSRect rect) {
	// Drawing code here.
	log.debug("FamilyGroupSheetView.drawRect(rect=" + rect + ")");
	int pageNum = NSPrintOperation.currentOperation().currentPage();
	log.debug("printing page #" + pageNum);
	float y = 0;
	try {
	  NSMutableParagraphStyle centeredStyle = (NSMutableParagraphStyle) NSParagraphStyle.defaultParagraphStyle().
											  mutableClone();
	  centeredStyle.setAlignment(NSText.CenterTextAlignment);
	  NSMutableDictionary centeredTitleDict = new NSMutableDictionary(times12Dict);
	  centeredTitleDict.takeValueForKey(centeredStyle, NSAttributedString.ParagraphStyleAttributeName);
	  final NSAttributedString titleStr = new NSAttributedString("Family Group Record",
		  (NSDictionary) centeredTitleDict);
	  NSGraphics.drawAttributedString(titleStr, rect);
	  rect = rect.rectByOffsettingRect(0, -NSGraphics.sizeOfAttributedString(titleStr).height());

	  NSGraphics.drawAttributedString(new NSAttributedString(new NSGregorianDateFormatter("%a, %b %e, %Y", false).
		  stringForObjectValue(new NSGregorianDate()), LABEL_FONT), rect);

	  NSMutableParagraphStyle rightAlignStyle = (NSMutableParagraphStyle) NSParagraphStyle.defaultParagraphStyle().
												mutableClone();
	  rightAlignStyle.setAlignment(NSText.RightTextAlignment);
	  NSMutableDictionary rightTimes9Dict = new NSMutableDictionary(times9Dict);
	  rightTimes9Dict.takeValueForKey(rightAlignStyle, NSAttributedString.ParagraphStyleAttributeName);
	  final NSAttributedString pageStr = new NSAttributedString("Page " + pageNum, (NSDictionary) rightTimes9Dict);
	  NSGraphics.drawAttributedString(pageStr, rect);
	  rect = rect.rectByOffsettingRect(0, -NSGraphics.sizeOfAttributedString(pageStr).height());

	  if (pageNum == 1) {
//             y += NSGraphics.sizeOfAttributedString(titleStr).height();
		final Individual husband = individual.getPreferredFamilyAsSpouse().getFather();
		final NSMutableAttributedString husbandStr = new NSMutableAttributedString("Husband: "
			/*+husband.getFullName()*/, LABEL_FONT);
		husbandStr.appendAttributedString(new NSAttributedString(husband.getFullName(), DATA_BOLD_FONT));
		NSGraphics.drawAttributedString(husbandStr, rect.rectByOffsettingRect(4, -y));
		y += NSGraphics.sizeOfAttributedString(husbandStr).height();
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);
		y += drawIndividualDetailInRect(husband, rect.rectByOffsettingRect(2, -y));
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);
		NSMutableAttributedString parentsStr = new NSMutableAttributedString("Father: ", LABEL_FONT);
		parentsStr.appendAttributedString(new NSAttributedString(husband.getFather().getFullName(), DATA_FONT));
		NSGraphics.drawAttributedString(parentsStr, rect.rectByOffsettingRect(4, -y + 1));
		parentsStr = new NSMutableAttributedString("Mother: ", LABEL_FONT);
		parentsStr.appendAttributedString(new NSAttributedString(husband.getMother().getFullName(), DATA_FONT));
		NSGraphics.drawAttributedString(parentsStr, rect.rectByOffsettingRect(220, -y + 1));
		y += NSGraphics.sizeOfAttributedString(parentsStr).height();
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 2);

		final Individual wife = individual.getPreferredFamilyAsSpouse().getMother();
		final NSMutableAttributedString wifeStr = new NSMutableAttributedString("Wife: ", LABEL_FONT);
		wifeStr.appendAttributedString(new NSAttributedString(wife.getFullName(), DATA_BOLD_FONT));
		NSGraphics.drawAttributedString(wifeStr, rect.rectByOffsettingRect(4, -y));
		y += NSGraphics.sizeOfAttributedString(wifeStr).height();
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);
		y += drawIndividualDetailInRect(wife, rect.rectByOffsettingRect(2, -y));
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);
		parentsStr = new NSMutableAttributedString("Father: ", LABEL_FONT);
		parentsStr.appendAttributedString(new NSAttributedString(wife.getFather().getFullName(), DATA_FONT));
		NSGraphics.drawAttributedString(parentsStr, rect.rectByOffsettingRect(4, -y + 1));
		parentsStr = new NSMutableAttributedString("Mother: ", LABEL_FONT);
		parentsStr.appendAttributedString(new NSAttributedString(wife.getMother().getFullName(), DATA_FONT));
		NSGraphics.drawAttributedString(parentsStr, rect.rectByOffsettingRect(220, -y + 1));
		y += NSGraphics.sizeOfAttributedString(parentsStr).height();
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 2);

		// chidren
		String childHeader = "Sex Children\nM/F";
		String childHeader2 = "List each child (living or dead)\nin order of birth";
		String childHeader3 = "LDS Ordinance Data\nfor Children";
//                 childHeader += "M/F\t\t\t\tin order of birth\t\t\t\t\t\tfor Children";
		final NSAttributedString headerStr = new NSAttributedString(childHeader, LABEL_FONT);
		final NSAttributedString headerStr2 = new NSAttributedString(childHeader2, LABEL_FONT);
		final NSAttributedString headerStr3 = new NSAttributedString(childHeader3, LABEL_FONT);
		NSGraphics.drawAttributedString(headerStr, rect.rectByOffsettingRect(4, -y));
		NSGraphics.drawAttributedString(headerStr2, rect.rectByOffsettingRect(122, -y));
		NSGraphics.drawAttributedString(headerStr3, rect.rectByOffsettingRect(322, -y));
		y += NSGraphics.sizeOfAttributedString(headerStr).height();
		NSGraphics.frameRect(new NSRect(0, rect.maxY() - y, rect.width(), y));
		int num = 1;
		for (Iterator iterator = individual.getPreferredFamilyAsSpouse().getChildren().iterator();
								 iterator.hasNext() && num <= 5; ) {
		  Individual child = (Individual) iterator.next();
		  NSMutableAttributedString numberStr = new NSMutableAttributedString(num++ +".\n", LABEL_FONT);
		  numberStr.appendAttributedString(new NSAttributedString(child.getGender().getAbbreviation(), DATA_FONT));
		  NSGraphics.drawAttributedString(numberStr, rect.rectByOffsettingRect(4, -y));
		  NSMutableAttributedString childNameStr = new NSMutableAttributedString("Name: ", LABEL_FONT);
		  childNameStr.appendAttributedString(new NSAttributedString(child.getFullName(), DATA_BOLD_FONT));
		  NSGraphics.drawAttributedString(childNameStr, rect.rectByOffsettingRect(20, -y));
		  NSMutableAttributedString spouseStr = new NSMutableAttributedString("Spouse: ", LABEL_FONT);
		  spouseStr.appendAttributedString(new NSAttributedString(child.getPreferredSpouse().getFullName(), DATA_FONT));
		  NSGraphics.drawAttributedString(spouseStr, rect.rectByOffsettingRect(220, -y));
		  y += NSGraphics.sizeOfAttributedString(childNameStr).height();
		  NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);
		  y += drawIndividualDetailInRect(child, rect.rectByOffsettingRect(20, -y), true);
		  NSGraphics.frameRect(new NSRect(0, rect.maxY() - y, rect.width(), y));
		}
		while (num <= 5) {
		  NSMutableAttributedString numberStr = new NSMutableAttributedString(num++ +".\n", LABEL_FONT);
		  numberStr.appendAttributedString(new NSAttributedString(" ", DATA_FONT));
		  NSGraphics.drawAttributedString(numberStr, rect.rectByOffsettingRect(4, -y));
		  NSMutableAttributedString childNameStr = new NSMutableAttributedString("Name: ", LABEL_FONT);
		  childNameStr.appendAttributedString(new NSAttributedString(" ", DATA_BOLD_FONT));
		  NSGraphics.drawAttributedString(childNameStr, rect.rectByOffsettingRect(20, -y));
		  NSMutableAttributedString spouseStr = new NSMutableAttributedString("Spouse: ", LABEL_FONT);
		  spouseStr.appendAttributedString(new NSAttributedString(" ", DATA_FONT));
		  NSGraphics.drawAttributedString(spouseStr, rect.rectByOffsettingRect(220, -y));
		  y += NSGraphics.sizeOfAttributedString(childNameStr).height();
		  NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);
		  y += drawIndividualDetailInRect(null, rect.rectByOffsettingRect(20, -y), true);
		  NSGraphics.frameRect(new NSRect(0, rect.maxY() - y, rect.width(), y));
		}
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y - 1, rect.width(), y), 2);
		final NSAttributedString submitterStr = new NSAttributedString("Name and Address of Submitter: ", LABEL_FONT);
		NSGraphics.drawAttributedString(submitterStr, rect.rectByOffsettingRect(0, -y));
	  }
	  else { // print all pages after one
//             y += NSGraphics.sizeOfAttributedString(titleStr).height();
		final Individual husband = individual.getPreferredFamilyAsSpouse().getFather();
		final NSMutableAttributedString husbandStr = new NSMutableAttributedString("Husband: "
			/*+husband.getFullName()*/, LABEL_FONT);
		husbandStr.appendAttributedString(new NSAttributedString(husband.getFullName(), DATA_BOLD_FONT));
		NSGraphics.drawAttributedString(husbandStr, rect.rectByOffsettingRect(4, -y));
		NSMutableAttributedString parentYearStr = new NSMutableAttributedString("Year of Birth: ", LABEL_FONT);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(husband.getBirthEvent().getDate());
		parentYearStr.appendAttributedString(new NSAttributedString("" + cal.get(GregorianCalendar.YEAR),
			DATA_BOLD_FONT));
		NSGraphics.drawAttributedString(parentYearStr, rect.rectByOffsettingRect(322, -y));
		y += NSGraphics.sizeOfAttributedString(husbandStr).height();
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);

		final Individual wife = individual.getPreferredFamilyAsSpouse().getMother();
		final NSMutableAttributedString wifeStr = new NSMutableAttributedString("Wife: ", LABEL_FONT);
		wifeStr.appendAttributedString(new NSAttributedString(wife.getFullName(), DATA_BOLD_FONT));
		NSGraphics.drawAttributedString(wifeStr, rect.rectByOffsettingRect(4, -y));
		parentYearStr = new NSMutableAttributedString("Year of Birth: ", LABEL_FONT);
		cal.setTime(wife.getBirthEvent().getDate());
		parentYearStr.appendAttributedString(new NSAttributedString("" + cal.get(GregorianCalendar.YEAR),
			DATA_BOLD_FONT));
		NSGraphics.drawAttributedString(parentYearStr, rect.rectByOffsettingRect(322, -y));
		y += NSGraphics.sizeOfAttributedString(wifeStr).height();
		NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 2);

		// chidren
		int num = 6 + (pageNum - 2) * 7;
		List childrenForPage = individual.getPreferredFamilyAsSpouse().getChildren().subList(num - 1,
			Math.min(individual.getPreferredFamilyAsSpouse().getChildren().size(), num - 1 + 7));
		String childHeader = "Sex Children\nM/F";
		String childHeader2 = "List each child (living or dead)\nin order of birth";
		String childHeader3 = "LDS Ordinance Data\nfor Children";
//                 childHeader += "M/F\t\t\t\tin order of birth\t\t\t\t\t\tfor Children";
		final NSAttributedString headerStr = new NSAttributedString(childHeader, LABEL_FONT);
		final NSAttributedString headerStr2 = new NSAttributedString(childHeader2, LABEL_FONT);
		final NSAttributedString headerStr3 = new NSAttributedString(childHeader3, LABEL_FONT);
		NSGraphics.drawAttributedString(headerStr, rect.rectByOffsettingRect(4, -y));
		NSGraphics.drawAttributedString(headerStr2, rect.rectByOffsettingRect(122, -y));
		NSGraphics.drawAttributedString(headerStr3, rect.rectByOffsettingRect(322, -y));
		y += NSGraphics.sizeOfAttributedString(headerStr).height();
		NSGraphics.frameRect(new NSRect(0, rect.maxY() - y, rect.width(), y));
		for (Iterator iterator = childrenForPage.iterator(); iterator.hasNext(); ) {
		  Individual child = (Individual) iterator.next();
		  NSMutableAttributedString numberStr = new NSMutableAttributedString(num++ +".\n", LABEL_FONT);
		  numberStr.appendAttributedString(new NSAttributedString(child.getGender().getAbbreviation(), DATA_FONT));
		  NSGraphics.drawAttributedString(numberStr, rect.rectByOffsettingRect(4, -y));
		  NSMutableAttributedString childNameStr = new NSMutableAttributedString("Name: ", LABEL_FONT);
		  childNameStr.appendAttributedString(new NSAttributedString(child.getFullName(), DATA_BOLD_FONT));
		  NSGraphics.drawAttributedString(childNameStr, rect.rectByOffsettingRect(20, -y));
		  NSMutableAttributedString spouseStr = new NSMutableAttributedString("Spouse: ", LABEL_FONT);
		  spouseStr.appendAttributedString(new NSAttributedString(child.getPreferredSpouse().getFullName(), DATA_FONT));
		  NSGraphics.drawAttributedString(spouseStr, rect.rectByOffsettingRect(220, -y));
		  y += NSGraphics.sizeOfAttributedString(childNameStr).height();
		  NSGraphics.frameRectWithWidth(new NSRect(0, rect.maxY() - y, rect.width(), y), 0.1f);
		  y += drawIndividualDetailInRect(child, rect.rectByOffsettingRect(20, -y), true);
		  NSGraphics.frameRect(new NSRect(0, rect.maxY() - y, rect.width(), y));
		}
	  }

	}
	catch (Exception e) {
	  log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	}
  }

  private float drawIndividualDetailInRect(Individual individual, NSRect rect) {
	return drawIndividualDetailInRect(individual, rect, false);
  }

  private float drawIndividualDetailInRect(Individual individual, NSRect rect, boolean isChild) {
	log.debug("drawIndividualDetailInRect(" + individual + ", " + rect + ")");
	if (individual == null) {
	  individual = new MyIndividual();
//      colors[currColor++].set();
//      NSGraphics.frameRectWithWidth(rect, 3);
	}
	rect = rect.rectByInsettingRect(2, 0);
	final NSAttributedString eventStr = makeIndividualEventString(individual, isChild);
	NSGraphics.drawAttributedString(eventStr, rect);
	NSGraphics.drawAttributedString(makeIndividualPlaceString(individual, isChild), rect.rectByOffsettingRect(100, 0));
	NSGraphics.drawAttributedString(makeIndividualOrdinanceString(individual, isChild),
									rect.rectByOffsettingRect(300, 0));
	return NSGraphics.sizeOfAttributedString(eventStr).height();
  }

  private NSAttributedString makeIndividualEventString(Individual individual, boolean isChild) {
	Event birthEvent = individual.getBirthEvent();
	if (birthEvent == null) {birthEvent = new MyEvent();
	}
	NSMutableAttributedString detailStr = new NSMutableAttributedString("Born: ", LABEL_FONT);
	detailStr.appendAttributedString(new NSAttributedString(birthEvent.getDateString() + " \n", DATA_FONT));
	Event christeningEvent = individual.getChristeningEvent();
	if (christeningEvent == null) {christeningEvent = new MyEvent();
	}
	detailStr.appendAttributedString(new NSAttributedString("Chr.: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(christeningEvent.getDateString() + " \n", DATA_FONT));
	Event deathEvent = individual.getDeathEvent();
	if (deathEvent == null) {deathEvent = new MyEvent();
	}
	detailStr.appendAttributedString(new NSAttributedString("Died: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(deathEvent.getDateString() + " \n", DATA_FONT));
	Event burialEvent = individual.getBurialEvent();
	if (burialEvent == null) {burialEvent = new MyEvent();
	}
	detailStr.appendAttributedString(new NSAttributedString("Bur.: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(burialEvent.getDateString() + " ", DATA_FONT));
	if (isChild || individual.getGender().equals(Gender.MALE)) {
	  Event marriageEvent = individual.getPreferredFamilyAsSpouse().getMarriageEvent();
	  if (marriageEvent == null) {marriageEvent = new MyEvent();
	  }
	  detailStr.appendAttributedString(new NSAttributedString("\nMarr: ", LABEL_FONT));
	  detailStr.appendAttributedString(new NSAttributedString(marriageEvent.getDateString() + " ", DATA_FONT));
	}
	return detailStr;
  }

  private NSAttributedString makeIndividualPlaceString(Individual individual, boolean isChild) {
	Event birthEvent = individual.getBirthEvent();
	if (birthEvent == null) {birthEvent = new MyEvent();
	}
	NSMutableAttributedString detailStr = new NSMutableAttributedString("Place: ", LABEL_FONT);
	detailStr.appendAttributedString(new NSAttributedString(birthEvent.getPlace().getFormatString() + " \n", DATA_FONT));
	Event christeningEvent = individual.getChristeningEvent();
	if (christeningEvent == null) {christeningEvent = new MyEvent();
	}
	detailStr.appendAttributedString(new NSAttributedString("Place: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(christeningEvent.getPlace().getFormatString() + " \n",
		DATA_FONT));
	Event deathEvent = individual.getDeathEvent();
	if (deathEvent == null) {deathEvent = new MyEvent();
	}
	detailStr.appendAttributedString(new NSAttributedString("Place: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(deathEvent.getPlace().getFormatString() + " \n", DATA_FONT));
	Event burialEvent = individual.getBurialEvent();
	if (burialEvent == null) {burialEvent = new MyEvent();
	}
	detailStr.appendAttributedString(new NSAttributedString("Place: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(burialEvent.getPlace().getFormatString() + " ", DATA_FONT));
	if (isChild || individual.getGender().equals(Gender.MALE)) {
	  Event marriageEvent = individual.getPreferredFamilyAsSpouse().getMarriageEvent();
	  if (marriageEvent == null) {marriageEvent = new MyEvent();
	  }
	  detailStr.appendAttributedString(new NSAttributedString("\nPlace: ", LABEL_FONT));
	  detailStr.appendAttributedString(new NSAttributedString(marriageEvent.getPlace().getFormatString() + " ",
		  DATA_FONT));
	}
	return detailStr;
  }

  private NSAttributedString makeIndividualOrdinanceString(Individual individual, boolean isChild) {
	Ordinance baptismEvent = individual.getLDSBaptism();
	if (baptismEvent == null) {baptismEvent = new MyOrdinance();
	}
	NSMutableAttributedString detailStr = new NSMutableAttributedString("Bap.: ", LABEL_FONT);
	detailStr.appendAttributedString(new NSAttributedString(baptismEvent.getDateString() + "  " +
		baptismEvent.getTemple().getCode() + "\n", DATA_FONT));
	Ordinance endowmentEvent = individual.getLDSEndowment();
	if (endowmentEvent == null) {endowmentEvent = new MyOrdinance();
	}
	detailStr.appendAttributedString(new NSAttributedString("End.: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(endowmentEvent.getDateString() + "  " +
		endowmentEvent.getTemple().getCode(), DATA_FONT));
	Ordinance sealingParentsEvent = individual.getLDSSealingToParents();
	if (sealingParentsEvent == null) {sealingParentsEvent = new MyOrdinance();
	}
	detailStr.appendAttributedString(new NSAttributedString("\nSlg P: ", LABEL_FONT));
	detailStr.appendAttributedString(new NSAttributedString(sealingParentsEvent.getDateString() + "  " +
		sealingParentsEvent.getTemple().getCode(), DATA_FONT));
	if (isChild || individual.getGender().equals(Gender.MALE) /* || currColor++%2==0*/) {
	  Ordinance sealingSpouseEvent = individual.getPreferredFamilyAsSpouse().getSealingToSpouse();
	  if (sealingSpouseEvent == null) {sealingSpouseEvent = new MyOrdinance();
	  }
	  detailStr.appendAttributedString(new NSAttributedString("\n\nSlg S: ", LABEL_FONT));
	  detailStr.appendAttributedString(new NSAttributedString(sealingSpouseEvent.getDateString() + "  " +
		  sealingSpouseEvent.getTemple().getCode(), DATA_FONT));
	}
	return detailStr;
  }

}
