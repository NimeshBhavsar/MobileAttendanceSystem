package com.krishna.attendance.Presentation.Activities.Days;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.DayDbService;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

import java.util.List;

public class DayListViewModel extends ViewModel {

    private DayDbService mDayDbService;
    private SubjectDbService mSubjectDbService;

    public DayListViewModel() {
        //DayDbService dayDbService
        mDayDbService = new DayDbService();
        mSubjectDbService = new SubjectDbService();
    }

    public ResponseModel deleteDay(long id) {
        return mDayDbService.deleteDay(id);
    }

    public LiveData<List<DayModel>> getDayList(long subjectId){
        return mDayDbService.getDayList(subjectId);
    }

    public LiveData<SubjectModel> getSubject(long subjectId) {
        return mSubjectDbService.getSubjectLive(subjectId);

    }
}
