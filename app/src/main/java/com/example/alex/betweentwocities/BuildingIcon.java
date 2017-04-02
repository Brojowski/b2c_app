package com.example.alex.betweentwocities;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Class for draggable icons used populate a city.
 */
public class BuildingIcon extends AppCompatImageView implements IDragCallback
{
    private BuildingType _building;
    private int _numberOfTiles;

    public BuildingIcon(Context context)
    {
        this(context, null);
    }

    public BuildingIcon(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BuildingIcon(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.setOnTouchListener(new BuildingIconTouchListener());
        _building = BuildingType.Blank;

        // Read the custom attribute for city type.
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BuildingIcon,
                0, 0);

        int strBuildingType;
        try
        {
            strBuildingType = a.getInt(R.styleable.BuildingIcon_buildingType, 0);
            _numberOfTiles = a.getInteger(R.styleable.BuildingIcon_numberOfIcons, 16);
        } finally
        {
            a.recycle();
        }

        _building = BuildingResourceConverter.buildingFromInt(strBuildingType);
        int drawableResource = BuildingResourceConverter.drawableFromBuilding(_building);
        if (drawableResource != -1)
        {
            setImageDrawable(context.getDrawable(drawableResource));
        }
        else
        {
            setImageDrawable(null);
        }
    }

    @Override
    public void onDragComplete()
    {
        _numberOfTiles--;
        if (_numberOfTiles <= 0)
        {
            this.setVisibility(GONE);
        }
    }

    /**
     * Touch listener that transfers BuildingType to the drop location.
     */
    private final class BuildingIconTouchListener implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            {
                ClipData data = BuildingResourceConverter.clipFromBuilding(_building);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                if (Build.VERSION.SDK_INT >= 24)
                {
                    view.startDragAndDrop(data, shadowBuilder, view, 0);
                }
                else
                {
                    view.startDrag(data, shadowBuilder, view, 0);
                }
                return true;
            }
            else
            {
                return false;
            }
        }
    }

}

