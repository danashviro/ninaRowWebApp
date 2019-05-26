package servlets;

import GameLogic.GameEngine;
import GameLogic.Player;
import utils.JsonUtils;
import utils.ServeltsUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "JoinGameServlet", urlPatterns = {"/joinGame"})
public class JoinGameServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean addPlayerSuccess;
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int gameIndex = Integer.parseInt(request.getParameter("gameIndex"));
        GameEngine game = ServeltsUtils.getGameByIndex(getServletContext(), gameIndex);
        addPlayerSuccess = game.addPlayer(SessionUtils.getUserName(request), SessionUtils.getPlayerType(request));
        if(addPlayerSuccess)
        {
            SessionUtils.setGameIndex(request,gameIndex);
        }

        out.println(JsonUtils.getJson(addPlayerSuccess));
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
