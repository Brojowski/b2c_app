package com.example.alex.betweentwocities;

import android.os.Bundle;
import android.util.Log;

import com.example.alex.betweentwocities.b2c_core.City;

import java.io.Serializable;

public class ScoreActivity extends PortraitActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Serializable content = getIntent().getSerializableExtra("City");
        if (content != null)
        {
            City toScore = (City)content;
            Log.v(ScoreActivity.class.toString(), toScore.toString());
        }
    }
}
