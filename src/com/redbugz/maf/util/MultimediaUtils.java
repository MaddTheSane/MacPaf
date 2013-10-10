/*
 * Created on Dec 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.redbugz.maf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.apple.cocoa.application.NSDocument;
import com.apple.cocoa.application.NSDocumentController;
import com.apple.cocoa.application.NSImage;
import com.apple.cocoa.application.NSPICTImageRep;
import com.apple.cocoa.foundation.NSData;
import com.apple.cocoa.foundation.NSPathUtilities;
import com.redbugz.maf.Multimedia;

/**
 * @author logan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MultimediaUtils {
	public static final Logger log = Logger.getLogger(MultimediaUtils.class);
	
	private MultimediaUtils() {}

	/**
	 * @param preferredImage
	 * @return
	 */
	public static NSImage makeImageFromMultimedia(Multimedia multimedia) {
		log.debug("makeImageFromMultimedia():"+multimedia);
		NSImage image = new NSImage();
		try {
			if (multimedia == null) {
//				Thread.dumpStack();
				return image;
			}
			if (multimedia.getBytes().length > 0) {
				if (Multimedia.PICT_FORMAT.equals(multimedia.getFormat())) {
					log.debug("pict");
					log.debug(new NSPICTImageRep(new NSData(multimedia.getBytes())));
					byte[] b = multimedia.getBytes();
					byte[] header = new byte[512];
					byte[] pictbytes = new byte[512+b.length];
					System.arraycopy(header, 0, pictbytes, 0, 512);
					System.arraycopy(b, 0, pictbytes, 512, b.length);
					log.debug(new NSPICTImageRep(new NSData(pictbytes)));
					image.addRepresentation(new NSPICTImageRep(new NSData(multimedia.getBytes())));
				} else {
					log.debug("making image from data:\n"+Hex.toHex(multimedia.getBytes()));
					image = new NSImage(new NSData(multimedia.getBytes()));
				}
			}
		} catch (RuntimeException e) {
			log.warn("ImageUtils.makeImageFromMultimedia: Could not make a valid image from this multimedia object:"+multimedia);
			e.printStackTrace();
		}
		log.debug("image:"+image);
		return image;
	}

	//Returns the contents of the file in a byte array.
	  public static byte[] getBytesFromFile(File file) throws IOException {
		  if (!file.canRead()) {
			  return new byte[0];
		  }
	      InputStream is = new FileInputStream(file);
	  
	      // Get the size of the file
	      long length = file.length();
	  
	      // You cannot create an array using a long type.
	      // It needs to be an int type.
	      // Before converting to an int type, check
	      // to ensure that file is not larger than Integer.MAX_VALUE.
	      if (length > Integer.MAX_VALUE) {
	          // File is too large
	      }
	  
	      // Create the byte array to hold the data
	      byte[] bytes = new byte[(int)length];
	  
	      // Read in the bytes
	      int offset = 0;
	      int numRead = 0;
	      while (offset < bytes.length
	             && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	          offset += numRead;
	      }
	  
	      // Ensure all the bytes have been read in
	      if (offset < bytes.length) {
	          throw new IOException("Could not completely read file "+file.getName());
	      }
	  
	      // Close the input stream and return bytes
	      is.close();
	      return bytes;
	  }

	/**
	 * @param filename either a fully qualified path to a file, or just a filename
	 * @return
	 */
	public static File findFile(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			// since the absolute path doesn't work, search using just the file name
			String name = NSPathUtilities.lastPathComponent(filename);
			log.warn("MultimediaUtils.findFile() file does not exist: "+filename+". Beginning filesystem search for "+name+"...");
			// first search the document package multimedia directory
			NSDocument currentDocument = NSDocumentController.sharedDocumentController().currentDocument();
			if (currentDocument != null) {
				File docDir = new File(currentDocument.fileName());
				file = recursiveFileSearch(docDir, name); 
				// recursively search the parent directory for the current document
				File parentDir = docDir.getParentFile();
				if (parentDir == null) {
					// no parent directory, search the 
				}
			}
			// lastly search the users home directory
			File homeDir = new File(System.getProperty("user.home"));
//			file = recursiveFileSearch(homeDir, name);
		}
		return file;
	}

	/**
	 * @param docDir
	 * @param name
	 * @return
	 */
	private static File recursiveFileSearch(File startDir, String name) {
		log.debug("MultimediaUtils.recursiveFileSearch():"+startDir.getPath()+":"+name);
		File file = new File(name);
		boolean found = false;
		long startTime = System.currentTimeMillis();
		try {
			Vector searchDirs = new Vector();
			searchDirs.add(startDir);
			while (!found && !searchDirs.isEmpty()) {
				File dir = (File) searchDirs.remove(0);
//				log.debug("searching dir:"+dir.getPath());
				File[] children = dir.listFiles();
				for (int i = 0; i < children.length; i++) {
					File child = children[i];
//					log.debug("comparing file:"+child.getName());
					if (child.isDirectory()) {
						searchDirs.add(child);
					} else if (child.isFile() && name.equals(child.getName())) {
						file = children[i];
						log.debug("found match: "+file.getAbsolutePath());
						found = true;
					}
				}
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("file search took: "+(System.currentTimeMillis() - startTime / 1000D) +" seconds.");
		return file;
	}
	
}
