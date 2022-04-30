package com.krishna.attendance.Presentation.Activities.Attendances;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.AttendanceDbService;
import com.krishna.attendance.Domain.DbService.DayDbService;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

import java.util.List;

public class AttendanceListViewModel extends ViewModel {

    private long mDayId;
    private LiveData<DayModel> mDayLiveData;
    //private LiveData<List<AttendanceModel>> mAttendanceList;

    private AttendanceDbService mAttendanceDbService;
    private SubjectDbService mSubjectDbService;
    private DayDbService mDayDbService;

    public AttendanceListViewModel() {
        // StudentDbService studentDbService
        mAttendanceDbService = new AttendanceDbService();
        mSubjectDbService = new SubjectDbService();
        mDayDbService = new DayDbService();
    }

    public LiveData<DayModel> getDay(long dayId) {
        if (mDayId != dayId || mDayLiveData == null) {
            mDayId = dayId;
            mDayLiveData = mDayDbService.getDayLive(dayId);
            //mAttendanceList = mAttendanceDbService.getAttendanceListLive(subjectId, dayId);
        }
        return mDayLiveData; //
    }

    public LiveData<List<AttendanceModel>> getAttendanceList(long subjectId, long dayId) {
        return mAttendanceDbService.getAttendanceListLive(subjectId, dayId);
    }

    public LiveData<SubjectModel> getSubject(long subjectId){
        return mSubjectDbService.getSubjectLive(subjectId);
    }

    public ResponseModel verify(long dayId) {
        return mDayDbService.verify(dayId);
    }
}
