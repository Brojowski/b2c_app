package com.example.alex.betweentwocities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b2c_core.BuildingType;
import com.example.b2c_core.DraftTransferObject;

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
        _tileOptionsManager.setLayoutMode(IconManager.Mode.Draft7);

        Intent starter = getIntent();
        if (starter.hasExtra(DraftTransferObject.class.toString()))
        {
            DraftTransferObject draftData = (DraftTransferObject) starter.getSerializableExtra(DraftTransferObject.class.toString());

            ArrayList<BuildingType> draftingTiles = new ArrayList<>();
            Collections.addAll(draftingTiles, draftData.getTiles());

            _tileOptionsManager.setIcons(draftingTiles);
        }

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
