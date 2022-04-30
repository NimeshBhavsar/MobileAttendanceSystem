package com.krishna.attendance.Domain.DbService;

import androidx.lifecycle.LiveData;

import com.krishna.attendance.Application.GlobalApplication;
import com.krishna.attendance.Core.Models.ResponseModel;
import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Domain.DTO.SubjectDTO;
import com.krishna.attendance.Persistence.DbContext.AppDatabase;
import com.krishna.attendance.Persistence.DbContext.DbHelper;
import com.krishna.attendance.Persistence.Entities.SubjectEntity;

import java.util.List;

public class SubjectDbService {
    private AppDatabase mAppDatabase;

    public SubjectDbService() {
        //AppDatabase appDatabase
        mAppDatabase = DbHelper.getAppDatabase(GlobalApplication.getAppContext()); // appDatabase;
    }

    public ResponseModel deleteSubject(long id) {
        SubjectEntity entity = mAppDatabase.mSubjectDao().find(id);
        if (entity != null) {
            mAppDatabase.mSubjectDao().delete(entity);
            return ResponseModel.getDeleteSuccess();
        }
        return ResponseModel.getFail("Data not found!");
    }

    public ResponseModel endSession(long subjectId) {
        SubjectEntity entity = mAppDatabase.mSubjectDao().find(subjectId);
        if (entity != null) {
            entity.ended = true;
            mAppDatabase.mSubjectDao().update(entity);
            return ResponseModel.getSuccess("Subject session ended successfully!");
        }
        return ResponseModel.getFail("Data not found!");
    }

    public SubjectModel getSubject(long subjectId) {
        return mAppDatabase.mSubjectDao().getSubject(subjectId);
    }

    public LiveData<List<SubjectModel>> getSubjectList() {
        return mAppDatabase.mSubjectDao().getSubjectList();
    }

    public LiveData<SubjectModel> getSubjectLive(long subjectId) {
        return mAppDatabase.mSubjectDao().getSubjectLive(subjectId);

    }

    public ResponseModel saveSubject(SubjectModel model) {
        if (model.id <= 0) {
            // add new
            model.id = mAppDatabase.mSubjectDao().insert(SubjectDTO.toEntity(model));
            return ResponseModel.getSaveSuccess(model);
            //new ResponseModel<>(ResponseStatusEnum.Success, "", model);
        } else {
            // update
            SubjectEntity entity = mAppDatabase.mSubjectDao().find(model.id);
            entity.course = model.course;
            entity.name = model.name;
            long count = mAppDatabase.mSubjectDao().update(entity);
            // TODO: check for "count > 0"
            return ResponseModel.getSaveSuccess(model);
            //new ResponseModel<>(ResponseStatusEnum.Success, "", model);
        }
    }
}
