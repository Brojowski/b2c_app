package com.example.alex.betweentwocities;

import android.graphics.Point;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to manage BuildingIcons and their configurations.
 */

public class IconManager
{
    public enum Mode
    {
        AllAvailable,
        Draft7,
        Draft5,
        Draft3,
        Place2
    }

    private static final int[][] ICON = new int[][]
            {
                    {
                            R.id.icon_1x1,
                            R.id.icon_1x2,
                            R.id.icon_1x3,
                            R.id.icon_1x4,
                            R.id.icon_1x5,
                            R.id.icon_1x6,
                            R.id.icon_1x7,
                    },
                    {
                            R.id.icon_2x1,
                            R.id.icon_2x2,
                            R.id.icon_2x3,
                            R.id.icon_2x4,
                    }
            };

    // If unlisted, defaults to on.
    private static Set<Point> CONFIG_ALL_OPEN;   // 5x2
    private static Set<Point> CONFIG_DRAFT_7;    // 7 line
    private static Set<Point> CONFIG_DRAFT_5;    // 5 line
    private static Set<Point> CONFIG_DRAFT_3;    // 3 line
    private static Set<Point> CONFIG_PLACE_2;
    private static ArrayList<BuildingType> ICONS_ALL;


    private Mode _iconLayoutMode = Mode.AllAvailable;
    private BuildingIcon[][] _iconViews;
    private ArrayList<BuildingType> _iconTypes;

    public IconManager(PortraitActivity context, int iconLayout)
    {
        init();
        generateBuildingIcons(context);
        GridLayout iconArea = (GridLayout)context.findViewById(iconLayout);
        iconArea.setOnDragListener(new RemoveFromBoardDropListener());
    }

    private void generateBuildingIcons(PortraitActivity context)
    {
        _iconViews = new BuildingIcon[2][];
        _iconViews[0] = new BuildingIcon[7];
        _iconViews[1] = new BuildingIcon[4];
        for (int y = 0; y < ICON.length; y++)
        {
            for (int x = 0; x < ICON[y].length; x++)
            {
                _iconViews[y][x] = (BuildingIcon) context.findViewById(ICON[y][x]);
                Log.v(IconManager.class.toString(), _iconViews[y][x].toString() + "[y:" + y + ",x:" + x + "]");
            }
        }
    }

    public void setLayoutMode(Mode layoutMode)
    {
        _iconLayoutMode = layoutMode;
        int numberTiles = 1;
        if (_iconLayoutMode == Mode.AllAvailable)
        {
            setIcons(ICONS_ALL);
            numberTiles = 17;
        }
        Set<Point> layoutSet = configFromMode(_iconLayoutMode);
        for (Point p : layoutSet)
        {
            Log.v(this.getClass().toString(),"Hiding BuildingIcon at [y:"+p.y+",x:"+p.x+"]");
            _iconViews[p.y - 1][p.x - 1].setVisibility(View.GONE);
            _iconViews[p.y - 1][p.x - 1].setNumberOfTiles(numberTiles);
        }
    }

    public void setIcons(ArrayList<BuildingType> icons)
    {
        _iconTypes = icons;
        Set<Point> offIcons = configFromMode(_iconLayoutMode);
        int max = _iconTypes.size();
        int i = 0;
        for (int y = 0; y < ICON.length; y++)
        {
            for (int x = 0; x < ICON[y].length; x++)
            {
                if (i < max && !offIcons.contains(new Point(x+1,y+1)))
                {
                    _iconViews[y][x].setBuildingType(_iconTypes.get(i));
                    i++;
                }
            }
        }
    }

