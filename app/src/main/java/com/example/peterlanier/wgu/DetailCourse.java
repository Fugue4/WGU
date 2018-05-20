package com.example.peterlanier.wgu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class DetailCourse extends AppCompatActivity implements
    ShareActionProvider.OnShareTargetSelectedListener {

    private AppDatabase database;
    private TextView title;
    private TextView start;
    private TextView end;
    private TextView mentor;
    private TextView mentorPhone;
    private TextView mentorEmail;
    private TextView status;
    private ListView listView;
    private Button setStartBtn;
    private Button setEndBtn;
    private Course currentCourse;
    private Calendar cal;

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
        setStartBtn = (Button) findViewById(R.id.btn_course_start_alarm);
        setEndBtn = (Button) findViewById(R.id.btn_course_end_alarm);

        currentCourse = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentCourse = (Course) b.getSerializable("CURRENT_COURSE");

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

    public void setAlert(View view){

        int[] dateArray = new int[3];
        System.out.println(view);
        String [] dateParts;

        if(view == setStartBtn){
            dateParts = start.getText().toString().split(Pattern.quote("/"));
            Toast.makeText(this, "Alarm set for course start",
                    Toast.LENGTH_LONG).show();
        } else if (view == setEndBtn) {
            dateParts = end.getText().toString().split(Pattern.quote("/"));
            Toast.makeText(this, "Alarm set for course end",
                    Toast.LENGTH_LONG).show();
        } else {
            dateParts = null;
        }

        for (int x = 0; x < dateParts.length; x++) {
            dateArray[x] = Integer.parseInt(dateParts[x]);
            System.out.println(x + " is " + dateArray[x]);
        }

        cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH, dateArray[0]);
//        cal.set(Calendar.YEAR, dateArray[2]);
//        cal.set(Calendar.DAY_OF_MONTH, dateArray[1]);
//        cal.set(Calendar.MONTH, 5);
//        cal.set(Calendar.YEAR, 2018);
//        cal.set(Calendar.DAY_OF_MONTH, 21);
//        cal.set(Calendar.HOUR_OF_DAY, 7);
//        cal.set(Calendar.MINUTE, 30);
        cal.setTimeInMillis(System.currentTimeMillis() + 3000);

        Intent i = new Intent(this, AlarmReceiver.class);
        Bundle b = new Bundle();
        if(view == setStartBtn){
            String message = currentCourse.title + " starts today";
            b.putString("THE_MESSAGE", message);
        } else if (view == setEndBtn) {
            String message = currentCourse.title + " ends today";
            b.putString("THE_MESSAGE", message);
        }
        i.putExtras(b);

        Random generator = new Random();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, generator.nextInt(),
                i, 0);


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_course, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.navigation_term_detail:

                List l =  database.termDao().getTerm(currentCourse.termId);
                Term currentTerm = (Term) l.get(0);

                Intent i1 = new Intent(DetailCourse.this, DetailTerm.class);
                Bundle b1 = new Bundle();
                b1.putSerializable("CURRENT_TERM", currentTerm);
                i1.putExtras(b1);
                startActivityForResult(i1, 0);

                return true;
            case R.id.navigation_edit_course:

                Intent i2 = new Intent(DetailCourse.this, EditCourse.class);
                Bundle b2 = new Bundle();
                b2.putSerializable("CURRENT_COURSE", currentCourse);
                i2.putExtras(b2);
                startActivityForResult(i2, 0);

                return true;
            case R.id.navigation_new_assessment:

                Intent i3 = new Intent(DetailCourse.this, EditAssessment.class);
                Bundle b3 = new Bundle();
                b3.putSerializable("PARENT_COURSE", currentCourse);
                i3.putExtras(b3);
                startActivityForResult(i3, 0);

                return true;
            case R.id.navigation_new_note:
                Intent i4 = new Intent(DetailCourse.this, CourseNote.class);
                Bundle b4 = new Bundle();
                b4.putSerializable("CURRENT_COURSE", currentCourse);
                i4.putExtras(b4);
                startActivity(i4);
                return true;
            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onShareTargetSelected(ShareActionProvider source,
                                         Intent intent) {
        Toast.makeText(this, intent.getComponent().toString(),
                Toast.LENGTH_LONG).show();

        return(false);
    }

}
