package com.krishna.attendance.Presentation.Activities.Subjects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

import java.util.List;

public class SubjectListViewModel extends ViewModel {
    private LiveData<List<SubjectModel>> mLiveData;

    private SubjectDbService mSubjectDbService;

    public SubjectListViewModel() {
        //SubjectDbService sessionDbService
        mSubjectDbService = new SubjectDbService();//sessionDbService;
    }

    public ResponseModel deleteSubject(long id) {
        return mSubjectDbService.deleteSubject(id);
    }

    public LiveData<List<SubjectModel>> getSubjectList(){
        if(mLiveData == null){
            mLiveData = mSubjectDbService.getSubjectList();
        }
        return mLiveData;
    }

}
