package com.example.alex.betweentwocities;

import com.example.alex.betweentwocities.b2c_core.BuildingType;

/**
 * Created by alex on 4/2/17.
 */

public interface IBuildingTileListener
{
    void onTileChanged(BuildingType building, int xPos, int yPos);
}
