package br.com.alura.roomapplication.database.conversor;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class ConversorData {

    @TypeConverter
    public static Long converteCalenderToLong(Calendar data) {
        return data.getTime().getTime();
    }

    @TypeConverter
    public static Calendar converteLongToCalendar(Long data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(data));
        return calendar;
    }

}
