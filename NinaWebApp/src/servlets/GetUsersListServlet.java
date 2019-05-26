package servlets;

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
import java.util.Map;

@WebServlet(name = "GetUsersListServlet" , urlPatterns = {"/getUsersList"})
public class GetUsersListServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        try(PrintWriter out=response.getWriter())
        {
            Map<String, PlayerType> users=ServeltsUtils.getGamesManager(request.getServletContext()).getUsers();
            String userNames[]=new String[users.size()];
            int i = 0;
            for(Map.Entry<String,PlayerType> entry: users.entrySet())
            {
                userNames[i] = entry.getKey();
                i++;
            }
            out.print(JsonUtils.getJson(userNames));
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
