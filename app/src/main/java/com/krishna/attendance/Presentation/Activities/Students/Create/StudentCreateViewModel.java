package com.krishna.attendance.Presentation.Activities.Students.Create;

import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.StudentDbService;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

public class StudentCreateViewModel extends ViewModel {

    private StudentDbService mStudentDbService;
    private SubjectDbService mSubjectDbService;

    public StudentCreateViewModel() {
        //SubjectDbService subjectDbService
        mSubjectDbService = new SubjectDbService();//subjectDbService;
        mStudentDbService = new StudentDbService();
    }

    public SubjectModel getSubject(long subjectId){
        return mSubjectDbService.getSubject(subjectId);
    }

    public StudentModel getStudent(long studentId){
        return mStudentDbService.getStudent(studentId);
    }

    public ResponseModel saveStudent(StudentModel model) {
        return mStudentDbService.saveStudent(model);
    }
}
