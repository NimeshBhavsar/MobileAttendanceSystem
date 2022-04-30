package com.krishna.attendance.Core.Models;

import androidx.room.Ignore;

public class SubjectModel {
    public long id;

    public String name;

    public String course;

    /**
     * If the class/session has ended ?
     */
    public boolean ended;

    public int studentsCount;
    public int daysCount;

    public SubjectModel() {
    }

   @Ignore
    public SubjectModel(long id, String name, String course, boolean ended) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.ended = ended;
    }
}
