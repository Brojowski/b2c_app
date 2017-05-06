package com.example.alex.betweentwocities;

import android.util.Log;
import android.view.View;

import com.example.b2c_core.BuildingType;
import com.example.b2c_core.City;

/**
 * Created by alex on 4/2/17.
 */

public class BoardManager implements IBuildingTileListener
{
    private static int[][] GRID_IDS = {
            { // y = 1
                    R.id.buildingPos_1x1,
                    R.id.buildingPos_1x2,
                    R.id.buildingPos_1x3,
                    R.id.buildingPos_1x4
            },
            {
                    R.id.buildingPos_2x1,
                    R.id.buildingPos_2x2,
                    R.id.buildingPos_2x3,
                    R.id.buildingPos_2x4
            },
            {
                    R.id.buildingPos_3x1,
                    R.id.buildingPos_3x2,
                    R.id.buildingPos_3x3,
                    R.id.buildingPos_3x4
            },
            {
                    R.id.buildingPos_4x1,
                    R.id.buildingPos_4x2,
                    R.id.buildingPos_4x3,
                    R.id.buildingPos_4x4
            }
    };
    private BuildingTile[][] _displayTiles = new BuildingTile[4][4];
    private City _city;
    private View _boardBase;
    private ITileUpdateListener _updateListener;
    private boolean _allowRandomPlace = false;

    public BoardManager(PortraitActivity boardContext)
    {
        this(boardContext.findViewById(android.R.id.content), new City());
    }

    public BoardManager(PortraitActivity boardContext, City existingCity)
    {
        this(boardContext.findViewById(android.R.id.content), existingCity);
    }

    public BoardManager(View boardContext, City existingCity)
    {
        _city = existingCity;
        _boardBase = boardContext;
        init();
    }

    public BoardManager(PortraitActivity boardContext, int boardBaseView)
    {
        this(boardContext.findViewById(boardBaseView), new City());
    }

    private void init()
    {
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                _displayTiles[y][x] = (BuildingTile) _boardBase.findViewById(GRID_IDS[y][x]);
                Log.v(BoardManager.class.toString(), _displayTiles[y][x].toString() + "[y:" + y + ",x:" + x + "]");
                _displayTiles[y][x].setGridPos(x, y);
                _displayTiles[y][x].addTileUpdateListener(this);
                _displayTiles[y][x].setBuildingType(_city.getBuildingAt(x, y));
            }
        }
    }

    @Override
    public boolean onTileChanged(BuildingType building, int xPos, int yPos)
    {
        Log.v(BoardManager.class.toString(), "Building change to " + building.toString() + " at [" + xPos + "," + yPos + "].");
        boolean placeSuccess;
        if (_allowRandomPlace)
        {
            placeSuccess = _city.tryAddTile(building, xPos, yPos);
        }
        else
        {
            placeSuccess = _city.tryPlaceTile(building, xPos, yPos);
        }
        if (_updateListener != null)
        {
            _updateListener.onTileUpdate(building, xPos, yPos);
        }
        return placeSuccess;
    }

    public void allowRandomPlace(boolean randomPlace)
    {
        _allowRandomPlace = randomPlace;
    }

    public City getCity()
    {
        return _city;
    }

    public void setCity(City city)
    {
        _city = city;
        init();
    }

    public void onTileUpdate(ITileUpdateListener iTileUpdateListener)
    {
        _updateListener = iTileUpdateListener;
    }
}
