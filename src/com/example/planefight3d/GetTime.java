package com.example.planefight3d;

import java.util.Calendar;
import java.util.Locale;

public class GetTime {
	
	public static String get_time()
	{
		Calendar c=Calendar.getInstance(Locale.CHINA);
		int year=c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH);
		int day=c.get(Calendar.DAY_OF_MONTH);
		int hour=c.get(Calendar.HOUR_OF_DAY);
		int minute=c.get(Calendar.MINUTE);
		int second=c.get(Calendar.SECOND);
		String times=year+"/"+month+"/"+day+"/"+hour+"/"+minute+"/"+second;
		return times;
	}

}
