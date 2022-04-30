package com.krishna.attendance.Persistence.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class StudentEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    /**
     * Class Roll Number (CRN) or Serial No. (SNo)
     */
    @ColumnInfo(name = "crn")
    public String crn;

    @ColumnInfo(name="image")
    public String image;

    public StudentEntity() {
    }

    @Ignore
    public StudentEntity(long id, String name, String crn, String image) {
        this.id = id;
        this.name = name;
        this.crn = crn;
        this.image = image;
    }
}
