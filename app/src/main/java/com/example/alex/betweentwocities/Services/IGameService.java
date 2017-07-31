package com.example.alex.betweentwocities.Services;

import com.example.b2c_core.*;

public interface IGameService
{
    boolean isConnected();
    void registerOnConnection(Runnable callback);
    void registerEventListener(IGameEvents listener);
    User getCurrentUser();

    void login(User u);
    void joinGame();
    void joinGame(User... friends);
    void draft(BuildingType tileOne, BuildingType tileTwo);
    void placeTile(City placedOn, BuildingType tilePlaced, int x, int y);
    void finishedPlacingTiles();
}
