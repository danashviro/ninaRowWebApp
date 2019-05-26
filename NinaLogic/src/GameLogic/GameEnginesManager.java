package GameLogic;

import GameLogic.GameEngine;
import GameLogic.PlayerType;
import XsdClasses.GameDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class GameEnginesManager
{
    private List<GameEngine> m_AvailableGames = new ArrayList<>();
    private Map<String, PlayerType> m_Users = new LinkedHashMap<>();

    public synchronized boolean addUser(String i_Name, PlayerType i_Type)
    {
        if(m_Users.containsKey(i_Name))
            return false;
        else
        {
            m_Users.put(i_Name, i_Type);
            return true;
        }
    }

    public synchronized String addGame(StringBuilder i_XmlContent,String creatorName)
    {
        Reader reader = new StringReader(i_XmlContent.toString());
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            GameDescriptor gameDescriptor = (GameDescriptor) jaxbUnmarshaller.unmarshal(reader);
            checkGameSettings(gameDescriptor);
            m_AvailableGames.add(new GameEngineImpl(gameDescriptor,creatorName,m_AvailableGames.size()));
        }
        catch(JAXBException e)
        {
            return "Something went wrong with reading the file.";
        }
        catch(IncorrectGameSettingsException e)
        {
            return e.getMessage();
        }
        return "The game has uploaded successfully";
    }

    private void checkGameSettings(GameDescriptor i_GameDescriptor) throws IncorrectGameSettingsException
    {
        boolean throwException = false;
        String msg = "";
        String gameName = i_GameDescriptor.getDynamicPlayers().getGameTitle();
        int rows = i_GameDescriptor.getGame().getBoard().getRows();
        int cols = i_GameDescriptor.getGame().getBoard().getColumns().intValue();
        int targetSize = i_GameDescriptor.getGame().getTarget().intValue();
        int numOfPlayers = i_GameDescriptor.getDynamicPlayers().getTotalPlayers();

        if(isNameExist(gameName))
        {
            msg = "the game name already exist,please change the name."+System.lineSeparator();
            throwException = true;
        }
        if(rows < 5 || rows > 50)
        {
            msg += "The number of rows(" + rows + ") found in the file is invalid,the number should be between 5-50."+System.lineSeparator();
            throwException = true;
        }
        if(cols < 6 || cols > 30)
        {
            msg += "The number of cols(" + cols + ") found in the file is invalid,the number should be between 6-30."+System.lineSeparator();
            throwException = true;
        }
        if(targetSize >= cols || targetSize >= rows || targetSize < 2)
        {
            msg += "The target number(" + targetSize + ") should be smaller then the number of cols(" + Math.min(cols,30) + ") and the number of rows(" + Math.min(rows,50) + ")."+System.lineSeparator();
            throwException = true;
        }
        if(numOfPlayers > 6 || numOfPlayers < 2)
        {
            msg += "The num of Players(" + numOfPlayers + ") should be between 2-6)."+System.lineSeparator();
            throwException = true;
        }
        if(throwException)
        {
            throw new IncorrectGameSettingsException(msg);
        }
    }

    private boolean isNameExist(String gameName)
    {
        for(GameEngine game : m_AvailableGames)
        {
            if(game.getName().equals(gameName))
                return true;
        }
        return false;
    }


    public List<GameEngine> getAvailableGames()
    {
        return m_AvailableGames;
    }

    public Map<String, PlayerType> getUsers()
    {
        return m_Users;
    }

}
