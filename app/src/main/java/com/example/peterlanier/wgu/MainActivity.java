package com.example.peterlanier.wgu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getDatabase(getApplicationContext());;

//        database.termDao().addTerm(new Term(0, "Test 1", "now", "then"));
//        database.termDao().addTerm(new Term(0, "Test 2", "now2", "then2"));

        final Button button = (Button) findViewById(R.id.home_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListTerm.class);
                startActivityForResult(i, 0);
            }

        });

    }

}
