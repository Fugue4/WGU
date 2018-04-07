package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailCourse extends AppCompatActivity {

    private AppDatabase database;
    private Assessment assessment;
    private TextView title;
    private TextView start;
    private TextView end;
    private TextView mentor;
    private TextView mentorPhone;
    private TextView mentorEmail;
    private TextView status;
    private ListView listView;
    private Course currentCourse;

    public DetailCourse() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        setTitle("Course Details");

        //Database
        database = AppDatabase.getDatabase(getApplicationContext());

        title = (TextView) findViewById(R.id.course_detail_title);
        start = (TextView) findViewById(R.id.course_detail_start);
        end = (TextView) findViewById(R.id.course_detail_end);
        mentor = (TextView) findViewById(R.id.course_detail_mentor);
        mentorPhone = (TextView) findViewById(R.id.course_detail_mentor_phone);
        mentorEmail = (TextView) findViewById(R.id.course_detail_mentor_email);
        status = (TextView) findViewById(R.id.course_detail_status);

        currentCourse = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentCourse = (Course) b.getSerializable("CURRENT_COURSE");

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            title.setText(currentCourse.title);
            start.setText(currentCourse.start);
            end.setText(currentCourse.end);
            mentor.setText(currentCourse.mentorName);
            mentorPhone.setText(currentCourse.mentorPhone);
            mentorEmail.setText(currentCourse.mentorEmail);
            status.setText(currentCourse.status);

        }

        listView = (ListView) findViewById(R.id.assessments_list_view);

        System.out.println("Courses Added");

        final ArrayList<Assessment> assessmentList = (ArrayList<Assessment>) database.assessmentDao().findAssessmentsForCourse(currentCourse.getId());


        final AssessmentAdapter adapter = new AssessmentAdapter(this, assessmentList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Assessment currentAssessment = (Assessment) parent.getItemAtPosition(position);

                Intent i = new Intent(DetailCourse.this, DetailAssessment.class);
                Bundle b = new Bundle();
                b.putSerializable("CURRENT_ASSESSMENT", currentAssessment);
                i.putExtras(b);
                startActivityForResult(i, 0);

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.navigation_edit_course:

                Intent i = new Intent(DetailCourse.this, EditCourse.class);
                Bundle b = new Bundle();
                b.putSerializable("CURRENT_COURSE", currentCourse);
                i.putExtras(b);
                startActivityForResult(i, 0);

                return true;
            case R.id.navigation_new_assessment:
                System.out.println("new_assessment");
                return true;
            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }


    }

}
