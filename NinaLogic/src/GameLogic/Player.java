package GameLogic;

import java.io.Serializable;

public class Player implements Serializable
{
    private final String name;
    private  int playerIndex;
    private int numOfMoves;
    private final PlayerType playerType;
    private boolean active = true;

    public void setPlayerIndex(int playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    public Player(String i_Name, PlayerType i_PlayerType)
    {
        name = i_Name;
        numOfMoves = 0;
        playerType = i_PlayerType;
    }


    public boolean isActive()
    {
        return active;
    }

    public void quit()
    {
        active = false;
    }


    public int getNumOfMoves()
    {
        return numOfMoves;
    }

    public int getIndex()
    {
        return playerIndex;
    }

    public String getName()
    {
        return name;
    }

    public PlayerType getPlayerType()
    {
        return playerType;
    }

    public void addNumOfMoves(int i_NumOfMovesToAdd)
    {
        numOfMoves += i_NumOfMovesToAdd;
    }

    public void resetPlayer()
    {
        numOfMoves =0;
        active =true;
    }
}
