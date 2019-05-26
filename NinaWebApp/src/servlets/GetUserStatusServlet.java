package servlets;

import utils.JsonUtils;
import utils.SessionUtils;
import utils.UserStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetUserStatusServlet", urlPatterns = {"/getUserStatus"})
public class GetUserStatusServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        UserStatus status = new UserStatus();
        String userName = SessionUtils.getUserName(request);
        status.setUserConnected(userName != null);
        int gameId = SessionUtils.getGameIndex(request);
        status.setInActiveGame(gameId != -1);
        status.setConnectedUsername(userName);
        try(PrintWriter out = response.getWriter())
        {
            out.print(JsonUtils.getJson(status));
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
