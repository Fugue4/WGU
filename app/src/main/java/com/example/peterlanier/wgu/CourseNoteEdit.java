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

            if(currentCourse.note != null || !currentCourse.note.isEmpty()) {
                note.setText(currentCourse.note);
            }

//            key = Integer.toString(currentCourse.getId());

        }



//        SharedPreferences prefs = getSharedPreferences(CourseNote.PREFS_NOTES, MODE_PRIVATE);
//        String restoredText = prefs.getString("text", null);
//        if (restoredText != null) {
//            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
//            int idName = prefs.getInt("idName", 0); //0 is the default value.
//        }

//        // MY_PREFS_NAME - a static String variable like:
//        //public static final String MY_PREFS_NAME = "MyPrefsFile";
//        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putString("name", "Elena");
//        editor.putInt("idName", 12);
//        editor.apply();

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
