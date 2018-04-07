package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

public class ListTerm extends AppCompatActivity {

    private ListView listView;
    private AppDatabase database;
    private Term term;


    //Create Sample Data
//    Term t1 = new Term("Spring Term 2001", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2001"), new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2001)"));
//    Term t2 = new Term("Fall Term 2001", new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2001"), new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2001)"));
//    Term t3 = new Term("Spring Term 2002", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2002"), new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2002)"));
//    Term t4 = new Term("Fall Term 2002", new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2002"), new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2002)"));

    public ListTerm() throws ParseException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_term);
        setTitle("Terms");

        //Database
        database = AppDatabase.getDatabase(getApplicationContext());

        listView = (ListView) findViewById(R.id.terms_list_view);

//        final ArrayList<Term> termList = new ArrayList<>();
        //Load Sample Data
//        termList.add(t1);
//        termList.add(t2);
//        termList.add(t3);
//        termList.add(t4);

//        database.termDao().removeAllTerms();
//        database.termDao().addTerm(new Term(0, "Test 1", "now", "then"));
//        database.termDao().addTerm(new Term(0, "Test 2", "now2", "then2"));
//        term = database.termDao().getAllTerm().get(0);

        final ArrayList<Term> termList = (ArrayList<Term>) database.termDao().getAllTerm();

        final TermAdapter adapter = new TermAdapter(this, termList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Term currentTerm = (Term) parent.getItemAtPosition(position);

                Intent i = new Intent(ListTerm.this, DetailTerm.class);
                Bundle b = new Bundle();
                b.putSerializable("CURRENT_TERM", currentTerm);
                i.putExtras(b);
                startActivityForResult(i, 0);

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_term_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.navigation_new_term:
                System.out.println("new_term");
                Intent i = new Intent(ListTerm.this, EditTerm.class);
                startActivity(i);
                return true;
//            case R.id.navigation_new_course:
//                System.out.println("new_course");
//                return true;
            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }


    }
}
