//
//  PocketPedigreeView.java
//  MacPAF
//
//  Created by Logan Allred on Sun May 30 2004.
//  Copyright (c) 2004 __MyCompanyName__. All rights reserved.
//

import com.apple.cocoa.foundation.*;
import com.apple.cocoa.application.*;
import com.redbugz.macpaf.Individual;
import org.apache.log4j.Logger;
import com.redbugz.macpaf.Ordinance;
import com.redbugz.macpaf.Event;
import com.redbugz.macpaf.Gender;
import com.apple.cocoa.application.NSFont;
import com.redbugz.macpaf.Place;
import com.redbugz.macpaf.test.MyEvent;


public class PocketPedigreeView extends NSView {
	private static final Logger log = Logger.getLogger(PedigreeView.class);

//   private NSMutableAttributedString nameText;
	private Individual individual = Individual.UNKNOWN;
	private int generations = 6;
	private static final NSFont times9 = NSFont.fontWithNameAndSize("Times-Roman", 9.0f);
	private static final NSFont times8 = NSFont.fontWithNameAndSize("Times-Roman", 8.0f);
//	private static final NSFont narrow9 = NSFont.fontWithNameAndSize("Futura-CondensedMedium", 9.0f);
//	private static final NSFont narrow8 = NSFont.fontWithNameAndSize("Futura-CondensedMedium", 8.0f);
	private static final NSFont narrow9 = NSFont.fontWithNameAndSize("LiGothicMed", 9.0f);
	private static final NSFont narrow8 = NSFont.fontWithNameAndSize("LiGothicMed", 8.0f);
//	private static final NSFont narrow9 = NSFont.fontWithNameAndSize("DomCasualD-Regu", 9.0f);
//	private static final NSFont narrow9 = NSFont.fontWithNameAndSize("Sedona", 9.0f);
	private static final NSFont qt9 = NSFont.fontWithNameAndSize("LiGothicMed", 9.0f);
	private static final NSFont times12 = NSFont.fontWithNameAndSize("Times-Roman", 12.0f);
	private static final NSDictionary times9Dict = new NSDictionary(times9, NSAttributedString.FontAttributeName);
	private static final NSDictionary narrow9Dict = new NSDictionary(narrow9, NSAttributedString.FontAttributeName);
	private static final NSDictionary times8Dict = new NSDictionary(times8, NSAttributedString.FontAttributeName);
	private static final NSDictionary narrow8Dict = new NSDictionary(narrow8, NSAttributedString.FontAttributeName);
	private static final NSDictionary qt9Dict = new NSDictionary(qt9, NSAttributedString.FontAttributeName);
	private static NSDictionary nameFontDict = times9Dict;
	private static NSDictionary detailFontDict = times8Dict;
	private static final NSColor[] colors = {NSColor.blueColor(), NSColor.redColor(), NSColor.yellowColor(),
											NSColor.brownColor(), NSColor.greenColor(), NSColor.cyanColor(),
											NSColor.magentaColor(), NSColor.grayColor(),
											NSColor.orangeColor(), NSColor.purpleColor(), NSColor.lightGrayColor(),
											NSColor.blueColor(), NSColor.greenColor(),
											NSColor.redColor(), NSColor.blackColor()};
	private int currColor = 0;

	public PocketPedigreeView(NSRect frameRect) {
	  super(frameRect);
	  log.error("PedigreeView(NSRect): " + frameRect);
	}

	public PocketPedigreeView(NSRect frame, Individual individual, int numGenerations) {
	  super(frame);
	  log.debug("PedigreeView(frame=" + frame + " indi=" + individual.getFullName() + " numGenerations=" + numGenerations);
	  // Initialization code here.
	  this.individual = individual;
	  this.generations = numGenerations;
	  //log.debug("Fonts: "+NSFontManager.sharedFontManager().availableFonts());
	  //log.debug("Font families: "+NSFontManager.sharedFontManager().availableFontFamilies());
	}

