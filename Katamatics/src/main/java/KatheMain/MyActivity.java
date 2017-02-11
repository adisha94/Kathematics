package KatheMain; //package com.example.adish.tutorial;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.R;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Share with a friend", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // for both portrait and landscape
        final TextView textView = (TextView) findViewById(R.id.inputBox);
        Button mainButton = (Button) findViewById(R.id.mainButton);

        mainButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false; // event not handled
            }
        });

    }

    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_my, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.-
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
    public void Enter(View view) {

    }

    public void onGoogleButtonClick(View view) {
        // have it search wikipedia instead of google if you can
        final EditText inputField = (EditText) findViewById(R.id.inputBox); // the google button box
        String message = "balance equation " + inputField.getText().toString();
        if (message.contains("empty") || message.contains("Empty")) {
            Toast.makeText(MyActivity.this, "Use this button search google for an in-depth analysis about the element. ", Toast.LENGTH_SHORT).show();
        } else {
            Intent search = new Intent(Intent.ACTION_WEB_SEARCH); // change from google to wikipedia
            search.putExtra(SearchManager.QUERY, message);
            startActivity(search);
        }
    }
}