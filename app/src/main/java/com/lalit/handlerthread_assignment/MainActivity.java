package com.lalit.handlerthread_assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MyWorkerThread.Callback{

    private static final String TAG = MainActivity.class.getSimpleName();
    private MyWorkerThread mWorkerThread;
    private ImageView imageView1 ;
    private ProgressBar progressBar ;

    private Handler uiHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,  "Handle message Called");
            imageView1.setImageBitmap((Bitmap) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageView1 = (ImageView) findViewById(R.id.img_1);

        mWorkerThread = new MyWorkerThread(this, uiHandler);
        String[] urls = new String[]{"https://shuttermuse.com/wp-content/uploads/2018/08/ANWR-June-29-Jul-11-2018-1798-1.jpg",
                "https://thumb.cloud.mail.ru/weblink/thumb/xw1/KzUH/jWTfJx9qX/26/15/261512739/261512739_2048.jpg",
                "https://farm6.staticflickr.com/5502/14462507460_2e4ccccf9c_o.jpg",
                "https://www.cameraegg.org/wp-content/uploads/2015/08/AF-S-NIKKOR-200-500mm-f5.6e-ed-vr-lesn-sample-images-8.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3Is9wBSqIf7e6_GRx7KQFYxm2R3VRi0yNCst7mHAzlR6jQXR-"};

        for (String url : urls){
            mWorkerThread.Execute(url);
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        mWorkerThread.quit();
        super.onDestroy();
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap) {
        Log.e(TAG,  " Call Back Called"+bitmap);
        progressBar.setVisibility(View.GONE);
        imageView1.setImageBitmap(bitmap);
     }

}
