package com.example.alex.betweentwocities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.b2c_core.BuildingType;
import com.example.b2c_core.City;
import com.example.b2c_core.PlaceTransferObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

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


        _iconManager = new IconManager(this, R.id.building_icons);

        Intent starter = getIntent();
        if (starter.hasExtra(PlaceTransferObject.class.toString()))
        {
            PlaceTransferObject transferObject = (PlaceTransferObject) starter.getSerializableExtra(PlaceTransferObject.class.toString());
            _iconManager.setLayoutMode(IconManager.Mode.Place2);
            ArrayList<BuildingType> tiles = new ArrayList<>();
            Collections.addAll(tiles, transferObject.tiles.get(transferObject.currentUser));
            _iconManager.setIcons(tiles);

            _displayManager = new BoardManager(this, transferObject.leftCity.getCity());
        }

        if (_displayManager == null)
        {
            _displayManager = new BoardManager(this);
        }
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
