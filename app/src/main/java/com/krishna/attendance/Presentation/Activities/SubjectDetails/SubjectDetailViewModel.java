package com.krishna.attendance.Presentation.Activities.SubjectDetails;

import androidx.lifecycle.LiveData;

import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

public class SubjectDetailViewModel {

    private SubjectDbService mSubjectDbService;
    private long mSubjectId;
    private LiveData<SubjectModel> mLiveData;

    public SubjectDetailViewModel() {
        mSubjectDbService = new SubjectDbService();
    }

    public ResponseModel endSession(long subjectId) {
        return mSubjectDbService.endSession(subjectId);
    }

    public LiveData<SubjectModel> getSubject(long subjectId){
        if(mSubjectId != subjectId || mLiveData == null){
            mSubjectId = subjectId;
            mLiveData = mSubjectDbService.getSubjectLive(subjectId);
        }
        return mLiveData;
    }
}
