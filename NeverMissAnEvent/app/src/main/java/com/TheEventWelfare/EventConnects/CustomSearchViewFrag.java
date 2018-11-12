package com.TheEventWelfare.EventConnects;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomSearchViewFrag extends Fragment implements View.OnClickListener {

    Snackbar bar;
    Snackbar snackbar1;

    ImageButton backbuttonsearch,finalsearchbutton;

    RelativeLayout mainlayoutforsearch;

    RecyclerView myrecyclerviewsearch;
    RegisterAdapterForRecyclerView myadapter;
    private List<DataForRegisterPage> mydatalist;
    LinearLayoutManager linearLayoutManager;


    static Boolean can_call;

    static int id = 0;
    TextView searchtext;
    static String searchstring = "";



    public CustomSearchViewFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_custom_search_view, container, false);





        backbuttonsearch = (ImageButton)myview.findViewById(R.id.backbuttonsearch);

        finalsearchbutton =(ImageButton)myview.findViewById(R.id.finalsearchbutton);

        searchtext = (TextView)myview.findViewById(R.id.searchtext);

        can_call = false;

        mainlayoutforsearch =(RelativeLayout) myview.findViewById(R.id.mainlayoutforsearch);

        mydatalist=new ArrayList<>();

        myrecyclerviewsearch=(RecyclerView)myview.findViewById(R.id.myrecyclerviewsearch);

        myadapter=new RegisterAdapterForRecyclerView(getContext(),mydatalist);

        //        snackbar with progressbar

        bar = Snackbar.make(container, "Loading More Data", Snackbar.LENGTH_INDEFINITE);
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


        // end

        // snackbar with callbacks
        snackbar1 = Snackbar
                .make(container, "Network Error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bar.show();
                        id = id + 10;
                        Intent intent = new Intent(TaskToLoadDataForSeacrh.ACTION_BROADCAST);
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                    }
                });
        snackbar1.setActionTextColor(Color.RED);

        // end

        if (!isNetworkAvailable()){

            snackbar1.show();

        }


        linearLayoutManager = new LinearLayoutManager(getContext());


        myrecyclerviewsearch.setAdapter(myadapter);


        myrecyclerviewsearch.setLayoutManager(linearLayoutManager);




        myrecyclerviewsearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (!isNetworkAvailable()){
                    bar.dismiss();
                    snackbar1.show();

                    return;
                }

                if (can_call && id < 500) {

                    bar.show();
                    id = id + 10;

                    Intent intent = new Intent(TaskToLoadDataForSeacrh.ACTION_BROADCAST);
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                }else{
                    bar.dismiss();

                }
            }

        });




        backbuttonsearch.setOnClickListener(this);
        finalsearchbutton.setOnClickListener(this);



        return myview;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.backbuttonsearch) {

            can_call = false;

            getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_for_custom_search);

            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();

        }



        else if(v.getId()==R.id.finalsearchbutton)
        {

            try {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainlayoutforsearch.getWindowToken(), 0);
            }
            catch (Exception e)
            {
            }




            if (searchtext.getText().toString().isEmpty())
            {
                return;
            }
            if(searchstring.equals(searchtext.getText().toString()))
            {
                return;
            }

            can_call = true;

            searchstring = searchtext.getText().toString().toUpperCase();

            id = 0;

            mydatalist.clear();
            myadapter.notifyDataSetChanged();
            getActivity().getSupportLoaderManager().restartLoader(R.id.loader_for_custom_search,null,loaderCallbacks);

            can_call = false;


        }



    }




    private LoaderManager.LoaderCallbacks<List<DataForRegisterPage>> loaderCallbacks= new LoaderManager.LoaderCallbacks<List<DataForRegisterPage>>() {
        @Override
        public Loader<List<DataForRegisterPage>> onCreateLoader(int id, Bundle args) {
            bar.show();

            return new TaskToLoadDataForSeacrh(getContext().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<List<DataForRegisterPage>> loader, List<DataForRegisterPage> data) {

            bar.dismiss();

            if (data!=null) {
                mydatalist.clear();
                mydatalist.addAll(data);
                myadapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<DataForRegisterPage>> loader) {
            id=0;
            mydatalist=new ArrayList<>();
            myadapter.notifyDataSetChanged();
        }
    };

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (!visible && bar != null ){
            bar.dismiss();
        }
    }



}
