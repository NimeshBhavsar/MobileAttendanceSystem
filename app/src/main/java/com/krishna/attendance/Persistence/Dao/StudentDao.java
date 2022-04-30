package com.krishna.attendance.Persistence.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Persistence.Entities.StudentEntity;

import java.util.List;

@Dao
public interface StudentDao extends BaseDao<StudentEntity> {

    @Query("select count(*) " +
            "from students where crn = :crn and id != :id ")
    boolean exists(String crn, long id);

    @Query("select * from students where id = :id ")
    StudentEntity find(long id);

    @Query("select " +
            "std.id as id, " +
            "std.crn as crn, " +
            "std.name as name, " +
            "0 as presencePercent, " +
            "0 presenceDays, "+
            "0 as classDays, " +
            "std.image as image " +
            "from students std " +
            "where std.id = :id ")
    StudentModel getStudent(long id);

    //TODO: need to get total class days, presence days,
    @Query("select " +
            "std.id as id, " +
            "std.crn as crn, " +
            "std.name as name, " +
            "100.0 * (select count(*) from attendances where subject_id = :subjectId and student_id = std.id and status = 'Present') " +
            "   / (select count(*) from days where subject_id = :subjectId) as presencePercent, " +
            "(select count(*) from attendances where subject_id = :subjectId and student_id = std.id and status = 'Present') as presenceDays, "+
            "(select count(*) from days where subject_id = :subjectId) as classDays, " +
            "std.image as image " +
            "from students std " +
            "inner join subject_students substd on std.id = substd.student_id " +
            "where std.id = :studentId and substd.subject_id = :subjectId ")
    LiveData<StudentModel> getStudent(long subjectId, long studentId);

    @Query("select " +
            "std.id as id, " +
            "std.crn as crn, " +
            "std.name as name, " +
            "100.0 * (select count(*) from attendances where subject_id = :subjectId and student_id = std.id and status = 'Present') " +
            "   / (select count(*) from days where subject_id = :subjectId) as presencePercent, " +
            "(select count(*) from attendances where subject_id = :subjectId and student_id = std.id and status = 'Present') as presenceDays, "+
            "(select count(*) from days where subject_id = :subjectId) as classDays, " +
            "std.image as image " +
            "from subject_students ss " +
            "inner join students std on ss.student_id = std.id " +
            "where ss.subject_id = :subjectId " +
            "order by std.crn asc ")
    List<StudentModel> getStudentList(long subjectId);

    @Query("select " +
            "std.id as id, " +
            "std.crn as crn, " +
            "std.name as name, " +
            "100.0 * (select count(*) from attendances where subject_id = :subjectId and student_id = std.id and status = 'Present') " +
            "   / (select count(*) from days where subject_id = :subjectId) as presencePercent, " +
            "(select count(*) from attendances where subject_id = :subjectId and student_id = std.id and status = 'Present') as presenceDays, "+
            "(select count(*) from days where subject_id = :subjectId) as classDays, " +
            "std.image as image " +
            "from subject_students ss " +
            "inner join students std on ss.student_id = std.id " +
            "where ss.subject_id = :subjectId " +
            "order by std.crn asc ")
    LiveData<List<StudentModel>> getStudentListLive(long subjectId);

}
