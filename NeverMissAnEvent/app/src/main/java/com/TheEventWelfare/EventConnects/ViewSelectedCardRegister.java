package com.TheEventWelfare.EventConnects;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

public class ViewSelectedCardRegister extends Fragment implements View.OnClickListener {

    TextView registertitle,registertype,registerVenue,registerPrice,registertime,registerdate,registerorg,registercont,registeremail,registerlink,registerdescript;

    //// TODO: 27-05-2017
    static String directlinkforregistration;  // actually url for downloading pic
    String directbookinglink; // to ge done to set visiblity of booking button I have'nt displayed the direct booking link
    Button linkbutton , imagebutton , callbutton ;
    private static final int PERMS_REQUEST_CODE = 123;
    Bitmap imagebitmap ;
    ImageView imagedownload;


    InternalDatabaseForSavedCards internalDatabaseForSavedCards;

     ProgressDialog progressbar;

    Snackbar bar , bardownload;

    static int idnumber;
    private EventDetailsFromDatabase eventDetailsFromDatabase;
    public ViewSelectedCardRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myview =  inflater.inflate(R.layout.fragment_view_selected_card_register, container, false);

            Bundle bundle =this.getArguments();
            idnumber=bundle.getInt("IDNUMBER");

        Typeface custom_font1 = Typeface.createFromAsset(getContext().getAssets(),  "fonts/NotoSerif-Regular.ttf");
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/BalooBhaijaan-Regular.ttf");


        imagedownload = (ImageView) myview.findViewById(R.id.imagedownloaded);
        linkbutton = (Button)myview.findViewById(R.id.linkbutton);
        imagebutton = (Button)myview.findViewById(R.id.imagebutton);
        callbutton = (Button)myview.findViewById(R.id.callbutton);

        registertitle = (TextView)myview.findViewById(R.id.registertitle);
        registertitle.setTypeface(custom_font);

        registertype= (TextView)myview.findViewById(R.id.registertype);
        registertype.setTypeface(custom_font1);

        registerVenue= (TextView)myview.findViewById(R.id.registerVenue);
        registerVenue.setTypeface(custom_font1);


        registerPrice= (TextView)myview.findViewById(R.id.registerPrice);
        registerPrice.setTypeface(custom_font1);


        registertime= (TextView)myview.findViewById(R.id.registertime);
        registertime.setTypeface(custom_font1);


        registerdate= (TextView)myview.findViewById(R.id.registerdate);
        registerdate.setTypeface(custom_font1);


        registerorg= (TextView)myview.findViewById(R.id.registerorg);
        registerorg.setTypeface(custom_font1);


        registercont= (TextView)myview.findViewById(R.id.registercont);
        registercont.setTypeface(custom_font1);


        registeremail= (TextView)myview.findViewById(R.id.registeremail);
        registeremail.setTypeface(custom_font1);


        registerlink= (TextView)myview.findViewById(R.id.registerlink);
        registerlink.setTypeface(custom_font1);


        registerdescript= (TextView)myview.findViewById(R.id.registerdescript);
        registerdescript.setTypeface(custom_font1);



        //        snackbar with progressbar

        bar = Snackbar.make(container, "Loading Data", Snackbar.LENGTH_INDEFINITE);
        ViewGroup contentLay = (ViewGroup) bar.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
        ProgressBar item = new ProgressBar(getContext());
        item.setIndeterminate(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);

        item.setLayoutParams(params);

        ViewGroup.MarginLayoutParams nextparams = (ViewGroup.MarginLayoutParams) item.getLayoutParams();
        nextparams.bottomMargin = 10;
        nextparams.topMargin = 10;
        nextparams.rightMargin = 10;
        item.requestLayout();


        contentLay.addView(item);


        bardownload = Snackbar.make(container, "Downloading", Snackbar.LENGTH_INDEFINITE);
        ViewGroup contentLaydownload = (ViewGroup) bardownload.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
        ProgressBar downloaditem = new ProgressBar(getContext());
        downloaditem.setIndeterminate(true);
        LinearLayout.LayoutParams paramsdown = new LinearLayout.LayoutParams(50, 50);

        downloaditem.setLayoutParams(paramsdown);

        ViewGroup.MarginLayoutParams nextparamsdown = (ViewGroup.MarginLayoutParams) downloaditem.getLayoutParams();
        nextparamsdown.bottomMargin = 10;
        nextparamsdown.topMargin = 10;
        nextparamsdown.rightMargin = 10;
        downloaditem.requestLayout();


        contentLaydownload.addView(downloaditem);


        // end



        internalDatabaseForSavedCards  = new InternalDatabaseForSavedCards(getActivity().getApplicationContext());
            eventDetailsFromDatabase = new EventDetailsFromDatabase();
        eventDetailsFromDatabase.setTaskdone(false);

//        progressbar=new ProgressDialog(getActivity());
//        progressbar.setTitle("Loading Data");
//        progressbar.setMessage("Please Wait");
//        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressbar.setCancelable(false);
//        progressbar.show();
//

