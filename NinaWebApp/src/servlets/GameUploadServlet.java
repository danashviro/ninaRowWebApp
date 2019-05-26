package servlets;

import utils.JsonUtils;
import utils.ServeltsUtils;
import utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet(name = "GameUploadServlet", urlPatterns = {"/gameUpload"})
@MultipartConfig(fileSizeThreshold = 1024*1024,maxFileSize = 1024*1024*5,maxRequestSize = 1024*1024*5*5)
public class GameUploadServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Collection<Part> parts = request.getParts();
        StringBuilder fileContent = new StringBuilder();
        for(Part part : parts)
        {
            fileContent.append(new Scanner(part.getInputStream()).useDelimiter("\\Z").next());
        }
        out.print(JsonUtils.getJson(ServeltsUtils.getGamesManager(request.getServletContext()).addGame(fileContent,SessionUtils.getUserName(request))));
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
