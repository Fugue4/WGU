package com.example.peterlanier.wgu;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        //Delete Term
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTerm != null){

                    if(!database.courseDao().findCoursesForTerm(currentTerm.id).isEmpty()) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailTerm.this);
                        alertDialogBuilder.setTitle("Cannot Delete Term");
                        alertDialogBuilder
                                .setMessage("You can not delete a term that has associated courses. You may delete all courses associated with this term now, but this action cannot be undone.")
                                .setCancelable(false)
                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Delete All Courses Now", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        database.courseDao().deleteCoursesFromTerm(currentTerm.id);

                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);

                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    } else {
                        database.termDao().removeTerm(currentTerm);

                        System.out.println("Deleted Term");
                        Intent i = new Intent(DetailTerm.this, ListTerm.class);
                        startActivityForResult(i, 0);
                    }

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
            case R.id.navigation_list_terms:
                Intent iii = new Intent(DetailTerm.this, ListTerm.class);
                startActivity(iii);
                return true;
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
                bb.putSerializable("PARENT_TERM", currentTerm);
                ii.putExtras(bb);
                startActivityForResult(ii, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }




}
