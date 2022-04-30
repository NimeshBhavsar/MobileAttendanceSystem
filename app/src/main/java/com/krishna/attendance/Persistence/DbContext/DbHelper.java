package com.krishna.attendance.Persistence.DbContext;

import android.content.Context;

import androidx.room.Room;

//import com.krishna.attendance.Application.Dependency.Annotations.ApplicationContext;

public class DbHelper {
    private static final Object DB_LOCK = new Object();
    private static AppDatabase appDatabase;

    public synchronized static AppDatabase getAppDatabase(Context context) { //@ApplicationContext
//        int oldVersion = 1;
//        int newVersion = 2;
//        Migration migration = new Migration_1_1(oldVersion,newVersion);

        if (appDatabase == null) {
            synchronized (DB_LOCK) {
                appDatabase = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME)
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
//                        .addMigrations(MigrationFirstVersion.MIGRATION_42_43)

                        // remove it in release version; release version should use migration.
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return appDatabase;
    }


}
