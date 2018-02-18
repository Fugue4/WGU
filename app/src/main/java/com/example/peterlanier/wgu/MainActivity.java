package com.example.peterlanier.wgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayList<Term> termList = Term.getTerms();
        String[] listTerms = new String[termList.size()];

        for(int i=0; i < termList.size(); i++){
            Term term = termList.get(i);
            listTerms[i] = term.title;
        }


        ArrayAdapter<String> termsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTerms);

        listView = (ListView) findViewById(R.id.terms_list_view);
        listView.setAdapter(termsAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
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
