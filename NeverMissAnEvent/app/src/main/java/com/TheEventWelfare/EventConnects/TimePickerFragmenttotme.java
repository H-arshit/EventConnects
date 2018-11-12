package com.TheEventWelfare.EventConnects;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by User on 04-04-2017.
 */

public class TimePickerFragmenttotme extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    int hours = 0,minutes = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new TimePickerDialog(getActivity(),this, hours, minutes,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {

        TextView totme = (TextView) getActivity().findViewById(R.id.totme);
        String am_pm;
        if(hourOfDay>=12)
        {
            if(hourOfDay!=12)
                hours=hourOfDay%12;
            else
                hours=hourOfDay;
            am_pm="PM";
        }
        else
        {
            hours=hourOfDay;
            am_pm="AM";
        }

        minutes=minute;

        totme.setText(hours+":"+minutes+ " " + am_pm);


    }
}
