package GameLogic;

import java.awt.*;

public class BoardCell
{
    private Point location;
    private int content;

    BoardCell(Point i_Location, int i_Content)
    {
        location = i_Location;
        content = i_Content;
    }

    public Point getLocation()
    {
        return location;
    }

    public void setLocation(Point i_Location)
    {
        this.location = i_Location;
    }

    public int getContent()
    {
        return content;
    }

    public void setContent(int i_Content)
    {
        this.content = i_Content;
    }
}
