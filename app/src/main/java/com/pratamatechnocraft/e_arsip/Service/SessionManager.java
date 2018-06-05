package com.pratamatechnocraft.e_arsip.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.pratamatechnocraft.e_arsip.LoginActivity;
import com.pratamatechnocraft.e_arsip.MainActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAMA = "NAMA";
    public static final String FOTO = "FOTO";
    public static final String LEVEL_USER = "LEVEL_USER";
    public static final String ID_USER = "ID_USER";
    public static final String NIP_USER = "NIP_USER";
    public static final String ID_BAGIAN = "ID_BAGIAN";
    public static final String NAMA_BAGIAN = "NAMA_BAGIAN";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences =context.getSharedPreferences( PREF_NAME, PRIVATE_MODE );
        editor = sharedPreferences.edit();

    }

    public void createSession(String id_user,String nip_user,String nama, String foto, String level_user, String id_bagian,String nama_bagian){
        editor.putBoolean(LOGIN, true);
        editor.putString( "ID_USER", id_user );
        editor.putString( "NIP_USER", nip_user );
        editor.putString( "NAMA", nama );
        editor.putString( "FOTO", foto );
        editor.putString( "LEVEL_USER", level_user );
        editor.putString( "ID_BAGIAN", id_bagian );
        editor.putString( "NAMA_BAGIAN", nama_bagian );
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean( LOGIN, false );
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent intent = new Intent( context, LoginActivity.class );
            context.startActivity( intent );
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>(  );
        user.put( ID_USER, sharedPreferences.getString( ID_USER, null ) );
        user.put( NIP_USER, sharedPreferences.getString( NIP_USER, null ) );
        user.put( NAMA, sharedPreferences.getString( NAMA, null ) );
        user.put( FOTO, sharedPreferences.getString( FOTO, null ) );
        user.put( LEVEL_USER, sharedPreferences.getString( LEVEL_USER, null ) );
        user.put( ID_BAGIAN, sharedPreferences.getString( ID_BAGIAN, null ) );
        user.put( NAMA_BAGIAN, sharedPreferences.getString( NAMA_BAGIAN, null ) );
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent( context, LoginActivity.class );
        context.startActivity( intent );
        ((MainActivity) context).finish();

    }
}
