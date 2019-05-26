package GameLogic;

import javafx.beans.property.BooleanProperty;

import java.io.File;
import java.util.BitSet;
import java.util.List;
import java.util.Set;

public interface GameEngine
{
    void makeAMove(int i_Col) throws NoSpaceInColException;

    void makeAPopoutMove(int i_Col) throws Exception;

    void makeAComputerMove();

    void initGame();

    Player getCurrentPlayer();

    void nextPlayer();

    void playerQuit(String userName);

    void prevPlayer();

    List<Player> getPlayers();

    int getBoardNumOfCols();

    int getBoardNumOfRows();

    int getTargetSize();

    Board getBoard();

    GameStatus getGameStatus();

    boolean getIsGameTypePopout();

    boolean addPlayer(String i_Name, String i_PlayerType);

    String getName();

    List<MoveInfo> getPartMoves(int startingIndex);

    int getNumOfTurns();

    Set<Player> getWinners();

    boolean getIsGameFull();

    int getRequiredNumberOfPlayers();

    void resetGameEngine();

    void decreaseActivePlayersByOne();

    int getNumOfActivePlayers();

    List<MoveInfo> getMoves();
}
