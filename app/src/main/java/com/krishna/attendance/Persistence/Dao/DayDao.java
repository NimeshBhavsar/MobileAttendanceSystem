package com.krishna.attendance.Persistence.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.krishna.attendance.Core.Enums.AttendanceStatusEnum;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Persistence.Entities.DayEntity;
import com.krishna.attendance.Persistence.Entities.StudentEntity;

import java.util.List;

@Dao
public interface DayDao extends BaseDao<DayEntity>{


    @Query("select * from days where id = :id ")
    DayEntity find(long id);

    @Query("select " +
            "d.id as id, " +
            "d.date as date, " +
            "d.subject_id as subjectId, " +
            "sub.course as subject, " +
            "sub.name as `group`, " +
            "d.verified as verified, " +
            "0 as presentStudents, "+
            "0 as totalStudents, " +
            "0 as presentPercent " +
            "from days d " +
            "inner join subjects sub on d.subject_id = sub.id " +
            "where d.id = :id ")
    DayModel getDay(long id);

    @Query("select " +
            "d.id as id, " +
            "d.date as date, " +
            "d.subject_id as subjectId, " +
            "sub.course as subject, " +
            "sub.name as `group`, " +
            "d.verified as verified, " +
            "(select count(*) from attendances att where att.day_id = d.id and att.status = 'Present' ) as presentStudents, "+
            "(select count(*) from students std inner join subject_students ss on std.id = ss.student_id where ss.subject_id = d.subject_id) as totalStudents, " +
            "100.0 * (select count(*) from attendances att where att.day_id = d.id and att.status = 'Present' )" +
            "       / (select count(*) from students std inner join subject_students ss on std.id = ss.student_id where ss.subject_id = sub.id ) as presentPercent " +
            "from days d " +
            "inner join subjects sub on d.subject_id = sub.id " +
            "where d.id = :id ")
    LiveData<DayModel> getDayLive(long id);


    @Query("select " +
            "d.id as id, " +
            "d.date as date, " +
            "d.subject_id as subjectId, " +
            "sub.course as subject, " +
            "sub.name as `group`, " +
            "d.verified as verified, " +
            "(select count(*) from attendances att where att.day_id = d.id and att.status = 'Present' ) as presentStudents, "+
            "(select count(*) from students std inner join subject_students ss on std.id = ss.student_id where ss.subject_id = d.subject_id) as totalStudents, " +
            "100.0 * (select count(*) from attendances att where att.day_id = d.id and att.status = 'Present' )" +
            "       / (select count(*) from students std inner join subject_students ss on std.id = ss.student_id where ss.subject_id = sub.id ) as presentPercent " +
            "from days d " +
            "inner join subjects sub on d.subject_id = sub.id " +
            "where d.subject_id = :subjectId " +
            "order by d.date desc ")
    LiveData<List<DayModel>> getDayList(long subjectId);

}
