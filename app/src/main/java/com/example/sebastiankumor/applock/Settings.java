package com.example.sebastiankumor.applock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.*;
import  android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by sebastiankumor.
 * this is screen where user can set his password, its being displayed only the first time the app run
 */

public class Settings extends ActionBarActivity implements OnClickListener
{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        setContentView(R.layout.settings_view);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_lock, menu);
        menu.findItem(R.id.menu_about).setVisible(false);
        menu.findItem(R.id.action_bar_unlock).setVisible(false);



        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intent = new Intent(this, AppLock.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public void onClick(View v) {
        //evaluate entered password
    EditText myEdit = (EditText) findViewById(R.id.writePass);

    if(myEdit.length()== 0){

        myEdit.setError(getText(R.string.settings_empty));
        myEdit.requestFocus();
    }

    else if (myEdit.length() >= 0 & myEdit.length() < 4) {

        myEdit.setError(getText(R.string.settings_pass_chars));
        myEdit.requestFocus();
    }
    else {

        String enteredPass = myEdit.getText().toString();
        Toast.makeText(Settings.this, R.string.settings_pass_set, Toast.LENGTH_SHORT).show();

        //saving password as a shared preference

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Password",enteredPass);
        editor.apply();
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

  }
}
