package com.example.peterlanier.wgu;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by peterlanier on 3/10/18.
 */

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCourse(Course course);

    @Query("SELECT * FROM course WHERE termId=:termId")
    List<Course> findCoursesForTerm(int termId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse(Course course);

    @Query("delete from course where id = :id")
    void delete(long id);
}
