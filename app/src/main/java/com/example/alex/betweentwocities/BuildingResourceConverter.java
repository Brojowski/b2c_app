package com.example.alex.betweentwocities;

import android.content.ClipData;

import com.example.b2c_core.BuildingType;

/**
 * A static class for converting different data
 * representations of BuildingType between each
 * other.
 */
public class BuildingResourceConverter
{
    private static final String TAG = "BuildingType";

    private static final String STR_BLANK = "Blank";
    private static final String STR_FACTORY = "Factory";
    private static final String STR_HOUSE = "House";
    private static final String STR_OFFICE = "Office";
    private static final String STR_PARK = "Park";
    private static final String STR_SHOP = "Shop";
    private static final String STR_TAVERN_BED = "Tavern_Bed";
    private static final String STR_TAVERN_DRINK = "Tavern_Drink";
    private static final String STR_TAVERN_FOOD = "Tavern_Food";
    private static final String STR_TAVERN_MUSIC = "Tavern_Music";

    /**
     * Builds a ClipData for consistent parsing.
     * @param building BuildingType to transfer.
     * @return ClipData
     */
    public static ClipData clipFromBuilding(BuildingType building)
    {
        return ClipData.newPlainText(TAG, building.toString());
    }

    /**
     * Maps string data to the BuildingType
     * @param data string (i.e. ClipData.Item value)
     * @return BuildingType
     */
    public static BuildingType buildingFromStr(String data)
    {
        switch (data)
        {
            case STR_BLANK:
                return BuildingType.Blank;
            case STR_FACTORY:
                return BuildingType.Factory;
            case STR_HOUSE:
                return BuildingType.House;
            case STR_OFFICE:
                return BuildingType.Office;
            case STR_PARK:
                return BuildingType.Park;
            case STR_SHOP:
                return BuildingType.Shop;
            case STR_TAVERN_BED:
                return BuildingType.Tavern_Bed;
            case STR_TAVERN_DRINK:
                return BuildingType.Tavern_Drink;
            case STR_TAVERN_FOOD:
                return BuildingType.Tavern_Food;
            case STR_TAVERN_MUSIC:
                return BuildingType.Tavern_Music;
            default:
                return BuildingType.Blank;
        }
    }

    /**
     * Maps from BuildingType value to image.
     *
     * @param building the BuildingType.
     * @return an int identifying the drawable image.
     */
    public static int drawableFromBuilding(BuildingType building)
    {
        switch (building)
        {
            case Factory:
                return R.mipmap.ic_factory;
            case House:
                return R.mipmap.ic_house;
            case Office:
                return R.mipmap.ic_office;
            case Park:
                return R.mipmap.ic_park;
            case Shop:
                return R.mipmap.ic_shop_icon;
            case Tavern_Bed:
                return R.mipmap.ic_tavern_bed;
            case Tavern_Drink:
                return R.mipmap.ic_tavern_drink;
            case Tavern_Food:
                return R.mipmap.ic_tavern_food;
            case Tavern_Music:
                return R.mipmap.ic_tavern_music;
            default:
                return R.mipmap.ic_blank;
        }
    }

    /**
     * Maps from custom:building_type attribute value.
     *
     * @param layoutInt int from attribute
     * @return respective BuildingType
     */
    public static BuildingType buildingFromInt(int layoutInt)
    {
        switch (layoutInt)
        {
            case 1:
                return BuildingType.Factory;
            case 2:
                return BuildingType.House;
            case 3:
                return BuildingType.Office;
            case 4:
                return BuildingType.Park;
            case 5:
                return BuildingType.Shop;
            case 6:
                return BuildingType.Tavern_Bed;
            case 7:
                return BuildingType.Tavern_Drink;
            case 8:
                return BuildingType.Tavern_Food;
            case 9:
                return BuildingType.Tavern_Music;
            default:
                return BuildingType.Blank;
        }
    }

    public static BuildingType buildingFromClipData(ClipData data)
    {
        String buildingStr = data.getItemAt(0).getText().toString();
        String clipLabel = data.getDescription().getLabel().toString();

        if (clipLabel.equals(TAG))
        {
            return BuildingResourceConverter.buildingFromStr(buildingStr);
        }

        return BuildingType.Blank;
    }
}
