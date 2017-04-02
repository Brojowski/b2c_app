package com.example.alex.betweentwocities;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays one building in a representation of a city.
 */
public class BuildingTile extends AppCompatImageView implements IDragCallback
{
    private int _xGridPos;
    private int _yGridPos;
    private BuildingType _building;
    private boolean _canAcceptBuilding;
    private boolean _canMoveBuilding;
    private boolean _preventTileReplacement;
    private IBuildingTileListener _tileUpdateListener;

    public BuildingTile(Context context)
    {
        this(context, null);
    }

    public BuildingTile(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public BuildingTile(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        _building = BuildingType.Blank;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BuildingTile, 0, 0);

        try
        {
            _canAcceptBuilding = a.getBoolean(R.styleable.BuildingTile_canAcceptBuilding, true);
            _canMoveBuilding = a.getBoolean(R.styleable.BuildingTile_canRemoveBuilding, true);
            _preventTileReplacement = a.getBoolean(R.styleable.BuildingTile_preventTileReplacement, true);
        } finally
        {
            a.recycle();
        }

        this.setOnDragListener(new BuildingDragListener());
        this.setOnTouchListener(new BuildingRepositionTouchListener());
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // If the building is one of the types with an image,
        // draw that image, otherwise show nothing.
        int imageResource = BuildingResourceConverter.drawableFromBuilding(_building);
        if (imageResource != -1)
        {
            setImageDrawable(getContext().getDrawable(imageResource));
        }
        else
        {
            setImageDrawable(null);
        }
    }

    public BuildingType getBuildingType()
    {
        return _building;
    }

    private boolean canPlaceTile()
    {
        boolean canPlace = _canAcceptBuilding;
        if (_preventTileReplacement)
        {
            canPlace = canPlace && (_building == BuildingType.Blank);
        }
        return canPlace;
    }

    @Override
    public void onDragComplete()
    {
        _building = BuildingType.Blank;
        tileUpdated();
        invalidate();
    }

    public void setGridPos(int x, int y)
    {
        _xGridPos = x;
        _yGridPos = y;
    }

    public void setBuildingType(BuildingType buildingType)
    {
        _building = buildingType;
    }

    public void setCanAcceptBuildingFlag(boolean canAccept)
    {
        _canAcceptBuilding = canAccept;
    }

    public void setCanMoveBuildingFlag(boolean canMove)
    {
        _canMoveBuilding = canMove;
    }

    public void addTileUpdateListener(IBuildingTileListener listener)
    {
        _tileUpdateListener = listener;
    }

    private void tileUpdated()
    {
        if (_tileUpdateListener != null)
        {
            _tileUpdateListener.onTileChanged(_building, _xGridPos, _yGridPos);
        }
    }

    /**
     * A class to register when a BuildingIcon is dropped on it
     * and to update the building it represents.
     */
    private class BuildingDragListener extends CallbackOnDragListener
    {
        @Override
        public boolean onDragEvent(View v, DragEvent event)
        {
            int action = event.getAction();
            switch (action)
            {
                case DragEvent.ACTION_DROP:
                    if (canPlaceTile())
                    {
                        _building = BuildingResourceConverter.buildingFromClipData(event.getClipData());
                        tileUpdated();
                        invalidate();
                        return true;
                    }
                    else
                    {
                        Toast.makeText(getContext()
                                , "Can't "+(_building != BuildingType.Blank? "replace" : "place") + " building."
                                , Toast.LENGTH_SHORT)
                                .show();
                    }
                // TODO: Visual indication of if can drop.
            }
            return false;
        }
    }

    /**
     * A class that clears the stored building on a long click.
     */
    private class BuildingRepositionTouchListener implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            if (!_canMoveBuilding)
            {
                return false;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN
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
            return false;
        }
    }
}
