package com.krishna.attendance.Persistence.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.krishna.attendance.Core.Enums.AttendanceStatusEnum;

@Entity(tableName = "attendances")
public class AttendanceEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "student_id")
    public long studentId;

    @ColumnInfo(name = "day_id")
    public long dayId;

    /**
     * Enum: AttendanceStatusEnum
     */
    @ColumnInfo(name = "status")
    public AttendanceStatusEnum status; //

    public AttendanceEntity() {
    }

    @Ignore
    public AttendanceEntity(long id, long studentId, long dayId, AttendanceStatusEnum status) {
        this.id = id;
        this.studentId = studentId;
        this.dayId = dayId;
        this.status = status;
    }
}
