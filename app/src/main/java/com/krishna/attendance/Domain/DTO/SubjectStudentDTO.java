package com.krishna.attendance.Domain.DTO;

import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Persistence.Entities.SubjectStudentEntity;

import java.util.List;

public class SubjectStudentDTO {

    public static SubjectStudentEntity toEntity(StudentModel model) {
        return new SubjectStudentEntity(0, model.subjectId, model.id);
    }



}
