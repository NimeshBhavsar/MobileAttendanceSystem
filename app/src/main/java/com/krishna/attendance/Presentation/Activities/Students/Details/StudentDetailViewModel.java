package com.krishna.attendance.Presentation.Activities.Students.Details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.AttendanceDbService;
import com.krishna.attendance.Domain.DbService.StudentDbService;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

import java.util.List;

// TODO:: have a way to first initialize viewModel with required ids(i.e. subjectId & studentId)
// TODO:: Then only call the get..( ) function.
// TODO:: Reason to do above: This will enable storing whole data in the viewModel and removes every DB call when get..( ) is assessed
public class StudentDetailViewModel extends ViewModel {

    private StudentDbService mStudentDbService;
    private AttendanceDbService mAttendanceDbService;
    private SubjectDbService mSubjectDbService;

    private long mSubjectId;
    private long mStudentId;

    private LiveData<StudentModel> mLiveData;

    public StudentDetailViewModel() {
        //StudentDbService studentDbService
        mStudentDbService = new StudentDbService();//studentDbService;
        mAttendanceDbService = new AttendanceDbService();
        mSubjectDbService = new SubjectDbService();
    }

    public LiveData<SubjectModel> getSubject(long subjectId){
        return mSubjectDbService.getSubjectLive(subjectId);
    }

    public LiveData<StudentModel> getStudent(long subjectId, long studentId) {
        if(mSubjectId!=subjectId || mStudentId != studentId || mLiveData == null){
            mSubjectId = subjectId;
            mStudentId = studentId;
            mLiveData = mStudentDbService.getStudent(subjectId, studentId);
        }
        return mLiveData;
    }

    public LiveData<List<AttendanceModel>> getAttendanceList(long subjectId, long studentId){
        return mAttendanceDbService.getStudentAttendanceListLive(subjectId, studentId);
    }
}
