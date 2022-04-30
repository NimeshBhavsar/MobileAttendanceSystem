package com.krishna.attendance.Domain.DbService;

import androidx.lifecycle.LiveData;

import com.krishna.attendance.Application.GlobalApplication;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Domain.DTO.DayDTO;
import com.krishna.attendance.Persistence.DbContext.AppDatabase;
import com.krishna.attendance.Persistence.DbContext.DbHelper;
import com.krishna.attendance.Persistence.Entities.DayEntity;

import java.util.List;

public class DayDbService {

    private AppDatabase mAppDatabase;

    public DayDbService() {
        //AppDatabase appDatabase
        mAppDatabase = DbHelper.getAppDatabase(GlobalApplication.getAppContext());
    }

    public ResponseModel deleteDay(long dayId) {
        DayEntity entity = mAppDatabase.mDayDao().find(dayId);
        if(entity != null){
            mAppDatabase.mDayDao().delete(entity);
            // delete all attendances
            mAppDatabase.mAttendanceDao().deleteAllByDayId(dayId);
            return ResponseModel.getDeleteSuccess(DayDTO.toModel(entity));
        } else {
            return ResponseModel.getFail("Data not found!");
        }
    }

    public DayModel getDay(long dayId) {
        return mAppDatabase.mDayDao().getDay(dayId);
    }


    public LiveData<List<DayModel>> getDayList(long subjectId) {
        return mAppDatabase.mDayDao().getDayList(subjectId);
    }

    public LiveData<DayModel> getDayLive(long dayId) {
        return mAppDatabase.mDayDao().getDayLive(dayId);
    }

    public ResponseModel verify(long dayId) {
        DayEntity entity = mAppDatabase.mDayDao().find(dayId);
        if(entity!=null){
            entity.verified = true;
            mAppDatabase.mDayDao().update(entity);
            return ResponseModel.getSaveSuccess(DayDTO.toModel(entity));
        } else {
            return ResponseModel.getFail("Data not found!");
        }

    }
}
