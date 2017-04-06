package com.example.alex.betweentwocities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alex.betweentwocities.b2c_core.BuildingType;

import java.util.ArrayList;

public class DraftingActivity extends PortraitActivity
{
    private IconManager _tileOptionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafting);

        _tileOptionsManager = new IconManager(this, R.id.available_tiles);
        _tileOptionsManager.setLayoutMode(IconManager.Mode.Draft7);

        ArrayList<BuildingType> draftingTiles = new ArrayList<>();
        draftingTiles.add(BuildingType.Tavern_Bed);
        draftingTiles.add(BuildingType.Shop);
        draftingTiles.add(BuildingType.House);
        draftingTiles.add(BuildingType.Park);
        draftingTiles.add(BuildingType.Factory);
        draftingTiles.add(BuildingType.Tavern_Music);
        draftingTiles.add(BuildingType.Office);
        _tileOptionsManager.setIcons(draftingTiles);

        Button doneDraftingBtn = (Button) findViewById(R.id.done_drafting);
        doneDraftingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Continue.
            }
        });
    }
}
