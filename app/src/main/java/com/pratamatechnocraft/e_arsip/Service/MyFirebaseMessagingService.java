package com.pratamatechnocraft.e_arsip.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import com.pratamatechnocraft.e_arsip.DetailSuratKeluarActivity;
import com.pratamatechnocraft.e_arsip.DetailSuratMasukActivity;
import com.pratamatechnocraft.e_arsip.LembarDisposisiActivity;
import com.pratamatechnocraft.e_arsip.MainActivity;
import com.pratamatechnocraft.e_arsip.Config;
import com.pratamatechnocraft.e_arsip.Model.NotificationUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Intent resultIntent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //parsing json data
        String title = remoteMessage.getData().get( "title" );
        String message = remoteMessage.getData().get( "message" );
        String jenis_notif = remoteMessage.getData().get( "jenis_notif" );
        String id = remoteMessage.getData().get( "id" );

        Log.d( "TAG", "onMessageReceived: "+jenis_notif );

        //creating MyNotificationManager object
        NotificationUtils mNotificationManager = new NotificationUtils(getApplicationContext());

        if (jenis_notif.equals( "surat masuk" )){
            resultIntent = new Intent(this, DetailSuratMasukActivity.class);
            resultIntent.putExtra("idSuratMasuk", id);
        }else if(jenis_notif.equals( "surat keluar" )){
            resultIntent = new Intent(this, DetailSuratKeluarActivity.class);
            resultIntent.putExtra("idSuratKeluar", id);
        }else{
            resultIntent = new Intent(this, LembarDisposisiActivity.class);
            resultIntent.putExtra("idDisposisi", id);
        }

        //if there is no image

        //displaying small notification
        mNotificationManager.showSmallNotification(title, message, resultIntent);
        mNotificationManager.playNotificationSound();
    }
}
