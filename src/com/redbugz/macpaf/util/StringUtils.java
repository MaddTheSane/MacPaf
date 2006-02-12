/*
 * Created on Dec 23, 2004
 *
 */
package com.redbugz.macpaf.util;

import java.util.Collection;
import java.util.Iterator;

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
	
	public static boolean notEmpty(String string) {
		return ! isEmpty(string);
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
	
	public static String nonNullString(String string) {
		String result = "";
		if (string != null) {
			result = string;
		}
		return result;
	}
	
	public static String join( String token, String[] strings )
    {
        StringBuffer sb = new StringBuffer();
        
        for( int x = 0; x < ( strings.length - 1 ); x++ )
        {
            sb.append( strings[x] );
            sb.append( token );
        }
        sb.append( strings[ strings.length - 1 ] );
        
        return( sb.toString() );
    }

	public static String join( String token, Collection strings )
    {
        StringBuffer sb = new StringBuffer();
        
        for (Iterator iter = strings.iterator(); iter.hasNext();) {
            sb.append( iter.next() );
            if (iter.hasNext()) {
            		sb.append( token );
            }
        }
        
        return( sb.toString() );
    }
}
