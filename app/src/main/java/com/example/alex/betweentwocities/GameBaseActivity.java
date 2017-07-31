package com.example.alex.betweentwocities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.alex.betweentwocities.Services.*;
import com.example.b2c_core.*;

/**
 * Created by alex on 3/28/17.
 */

public abstract class GameBaseActivity extends AppCompatActivity implements IGameEvents
{
    private static final String TAG = GameBaseActivity.class.getSimpleName();
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

    @Override
    public void onStartDraft(DraftTransferObject dto)
    {
        Log.i(TAG,"onStartDraft");
        Intent startDraftIntent = new Intent(this, DraftingActivity.class);
        startDraftIntent.putExtra(DraftTransferObject.class.toString(), dto);
        startActivity(startDraftIntent);
    }

    @Override
    public void onStartPlace(PlaceTransferObject pto)
    {
        Log.i(TAG,"onStartPlace");
    }

    @Override
    public void onBoardUpdate(PlaceTileTransferObject ptto)
    {
        Log.i(TAG,"onBoardUpdate");
    }

    protected void onGameServiceConnection(){}

    private ServiceConnection _gameServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            DummyGameService.DummyBinder binder = (DummyGameService.DummyBinder) service;
            _gameService = binder.getService();
            _gameService.registerEventListener(GameBaseActivity.this);
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
