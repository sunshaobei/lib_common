package com.sunsh.baselibrary.http.ok3.utils;

import android.util.Log;

/**
 * Created by sunsh on 18/5/30.
 */
public class L
{
    private static boolean debug = true;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }
    public static void i(String msg)
    {
        if (debug)
        {
            Log.i("OkHttp", msg);
        }
    }

}

