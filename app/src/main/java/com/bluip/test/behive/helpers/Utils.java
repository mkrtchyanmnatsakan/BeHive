package com.bluip.test.behive.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Utils {


    @NonNull
    public static String getVersionAppName(Context context){

        String version = "";

        try {
            PackageInfo pInfo = context. getPackageManager().getPackageInfo(context. getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return  version;
    }



    public static void settingStringValuesInPreference(@Nullable Context context,String key,String value){



        if(context == null){

            return;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        prefs.edit().putString(key,value)
                .apply();


    }





    public static String getStringInPreference(@Nullable Context context,String key){



        if(context == null){

            return "";
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        return prefs.getString(key,"");

    }



    public static void settingBooleanValuesInPreference(@Nullable Context context,String key,boolean value){

        if(context == null){

            return;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        prefs.edit().putBoolean(key,value)
                .apply();


    }


    public static boolean getBooleanInPreference(@Nullable Context context,String key){



        if(context == null){

            return false;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        return prefs.getBoolean(key,false);

    }


    public static void hideKeyboard(@Nullable Activity activity){

        if(activity == null){

            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }

        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);

    }


    public static void hideKeyboardEditText(@Nullable Activity activity, EditText editText){

        if(activity == null){

            return;
        }



        editText.requestFocus();
        InputMethodManager imgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }



}
