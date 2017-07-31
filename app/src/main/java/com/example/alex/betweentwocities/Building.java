package com.example.alex.betweentwocities;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.b2c_core.BuildingType;

/**
 * Created by alex on 7/30/17.
 */

public class Building extends AppCompatImageView
{
    private BuildingType _buildingType = BuildingType.Blank;
    private boolean _canMove;
    private boolean _canReplace;
    private int _numberOfTiles;

    public Building(Context context)
    {
        super(context);
    }

    public Building(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Building(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Building, 0, 0);

        try
        {
            _canMove = a.getBoolean(R.styleable.Building_CanMove, false);
            _canReplace = a.getBoolean(R.styleable.Building_CanReplace, false);
            _numberOfTiles = a.getInt(R.styleable.Building_NumberOfTiles, 1);
            int tileNumber = a.getInt(R.styleable.Building_BuildingImage, 0);
            _buildingType = BuildingResourceConverter.buildingFromInt(tileNumber);
        } finally
        {
            a.recycle();
        }
    }

    public void setBuildingType(BuildingType buildingImage)
    {
        _buildingType = buildingImage;
        int drawableId = BuildingResourceConverter.drawableFromBuilding(_buildingType);
        Drawable image = getContext().getDrawable(drawableId);
        setImageDrawable(image);
    }
}
