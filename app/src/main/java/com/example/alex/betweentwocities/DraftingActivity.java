package com.example.alex.betweentwocities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;

import com.example.alex.betweentwocities.databinding.ActivityDraftingBinding;
import com.example.b2c_core.BuildingType;
import com.example.b2c_core.DraftTransferObject;

import java.util.Arrays;

public class DraftingActivity extends GameBaseActivity
{
    ObservableArrayList<BuildingType> _draftableBuildings = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final ActivityDraftingBinding g = DataBindingUtil.setContentView(this, R.layout.activity_drafting);

        Intent caller = getIntent();
        DraftTransferObject dto =(DraftTransferObject) caller.getSerializableExtra(DraftTransferObject.class.toString());
        // TODO: copy tiles to view.

        _draftableBuildings.addAll(Arrays.asList(dto.availableTiles));

        g.setAvailableTiles(_draftableBuildings);
    }

}
