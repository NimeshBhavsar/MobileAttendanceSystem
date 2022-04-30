package com.krishna.attendance.Domain.DTO;

import com.krishna.attendance.Core.Models.DayModel;
import com.krishna.attendance.Persistence.Entities.DayEntity;

public class DayDTO {

    public static DayEntity toEntity(DayModel model){
        return new DayEntity(model.id, model.date, model.subjectId, model.verified);
    }

    public static DayModel toModel(DayEntity entity){
        return new DayModel();
    }

}