        imagebutton.setVisibility(View.GONE);
        callbutton.setVisibility(View.GONE);
        linkbutton.setVisibility(View.GONE);



        getActivity().getSupportLoaderManager().initLoader(idnumber,null,loaderCallbacks);

        imagebutton.setOnClickListener(this);
        imagedownload.setOnClickListener(this);
        callbutton.setOnClickListener(this);
        linkbutton.setOnClickListener(this);

        setHasOptionsMenu(true);
        return myview;
    }


    private LoaderManager.LoaderCallbacks<EventDetailsFromDatabase> loaderCallbacks = new LoaderManager.LoaderCallbacks<EventDetailsFromDatabase>() {
        @Override
        public Loader<EventDetailsFromDatabase> onCreateLoader(int id, Bundle args) {

            bar.show();

            return new TaskToLoadCardDataForRegister(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<EventDetailsFromDatabase> loader, EventDetailsFromDatabase data) {


            getActivity().getSupportLoaderManager().destroyLoader(idnumber);
            bar.dismiss();

            if (data == null)
            {
                Toast.makeText(getContext(),"Network Error",Toast.LENGTH_LONG).show();
                String res = "No Internet Connection";
                registertitle.setText(res);



            }

            if(data!=null)
            {
                eventDetailsFromDatabase = data;
                fillthedata();
            }

        }

        @Override
        public void onLoaderReset(Loader<EventDetailsFromDatabase> loader) {

            eventDetailsFromDatabase = new EventDetailsFromDatabase();

        }
    };


        private String getdataforsharecard()
        {
            String dataforcard = "";

            String temp = eventDetailsFromDatabase.getTitle();
            dataforcard = dataforcard + temp + "\n";
            temp = "TYPE : " + eventDetailsFromDatabase.getType();
            dataforcard = dataforcard + temp + "\n";
            temp = "VENUE: " + eventDetailsFromDatabase.getVenue();
            dataforcard = dataforcard + temp + "\n";
            temp = "PRICE : Rs " + eventDetailsFromDatabase.getPrice();
            dataforcard = dataforcard + temp + "\n";
            temp = "TIME : " + eventDetailsFromDatabase.getTiming();
            dataforcard = dataforcard + temp + "\n";
            temp = "DATE : " + eventDetailsFromDatabase.getDates();
            dataforcard = dataforcard + temp + "\n";
            temp =  "ORGANIZER : " + eventDetailsFromDatabase.getOrganizer();
            dataforcard = dataforcard + temp + "\n";
            temp = "Contact : " + eventDetailsFromDatabase.getContact();
            dataforcard = dataforcard + temp + "\n";
            temp = "Email-Id : " + eventDetailsFromDatabase.getEmailid();
            dataforcard = dataforcard + temp + "\n";
            temp =  "Web Link : " + eventDetailsFromDatabase.getWeblink();
            dataforcard = dataforcard + temp + "\n";
            temp = "Description: " + eventDetailsFromDatabase.getDescription();
            dataforcard = dataforcard + temp + "\n";
            temp = "Booking Link : "+ eventDetailsFromDatabase.getDirectlink();
            dataforcard = dataforcard + temp + "\n";


            return dataforcard;
        }


        private void fillthedata()
        {
            String temp;
            registertitle.setText(eventDetailsFromDatabase.getTitle());
            temp = "TYPE : " + eventDetailsFromDatabase.getType();
            registertype.setText(temp);
            temp = "VENUE: " + eventDetailsFromDatabase.getVenue();
            registerVenue.setText(temp);
            temp = "PRICE : Rs " + eventDetailsFromDatabase.getPrice();
            registerPrice.setText(temp);
            temp = "TIME : " + eventDetailsFromDatabase.getTiming();
            registertime.setText(temp);
            temp = "DATE : " + eventDetailsFromDatabase.getDates();
            registerdate.setText(temp);
            temp =  "ORGANIZER : " + eventDetailsFromDatabase.getOrganizer();
            registerorg.setText(temp);
            temp = "Contact : " + eventDetailsFromDatabase.getContact();
            registercont.setText(temp);
            temp = "Email-Id : " + eventDetailsFromDatabase.getEmailid();
            registeremail.setText(temp);
            temp =  "Web Link : " + eventDetailsFromDatabase.getWeblink();
            registerlink.setText(temp);
            temp = "Description: " + eventDetailsFromDatabase.getDescription();
            registerdescript.setText(temp);

            directlinkforregistration = eventDetailsFromDatabase.getUpcomingpicpath();


            imagebutton.setVisibility(View.VISIBLE);
            linkbutton.setVisibility(View.VISIBLE);
            callbutton.setVisibility(View.VISIBLE);

    eventDetailsFromDatabase.setTaskdone(true);
            if (eventDetailsFromDatabase.getUpcomingpicpath().equals("N.A") || eventDetailsFromDatabase.getUpcomingpicpath().isEmpty())
            {
                    imagebutton.setVisibility(View.GONE);
            }
            if (eventDetailsFromDatabase.getDirectlink().equals("N.A") || eventDetailsFromDatabase.getDirectlink().isEmpty())
            {
                linkbutton.setVisibility(View.GONE);
            }
            if (eventDetailsFromDatabase.getContact().equals("N.A") || eventDetailsFromDatabase.getContact().isEmpty())
            {
                    callbutton.setVisibility(View.GONE);
            }


        }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.imagebutton)
        {
            downloadimagetask();
        }
        else if (v.getId()==R.id.callbutton)
        {
            Uri uri;

            String phoneno = eventDetailsFromDatabase.getContact();
            phoneno = "tel:"+phoneno;


            uri=Uri.parse(phoneno);
            Intent i3=new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i3);

        }
        else if (v.getId()==R.id.linkbutton)
        {
            Uri uri;
            directbookinglink = eventDetailsFromDatabase.getDirectlink();
//            if (!directbookinglink.contains("http://"))
//            {
//                directbookinglink = "http://" + directbookinglink;
//            }

            uri=Uri.parse(directbookinglink);
            Intent i=new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        }
        else if(v.getId()==R.id.imagedownloaded)
        {



            try {

                Intent intent = new Intent(getContext(),FullScreenImageView.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                imagebitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("image", byteArray);
                startActivity(intent);

            }catch (Exception e)
            {

            }

        }



    }

    private void downloadimagetask()
    {

            if (imagebitmap == null)
            {
                getActivity().getSupportLoaderManager().initLoader(idnumber + 100000,null,imagecallback);
            }

    }


