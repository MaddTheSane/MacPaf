//
//  PedigreeView.java
//  MacPAFTest
//
//  Created by Logan Allred on Sun May 18 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

import com.apple.cocoa.application.NSBezierPath;
import com.apple.cocoa.application.NSColor;
import com.apple.cocoa.application.NSFont;
import com.apple.cocoa.application.NSGraphics;
import com.apple.cocoa.application.NSMutableParagraphStyle;
import com.apple.cocoa.application.NSParagraphStyle;
import com.apple.cocoa.application.NSText;
import com.apple.cocoa.application.NSView;
import com.apple.cocoa.foundation.NSAttributedString;
import com.apple.cocoa.foundation.NSDictionary;
import com.apple.cocoa.foundation.NSGregorianDate;
import com.apple.cocoa.foundation.NSGregorianDateFormatter;
import com.apple.cocoa.foundation.NSMutableAttributedString;
import com.apple.cocoa.foundation.NSMutableDictionary;
import com.apple.cocoa.foundation.NSMutablePoint;
import com.apple.cocoa.foundation.NSPoint;
import com.apple.cocoa.foundation.NSRect;
import com.apple.cocoa.foundation.NSSize;
import com.redbugz.macpaf.*;


public class PedigreeView extends NSView {
//   private NSMutableAttributedString nameText;
   private Individual individual = Individual.UNKNOWN;
   private int generations = 4;
   private static final NSFont times9 = NSFont.fontWithNameAndSize("Times-Roman",9.0f);
   private static final NSFont times12 = NSFont.fontWithNameAndSize("Times-Roman",12.0f);
   private static final NSDictionary times9Dict = new NSDictionary(times9, NSAttributedString.FontAttributeName);
   private static final NSDictionary times12Dict = new NSDictionary(times12, NSAttributedString.FontAttributeName);
   private static final NSColor[]colors = {NSColor.blueColor(), NSColor.redColor(), NSColor.yellowColor(),
         NSColor.brownColor(), NSColor.greenColor(), NSColor.cyanColor(), NSColor.magentaColor(), NSColor.grayColor(),
      NSColor.orangeColor(), NSColor.purpleColor(), NSColor.lightGrayColor(), NSColor.blueColor(), NSColor.greenColor(),
      NSColor.redColor(), NSColor.blackColor()};
   private int currColor = 0;

public PedigreeView() {
super();
System.err.println("PedigreeView()");
}

public PedigreeView(NSRect frameRect) {
super(frameRect);
System.err.println("PedigreeView(NSRect): "+frameRect);
 }

   public PedigreeView(NSRect frame, Individual individual, int numGenerations) {
        super(frame);
      System.out.println("PedigreeView(frame="+frame+" indi="+individual.getFullName()+" numGenerations="+numGenerations);
        // Initialization code here.
      this.individual = individual;
      this.generations = numGenerations;
      //System.out.println("Fonts: "+NSFontManager.sharedFontManager().availableFonts());
      //System.out.println("Font families: "+NSFontManager.sharedFontManager().availableFontFamilies());
   }

   public void setIndividual(Individual newIndividual) {
     if (newIndividual == null) {
       newIndividual = Individual.UNKNOWN;
     }
     System.out.println("PedigreeView.setIndividual(newIndividual) setting individual to "+newIndividual);
     individual = newIndividual;
   }