    private static void init()
    {
        if (CONFIG_ALL_OPEN == null)
        {
            CONFIG_ALL_OPEN = new HashSet<>();
            CONFIG_ALL_OPEN.add(new Point(1,1));
            CONFIG_ALL_OPEN.add(new Point(7,1));
        }
        if (CONFIG_DRAFT_7 == null)
        {
            CONFIG_DRAFT_7 = new HashSet<>();
            // Bottom row off.
            CONFIG_DRAFT_7.add(new Point(1,2));
            CONFIG_DRAFT_7.add(new Point(2,2));
            CONFIG_DRAFT_7.add(new Point(3,2));
            CONFIG_DRAFT_7.add(new Point(4,2));
        }
        if (CONFIG_DRAFT_5 == null)
        {
            CONFIG_DRAFT_5 = new HashSet<>();
            // First
            CONFIG_DRAFT_5.add(new Point(1,1));
            // Last
            CONFIG_DRAFT_5.add(new Point(7,1));
            // Turns bottom row off
            CONFIG_DRAFT_5.add(new Point(1,2));
            CONFIG_DRAFT_5.add(new Point(2,2));
            CONFIG_DRAFT_5.add(new Point(3,2));
            CONFIG_DRAFT_5.add(new Point(4,2));
        }
        if (CONFIG_DRAFT_3 == null)
        {
            CONFIG_DRAFT_3 = new HashSet<>();
            // Top row
            CONFIG_DRAFT_3.add(new Point(1,1));
            CONFIG_DRAFT_3.add(new Point(2,1));
            CONFIG_DRAFT_3.add(new Point(6,1));
            CONFIG_DRAFT_3.add(new Point(7,1));
            // Turns bottom row off
            CONFIG_DRAFT_3.add(new Point(1,2));
            CONFIG_DRAFT_3.add(new Point(2,2));
            CONFIG_DRAFT_3.add(new Point(3,2));
            CONFIG_DRAFT_3.add(new Point(4,2));
        }
        if (CONFIG_PLACE_2 == null)
        {
            CONFIG_PLACE_2 = new HashSet<>();
            // Top row
            CONFIG_PLACE_2.add(new Point(1,1));
            CONFIG_PLACE_2.add(new Point(2,1));
            CONFIG_PLACE_2.add(new Point(4,1));
            CONFIG_PLACE_2.add(new Point(6,1));
            CONFIG_PLACE_2.add(new Point(7,1));
            // Turns bottom row off
            CONFIG_PLACE_2.add(new Point(1,2));
            CONFIG_PLACE_2.add(new Point(2,2));
            CONFIG_PLACE_2.add(new Point(3,2));
            CONFIG_PLACE_2.add(new Point(4,2));
        }
        if (ICONS_ALL == null)
        {
            ICONS_ALL = new ArrayList<>();
            ICONS_ALL.add(BuildingType.Shop);
            ICONS_ALL.add(BuildingType.Factory);
            ICONS_ALL.add(BuildingType.House);
            ICONS_ALL.add(BuildingType.Office);
            ICONS_ALL.add(BuildingType.Park);
            ICONS_ALL.add(BuildingType.Tavern_Bed);
            ICONS_ALL.add(BuildingType.Tavern_Drink);
            ICONS_ALL.add(BuildingType.Tavern_Food);
            ICONS_ALL.add(BuildingType.Tavern_Music);
        }
    }

    private static Set<Point> configFromMode(Mode displayMode)
    {
        switch (displayMode)
        {
            case AllAvailable:
                return CONFIG_ALL_OPEN;
            case Draft7:
                return CONFIG_DRAFT_7;
            case Draft5:
                return CONFIG_DRAFT_5;
            case Draft3:
                return CONFIG_DRAFT_3;
            case Place2:
                return CONFIG_PLACE_2;
        }
        return null;
    }

    private class RemoveFromBoardDropListener extends CallbackOnDragListener
    {

        @Override
        public boolean onDragEvent(View v, DragEvent event)
        {
            Set<Point> offIcons = configFromMode(_iconLayoutMode);
            if (event.getAction() == DragEvent.ACTION_DROP)
            {
                BuildingType returnedTile = BuildingResourceConverter.buildingFromClipData(event.getClipData());
                for (int y = 0; y < _iconViews.length; y++)
                {
                    for (int x = 0; x < _iconViews[y].length; x++)
                    {
                        if (!offIcons.contains(new Point(x+1,y+1))
                                && _iconViews[y][x].getBuildingType() == BuildingType.Blank)
                        {
                            _iconViews[y][x].setBuildingType(returnedTile);
                            _iconViews[y][x].setNumberOfTiles(1);
                            return true;
                        }
                    }
                }
            }
            return true;
        }
    }
}
