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
}
