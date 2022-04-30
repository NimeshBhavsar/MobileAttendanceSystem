package com.krishna.attendance.Presentation.Activities.Subjects.Create;

import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

public class SubjectCreateViewModel extends ViewModel {

    private SubjectDbService mSubjectDbService;

    public SubjectCreateViewModel() {
        //SubjectDbService subjectDbService
        mSubjectDbService = new SubjectDbService();//subjectDbService;
    }

    public SubjectModel getSubject(long subjectId){
        return mSubjectDbService.getSubject(subjectId);
    }

    public ResponseModel saveSubject(SubjectModel model) {
        return mSubjectDbService.saveSubject(model);
    }
}