	public void setIndividual(Individual newIndividual) {
	  if (newIndividual == null) {
		newIndividual = Individual.UNKNOWN;
	  }
	  log.debug("PedigreeView.setIndividual(newIndividual) setting individual to " + newIndividual);
	  individual = newIndividual;
	}

	public void drawRect(NSRect rect) {
	  // Drawing code here.
	  log.debug("PedigreeView.drawRect(rect=" + rect + ")");
	  try {
		NSGraphics.eraseRect(rect);
  NSRect ppRect = new NSRect(0, 0, 445, 260);
  drawPedigreeInRect(ppRect);
  nameFontDict = narrow9Dict;
  detailFontDict = narrow8Dict;
  drawPedigreeInRect(ppRect.rectByOffsettingRect(0, 300));
 /*
		// generation 2
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0, rect.midY()), new NSPoint(genWidth, rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0, rect.midY()), new NSPoint(genWidth, rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0, rect.midY()), new NSPoint(genWidth, rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0, rect.midY()), new NSPoint(genWidth, rect.midY()));


//       List genRects = new ArrayList(rows);
//       for (int k=0; k < rows; k++) {
//          genRects.add(new NSPoint());
//       }
//       for (Iterator iterator = genRects.iterator(); iterator.hasNext();) {
//          Object o = (Object) iterator.next();
//
//       }
//       for (int gen=generations; gen > 0; gen--) {
//          int rows = (int) Math.round(Math.pow(2, gen-1));
//          for (int j=rows; j > 0; j--) {
//             log.debug("drawing generation "+gen+" #"+j+"\nleft from "+(rect.width()*(gen-1)/generations)+" to "+(rect.width()*gen/generations));
//             final NSPoint leftPoint = new NSPoint(rect.width()*(gen-1)/generations,rect.height()*j/rows);
//             log.debug("leftPoint="+leftPoint);
//             final NSPoint rightPoint = new NSPoint(rect.width()*gen/generations,rect.height()*j/rows);
//             log.debug("rightPoint="+rightPoint);
//             NSBezierPath.strokeLineFromPoint( leftPoint, rightPoint);
//          }
//       }
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
	   */
	  }
	  catch (Exception e) {
		log.error("Exception: ", e); //To change body of catch statement use Options | File Templates.
	  }
	}

