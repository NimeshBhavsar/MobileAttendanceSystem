package com.krishna.attendance.Core.Models;

import androidx.room.Ignore;

public class StudentModel {

    public long id;

    public String name;

    /**
     * Class Roll Number (CRN) or Serial No. (SNo)
     */
    public String crn;

    public String image;

    // for CREATE, UPDATE operations
    @Ignore
    public long subjectId;

    public float presencePercent;

    public int classDays; // total class days
    public int presenceDays; // total presence days

    public StudentModel() {
    }

    @Ignore
    public StudentModel(long id, String name, String crn, String image, int subjectId) {
        this.id = id;
        this.name = name;
        this.crn = crn;
        this.image = image;
        this.subjectId = subjectId;
    }
}
