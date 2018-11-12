package com.TheEventWelfare.EventConnects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TasktoLoadList extends AsyncTaskLoader<List<DataForRegisterPage>>{

    private int id;
    private List<DataForRegisterPage> mycachelist;

    public static final String ACTION_BROADCAST="com.forceload.onscrollfunction";


    public TasktoLoadList(Context context) {
        super(context);
        mycachelist=new ArrayList<>();
    }


    @Override
    protected void onStartLoading() {

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
            IntentFilter intentFilter = new IntentFilter(ACTION_BROADCAST);
        localBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);
        if (mycachelist.size()==0)
        {
                forceLoad();
        }
        else
        {
              super.deliverResult(mycachelist);
        }




    }

    @Override
    public List<DataForRegisterPage> loadInBackground() {

        id=RegisterPage.ide;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://eventconnects.in/downloadlistdetails.php?id="+id).build();
        try {
            Response response = client.newCall(request).execute();

            JSONArray jsonarray = new JSONArray(response.body().string());
            List<DataForRegisterPage> mydatas=new ArrayList<>();

            for (int j=0; j<jsonarray.length() ; j++)
            {
                JSONObject object = jsonarray.getJSONObject(j);

                DataForRegisterPage data=new DataForRegisterPage(object.getInt("id"),object.getString("companyname"),object.getString("title"),object.getString("dates"),object.getInt("eofcontent"));
                mydatas.add(data);


            }
            response.body().close();
            return mydatas;
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }

    }

    @Override
    public void deliverResult(List<DataForRegisterPage> data) {
        if (data!=null)
        {
            mycachelist.addAll(data);
        }
        data=new ArrayList<>();
        data.addAll(mycachelist);
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            forceLoad();
        }
    };
}
