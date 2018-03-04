package com.example.peterlanier.wgu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class EditTerm extends AppCompatActivity {

    private EditText title;
    private EditText start;
    private EditText end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (EditText) findViewById(R.id.edit_term_title);
        start = (EditText) findViewById(R.id.edit_term_start);
        end = (EditText) findViewById(R.id.edit_term_end);

        Term editTerm = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Term From Previous Activity
            editTerm = (Term) b.getSerializable("EDIT_TERM");
            System.out.println("After load " + editTerm.title);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

//            title.setHint(editTerm.title);
            title.setText(editTerm.title, TextView.BufferType.EDITABLE);
            title.selectAll();

            start.setText(df.format(editTerm.start));
            end.setText(df.format(editTerm.end));
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
