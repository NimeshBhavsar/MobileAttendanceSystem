package com.krishna.attendance.Persistence.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Persistence.Entities.SubjectEntity;

import java.util.List;

@Dao
public interface SubjectDao extends BaseDao<SubjectEntity> {

    @Query("select * from subjects where id = :id ")
    SubjectEntity find(long id);

    @Query("select " +
            "sub.id as id, " +
            "sub.name as name, " +
            "sub.course as course, " +
            "sub.ended as ended, " +
            "(select count(*) from subject_students where subject_id = sub.id) as studentsCount, " +
            "(select count(*) from days where subject_id = sub.id) as daysCount " +
            "from subjects sub " +
            "where sub.id = :subjectId ")
    SubjectModel getSubject(long subjectId);

    @Query("select " +
            "sub.id as id, " +
            "sub.name as name, " +
            "sub.course as course, " +
            "sub.ended as ended, " +
            "(select count(*) from subject_students where subject_id = sub.id) as studentsCount, " +
            "(select count(*) from days where subject_id = sub.id) as daysCount " +
            "from subjects sub " +
            "order by sub.ended asc, sub.course asc ")
    LiveData<List<SubjectModel>> getSubjectList();

    @Query("select " +
            "sub.id as id, " +
            "sub.name as name, " +
            "sub.course as course, " +
            "sub.ended as ended, " +
            "(select count(*) from subject_students where subject_id = sub.id) as studentsCount, " +
            "(select count(*) from days where subject_id = sub.id) as daysCount " +
            "from subjects sub " +
            "where sub.id = :subjectId ")
    LiveData<SubjectModel> getSubjectLive(long subjectId);


}
