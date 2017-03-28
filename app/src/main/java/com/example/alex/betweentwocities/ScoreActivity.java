package com.example.alex.betweentwocities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
