//
//  XMLTest.java
//  Redwood
//
//  Created by Logan Allred on Wed Jan 30 2002.
//  Copyright (c) 2001 __MyCompanyName__. All rights reserved.
//

import org.jdom.Document;
//import org.com.redbugz.macpaf.jdom.Parent;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.SAXOutputter;
import org.jdom.output.XMLOutputter;

import gedml.GedcomOutputter;

import java.io.*;

import lee.gedcom.*;
import lee.xml.*;

import javax.swing.tree.TreeNode;

public class XMLTest {
	private static boolean debugging = false;

   public static TreeNode parseUsingLee(File gedcomFile) {
      GEDReader rdr = null;
      GEDParser parser = new GEDParser();
      try {
         rdr = new GEDReader(new FileReader(gedcomFile));
         parser.parse(rdr);
         rdr.close();
      } catch (Exception e) {
         e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      }
            GEDNode root = parser.getRoot();
      return root;
   }

   public static Document docParsedWithKay(File gedcomFile) {
      SAXBuilder builder = new SAXBuilder("gedml.GedcomParser");
      Document doc = null;
      try {
         doc = builder.build(gedcomFile);
      } catch (JDOMException e) {
         e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      } catch (IOException e) {
          e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      }
      if (debugging) {
         System.out.println("^*^*^* Doc parsed with gedml:");
      outputDocToConsole(doc);
      }
      return doc;
   }
   
   public static void outputWithKay(Document doc, OutputStream out) {
       GedcomOutputter gedcomOut = new GedcomOutputter(out);
       SAXOutputter outputter = new SAXOutputter(gedcomOut);
       try {
           outputter.output(doc);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

	public static Document parseGedcom(File gedcomFile) {
			Element root = new Element(gedcomFile.getName());
			Document doc = new Document(root);
		try {
//         testTrimLeadingWhitespace();
         BufferedReader fr = new BufferedReader(new FileReader(gedcomFile));
			String line = fr.readLine();
			int level, currLevel = 0;
			Element currElem = root;
			while (line != null) {
				debug("readline: "+line);
				level = Integer.parseInt(line.substring(0,1));
				if (level > 0)
				{
					String tag = line.substring(2);
					String content = new String();
					if (line.length() > 5)
					{
						tag = line.substring(2,6).trim();
//                  content = line.substring(6);
                  content = trimLeadingWhitespace(line.substring(6));
					}
						Element newElem = (Element) new Element(tag).addContent(content);
					if (level > currLevel)
					{
						if (line.indexOf("CONT") > 0)
						{
//                     currElem.addContent(line.substring(6));
                     currElem.addContent(line.substring(6).trim());
							level = currLevel;
							newElem = currElem;
						}
						else
						{
							currElem.addContent(newElem);
						}
					}
					else if (level == currLevel)
					{
						currElem.getParent().addContent(newElem);	
					}
					else
					{
						currElem = (Element) currElem.getParent();
						currElem.getParent().addContent(newElem);
					}
					currLevel = level;
						currElem = newElem;
				}
				else
				{
					String tagname = "Unknown-Top-level-Tag";
					if (line.indexOf("INDI") > 0)
					{
						tagname = "Individual";
					}
					else if (line.indexOf("SUBM") > 0)
					{
						tagname = "Submitter";
					}
					else if (line.indexOf("HEAD") > 0)
					{
						tagname = "Head";
					}
					else if (line.indexOf("TRLR") > 0)
					{
						tagname = "Trailer";
					}
                                        else if (line.indexOf("FAM") > 0)
                                        {
                                            tagname = "Family";
                                        }
                                        else if (line.indexOf("NOTE") > 0)
                                        {
                                            tagname = "Note";
                                        }
                                        currElem = (Element) new Element(tagname).addContent(line);
					root.addContent(currElem);
					currLevel = level;
				}
				line = fr.readLine();
			}
         outputDocToConsole(doc);

      }
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}

   private static void outputDocToConsole(Document doc) {
      try {
         XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
         if (debugging)
         {
            outputter.output(doc, System.out);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void testTrimLeadingWhitespace() {
      System.out.println("whitespace test:");
      System.out.println("|"+trimLeadingWhitespace(null)+"|");
      System.out.println("|"+trimLeadingWhitespace("")+"|");
      System.out.println("|"+trimLeadingWhitespace(" ")+"|");
      System.out.println("|"+trimLeadingWhitespace("A")+"|");
      System.out.println("|"+trimLeadingWhitespace(" A")+"|");
      System.out.println("|"+trimLeadingWhitespace("A ")+"|");
      System.out.println("|"+trimLeadingWhitespace("Lots of space at end         ")+"|");
      System.out.println("|"+trimLeadingWhitespace("     Lots of whitespace at beginning")+"|");
      System.out.println("|"+trimLeadingWhitespace("     Lots of whitespace at both ends        ")+"|");
      System.out.println("whitespace test done.");
   }

   private static String trimLeadingWhitespace(String s) {
      if (s == null) {
         return null;
      }
      if (s.length() <= 1) {
         return s.trim();
      }
      // if last character is not whitespace, return s.trim()
      if (!Character.isWhitespace(s.charAt(s.length()-1))) {
         return s.trim();
      }
      int index = 0;
      while (Character.isWhitespace(s.charAt(index))) {index++;};
      return s.substring(0, index).trim()+s.substring(index);
   }

   private static void debug(String message)
{
		if (debugging)
		{
			System.out.println(message);
		}
}

}
