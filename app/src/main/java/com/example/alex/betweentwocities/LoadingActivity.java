package com.example.alex.betweentwocities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoadingActivity extends PortraitActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    private void onConnection()
    {
        _gameService.joinGame();

        ProgressBar connBar = (ProgressBar) findViewById(R.id.connected_spinner);
        connBar.setVisibility(View.INVISIBLE);
        ImageView complete = (ImageView) findViewById(R.id.connected_complete);
        complete.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onGameServiceConnection()
    {
        if (_bound)
        {
            Runnable onConnection = new Runnable()
            {
                @Override
                public void run()
                {
                    LoadingActivity.this.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            onConnection();
                        }
                    });
                }
            };
            if (_gameService.isConnected())
            {
                onConnection();
            }
            else
            {
                _gameService.registerOnConnection(onConnection);
            }
        }
    }
}
