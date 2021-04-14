package club.veluxpvp.core.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class TimeUtil {

	public static String formatDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
	}
	
	public static int timeToSeconds(String time) {
		String t = time.toLowerCase();
		int number = 0;
		
		try {
			number = Integer.valueOf(t.replaceAll("s", "").replaceAll("m", "").replaceAll("h", "").replaceAll("d", "").replaceAll("w", "").replaceAll("y", ""));
		} catch(NumberFormatException e) { return -1; }
	
		if(t.contains("s")) {
			return number;
		}
		
		if(t.contains("m")) {
			return number * 60;
		}
		
		if(t.contains("h")) {
			return (number * 60) * 60;
		}
		
		if(t.contains("d")) {
			return ((number * 60) * 60) * 24;
		}
		
		if(t.contains("w")) {
			return (((number * 60) * 60) * 24) * 7;
		}
		
		if(t.contains("y")) {
			return (((number * 60) * 60) * 24) * 365;
		}
		
		return -1;
	}
	
	public static String getFormattedDuration(int time, boolean startingZero) {
		int hours;
		int minutes;
		int seconds;
		hours = (int) (time % 86400) / 3600;
		minutes = (int) ((time % 3600) / 60);
		seconds = (int) ((time % 3600) % 60);
		
		String duration = "";
		
		if(hours >= 1) {
			if(hours < 10) {
				duration = "0" + hours + ":";
			} else {
				duration = hours + ":";
			}
		}
		
		if(minutes < 10) {
			duration += (startingZero ? "0" : "") + minutes + ":";
		} else {
			duration += minutes + ":";
		}
		
		if(seconds < 10) {
			duration += "0" + seconds;
		} else {
			duration += seconds;
		}
		
		return duration;
	}
	
	public static String getFormattedDuration(int time) {
		int days;
		int hours;
		int minutes;
		int seconds;
		days = (int) (time / 86400);
		hours = (int) (time % 86400) / 3600;
		minutes = (int) ((time % 3600) / 60);
		seconds = (int) ((time % 3600) % 60);
		
		String duration = "";
		
		if(seconds > 0) duration += seconds + (seconds > 1 ? " seconds" : " second");
		if(minutes > 0) duration = minutes + (minutes > 1 ? " minutes, " : " minute, ") + duration;
		if(hours > 0) duration = hours + (hours > 1 ? " hours, " : " hour, ") + duration;
		if(days > 0) duration = days + (days > 1 ? " days, " : " day, ") + duration;

		return duration;
	}
	
	public static Date addTimeToDate(String duration) {
		String d = duration.toLowerCase();
		Calendar c = Calendar.getInstance();
		
		c.setTime(new Date());
		
		if(d.contains("s")) {
			int seconds = Integer.valueOf(d.replaceAll("s", ""));
			
			c.add(Calendar.SECOND, seconds);
			return c.getTime();
		} else if(d.contains("m")) {
			int minutes = Integer.valueOf(d.replaceAll("m", ""));
			
			c.add(Calendar.MINUTE, minutes);
			return c.getTime();
		} else if(d.contains("h")) {
			int hours = Integer.valueOf(d.replaceAll("h", ""));
			
			c.add(Calendar.HOUR_OF_DAY, hours);
			return c.getTime();
		} else if(d.contains("d")) {
			int days = Integer.valueOf(d.replaceAll("d", ""));
			
			c.add(Calendar.DAY_OF_MONTH, days);
			return c.getTime();
		} else if(d.contains("w")) {
			int weeks = Integer.valueOf(d.replaceAll("w", ""));
			
			c.add(Calendar.WEEK_OF_MONTH, weeks);
			return c.getTime();
		} else if(d.contains("y")) {
			int years = Integer.valueOf(d.replaceAll("y", ""));
			
			c.add(Calendar.YEAR, years);
			return c.getTime();
		}
		
		return null;
	}
}
