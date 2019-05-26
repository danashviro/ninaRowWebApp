package GameLogic;

public abstract class VictoryChecker
{
    protected int targetSize;
    protected Board board;

    public VictoryChecker(int i_TargetSize, Board i_Board)
    {
        this.targetSize = i_TargetSize;
        this.board = i_Board;
    }

    public abstract boolean checkVictoryCondition(BoardCell i_Move);

    protected boolean checkDirectionVictory(BoardCell i_Move , int i_RowDir, int i_ColDir)
    {
        int tempCol, tempRow;
        int count = 1;
        int PlayerSign = i_Move.getContent();
        tempCol = i_Move.getLocation().x - i_ColDir;
        tempRow = i_Move.getLocation().y - i_RowDir;

        while(board.isPointInBoardLimits(tempCol, tempRow) && board.getValueInBoard(tempRow,tempCol) == PlayerSign && count < targetSize)
        {
            count++;
            tempCol -= i_ColDir;
            tempRow -= i_RowDir;
        }

        tempCol = i_Move.getLocation().x + i_ColDir;
        tempRow = i_Move.getLocation().y + i_RowDir;
        while(board.isPointInBoardLimits(tempCol, tempRow) && board.getValueInBoard(tempRow,tempCol) == PlayerSign && count < targetSize)
        {
            count++;
            tempCol += i_ColDir;
            tempRow += i_RowDir;
        }

        return count == targetSize;
    }
}
