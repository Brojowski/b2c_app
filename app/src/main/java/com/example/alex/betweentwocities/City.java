package com.example.alex.betweentwocities;

import java.io.Serializable;

/**
 * Data representation of a city.
 */
public class City implements Serializable
{
    private BuildingType[][] _layout = new BuildingType[4][4];

    private int _score = 0;
    private boolean _scoreUpToDate = false;


    public City()
    {
        for (BuildingType[] row : _layout)
        {
            for (int i = 0; i < row.length; i++)
            {
                row[i] = BuildingType.Blank;
            }
        }
    }

    public City(BuildingType[][] layout)
    {
        _layout = layout;
    }

    public BuildingType[][] getCityLayout()
    {
        return _layout.clone();
    }

    public void setScore(int score)
    {
        _score = score;
        _scoreUpToDate = true;
    }

    public boolean tryShiftUp()
    {
        if (!canShiftUp())
        {
            // There are tiles in the way.
            return false;
        }

        // Transfer tiles up from rows 2-4 (indexes 1-3)
        for (int y = 1; y < _layout.length; y++)
        {
            for (int x = 0; x < _layout[y].length; x++)
            {
                _layout[y - 1][x] = _layout[y][x];
            }
        }

        //Blank out the last row.
        for (int x = 0; x < _layout[3].length; x++)
        {
            _layout[3][x] = BuildingType.Blank;
        }
        return true;
    }

    public boolean tryShiftDown()
    {
        if (!canShiftDown())
        {
            // There are tiles in the way.
            return false;
        }

        // Transfer tiles up from rows 1-3 (indexes 0-2)
        for (int y = _layout.length - 1; y > 0; y--)
        {
            for (int x = 0; x < _layout[y].length; x++)
            {
                _layout[y][x] = _layout[y - 1][x];
            }
        }

        //Blank out the last row.
        for (int x = 0; x < _layout[0].length; x++)
        {
            _layout[0][x] = BuildingType.Blank;
        }
        return true;
    }

    public boolean tryShiftLeft()
    {
        if (!canShiftLeft())
        {
            return false;
        }

        for (int x= 1; x < _layout.length; x++)
        {
            for (int y = 0; y < _layout[x].length; y++)
            {
                _layout[y][x-1] = _layout[y][x];
            }
        }

        // Blank out right column.
        for (int i = 0; i < _layout.length; i++)
        {
            _layout[i][3] = BuildingType.Blank;
        }
        return true;
    }

    public boolean tryShiftRight()
    {
        if (!canShiftRight())
        {
            return true;
        }

        for (int x = _layout.length - 1; x > 0; x--)
        {
            for (int y = 0; y < _layout[x].length; y++)
            {
                _layout[y][x] = _layout[y][x-1];
            }
        }

        // Blank out left column.
        for (int i = 0; i < _layout.length; i++)
        {
            _layout[i][0] = BuildingType.Blank;
        }
        return true;
    }

    public boolean canShiftUp()
    {
        for (int i = 0; i < _layout[0].length; i++)
        {
            if (_layout[0][i] != BuildingType.Blank)
            {
                return false;
            }
        }
        return true;
    }

    public boolean canShiftDown()
    {
        for (int i = 0; i < _layout[3].length; i++)
        {
            if (_layout[3][i] != BuildingType.Blank)
            {
                return false;
            }
        }
        return true;
    }

    public boolean canShiftLeft()
    {
        for (int i = 0; i < _layout.length; i++)
        {
            if (_layout[i][0] != BuildingType.Blank)
            {
                return false;
            }
        }
        return true;
    }

    public boolean canShiftRight()
    {
        for (int i = 0; i < _layout.length; i++)
        {
            if (_layout[i][3] != BuildingType.Blank)
            {
                return false;
            }
        }
        return true;
    }

    public boolean canAddTile(int x, int y)
    {
        return inBounds(x,y) && _layout[y][x] == BuildingType.Blank;
    }

    private boolean inBounds(int x, int y)
    {
        return x >= 0
                && x < 4
                && y >=0
                && y < 4;
    }

    public boolean tryAddTile(BuildingType tile, int x, int y)
    {
        if (canAddTile(x,y))
        {
            _layout[y][x] = tile;
            _scoreUpToDate = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < _layout.length; i++)
        {
            String formatString = "[%-12s][%-12s][%-12s][%-12s]\n";
            output.append(String.format(formatString, _layout[i]));
        }
        return output.toString();
    }

}
