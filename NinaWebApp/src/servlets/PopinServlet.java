package servlets;

import GameLogic.GameEngine;
import GameLogic.NoSpaceInColException;
import utils.JsonUtils;
import utils.ServeltsUtils;
import utils.SessionUtils;
import utils.TurnInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PopinServlet",urlPatterns = {"/popin"})
public class PopinServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int gameIndex = SessionUtils.getGameIndex(request);
        GameEngine gameEngine = ServeltsUtils.getGameByIndex(getServletContext(), gameIndex);
        int col = Integer.parseInt(request.getParameter("col"));
        TurnInfo turnInfo = new TurnInfo();
        try
        {
            gameEngine.makeAMove(col);
            turnInfo.setTurnSuccess(true);
        }
        catch(NoSpaceInColException e)
        {
            turnInfo.setErrorMessage(e.getMessage());
            turnInfo.setTurnSuccess(false);
        }
        out.print(JsonUtils.getJson(turnInfo));
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

}
