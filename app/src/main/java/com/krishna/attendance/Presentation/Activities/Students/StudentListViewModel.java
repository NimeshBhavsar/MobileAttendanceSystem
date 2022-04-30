package com.krishna.attendance.Presentation.Activities.Students;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.StudentDbService;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

import java.util.ArrayList;
import java.util.List;

public class StudentListViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private long mSubjectId;
   // private MutableLiveData<List<StudentModel>> mLiveData;
    private LiveData<List<StudentModel>> mLiveData;

    private StudentDbService mStudentDbService;
    private SubjectDbService mSubjectDbService;

    public StudentListViewModel() {
        // StudentDbService studentDbService
        mStudentDbService = new StudentDbService();
        mSubjectDbService = new SubjectDbService();
    }

    public ResponseModel deleteStudent(long subjectId, long studentId) {
        return mStudentDbService.deleteStudent(subjectId, studentId);
    }

    public LiveData<List<StudentModel>> getStudentListLive(long subjectId){
        /*if(mLiveData == null){
            // assign empty list to avoid errors in observers
            mLiveData = new MutableLiveData<>((List<StudentModel>)new ArrayList<StudentModel>());
        }*/
        if(mSubjectId != subjectId || mLiveData == null){
            mSubjectId = subjectId;
            //mLiveData.setValue(mStudentDbService.getStudentList(subjectId));
            mLiveData = mStudentDbService.getStudentListLive(subjectId);
        }
        return mLiveData;
    }

    public LiveData<SubjectModel> getSubject(long subjectId) {
        return mSubjectDbService.getSubjectLive(subjectId);
    }
}
