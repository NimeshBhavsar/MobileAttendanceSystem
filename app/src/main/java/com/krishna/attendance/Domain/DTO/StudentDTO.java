package com.krishna.attendance.Domain.DTO;

import com.krishna.attendance.Core.Models.StudentModel;
import com.krishna.attendance.Persistence.Entities.StudentEntity;

public class StudentDTO {

    public static StudentEntity toEntity(StudentModel model){
        return new StudentEntity(model.id, model.name, model.crn, model.image);
    }

    public static StudentModel toModel(StudentEntity entity, int subjectId){
        return new StudentModel(entity.id, entity.name, entity.crn, entity.image, subjectId);
    }

}
