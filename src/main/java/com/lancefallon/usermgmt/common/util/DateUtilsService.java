package com.lancefallon.usermgmt.common.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateUtilsService {
	public Date calendarToDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}
}
