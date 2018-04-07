package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailTerm extends AppCompatActivity {

    private AppDatabase database;
    private Course course;
    private TextView title;
    private TextView start;
    private TextView end;
    private ListView listView;
    private Button delete;
    private Term currentTerm;

//    Course c1 = new Course("Course 1", new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2001"), new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2001)"));
//    Course c2 = new Course("Course 2", new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2001"), new SimpleDateFormat("MM/dd/yyyy").parse("01/12/2001)"));
//    Course c3 = new Course("Course 3", new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2002"), new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2002)"));
//    Course c4 = new Course("Course 4", new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2002"), new SimpleDateFormat("MM/dd/yyyy").parse("01/12/2002)"));
//    Course c5 = new Course("Course 5", new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2001"), new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2001)"));
//    Course c6 = new Course("Course 6", new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2001"), new SimpleDateFormat("MM/dd/yyyy").parse("01/12/2001)"));
//    Course c7 = new Course("Course 7", new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2002"), new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2002)"));
//    Course c8 = new Course("Course 8", new SimpleDateFormat("MM/dd/yyyy").parse("01/06/2002"), new SimpleDateFormat("MM/dd/yyyy").parse("01/12/2002)"));

    public DetailTerm() throws ParseException {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_term);
        setTitle("Term Details");

        //Database
        database = AppDatabase.getDatabase(getApplicationContext());

        title = (TextView) findViewById(R.id.term_detail_title);
        start = (TextView) findViewById(R.id.term_detail_start);
        end = (TextView) findViewById(R.id.term_detail_end);
        delete = (Button) findViewById(R.id.btn_delete_term);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            //Retrieve Term From Previous Activity
            currentTerm = (Term) b.getSerializable("CURRENT_TERM");

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            title.setText(currentTerm.title);
            start.setText(currentTerm.start);
            end.setText(currentTerm.end);
        }

        listView = (ListView) findViewById(R.id.courses_list_view);

//        final ArrayList<Course> courseList = new ArrayList<>();
        //Load Sample Data
//        courseList.add(c1);
//        courseList.add(c2);
//        courseList.add(c3);
//        courseList.add(c4);
//        courseList.add(c5);
//        courseList.add(c6);
//        courseList.add(c7);
//        courseList.add(c8);
//        c1.setMentorName("Joe Brown");
//        c1.setMentorPhone("(201) 584-9638");
//        c1.setMentorEmail("brown@wgu.edu");

//        database.courseDao().addCourse(new Course(1, "course1", "this point", "that point", currentTerm.getId()));
//        database.courseDao().addCourse(new Course(2, "course2", "this point", "that point", 1));
//        database.courseDao().addCourse(new Course(3, "course3", "this point", "that point", 2));
        System.out.println("Courses Added");

        final ArrayList<Course> courseList = (ArrayList<Course>) database.courseDao().findCoursesForTerm(currentTerm.getId());

        final CourseAdapter adapter = new CourseAdapter(this, courseList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Course currentCourse = (Course) parent.getItemAtPosition(position);

                Intent i = new Intent(DetailTerm.this, DetailCourse.class);
                Bundle b = new Bundle();
                b.putSerializable("CURRENT_COURSE", currentCourse);
                i.putExtras(b);
                startActivityForResult(i, 0);

            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        final Term editTerm = currentTerm;
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(getBaseContext(), EditTerm.class);
//                Bundle b = new Bundle();
//                b.putSerializable("EDIT_TERM", editTerm);
//                i.putExtras(b);
//                startActivityForResult(i, 0);
//            }
//        });

        //Delete Term
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTerm != null){
                    database.termDao().removeTerm(currentTerm);

                    System.out.println("Deleted Term");
                    Intent i = new Intent(DetailTerm.this, ListTerm.class);
                    startActivityForResult(i, 0);

                }
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.navigation_edit_term:
                Intent i = new Intent(DetailTerm.this, EditTerm.class);
                Bundle b = new Bundle();
                b.putSerializable("EDIT_TERM", currentTerm);
                i.putExtras(b);
                startActivityForResult(i, 0);

                return true;
            case R.id.navigation_new_course:
                Intent ii = new Intent(DetailTerm.this, EditCourse.class);
                Bundle bb = new Bundle();
                bb.putSerializable("EDIT_TERM", currentTerm);
                ii.putExtras(bb);
                startActivityForResult(ii, 0);
                return true;
            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }


    }


}
