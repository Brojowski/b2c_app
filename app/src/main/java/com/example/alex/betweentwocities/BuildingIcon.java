package com.example.alex.betweentwocities;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.alex.betweentwocities.b2c_core.BuildingType;

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
        } finally
        {
            a.recycle();
        }

        _building = BuildingResourceConverter.buildingFromInt(strBuildingType);
        updateDrawable();
    }

    public void setBuildingType(BuildingType type)
    {
        _building = type;
        invalidate();
    }

    public BuildingType getBuildingType()
    {
        return _building;
    }

    public int getNumberBuildings()
    {
        return _numberOfTiles;
    }

    public void setNumberOfTiles(int number)
    {
        _numberOfTiles = number;
    }

    private void updateDrawable()
    {
        int drawableResource = BuildingResourceConverter.drawableFromBuilding(_building);
        if (drawableResource != -1)
        {
            setImageDrawable(getContext().getDrawable(drawableResource));
        }
        else
        {
            setImageDrawable(null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        updateDrawable();
        super.onDraw(canvas);
    }

    @Override
    public void onDragComplete()
    {
        _numberOfTiles--;
        if (_numberOfTiles <= 0)
        {
            _building = BuildingType.Blank;
            invalidate();
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
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
                    && _building != BuildingType.Blank)
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

