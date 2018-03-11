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
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAssessment(Assessment assessment);

    @Query("SELECT * FROM assessment WHERE courseId=:courseId")
    List<Assessment> findAssessmentsForCourse(int courseId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAssessment(Assessment assessment);

    @Query("delete from assessment where id = :id")
    void delete(long id);
}
