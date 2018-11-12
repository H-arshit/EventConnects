package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class TaskToUploadAdvertiserRequest extends AsyncTaskLoader<String> {


   private BufferedWriter bufferedWriter;
    private String twoHyphens = "--";
    private String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
    private  String lineEnd = "\r\n";


    public TaskToUploadAdvertiserRequest(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
     forceLoad();
    }


    @Override
    public String loadInBackground() {



        UploadDetails uploadDetails = OrganiseFinalView.uploadDetails;

        String register="http://eventconnects.in/uploadscript.php";

        try {

            URL url=new URL(register);
            HttpURLConnection mycon=(HttpURLConnection)url.openConnection();
            mycon.setRequestMethod("POST");
            mycon.setDoOutput(true);
            mycon.setDoInput(true);
            mycon.setUseCaches(false);
            mycon.setRequestProperty("Connection", "Keep-Alive");
            mycon.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            mycon.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);


            OutputStream myoutput=mycon.getOutputStream();
             bufferedWriter=new BufferedWriter(new OutputStreamWriter(myoutput,"UTF-8"));
//            String post_data=
//                    URLEncoder.encode("worktype","UTF-8")+"="+URLEncoder.encode(uploadDetails.getTypename(),"UTF-8")+"&"+
//                            URLEncoder.encode("worktitle","UTF-8")+"="+URLEncoder.encode(uploadDetails.getTitlename(),"UTF-8")+"&"+
//                            URLEncoder.encode("venue","UTF-8")+"="+URLEncoder.encode(uploadDetails.getVen(),"UTF-8")+"&"+
//                            URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(uploadDetails.getPrice(),"UTF-8")+"&"+
//                            URLEncoder.encode("dates","UTF-8")+"="+URLEncoder.encode(uploadDetails.getDates(),"UTF-8")+"&"+
//                            URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(uploadDetails.getTime(),"UTF-8")+"&"+
//                            URLEncoder.encode("orgname","UTF-8")+"="+URLEncoder.encode(uploadDetails.getOrgname(),"UTF-8")+"&"+
//                            URLEncoder.encode("phonenum","UTF-8")+"="+URLEncoder.encode(uploadDetails.getPhonenum(),"UTF-8")+"&"+
//                            URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(uploadDetails.getEmail(),"UTF-8")+"&"+
//                            URLEncoder.encode("weblink","UTF-8")+"="+URLEncoder.encode(uploadDetails.getWeblink(),"UTF-8")+"&"+
//                            URLEncoder.encode("bookinglink","UTF-8")+"="+URLEncoder.encode(uploadDetails.getBookinglink(),"UTF-8")+"&"+
//                            URLEncoder.encode("descript","UTF-8")+"="+URLEncoder.encode(uploadDetails.getDescript(),"UTF-8")+"&"+
//                            URLEncoder.encode("pastimage","UTF-8")+"="+URLEncoder.encode(uploadDetails.getPastimagecode(),"UTF-8")+"&"+
//                            URLEncoder.encode("upcomingimage","UTF-8")+"="+URLEncoder.encode(uploadDetails.getUpcomingimagecode(),"UTF-8")+"&"+
//                            URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(uploadDetails.getUserid(),"UTF-8");


            filldata("worktype",uploadDetails.getTypename());
            filldata("worktitle",uploadDetails.getTitlename());
            filldata("venue",uploadDetails.getVen());
            filldata("price",uploadDetails.getPrice());
            filldata("dates",uploadDetails.getDates());
            filldata("time",uploadDetails.getTime());
            filldata("orgname",uploadDetails.getOrgname());
            filldata("phonenum",uploadDetails.getPhonenum());
            filldata("email",uploadDetails.getEmail());
            filldata("weblink",uploadDetails.getWeblink());
            filldata("bookinglink",uploadDetails.getBookinglink());
            filldata("descript",uploadDetails.getDescript());
            filldata("pastimage",uploadDetails.getPastimagecode());
            filldata("upcomingimage",uploadDetails.getUpcomingimagecode());
            filldata("userid",uploadDetails.getUserid());

//            bufferedWriter.write(post_data);
//            bufferedWriter.flush();

            bufferedWriter.write(lineEnd);
                    bufferedWriter.flush();
            bufferedWriter.write(twoHyphens + boundary + twoHyphens);
            bufferedWriter.write(lineEnd);
            bufferedWriter.flush();

            bufferedWriter.close();
            myoutput.close();
            InputStream inputStream=mycon.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="",line="";
            while((line=bufferedReader.readLine())!=null)
            {
                result+=line;
            }
            bufferedReader.close();
            inputStream.close();
            mycon.disconnect();
            return result;
        } catch (MalformedURLException e) {
            return "Network Error \n Unable To Connect";

        }catch (Exception e){
            return "Network Error \n Unable To Connect";
        }





    }


    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
    }

  private  void filldata (String name , String value){

        try {


            bufferedWriter.write(twoHyphens + boundary + lineEnd);
            bufferedWriter.write("Content-Disposition: form-data; name=\"" + name + "\"" + lineEnd);
            bufferedWriter.write("Content-Type: text/plain; charset=" + "UTF-8" + lineEnd);
            bufferedWriter.write(lineEnd);
            bufferedWriter.write(value);
            bufferedWriter.write(lineEnd);
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
