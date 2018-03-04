package com.pharmeasy.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.login.LoginActivity;

/**
 * Created by vihaanverma on 04/03/18.
 */

public class SessionUtil {

    private static final String PREF_NAME = "app_prefs";
    private static final String LOGGED_IN_USER = "loggedInUser";
    private static final String LOGGED_IN_DOCTOR= "loggedInDoctor";
    public static void saveLoggedInUser(Context context, User user)
    {
        SharedPreferences prefs= context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(LOGGED_IN_USER, json);
        prefsEditor.commit();
    }

    public static User getLoggedInUser(Context context){
        Gson gson = new Gson();
        SharedPreferences prefs= context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        String json = prefs.getString(LOGGED_IN_USER, "");
        User user= gson.fromJson(json, User.class);
        return user;
    }

    public static void saveLoggedInDoctor(Context context, Doctor doctor)
    {
        SharedPreferences prefs= context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(doctor);
        prefsEditor.putString(LOGGED_IN_DOCTOR, json);
        prefsEditor.commit();
    }

    public static Doctor getLoggedInDoctor(Context context){
        Gson gson = new Gson();
        SharedPreferences prefs= context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        String json = prefs.getString(LOGGED_IN_DOCTOR, "");
        Doctor doctor= gson.fromJson(json, Doctor.class);
        return doctor;
    }

    public static void clearLoggedInUser(Context context)
    {
        SharedPreferences prefs= context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.remove(LOGGED_IN_USER);
        prefsEditor.apply();
    }

    public static void clearLoggedInDoctor(Context context)
    {
        SharedPreferences prefs= context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.remove(LOGGED_IN_DOCTOR);
        prefsEditor.apply();
    }

    public static void logout(Context context){
        clearLoggedInDoctor(context);
        clearLoggedInUser(context);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
