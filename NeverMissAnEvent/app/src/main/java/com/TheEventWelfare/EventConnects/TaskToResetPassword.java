package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TaskToResetPassword extends AsyncTaskLoader<String> {
    public TaskToResetPassword(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        if (!ForgotPassword.request_change_password)
        {
            return " ";
        }

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://eventconnects.in/forgotpasswordrequest.php").newBuilder();

        ForgotPassword.request_change_password = false;

        urlBuilder.addQueryParameter("emailid",ForgotPassword.userDetails.getEmailid());
        urlBuilder.addQueryParameter("password",ForgotPassword.userDetails.getPassword());
        urlBuilder.addQueryParameter("contact",ForgotPassword.userDetails.getContact());
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
