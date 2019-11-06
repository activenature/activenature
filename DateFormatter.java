package com.active.chdating.net;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public String ZhihuDailyDateFormat(long date){
        String sDate;
        Date d=new Date(date);
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        sDate=format.format(d);

        return sDate;
    }
}
