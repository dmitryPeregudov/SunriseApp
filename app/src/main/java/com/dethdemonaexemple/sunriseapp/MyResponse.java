package com.dethdemonaexemple.sunriseapp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyResponse implements Serializable {
     String sunrise;

     String sunset;

     String solarNoon;

     String dayLength;

     String civilTwilightBegin;

     String civilTwilightEnd;

     String nauticalTwilightBegin;

     String nauticalTwilightEnd;

     String astronomicalTwilightBegin;

     String astronomicalTwilightEnd;

    public MyResponse(Results results) {
        try{
        this.sunrise ="Sunrise :"+ toLocalDateString(results.getSunrise());
        this.sunset ="Sunset :"+ toLocalDateString(results.getSunset());
        this.solarNoon = "Solar noon :"+ toLocalDateString(results.getSolarNoon());
        this.dayLength ="Day length :"+ dayString(results.getDayLength());
        this.civilTwilightBegin ="Civil twilight begin :"+ toLocalDateString(results.getCivilTwilightBegin());
        this.civilTwilightEnd ="Civil twilight end :"+ toLocalDateString(results.getCivilTwilightEnd());
        this.nauticalTwilightBegin ="Nautical twilight begin :"+ toLocalDateString(results.getNauticalTwilightBegin());
        this.nauticalTwilightEnd ="Nautical twilight end :"+ toLocalDateString(results.getNauticalTwilightEnd());
        this.astronomicalTwilightBegin ="Astronomical twilight begin :"+ toLocalDateString(results.getAstronomicalTwilightBegin());
        this.astronomicalTwilightEnd ="Astronomical twilight end :"+ toLocalDateString(results.getAstronomicalTwilightEnd());
        }catch (ParseException e){e.printStackTrace();}
    }
    private static String toLocalDateString(String dat) throws ParseException {

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        Date myDate = date.parse(dat);
        date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a",Locale.getDefault());

        return date.format(myDate);
    }

    private static String dayString(String dat) throws ParseException {

        DateFormat date = new SimpleDateFormat("s", Locale.getDefault());
        Date myDate = date.parse(dat);
        date=new SimpleDateFormat("HH:mm:ss",Locale.getDefault());

        return date.format(myDate);
    }

}