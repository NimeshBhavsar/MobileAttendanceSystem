package com.krishna.attendance.Persistence.DbContext;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.krishna.attendance.Persistence.Dao.AttendanceDao;
import com.krishna.attendance.Persistence.Dao.DayDao;
import com.krishna.attendance.Persistence.Dao.StudentDao;
import com.krishna.attendance.Persistence.Dao.SubjectDao;
import com.krishna.attendance.Persistence.Dao.SubjectStudentDao;
import com.krishna.attendance.Persistence.Entities.AttendanceEntity;
import com.krishna.attendance.Persistence.Entities.DayEntity;
import com.krishna.attendance.Persistence.Entities.StudentEntity;
import com.krishna.attendance.Persistence.Entities.SubjectEntity;
import com.krishna.attendance.Persistence.Entities.SubjectStudentEntity;

@Database(entities = {
        AttendanceEntity.class,
        DayEntity.class,
        SubjectEntity.class,
        SubjectStudentEntity.class,
        StudentEntity.class
},
        version = AppDatabase.VERSION,
        exportSchema = false
)
@TypeConverters({Converters.class}) //define the type-converter classes here
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "attendance-db";
    static final int VERSION = 1;

    public abstract AttendanceDao mAttendanceDao();

    public abstract DayDao mDayDao();

    public abstract SubjectDao mSubjectDao();

    public abstract SubjectStudentDao mSubjectStudentDao();

    public abstract StudentDao mStudentDao();

}
