package com.TheEventWelfare.EventConnects;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Competition extends Fragment {


    public Competition() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_competition, container, false);
        setHasOptionsMenu(true);
        return myview;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menuforcompetitions,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.myfilter:
                Toast.makeText(getActivity(),"Set Filter",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mycomp:
                Toast.makeText(getActivity(),"No Events ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.accountsettings:
                Toast.makeText(getActivity(),"Settings for accounts ",Toast.LENGTH_SHORT).show();

                break;
            case R.id.search:
                Toast.makeText(getActivity(),"Search",Toast.LENGTH_SHORT).show();
                break;
            case R.id.notify:
                Toast.makeText(getActivity(),"Notify",Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

}
