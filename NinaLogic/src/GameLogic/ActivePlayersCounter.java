package GameLogic;

public class ActivePlayersCounter
{
    int numOfActivePlayers;
    public synchronized int getNumOfActivePlayers()
    {
        return numOfActivePlayers;
    }

    public synchronized void setNumOfActivePlayers(int i_NumOfActivePlayers)
    {
        numOfActivePlayers=i_NumOfActivePlayers;
    }
}
