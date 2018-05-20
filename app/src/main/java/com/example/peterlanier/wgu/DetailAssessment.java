package com.example.peterlanier.wgu;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DetailAssessment extends AppCompatActivity {

    private TextView title;
    private TextView due;
    private TextView goal;
    private TextView goalTitle;
    private TextView type;
    private Assessment currentAssessment;
    private AppDatabase database;
    private Button addGoal;
    DatePickerDialog.OnDateSetListener addGoalListener;
    final int DATE_PICKER_GOAL = 0;
    int gYear = 2018, gMonth = 0, gDay = 1;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_assessment);
        setTitle("Assessment Details");
        database = AppDatabase.getDatabase(getApplicationContext());

        title = (TextView) findViewById(R.id.assessment_detail_title);
        due = (TextView) findViewById(R.id.assessment_detail_due);
        addGoal = (Button) findViewById(R.id.edit_btn_assessment_goal);
        goal = (TextView) findViewById(R.id.assessment_detail_goal);
        type = (TextView) findViewById(R.id.assessment_detail_type);
        goalTitle = (TextView) findViewById(R.id.assessment_detail_goal_title);
        goalTitle.setText("Set Alerts For Goal Dates");

        currentAssessment = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentAssessment = (Assessment) b.getSerializable("CURRENT_ASSESSMENT");

            title.setText(currentAssessment.title);
            due.setText(currentAssessment.due);
            type.setText(currentAssessment.type);

            displayGoals();

//            if(!currentAssessment.goal.isEmpty()) {
//                StringBuilder sb = new StringBuilder();
//                for (String g : currentAssessment.goal) {
//                    sb.append(g + "\n");
//                }
//                System.out.println(sb);
//                goal.setText(sb);
//            }
        }



        addGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_PICKER_GOAL);
            }
        });

        addGoalListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                setGoalDate(arg1, arg2 + 1, arg3);
                System.out.println("heard date picker goal");
                //maybe try adding to goals arraylist here.

            }
        };


    }

    public void setDate(View view) {
        showDialog(999);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_assessment, menu);
        return true;
    }

    private void setGoalDate(int year, int month, int day) {

        StringBuilder sb = new StringBuilder();
                sb.append(month)
                .append("/").append(day)
                .append("/").append(year);
        currentAssessment.goal.add(sb.toString());
        database.assessmentDao().updateAssessment(currentAssessment);
        setAlert(year, month, day);
        displayGoals();
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch(id){
            case DATE_PICKER_GOAL:
                return new DatePickerDialog(this, addGoalListener, gYear, gMonth, gDay);
        }
        return null;
    }

    private void displayGoals(){
        if(!currentAssessment.goal.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String g : currentAssessment.goal) {
                sb.append(g + "\n");
            }
            System.out.println(sb);
            goal.setText(sb);
        } else {
            goal.setText("No alerts set.");
        }
    }

    public void setAlert(int year, int month, int day){

//        int[] dateArray = new int[3];
//        String[] dateParts;
//
//
//
//
//        if(!currentAssessment.goal.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            for (String g : currentAssessment.goal) {
//                sb.append(g + "\n");
//            }
//            System.out.println(sb);
//            goal.setText(sb);
//        } else {
//            goal.setText("No alerts set.");
//        }
//
//
//
//
//
//
//        if(view == setStartBtn){
//            dateParts = start.getText().toString().split(Pattern.quote("/"));
//            Toast.makeText(this, "Alarm set for course start",
//                    Toast.LENGTH_LONG).show();
//        } else if (view == setEndBtn) {
//            dateParts = end.getText().toString().split(Pattern.quote("/"));
//            Toast.makeText(this, "Alarm set for course end",
//                    Toast.LENGTH_LONG).show();
//        } else {
//            dateParts = null;
//        }
//
//        for (int x = 0; x < dateParts.length; x++) {
//            dateArray[x] = Integer.parseInt(dateParts[x]);
//            System.out.println(x + " is " + dateArray[x]);
//        }
        Toast.makeText(this, "New alarm set for goal date",
                    Toast.LENGTH_LONG).show();

        cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH,day);
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 00);
//        cal.setTimeInMillis(System.currentTimeMillis() + 3000);

        Intent i = new Intent(this, AlarmReceiver.class);
        Bundle b = new Bundle();

        String message = currentAssessment.title + " goal date alarm";
        b.putString("THE_MESSAGE", message);
        i.putExtras(b);

        Random generator = new Random();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, generator.nextInt(),
                i, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.navigation_course_detail:
                List l =  database.courseDao().findCourseFromAssessment(currentAssessment.courseId);
                Course currentCourse = (Course) l.get(0);

                Intent iii = new Intent(DetailAssessment.this, DetailCourse.class);
                Bundle bbb = new Bundle();
                bbb.putSerializable("CURRENT_COURSE", currentCourse);
                iii.putExtras(bbb);
                startActivity(iii);
                return true;
            case R.id.navigation_edit_assessment:
                Intent i = new Intent(DetailAssessment.this, EditAssessment.class);
                Bundle b = new Bundle();
                b.putSerializable("CURRENT_ASSESSMENT", currentAssessment);
                i.putExtras(b);
                startActivityForResult(i, 0);
                return true;
            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }

    }

}
