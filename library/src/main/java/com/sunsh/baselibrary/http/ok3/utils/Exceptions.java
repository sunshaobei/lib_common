package com.sunsh.baselibrary.http.ok3.utils;

/**
 * Created by sunsh on 18/5/30.
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
