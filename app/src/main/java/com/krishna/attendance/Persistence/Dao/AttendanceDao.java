package com.krishna.attendance.Persistence.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Persistence.Entities.AttendanceEntity;

import java.util.List;


@Dao
public interface AttendanceDao extends BaseDao<AttendanceEntity> {

    @Query("select * from attendances where day_id = :dayId and student_id = :studentId")
    AttendanceEntity getAttendance(long dayId, long studentId);



    @Query("select " +
            "att.id as id, " +
            "std.id as studentId, " +
            "std.crn as studentCrn, " +
            "std.name as studentName, " +
            "att.day_id as dayId, " +
            // since dayId is given in param it's safe to assume that the caller already has day object
            // so no need to assign date in the attendance list
            "'' as date, " +
            "att.status as status " +
            "from subjects sub " +
            "inner join subject_students substd on sub.id = substd.subject_id " +
            "inner join students std on substd.student_id = std.id " +
            "left join days d on sub.id = d.subject_id " +
            "left join attendances att on std.id = att.student_id and att.day_id = d.id " +
            // "left join days d on att.day_id = d.id " +
            "where sub.id = :subjectId and d.id = :dayId " +
            //"where sub.id = :subjectId and (:dayId = 0 or att.day_id = :dayId) " +
            "order by std.crn asc ")
    LiveData<List<AttendanceModel>> getAttendanceListLive(long subjectId, long dayId);

    /**
     * gets student list even if the day is not given. it's required logic.
     * @param subjectId
     * @param dayId
     * @return
     */
    @Query("select " +
            "att.id as id, " +
            "std.id as studentId, " +
            "std.crn as studentCrn, " +
            "std.name as studentName, " +
            // since dayId is given in param it's safe to assume that the caller already has day object
            // so no need to assign date in the attendance list
            "'' as date, " +
            "att.day_id as dayId, " +
            "att.status as status " +
            "from subjects sub " +
            "inner join subject_students substd on sub.id = substd.subject_id " +
            "inner join students std on substd.student_id = std.id " +
            "left join attendances att on std.id = att.student_id and att.day_id = :dayId " +
            //"left join days d on att.day_id = d.id " +
            "where sub.id = :subjectId " + //and (:dayId = 0 or att.day_id = :dayId)
            "order by std.crn asc ")
    List<AttendanceModel> getAttendanceList(long subjectId, long dayId);

    @Query("select " +
            "att.id as id, " +
            "std.id as studentId, " +
            "std.crn as studentCrn, " +
            "std.name as studentName, " +
            "d.date as date, " +
            "att.day_id as dayId, " +
            "att.status as status " +
            "from subjects sub " +
            "inner join subject_students substd on sub.id = substd.subject_id " +
            "inner join students std on substd.student_id = std.id " +
            "inner join days d on sub.id = d.subject_id "+//att.day_id = d.id " +
            "left join attendances att on std.id = att.student_id and d.id = att.day_id " +
            "where sub.id = :subjectId and std.id = :studentId " +
            "order by d.date asc ")
    LiveData<List<AttendanceModel>> getStudentAttendanceList(long subjectId, long studentId);

    @Query("select * from attendances att where att.day_id = :dayId")
    List<AttendanceEntity> getAttendanceList(long dayId);

    @Query("select att.* " +
            "from attendances att " +
            "inner join days d on att.day_id = d.id " +
            "where d.subject_id = :subjectId and att.student_id = :studentId")
    List<AttendanceEntity> getAttendanceListOfStudent(long subjectId, long studentId);

    @Query("delete from attendances where day_id = :dayId")
    void deleteAllByDayId(long dayId);

    //

    /*@Query("select " +
            "att.id as id, " +
            "att.student_id as studentId, " +
            "std.crn as studentCrn, " +
            "std.name as studentName, " +
            "att.day_id as dayId, " +
           // "d.date as date, " +
            "att.status as status " +
            "from attendances att " +
            "inner join students std on att.student_id = std.id " +
            "inner join days d on att.day_id = d.id " +
            "where att.day_id = :dayId " +
            "order by std.crn asc ")
    LiveData<List<AttendanceModel>> getAttendanceList(long dayId);*/

}
