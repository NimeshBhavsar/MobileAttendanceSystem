package com.krishna.attendance.Persistence.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.krishna.attendance.Persistence.DbContext.Converters;

import java.util.Date;

@Entity(tableName = "days")
public class DayEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "subject_id")
    public long subjectId;

    /**
     * NOT implemented for now
     * Verified "attendance day" is either not editable or tracked for each edits (e.g. logs)
     */
    @ColumnInfo(name = "verified")
    public boolean verified;

    public DayEntity() {
    }

    @Ignore

    public DayEntity(long id, Date date, long subjectId, boolean verified) {
        this.id = id;
        this.date = date;
        this.subjectId = subjectId;
        this.verified = verified;
    }
}