	private void drawPedigreeInRect(NSRect ppRect) {
  NSGraphics.frameRectWithWidth(ppRect, 0.5f);
	    ppRect = ppRect.rectByInsettingRect(2, 1);

	  		// 1st & 2nd generation are indented 6 pixels from left
	  		float indent = 6;
	  		float genWidth = (ppRect.width() - indent*3) / (generations - 2);

	  		float rowHeight = 8;//narrow9.ascender()-narrow9.descender();
	  		NSSize indivSize = new NSSize(genWidth, rowHeight);
	  		NSSize indivDetailSize = new NSSize(genWidth, indivSize.height()*2+2);

// generation 6
	    float maxX = ppRect.maxX();
	    float lineX = maxX - genWidth;
	    float lineY = ppRect.y() - indivSize.height() + rowHeight + 2;
		float adj = 1;
		if (nameFontDict == times9Dict) {
		  adj = 2;
}
	    NSPoint p63 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p63, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getMother().getMother().getMother().getFullName(), nameFontDict), new NSPoint(p63.x(), p63.y()-adj));
	    lineY += rowHeight;

	    NSPoint p62 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p62, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getMother().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p62.x(), p62.y()-adj));
	    lineY += rowHeight;

	    NSPoint p61 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p61, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getMother().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p61.x(), p61.y()-adj));
	    lineY += rowHeight;

	    NSPoint p60 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p60, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getMother().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p60.x(), p60.y()-adj));
	    lineY += rowHeight;

	    NSPoint p59 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p59, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getFather().getMother().getMother().getFullName(), nameFontDict),new NSPoint(p59.x(), p59.y()-adj));
	    lineY += rowHeight;

	    NSPoint p58 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p58, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getFather().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p58.x(), p58.y()-adj));
	    lineY += rowHeight;

	    NSPoint p57 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p57, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getFather().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p57.x(), p57.y()-adj));
	    lineY += rowHeight;

	    NSPoint p56 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p56, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getFather().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p56.x(), p56.y()-adj));
	    lineY += rowHeight;

	    NSPoint p55 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p55, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getFather().getMother().getMother().getMother().getFullName(), nameFontDict),new NSPoint(p55.x(), p55.y()-adj));
	    lineY += rowHeight;

	    NSPoint p54 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p54, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getFather().getMother().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p54.x(), p54.y()-adj));
	    lineY += rowHeight;

	    NSPoint p53 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p53, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getFather().getMother().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p53.x(), p53.y()-adj));
	    lineY += rowHeight;

	    NSPoint p52 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p52, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getFather().getMother().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p52.x(), p52.y()-adj));
	    lineY += rowHeight;

	    NSPoint p51 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p51, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getFather().getFather().getMother().getMother().getFullName(), nameFontDict),new NSPoint(p51.x(), p51.y()-adj));
	    lineY += rowHeight;

	    NSPoint p50 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p50, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getFather().getFather().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p50.x(), p50.y()-adj));
	    lineY += rowHeight;

	    NSPoint p49 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p49, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getFather().getFather().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p49.x(), p49.y()-adj));
	    lineY += rowHeight;

	    NSPoint p48 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p48, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getFather().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p48.x(), p48.y()-adj));
	    lineY += rowHeight;

	    NSPoint p47 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p47, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getMother().getMother().getMother().getMother().getFullName(), nameFontDict),new NSPoint(p47.x(), p47.y()-adj));
	    lineY += rowHeight;

	    NSPoint p46 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p46, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getMother().getMother().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p46.x(), p46.y()-adj));
	    lineY += rowHeight;

	    NSPoint p45 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p45, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getMother().getMother().getMother().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p45.x(), p45.y()-adj));
	    lineY += rowHeight;

	    NSPoint p44 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p44, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getMother().getMother().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p44.x(), p44.y()-adj));
	    lineY += rowHeight;

	    NSPoint p43 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p43, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getMother().getFather().getMother().getMother().getFullName(), nameFontDict),new NSPoint(p43.x(), p43.y()-adj));
	    lineY += rowHeight;

	    NSPoint p42 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p42, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getMother().getFather().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p42.x(), p42.y()-adj));
	    lineY += rowHeight;

	    NSPoint p41 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p41, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getMother().getFather().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p41.x(), p41.y()-adj));
	    lineY += rowHeight;

	    NSPoint p40 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p40, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getMother().getFather().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p40.x(), p40.y()-adj));
	    lineY += rowHeight;

	    NSPoint p39 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p39, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getMother().getMother().getMother().getFullName(), nameFontDict),new NSPoint(p39.x(), p39.y()-adj));
	    lineY += rowHeight;

	    NSPoint p38 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p38, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getMother().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p38.x(), p38.y()-adj));
	    lineY += rowHeight;

	    NSPoint p37 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p37, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getMother().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p37.x(), p37.y()-adj));
	    lineY += rowHeight;

	    NSPoint p36 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p36, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getMother().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p36.x(), p36.y()-adj));
	    lineY += rowHeight;

	    NSPoint p35 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p35, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getFather().getMother().getMother().getFullName(), nameFontDict),new NSPoint(p35.x(), p35.y()-adj));
	    lineY += rowHeight;

	    NSPoint p34 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p34, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getFather().getMother().getFather().getFullName(), nameFontDict),new NSPoint(p34.x(), p34.y()-adj));
	    lineY += rowHeight;

	    NSPoint p33 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p33, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getFather().getFather().getMother().getFullName(), nameFontDict),new NSPoint(p33.x(), p33.y()-adj));
	    lineY += rowHeight;

	    NSPoint p32 = new NSPoint(lineX, lineY);
	    NSBezierPath.strokeLineFromPoint(p32, new NSPoint(maxX, lineY));
	    NSGraphics.drawAttributedString(new NSAttributedString(" " + individual.getFather().getFather().getFather().getFather().getFather().getFullName(), nameFontDict),new NSPoint(p32.x(), p32.y()-adj));
	    lineY += rowHeight;

	    NSBezierPath.strokeLineFromPoint(p62, p63);
	    NSBezierPath.strokeLineFromPoint(p60, p61);
	    NSBezierPath.strokeLineFromPoint(p58, p59);
	    NSBezierPath.strokeLineFromPoint(p56, p57);
	    NSBezierPath.strokeLineFromPoint(p54, p55);
	    NSBezierPath.strokeLineFromPoint(p52, p53);
	    NSBezierPath.strokeLineFromPoint(p50, p51);
	    NSBezierPath.strokeLineFromPoint(p48, p49);
	    NSBezierPath.strokeLineFromPoint(p46, p47);
	    NSBezierPath.strokeLineFromPoint(p44, p45);
	    NSBezierPath.strokeLineFromPoint(p42, p43);
	    NSBezierPath.strokeLineFromPoint(p40, p41);
	    NSBezierPath.strokeLineFromPoint(p38, p39);
	    NSBezierPath.strokeLineFromPoint(p36, p37);
	    NSBezierPath.strokeLineFromPoint(p34, p35);
	    NSBezierPath.strokeLineFromPoint(p32, p33);

