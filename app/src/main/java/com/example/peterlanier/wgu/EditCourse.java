package com.example.peterlanier.wgu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EditCourse extends AppCompatActivity {

    private EditText title;
    private TextView start;
    private TextView end;
    private EditText mentor;
    private EditText phone;
    private EditText email;

    private Button cancel;
    private Button save;
    private Button btn_start;
    private Button btn_end;
    private boolean update;

    //Datepicker
    int sYear, sMonth, sDay, eYear, eMonth, eDay;
    DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;
    final int DATE_PICKER_START = 0;
    final int DATE_PICKER_END = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);


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

    }

    public void onRadioButtonClicked(View v){

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

}
