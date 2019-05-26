package GameLogic;

public class NoSpaceInRowException extends Exception
{
    public NoSpaceInRowException(int i_col)
    {
        super("This col(" + (i_col + 1) + ") is full,please enter another col");
    }
}
