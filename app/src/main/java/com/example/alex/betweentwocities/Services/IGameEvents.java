package com.example.alex.betweentwocities.Services;

import com.example.b2c_core.DraftTransferObject;
import com.example.b2c_core.PlaceTileTransferObject;
import com.example.b2c_core.PlaceTransferObject;

/**
 * Created by alex on 7/30/17.
 */

public interface IGameEvents
{
    void onStartDraft(DraftTransferObject dto);
    void onStartPlace(PlaceTransferObject pto);
    void onBoardUpdate(PlaceTileTransferObject ptto);
}
