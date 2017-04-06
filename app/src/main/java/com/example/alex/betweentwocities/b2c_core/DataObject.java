package com.example.alex.betweentwocities.b2c_core;

import org.json.JSONObject;

/**
 * Created by alex on 4/6/17.
 */

public abstract class DataObject
{
    protected String _event;

    public abstract void fromJSON(JSONObject json);
    public abstract JSONObject toJSON();
}
