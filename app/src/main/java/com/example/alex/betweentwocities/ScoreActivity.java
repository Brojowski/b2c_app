package com.example.alex.betweentwocities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.b2c_core.City;
import com.example.b2c_core.CityScorer;

import java.io.Serializable;

public class ScoreActivity extends PortraitActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        TextView scoreView = (TextView) findViewById(R.id.score_display);

        Serializable content = getIntent().getSerializableExtra(City.class.toString());
        if (content != null)
        {
            City toScore = (City)content;
            Log.v(ScoreActivity.class.toString(), toScore.toString());
            int score = CityScorer.scoreCity(toScore, 3);
            scoreView.setText("" + score);
        }
    }
}