//if (nameFontDict == times8Dict) {
//		  nameFontDict = times9Dict;
//		}
//if (nameFontDict == times8Dict) {
//		  nameFontDict = times9Dict;
//		}

	    // generation 5
	    maxX = lineX;
	    lineX = maxX - genWidth;
	    lineY = ppRect.y();// - indivSize.height();
	  		rowHeight = rowHeight * 2;
	    NSRect indivRect = new NSRect(new NSPoint(lineX, 0), indivDetailSize);
//		if (detailFontDict == times8Dict) {
//		  indivRect = indivRect.rectByOffsettingRect(0, -1);
//}
	    NSPoint p31 = drawIndividualInRect(individual.getMother().getMother().getMother().getMother(),
	  									 indivRect.rectByOffsettingRect(0, (p63.y() + p62.y()) / 2));
	    lineY += rowHeight;
	    NSPoint p30 = drawIndividualInRect(individual.getMother().getMother().getMother().getFather(),
	  									 indivRect.rectByOffsettingRect(0, (p61.y() + p60.y()) / 2));
	    lineY += rowHeight;
	    NSPoint p29 = drawIndividualInRect(individual.getMother().getMother().getFather().getMother(),
	  									 indivRect.rectByOffsettingRect(0, (p59.y() + p58.y()) / 2));
	    lineY += rowHeight;
	    NSPoint p28 = drawIndividualInRect(individual.getMother().getMother().getFather().getFather(),
	  									 indivRect.rectByOffsettingRect(0, (p57.y() + p56.y()) / 2));
	    lineY += rowHeight;
	    NSPoint p27 = drawIndividualInRect(individual.getMother().getFather().getMother().getMother(),
	  									 indivRect.rectByOffsettingRect(0, (p55.y() + p54.y()) / 2));
	    lineY += rowHeight;
	    NSPoint p26 = drawIndividualInRect(individual.getMother().getFather().getMother().getFather(),
	  									indivRect.rectByOffsettingRect(0, (p53.y() + p52.y()) / 2));
	    lineY += rowHeight;
	    NSPoint p25 = drawIndividualInRect(individual.getMother().getFather().getFather().getMother(),
	  									indivRect.rectByOffsettingRect(0, (p51.y() + p50.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p24 = drawIndividualInRect(individual.getMother().getFather().getFather().getFather(),
	  									indivRect.rectByOffsettingRect(0, (p49.y() + p48.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p23 = drawIndividualInRect(individual.getFather().getMother().getMother().getMother(),
	  									indivRect.rectByOffsettingRect(0, (p47.y() + p46.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p22 = drawIndividualInRect(individual.getFather().getMother().getMother().getFather(),
	  									indivRect.rectByOffsettingRect(0, (p45.y() + p44.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p21 = drawIndividualInRect(individual.getFather().getMother().getFather().getMother(),
	  									indivRect.rectByOffsettingRect(0, (p43.y() + p42.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p20 = drawIndividualInRect(individual.getFather().getMother().getFather().getFather(),
	  									indivRect.rectByOffsettingRect(0, (p41.y() + p40.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p19 = drawIndividualInRect(individual.getFather().getFather().getMother().getMother(),
	  									indivRect.rectByOffsettingRect(0, (p39.y() + p38.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p18 = drawIndividualInRect(individual.getFather().getFather().getMother().getFather(),
	  								   indivRect.rectByOffsettingRect(0, (p37.y() + p36.y()) / 2));
	   lineY += rowHeight;
	   NSPoint p17 = drawIndividualInRect(individual.getFather().getFather().getFather().getMother(),
	  								   indivRect.rectByOffsettingRect(0, (p35.y() + p34.y()) / 2));
	  lineY += rowHeight;
	  NSPoint p16 = drawIndividualInRect(individual.getFather().getFather().getFather().getFather(),
	  								   indivRect.rectByOffsettingRect(0, (p33.y() + p32.y()) / 2));

	    NSBezierPath.strokeLineFromPoint(p31, p30);
	    NSBezierPath.strokeLineFromPoint(p29, p28);
	    NSBezierPath.strokeLineFromPoint(p27, p26);
	    NSBezierPath.strokeLineFromPoint(p25, p24);
	    NSBezierPath.strokeLineFromPoint(p23, p22);
	    NSBezierPath.strokeLineFromPoint(p21, p20);
	    NSBezierPath.strokeLineFromPoint(p19, p18);
	    NSBezierPath.strokeLineFromPoint(p17, p16);

if (detailFontDict == narrow8Dict) {
		  detailFontDict = narrow9Dict;
		}
//		if (detailFontDict == times8Dict) {
//				  detailFontDict = times9Dict;
//				}

	  		// generation 4
	  		maxX = lineX;
	  		lineX = lineX - genWidth;
	  		lineY = ppRect.y() - indivSize.height();
	  		rowHeight = rowHeight * 2;
	  		indivRect = new NSRect(new NSPoint(lineX, 0), indivDetailSize);
	  		NSMutablePoint indivPoint = new NSMutablePoint(lineX, 0);
	  		NSPoint p15 = drawIndividualInRect(individual.getMother().getMother().getMother(),
	  										   indivRect.rectByOffsettingRect(0, (p31.y() + p30.y()) / 2));
	  		lineY += rowHeight;
	  		////indivPoint.setY(indivPoint.y() + indivDetailSize.height());
	  		NSPoint p14 = drawIndividualInRect(individual.getMother().getMother().getFather(),
	  										   indivRect.rectByOffsettingRect(0, (p29.y() + p28.y()) / 2));
	  		lineY += rowHeight;
	  		//indivPoint.setY(indivPoint.y() + indivDetailSizeWithMarr.height());
	  		NSPoint p13 = drawIndividualInRect(individual.getMother().getFather().getMother(),
	  										   indivRect.rectByOffsettingRect(0, (p27.y() + p26.y()) / 2));
	  		lineY += rowHeight;
	  		////indivPoint.setY(indivPoint.y() + indivDetailSize.height());
	  		NSPoint p12 = drawIndividualInRect(individual.getMother().getFather().getFather(),
	  										   indivRect.rectByOffsettingRect(0, (p25.y() + p24.y()) / 2));
	  		lineY += rowHeight;
	  		//indivPoint.setY(indivPoint.y() + indivDetailSizeWithMarr.height());
	  		NSPoint p11 = drawIndividualInRect(individual.getFather().getMother().getMother(),
	  										   indivRect.rectByOffsettingRect(0, (p23.y() + p22.y()) / 2));
	  		lineY += rowHeight;
	  		////indivPoint.setY(indivPoint.y() + indivDetailSize.height());
	  		NSPoint p10 = drawIndividualInRect(individual.getFather().getMother().getFather(),
	  										   indivRect.rectByOffsettingRect(0, (p21.y() + p20.y()) / 2));
	  		lineY += rowHeight;
	  		//indivPoint.setY(indivPoint.y() + indivDetailSizeWithMarr.height());
	  		NSPoint p9 = drawIndividualInRect(individual.getFather().getFather().getMother(),
	  										  indivRect.rectByOffsettingRect(0, (p19.y() + p18.y()) / 2));
	  		lineY += rowHeight;
	  		////indivPoint.setY(indivPoint.y() + indivDetailSize.height());
	  		NSPoint p8 = drawIndividualInRect(individual.getFather().getFather().getFather(),
	  										  indivRect.rectByOffsettingRect(0, (p17.y() + p16.y()) / 2));
	  		log.debug("p8=" + p8 + " p9=" + p9 + " p10=" + p10 + " p11=" + p11 + " p12=" + p12 + " p13=" + p13 + " p14=" +
	  				  p14 + " p15=" + p15);
	  		NSBezierPath.strokeLineFromPoint(p15, p14);
	  		NSBezierPath.strokeLineFromPoint(p12, p13);
	  		NSBezierPath.strokeLineFromPoint(p10, p11);
	  		NSBezierPath.strokeLineFromPoint(p8, p9);

	  		// generation 3
	  		maxX = lineX;
	  		lineX = lineX - genWidth;
	  		lineY = ppRect.y() - indivSize.height();
	  		rowHeight = rowHeight * 2;
//             rows = (int) Math.round(Math.pow(2, generations-2));
//             rowHeight = (rect.height()) / rows;
	  		indivRect = new NSRect(new NSPoint(lineX, 0), indivDetailSize);
//             ppRect.y();
	  		NSPoint p7 = drawIndividualInRect(individual.getMother().getMother(),
	  										  indivRect.rectByOffsettingRect(0, (p15.y() + p14.y()) / 2));
	  		lineY += rowHeight;
	  		NSPoint p6 = drawIndividualInRect(individual.getMother().getFather(),
	  										  indivRect.rectByOffsettingRect(0, (p12.y() + p13.y()) / 2));
	  		lineY += rowHeight;
	  		NSPoint p5 = drawIndividualInRect(individual.getFather().getMother(),
	  										  indivRect.rectByOffsettingRect(0, (p10.y() + p11.y()) / 2));
	  		lineY += rowHeight;
	  		NSPoint p4 = drawIndividualInRect(individual.getFather().getFather(),
	  										  indivRect.rectByOffsettingRect(0, (p8.y() + p9.y()) / 2));
	  		NSPoint gapPoint = new NSPoint(p6.x(), (p6.y() + p7.y()) / 2);
	  		NSBezierPath.strokeLineFromPoint(p6, new NSPoint(gapPoint.x(), gapPoint.y() + 15));
	  		NSBezierPath.strokeLineFromPoint(new NSPoint(gapPoint.x(), gapPoint.y() - 15), p7);
	  		gapPoint = new NSPoint(p4.x(), (p4.y() + p5.y()) / 2);
	  		NSBezierPath.strokeLineFromPoint(p4, new NSPoint(gapPoint.x(), gapPoint.y() + 15));
	  		NSBezierPath.strokeLineFromPoint(new NSPoint(gapPoint.x(), gapPoint.y() - 15), p5);

	  		// generation 2
	  		lineX = indent;
	  		maxX = lineX + genWidth;
	  		lineY = ppRect.y() - indivSize.height();
	  		rowHeight = rowHeight * 2;
//             rows = (int) Math.round(Math.pow(2, generations-3));
//             rowHeight = (rect.height()) / rows;
	  		indivRect = new NSRect(new NSPoint(lineX, 0), indivDetailSize);
//             lineY = ppRect.y() - indivSize.height();
	  		NSPoint p3 = drawIndividualInRect(individual.getMother(),
	  										  indivRect.rectByOffsettingRect(0, (p6.y() + p7.y()) / 2));
	  		lineY += rowHeight;
	  		NSPoint p2 = drawIndividualInRect(individual.getFather(),
	  										  indivRect.rectByOffsettingRect(0, (p4.y() + p5.y()) / 2));
	  		lineY += rowHeight;
	  		NSBezierPath.strokeLineFromPoint(p2, p3);

	  		// generation 1
	  		lineX = indent;
	  		maxX = lineX + genWidth;
	  		lineY = ppRect.y() - indivSize.height();
	  		rowHeight = rowHeight * 2;
	  		indivRect = new NSRect(new NSPoint(lineX, 0), indivDetailSize);
	  		drawIndividualInRect(individual, indivRect.rectByOffsettingRect(0, (p2.y() + p3.y()) / 2),
	  							 2);
	}

	private NSPoint drawIndividualInRect(Individual individual, NSRect nsRect) {
	  return drawIndividualInRect(individual, nsRect, 1);
	}

	private NSPoint drawIndividualInRect(Individual individual, NSRect rect, float detailOffset) {
	  log.debug("drawIndividualInRect(" + individual + ", " + rect + ")");
	  rect = rect.rectByInsettingRect(1, 1);
//      colors[currColor++].set();
//      NSGraphics.frameRectWithWidth(rect, 3);
	  float y = rect.y()-1;
	  NSMutableAttributedString detailStr = new NSMutableAttributedString(makeIndividualDetailStringForRect(individual, rect));
	  int adj = 2;
	  if (detailFontDict == times8Dict) {
		adj = 1;
}
	  NSRect detailRect = rect.rectByOffsettingRect(detailOffset+1, -NSGraphics.sizeOfAttributedString(detailStr).height() + adj);
	  System.out.println("font attr:"+detailStr.fontAttributesInRange(new NSRange(0, detailStr.length())));
	  NSFont font = (NSFont) detailStr.attributeAtIndex(NSAttributedString.FontAttributeName, 0, null);
//	  NSGraphics.frameRectWithWidth(detailRect, 0.1f);
	  NSGraphics.drawAttributedString(detailStr, detailRect.origin());
	  float lineY = y + NSGraphics.sizeOfAttributedString(detailStr).height();
	  NSBezierPath.strokeLineFromPoint(new NSPoint(rect.x(), y), new NSPoint(rect.maxX()+1, y));
	  NSGraphics.drawAttributedString(new NSAttributedString(individual.getFullName(), nameFontDict),
									  new NSPoint(rect.x()+2, y-1));
	  log.debug("total height = " + (y ));//+ NSGraphics.sizeOfAttributedString(detailStr).height()));
	  return new NSPoint(rect.x(), y);
	}

//  private NSAttributedString makeIndividualDetailString(Individual individual) {
//	  Event birthEvent = individual.getBirthEvent();
//	  if (birthEvent == null) {birthEvent = new MyEvent();
//	  }
//	  String detailStr = birthEvent.getDateString() + " ";
//	  detailStr += birthEvent.getPlace().getFormatString();// + "\n";
//	  if (individual.getGender().equals(Gender.MALE) /* || currColor++%2==0*/) {
//		Event marriageEvent = individual.getFamilyAsSpouse().getMarriageEvent();
//		if (marriageEvent == null) {marriageEvent = new MyEvent();
//		}
//		detailStr += "Married: " + marriageEvent.getDateString() + "\n";
//		detailStr += "Place: " + marriageEvent.getPlace().getFormatString() + "\n";
//	  }
//	  Event deathEvent = individual.getDeathEvent();
//	  if (deathEvent == null) {deathEvent = new MyEvent();
//	  }
//	  detailStr += "Died: " + deathEvent.getDateString() + "\n";
//	  detailStr += "Place: " + deathEvent.getPlace().getFormatString();
//	  NSArray narrowFonts = new NSArray(new String[] {
//  "ArialNarrow", "AuroraCondensedBT", "DomCasualD-Regu", "Futura-CondensedMedium", "QuickTypeCondensed", "Sedona", "LiGothicMed"
//"DomCasualD-Regu", "Futura-CondensedMedium", "Sedona"
//	  });
//  if (++counter/16 >= narrowFonts.count()) { counter = 0;}
//
//	  return new NSAttributedString(detailStr, new NSDictionary(NSFont.fontWithNameAndSize((String)narrowFonts.objectAtIndex(counter/16), 9.0f), NSAttributedString.FontAttributeName));
//	}

  private NSAttributedString makeIndividualDetailStringForRect(Individual individual, NSRect rect) {
	Event birthEvent = individual.getBirthEvent();
	System.out.println("birthevent="+birthEvent);
if (birthEvent == null) {birthEvent = new MyEvent();
}
	System.out.println("birthplace for indi ("+individual+"): "+birthEvent.getPlace().getFormatString());
//	NSArray narrowFonts = new NSArray(new String[] {
//  "ArialNarrow", "AuroraCondensedBT", "DomCasualD-Regu", "Futura-CondensedMedium", "QuickTypeCondensed", "Sedona", "LiGothicMed"
//	"DomCasualD-Regu", "Futura-CondensedMedium", "Sedona"
//	});
//	if (++counter/16 >= narrowFonts.count()) { counter = 0;}
String detailStr = birthEvent.getDateString() + " ";
detailStr += birthEvent.getPlace().getFormatString();// + "\n";
	// check if string is too long for space
	NSAttributedString resultStr = new NSAttributedString(detailStr, detailFontDict);
	NSSize strSize = NSGraphics.sizeOfAttributedString(resultStr);
	System.out.println("detailstr size:"+strSize);
	int abbrevLevel = 0;
	while ((strSize.width() > rect.width() && ++abbrevLevel <= Place.MAX_SEVERITY)) {
	  System.err.println("string too long, condensing");//+detailStr.stringReference());
	  detailStr = birthEvent.getDateString() +" "+ birthEvent.getPlace().getAbbreviatedFormatString(abbrevLevel);
	  resultStr = new NSAttributedString(detailStr, detailFontDict);
	  strSize = NSGraphics.sizeOfAttributedString(resultStr);
	System.out.println("detailstr size:"+strSize+resultStr);
	}
//	  if (individual.getGender().equals(Gender.MALE) /* || currColor++%2==0*/) {
//		Event marriageEvent = individual.getFamilyAsSpouse().getMarriageEvent();
//		if (marriageEvent == null) {marriageEvent = new MyEvent();
//		}
//		detailStr += "Married: " + marriageEvent.getDateString() + "\n";
//		detailStr += "Place: " + marriageEvent.getPlace().getFormatString() + "\n";
//	  }
//	  Event deathEvent = individual.getDeathEvent();
//	  if (deathEvent == null) {deathEvent = new MyEvent();
//	  }
//	  detailStr += "Died: " + deathEvent.getDateString() + "\n";
//	  detailStr += "Place: " + deathEvent.getPlace().getFormatString();

return resultStr;

}


// code based from an email list claiming to be derived from the Omni frameworks
// that adjusts the size of the string to match the size of the text field

 NSAttributedString sizeStringForRect(NSAttributedString theString, NSRect rect)
 {
	 NSRect frame;
	 NSRect newBounds;
	 NSPoint newPoint;
	 NSSize stringSize;
	 double frameProportion, stringProportion, boundsMultiplier;

	 stringSize = NSGraphics.sizeOfAttributedString(theString);
	 frame = frame();

	 frameProportion = frame.size().width() / frame.size().height();
	 stringProportion = stringSize.width() / stringSize.height();
	 boundsMultiplier = stringProportion / frameProportion;

	 if (boundsMultiplier < 1.0) {
		 newBounds = new NSRect(0f,
								0f,
								(float)(stringSize.width() / boundsMultiplier),
								stringSize.height());
		 newPoint = new NSPoint((newBounds.size().width() - stringSize.width()) / 2, 0);
	 }
	 else {
		 newBounds = new NSRect(0f,
								0f,
								stringSize.width(),
								(float)(stringSize.height() * boundsMultiplier));
		 newPoint = new NSPoint(0, (newBounds.size().height() - stringSize.height()) / 2);
	 }

	 setBounds(newBounds);
	 return theString;// drawAtPoint:newPoint];
 }
}
