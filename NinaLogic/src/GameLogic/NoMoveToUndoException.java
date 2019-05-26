package GameLogic;

public class NoMoveToUndoException extends Exception
{
    public NoMoveToUndoException()
    {
        super("There are no moves to undo at this point.");
    }
}
