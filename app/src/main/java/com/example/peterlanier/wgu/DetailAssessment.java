package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailAssessment extends AppCompatActivity {

    private TextView title;
    private TextView due;
    private TextView goal;
    private TextView notes;
    private TextView type;
    private Assessment currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_assessment);
        setTitle("Assessment Details");

        title = (TextView) findViewById(R.id.assessment_detail_title);
        due = (TextView) findViewById(R.id.assessment_detail_due);

        currentAssessment = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentAssessment = (Assessment) b.getSerializable("CURRENT_ASSESSMENT");

            title.setText(currentAssessment.title);
            due.setText(currentAssessment.due);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
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
