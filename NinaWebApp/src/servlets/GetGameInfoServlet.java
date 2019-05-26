package servlets;

import GameLogic.GameEngine;
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

@WebServlet(name = "GetGameInfoServlet",urlPatterns = {"/getGameInfo"})
public class GetGameInfoServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        int gameIndex = SessionUtils.getGameIndex(request);
        GameEngine gameEngine = ServeltsUtils.getGameByIndex(getServletContext(),gameIndex);
        GameInfo gameInfo = new GameInfo();
        gameInfo.cols = gameEngine.getBoardNumOfCols();
        gameInfo.rows = gameEngine.getBoardNumOfRows();
        gameInfo.popout = gameEngine.getIsGameTypePopout();
        PrintWriter out = response.getWriter();
        out.print(JsonUtils.getJson(gameInfo));
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

    public class GameInfo
    {
        private int rows,cols;
        private boolean popout;
    }
}
