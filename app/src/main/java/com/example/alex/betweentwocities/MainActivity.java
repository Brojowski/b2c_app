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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button score_btn = (Button) findViewById(R.id.score_btn);
        score_btn.setOnClickListener(new ScoreOnClickListener());
    }

    private class ScoreOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            GridLayout cityLayout = (GridLayout) findViewById(R.id.city_layout);

            if (cityLayout.getChildCount() != 16)
            {
                Toast.makeText(MainActivity.this, "There are a weird number of children...\n Exiting", Toast.LENGTH_SHORT).show();
                return;
            }

            City newCity = new City();
            int x = 0;
            int y = 0;
            for (int i = 0; i < 16; i++)
            {
                if (x >= 4)
                {
                    x = 0;
                    y++;
                }
                BuildingTile tile = (BuildingTile) cityLayout.getChildAt(i);
                newCity.tryAddTile(tile.getBuildingType(), x, y);
                x++;
            }
            Log.v(MainActivity.class.toString(), newCity.toString());
            Intent scoreCity = new Intent(MainActivity.this, ScoreActivity.class);
            scoreCity.putExtra("City", newCity);
            startActivity(scoreCity);
        }
    }
}
