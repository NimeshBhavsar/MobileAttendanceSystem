package com.krishna.attendance.Persistence.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.krishna.attendance.Persistence.Entities.SubjectStudentEntity;

@Dao
public interface SubjectStudentDao extends BaseDao<SubjectStudentEntity> {

    @Query("select * from subject_students where subject_id = :subjectId and student_id = :studentId ")
    SubjectStudentEntity get(long subjectId, long studentId);

}
