package com.example.sebastiankumor.applock;

import android.app.ActivityManager;
import  android.content.Context;
import java.util.List;
import android.os.Looper;
import java.util.List;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sebastiankumor.
 * started with the system , checks which app is being turned on and if its set to be locked in main screen
 * this approach is not possible anymore only in older versions of android
 */
class Locked_app_handler extends BroadcastReceiver {
//    ActivityManager am = null;
//    Context context = null;
//
//    public void CheckRunningActivity(Context contx){
//        context = contx;
//        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//    }
//
//    public void run(){
//        Looper.prepare();
//
//        while(true){
//            // Return a list of the tasks that are currently running,
//            // with the most recent being first and older ones after in order.
//            // Taken 1 inside getRunningTasks method means want to take only
//            // top activity from stack and forgot the olders.
//            List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);
//
//            String currentRunningActivityName = taskInfo.get(0).topActivity.getClassName();
//
//            if (currentRunningActivityName.equals("PACKAGE_NAME.ACTIVITY_NAME")) {
//                // show your activity here on top of PACKAGE_NAME.ACTIVITY_NAME
//            }
//        }
//       // Looper.loop();
//    }
public final String TAG = "appReceiver"; // CheckRunningApplicationReceiver

    @Override
    public void onReceive(Context aContext, Intent anIntent) {

        try {

            // Using ACTIVITY_SERVICE with getSystemService(String)
            // to retrieve a ActivityManager for interacting with the global system state.

            ActivityManager amanager = (ActivityManager) aContext
                    .getSystemService(Context.ACTIVITY_SERVICE);

            // Return a list of the tasks that are currently running,
            // with the most recent being first and older ones after in order.

            List<ActivityManager.RunningTaskInfo> alltasks = amanager
                    .getRunningTasks(1);

            //
            for (ActivityManager.RunningTaskInfo aTask : alltasks) {


                // Used to check for CALL screen

                if (aTask.topActivity.getClassName().equals("com.android.phone.InCallScreen")
                        || aTask.topActivity.getClassName().equals("com.android.contacts.DialtactsActivity"))
                {
                    // When user on call screen show a alert message
                    Toast.makeText(aContext, "Phone Call Screen.", Toast.LENGTH_LONG).show();
                }

                // Used to check for SMS screen

                if (aTask.topActivity.getClassName().equals("com.android.mms.ui.ConversationList")
                        || aTask.topActivity.getClassName().equals("com.android.mms.ui.ComposeMessageActivity"))
                {
                    // When user on Send SMS screen show a alert message
                    Toast.makeText(aContext, "Send SMS Screen.", Toast.LENGTH_LONG).show();
                }


                // Use to check for CURRENT example main screen

                String packageName = "com.example.sebastiankumor.applock";

                if (aTask.topActivity.getClassName().equals(
                        packageName + ".AppLock"))
                {
                    // When opens this example screen then show a alert message
                    Toast.makeText(aContext, R.string.handler_mainToast,
                            Toast.LENGTH_LONG).show();
                }


                // These are showing current running activity in logcat with
                // the use of different methods

                Log.i(TAG, "-------------------------------");

                Log.i(TAG, "aTask.baseActivity: "
                        + aTask.baseActivity.flattenToShortString());

                Log.i(TAG, "aTask.baseActivity: "
                        + aTask.baseActivity.getClassName());

                Log.i(TAG, "aTask.topActivity: "
                        + aTask.topActivity.flattenToShortString());

                Log.i(TAG, "aTask.topActivity: "
                        + aTask.topActivity.getClassName());

                Log.i(TAG, "--------------------------------");


            }

        } catch (Throwable t) {
            Log.i(TAG, "Throwable caught: "
                    + t.getMessage(), t);
        }

    }
}