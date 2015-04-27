package com.example.sebastiankumor.applock;

import java.util.ArrayList;
import java.util.List;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import  android.widget.AdapterView.OnItemClickListener;

/**
 * Created by sebastiankumor.
 * main activity with list of apps ,option to lock them and go to settings to set/change password
 */

public class AppLock extends ActionBarActivity implements OnItemClickListener{
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    ListView list;
    private ApplicationAdapter listadapter = null;
    private ArrayList<String> appSettings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_app_lock);

        packageManager = getPackageManager();
        list = (ListView) findViewById(R.id.list);
        new LoadApplications().execute();

        list.setOnItemClickListener(this);
        initialize();

    }

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
            TextView c = (TextView) view.findViewById(R.id.app_name);
            String playerChanged = c.getText().toString();

            Toast.makeText(AppLock.this,playerChanged, Toast.LENGTH_SHORT).show();
        }

    private void initialize() {
        //initialize the service
        getBaseContext().getApplicationContext().sendBroadcast(
                new Intent("StartupReceiver_Manual_Start"));
    }
   // @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        ApplicationInfo app = applist.get(position);
//        try {
//            Intent intent = packageManager
//                    .getLaunchIntentForPackage(app.packageName);
//
//            if (null != intent) {
//                startActivity(intent);
//            }
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(AppLock.this, e.getMessage(),
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(AppLock.this, e.getMessage(),
//                    Toast.LENGTH_LONG).show();
//        }
//    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_lock, menu);
        menu.findItem(R.id.action_bar_unlock).setVisible(false);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;


        switch (item.getItemId()) {
            case R.id.menu_about: {
                displayAboutDialog();

                break;
            }
            default: {
                result = super.onOptionsItemSelected(item);

                break;
            }
        }

        return result;
    }

    private void displayAboutDialog() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saved_pass = preferences.getString("Password","");

        if(saved_pass==""){
             Intent intent = new Intent(AppLock.this, Settings.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(AppLock.this, Change_pass.class);
            startActivity(intent);
        }


    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {

            listadapter = new ApplicationAdapter(AppLock.this, R.layout.list_row, packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            return null;
        }

        protected void onPostExecute(Void result) {
            list.setAdapter(listadapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        protected void onPreExecute() {
            progress = ProgressDialog.show(AppLock.this, null, getText(R.string.appLock_loading_apps));
            super.onPreExecute();
        }
    }



}
