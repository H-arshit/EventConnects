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


public class TaskToLoadCardDataForRegister extends AsyncTaskLoader<EventDetailsFromDatabase> {

    private int id;
    private EventDetailsFromDatabase databasecache;



    public TaskToLoadCardDataForRegister(Context context) {
        super(context);
//        databasecache = new EventDetailsFromDatabase();
//        databasecache.setTaskdone(false);
    }

    @Override
    protected void onStartLoading() {
        if(databasecache == null) {
            forceLoad();
        }
        else
        {
            super.deliverResult(databasecache);
        }
    }

    @Override
    public EventDetailsFromDatabase loadInBackground() {
        id = ViewSelectedCardRegister.idnumber;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://eventconnects.in/getcarddetails.php?id="+id).build();
        try {
            Response response = client.newCall(request).execute();

            JSONArray jsonarray = new JSONArray(response.body().string());
            EventDetailsFromDatabase mydatas = new EventDetailsFromDatabase();

            for (int j=0; j<jsonarray.length() ; j++)
            {
                JSONObject object = jsonarray.getJSONObject(j);

                mydatas.setTaskdone(true);
                mydatas.setType(object.getString("type"));
                mydatas.setTitle(object.getString("title"));
                mydatas.setVenue(object.getString("venue"));
                mydatas.setPrice(object.getString("price"));
                mydatas.setDates(object.getString("dates"));
                mydatas.setTiming(object.getString("timing"));
                mydatas.setDirectlink(object.getString("directbooklink"));
                mydatas.setOrganizer(object.getString("companyname"));
                mydatas.setContact(object.getString("contact"));
                mydatas.setEmailid(object.getString("emailid"));
                mydatas.setWeblink(object.getString("weblink"));
                mydatas.setDescription(object.getString("description"));
                mydatas.setUpcomingpicpath(object.getString("upcoming"));

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
    public void deliverResult(EventDetailsFromDatabase data) {


          databasecache=data;
        super.deliverResult(data);
    }
}
