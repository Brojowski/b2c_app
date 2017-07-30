package com.example.alex.betweentwocities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button joinGameBtn = (Button) findViewById(R.id.btn_join_game);
        joinGameBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent joinGame = new Intent(HomeActivity.this, LoadingActivity.class);
                startActivity(joinGame);
            }
        });
        final Button scoreBoardBtn = (Button) findViewById(R.id.btn_score_board);
        scoreBoardBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Add standalone scoring area.
            }
        });
    }
}
