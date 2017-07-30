package com.example.alex.betweentwocities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alex on 3/28/17.
 */

public abstract class PortraitActivity extends AppCompatActivity
{
    protected IGameService _gameService;
    protected boolean _bound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent bindToService = new Intent(this, DummyGameService.class);
        bindService(bindToService, _gameServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(_gameServiceConnection);
    }

    protected void onGameServiceConnection(){}

    private ServiceConnection _gameServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            DummyGameService.DummyBinder binder = (DummyGameService.DummyBinder) service;
            _gameService = binder.getService();
            _bound = true;
            onGameServiceConnection();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            _bound = false;
        }
    };
}
