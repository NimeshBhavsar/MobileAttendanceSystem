package com.krishna.attendance.Core.Models;

import androidx.room.Ignore;

import java.util.Date;
import java.util.List;

public class DayModel {
    public long id;

    public Date date;

    public long subjectId;

    public String subject;
    public String group;

    /**
     * NOT implemented for now
     * TODO:: Verified "attendance day" is either not editable or tracked for each edits (e.g. logs)
     */
    public boolean verified;

    public int presentStudents;
    public int totalStudents;
    public float presentPercent;

    @Ignore
    public List<AttendanceModel> attendanceList;

    public DayModel() {
    }

    @Ignore
    public DayModel(long id, Date date, long subjectId, boolean verified) {
        this.id = id;
        this.date = date;
        this.subjectId = subjectId;
        this.verified = verified;
    }
}
