package com.example.sebastiankumor.applock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by sebastiankumor.
 * this service starts the app when the phone was rebooted so it can listen to the clicks onlcoked apps
 */
public class AppLock_Boot_Start  extends BroadcastReceiver{

//    public  void onReceive(final Context context, Intent intent) {
//
//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            Intent launch = new Intent(context, Locked_app_handler.class);
//            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(launch);
//        }
//    }


        static final String TAG = "AppBootStart";

        final int startupID = 1111111;


        @Override
        public void onReceive(Context context, Intent intent) {

            // Create AlarmManager from System Services
            final AlarmManager alarmManager = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);
            try{
                // Create pending intent for Locked_app_handler class
                // it will call after each 5 seconds

                Intent intent1 = new Intent(context, Locked_app_handler.class);
                PendingIntent ServiceManagementIntent = PendingIntent.getBroadcast(context,
                        startupID, intent1, 0);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 500, ServiceManagementIntent);

            } catch (Exception e) {
                Log.i(TAG, "Exception : "+e);
            }

        }


}
