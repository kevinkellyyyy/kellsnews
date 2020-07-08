package id.ac.umn.kevinkellyisyanta;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        preferences = ctx.getSharedPreferences("APPNEWS", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoggedIn(boolean loggedIn){
        editor.putBoolean("loggedInMode", loggedIn);
        editor.commit();
    }

    public boolean loggedIn(){
        return preferences.getBoolean("loggedInMode", false);
    }
}
