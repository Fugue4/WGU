package com.example.peterlanier.wgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailTerm extends AppCompatActivity {

    private TextView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_term);

        v = (TextView) findViewById(R.id.term_detail_title);

        Term currentTerm = null;

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            currentTerm = (Term) b.getSerializable("CURRENT_TERM");
            v.setText(currentTerm.title);
        }




    }
}
