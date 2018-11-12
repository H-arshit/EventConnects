package com.TheEventWelfare.EventConnects;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

public class NavigationDrawerPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        private DrawerLayout drawerlayoutid;
        private ActionBarDrawerToggle togglebutton;
        private NavigationView navigationaccess;
        private final String PREF_NAME = "com.data.workshop.event";
       static TextView usernamenav;
        static ImageView image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_page);




        drawerlayoutid=(DrawerLayout)findViewById(R.id.drawerlayoutid);
        togglebutton=new ActionBarDrawerToggle(NavigationDrawerPage.this,drawerlayoutid,R.string.open,R.string.close);


        drawerlayoutid.addDrawerListener(togglebutton);
        togglebutton.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mydefaultfragment();


        navigationaccess=(NavigationView)findViewById(R.id.navigationaccess);

        View headerview  = navigationaccess.getHeaderView(0);

        usernamenav = (TextView)headerview.findViewById(R.id.usernamenav);
        image = (ImageView) headerview.findViewById(R.id.image_view_name_initials);



        namedisplaynav();
        navigationaccess.setNavigationItemSelectedListener(this);



    }

    private void mydefaultfragment()
    {
        Fragment mydeffrag=null;

        Class fragmentclass=FragmentHolderForEventNews.class;

        try {
            mydeffrag = (Fragment) fragmentclass.newInstance();
        } catch (Exception e) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        setTitle("Event News");
        fragmentManager.beginTransaction().replace(R.id.frame, mydeffrag).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(togglebutton.onOptionsItemSelected(item))
        {
            return true;
         }
        return super.onOptionsItemSelected(item);
            }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {



        Fragment fragmentformenuitems=null;
        Class fragmentclass=null;

        switch (item.getItemId())
        {
            case R.id.eventnews:
                fragmentclass=FragmentHolderForEventNews.class;
                break;
            case R.id.registerworkshop:
                fragmentclass=RegisterPage.class;
                break;
            case R.id.organsie:
                fragmentclass=OrganizeWorkshop.class;
                break;
            case R.id.savedworkshop:
                fragmentclass=SavedCardsList.class;
                break;
//            case R.id.festdata:
//                fragmentclass=Fest.class;
//                break;
//            case R.id.competititons:
//                fragmentclass=Competition.class;
//                break;
            case R.id.Settingforapp:
                fragmentclass = AccountSettings.class;
                break;
            case R.id.about:
            {
                AlertDialog.Builder aboutpage = new AlertDialog.Builder(this);
                aboutpage.setMessage("# This app aims to bring every event occuring near you into your notice so that you never miss a " +
                        "chance to attend them " +
                        " . In case of problem write to eventconnects.in@gmail.com ");
                aboutpage.setCancelable(false);
                aboutpage.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.cancel();


                    }
                });


                AlertDialog about = aboutpage.create();
                about.setTitle("About");
                about.show();
            }
                break;
            case R.id.feedback:
                fragmentclass = Feedback.class;
                break;
            case R.id.reportproblem:
                fragmentclass=ReportFraud.class;
                break;
//            case R.id.shareapp:
//                Toast.makeText(this,"Spread Me",Toast.LENGTH_SHORT).show();
//                break;
            case R.id.logout:


                SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editorffordata = sharedPreferences.edit();
                editorffordata.putString("USERNAME","");
                editorffordata.putString("PASSWORD","");
                editorffordata.putString("CONTACT","");
                editorffordata.putString("EMAIL_ID","");
                editorffordata.commit();

                getSupportLoaderManager().destroyLoader(R.id.loder_id_upcoming);
                getSupportLoaderManager().destroyLoader(R.id.loader_id_pastpic);
                getSupportLoaderManager().destroyLoader(R.id.loader_id_companyname);


                try{
                    getSupportLoaderManager().destroyLoader(R.id.loader_for_custom_search);
                }
                catch (Exception e)
                {

                }

                Intent logmeout=new Intent(this,SigninPage.class);
                logmeout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                logmeout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logmeout.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(logmeout);
                break;
        }

        try {
            fragmentformenuitems = (Fragment) fragmentclass.newInstance();
        } catch (Exception e) {
            return false;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragmentformenuitems).commit();

         //item.setChecked(true);
        setTitle(item.getTitle());
        drawerlayoutid.closeDrawer(GravityCompat.START);
        return true;
    }

    private void namedisplaynav() {

        final String PREF_NAME = "com.data.workshop.event";

        SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        usernamenav.setText(sharedPreferences.getString("USERNAME",""));
        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRound(sharedPreferences.getString("USERNAME","").substring(0,1), Color.parseColor("#008080"));
        image.setImageDrawable(drawable);


    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Press Back Again To Exit",Toast.LENGTH_LONG).show();


        //moveTaskToBack(true);
    }

}
