package com.manhhdc.qltk.Moduls;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;

import com.manhhdc.qltk.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static final byte UPDATE = 1, CREATE = 0;

    public static void Alert(Context context, String msg){
        Alert(context,"Thông báo", msg);
    }

    public static void Alert(Context context, String title, String message){
        Alert(context, title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    public static void Alert(Context context, String title, String message, DialogInterface.OnClickListener ok){
        Alert(context, title, message, ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
    public static void Alert(Context context, String title, String message, DialogInterface.OnClickListener ok, DialogInterface.OnClickListener cancel){
        AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Cancel", cancel)
                .setPositiveButton("Ok", ok)
                .show();
    }

    public static void DatePick(Context context, String value, DatePickerDialog.OnDateSetListener action) {

        try{
            Date date = new Date();
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                date = formatter.parse(value);
            }catch (Exception ex){
                date.setYear(2020);

                date.setMonth(1);
                date.setDate(1);
            }

            DatePickerDialog mDlgDatePicker = new DatePickerDialog(context, action, date.getYear(), date.getMonth(), date.getDay());

            mDlgDatePicker.show();
        }catch (Exception e){

        }
    }

    public static String getDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String myFormat = "dd/MM/yyyy"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

}
