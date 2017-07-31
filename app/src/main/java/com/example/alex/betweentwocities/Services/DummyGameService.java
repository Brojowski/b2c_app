package com.example.alex.betweentwocities.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.b2c_core.BuildingType;
import com.example.b2c_core.City;
import com.example.b2c_core.User;

/**
 * Created by alex on 7/30/17.
 */

public class DummyGameService extends Service implements IGameService
{
    private final DummyBinder _binder = new DummyBinder();
    private static final String TAG = DummyGameService.class.getSimpleName();
    private IGameEvents _eventListener;
    private User _currentUser;

    public DummyGameService()
    {
        log("Start "+TAG);
    }

    private void log(String msg)
    {
        log(msg, Log.INFO);
    }

    private void log(String msg, int status)
    {
        switch (status)
        {
            case Log.ERROR:
                Log.e(TAG, msg);
            case Log.VERBOSE:
                Log.v(TAG, msg);
            case Log.WARN:
                Log.w(TAG, msg);
            case Log.INFO:
                Log.i(TAG, msg);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return _binder;
    }

    @Override
    public boolean isConnected()
    {
        log("isConnected");
        return true;
    }

    @Override
    public void registerOnConnection(Runnable callback)
    {
        log("registerOnConnection");
    }

    @Override
    public void registerEventListener(IGameEvents listener)
    {
        _eventListener = listener;
    }

    @Override
    public User getCurrentUser()
    {
        log("getCurrentUser");
        return _currentUser;
    }

    @Override
    public void login(User u)
    {
        log("login");
        _currentUser = u;
    }

    @Override
    public void joinGame()
    {
        log("joinGame");
        if (_eventListener != null)
        {
            _eventListener.onStartDraft();
        }
    }

    @Override
    public void joinGame(User... friends)
    {
        log("joinGame(friends)");
    }

    @Override
    public void draft(BuildingType tileOne, BuildingType tileTwo)
    {
        log("draft");
    }

    @Override
    public void placeTile(City placedOn, BuildingType tilePlaced, int x, int y)
    {
        log("placeTile");
    }

    @Override
    public void finishedPlacingTiles()
    {
        log("finishedPlacingTiles");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        log("onStartCommand");
        return START_STICKY;
    }

    public class DummyBinder extends Binder
    {
        public IGameService getService()
        {
            return DummyGameService.this;
        }
    }
}
