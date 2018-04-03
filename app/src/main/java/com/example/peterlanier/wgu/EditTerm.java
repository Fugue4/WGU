package com.example.peterlanier.wgu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class EditTerm extends AppCompatActivity {

    private AppDatabase database;
    private EditText title;
    private TextView start;
    private TextView end;
    private Button cancel;
    private Button save;
    private Button btn_start;
    private Button btn_end;
    private boolean update = false;
    int sYear, sMonth, sDay, eYear, eMonth, eDay;
    DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;

    final int DATE_PICKER_START = 0;
    final int DATE_PICKER_END = 1;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = AppDatabase.getDatabase(getApplicationContext());

        title = (EditText) findViewById(R.id.edit_term_title);
        start = (TextView) findViewById(R.id.edit_term_start);
        end = (TextView) findViewById(R.id.edit_term_end);
        btn_start = (Button) findViewById(R.id.edit_btn_term_start);
        btn_end = (Button) findViewById(R.id.edit_btn_term_end);
        save = (Button) findViewById(R.id.btn_save_term);
        cancel = (Button) findViewById(R.id.btn_cancel_term);

        System.out.println("get tag 1" + btn_start.getTag());

        Term currentTerm = null;


        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Term From Previous Activity
            currentTerm = (Term) b.getSerializable("EDIT_TERM");
            System.out.println("After load " + currentTerm.title);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");


            title.setText(currentTerm.title, TextView.BufferType.EDITABLE);
            start.setText(currentTerm.start);
            end.setText(currentTerm.end);
            update = true;
        }

//        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        showDate(year, month+1, day);

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
                setStartDate(arg1, arg2+1, arg3);
                System.out.println("heard date picker start");
            }
        };


        to_dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet (DatePicker arg0,int arg1, int arg2, int arg3){
                setEndDate(arg1, arg2+1, arg3);
                System.out.println("heard date picker end");
            }
        };

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (update = true){
                    database.termDao().updateTerm(new Term(0, title.getText().toString(), start.getText().toString(), end.getText().toString()));
                    System.out.println("term updated");
                } else {
                    database.termDao().addTerm(new Term(0, title.getText().toString(), start.getText().toString(), end.getText().toString()));
                    System.out.println("term added");
                }

                returnToListTerm();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnToListTerm();
            }
        });

    }


    private void returnToListTerm(){
        Intent i = new Intent(EditTerm.this, ListTerm.class);
        startActivityForResult(i, 0);
    }


    @SuppressWarnings("deprecation")
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