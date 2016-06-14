package com.example.micheal.assingment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Micheal on 07/04/16.
 */
public class DrawActivity extends AppCompatActivity {


    private double score;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.draw);

        Intent i = getIntent();
        score = i.getDoubleExtra("score", 0.0);

        System.out.println(score);


        View customView = new DrawBarChart(DrawActivity.this, (int) score);
        LinearLayout layout = (LinearLayout) findViewById(R.id.draw);
        layout.addView(customView, 600, 1000);





    }



}
