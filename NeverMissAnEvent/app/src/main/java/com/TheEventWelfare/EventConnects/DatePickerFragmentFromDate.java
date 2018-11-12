package com.TheEventWelfare.EventConnects;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by Harshit on 04-04-2017.
 */

public class DatePickerFragmentFromDate extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    int myyear,mydate,mymonth;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, myyear, mydate, mymonth);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView fromdte = (TextView) getActivity().findViewById(R.id.fromdte);
        myyear=year;
        mymonth=month+1;
        mydate=dayOfMonth;
        fromdte.setText(mydate+"/"+mymonth+"/"+myyear);

    }
}