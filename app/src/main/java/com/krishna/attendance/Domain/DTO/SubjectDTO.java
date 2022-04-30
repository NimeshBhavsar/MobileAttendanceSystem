package com.krishna.attendance.Domain.DTO;

import com.krishna.attendance.Core.Models.SubjectModel;
import com.krishna.attendance.Persistence.Entities.SubjectEntity;

public class SubjectDTO {

    public static SubjectEntity toEntity(SubjectModel model) {
        return new SubjectEntity(model.id, model.name, model.course, model.ended);
    }

    public static SubjectModel toModel(SubjectEntity entity) {
        return new SubjectModel(entity.id, entity.name, entity.course, entity.ended);
    }

    // Won't be used for now; Reason: sometimes some fields need not be updated so can't assign all
    // the fields
    /*public static SubjectEntity toEntity(SubjectModel model, SubjectEntity entity){
        if(entity == null){
            entity = new SubjectEntity();
        }
        entity.id = model.id;
        entity.name = model.name;
        entity.course = model.course;
        entity.ended = model.ended;
        return entity;
    }*/

}