//    public  boolean isStoragePermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
//                return true;
//            } else {
//
//                Log.v(TAG,"Permission is revoked");
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                return false;
//            }
//        }
//        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG,"Permission is granted");
//            return true;
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
//            //resume tasks needing this permission
//
//                saveImageToExternalStorage(imagebitmap);
//
//        }
//    }



    private boolean hasPermissions(){
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(getContext(),perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMS_REQUEST_CODE:

                for (int res : grantResults){
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed){
            //user granted all permissions we can perform our task.
            saveImageToExternalStorage(imagebitmap);
        }
        else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(getContext(), "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }





    private void saveImageToExternalStorage(Bitmap bitmap) {

        //always save as

        if (bitmap == null)
        {
            return;
        }



        String fileName = "workshop.jpg";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(ExternalStorageDirectory + File.separator + fileName);

        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes.toByteArray());

            ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
            String imagePath = file.getAbsolutePath();
            String name = file.getName();
            String description = "Workshop Info";
            String savedURL = MediaStore.Images.Media
                    .insertImage(cr, imagePath, name, description);

            Toast.makeText(getContext(), savedURL, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    LoaderManager.LoaderCallbacks<Bitmap> imagecallback = new LoaderManager.LoaderCallbacks<Bitmap>() {
        @Override
        public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
            bardownload.show();
            return new TaskToGetCardImage(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {

            bardownload.dismiss();

            imagebitmap = data;
            if (imagebitmap == null)
            {
                Toast.makeText(getContext(),"Network Error..",Toast.LENGTH_LONG).show();
            }


            if (imagebitmap != null)
            {
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, imagebitmap.getWidth(), getActivity().getApplicationContext().getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, imagebitmap.getHeight(), getActivity().getApplicationContext().getResources().getDisplayMetrics());

                imagedownload.setImageBitmap(ImageScalig.BITMAP_RESIZER(imagebitmap,300,300));
                imagedownload.setVisibility(View.VISIBLE);

                imagedownload.setMinimumWidth(width);
                imagedownload.setMinimumHeight(height);

                if(hasPermissions())
                saveImageToExternalStorage(imagebitmap);
                else
                    requestPerms();


                }

            getActivity().getSupportLoaderManager().destroyLoader(idnumber + 100000);



        }

        @Override
        public void onLoaderReset(Loader<Bitmap> loader) {
            imagebitmap = null;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_card_to_save,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.savemycardofline)
        {
            if(eventDetailsFromDatabase.isTaskdone()==false)
            {
                Toast.makeText(getActivity().getApplicationContext(),"Unable to save .. \n Please try again later",Toast.LENGTH_LONG).show();
                return true;
            }
                    Boolean result = internalDatabaseForSavedCards.insertdataintointernaldatabase(eventDetailsFromDatabase);

                    if (result)
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Unable to save .. \n Please try again later",Toast.LENGTH_LONG).show();
                    }
        }
        else if (item.getItemId()== R.id.sharecard)
        {
            Intent sendcard=new Intent();
            sendcard.setAction(Intent.ACTION_SEND);
            sendcard.putExtra(Intent.EXTRA_TEXT,getdataforsharecard());
            sendcard.setType("text/plain");
            startActivity(Intent.createChooser(sendcard,"Share card via"));

        }

        return true;
    }
}


