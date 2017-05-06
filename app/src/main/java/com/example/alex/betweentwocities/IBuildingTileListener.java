package com.example.alex.betweentwocities;


import com.example.b2c_core.BuildingType;

/**
 * Created by alex on 4/2/17.
 */

public interface IBuildingTileListener
{
    boolean onTileChanged(BuildingType building, int xPos, int yPos);
}
