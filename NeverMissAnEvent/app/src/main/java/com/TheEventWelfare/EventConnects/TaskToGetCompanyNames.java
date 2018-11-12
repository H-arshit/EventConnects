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


public class TaskToGetCompanyNames extends AsyncTaskLoader<List<EventNewsGenericDataType<String>>> {

    private int id;
    private List<EventNewsGenericDataType<String>> cachelistcompany;
    public static final String ACTION_COMPANY_BROADCAST = "com.forceload.to.companyname";


    public TaskToGetCompanyNames(Context context) {
        super(context);
        cachelistcompany = new ArrayList<>();

    }


    @Override
    protected void onStartLoading() {

        LocalBroadcastManager localBroadcastManagerforcomapny = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilterforcomapny = new IntentFilter(ACTION_COMPANY_BROADCAST);
        localBroadcastManagerforcomapny.registerReceiver(broadcastReceivercompany,intentFilterforcomapny);
        if (cachelistcompany.size()==0)
        {
            forceLoad();
        }
        else
        {
            super.deliverResult(cachelistcompany);
        }
    }



    @Override
    public List<EventNewsGenericDataType<String>> loadInBackground() {
        id  =   FragmentForCompanyName.idcompany;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://eventconnects.in/eventdetailscompanyname.php?id="+id).build();
        try {
            Response response = client.newCall(request).execute();

            JSONArray jsonarray = new JSONArray(response.body().string());
            List<EventNewsGenericDataType<String>> mydatas=new ArrayList<>();

            for (int j=0; j<jsonarray.length() ; j++)
            {
                JSONObject object = jsonarray.getJSONObject(j);

                String data= object.getString("companyname");
                int idnox = object.getInt("id");
                int islaststat = object.getInt("eofcontent");
                String des = object.getString("description");
                String image_path = object.getString("company_logo");
                EventNewsGenericDataType<String> eventNewsGenericDataType = new EventNewsGenericDataType<>();
                eventNewsGenericDataType.setData(data);
                eventNewsGenericDataType.setId(idnox);
                eventNewsGenericDataType.setIslastcontent(islaststat);
                eventNewsGenericDataType.setImage_path(image_path);
                eventNewsGenericDataType.setDescription(des);

                if (!eventNewsGenericDataType.getImage_path().equals("N.A"))
                {
                    URLConnection connection = new URL(eventNewsGenericDataType.getImage_path()).openConnection();
                    Bitmap image = BitmapFactory.decodeStream((InputStream) connection.getContent(),null,null);
                    if (image!= null)
                    {
                       eventNewsGenericDataType.setImage(image);
                    }else
                    {
                        Bitmap largeIcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.anomynous);
                        eventNewsGenericDataType.setImage(largeIcon);
                    }

                }
                else{
                    Bitmap largeIcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.anomynous);
                    eventNewsGenericDataType.setImage(largeIcon);
                }
                mydatas.add(eventNewsGenericDataType);



            }
            response.body().close();


            return mydatas;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        }
        return null;
    }


    @Override
    public void deliverResult(List<EventNewsGenericDataType<String>> data) {
        if(data!=null)
        {
            cachelistcompany.addAll(data);
        }
        data = new ArrayList<>();
        data.addAll(cachelistcompany);
        super.deliverResult(data);
    }
    @Override
    protected void onReset() {
        super.onReset();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceivercompany);

    }

    private BroadcastReceiver broadcastReceivercompany = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            forceLoad();
        }
    };
}
