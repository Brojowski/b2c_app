package com.example.alex.betweentwocities;

import com.example.b2c_core.BuildingType;

/**
 * Created by alex on 4/28/17.
 */

public interface ITileUpdateListener
{
    void onTileUpdate(BuildingType tile, int x, int y);
}
