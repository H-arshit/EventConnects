package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TaskToLogin extends AsyncTaskLoader<UserDetails> {

  //  public static final String ACTION_BROADCAST_LOGIN="com.forceload.logindetails";
    private static final String TAG ="Debugtask" ;

    public TaskToLogin(Context context) {
        super(context);

    }


    @Override
    protected void onStartLoading() {
//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
//        IntentFilter intentFilter = new IntentFilter(ACTION_BROADCAST_LOGIN);
//        localBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);

            forceLoad();
    }

    @Override
    public UserDetails loadInBackground() {
        String emailid=SigninPage.emailid,password = SigninPage.passwordchech;
        Log.d(TAG, "loadInBackground() called " + emailid);

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://eventconnects.in/logintask.php").newBuilder();
        urlBuilder.addQueryParameter("emailid",emailid);
        urlBuilder.addQueryParameter("password",password);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        try {


            Response response = client.newCall(request).execute();


            JSONArray jsonarray = new JSONArray(response.body().string());


            UserDetails mydatas=new UserDetails();

            for (int j=0; j<jsonarray.length() ; j++)
            {
                JSONObject object = jsonarray.getJSONObject(j);

                mydatas.setEmailid(object.getString("emailid"));
//                Log.d(TAG,object.getString("emailid"));
                mydatas.setContact(object.getString("contact"));
//                Log.d(TAG,object.getString("contact"));
                mydatas.setPassword(object.getString("password"));
//                Log.d(TAG,object.getString("password"));
                mydatas.setUsername(object.getString("username"));
//                Log.d(TAG,object.getString("username"));
            }
            response.body().close();
            return mydatas;
        } catch (IOException e) {
            return null;

        } catch (JSONException e) {
            return new UserDetails();

        }finally {
            SigninPage.READY_TO_CHECK = true ;
        }

    }

    @Override
    public void deliverResult(UserDetails data) {

        super.deliverResult(data);
    }
    protected void onReset() {
        super.onReset();
//        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            forceLoad();
//        }
//    };


}
