package com.krishna.attendance.Domain.DbService;


import androidx.lifecycle.LiveData;

import com.krishna.attendance.Application.GlobalApplication;
import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Domain.DTO.AttendanceDTO;
import com.krishna.attendance.Domain.DTO.DayDTO;
import com.krishna.attendance.Persistence.DbContext.AppDatabase;
import com.krishna.attendance.Persistence.DbContext.DbHelper;
import com.krishna.attendance.Persistence.Entities.AttendanceEntity;
import com.krishna.attendance.Persistence.Entities.DayEntity;

import java.util.List;

public class AttendanceDbService {

    private AppDatabase mAppDatabase;

    // todo: inject appDatabase
    public AttendanceDbService() {
        //AppDatabase appDatabase
        mAppDatabase = DbHelper.getAppDatabase(GlobalApplication.getAppContext());
    }


    public LiveData<List<AttendanceModel>> getAttendanceListLive(long subjectId, long dayId) {
        return mAppDatabase.mAttendanceDao().getAttendanceListLive(subjectId, dayId);
    }

    public LiveData<List<AttendanceModel>> getStudentAttendanceListLive(long subjectId, long studentId) {
        return mAppDatabase.mAttendanceDao().getStudentAttendanceList(subjectId, studentId);
    }

    public List<AttendanceModel> getAttendanceList(long subjectId, long dayId) {
        return mAppDatabase.mAttendanceDao().getAttendanceList(subjectId, dayId);
    }

    public ResponseModel saveAttendance(final DayModel model) {
        mAppDatabase.runInTransaction(new Runnable() {
            @Override
            public void run() {

                // 1-- save day
                DayEntity dayEntity = mAppDatabase.mDayDao().find(model.id);
                if(dayEntity == null){
                    // add
                    dayEntity = DayDTO.toEntity(model);
                    long id = mAppDatabase.mDayDao().insert(dayEntity);
                    dayEntity.id = id;
                } else {
                    // update
                    dayEntity.date = model.date;
                    mAppDatabase.mDayDao().update(dayEntity);
                }

                // 2-- save attendances
                if(model.attendanceList!=null){
                    for(AttendanceModel attendanceModel : model.attendanceList){
                        AttendanceEntity attendanceEntity = mAppDatabase.mAttendanceDao().getAttendance(dayEntity.id, attendanceModel.studentId);
                        if(attendanceEntity == null){
                            // insert
                            attendanceEntity = AttendanceDTO.toEntity(attendanceModel);
                            attendanceEntity.dayId = dayEntity.id;
                            mAppDatabase.mAttendanceDao().insert(attendanceEntity);
                        } else {
                            // update
                            attendanceEntity.dayId = dayEntity.id;
                            attendanceEntity.status = attendanceModel.status;
                            mAppDatabase.mAttendanceDao().update(attendanceEntity);
                        }
                    }
                }
            }
        });
        // TODO:: shouldn't return 'success' from outside of runnable; consider returning response after actual save operation
        return ResponseModel.getSaveSuccess(model);
    }
}
