package com.example.sebastiankumor.applock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;


/**
 * Created by sebastiankumor.
 * screen where user can change his already existing password
 */
public class Change_pass extends ActionBarActivity   implements OnClickListener{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.change_pass_screen);

        Button button = (Button) findViewById(R.id.button_changePass);
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
        //all the validation of the passwords

        EditText myEdit_old = (EditText) findViewById(R.id.editPass_old);
        EditText myEdit_new = (EditText) findViewById(R.id.editPass_new);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saved_pass = preferences.getString("Password","");

        if(myEdit_old.length()== 0 ){


                myEdit_old.setError(getText(R.string.change_pass_empty));
                myEdit_old.requestFocus();
        }

        else if (myEdit_new.length() >= 0 & myEdit_new.length() < 4) {

            myEdit_new.setError(getText(R.string.change_pass_short));
            myEdit_new.requestFocus();
        }
        else if (myEdit_old.getText().toString().equals(saved_pass)) {

            String new_pass = myEdit_new.getText().toString();
            Toast.makeText(Change_pass.this,R.string.change_pass_changed, Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Password",new_pass);
            editor.apply();

            InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

            myEdit_new.setText("");
            myEdit_old.setText("");
                    // submit new pass to shared preferences
        }
        else{
            myEdit_old.setError(getText(R.string.change_pass_wrong));
            myEdit_old.requestFocus();

            Toast.makeText(Change_pass.this, R.string.change_pass_wrong, Toast.LENGTH_SHORT).show();
        }

    }



}
