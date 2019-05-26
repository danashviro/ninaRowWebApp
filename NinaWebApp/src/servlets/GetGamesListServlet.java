package servlets;

import GameLogic.GameEngine;
import utils.JsonUtils;
import utils.ServeltsUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetGamesListServlet", urlPatterns = {"/getGamesList"})
public class GetGamesListServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<GameEngine> gamesList = ServeltsUtils.getGamesManager(request.getServletContext()).getAvailableGames();
        out.print(JsonUtils.getJson(gamesList));
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
