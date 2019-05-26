package GameLogic;

public class CircularVictoryChecker extends VictoryChecker
{

    public CircularVictoryChecker(int i_TargetSize, Board i_Board)
    {
        super(i_TargetSize, i_Board);
    }

    @Override
    public boolean checkVictoryCondition(BoardCell i_Move)
    {
        return (checkCircularDirectionVictory(i_Move, 1, 0) ||
                checkDirectionVictory(i_Move, 1, 1) ||
                checkCircularDirectionVictory(i_Move, 0, 1) ||
                checkDirectionVictory(i_Move, -1, 1));
    }

    private boolean checkCircularDirectionVictory(BoardCell i_Move, int i_RowDir, int i_ColDir)
    {
        int tempCol, tempRow;
        int count = 1;
        int PlayerSign = i_Move.getContent();

        tempCol = updateCircNumber(i_Move.getLocation().x, board.getCols(), -i_ColDir);
        tempRow = updateCircNumber(i_Move.getLocation().y, board.getRows(), -i_RowDir);
        while(board.getValueInBoard(tempRow, tempCol) == PlayerSign && count < targetSize)
        {
            count++;
            tempCol = updateCircNumber(tempCol, board.getCols(), -i_ColDir);
            tempRow = updateCircNumber(tempRow, board.getRows(), -i_RowDir);
        }

        tempCol = updateCircNumber(i_Move.getLocation().x, board.getCols(), i_ColDir);
        tempRow = updateCircNumber(i_Move.getLocation().y, board.getRows(), i_RowDir);
        while(board.getValueInBoard(tempRow, tempCol) == PlayerSign && count < targetSize)
        {
            count++;
            tempCol = updateCircNumber(tempCol, board.getCols(), i_ColDir);
            tempRow = updateCircNumber(tempRow, board.getRows(), i_RowDir);
        }

        return count == targetSize;
    }

    private int updateCircNumber(int i_Value, int i_Size, int i_Dir)
    {
        i_Value += i_Dir;
        if(i_Value >= i_Size)
            i_Value = 0;
        else if(i_Value < 0)
            i_Value = i_Size - 1;
        return i_Value;
    }
}
