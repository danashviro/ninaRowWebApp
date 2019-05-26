package utils;

public class TurnInfo
{
    public boolean isTurnSuccess()
    {
        return turnSuccess;
    }

    public void setTurnSuccess(boolean turnSuccess)
    {
        this.turnSuccess = turnSuccess;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    private boolean turnSuccess;
    private String errorMessage;
}