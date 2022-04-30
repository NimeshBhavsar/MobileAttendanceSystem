package com.krishna.attendance.Persistence.DbContext;

import androidx.room.TypeConverter;

import com.krishna.attendance.Core.Enums.AttendanceStatusEnum;

import java.sql.Time;
import java.util.Date;

public class Converters {
    // DATE converters
    @TypeConverter
    public static Date fromTimStamp(Long value){
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

    // TIME converters
    @TypeConverter
    public static Time fromLongToTime(Long value){
        return value == null ? null : new Time(value);
    }
    @TypeConverter
    public static Long timeToLong(Time time){
        return time == null ? null : time.getTime();
    }

    // Attendance Status Converter
    @TypeConverter
    public static AttendanceStatusEnum fromAttendanceStatusString(String value){
        return value == null ? AttendanceStatusEnum.Absent : value.equals("") ? AttendanceStatusEnum.Absent : AttendanceStatusEnum.valueOf(value);
    }
    @TypeConverter
    public static String fromAttendanceStatusEnum(AttendanceStatusEnum value){
        return value.toString();
    }

}
