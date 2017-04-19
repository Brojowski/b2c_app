package com.example.alex.betweentwocities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.b2c_core.BuildingType;
import com.example.b2c_core.DraftTransferObject;
import com.example.b2c_core.PostDraftTransferObject;

import java.util.ArrayList;
import java.util.Collections;

public class DraftingActivity extends PortraitActivity
{
    private IconManager _tileOptionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafting);

        _tileOptionsManager = new IconManager(this, R.id.available_tiles);

        Intent starter = getIntent();
        if (starter.hasExtra(DraftTransferObject.class.toString()))
        {
            DraftTransferObject draftData = (DraftTransferObject) starter.getSerializableExtra(DraftTransferObject.class.toString());

            ArrayList<BuildingType> draftingTiles = new ArrayList<>();
            Collections.addAll(draftingTiles, draftData.getTiles());

            IconManager.Mode layoutMode;
            switch (draftData.getTiles().length)
            {
                case 3:
                    layoutMode = IconManager.Mode.Draft3;
                    break;
                case 5:
                    layoutMode = IconManager.Mode.Draft5;
                    break;
                case 7:
                    layoutMode = IconManager.Mode.Draft7;
                    break;
                default:
                    layoutMode = IconManager.Mode.AllAvailable;
                    break;
            }
            _tileOptionsManager.setLayoutMode(layoutMode);
            _tileOptionsManager.setIcons(draftingTiles);
        }

        final BuildingTile draftedTile1 = (BuildingTile) findViewById(R.id.drafting_tile_1);
        final BuildingTile draftedTile2 = (BuildingTile) findViewById(R.id.drafting_tile_2);

        Button doneDraftingBtn = (Button) findViewById(R.id.done_drafting);
        doneDraftingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Continue.
                if (_bound)
                {
                    if (draftedTile1.getBuildingType() == BuildingType.Blank
                            || draftedTile2.getBuildingType() == BuildingType.Blank)
                    {
                        Toast.makeText(DraftingActivity.this, "Two tiles must be chosen.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PostDraftTransferObject draftResult = PostDraftTransferObject.create(
                            draftedTile1.getBuildingType(),
                            draftedTile2.getBuildingType());
                    _gameService.finishDraft(draftResult);
                }
                else
                {
                    Log.e(DraftingActivity.this.getClass().toString(), "Service not bound");
                }
            }
        });
    }
}
