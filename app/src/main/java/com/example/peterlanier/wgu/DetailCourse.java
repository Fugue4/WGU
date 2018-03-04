package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailCourse extends AppCompatActivity {

    private TextView title;
    private TextView start;
    private TextView end;
    private ListView listView;

    Assessment c1 = new Assessment("Assessment 1", new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2001)"));
    Assessment c2 = new Assessment("Assessment 2", new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2001)"));
    Assessment c3 = new Assessment("Assessment 3", new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2002)"));
    Assessment c4 = new Assessment("Assessment 4", new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2002)"));

    public DetailCourse() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);

        title = (TextView) findViewById(R.id.course_detail_title);
        start = (TextView) findViewById(R.id.course_detail_start);
        end = (TextView) findViewById(R.id.course_detail_end);

        Course currentCourse = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Course From Previous Activity
            currentCourse = (Course) b.getSerializable("CURRENT_COURSE");

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            title.setText(currentCourse.title);
            start.setText(df.format(currentCourse.start));
            end.setText(df.format(currentCourse.end));
        }

        listView = (ListView) findViewById(R.id.assessments_list_view);

        final ArrayList<Assessment> assessmentList = new ArrayList<>();
        //Load Sample Data
        assessmentList.add(c1);
        assessmentList.add(c2);
        assessmentList.add(c3);
        assessmentList.add(c4);

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
}
