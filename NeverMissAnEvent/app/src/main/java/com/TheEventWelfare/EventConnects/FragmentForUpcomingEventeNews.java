package com.TheEventWelfare.EventConnects;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForUpcomingEventeNews extends Fragment {

    ProgressBar progressthought;
    Snackbar bar;
    TextView mythoughts;
    static int idupcoming = 0 ;
    RecyclerView upcomingimageeventnews ;
    private List<EventNewsGenericDataType<Bitmap>> dataforupcoming ;
    PicEventNewsRecyclerView adapterupcoming ;
    GridLayoutManager gridLayoutManager ;
    Context context;





    public FragmentForUpcomingEventeNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_fragment_for_upcoming_evente_news, container, false);

        progressthought = (ProgressBar)myview.findViewById(R.id.progressthought);



        context = getActivity().getApplicationContext();

        mythoughts = (TextView)myview.findViewById(R.id.mythoughts);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/NotoSerif-Regular.ttf");
        mythoughts.setTypeface(custom_font);

        dataforupcoming = new ArrayList<>();

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


        // end



        upcomingimageeventnews = (RecyclerView)myview.findViewById(R.id.upcomingimageeventnews);

        adapterupcoming = new PicEventNewsRecyclerView(context,dataforupcoming);

        gridLayoutManager = new GridLayoutManager(context,2);


        getActivity().getSupportLoaderManager().initLoader(R.id.loder_id_upcoming,null,loaderCallbacksforupcoming);
        getActivity().getSupportLoaderManager().initLoader(R.id.loader_id_thoughts,null,loaderCallbacksthoughts);

        upcomingimageeventnews.setAdapter(adapterupcoming);

        upcomingimageeventnews.setLayoutManager(gridLayoutManager);




        upcomingimageeventnews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!isNetworkAvailable()){
                    bar.dismiss();
                    return;
                }

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition()== dataforupcoming.size()-1)
                {
                    if (dataforupcoming.size() != 0) {
                        idupcoming = dataforupcoming.get(dataforupcoming.size() - 1).getId();
                    }
                    if (dataforupcoming.size()!=0 && dataforupcoming.get(dataforupcoming.size() - 1).islastcontent()!= dataforupcoming.size() ) {
                        bar.show();

                        Intent intent = new Intent(TaskToGetImageUrl.ACTION_UPCOMING_BROADCAST);
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


    private LoaderManager.LoaderCallbacks<String> loaderCallbacksthoughts = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {

            return new TaskToLoadThought(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            progressthought.setVisibility(View.GONE);

//            getActivity().getSupportLoaderManager().destroyLoader(R.id.loader_id_thoughts);
            if(data!= null)
            {
                mythoughts.setText(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<List<EventNewsGenericDataType<Bitmap>>> loaderCallbacksforupcoming = new LoaderManager.LoaderCallbacks<List<EventNewsGenericDataType<Bitmap>>>() {
        @Override
        public Loader<List<EventNewsGenericDataType<Bitmap>>> onCreateLoader(int id, Bundle args) {
            bar.show();
            return new TaskToGetImageUrl(getActivity().getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<List<EventNewsGenericDataType<Bitmap>>> loader, List<EventNewsGenericDataType<Bitmap>> data) {
            bar.dismiss();
            if (data!= null)
            {
                dataforupcoming.clear();
                dataforupcoming.addAll(data);
                adapterupcoming.notifyDataSetChanged();
            }

        }

        @Override
        public void onLoaderReset(Loader<List<EventNewsGenericDataType<Bitmap>>> loader) {
            idupcoming = 0 ;
            dataforupcoming = new ArrayList<>();
            adapterupcoming.notifyDataSetChanged();
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
