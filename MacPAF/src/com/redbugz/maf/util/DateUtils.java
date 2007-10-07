package com.redbugz.maf.util;

import org.joda.time.*;
import org.joda.time.format.*;

public class DateUtils {

	public static final DateTimeFormatter fileTimestampFormat = DateTimeFormat.forPattern("YYDDD-HHmmss");

	public static String makeFileTimestampString() {
		return fileTimestampFormat.print(new DateTime());
	}

}
