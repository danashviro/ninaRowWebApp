package GameLogic;

public class RegularVictoryChecker extends VictoryChecker
{
    public RegularVictoryChecker(int i_TargetSize, Board i_Board)
    {
        super(i_TargetSize,i_Board);
    }

    @Override
    public boolean checkVictoryCondition(BoardCell i_Move)
    {
        return (checkDirectionVictory(i_Move, 1, 0) ||
                checkDirectionVictory(i_Move, 1, 1) ||
                checkDirectionVictory(i_Move, 0, 1) ||
                checkDirectionVictory(i_Move, -1, 1));
    }
}
