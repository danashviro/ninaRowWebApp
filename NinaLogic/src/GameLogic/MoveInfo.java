package GameLogic;

import java.util.*;
import java.util.List;

public class MoveInfo
{
    private List<BoardCell> changedCells = new ArrayList<>();


    public void addCell(BoardCell i_Move)
    {
        changedCells.add(i_Move);
    }

    public List<BoardCell> getMove()
    {
        return changedCells;
    }




}
