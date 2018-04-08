package com.example.peterlanier.wgu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class EditCourse extends AppCompatActivity {

    private EditText title;
    private TextView start;
    private TextView end;
    private EditText mentor;
    private EditText phones;
    private EditText emails;
    private String statusValue;
    private RadioButton r1, r2, r3;

    private Button cancel;
    private Button save;
    private Button btn_start;
    private Button btn_end;
    private Boolean update;
    private AppDatabase database;
    private Course updateCourse;
    private Course c;
    private Term t;


    //Datepicker
    int sYear = 2018, sMonth = 0, sDay = 1, eYear = 2018, eMonth = 11, eDay = 31;
    DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;
    final int DATE_PICKER_START = 0;
    final int DATE_PICKER_END = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        database = AppDatabase.getDatabase(getApplicationContext());

        title = (EditText) findViewById(R.id.edit_course_title);
        start = (TextView) findViewById(R.id.edit_course_start);
        end = (TextView) findViewById(R.id.edit_course_end);
        mentor = (EditText) findViewById(R.id.edit_course_mentor);
        phones = (EditText) findViewById(R.id.edit_course_phones);
        emails = (EditText) findViewById(R.id.edit_course_emails);
        r1 = (RadioButton) findViewById(R.id.radio_status_1);
        r2 = (RadioButton) findViewById(R.id.radio_status_2);
        r3 = (RadioButton) findViewById(R.id.radio_status_3);
        save = (Button) findViewById(R.id.btn_save_course);
        cancel = (Button) findViewById(R.id.btn_cancel_course);
        btn_start = (Button) findViewById(R.id.edit_btn_course_start);
        btn_end = (Button) findViewById(R.id.edit_btn_course_end);

        c = null;
        update = false;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {

            if (b.containsKey("PARENT_TERM")) {
                //Retrieve Term From Previous Activity
                t = (Term) b.getSerializable("PARENT_TERM");

                setTitle("Add course to " + t.title);
            }

            if (b.containsKey("CURRENT_COURSE")) {
                c = (Course) b.getSerializable("CURRENT_COURSE");
                title.setText(c.title);
                start.setText(c.start);
                end.setText(c.end);
                mentor.setText(c.mentorName);
                phones.setText(c.mentorPhone);
                emails.setText(c.mentorEmail);

                if (c.status != null) {
                    switch (c.status) {
                        case "In Progress":
                            r1.toggle();
                            break;
                        case "Completed":
                            r2.toggle();
                            break;
                        case "Dropped":
                            r3.toggle();
                            break;
                    }
                }

                update = true;

            }

        }


        btn_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_PICKER_START);
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_PICKER_END);
            }
        });

        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                setStartDate(arg1, arg2 + 1, arg3);
                System.out.println("heard date picker start");
            }
        };


        to_dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                setEndDate(arg1, arg2 + 1, arg3);
                System.out.println("heard date picker end");
            }
        };

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (c == null) {
                    c = new Course(0, title.getText().toString(), start.getText().toString(), end.getText().toString(), t.id);
                }
                updateCourse = setCourse();

                if (update){
                    database.courseDao().updateCourse(updateCourse);
                    System.out.println("course updated");
                } else {
                    database.courseDao().addCourse(updateCourse);
                    System.out.println("course added");
                }

                returnToDetailCourse();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnToDetailCourse();
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_status_1:
                if (checked)
                    statusValue = "In Progress";
                    break;
            case R.id.radio_status_2:
                if (checked)
                    statusValue = "Completed";
                    break;
            case R.id.radio_status_3:
                if (checked)
                    statusValue = "Dropped";
                    break;
        }
    }


    //////////////////////////////////////////////////////

    public void setDate(View view) {
        showDialog(999);
    }

    private void setStartDate(int year, int month, int day) {
        start.setText(new StringBuilder().append(month)
                .append("/").append(day)
                .append("/").append(year));
    }

    private void setEndDate(int year, int month, int day) {
        end.setText(new StringBuilder().append(month)
                .append("/").append(day)
                .append("/").append(year));
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch(id){
            case DATE_PICKER_START:
                return new DatePickerDialog(this, from_dateListener, sYear, sMonth, sDay);
            case DATE_PICKER_END:
                return new DatePickerDialog(this, to_dateListener, eYear, eMonth, eDay);
        }
        return null;
    }

    private void returnToDetailCourse(){
        Intent i = new Intent(EditCourse.this, DetailCourse.class);
        Bundle b = new Bundle();
        b.putSerializable("CURRENT_COURSE", c);
        i.putExtras(b);
        startActivity(i);
    }

    private Course setCourse(){

        c.setTitle(title.getText().toString());
        c.setStart(start.getText().toString());
        c.setEnd(end.getText().toString());
        c.setMentorName(mentor.getText().toString());
        c.setMentorPhone(phones.getText().toString());
        c.setMentorEmail(emails.getText().toString());
        c.setStatus(statusValue);

        return c;
    }

}
