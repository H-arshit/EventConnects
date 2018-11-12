package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskToCreateId extends AsyncTaskLoader<String>
{

    public TaskToCreateId(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        if (CreateUserId.userDetailsforId.getUsername().length()==0)
        {
            return " ";
        }

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://eventconnects.in/CreateIdTask.php").newBuilder();

        urlBuilder.addQueryParameter("emailid",CreateUserId.userDetailsforId.getEmailid());
        urlBuilder.addQueryParameter("password",CreateUserId.userDetailsforId.getPassword());
        urlBuilder.addQueryParameter("contact",CreateUserId.userDetailsforId.getContact());
        urlBuilder.addQueryParameter("username",CreateUserId.userDetailsforId.getUsername());
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();
        try {


            Response response = client.newCall(request).execute();


            String result = response.body().string();

            response.body().close();

          return result;



        } catch (IOException e) {

            return "Network Error Try again Later";

        }

    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
    }
}
