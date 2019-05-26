package servlets;

import GameLogic.GameEnginesManager;
import GameLogic.PlayerType;
import utils.JsonUtils;
import utils.ServeltsUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignUpServlet", urlPatterns = {"/signUp"})
public class SignUpServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean isExists = false;
        response.setContentType("application/json;charset=UTF-8");
        GameEnginesManager gamesManager = ServeltsUtils.getGamesManager(getServletContext());
        String username = request.getParameter("username");
        PlayerType playerType = PlayerType.valueOf(request.getParameter("playerType").toUpperCase());
        isExists = (!gamesManager.addUser(username, playerType));
        try(PrintWriter out = response.getWriter())
        {
            if(!isExists)
            {
                request.getSession().setAttribute("userName", username);
                request.getSession().setAttribute("playerType", playerType);
            }
            out.print(JsonUtils.getJson(isExists));
        }
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
