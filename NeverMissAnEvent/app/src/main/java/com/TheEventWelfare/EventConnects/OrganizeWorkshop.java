package com.TheEventWelfare.EventConnects;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizeWorkshop extends Fragment implements View.OnClickListener {
    Context context;
    Integer REQUEST_CAMERA=1,REQUEST_FILE=0;
    private Button nextbutton,pasteventimage,upcomingeventimage;
    private ImageView upcomingeventimageview,pasteventimageview;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1212;
    private RadioGroup rdgrp;
    private RadioButton rdbtn;
    private EditText titleinp,venueinp,priceinp,fromdte,todte,fromtme,totme,orgcmpyname,phonenumber,emailid,weblink,descripinp,directbooklink;
    View viewobject;
    String pastimagecode="",upcomingimagecode="";
    ProgressDialog progressbar;
    Bitmap pastimage ,upcomingimage;

    public OrganizeWorkshop() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_organize_workshop, container, false);
        context=getContext();

        viewobject=myview;

        nextbutton=(Button)myview.findViewById(R.id.nextbutton);
        rdgrp=(RadioGroup)myview.findViewById(R.id.rdgrp);
        titleinp=(EditText)myview.findViewById(R.id.titleinp);
        venueinp=(EditText)myview.findViewById(R.id.venueinp);
        priceinp=(EditText)myview.findViewById(R.id.priceinp);
        fromdte=(EditText)myview.findViewById(R.id.fromdte);
        todte=(EditText)myview.findViewById(R.id.todte) ;
        fromtme=(EditText)myview.findViewById(R.id.fromtme);
        totme=(EditText)myview.findViewById(R.id.totme);
        orgcmpyname=(EditText)myview.findViewById(R.id.orgcmpyname);
        descripinp=(EditText)myview.findViewById(R.id.descripinp);
        weblink=(EditText)myview.findViewById(R.id.weblink);
        emailid=(EditText)myview.findViewById(R.id.emailid);
        phonenumber=(EditText)myview.findViewById(R.id.phonenumber);
        directbooklink=(EditText)myview.findViewById(R.id.directbooklink);

        pasteventimage=(Button)myview.findViewById(R.id.pasteventimage);
        upcomingeventimage=(Button)myview.findViewById(R.id.upcomingeventimage);

        upcomingeventimageview=(ImageView)myview.findViewById(R.id.upcomingeventimageview);
        pasteventimageview=(ImageView)myview.findViewById(R.id.pasteventimageview);

        progressbar=new ProgressDialog(getActivity());
        progressbar.setTitle("Checking");
        progressbar.setMessage("Please Wait");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setCancelable(false);

        if (pastimage!=null){
            pasteventimageview.setImageBitmap(ImageScalig.BITMAP_RESIZER(pastimage,150,150));

        }
        if (upcomingimage!=null){
            upcomingeventimageview.setImageBitmap(ImageScalig.BITMAP_RESIZER(upcomingimage,150,150));

        }



