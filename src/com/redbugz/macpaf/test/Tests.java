/*
 * Created on Dec 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.macpaf.test;

import java.io.File;

import org.apache.log4j.Logger;

import com.apple.cocoa.application.NSBitmapImageRep;
import com.apple.cocoa.application.NSImage;
import com.apple.cocoa.application.NSImageRep;
import com.apple.cocoa.foundation.NSData;
import com.redbugz.macpaf.util.Base64;
import com.redbugz.macpaf.util.Hex;
import com.redbugz.macpaf.util.MultimediaUtils;
import com.redbugz.macpaf.util.StringUtils;
import com.redbugz.macpaf.util.XMLTest;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Tests {
	public static final Logger log = Logger.getLogger(Tests.class);
	
public static void testBase64() {
  	byte[] none=new byte[0];
  	byte[] one = new byte[] {0x00};
  	byte[] two = new byte[] {0x7f,0x7f};
  	byte[] three = new byte[] {0x00,0x00,0x00};
  	byte[] four = new byte[] {-1,-1,-1,-1};
  	String s = Base64.encodeBytes(new byte[] {0x50, 0x44, 0x46, 0x00, 0x0b, 0x0c, 0x25, 0x26, 0x3f, 0x3f});
  	log.debug(s);
  	log.debug("out:"+Hex.toHex(Base64.decode("I2F4..gA7GMzDzzz")));
  	byte[] allbytes = new byte[Byte.MAX_VALUE-Byte.MIN_VALUE+1];
  	byte b = Byte.MIN_VALUE;
  	for (int i = 0; i < allbytes.length; i++) {
//  		log.debug("adding byte: "+b+"("+Hex.toHex(b)+")");
		allbytes[i] = b++;
	}
  	log.debug("allbytes:\n"+Base64.encodeBytes(allbytes, Base64.DONT_BREAK_LINES));
  	log.debug("again   :\n"+Hex.toHexF(Base64.decode(Base64.encodeBytes(allbytes, Base64.DONT_BREAK_LINES))));
  	log.debug("roundtrip:"+allbytes.equals(Hex.toHexF(Base64.decode(Base64.encodeBytes(allbytes, Base64.DONT_BREAK_LINES)))));
  	log.debug("none  :"+Hex.toHex(Base64.decode(Base64.encodeBytes(none, Base64.DONT_BREAK_LINES))));
  	log.debug("one   :"+Hex.toHex(Base64.decode(Base64.encodeBytes(one, Base64.DONT_BREAK_LINES))));
  	log.debug("two   :"+Hex.toHex(Base64.decode(Base64.encodeBytes(two, Base64.DONT_BREAK_LINES))));
  	log.debug("three :"+Hex.toHex(Base64.decode(Base64.encodeBytes(three, Base64.DONT_BREAK_LINES))));
	log.debug("four  :"+Hex.toHex(Base64.decode(Base64.encodeBytes(four, Base64.DONT_BREAK_LINES))));
	
	try {
		log.debug(new NSImage(new NSData(Base64.decode(".HM.......k.1..F.jwA.Dzzzzw............A....1.........0U.66..E.8.......A..k.a6.A.......A..k.........../6....G.......0../..U......w1/m........HC0..../...zzzzzzzz..5zzk..AnA..U..W6U....2rRrRrRrR.Dw...............k.1.......1..A...5ykE/zzzx/.g//.Hxzk6/.Tzy/.k1/Dw/.Tvz.E5zzUE9/kHz.Tw2/DzzzEEA.kE2zk5yzk2/zzs21.U2/Dw/.Tw/.Tzy/.fy/.HzzkHzzzo21Ds00.E2.UE2.U62/.k./Ds0.UE0/Do0..E8/UE2.U62.U9w/.Tx/.20.jg2/jo2..9u/.0U.6A.zk"))));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void testImageFileBytes(File file) {
	try {
		byte[] filebytes = MultimediaUtils.getBytesFromFile(file);
		log.debug("bytes "+file.getName()+":\n"+Hex.toHex(filebytes));
//		log.debug("Fbytes "+file.getName()+":\n"+Hex.toHexF(filebytes));
		log.debug("encoded "+file.getName()+":\n"+Base64.encodeBytes(filebytes));
		log.debug("decoded "+file.getName()+":\n"+Hex.toHex(Base64.decode(Base64.encodeBytes(filebytes))));
		log.debug("roundtrip OK? "+file.getName()+": "+Hex.toHex(filebytes).equals(Hex.toHex(Base64.decode(Base64.encodeBytes(filebytes)))));
		log.debug(new NSImage(file.toURL()));
		log.debug("valid:"+new NSImage(file.toURL()).isValid());
//		log.debug("png type?:"+((NSBitmapImageRep) new NSImage(file.toURL()).representations().lastObject()).valueForProperty(NSBitmapImageRep.PNGFileType));
		log.debug(new NSImage(new NSData(filebytes)));
	} catch (Exception e) {
		e.printStackTrace();
	}		
}

public static void testImageFiles() {
	log.debug("image filetypes: "+NSImage.imageFileTypes());
	try {
		File testbmp = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test.bmp");
		File testgif = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test.gif");
		File testjpeg = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test.jpeg");
		File testpict = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test.pict");
		File testpng = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test.png");
		File testtiff = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test.tiff");
		File test2pict = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test2.pict");
		File test2tiff = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test2.tiff");
		File test2gif = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test2.gif");
		File test3gif = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test3.gif");
		File test4gif = new File("/Users/logan/Projects/MacPAF/gedcom/Test files/test4.gif");

		testImageFileBytes(testbmp);
		testImageFileBytes(testgif);
		testImageFileBytes(testjpeg);
		testImageFileBytes(testpict);
		testImageFileBytes(testpng);
		testImageFileBytes(testtiff);
		testImageFileBytes(test2pict);
		testImageFileBytes(test2tiff);
		testImageFileBytes(test2gif);
		testImageFileBytes(test3gif);
		testImageFileBytes(test4gif);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void testTrimLeadingWhitespace() {
	log.debug("whitespace test:");
	log.debug("|" + StringUtils.trimLeadingWhitespace(null) + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace("") + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace(" ") + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace("A") + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace(" A") + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace("A ") + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace("Lots of space at end         ") + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace("     Lots of whitespace at beginning") + "|");
	log.debug("|" + StringUtils.trimLeadingWhitespace("     Lots of whitespace at both ends        ") + "|");
	log.debug("whitespace test done.");
  }

}
