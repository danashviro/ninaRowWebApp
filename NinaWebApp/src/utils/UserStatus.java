package utils;

public class UserStatus
{
    private boolean isUserConnected;
    private boolean inActiveGame;

    public String getConnectedUsername()
    {
        return connectedUsername;
    }

    public void setConnectedUsername(String connectedUsername)
    {
        this.connectedUsername = connectedUsername;
    }

    private String connectedUsername;

    public void setUserConnected(boolean userConnected)
    {
        isUserConnected = userConnected;
    }

    public void setInActiveGame(boolean inActiveGame)
    {
        this.inActiveGame = inActiveGame;
    }

    public boolean isUserConnected()
    {
        return isUserConnected;
    }

    public boolean isInActiveGame()
    {
        return inActiveGame;
    }
}
