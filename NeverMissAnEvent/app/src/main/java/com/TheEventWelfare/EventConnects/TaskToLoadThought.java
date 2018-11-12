package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 04-06-2017.
 */

public class TaskToLoadThought extends AsyncTaskLoader<String> {

    private String cachedata;
    public TaskToLoadThought(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
       if (cachedata == null || cachedata.equals("") || cachedata.equals("Network Error")){
           forceLoad();
       }else{
           super.deliverResult(cachedata);
       }
    }

    @Override
    public String loadInBackground() {
        String resultstring = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://eventconnects.in/getthoughts.php").build();
        try {
            Response response = client.newCall(request).execute();

            JSONArray jsonarray = new JSONArray(response.body().string());

            for (int j=0; j<jsonarray.length() ; j++)
            {
                JSONObject object = jsonarray.getJSONObject(j);
                resultstring =  object.getString("thoughts");
            }

            response.body().close();

            return resultstring;
        } catch (IOException e) {
            return "Network Error";
        } catch (JSONException e) {

            return "Network Error";

            }
    }

    @Override
    public void deliverResult(String data) {
        cachedata = data;
        super.deliverResult(data);
    }
}
