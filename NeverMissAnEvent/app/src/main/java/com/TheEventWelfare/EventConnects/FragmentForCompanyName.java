package com.TheEventWelfare.EventConnects;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForCompanyName extends Fragment {

    static int idcompany = 0;

    Snackbar bar;


    RecyclerView compaieslisteventnews;
    private List<EventNewsGenericDataType<String>> companynamedata;
    AdapterForCompanyName adapterForCompanyName;
    LinearLayoutManager linearLayoutManagerforcompany;
    Context context;


    public FragmentForCompanyName() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_fragment_for_company_name, container, false);


        context = getActivity().getApplicationContext();

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

        companynamedata = new ArrayList<>();

        compaieslisteventnews = (RecyclerView)myview.findViewById(R.id.compaieslisteventnews);

        adapterForCompanyName = new AdapterForCompanyName(context,companynamedata);

        linearLayoutManagerforcompany = new LinearLayoutManager(context);



        getActivity().getSupportLoaderManager().initLoader(R.id.loader_id_companyname,null,loaderCallbacksforcompanynames);

        compaieslisteventnews.setAdapter(adapterForCompanyName);

        compaieslisteventnews.setLayoutManager(linearLayoutManagerforcompany);



        compaieslisteventnews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (!isNetworkAvailable()){
                    bar.dismiss();
                    return;
                }

                if(linearLayoutManagerforcompany.findLastCompletelyVisibleItemPosition()==companynamedata.size()-1) {

                    if (companynamedata.size() != 0)
                    idcompany = companynamedata.get(companynamedata.size() - 1).getId();

                    if (companynamedata.size() != 0 && companynamedata.get(companynamedata.size()-1).islastcontent()!= companynamedata.size() ){
                        bar.show();

                        Intent intent = new Intent(TaskToGetCompanyNames.ACTION_COMPANY_BROADCAST);
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                    }

                }else{
                    bar.dismiss();
                }
            }
        });





        return myview;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    private LoaderManager.LoaderCallbacks<List<EventNewsGenericDataType<String>>> loaderCallbacksforcompanynames = new LoaderManager.LoaderCallbacks<List<EventNewsGenericDataType<String>>>() {
        @Override
        public Loader<List<EventNewsGenericDataType<String>>> onCreateLoader(int id, Bundle args) {
            bar.show();
            return new TaskToGetCompanyNames(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<List<EventNewsGenericDataType<String>>> loader, List<EventNewsGenericDataType<String>> data) {
            bar.dismiss();
            if (data!=null)
            {
                companynamedata.clear();
                companynamedata.addAll(data);
                adapterForCompanyName.notifyDataSetChanged();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<EventNewsGenericDataType<String>>> loader) {
            idcompany = 0 ;
            companynamedata = new ArrayList<>();
            adapterForCompanyName.notifyDataSetChanged();
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
