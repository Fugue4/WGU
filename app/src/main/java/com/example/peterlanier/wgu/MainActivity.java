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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    //Create Sample Data
    Term t1 = new Term("Spring Term 2001", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2001"), new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2001)"));
    Term t2 = new Term("Fall Term 2001", new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2001"), new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2001)"));
    Term t3 = new Term("Spring Term 2002", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2002"), new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2002)"));
    Term t4 = new Term("Fall Term 2002", new SimpleDateFormat("dd/MM/yyyy").parse("01/06/2002"), new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2002)"));

    public MainActivity() throws ParseException {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.terms_list_view);


        //Load Sample Data
        final ArrayList<Term> termList = new ArrayList<>();
        termList.add(t1);
        termList.add(t2);
        termList.add(t3);
        termList.add(t4);

        TermAdapter adapter = new TermAdapter(this, termList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent myIntent = new Intent(view.getContext(), DetailTerm.class);
                startActivityForResult(myIntent, 0);

            }
        });

    }



    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        System.out.println(t1);
        switch (item.getItemId()) {
            case R.id.navigation_terms:
                System.out.println("navigated to terms");
                return true;
            default:
                System.out.println("I failed");
                return super.onOptionsItemSelected(item);
        }


    }


}
