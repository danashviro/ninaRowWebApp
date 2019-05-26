package utils;

import com.google.gson.Gson;

public class JsonUtils
{
    public static String getJson(Object i_Object)
    {
        Gson gson=new Gson();
        return gson.toJson(i_Object);
    }
}
