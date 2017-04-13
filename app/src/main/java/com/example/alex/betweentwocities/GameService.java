package com.example.alex.betweentwocities;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.EngineIOException;


/**
 * Created by alex on 4/6/17.
 */

public class GameService extends IntentService
{
    private static final String url = "http://10.1.1.191:8000";
    private Socket _socket;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GameService()
    {
        super(GameService.class.toString());
        Log.v(this.getClass().toString(), "Start GameService.");
        init();
        registerServerEvents();
    }

    private void registerServerEvents()
    {
/*        _socket.on(Routes.FromServer.BEGIN_DRAFT, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {

            }
        });*/
    }

    private void joinGame()
    {
/*
        _socket.emit(Routes.ToServer.JOIN_GAME,"{\"uname\":\"test\"}");
*/
    }

    private void init()
    {
        try
        {
            _socket = IO.socket(url);
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
            return;
        }

        _socket.on(Socket.EVENT_CONNECT, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                Log.v(GameService.this.getClass().toString(), "Socket Connected.");

                //TODO: temp
                joinGame();
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                Log.e(GameService.this.getClass().toString(), "Socket Disconnected.");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                for (Object arg: args)
                {
                    EngineIOException e = (EngineIOException) arg;
                    Log.e(GameService.this.getClass().toString() + " code: ",""+e.code);
                    Log.e(GameService.this.getClass().toString() + " transport: ",""+e.transport);
                }
            }
        }).on(Socket.EVENT_ERROR, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                Log.v(GameService.this.getClass().toString()+" EVENT_ERROR", args.toString());
            }
        });

        _socket.connect();
        Log.v(this.getClass().toString(), "Is connected: " + _socket.connected());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {

    }
}
