package com.redbugz.macpaf.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

	public static final DateTimeFormatter fileTimestampFormat = DateTimeFormat.forPattern("YYDDD-HHmmss");

	public static String makeFileTimestampString() {
		return fileTimestampFormat.print(new DateTime());
	}

}
