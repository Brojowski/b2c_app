package com.example.alex.betweentwocities;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.b2c_core.BuildingType;
import com.example.b2c_core.City;
import com.example.b2c_core.DraftTransferObject;
import com.example.b2c_core.PlaceTileTransferObject;
import com.example.b2c_core.PlaceTransferObject;
import com.example.b2c_core.PostDraftTransferObject;
import com.example.b2c_core.Routes;
import com.example.b2c_core.SharedCity;
import com.example.b2c_core.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.EngineIOException;


/**
 * Created by alex on 4/6/17.
 */

public class GameService extends Service implements IGameService
{
    private final IBinder _gameServiceBinder = new GameServiceBinder();
    private static final String url = "http://10.1.1.191:8000";
    private Socket _socket;
    private Runnable _connectionCallback;
    private IBoardUpdateListener _updateReciever;
    private Map<User, SharedCity> _cityUpdates = new HashMap<>();

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
                if (_connectionCallback != null)
                {
                    _connectionCallback.run();
                }
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
    private void registerServerEvents()
    {
        _socket.on(Routes.FromServer.BEGIN_DRAFT, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                ObjectMapper mapper = new ObjectMapper();
                try
                {
                    DraftTransferObject c = mapper.readValue(args[0].toString(), DraftTransferObject.class);
                    Log.v(this.getClass().toString(), c.toString());

                    //Intent notificationIntent = new Intent(GameService.this, DraftingActivity.class);
                    //notificationIntent.putExtra(DraftTransferObject.class.toString(), c);
                    //startActivity(notificationIntent);

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).on(Routes.FromServer.BEGIN_PLACE, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                ObjectMapper mapper = new ObjectMapper();
                try
                {
                    PlaceTransferObject c = mapper.readValue(args[0].toString(), PlaceTransferObject.class);
                    Log.v(this.getClass().toString(), c.toString());

                    //Intent notificationIntent = new Intent(GameService.this, PlaceActivity.class);
                    //notificationIntent.putExtra(PlaceTransferObject.class.toString(), c);
                    //startActivity(notificationIntent);

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).on(Routes.FromServer.BOARD_UPDATE, new Emitter.Listener()
        {
            @Override
            public void call(Object... args)
            {
                Log.v(GameService.this.getClass().toString(), Routes.FromServer.BOARD_UPDATE);
                ObjectMapper mapper = new ObjectMapper();
                if (args.length < 1)
                {
                    return;
                }
                try
                {
                    SharedCity city = mapper.readValue(args[0].toString(), SharedCity.class);
                    _cityUpdates.put(city.getLeftPlayer(), city);

                    if (_updateReciever != null)
                    {
                        emptyBoardUpdates();
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean isConnected()
    {
        return _socket.connected();
    }

    @Override
    public void registerOnConnection(Runnable callback)
    {
        _connectionCallback = callback;
    }

    @Override
    public User getCurrentUser()
    {
        return null;
    }

    @Override
    public void login(User u)
    {

    }

    public void joinGame()
    {
        Log.v(this.getClass().toString(), "Trying to join game.");
        _socket.emit(Routes.ToServer.JOIN_GAME, "{\"uname\":\"test\"}");
    }

    @Override
    public void joinGame(User... friends)
    {

    }

    @Override
    public void draft(BuildingType tileOne, BuildingType tileTwo)
    {

    }

    @Override
    public void placeTile(City placedOn, BuildingType tilePlaced, int x, int y)
    {

    }

    @Override
    public void finishedPlacingTiles()
    {

    }

    private void finishDraft(PostDraftTransferObject draftResult)
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

    private void emptyBoardUpdates()
    {
        Iterator<Map.Entry<User, SharedCity>> updates = _cityUpdates.entrySet().iterator();
        while (updates.hasNext())
        {
            _updateReciever.onBoardUpdate(updates.next().getValue());
            updates.remove();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    public class GameServiceBinder extends Binder
    {
        public IGameService getService()
        {
            return GameService.this;
        }
    }
}