//        setHasOptionsMenu(true);
        upcomingeventimage.setOnClickListener(this);
        pasteventimage.setOnClickListener(this);
        fromtme.setOnClickListener(this);
        totme.setOnClickListener(this);
        nextbutton.setOnClickListener(this);
        fromdte.setOnClickListener(this);
        todte.setOnClickListener(this);



        if (!isNetworkAvailable())
        {
            shaowalertdialog();
        }

        showtermsandcondition();




        return myview;
    }

    private void showtermsandcondition() {

        AlertDialog.Builder termsandcon = new AlertDialog.Builder(getActivity());
        termsandcon.setMessage("Your Information Will Be Available Within 24 Hours After A Short Confirmation.\nMake Sure All Data You Provide Is Legal");
        termsandcon.setCancelable(false);
        termsandcon.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        AlertDialog showterms = termsandcon.create();
        showterms.setTitle("IMPORTANT NOTIFICATION");
        showterms.show();



    }

    private void shaowalertdialog() {


        AlertDialog.Builder nointernet = new AlertDialog.Builder(getActivity());
        nointernet.setMessage("No Internet Available . \n Internet Connection Needed To Complete The Task");
        nointernet.setCancelable(false);
        nointernet.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        AlertDialog nointernetalert = nointernet.create();
        nointernetalert.setTitle("Alert!!");
        nointernetalert.show();

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }





    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {

            case R.id.pasteventimage:
                REQUEST_CAMERA=3;
                REQUEST_FILE=4;
                takeimagetask();

                break;

            case R.id.upcomingeventimage:

                REQUEST_CAMERA=1;
                REQUEST_FILE=0;
                takeimagetask();



                break;

            case R.id.fromdte:
                DialogFragment newFragment2 = new DatePickerFragmentFromDate();
                newFragment2.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.todte:
                DialogFragment newFragment3 = new DatePickerFragmentToDate();
                newFragment3.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.totme:
                DialogFragment newFragment = new TimePickerFragmenttotme();
                newFragment.show(getFragmentManager(),"TimePicker");
                break;
            case R.id.fromtme:
                DialogFragment newFragment1 = new TimePickerFragmentFromtme();
                newFragment1.show(getFragmentManager(),"TimePicker");
                break;
            case R.id.nextbutton: {
                if(!iscompleted())
                {
                    return;
                }

                Bundle i = new Bundle();

                if (R.id.tech == rdgrp.getCheckedRadioButtonId()) {
                    rdbtn = (RadioButton)viewobject.findViewById(R.id.tech);
                    i.putString("WorkType", rdbtn.getText().toString());
                } else if (R.id.nontech == rdgrp.getCheckedRadioButtonId()) {
                    rdbtn = (RadioButton)viewobject.findViewById(R.id.nontech);
                    i.putString("WorkType", rdbtn.getText().toString());
                }
                i.putString("TitleName", titleinp.getText().toString().toUpperCase());
                i.putString("Venue", venueinp.getText().toString());
                i.putString("Price", priceinp.getText().toString());
                i.putString("fromdate", fromdte.getText().toString());
                i.putString("todate", todte.getText().toString());
                i.putString("fromtime", fromtme.getText().toString());
                i.putString("totime", totme.getText().toString());
                i.putString("orgname", orgcmpyname.getText().toString());
                i.putString("phone", phonenumber.getText().toString());
                i.putString("email", emailid.getText().toString());
                i.putString("weblink", weblink.getText().toString());
                i.putString("description", descripinp.getText().toString());
                i.putString("directbookinglink",directbooklink.getText().toString());



                i.putString("upcomingimage",upcomingimagecode);
                i.putString("pastimage",pastimagecode);





                OrganiseFinalView callorganizefinal=new OrganiseFinalView();
                callorganizefinal.setArguments(i);
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, callorganizefinal);
                fragmentTransaction.addToBackStack("forbackbutton").commit();

            }
            break;
        }
    }






    private boolean iscompleted()
    {
        boolean iscorrect=true;
        if(titleinp.getText().toString().isEmpty())
        {
            titleinp.setError("Required");
            iscorrect=false;
        }
        if(venueinp.getText().toString().isEmpty())
        {
            venueinp.setError("Required");
            iscorrect=false;
        }
        if(priceinp.getText().toString().isEmpty())
        {
            priceinp.setError("Required");
            iscorrect=false;
        }
        if(phonenumber.getText().toString().isEmpty())
        {
            phonenumber.setError("Required");
            iscorrect=false;
        }
        return iscorrect;
    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//
//        getActivity().getMenuInflater().inflate(R.menu.main_option_menu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//
//        return;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(getActivity(),"To be created  Sorry for inconvinience",Toast.LENGTH_SHORT).show();
//        return true;
//    }

    public void takeimagetask()
    {
        final String[] menuforalert = {"Camera", "From Device"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setCancelable(true);
        builder.setItems(menuforalert, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {


                    Intent cameraintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraintent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivityForResult(cameraintent, REQUEST_CAMERA);


                } else if (which == 1) {


                    if(hasPermissions()) {

                        getfilefromexternalstorage();

                    }else{
                        requestPerms();
                    }

                }
            }

        });

        builder.show();

    }

    private void requestPerms(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

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
            getfilefromexternalstorage();
        }
        else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Toast.makeText(getContext(), "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    public void getfilefromexternalstorage(){
        Intent fileintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fileintent.setType("image/*");
        startActivityForResult(Intent.createChooser(fileintent, "Select File"), REQUEST_FILE);
    }

    private boolean hasPermissions(){

        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
            return true;
    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK)
        {
            if(requestCode==REQUEST_FILE)
            {
                Uri selectedimageuri = data.getData();
                Bitmap image;
                try {
                     image = decodeUri(getActivity(),selectedimageuri,150);
                } catch (FileNotFoundException e) {

                    Toast.makeText(getActivity(),"File Not Found",Toast.LENGTH_SHORT).show();
                    return;
                }


                if (REQUEST_FILE==4)
                {



                        pasteventimageview.setImageBitmap(image);


                        // converting the images to base64 string for easy transfer
                        if (pasteventimageview.getDrawable() != null) {


                            pastimage = image;


                            LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
                                @Override
                                public Loader<String> onCreateLoader(int id, Bundle args) {
                                    progressbar.show();
                                    return new TaskToConvertImage(getActivity().getApplicationContext(), pastimage);
                                }

                                @Override
                                public void onLoadFinished(Loader<String> loader, String data) {

                                    pastimagecode = data;
                                    getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_for_image_convert_upcom);
                                    progressbar.dismiss();
                                }

                                @Override
                                public void onLoaderReset(Loader<String> loader) {

                                }
                            };

                            getActivity().getSupportLoaderManager().initLoader(R.id.loader_for_image_convert_upcom, null, loaderCallbacks);


                        }




                }

                else if (REQUEST_FILE==0)
                {

                    upcomingeventimageview.setImageBitmap(image);



                                        // converting the images to base64 string for easy transfer
                                        if(upcomingeventimageview.getDrawable() != null)
                                        {
                                             upcomingimage=image;


                                            LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
                                                @Override
                                                public Loader<String> onCreateLoader(int id, Bundle args) {
                                                    progressbar.show();
                                                    return new TaskToConvertImage(getActivity().getApplicationContext(), upcomingimage);
                                                }

                                                @Override
                                                public void onLoadFinished(Loader<String> loader, String data) {

                                                    upcomingimagecode = data;
                                                    getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_for_image_convert_upcom);
                                                    progressbar.dismiss();
                                                }

                                                @Override
                                                public void onLoaderReset(Loader<String> loader) {

                                                }
                                            };

                                            getActivity().getSupportLoaderManager().initLoader(R.id.loader_for_image_convert_upcom,null,loaderCallbacks);



                                        }

                }

            }
            else if (requestCode==REQUEST_CAMERA)
            {
                Bundle bundle = data.getExtras();
                final Bitmap image=(Bitmap) bundle.get("data");
                if (REQUEST_CAMERA==3)
                {
                    pasteventimageview.setImageBitmap(ImageScalig.BITMAP_RESIZER(image,150,150));

                    // converting the images to base64 string for easy transfer
                    if(pasteventimageview.getDrawable() != null)
                    {
                        pastimage=image;


                        LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
                            @Override
                            public Loader<String> onCreateLoader(int id, Bundle args) {
                                progressbar.show();
                                return new TaskToConvertImage(getActivity().getApplicationContext(), pastimage);
                            }

                            @Override
                            public void onLoadFinished(Loader<String> loader, String data) {

                                pastimagecode = data;
                                getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_for_image_convert_upcom);
                                progressbar.dismiss();
                            }

                            @Override
                            public void onLoaderReset(Loader<String> loader) {

                            }
                        };

                        getActivity().getSupportLoaderManager().initLoader(R.id.loader_for_image_convert_upcom,null,loaderCallbacks);



                    }




                }

                else if (REQUEST_CAMERA==1)
                {
                    upcomingeventimageview.setImageBitmap(ImageScalig.BITMAP_RESIZER(image,150,150));


                    // converting the images to base64 string for easy transfer
                    if(upcomingeventimageview.getDrawable() != null)
                    {
                        upcomingimage=image;


                        LoaderManager.LoaderCallbacks<String> loaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
                            @Override
                            public Loader<String> onCreateLoader(int id, Bundle args) {
                                progressbar.show();
                                return new TaskToConvertImage(getActivity().getApplicationContext(), upcomingimage);
                            }

                            @Override
                            public void onLoadFinished(Loader<String> loader, String data) {

                                upcomingimagecode = data;
                                getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_for_image_convert_upcom);
                                progressbar.dismiss();
                            }

                            @Override
                            public void onLoaderReset(Loader<String> loader) {

                            }
                        };

                        getActivity().getSupportLoaderManager().initLoader(R.id.loader_for_image_convert_upcom,null,loaderCallbacks);



                    }
                }


            }

        }
    }
}
