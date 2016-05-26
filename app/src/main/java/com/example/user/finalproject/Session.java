package com.example.user.finalproject;

import android.content.Context;
import android.content.SharedPreferences;


public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editer;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("login" , Context.MODE_PRIVATE);
        editer = prefs.edit();
    }

    public void setLogged(boolean logged){
        editer.putBoolean("loggedMode" , logged);
        editer.commit();
    }

    public boolean logged(){
        return prefs.getBoolean("loggedMode" , false);
    }

    public String loggedUser(String user){
        return user;
    }
}
