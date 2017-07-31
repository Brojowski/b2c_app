package com.example.alex.betweentwocities;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.ListenerUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.b2c_core.BuildingType;

/**
 * Created by alex on 7/30/17.
 */

public class BindingTools
{
    @BindingAdapter({"entries", "layout"})
    public static <T> void setEntries(ViewGroup viewGroup,
                                      ObservableList<T> entries, int layoutId) {
        viewGroup.removeAllViews();
        if (entries != null)
        {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (T item: entries)
            {
                ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, true);
                binding.setVariable(BR.tile, item);
            }
        }
    }
}
