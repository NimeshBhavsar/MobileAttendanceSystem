package com.krishna.attendance.Persistence.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class SubjectEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "course")
    public String course;

    /**
     * Whether the class/session has ended
     */
    @ColumnInfo(name = "ended")
    public boolean ended;

    public SubjectEntity() {
    }

    @Ignore
    public SubjectEntity(long id, String name, String course, boolean ended) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.ended = ended;
    }
}
