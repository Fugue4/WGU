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
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTerm(Term term);

    @Query("select * from term")
    public List<Term> getAllTerm();

    @Query("select * from term where id = :termId")
    public List<Term> getTerm(long termId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTerm(Term term);

    @Query("delete from term")
    void removeAllTerms();


}
