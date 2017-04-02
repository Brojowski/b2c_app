package com.example.alex.betweentwocities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends PortraitActivity
{
    private BoardManager _displayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button score_btn = (Button) findViewById(R.id.score_btn);
        score_btn.setOnClickListener(new ScoreOnClickListener());

        _displayManager = new BoardManager(this);
    }

    private class ScoreOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            City newCity = _displayManager.getCity();
            Log.v(MainActivity.class.toString(), newCity.toString());
            Intent scoreCity = new Intent(MainActivity.this, ScoreActivity.class);
            scoreCity.putExtra("City", newCity);
            startActivity(scoreCity);
        }
    }
}
