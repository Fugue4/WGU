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

public class EditAssessment extends AppCompatActivity {

    private EditText title;
    private TextView due;
    private TextView goal;
    private TextView notes;
    private String assessmentType;
    private RadioButton r1, r2;

    private Button cancel;
    private Button save;
    private Button btn_due;
    private Button btn_goal;
    private Boolean update;
    private AppDatabase database;
    private Assessment updateAssessment;
    private Assessment a;
    private Course c;


    //Datepicker
    int sYear = 2018, sMonth = 0, sDay = 1, eYear = 2018, eMonth = 11, eDay = 31;
    DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;
    final int DATE_PICKER_START = 0;
    final int DATE_PICKER_END = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        database = AppDatabase.getDatabase(getApplicationContext());

        title = (EditText) findViewById(R.id.edit_assessment_title);
        due = (TextView) findViewById(R.id.edit_assessment_due);
        goal = (TextView) findViewById(R.id.edit_assessment_goal);
        notes = (EditText) findViewById(R.id.edit_assessment_notes);
        r1 = (RadioButton) findViewById(R.id.radio_type_1);
        r2 = (RadioButton) findViewById(R.id.radio_type_2);
        save = (Button) findViewById(R.id.btn_save_assessment);
        cancel = (Button) findViewById(R.id.btn_cancel_assessment);
        btn_due = (Button) findViewById(R.id.edit_btn_assessment_due);
        btn_goal = (Button) findViewById(R.id.edit_btn_assessment_goal);

        c = null;
        update = false;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {

            if (b.containsKey("PARENT_COURSE")) {
                //Retrieve Term From Previous Activity
                c = (Course) b.getSerializable("PARENT_COURSE");

                setTitle("Add assessment to " + c.title);
            }

            if (b.containsKey("CURRENT_ASSESSMENT")) {
                a = (Assessment) b.getSerializable("CURRENT_ASSESSMENT");
                title.setText(a.title);
                due.setText(a.due);
                goal.setText(a.goal);
                notes.setText(a.notes);

                if (a.type != null) {
                    switch (a.type) {
                        case "Objective":
                            r1.toggle();
                            break;
                        case "Performance":
                            r2.toggle();
                            break;
                    }
                }

                update = true;

            }

        }

        btn_due.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_PICKER_START);
            }
        });

        btn_goal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_PICKER_END);
            }
        });

        from_dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                setDueDate(arg1, arg2 + 1, arg3);
                System.out.println("heard date picker due");
            }
        };


        to_dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                setGoalDate(arg1, arg2 + 1, arg3);
                System.out.println("heard date picker goal");
            }
        };

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (a == null) {
                    a = new Assessment(0, title.getText().toString(), due.getText().toString(), c.id);
                }
                updateAssessment = setAssessment();

                if (update){
                    database.assessmentDao().updateAssessment(updateAssessment);
                    System.out.println("course updated");
                } else {
                    database.assessmentDao().addAssessment(updateAssessment);
                    System.out.println("course added");
                }

                returnToDetailAssessment();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnToDetailAssessment();
            }
        });



    }

    public void onRadioButtonClicked2(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_type_1:
                if (checked)
                    assessmentType = "Objective";
                break;
            case R.id.radio_type_2:
                if (checked)
                    assessmentType = "Performance";
                break;
        }
    }


    //////////////////////////////////////////////////////

    public void setDate(View view) {
        showDialog(999);
    }

    private void setDueDate(int year, int month, int day) {
        due.setText(new StringBuilder().append(month)
                .append("/").append(day)
                .append("/").append(year));
    }

    private void setGoalDate(int year, int month, int day) {
        goal.setText(new StringBuilder().append(month)
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

    private void returnToDetailAssessment(){
        Intent i = new Intent(EditAssessment.this, DetailAssessment.class);
        Bundle b = new Bundle();
        b.putSerializable("CURRENT_ASSESSMENT", a);
        i.putExtras(b);
        startActivityForResult(i, 0);
    }

    private Assessment setAssessment(){

        a.setTitle(title.getText().toString());
        a.setDue(due.getText().toString());
        a.setGoal(goal.getText().toString());
        a.setNotes(notes.getText().toString());
        a.setType(assessmentType);

        return a;
    }





}
