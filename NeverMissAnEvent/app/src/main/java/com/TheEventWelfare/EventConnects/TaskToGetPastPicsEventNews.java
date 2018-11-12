package com.TheEventWelfare.EventConnects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TaskToGetPastPicsEventNews extends AsyncTaskLoader<List<EventNewsGenericDataType<Bitmap>>> {
    private int id;
    private List<EventNewsGenericDataType<Bitmap>> cachelistpast;
    public static final String ACTION_PASTPIC_BROADCAST = "com.forceload.to.pastpics";


    public TaskToGetPastPicsEventNews(Context context) {
        super(context);
        cachelistpast = new ArrayList<>();
    }


    @Override
    protected void onStartLoading() {

        LocalBroadcastManager localBroadcastManagerforpastpics = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilterforpast = new IntentFilter(ACTION_PASTPIC_BROADCAST);
        localBroadcastManagerforpastpics.registerReceiver(broadcastReceiverpast,intentFilterforpast);
        if (cachelistpast.size()==0)
        {
            forceLoad();
        }
        else
        {
            super.deliverResult(cachelistpast);
        }
    }

    @Override
    public List<EventNewsGenericDataType<Bitmap>> loadInBackground() {
        id  =   FragmentForPastPics.idpastpic;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://eventconnects.in/urlpastpic.php?id="+id).build();
        try {
            Response response = client.newCall(request).execute();

            JSONArray jsonarray = new JSONArray(response.body().string());
            List<EventNewsGenericDataType<String>> mydatas=new ArrayList<>();

            for (int j=0; j<jsonarray.length() ; j++)
            {
                JSONObject object = jsonarray.getJSONObject(j);

                String data= object.getString("pastpic");
                int idnos = object.getInt("id");
                int islastcontent = object.getInt("eofcontent");
                EventNewsGenericDataType<String> eventNewsGenericDataType = new EventNewsGenericDataType<>();
                eventNewsGenericDataType.setData(data);
                eventNewsGenericDataType.setId(idnos);

                    eventNewsGenericDataType.setIslastcontent(islastcontent);


                mydatas.add(eventNewsGenericDataType);


            }
            response.body().close();


            List<EventNewsGenericDataType<Bitmap>> imagesdata = new ArrayList<>();

            for(int k=0;k<mydatas.size();k++)
            {
                String url = mydatas.get(k).getData();
                int idnos = mydatas.get(k).getId();
                int islastcontent = mydatas.get(k).islastcontent();
                if (!url.equals("N.A"))
                {
                    URLConnection connection = new URL(url).openConnection();
                    Bitmap image = BitmapFactory.decodeStream((InputStream) connection.getContent(),null,null);
                    if (image!= null)
                    {
                        EventNewsGenericDataType<Bitmap> eventNewsGenericDataType = new EventNewsGenericDataType<>();
                        eventNewsGenericDataType.setId(idnos);
                        eventNewsGenericDataType.setIslastcontent(islastcontent);
                        eventNewsGenericDataType.setData(image);
                        imagesdata.add(eventNewsGenericDataType);
                    }

                }
            }
            if (imagesdata.size()>0)
            {
                EventNewsGenericDataType<Bitmap> eventNewsGenericDataType = imagesdata.get(imagesdata.size()-1);
                eventNewsGenericDataType.setId(mydatas.get(mydatas.size()-1).getId());
                eventNewsGenericDataType.setData(eventNewsGenericDataType.getData());
                imagesdata.set(imagesdata.size()-1,eventNewsGenericDataType);
            }

            return imagesdata;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        }
        return null;
    }

    @Override
    public void deliverResult(List<EventNewsGenericDataType<Bitmap>> data) {
        if ((data == null || data.size()==0 )&& cachelistpast.size()!=0)
        {
            EventNewsGenericDataType<Bitmap> eventNewsGenericDataType = cachelistpast.get(cachelistpast.size()-1);
            eventNewsGenericDataType.setId(eventNewsGenericDataType.getId()+4);
            eventNewsGenericDataType.setData(eventNewsGenericDataType.getData());
            cachelistpast.set(cachelistpast.size()-1,eventNewsGenericDataType);
        }
        if(data!=null && data.size()!=0)
        {
            cachelistpast.addAll(data);
        }
        data = new ArrayList<>();
        data.addAll(cachelistpast);


        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiverpast);

    }

    private BroadcastReceiver broadcastReceiverpast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            forceLoad();
        }
    };
}
