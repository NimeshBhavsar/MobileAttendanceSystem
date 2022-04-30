package com.krishna.attendance.Persistence.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "subject_students")
public class SubjectStudentEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "subject_id")
    public long subjectId;

    @ColumnInfo(name = "student_id")
    public long studentId;

    public SubjectStudentEntity() {
    }

    @Ignore
    public SubjectStudentEntity(long id, long subjectId, long studentId) {
        this.id = id;
        this.subjectId = subjectId;
        this.studentId = studentId;
    }
}
