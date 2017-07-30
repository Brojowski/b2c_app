package com.example.alex.betweentwocities;

import com.example.b2c_core.*;

public interface IGameService
{
    boolean isConnected();
    void registerOnConnection(Runnable callback);
    User getCurrentUser();

    void login(User u);
    void joinGame();
    void joinGame(User... friends);
    void draft(BuildingType tileOne, BuildingType tileTwo);
    void placeTile(City placedOn, BuildingType tilePlaced, int x, int y);
    void finishedPlacingTiles();
}
