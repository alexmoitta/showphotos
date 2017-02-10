package com.yourdomain.showphotos;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;




//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;




/**
 * Created by asantos on 01/08/15.
 */
public class ImageDownloader<Token> extends HandlerThread
{
    private static final String TAG = "ImageDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    Handler handler;
    private ConcurrentMap<Token,String> requestMap = new ConcurrentHashMap<>();


    public ImageDownloader()
    {
        super(TAG);
    }


    @SuppressLint("HandlerLeak")

    @Override
    protected void onLooperPrepared()
    {
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if (msg.what == MESSAGE_DOWNLOAD)
                {
                    @SuppressWarnings("unchecked")
                    Token token = (Token) msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    //handleRequest(token); //falta fazer isso - seguir exemplo da página 444

                }

            }
        };
    }





    public void queueImage(Token token, String url)
    {
        Log.i(TAG, "URL:" + url);

    }

}

