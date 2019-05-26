package GameLogic;

import XsdClasses.GameDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameEngineImpl implements GameEngine
{
    private ActivePlayersCounter activePlayersCounter = new ActivePlayersCounter();
    private GameDescriptor gameDescriptor;
    private Game game;
    private List<Player> players = new ArrayList<>();
    private int cols, rows, targetSize;
    private boolean isGameTypePopout = false;
    private int requiredNumberOfPlayers;
    private String gameName;
    private String gameType;
    private String gameCreator;
    private int gameEngineIndex;
    private List<MoveInfo> moves = new ArrayList<>();
    private GameStatus gameStatus;

    public GameEngineImpl(GameDescriptor gameDescriptor, String creatorName, int i_gameEngineIndex)
    {
        activePlayersCounter.setNumOfActivePlayers(0);
        this.gameDescriptor = gameDescriptor;
        cols = this.gameDescriptor.getGame().getBoard().getColumns().intValue();
        rows = this.gameDescriptor.getGame().getBoard().getRows();
        targetSize = this.gameDescriptor.getGame().getTarget().intValue();
        requiredNumberOfPlayers = this.gameDescriptor.getDynamicPlayers().getTotalPlayers();
        gameName = this.gameDescriptor.getDynamicPlayers().getGameTitle();
        gameType = this.gameDescriptor.getGame().getVariant();
        gameCreator = creatorName;
        gameEngineIndex = i_gameEngineIndex;
        isGameTypePopout = GameType.valueOf(gameType.toUpperCase()) == GameType.POPOUT;
        gameStatus = GameStatus.NOT_STARTED;

    }

    @Override
    public boolean getIsGameTypePopout()
    {
        return isGameTypePopout;
    }

    public synchronized boolean addPlayer(String i_Name, String i_PlayerType)
    {
        if(!isPlayerExist(i_Name) && players.size() < requiredNumberOfPlayers)
        {
            players.add(new Player(i_Name, PlayerType.valueOf(i_PlayerType.toUpperCase())));
            activePlayersCounter.setNumOfActivePlayers(players.size());
            if(players.size() == requiredNumberOfPlayers)
            {
                initGame();
            }

            return true;
        }
        else
            return false;
    }

    private boolean isPlayerExist(String name)
    {
        for(Player player : players)
        {
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }

    @Override
    public void makeAMove(int i_Col) throws NoSpaceInColException
    {
        MoveInfo moveInfo;
        moveInfo = game.makeAMove(i_Col);
        if(game.getIsBoardFull() || game.getisHaveAWinner())
            gameStatus = GameStatus.ENDED;
        moves.add(moveInfo);
    }

    @Override
    public void makeAComputerMove()
    {
        MoveInfo moveInfo;
        moveInfo = game.makeAComputerTurn();
        if(game.getIsBoardFull() || game.getisHaveAWinner())
            gameStatus = GameStatus.ENDED;
        moves.add(moveInfo);
    }

    @Override
    public void makeAPopoutMove(int i_Col) throws Exception
    {
        MoveInfo moveInfo;
        moveInfo = game.makeAPopoutMove(i_Col);
        if(game.getIsBoardFull() || game.getisHaveAWinner())
            gameStatus = GameStatus.ENDED;
        moves.add(moveInfo);
    }

    @Override
    public void initGame()
    {
        Board board = new Board(rows, cols);
        game = new Game(board, players, targetSize, GameType.valueOf(gameDescriptor.getGame().getVariant().toUpperCase()), activePlayersCounter);
        isGameTypePopout = (game.getGameType() == GameType.POPOUT);
        setPlayersIndexes();
        gameStatus = GameStatus.STARTED;
    }

    private void setPlayersIndexes()
    {
        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).setPlayerIndex(i);
        }
    }

    @Override
    public Player getCurrentPlayer()
    {
        return game.getCurrentPlayer();
    }

    @Override
    public void nextPlayer()
    {
        game.nextPlayer();
    }

    @Override
    public void playerQuit(String userName)
    {
        if(gameStatus == GameStatus.NOT_STARTED)
        {
            removeUserFromList(userName);
        }
        else
        {
            MoveInfo moveInfo = game.playerQuit();
            if(game.getisHaveAWinner())
                gameStatus = GameStatus.ENDED;
            moves.add(moveInfo);
        }
    }

    private synchronized void removeUserFromList(String userName)
    {
        int index = -1;
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getName().equals(userName))
                index = i;
        }
        if(index != -1)
        {
            players.remove(index);
            activePlayersCounter.setNumOfActivePlayers(players.size());
        }
    }

    @Override
    public void prevPlayer()
    {
        game.prevPlayer();
    }

    @Override
    public List<Player> getPlayers()
    {
        return players;
    }

    @Override
    public int getBoardNumOfCols()
    {
        return cols;
    }

    @Override
    public int getBoardNumOfRows()
    {
        return rows;
    }

    @Override
    public int getTargetSize()
    {
        return targetSize;
    }

    @Override
    public Board getBoard()
    {
        return game.getBoard();
    }

    @Override
    public GameStatus getGameStatus()
    {
        return gameStatus;
    }


    @Override
    public String getName()
    {
        return gameName;
    }

    @Override
    public synchronized List<MoveInfo> getPartMoves(int startingIndex)
    {
        if(moves.size() == 0 || startingIndex == moves.size())
            return new ArrayList<>(0);
        else
            return moves.subList(startingIndex, moves.size());
    }

    @Override
    public int getNumOfTurns()
    {
        return moves.size();
    }

    @Override
    public Set<Player> getWinners()
    {
        return game.getWinners();
    }

    @Override
    public boolean getIsGameFull()
    {
        return game.getIsBoardFull();
    }

    @Override
    public int getRequiredNumberOfPlayers()
    {
        return requiredNumberOfPlayers;
    }

    @Override
    public void resetGameEngine()
    {
        players = new ArrayList<>();
        moves = new ArrayList<>();
        gameStatus = GameStatus.NOT_STARTED;
        activePlayersCounter.setNumOfActivePlayers(0);
    }

    @Override
    public synchronized void decreaseActivePlayersByOne()
    {
        activePlayersCounter.setNumOfActivePlayers(activePlayersCounter.getNumOfActivePlayers()-1);
        if(activePlayersCounter.getNumOfActivePlayers() == 0 && gameStatus == GameStatus.ENDED)
        {
            resetGameEngine();
        }
    }

    @Override
    public synchronized int getNumOfActivePlayers()
    {
        return activePlayersCounter.getNumOfActivePlayers();
    }

    @Override
    public List<MoveInfo> getMoves()
    {
        return moves;
    }

}
