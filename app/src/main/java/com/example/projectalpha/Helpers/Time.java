package com.example.projectalpha.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Time {

    private static String hari(String time){
        String hari = null;

        Calendar c = Calendar.getInstance();

        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "EEEE";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, new Locale("in", "ID"));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, new Locale("in", "ID"));

        Date date;

        try {
            date = inputFormat.parse(time);
            c.setTime(date);
            hari = outputFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return hari;
    }

    public static String tanggal(String time){
        String tanggal, strH = null, strB = null, strT = null;

        String inputPattern = "yyyy-MM-dd";
        String hariPattern = "dd";
        String bulanPattern = "MM";
        String tahunPattern = "yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, new Locale("in", "ID"));
        SimpleDateFormat hariFormat = new SimpleDateFormat(hariPattern, new Locale("in", "ID"));
        SimpleDateFormat bulanFormat = new SimpleDateFormat(bulanPattern, new Locale("in", "ID"));
        SimpleDateFormat tahunFormat = new SimpleDateFormat(tahunPattern, new Locale("in", "ID"));

        Date date;

        try {
            date = inputFormat.parse(time);
            strH = hariFormat.format(date);
            strB = bulanFormat.format(date);
            strT = tahunFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert strB != null;
        switch (strB){
            case "02":
                tanggal = hari(time)+", "+strH+" Februari "+strT;
                break;
            case "03":
                tanggal = hari(time)+", "+strH+" Maret "+strT;
                break;
            case "04":
                tanggal = hari(time)+", "+strH+" April "+strT;
                break;
            case "05":
                tanggal = hari(time)+", "+strH+" Mei "+strT;
                break;
            case "06":
                tanggal = hari(time)+", "+strH+" Juni "+strT;
                break;
            case "07":
                tanggal = hari(time)+", "+strH+" Juli "+strT;
                break;
            case "08":
                tanggal = hari(time)+", "+strH+" Agustus "+strT;
                break;
            case "09":
                tanggal = hari(time)+", "+strH+" September "+strT;
                break;
            case "10":
                tanggal = hari(time)+", "+strH+" Oktober "+strT;
                break;
            case "11":
                tanggal = hari(time)+", "+strH+" November "+strT;
                break;
            case "12":
                tanggal = hari(time)+", "+strH+" Desember "+strT;
                break;
            default:
                tanggal = hari(time)+", "+strH+" Januari "+strT;
                break;
        }

        return tanggal;
    }

}
