package servlets;

import GameLogic.GameEngine;
import utils.ServeltsUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RemoveGameIndexFromSessionServlet",urlPatterns = {"/removeGameIndexFromSession"})
public class RemoveGameIndexFromSessionServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int gameIndex = SessionUtils.getGameIndex(request);
        GameEngine gameEngine = ServeltsUtils.getGameByIndex(getServletContext(), gameIndex);
        gameEngine.decreaseActivePlayersByOne();
        SessionUtils.setGameIndex(request,-1);
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
