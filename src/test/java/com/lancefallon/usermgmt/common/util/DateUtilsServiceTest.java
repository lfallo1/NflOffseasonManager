package com.lancefallon.usermgmt.common.util;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsServiceTest {

    private static final int DATE = 1;
    private static final int MONTH = 10;
    private static final int YEAR = 2018;

    @Test
    void calendarToDate() {
        DateUtilsService dateUtilsService = new DateUtilsService();
        Date result = dateUtilsService.calendarToDate(YEAR, MONTH, DATE);

        Calendar resultCal = Calendar.getInstance();
        resultCal.setTime(result);

        assertEquals(resultCal.get(Calendar.YEAR), YEAR);
        assertEquals(resultCal.get(Calendar.MONTH), MONTH - 1); //0-based index for month
        assertEquals(resultCal.get(Calendar.DATE), DATE);
    }
}