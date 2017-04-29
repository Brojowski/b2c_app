package com.example.alex.betweentwocities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.b2c_core.BuildingType;
import com.example.b2c_core.PlaceTransferObject;
import com.example.b2c_core.SharedCity;
import com.example.b2c_core.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlaceActivity extends PortraitActivity implements IBoardUpdateListener
{
    private BoardManager _leftBoard;
    private BoardManager _rightBoard;
    private BoardManager _otherBoard;
    private User _currentUser;

    /**
     * The user is always the board's left player.
     */
    private Map<User, BoardManager> _boards = new HashMap<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        TextView leftLabel = (TextView) findViewById(R.id.left_tile_label);
        TextView rightLabel = (TextView) findViewById(R.id.right_tile_label);

        _leftBoard = new BoardManager(this, R.id.left_board);
        _rightBoard = new BoardManager(this, R.id.right_board);
        _otherBoard = new BoardManager(this, R.id.other_board);

        IconManager left = new IconManager(this, R.id.left_icons);
        IconManager center = new IconManager(this, R.id.center_icons);
        IconManager right = new IconManager(this, R.id.right_icons);

        left.setLayoutMode(IconManager.Mode.Place2);
        center.setLayoutMode(IconManager.Mode.Place2);
        right.setLayoutMode(IconManager.Mode.Place2);

        Intent starter = getIntent();
        if (starter.hasExtra(PlaceTransferObject.class.toString()))
        {
            PlaceTransferObject transferObject = (PlaceTransferObject) starter.getSerializableExtra(PlaceTransferObject.class.toString());

            _currentUser = transferObject.currentUser;

            final SharedCity leftCity = transferObject.leftCity;
            final SharedCity rightCity = transferObject.rightCity;

            _leftBoard.onTileUpdate(new ITileUpdateListener()
            {
                @Override
                public void onTileUpdate(BuildingType tile, int x, int y)
                {
                    PlaceActivity.this.onTileUpdate(leftCity, tile, x, y);
                }
            });
            _rightBoard.onTileUpdate(new ITileUpdateListener()
            {
                @Override
                public void onTileUpdate(BuildingType tile, int x, int y)
                {
                    PlaceActivity.this.onTileUpdate(rightCity, tile, x, y);
                }
            });

            _leftBoard.setCity(leftCity.getCity());
            _rightBoard.setCity(rightCity.getCity());
            _otherBoard.setCity(transferObject.otherCity.getCity());

            _boards.put(transferObject.leftCity.getLeftPlayer(), _leftBoard);
            _boards.put(transferObject.rightCity.getLeftPlayer(), _rightBoard);
            _boards.put(transferObject.rightCity.getRightPlayer(), _otherBoard);

            Map<User, BuildingType[]> tiles = transferObject.tiles;
            left.setIcons(getTiles(transferObject.leftCity.getLeftPlayer(), tiles));
            center.setIcons(getTiles(transferObject.currentUser, tiles));
            right.setIcons(getTiles(transferObject.rightCity.getRightPlayer(), tiles));

            leftLabel.setText(transferObject.leftCity.getLeftPlayer().getUsername());
            rightLabel.setText(transferObject.rightCity.getRightPlayer().getUsername());
        }

        Button donePlacingBtn = (Button) findViewById(R.id.btn_place_complete);
        donePlacingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Logic to make sure tiles played correctly.
                if (_bound)
                {
                    _gameService.placeComplete();
                }
            }
        });
    }

    private ArrayList<BuildingType> getTiles(User player, Map<User, BuildingType[]> tiles)
    {
        ArrayList<BuildingType> leftTiles = new ArrayList<>();
        BuildingType[] playersTiles = tiles.get(player);
        if (playersTiles != null)
        {
            Collections.addAll(leftTiles, playersTiles);
        }
        return leftTiles;
    }


    @Override
    protected void onGameServiceConnection()
    {
        _gameService.addBoardUpdateListener(this);
    }

    @Override
    public void onBoardUpdate(SharedCity sharedCity)
    {
        Log.v(this.getClass().toString(), "BoardUpdate");
        BoardManager boardToUpdate = _boards.get(sharedCity.getLeftPlayer());
        if (boardToUpdate != null)
        {
            boardToUpdate.setCity(sharedCity.getCity());
        }
    }

    public void onTileUpdate(SharedCity updatedCity, BuildingType tile, int x, int y)
    {
        // TODO: send tile updates to server.
        if (_bound)
        {
            _gameService.placeTile(_currentUser, tile, updatedCity, x, y);
        }
    }
}
