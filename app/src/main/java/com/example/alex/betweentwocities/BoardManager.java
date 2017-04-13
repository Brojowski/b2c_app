package com.example.alex.betweentwocities;

import android.util.Log;

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

    public BoardManager(PortraitActivity boardContext)
    {
        this(boardContext,new City());
    }

    public BoardManager(PortraitActivity boardContext, City existingCity)
    {
        _city = existingCity;
        init(boardContext);
    }

    private void init(PortraitActivity boardContext)
    {
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                _displayTiles[y][x] = (BuildingTile) boardContext.findViewById(GRID_IDS[y][x]);
                Log.v(BoardManager.class.toString(),_displayTiles[y][x].toString()+ "[y:"+y+",x:"+x+"]");
                _displayTiles[y][x].setGridPos(x, y);
                _displayTiles[y][x].addTileUpdateListener(this);
                _displayTiles[y][x].setBuildingType(_city.getBuildingAt(x, y));
            }
        }
    }

    @Override
    public void onTileChanged(BuildingType building, int xPos, int yPos)
    {
        Log.v(BoardManager.class.toString(), "Building change to " + building.toString() + " at [" + xPos + "," + yPos + "].");
        _city.tryAddTile(building, xPos, yPos);
    }

    public City getCity()
    {
        return _city;
    }
}
