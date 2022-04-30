package com.krishna.attendance.Core.Models;

import androidx.room.Ignore;

import com.krishna.attendance.Core.Enums.AttendanceStatusEnum;

import java.util.Date;

public class AttendanceModel {
    public long id;

    public long studentId;
    public String studentName;
    public String studentCrn;

    public long dayId;
    public Date date;

    //public int subjectId;
    //public String subjectName;

    /**
     * Enum: AttendanceStatusEnum
     */
    public AttendanceStatusEnum status; //

    public AttendanceModel() {
    }

    @Ignore
    public AttendanceModel(long id, long studentId, String studentName, String studentCrn, long dayId, AttendanceStatusEnum status) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentCrn = studentCrn;
        this.dayId = dayId;
        this.status = status;
    }
}
