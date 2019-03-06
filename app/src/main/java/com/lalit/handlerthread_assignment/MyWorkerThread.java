package com.lalit.handlerthread_assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyWorkerThread extends HandlerThread {

    private Handler mResponseHandler;
    private static final String TAG = MyWorkerThread.class.getSimpleName();
    private Callback mCallback;

    private Handler handler ;

    public interface Callback {
        public void onImageDownloaded(Bitmap bitmap);
    }


    public MyWorkerThread(Callback callback, Handler responseHandler) {
        super(TAG);
        start();
        handler = new Handler(getLooper());
        mCallback = callback;
        mResponseHandler = responseHandler;
    }


    public MyWorkerThread Execute(final String urls){
        Runnable r = new Runnable() {
            @Override
            public void run() {
               try {
                    URL url = new URL(urls);
                    final Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Log.e(TAG,  " DOWNLOADED 1"+image);
//                    mResponseHandler.post(new Runnable() {
//                       @Override
//                       public void run() {
//                           //Log.e(TAG,  " DOWNLOADED "+bitmap);
//                           mCallback.onImageDownloaded(image);
//                       }
//                   });
                   Message msg = Message.obtain();
                   msg.obj = image ;
                   mResponseHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,  "CATCH"+e);
                }
            }
        };
        handler.post(r);
        return this;
    }



}
