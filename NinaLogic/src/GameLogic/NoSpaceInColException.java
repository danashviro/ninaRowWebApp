package GameLogic;

public class NoSpaceInColException extends Exception
{
    public NoSpaceInColException(int i_col)
    {
        super("This col(" + (i_col + 1) + ") is full, please enter another col");
    }
}
