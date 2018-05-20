package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CourseNoteEdit extends AppCompatActivity {

    private Course currentCourse;
    private String key;
    private EditText note;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_note_edit);
        database = AppDatabase.getDatabase(getApplicationContext());
        note = (EditText) findViewById(R.id.editNote);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentCourse = (Course) b.getSerializable("CURRENT_COURSE");

            if(currentCourse.note != null) {
                note.setText(currentCourse.note);
            }

        }

    }

    public void cancelNote(View v){
        Intent i = new Intent(CourseNoteEdit.this, CourseNote.class);
        Bundle bb = new Bundle();
        bb.putSerializable("CURRENT_COURSE", currentCourse);
        i.putExtras(bb);
        startActivity(i);
    }

    public void saveNote(View v){
        currentCourse.note = note.getText().toString();
        database.courseDao().updateCourse(currentCourse);

        Intent i = new Intent(CourseNoteEdit.this, CourseNote.class);
        Bundle bb = new Bundle();
        bb.putSerializable("CURRENT_COURSE", currentCourse);
        i.putExtras(bb);
        startActivity(i);

    }

}
