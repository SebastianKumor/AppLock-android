package com.example.sebastiankumor.applock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import  android.view.View.OnClickListener;

/**
 * Created by sebastiankumor.
 * this supposed to be the unlock screen which pops up when user presses locked app
 */
public class Locked_app_Unlock extends ActionBarActivity implements OnClickListener{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.app_lock_unlock_screen);

        Button button_unlock = (Button) findViewById(R.id.button_unlock);
        button_unlock.setOnClickListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_lock, menu);
        menu.findItem(R.id.action_bar_unlock).setVisible(false);
        menu.findItem(R.id.menu_about).setVisible(false);



        return true;
    }

    @Override
    public void onClick(View v) {
        EditText myEdit_pass = (EditText) findViewById(R.id.editText_enterPass);

        //retrieve saved password
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String saved_pass = preferences.getString("Password","");
        String entered_pass = myEdit_pass.getText().toString();


        if(saved_pass.equals(entered_pass)){
            Toast.makeText(Locked_app_Unlock.this,R.string.pass_success, Toast.LENGTH_SHORT).show();
            //TODO when correct password is entered dismiss view and proceed with opening the locked app
            // i miss the working service for user activity
        }
        else {

            Toast.makeText(Locked_app_Unlock.this, R.string.enter_correct_pass, Toast.LENGTH_SHORT).show();

            myEdit_pass.setError(getText(R.string.wrong_pass));
            // warn user that he entered wrong password
        }

    }

}
