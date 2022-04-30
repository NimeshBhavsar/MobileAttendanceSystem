package com.krishna.attendance.Domain.DbService;

import androidx.lifecycle.LiveData;

import com.krishna.attendance.Application.GlobalApplication;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Domain.DTO.StudentDTO;
import com.krishna.attendance.Domain.DTO.SubjectStudentDTO;
import com.krishna.attendance.Persistence.DbContext.AppDatabase;
import com.krishna.attendance.Persistence.DbContext.DbHelper;
import com.krishna.attendance.Persistence.Entities.AttendanceEntity;
import com.krishna.attendance.Persistence.Entities.StudentEntity;
import com.krishna.attendance.Persistence.Entities.SubjectStudentEntity;

import java.util.List;

public class StudentDbService {
    private AppDatabase mAppDatabase;

    public StudentDbService() {
        //AppDatabase appDatabase
        mAppDatabase = DbHelper.getAppDatabase(GlobalApplication.getAppContext());
    }

    public ResponseModel deleteStudent(long subjectId, long studentId) {
        StudentEntity entity = mAppDatabase.mStudentDao().find(studentId);
        if (entity != null) {
            SubjectStudentEntity subjectStudentEntity = mAppDatabase.mSubjectStudentDao().get(subjectId, studentId);
            if(subjectStudentEntity!=null){
                mAppDatabase.mSubjectStudentDao().delete(subjectStudentEntity);
            }
            mAppDatabase.mStudentDao().delete(entity);
            List<AttendanceEntity> attEntities = mAppDatabase.mAttendanceDao().getAttendanceListOfStudent(subjectId, studentId);
            mAppDatabase.mAttendanceDao().delete(attEntities);
            return ResponseModel.getDeleteSuccess(StudentDTO.toModel(entity, 0));
        }
        return ResponseModel.getFail("Data not found!");
    }

    public StudentModel getStudent(long studentId) {
        return mAppDatabase.mStudentDao().getStudent(studentId);
    }

    public LiveData<StudentModel> getStudent(long subjectId, long studentId) {
        return mAppDatabase.mStudentDao().getStudent(subjectId, studentId);
    }

    public List<StudentModel> getStudentList(long subjectId) {
        return mAppDatabase.mStudentDao().getStudentList(subjectId);
    }

    public LiveData<List<StudentModel>> getStudentListLive(long subjectId) {
        return mAppDatabase.mStudentDao().getStudentListLive(subjectId);
    }

    public ResponseModel saveStudent(StudentModel model) {
        if (model.id <= 0) {
            // add new
            boolean alreadyExists = mAppDatabase.mStudentDao().exists( model.crn, model.id);
            if(alreadyExists)
                return ResponseModel.getFail("Duplicate CRN already exists!");
            model.id = mAppDatabase.mStudentDao().insert(StudentDTO.toEntity(model));
            // add to the subject-student relation
            mAppDatabase.mSubjectStudentDao().insert(SubjectStudentDTO.toEntity(model));
            return ResponseModel.getSaveSuccess( model);
            //new ResponseModel<>(ResponseStatusEnum.Success, "", model);
        } else {
            // update
            boolean alreadyExists = mAppDatabase.mStudentDao().exists( model.crn, model.id);
            if(alreadyExists)
                return ResponseModel.getFail("Duplicate CRN already exists!");

            StudentEntity entity = mAppDatabase.mStudentDao().find(model.id);
            entity.crn = model.crn;
            entity.name = model.name;
            entity.image = model.image;
            long count = mAppDatabase.mStudentDao().update(entity);
            // TODO: check for "count > 0"
            return ResponseModel.getSaveSuccess(model);
            // return new ResponseModel<>(ResponseStatusEnum.Success, "", model);
        }
    }
}
