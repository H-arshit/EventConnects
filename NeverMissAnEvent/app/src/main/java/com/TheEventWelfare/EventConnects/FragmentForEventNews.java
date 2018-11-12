package com.TheEventWelfare.EventConnects;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentForEventNews extends Fragment {



    TabLayout mytablayout;
    ViewPager myviewpager;
    public static final int nosoftab=3;
    public static final String[] tabnames={"Latest","Companies","PastEvents"};


    public FragmentForEventNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview =  inflater.inflate(R.layout.fragment_fragment_for_event_news, container, false);

        mytablayout=(TabLayout)myview.findViewById(R.id.mytablayout);
        myviewpager=(ViewPager)myview.findViewById(R.id.myviewpager);

        myviewpager.setAdapter(new MyAdapter(getChildFragmentManager()));
        myviewpager.post(new Runnable() {
            @Override
            public void run() {
                mytablayout.setupWithViewPager(myviewpager);
            }
        });

        return myview;



    }

    private class MyAdapter extends FragmentPagerAdapter
    {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabnames[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new FragmentForUpcomingEventeNews();

                case 1:
                    return new FragmentForCompanyName();
                case 2:
                    return new FragmentForPastPics();
            }

            return null;
        }

        @Override
        public int getCount() {
            return nosoftab;
        }
    }

}
