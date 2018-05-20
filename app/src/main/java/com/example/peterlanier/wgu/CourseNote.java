package com.example.peterlanier.wgu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CourseNote extends AppCompatActivity  implements
        ShareActionProvider.OnShareTargetSelectedListener {

    private Course currentCourse;
    private TextView note;
    private String content;
    private String key;
    private ShareActionProvider mShareActionProvider;
    private Intent shareIntent = new Intent(Intent.ACTION_SEND);
    public static final String PREFS_NOTES = "CourseNotes";
    private Button editButton;
//    https://github.com/commonsguy/cw-omnibus/blob/master/AppCompat/Share/app/src/main/java/com/commonsware/android/sap/MainActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_note);

        note = (TextView) findViewById(R.id.note_text);
        content = note.getText().toString();
        editButton = (Button) findViewById(R.id.btn_edit_note) ;

        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);

        currentCourse = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentCourse = (Course) b.getSerializable("CURRENT_COURSE");
            setTitle("Notes for " + currentCourse.title);

            key = Integer.toString(currentCourse.getId());

            if(currentCourse.note == null || currentCourse.note.isEmpty()) {
                note.setText("There are no notes for this course yet. Would you like to start a note?");
                editButton.setText("Start Note");
            } else {
                note.setText(currentCourse.note);
            }

        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NOTES, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            int idName = prefs.getInt("idName", 0); //0 is the default value.
        }

//        // MY_PREFS_NAME - a static String variable like:
//        //public static final String MY_PREFS_NAME = "MyPrefsFile";
//        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putString("name", "Elena");
//        editor.putInt("idName", 12);
//        editor.apply();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_note, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(shareIntent);
        mShareActionProvider.setOnShareTargetSelectedListener(this);

        return(super.onCreateOptionsMenu(menu));
//        return true;
    }

    public void editNote(View v){
        Intent i = new Intent(CourseNote.this, CourseNoteEdit.class);
        Bundle b = new Bundle();
        b.putSerializable("CURRENT_COURSE", currentCourse);
        i.putExtras(b);
        startActivity(i);



    }


    @Override
    public boolean onShareTargetSelected(ShareActionProvider source,
                                         Intent intent) {
        Toast.makeText(this, intent.getComponent().toString(),
                Toast.LENGTH_LONG).show();

        return(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.navigation_return_course:

                Intent i = new Intent(CourseNote.this, DetailCourse.class);
                Bundle b = new Bundle();
                b.putSerializable("CURRENT_COURSE", currentCourse);
                i.putExtras(b);
                startActivity(i);

                return true;

            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }

    }
}
