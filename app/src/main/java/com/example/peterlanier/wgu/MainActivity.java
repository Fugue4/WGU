package com.example.peterlanier.wgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

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


        //load objects to array
        final ArrayList<Term> termList = new ArrayList<>();
        termList.add(t1);
        termList.add(t2);
        termList.add(t3);
        termList.add(t4);

        TermAdapter adapter = new TermAdapter(this, termList);
        listView.setAdapter(adapter);


//        String[] listTerms = new String[termList.size()];
//
//        //place objects into string
//        for(int i=0; i < termList.size(); i++){
//            Term term = termList.get(i);
//            listTerms[i] = term.title;
//        }
//
//
//        ArrayAdapter<String> termsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTerms);
//
//        listView = (ListView) findViewById(R.id.terms_list_view);
//        listView.setAdapter(termsAdapter);
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
