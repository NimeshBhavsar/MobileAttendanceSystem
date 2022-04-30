package com.krishna.attendance.Persistence.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

public interface BaseDao<T> {
    @Delete
    int delete(T... entity);

    @Delete
    int delete(List<T> entity);

    @Insert
    List<Long> insert(T... entity);

    @Insert
    List<Long> insert(List<T> entity);

    @Insert
    Long insert(T entity);

    //here int represents how many rows updated
    @Update
    int update(T... entity);

}
