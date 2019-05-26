package GameLogic;

import java.awt.*;
import java.io.Serializable;

public class Board implements Serializable
{
    private int rows;
    private int cols;
    private int[][] playersMat;
    public static final int BLANK = -1;

    public Board(int i_Rows, int i_Cols)
    {
        this.rows = i_Rows;
        this.cols = i_Cols;
        initBoard();
    }

    public Board(Board i_Board)
    {
        cols = i_Board.cols;
        rows = i_Board.rows;
        playersMat = new int[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                playersMat[i][j] = i_Board.getValueInBoard(i, j);
    }


    public int getValueInBoard(int i_Row, int i_Col)
    {
        return playersMat[i_Row][i_Col];
    }

    private void initBoard()
    {
        playersMat = new int[rows][cols];
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                playersMat[i][j] = BLANK;
            }
        }
    }


    public BoardCell updateBoardAfterRegularMove(int i_Col, Player i_CurrentPlayer) throws NoSpaceInColException
    {
        Point move = getFirstFreeSpotInCol(i_Col);
        playersMat[move.y][move.x] = i_CurrentPlayer.getIndex();
        return new BoardCell(move, i_CurrentPlayer.getIndex());
    }

    private Point getFirstFreeSpotInCol(int i_Col) throws NoSpaceInColException
    {
        for(int i = rows - 1; i >= 0; i--)
        {
            if(playersMat[i][i_Col] == BLANK)
            {
                return new Point(i_Col, i);
            }
        }
        throw new NoSpaceInColException(i_Col);
    }


    public boolean isPointInBoardLimits(int i_Col, int i_Row)
    {
        return (i_Col < cols && i_Col >= 0 && i_Row >= 0 && i_Row < rows);
    }


    public boolean checkIfBoardIsFull()
    {
        for(int i = 0; i < cols; i++)
        {
            if(playersMat[0][i] == BLANK)
                return false;
        }
        return true;
    }

    public void clearPointInBoard(int i_Row, int i_Col)
    {
        playersMat[i_Row][i_Col] = BLANK;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public void updateBoardAfterPopoutMove(int i_Col, Player i_CurrentPlayer) throws Exception
    {
        if(playersMat[rows - 1][i_Col] == BLANK)
            throw new Exception("This column is empty");
        else if(playersMat[rows - 1][i_Col] != i_CurrentPlayer.getIndex())
            throw new Exception("You cannot popout another player disk");
        for(int i = rows - 1; i > 0; i--)
            playersMat[i][i_Col] = playersMat[i - 1][i_Col];
        playersMat[0][i_Col] = BLANK;
    }

    public boolean checkIfPlayerHasDiskInBottomRow(Player i_Player)
    {
        int playerIndex = i_Player.getIndex();
        for(int i = 0; i < cols; i++)
        {
            if(playersMat[rows - 1][i] == playerIndex)
                return true;
        }
        return false;
    }

    public void removePlayerDisks(Player i_Player)
    {
        int read, write;
        int playerIndex = i_Player.getIndex();
        for(int i = 0; i < cols; i++)
        {
            for(read = rows - 1, write = rows - 1; read >= 0; read--)
            {
                if(playersMat[read][i] != playerIndex)
                {
                    playersMat[write][i] = playersMat[read][i];
                    write--;
                }
            }
            while(write >= 0)
            {
                playersMat[write][i] = BLANK;
                write--;
            }
        }
    }
}
