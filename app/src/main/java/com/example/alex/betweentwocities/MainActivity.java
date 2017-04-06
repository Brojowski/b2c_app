package com.example.alex.betweentwocities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alex.betweentwocities.b2c_core.BuildingType;
import com.example.alex.betweentwocities.b2c_core.City;

import java.util.ArrayList;

public class MainActivity extends PortraitActivity
{
    private BoardManager _displayManager;
    private IconManager _iconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button score_btn = (Button) findViewById(R.id.score_btn);
        score_btn.setOnClickListener(new ScoreOnClickListener());

        _displayManager = new BoardManager(this);
        _iconManager = new IconManager(this, R.id.building_icons);
        _iconManager.setLayoutMode(IconManager.Mode.Place2);
        ArrayList<BuildingType> temp = new ArrayList<>();
        temp.add(BuildingType.Shop);
        temp.add(BuildingType.Park);
        _iconManager.setIcons(temp);
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
