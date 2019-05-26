package utils;


import GameLogic.GameEngine;
import GameLogic.GameEnginesManager;

import javax.servlet.ServletContext;
import java.util.List;

public class ServeltsUtils
{

    public static GameEnginesManager getGamesManager(ServletContext i_Context)
    {
        if(i_Context.getAttribute("gamesManager") == null)
            i_Context.setAttribute("gamesManager", new GameEnginesManager());
        return (GameEnginesManager) i_Context.getAttribute("gamesManager");
    }


    public static GameEngine getGameByIndex(ServletContext i_Context, int gameIndex)
    {
        return getGamesManager(i_Context).getAvailableGames().get(gameIndex);
    }
}
