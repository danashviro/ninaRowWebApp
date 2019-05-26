package GameLogic;

import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game implements Serializable
{
    private ActivePlayersCounter activePlayersCounter;
    private final Board m_Board;
    private GameType m_GameType;
    private final List<Player> m_Players;
    private Player m_CurrentPlayer;
    private final int m_TargetSize;
    private VictoryChecker m_VictoryChecker;
    private boolean isHaveAWinner = false;
    private Set<Player> winners = new HashSet<>();
    private boolean isBoardFull = false;

    public Game(Board i_Board, List<Player> i_Players, int i_TargetSize, GameType i_GameType, ActivePlayersCounter i_NumOfActivePlayers)
    {
        m_Board = i_Board;
        m_Players = i_Players;
        m_CurrentPlayer = m_Players.get(0);
        m_TargetSize = i_TargetSize;
        m_GameType = i_GameType;
        m_VictoryChecker = setVictoryChecker();
        activePlayersCounter = i_NumOfActivePlayers;
        initPlayers();
    }

    private void initPlayers()
    {
        for(Player player : m_Players)
        {
            player.resetPlayer();
        }
    }

    public GameType getGameType()
    {
        return m_GameType;
    }

    private VictoryChecker setVictoryChecker()
    {
        VictoryChecker victoryChecker;
        switch(m_GameType)
        {
            case CIRCULAR:
                victoryChecker = new CircularVictoryChecker(m_TargetSize, m_Board);
                break;
            default:
                victoryChecker = new RegularVictoryChecker(m_TargetSize, m_Board);
        }
        return victoryChecker;
    }

    public MoveInfo makeAMove(int i_Col) throws NoSpaceInColException
    {
        MoveInfo moveInfo = new MoveInfo();
        BoardCell boardCell = m_Board.updateBoardAfterRegularMove(i_Col, m_CurrentPlayer);
        boolean gameEnded = false;

        m_CurrentPlayer.addNumOfMoves(1);
        if(m_VictoryChecker.checkVictoryCondition(boardCell))
        {
            isHaveAWinner = true;
            winners.add(m_CurrentPlayer);
            gameEnded = true;
        }
        else if(boardCell.getLocation().y == 0 && m_Board.checkIfBoardIsFull())
        {
            gameEnded = m_GameType != GameType.POPOUT ? true : !isNextPlayerHasPopoutMove();
            isBoardFull = gameEnded;
        }

        moveInfo.addCell(boardCell);
        if(!gameEnded)
        {
            nextPlayer();
        }
        return moveInfo;
    }

    private boolean isNextPlayerHasPopoutMove()
    {
        return m_Board.checkIfPlayerHasDiskInBottomRow(getNextPlayer());
    }

    public MoveInfo makeAPopoutMove(int i_Col) throws Exception
    {
        MoveInfo moveInfo = new MoveInfo();
        int value;
        BoardCell boardCell;
        m_Board.updateBoardAfterPopoutMove(i_Col, m_CurrentPlayer);
        m_CurrentPlayer.addNumOfMoves(1);
        for(int i = m_Board.getRows() - 1; i >= 0; i--)
        {
            value = m_Board.getValueInBoard(i, i_Col);
            boardCell = new BoardCell(new Point(i_Col, i), value);

            if(value != Board.BLANK && m_VictoryChecker.checkVictoryCondition(boardCell))
            {
                winners.add(m_Players.get(m_Board.getValueInBoard(i, i_Col)));
                isHaveAWinner = true;
            }
            moveInfo.addCell(boardCell);
        }
        if(!isHaveAWinner)
            nextPlayer();

        return moveInfo;
    }

    public MoveInfo makeAComputerTurn()
    {
        Random randomChance = new Random();
        int chance = randomChance.nextInt(m_GameType == GameType.POPOUT && m_Board.checkIfPlayerHasDiskInBottomRow(m_CurrentPlayer) ? 10 : 7);
        if(chance >= 7 || m_Board.checkIfBoardIsFull())
            return makeAPopoutComputerMove();
        else
            return makeARegularComputerMove();

    }

    private MoveInfo makeAPopoutComputerMove()
    {
        Random randomCol = new Random();

        try
        {
            return makeAPopoutMove(randomCol.nextInt(m_Board.getCols()));
        }
        catch(Exception e)
        {
            return makeAPopoutComputerMove();
        }
    }


    public MoveInfo makeARegularComputerMove()
    {
        Random randomCol = new Random();

        try
        {
            return makeAMove(randomCol.nextInt(m_Board.getCols()));
        }
        catch(NoSpaceInColException e)
        {
            return makeARegularComputerMove();
        }
    }


    public MoveInfo playerQuit()
    {
        m_Board.removePlayerDisks(m_CurrentPlayer);
        m_CurrentPlayer.quit();
        activePlayersCounter.setNumOfActivePlayers(activePlayersCounter.getNumOfActivePlayers()-1);
        return checkIfHaveWinnersAfterQuit();
    }

    private MoveInfo checkIfHaveWinnersAfterQuit()
    {
        MoveInfo moveInfo = new MoveInfo();
        if(activePlayersCounter.getNumOfActivePlayers() == 1)
        {
            winners.add(getNextPlayer());
            isHaveAWinner = true;
        }
        else
        {
            for(int i = 0; i < m_Board.getRows(); i++)
            {
                for(int j = 0; j < m_Board.getCols(); j++)
                {
                    BoardCell boardCell = new BoardCell(new Point(j, i), m_Board.getValueInBoard(i, j));
                    moveInfo.addCell(boardCell);
                    if(boardCell.getContent() != Board.BLANK && m_VictoryChecker.checkVictoryCondition(boardCell))
                    {
                        winners.add(m_Players.get(boardCell.getContent()));
                        isHaveAWinner = true;
                    }
                }
            }
        }
        if(!isHaveAWinner)
            nextPlayer();
        return moveInfo;
    }


    private Player getPreviousPlayer()
    {
        Player player;
        do
        {
            if(m_CurrentPlayer.getIndex() == 0)
                player = m_Players.get(m_Players.size() - 1);
            else
                player = m_Players.get(m_CurrentPlayer.getIndex() - 1);
        }while(!player.isActive());
        return player;
    }

    private Player getNextPlayer()
    {
        Player player = m_CurrentPlayer;
        do
        {
            if(player.getIndex() == m_Players.size() - 1)
                player = m_Players.get(0);
            else
                player = m_Players.get(player.getIndex() + 1);
        }while(!player.isActive());
        return player;
    }

    public Player getCurrentPlayer()
    {
        return m_CurrentPlayer;
    }

    public void nextPlayer()
    {
        m_CurrentPlayer = getNextPlayer();
    }

    public void prevPlayer()
    {
        m_CurrentPlayer = getPreviousPlayer();
    }

    public List<Player> getPlayers()
    {
        return m_Players;
    }

    public Board getBoard()
    {
        return m_Board;
    }

    public int getTargetSize()
    {
        return m_TargetSize;
    }

    public boolean getIsBoardFull()
    {
        return isBoardFull;
    }

    public boolean getisHaveAWinner()
    {
        return isHaveAWinner;
    }

    public Set<Player> getWinners()
    {
        return winners;
    }

}
