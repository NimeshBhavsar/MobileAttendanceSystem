package com.krishna.attendance.Domain.DTO;

import com.krishna.attendance.Core.Models.AttendanceModel;
import com.krishna.attendance.Persistence.Entities.AttendanceEntity;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDTO {

    public static AttendanceEntity toEntity(AttendanceModel model){
        return new AttendanceEntity(model.id, model.studentId, model.dayId, model.status);
    }

    public static AttendanceModel toModel(AttendanceEntity entity){
        return new AttendanceModel(entity.id, entity.studentId, "", "", entity.dayId, entity.status);
    }

    public static List<AttendanceEntity> toEntities(long dayId, List<AttendanceModel> models){
        List<AttendanceEntity> entities = new ArrayList<>();
        for(AttendanceModel model: models){
            AttendanceEntity entity = AttendanceDTO.toEntity(model);
            entity.dayId = dayId;
            entities.add(entity);
        }
        return entities;
    }

}
