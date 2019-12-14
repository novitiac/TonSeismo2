package com.example.android.quakereport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button numbers = (Button) findViewById(R.id.prepare_move_intent);
        numbers.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(TodoActivity.this, PrepareActivity.class);
                startActivity(numbersIntent);
            }
        });

        Button family = (Button) findViewById(R.id.survive_move_intent);
        family.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                Intent familyIntent = new Intent(TodoActivity.this, SurviveActivity.class);
                startActivity(familyIntent);
            }
        });

        Button colors = (Button) findViewById(R.id.recover_move_intent);
        colors.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the colors category is clicked on.
            @Override
            public void onClick(View view) {
                Intent colorsIntent = new Intent(TodoActivity.this, RecoverActivity.class);
                startActivity(colorsIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
