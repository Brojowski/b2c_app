package com.example.alex.betweentwocities;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.b2c_core.DraftTransferObject;
import com.example.b2c_core.PostDraftTransferObject;
import com.example.b2c_core.Routes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.EngineIOException;


/**
 * Created by alex on 4/6/17.
 */

public class GameService extends Service
{
    private final IBinder _gameServiceBinder = new GameServiceBinder();
    private static final String url = "http://10.1.1.191:8000";
    private Socket _socket;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GameService()
    {
        Log.v(this.getClass().toString(), "Start GameService.");
        init();
        registerServerEvents();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return _gameServiceBinder;
    }

    private void registerServerEvents()
    {
        _socket.on(Routes.FromServer.BEGIN_DRAFT, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                for (Object o : args)
                {
                    ObjectMapper mapper = new ObjectMapper();
                    try
                    {
                        DraftTransferObject c = mapper.readValue(o.toString(), DraftTransferObject.class);
                        Log.v(this.getClass().toString(), c.toString());

                        Intent notificationIntent = new Intent(GameService.this, DraftingActivity.class);
                        notificationIntent.putExtra(DraftTransferObject.class.toString(), c);
                        startActivity(notificationIntent);

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void joinGame()
    {
        Log.v(this.getClass().toString(), "Trying to join game.");
        _socket.emit(Routes.ToServer.JOIN_GAME, "{\"uname\":\"test\"}");
    }

    public void finishDraft(PostDraftTransferObject draftResult)
    {
        Log.v(this.getClass().toString(), "Finishing draft.");

        ObjectMapper m = new ObjectMapper();
        try
        {
            _socket.emit(Routes.ToServer.DRAFT_COMPLETE, m.writeValueAsString(draftResult));
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
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
                for (Object arg : args)
                {
                    EngineIOException e = (EngineIOException) arg;
                    Log.e(GameService.this.getClass().toString() + " code: ", "" + e.code);
                    Log.e(GameService.this.getClass().toString() + " transport: ", "" + e.transport);
                }
            }
        }).on(Socket.EVENT_ERROR, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                Log.v(GameService.this.getClass().toString() + " EVENT_ERROR", args.toString());
            }
        });

        _socket.connect();
        Log.v(this.getClass().toString(), "Is connected: " + _socket.connected());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    public class GameServiceBinder extends Binder
    {
        public GameService getService()
        {
            return GameService.this;
        }
    }
}