    public void drawRect(NSRect rect) {
        // Drawing code here.
       System.out.println("PedigreeView.drawRect(rect="+rect+")");
       try {
         NSGraphics.eraseRect(rect);
//          NSDictionary system9 = new NSDictionary(NSFont.systemFontOfSize(9.0f), NSAttributedString.FontAttributeName);
//          NSDictionary userfixed9 = new NSDictionary(NSFont.userFixedPitchFontOfSize(9.0f), NSAttributedString.FontAttributeName);
//          NSDictionary user9 = new NSDictionary(NSFont.userFontOfSize(9.0f), NSAttributedString.FontAttributeName);
//          NSDictionary message9 = new NSDictionary(NSFont.messageFontOfSize(9.0f), NSAttributedString.FontAttributeName);
          NSDictionary boldTimes9 = new NSDictionary(NSFont.fontWithNameAndSize("Times-Bold",9.0f),  NSAttributedString.FontAttributeName);
          NSMutableAttributedString headerString = new NSMutableAttributedString("PEDIGREE CHART\n\n", times12Dict);
          headerString.appendAttributedString(new NSAttributedString("Person Number 1 on this chart is the same as no. ___ on chart no. ___\n\n", times9Dict));
          headerString.appendAttributedString(new NSAttributedString("Marked Boxes are Completed\nor Unnecessary Ordinances:\n", times9Dict));
          headerString.appendAttributedString(new NSAttributedString("B", boldTimes9));
          headerString.appendAttributedString(new NSAttributedString("=Baptized\n", times9Dict));
          headerString.appendAttributedString(new NSAttributedString("E", boldTimes9));
          headerString.appendAttributedString(new NSAttributedString("=Endowed\n", times9Dict));
          headerString.appendAttributedString(new NSAttributedString("P", boldTimes9));
          headerString.appendAttributedString(new NSAttributedString("=Sealed to Parents\n", times9Dict));
          headerString.appendAttributedString(new NSAttributedString("S", boldTimes9));
          headerString.appendAttributedString(new NSAttributedString("=Sealed to Spouse\n", times9Dict));
          headerString.appendAttributedString(new NSAttributedString("C", boldTimes9));
          headerString.appendAttributedString(new NSAttributedString("=All Children's Ordinances\n", times9Dict));
       NSGraphics.drawAttributedString(headerString, rect);

          NSMutableParagraphStyle centeredStyle = (NSMutableParagraphStyle) NSParagraphStyle.defaultParagraphStyle().mutableClone();
          centeredStyle.setAlignment(NSText.CenterTextAlignment);
          NSMutableDictionary centeredTimes9 = new NSMutableDictionary(times9Dict);
          centeredTimes9.takeValueForKey(centeredStyle, NSAttributedString.ParagraphStyleAttributeName);
          NSGraphics.drawAttributedString(new NSAttributedString(new NSGregorianDateFormatter("%a, %b %e, %Y", false).stringForObjectValue(new NSGregorianDate()), (NSDictionary)centeredTimes9), rect);

          NSMutableParagraphStyle rightAlignStyle = (NSMutableParagraphStyle) NSParagraphStyle.defaultParagraphStyle().mutableClone();
          rightAlignStyle.setAlignment(NSText.RightTextAlignment);
          NSMutableDictionary rightTimes9Dict = new NSMutableDictionary(times9Dict);
          rightTimes9Dict.takeValueForKey(rightAlignStyle, NSAttributedString.ParagraphStyleAttributeName);
          NSGraphics.drawAttributedString(new NSAttributedString("Chart No. ___", (NSDictionary)rightTimes9Dict), rect);


       // 2nd generation is indented 20 pixels from left
       float genWidth = (rect.width()-20) / (generations-1);

       // generation 4
          int rows = (int) Math.round(Math.pow(2, generations-1));
          float rowHeight = (rect.height()) / rows;
          NSRect indivRect = new NSRect(rect.maxX()-genWidth, 0, genWidth, rowHeight);
       float lineY = 0;
          NSSize indivDetailSize = new NSSize(genWidth, 64);
          NSSize indivDetailSizeWithMarr = new NSSize(genWidth, 98);
          NSMutablePoint indivPoint = new NSMutablePoint(rect.maxX()-genWidth, 0);
          NSPoint p15 = drawIndividualInRect(individual.getMother().getMother().getMother(), new NSRect(indivPoint, indivDetailSize));
          lineY += indivDetailSize.height();
          indivPoint.setY(indivPoint.y()+indivDetailSize.height());
          NSPoint p14 = drawIndividualInRect(individual.getMother().getMother().getFather(), new NSRect(indivPoint, indivDetailSizeWithMarr));
          lineY += indivDetailSize.height();
          indivPoint.setY(indivPoint.y()+indivDetailSizeWithMarr.height());
          NSPoint p13 = drawIndividualInRect(individual.getMother().getFather().getMother(), new NSRect(indivPoint, indivDetailSize));
          lineY += indivDetailSize.height();
          indivPoint.setY(indivPoint.y()+indivDetailSize.height());
          NSPoint p12 = drawIndividualInRect(individual.getMother().getFather().getFather(), new NSRect(indivPoint, indivDetailSizeWithMarr));
          lineY += indivDetailSize.height();
          indivPoint.setY(indivPoint.y()+indivDetailSizeWithMarr.height());
          NSPoint p11 = drawIndividualInRect(individual.getFather().getMother().getMother(), new NSRect(indivPoint, indivDetailSize));
          lineY += indivDetailSize.height();
          indivPoint.setY(indivPoint.y()+indivDetailSize.height());
          NSPoint p10 = drawIndividualInRect(individual.getFather().getMother().getFather(), new NSRect(indivPoint, indivDetailSizeWithMarr));
          lineY += indivDetailSize.height();
          indivPoint.setY(indivPoint.y()+indivDetailSizeWithMarr.height());
          NSPoint p9 = drawIndividualInRect(individual.getFather().getFather().getMother(), new NSRect(indivPoint, indivDetailSize));
          lineY += indivDetailSize.height();
          indivPoint.setY(indivPoint.y()+indivDetailSize.height());
          NSPoint p8 = drawIndividualInRect(individual.getFather().getFather().getFather(), new NSRect(indivPoint, indivDetailSizeWithMarr));
          System.out.println("p8="+p8+" p9="+p9+" p10="+p10+" p11="+p11+" p12="+p12+" p13="+p13+" p14="+p14+" p15="+p15);
          NSBezierPath.strokeLineFromPoint(p15, p14);
          NSBezierPath.strokeLineFromPoint(p12, p13);
          NSBezierPath.strokeLineFromPoint(p10, p11);
          NSBezierPath.strokeLineFromPoint(p8, p9);

          // generation 3
//             rows = (int) Math.round(Math.pow(2, generations-2));
//             rowHeight = (rect.height()) / rows;
             indivRect = new NSRect(rect.maxX()-genWidth*2, 0, genWidth, rowHeight);
//             lineY = 0;
          NSPoint p7 = drawIndividualInRect(individual.getMother().getMother(), indivRect.rectByOffsettingRect(0,(p15.y()+p14.y())/2-indivRect.height()));
          lineY += rowHeight;
          NSPoint p6 = drawIndividualInRect(individual.getMother().getFather(), indivRect.rectByOffsettingRect(0,(p12.y()+p13.y())/2-indivRect.height()));
          lineY += rowHeight;
          NSPoint p5 = drawIndividualInRect(individual.getFather().getMother(), indivRect.rectByOffsettingRect(0,(p10.y()+p11.y())/2-indivRect.height()));
          lineY += rowHeight;
          NSPoint p4 = drawIndividualInRect(individual.getFather().getFather(), indivRect.rectByOffsettingRect(0,(p8.y()+p9.y())/2-indivRect.height()));
          NSBezierPath.strokeLineFromPoint(p6, p7);
          NSBezierPath.strokeLineFromPoint(p4, p5);

          // generation 2
//             rows = (int) Math.round(Math.pow(2, generations-3));
//             rowHeight = (rect.height()) / rows;
             indivRect = new NSRect(rect.maxX()-genWidth*3, 0, genWidth, rowHeight);
//             lineY = 0;
          NSPoint p3 = drawIndividualInRect(individual.getMother(), indivRect.rectByOffsettingRect(0,(p6.y()+p7.y())/2-indivRect.height()));
          lineY += rowHeight;
          NSPoint p2 = drawIndividualInRect(individual.getFather(), indivRect.rectByOffsettingRect(0,(p4.y()+p5.y())/2-indivRect.height()));
          lineY += rowHeight;
          NSPoint gapPoint = new NSPoint(p2.x(), (p2.y()+p3.y())/2);
          NSBezierPath.strokeLineFromPoint(p2, new NSPoint(gapPoint.x(), gapPoint.y()+15));
          NSBezierPath.strokeLineFromPoint(new NSPoint(gapPoint.x(), gapPoint.y()-7), p3);

          // generation 1
          indivRect = new NSRect(0, 0, genWidth, rowHeight);
          drawIndividualInRect(individual, indivRect.rectByOffsettingRect(0,(p2.y()+p3.y())/2-indivRect.height()), 20);

//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY), new NSPoint(rightX, lineY));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY-rowHeight), new NSPoint(leftX, lineY));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX-genWidth, lineY-(rowHeight/2)), new NSPoint(leftX, lineY-(rowHeight/2)));
//       NSGraphics.drawAttributedString(new NSAttributedString(" Florence Rheuamah Porter", new NSDictionary(times9, NSAttributedString.FontAttributeName)), new NSPoint(leftX, lineY));
//          NSGraphics.drawAttributedString(nameText, new NSPoint(leftX, lineY-NSGraphics.sizeOfAttributedString(nameText).height()));
//       lineY += rowHeight;
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY), new NSPoint(rightX, lineY));
//       NSGraphics.drawAttributedString(new NSAttributedString(" Henry Ivan Hall", new NSDictionary(times9, NSAttributedString.FontAttributeName)), new NSPoint(leftX, lineY));
//          NSGraphics.drawAttributedString(nameText, new NSPoint(leftX, lineY-NSGraphics.sizeOfAttributedString(nameText).height()));
//       lineY += rowHeight;
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY), new NSPoint(rightX, lineY));
//       NSGraphics.drawAttributedString(new NSAttributedString(" Alma Gertrude Funke", new NSDictionary(times9, NSAttributedString.FontAttributeName)), new NSPoint(leftX, lineY));
//          NSGraphics.drawAttributedString(nameText, new NSPoint(leftX, lineY-NSGraphics.sizeOfAttributedString(nameText).height()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY-rowHeight), new NSPoint(leftX, lineY));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX-genWidth, lineY-(rowHeight/2)), new NSPoint(leftX, lineY-(rowHeight/2)));
//       lineY += rowHeight;
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY), new NSPoint(rightX, lineY));
//       NSGraphics.drawAttributedString(new NSAttributedString(" Joseph Smith Thompson", new NSDictionary(times9, NSAttributedString.FontAttributeName)), new NSPoint(leftX, lineY));
//          NSGraphics.drawAttributedString(nameText, new NSPoint(leftX, lineY-NSGraphics.sizeOfAttributedString(nameText).height()));
//       lineY += rowHeight;
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY), new NSPoint(rightX, lineY));
//       NSGraphics.drawAttributedString(new NSAttributedString(" Grace Bleak", new NSDictionary(times9, NSAttributedString.FontAttributeName)), new NSPoint(leftX, lineY));
//          NSGraphics.drawAttributedString(nameText, new NSPoint(leftX, lineY-NSGraphics.sizeOfAttributedString(nameText).height()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY-rowHeight), new NSPoint(leftX, lineY));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX-genWidth, lineY-(rowHeight/2)), new NSPoint(leftX, lineY-(rowHeight/2)));
//       lineY += rowHeight;
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY), new NSPoint(rightX, lineY));
//       NSGraphics.drawAttributedString(new NSAttributedString(" Leonard Ashby Wood", new NSDictionary(times9, NSAttributedString.FontAttributeName)), new NSPoint(leftX, lineY));
//          NSGraphics.drawAttributedString(nameText, new NSPoint(leftX, lineY-NSGraphics.sizeOfAttributedString(nameText).height()));
//       lineY += rowHeight;
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY), new NSPoint(rightX, lineY));
//       NSGraphics.drawAttributedString(new NSAttributedString(" Norma Fay Justesen", new NSDictionary(times9, NSAttributedString.FontAttributeName)), new NSPoint(leftX, lineY));
//          NSGraphics.drawAttributedString(nameText, new NSPoint(leftX, lineY-NSGraphics.sizeOfAttributedString(nameText).height()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX, lineY-rowHeight), new NSPoint(leftX, lineY));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(leftX-genWidth, lineY-(rowHeight/2)), new NSPoint(leftX, lineY-(rowHeight/2)));


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
//             System.out.println("drawing generation "+gen+" #"+j+"\nleft from "+(rect.width()*(gen-1)/generations)+" to "+(rect.width()*gen/generations));
//             final NSPoint leftPoint = new NSPoint(rect.width()*(gen-1)/generations,rect.height()*j/rows);
//             System.out.println("leftPoint="+leftPoint);
//             final NSPoint rightPoint = new NSPoint(rect.width()*gen/generations,rect.height()*j/rows);
//             System.out.println("rightPoint="+rightPoint);
//             NSBezierPath.strokeLineFromPoint( leftPoint, rightPoint);
//          }
//       }
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
//       NSBezierPath.strokeLineFromPoint(new NSPoint(0,rect.midY()), new NSPoint(100,rect.midY()));
       } catch (Exception e) {
          e.printStackTrace();  //To change body of catch statement use Options | File Templates.
       }
    }

   private NSPoint drawIndividualInRect(Individual individual, NSRect nsRect) {
      return drawIndividualInRect(individual, nsRect, 0);
   }

   private NSPoint drawIndividualInRect(Individual individual, NSRect rect, float detailOffset) {
      System.out.println("drawIndividualInRect("+individual+", "+rect+")");
      rect = rect.rectByInsettingRect(1,1);
//      colors[currColor++].set();
//      NSGraphics.frameRectWithWidth(rect, 3);
      float y = rect.y();
      final NSAttributedString detailStr = makeIndividualDetailString(individual);
      NSRect detailRect = rect.rectByOffsettingRect(detailOffset, 0);
      NSGraphics.drawAttributedString(detailStr, detailRect.origin());
      y += NSGraphics.sizeOfAttributedString(detailStr).height();
      // draw ordinance boxes
      NSAttributedString ordString = new NSAttributedString("B", times9Dict);
      NSAttributedString ordString2 = new NSAttributedString("w", times9Dict);
      NSAttributedString ordString3 = new NSAttributedString("i", times9Dict);
      NSAttributedString ordString4 = new NSAttributedString("y", times9Dict);
      NSAttributedString[] ordStrings = new NSAttributedString[5];
      ordStrings[0] =  new NSAttributedString("B", times9Dict);
      ordStrings[1] =  new NSAttributedString("E", times9Dict);
      ordStrings[2] =  new NSAttributedString("P", times9Dict);
      ordStrings[3] =  new NSAttributedString("S", times9Dict);
      ordStrings[4] =  new NSAttributedString("C", times9Dict);
      Ordinance[] ordinances = new Ordinance[5];
      ordinances[0] = individual.getLDSBaptism();
      ordinances[1] = individual.getLDSEndowment();
      ordinances[2] = individual.getLDSSealingToParents();
      ordinances[3] = individual.getFamilyAsSpouse().getSealingToSpouse();
      ordinances[4] = null;
      System.out.println("B size="+NSGraphics.sizeOfAttributedString(ordString));
      System.out.println("w size="+NSGraphics.sizeOfAttributedString(ordString2));
      System.out.println("i size="+NSGraphics.sizeOfAttributedString(ordString3));
      System.out.println("y size="+NSGraphics.sizeOfAttributedString(ordString4));
      NSSize boxSize = new NSSize(NSGraphics.sizeOfAttributedString(ordString).height()-2, NSGraphics.sizeOfAttributedString(ordString).height()-2);
      for (int i=0; i<5; i++) {
         final NSRect boxRect = new NSRect(new NSPoint(rect.x()+1+detailOffset+((2+boxSize.width())*i), y-1), boxSize);
         NSGraphics.frameRectWithWidth(boxRect, .3f);
         NSGraphics.drawAttributedString(ordStrings[i], boxRect.rectByOffsettingRect(2,1));
         if (ordinances[i] != null && ordinances[i].isCompleted()) {
            float defaultLineWidth = NSBezierPath.defaultLineWidth();
            NSBezierPath.setDefaultLineWidth(.3f);
            NSBezierPath.strokeLineFromPoint(boxRect.origin(), new NSPoint(boxRect.maxX(), boxRect.maxY()));
            NSBezierPath.strokeLineFromPoint(new NSPoint(boxRect.x(), boxRect.maxY()), new NSPoint(boxRect.maxX(), boxRect.y()));
            NSBezierPath.setDefaultLineWidth(defaultLineWidth);
         }
      }
      y += boxSize.height() + 1; // boxRect height plus space
      float lineY = y;
      NSBezierPath.strokeLineFromPoint(new NSPoint(rect.x(), y), new NSPoint(rect.maxX(), y));
      NSGraphics.drawAttributedString(new NSAttributedString(" "+individual.getFullName(), times9Dict), new NSPoint(rect.x(), y));
      System.out.println("total height = "+(y+NSGraphics.sizeOfAttributedString(detailStr).height()));
      return new NSPoint(rect.x(), lineY);
   }

   private NSAttributedString makeIndividualDetailString(Individual individual) {
      Event birthEvent = individual.getBirthEvent();
      if (birthEvent == null) {birthEvent = new MyEvent();}
      String detailStr = "Born: "+birthEvent.getDateString()+"\n";
      detailStr += "Place: "+birthEvent.getPlace().getFormatString()+"\n";
      if (individual.getGender().equals(Gender.MALE)/* || currColor++%2==0*/) {
         Event marriageEvent = individual.getFamilyAsSpouse().getMarriageEvent();
         if (marriageEvent == null) {marriageEvent = new MyEvent();}
         detailStr += "Married: "+marriageEvent.getDateString()+"\n";
         detailStr += "Place: "+marriageEvent.getPlace().getFormatString()+"\n";
      }
      Event deathEvent = individual.getDeathEvent();
      if (deathEvent == null) {deathEvent = new MyEvent();}
      detailStr += "Died: "+deathEvent.getDateString()+"\n";
      detailStr += "Place: "+deathEvent.getPlace().getFormatString();
      return new NSAttributedString(detailStr, times9Dict);
   }

}
