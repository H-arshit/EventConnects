package com.TheEventWelfare.EventConnects;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
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
public class FragmentForPastPics extends Fragment {

    static int idpastpic = 0;

    Snackbar bar;


    RecyclerView pastimageseventnews;
    private List<EventNewsGenericDataType<Bitmap>> dataforpastpic;
    PicEventNewsRecyclerView adapterpast;
    GridLayoutManager gridLayoutManager;
    Context context;





    public FragmentForPastPics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_fragment_for_past_pics, container, false);


        context = getActivity().getApplicationContext();

        dataforpastpic = new ArrayList<>();


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


        pastimageseventnews = (RecyclerView)myview.findViewById(R.id.pastimageseventnews);

        adapterpast = new PicEventNewsRecyclerView(context,dataforpastpic);

        gridLayoutManager = new GridLayoutManager(context,2);

        getActivity().getSupportLoaderManager().initLoader(R.id.loader_id_pastpic,null,loaderCallbacksforpastpic);

        pastimageseventnews.setAdapter(adapterpast);

        pastimageseventnews.setLayoutManager(gridLayoutManager);

        pastimageseventnews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (!isNetworkAvailable()){
                    bar.dismiss();
                    return;
                }

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition()==dataforpastpic.size()-1) {

                    if (dataforpastpic.size()!= 0)
                    idpastpic = dataforpastpic.get(dataforpastpic.size() - 1).getId();

                    if (dataforpastpic.size() != 0 && dataforpastpic.get(dataforpastpic.size() - 1).islastcontent()!=dataforpastpic.size() ) {
                        bar.show();
                        Intent intent = new Intent(TaskToGetPastPicsEventNews.ACTION_PASTPIC_BROADCAST);
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



    private LoaderManager.LoaderCallbacks<List<EventNewsGenericDataType<Bitmap>>> loaderCallbacksforpastpic = new LoaderManager.LoaderCallbacks<List<EventNewsGenericDataType<Bitmap>>>() {
        @Override
        public Loader<List<EventNewsGenericDataType<Bitmap>>> onCreateLoader(int id, Bundle args) {


            bar.show();
            return new TaskToGetPastPicsEventNews(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<List<EventNewsGenericDataType<Bitmap>>> loader, List<EventNewsGenericDataType<Bitmap>> data) {

            bar.dismiss();


            if (data!= null)
            {
                dataforpastpic.clear();
                dataforpastpic.addAll(data);
                adapterpast.notifyDataSetChanged();
            }

        }

        @Override
        public void onLoaderReset(Loader<List<EventNewsGenericDataType<Bitmap>>> loader) {
            idpastpic = 0 ;
            dataforpastpic = new ArrayList<>();
            adapterpast.notifyDataSetChanged();
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
