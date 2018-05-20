package com.example.peterlanier.wgu;

import android.content.Intent;
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
    private String message;
    private String subject;
    private ShareActionProvider mShareActionProvider;
    private Intent shareIntent = new Intent(Intent.ACTION_SEND);
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_note);

        note = (TextView) findViewById(R.id.note_text);
        editButton = (Button) findViewById(R.id.btn_edit_note) ;
        message = "No notes for course";
        subject = "";



        currentCourse = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentCourse = (Course) b.getSerializable("CURRENT_COURSE");
            setTitle("Notes for " + currentCourse.title);

            if(currentCourse.note == null || currentCourse.note.isEmpty()) {
                note.setText("There are no notes for this course yet. Would you like to start a note?");
                editButton.setText("Start Note");
            } else {
                note.setText(currentCourse.note);
                message = currentCourse.note;
                subject = "WGU Notes for " + currentCourse.title;
            }

        }

        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

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
