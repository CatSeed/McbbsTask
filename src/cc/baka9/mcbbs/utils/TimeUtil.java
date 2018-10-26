package cc.baka9.mcbbs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static long randomTime(long baseTime, long minMillisecond, long maxMillisecond) {
		return (long) (baseTime + minMillisecond + (Math.random() * (maxMillisecond - minMillisecond)));

	}

	public static long randomTime(long baseTime, int minSecond, int maxSecond) {
		return randomTime(baseTime, minSecond * 1000L, maxSecond * 1000L);

	}

	public static long getTomorrowBegin() {
		long now = System.currentTimeMillis();
		long tomorrow = now + 1000 * 60 * 60 * 24 - (now + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);

		return tomorrow;
	}

	public static long getToDayBegin() {
		long now = System.currentTimeMillis();
		long today = now - (now + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);

		return today;
	}

	public static String getFormatNow() {
		return dateFormat.format(new Date());
	}

	public static String getFormatTime(long time) {
		return dateFormat.format(time);
	}

	public static long formatHHMM(String str) {
		String[] strs = str.split("-");
		long h = Integer.valueOf(strs[0]) * 60 * 60 * 1000;
		long m = Integer.valueOf(strs[1]) * 60 * 1000;
		return h + m;

	}

}
