package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CourseNote extends AppCompatActivity  implements
        ShareActionProvider.OnShareTargetSelectedListener {

    private Course currentCourse;
    private TextView note;
    private String content;
    private ShareActionProvider mShareActionProvider;
    private Intent shareIntent = new Intent(Intent.ACTION_SEND);
//    https://github.com/commonsguy/cw-omnibus/blob/master/AppCompat/Share/app/src/main/java/com/commonsware/android/sap/MainActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_note);
        setTitle("Course Notes");

        note = (TextView) findViewById(R.id.note_text);
        content = note.getText().toString();

//        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
//        mShareActionProvider.setShareIntent(shareIntent);
//        currentCourse = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentCourse = (Course) b.getSerializable("CURRENT_COURSE");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_note, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setOnShareTargetSelectedListener(this);

        return(super.onCreateOptionsMenu(menu));
//        return true;
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
                b.putSerializable("PARENT_COURSE", currentCourse);
                i.putExtras(b);
                startActivityForResult(i, 0);

                return true;

            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }

    }
}
