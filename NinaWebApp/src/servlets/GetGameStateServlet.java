package servlets;

import GameLogic.*;
import utils.JsonUtils;
import utils.ServeltsUtils;
import utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@WebServlet(name = "GetGameStateServlet", urlPatterns = {"/getGameState"})
public class GetGameStateServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        int gameIndex = SessionUtils.getGameIndex(request);
        int numOfUpdatedTurnes = Integer.parseInt(request.getParameter("numberOfTurns"));
        GameEngine gameEngine = ServeltsUtils.getGameByIndex(getServletContext(), gameIndex);
        PrintWriter out = response.getWriter();
        GameState gameState = new GameState();
        gameState.gameStatus = gameEngine.getGameStatus();
        gameState.requiredNumberOfPlayers = gameEngine.getRequiredNumberOfPlayers();
        gameState.players = gameEngine.getPlayers();
        if(gameEngine.getGameStatus() != GameStatus.NOT_STARTED)
        {
            gameState.myName = SessionUtils.getUserName(request);
            gameState.moves = gameEngine.getPartMoves(numOfUpdatedTurnes);
            gameState.currentPlayerIndex = gameEngine.getCurrentPlayer().getIndex();
            gameState.isMyTurn = SessionUtils.getUserName(request).equals(gameEngine.getCurrentPlayer().getName());
            gameState.isHuman = gameEngine.getCurrentPlayer().getPlayerType() == PlayerType.HUMAN;
            gameState.winners = gameEngine.getWinners();
            gameState.isBoardFull = gameEngine.getIsGameFull();
            gameState.numOfMoves = gameEngine.getMoves().size();
        }
        out.print(JsonUtils.getJson(gameState));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    public class GameState
    {
        private int numOfMoves;
        private List<MoveInfo> moves;
        private List<Player> players;
        private int currentPlayerIndex;
        private boolean isMyTurn = false;
        private int requiredNumberOfPlayers;
        private boolean isHuman = false;
        private GameStatus gameStatus;
        private Set<Player> winners;
        private boolean isBoardFull;
        private String myName;
    }

}
