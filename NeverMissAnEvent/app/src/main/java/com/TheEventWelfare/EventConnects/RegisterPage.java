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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterPage extends Fragment implements SearchView.OnQueryTextListener{


    Snackbar bar;
    RecyclerView myrecyclerview;
    RegisterAdapterForRecyclerView myadapter;
    Context context;
    private List<DataForRegisterPage> mydatalist;
    Snackbar snackbar1;
    static int ide=0;

    LinearLayoutManager linearLayoutManager;

    public RegisterPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View myview = inflater.inflate(R.layout.fragment_register_page, container, false);



        context=getContext();


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

        mydatalist=new ArrayList<>();

        myrecyclerview=(RecyclerView)myview.findViewById(R.id.myrecyclerview);

        myadapter=new RegisterAdapterForRecyclerView(context,mydatalist);

        linearLayoutManager = new LinearLayoutManager(context);

        getActivity().getSupportLoaderManager().initLoader(R.id.loader_id,null,loaderCallbacks);

        // snackbar with callbacks
       snackbar1 = Snackbar
                .make(container, "Network Error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bar.show();
                        Intent intent = new Intent(TasktoLoadList.ACTION_BROADCAST);
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);


                    }
                });
        snackbar1.setActionTextColor(Color.RED);

        // end

        if (!isNetworkAvailable()){

            snackbar1.show();

        }
        myrecyclerview.setAdapter(myadapter);



        myrecyclerview.setLayoutManager(linearLayoutManager);

        myrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (!isNetworkAvailable()){
                    if (mydatalist.size() != 0) {
                        ide = mydatalist.get(mydatalist.size() - 1).getId();
                    }
                    bar.dismiss();
                    snackbar1.show();
                    return;
                }
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==mydatalist.size()-1)
                {

                    if (mydatalist.size() != 0) {
                        ide = mydatalist.get(mydatalist.size() - 1).getId();
                    }
                    if (mydatalist.size()!=0 && mydatalist.get(mydatalist.size()-1).islastcontent()!= mydatalist.size() ) {
                        bar.show();
                        Intent intent = new Intent(TasktoLoadList.ACTION_BROADCAST);
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                    }
                }else{
                    bar.dismiss();
                }
            }

        });





        setHasOptionsMenu(true);

        return myview;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menuforbookseat,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
//            case R.id.myfilter:
//                AlertDialog.Builder myfilterbuilder=new AlertDialog.Builder(getActivity());
//                LayoutInflater myinflator=getActivity().getLayoutInflater();
//                View dialogview=myinflator.inflate(R.layout.filterforregister,null);
//                myfilterbuilder.setView(dialogview);
//                myfilterbuilder.setCancelable(true);
//                myfilterbuilder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog filteralert=myfilterbuilder.create();
//                filteralert.setTitle("Filter Options");
//                filteralert.show();
//
//                break;
//            case R.id.accountsettings:
//                Toast.makeText(getActivity(),"Setting for accounts ",Toast.LENGTH_SHORT).show();
//
//                break;
            case R.id.search:


                CustomSearchViewFrag customSearchViewFrag=new CustomSearchViewFrag();

                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, customSearchViewFrag);
                fragmentTransaction.addToBackStack("forbackbuttonfrag").commit();









//                SearchView searchview= (SearchView) MenuItemCompat.getActionView(item);
//                searchview.setOnQueryTextListener(this);
                break;
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        List<DataForRegisterPage> filtereddata=new ArrayList<>();



        for(int i=0;i<mydatalist.size();i++)
        {
            String str=mydatalist.get(i).getTitlename().toLowerCase();
            if(str.contains(newText))
            {
                filtereddata.add(mydatalist.get(i));
            }
        }
        myadapter.setFilter(filtereddata);
        return true;
    }

    private LoaderManager.LoaderCallbacks<List<DataForRegisterPage>> loaderCallbacks= new LoaderManager.LoaderCallbacks<List<DataForRegisterPage>>() {
        @Override
        public Loader<List<DataForRegisterPage>> onCreateLoader(int id, Bundle args) {
            bar.show();

            return new TasktoLoadList(getActivity().getApplicationContext());
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
            ide=0;
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