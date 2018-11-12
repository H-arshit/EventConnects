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

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskToLoadDataForSeacrh extends AsyncTaskLoader<List<DataForRegisterPage>> {



    private List<DataForRegisterPage> mycachelist;

    private String searchstring="";

    public static final String ACTION_BROADCAST="com.forceload.for.seacchresult";



    public TaskToLoadDataForSeacrh(Context context) {


        super(context);
        mycachelist=new ArrayList<>();
        searchstring = CustomSearchViewFrag.searchstring;


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

        CustomSearchViewFrag.can_call = false;


        int id=CustomSearchViewFrag.id;

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://eventconnects.in/customsearchtask.php").newBuilder();


        urlBuilder.addQueryParameter("id",String.valueOf(id));

        urlBuilder.addQueryParameter("workshoptitle",searchstring);

        String url = urlBuilder.build().toString();


        Request request = new Request.Builder().url(url).build();
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
            CustomSearchViewFrag.can_call = true;

            return mydatas;
        } catch (IOException e) {

            e.printStackTrace();
        } catch (JSONException e) {
        }




        CustomSearchViewFrag.can_call = true;



        return null;
    }

    @Override
    public void deliverResult(List<DataForRegisterPage> data) {

        CustomSearchViewFrag.can_call = true;
        if (data == null)
        {
            if (CustomSearchViewFrag.id < 500)
            {
                CustomSearchViewFrag.id = CustomSearchViewFrag.id + 10;

                Intent intent = new Intent(TaskToLoadDataForSeacrh.ACTION_BROADCAST);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            }
        }

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
