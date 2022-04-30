package com.krishna.attendance.Presentation.Activities.Attendances.Create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DbService.AttendanceDbService;
import com.krishna.attendance.Domain.DbService.DayDbService;
import com.krishna.attendance.Domain.DbService.SubjectDbService;

import java.util.List;

public class AttendanceCreateViewModel extends ViewModel {

    private AttendanceDbService mAttendanceDbService;
    private DayDbService mDayDbService;
    private SubjectDbService mSubjectDbService;


    private long mDayId;
    private long mSubjectId;

    public AttendanceCreateViewModel() {
        //SubjectDbService subjectDbService
        mSubjectDbService = new SubjectDbService();//subjectDbService;
        mDayDbService = new DayDbService();
        mAttendanceDbService = new AttendanceDbService();
    }

    public List<AttendanceModel> getAttendanceList(long subjectId, long dayId) {
        return mAttendanceDbService.getAttendanceList(subjectId, dayId);
    }

    public DayModel getDay(long dayId){
        mDayId = dayId;
        return mDayDbService.getDay(dayId);
    }

    public SubjectModel getSubject(long subjectId) {
        return mSubjectDbService.getSubject(subjectId);
    }

    public ResponseModel saveAttendance(DayModel model) {
        return mAttendanceDbService.saveAttendance(model);
    }
}
