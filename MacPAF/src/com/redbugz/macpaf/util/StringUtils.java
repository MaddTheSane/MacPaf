/*
 * Created on Dec 23, 2004
 *
 */
package com.redbugz.macpaf.util;

/**
 * @author logan
 *
 */
public class StringUtils {
	private StringUtils() {}
	
	public static boolean isEmpty(String string) {
		if (string == null || string.length() == 0) {
			return true;
		}
		return false;
	}

	public static String trimLeadingWhitespace(String s) {
		if (s == null) {
		  return null;
		}
		if (s.length() <= 1) {
		  return s.trim();
		}
		// if last character is not whitespace, return s.trim()
		if (!Character.isWhitespace(s.charAt(s.length() - 1))) {
		  return s.trim();
		}
		int index = 0;
		while (Character.isWhitespace(s.charAt(index))) {index++;
		}
		;
		return s.substring(0, index).trim() + s.substring(index);
	  }
}
