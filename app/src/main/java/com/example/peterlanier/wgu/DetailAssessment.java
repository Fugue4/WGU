package com.example.peterlanier.wgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class DetailAssessment extends AppCompatActivity {

    private TextView title;
    private TextView due;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_assessment);

        title = (TextView) findViewById(R.id.assessment_detail_title);
        due = (TextView) findViewById(R.id.assessment_detail_due);

        Assessment currentAssessment = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentAssessment = (Assessment) b.getSerializable("CURRENT_ASSESSMENT");

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            title.setText(currentAssessment.title);
            due.setText(df.format(currentAssessment.due));
        }
    }
}
