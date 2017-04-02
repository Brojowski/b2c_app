package com.example.alex.betweentwocities;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

/**
 * Created by alex on 4/2/17.
 */

public abstract class CallbackOnDragListener implements View.OnDragListener
{
    @Override
    public final boolean onDrag(View v, DragEvent event)
    {
        if (onDragEvent(v, event) )
        {
            if (event.getAction() == DragEvent.ACTION_DROP
                && event.getLocalState() instanceof IDragCallback)
            {
                IDragCallback tile = (IDragCallback)event.getLocalState();
                Log.v(BuildingTile.class.toString(), "Has a drag callback.");
                tile.onDragComplete();
            }
            return true;
        }
        return false;
    }

    public abstract boolean onDragEvent(View v, DragEvent event);
}
