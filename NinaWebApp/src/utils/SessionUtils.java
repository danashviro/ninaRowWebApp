package utils;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils
{
    public static String getUserName(HttpServletRequest i_Request)
    {
        return getStringAttribute(i_Request, "userName");
    }

    public static String getGameName(HttpServletRequest i_Request)
    {
        return getStringAttribute(i_Request, "gameName");
    }

    public static String getPlayerType(HttpServletRequest i_Request)
    {
        return getStringAttribute(i_Request, "playerType");
    }

    public static void setGameIndex(HttpServletRequest request, int index)
    {
        request.getSession(false).setAttribute("gameIndex", index);
    }

    public static int getGameIndex(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        Object gameIndex =  session!=null ? session.getAttribute("gameIndex"):null;
        return gameIndex != null ? (int)gameIndex : -1 ;
    }


    private static String getStringAttribute(HttpServletRequest i_Request, String i_AttributeKey)
    {
        HttpSession session = i_Request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(i_AttributeKey) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

}
