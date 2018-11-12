package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by User on 27-05-2017.
 */

public class TaskToGetCardImage extends AsyncTaskLoader<Bitmap> {

    String directlinkforimage = " ";

    public TaskToGetCardImage(Context context) {
            super(context);

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Bitmap loadInBackground() {

        directlinkforimage = ViewSelectedCardRegister.directlinkforregistration;
        try {
            URLConnection connection = new URL(directlinkforimage).openConnection();
            return BitmapFactory.decodeStream((InputStream) connection.getContent(),null,null);
        } catch (Exception e) {
            return null;
        }


    }

    @Override
    public void deliverResult(Bitmap data) {
        super.deliverResult(data);
    }
}
