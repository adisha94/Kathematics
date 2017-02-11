package KathaMain;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kathaMain.adish.tutorial.R;

public class MyActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // buttons on the front end view of the activity
        // for both portrait and landscape
        Button mainButton = (Button) findViewById(R.id.mainButton); // the main button pressed to activate the balence method
        final TextView result = (TextView) findViewById(R.id.Result); // mini display console
        final EditText inputField = (EditText) findViewById(R.id.inputBox); // the google button box
        Button googleButton = (Button) findViewById(R.id.Google_Button); // the main button pressed to activate the balence method
        final Button clearButton = (Button) findViewById(R.id.clear);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = result.getText().toString();
                Snackbar.make(view, "You just shared this answer with a friend 8D", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);
            }
        });



        googleButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                String message = inputField.getText().toString();
                Toast.makeText(getApplicationContext(), "Tap to share with people", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (message.isEmpty())
                    // return an error message if this occurs
                    intent.putExtra(Intent.EXTRA_TEXT, "null");
                intent.putExtra(Intent.EXTRA_TEXT, "equation to search on google: " + message);
                startActivity(intent);
                return true;
            }
        });

        /**
         * if we cannot see the output in the window, then we must be able to at least
         * see the resulting equation in a toast
         */
        mainButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                String message = inputField.getText().toString();
                // secondary check
                if (message.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Note: " +
                            "no Input Detected", Toast.LENGTH_SHORT);
                    result.setEnabled(false);
                    return true;
                }
                else if (message.contains("empty") || message.contains("Empty"))
                {
                    Toast.makeText(getApplicationContext(), "You are a weirdo"
                            , Toast.LENGTH_SHORT);
                    result.setEnabled(false);
                    return true;
                }
                else
                {
                    Toast.makeText(MyActivity.this, ChemicalBalancer.balanceEquation(message), Toast.LENGTH_SHORT).show();
                    return true;
                }

            }
        });


        /**
         * clears the input data as well as reset's the output data
         */
        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                inputField.setText("");
                result.setText("[]");
            }
        });


        /**
         * 1st demo equation. keep this but include other equations as well for the other inputs
         */
        mainButton.setOnClickListener(new View.OnClickListener()
        {
        	// This is for the sample problem
            @Override
            public void onClick(View v) {
                // activate the balence method
                // only show if 'H20 + CO2' is inputted
                String message = inputField.getText().toString();
                if (message.contains("H2O + CO2") || message.contains("CO2 + H2O"))
                {
                    String resultingMessage = message + " = H2CO3";
                    result.setText(resultingMessage);
                }
            }
        });


        mainButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // the answer field of the application
                String message = inputField.getText().toString(); // this will be the area where we display the answer.
                                                                  // we need to resize and make the answer more clear
                                                                  // when we show the answer in more clarity
                if (message.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Nothing has been" +
                            "inputted into the field", Toast.LENGTH_SHORT);
                    result.setEnabled(false);
                }
                else if (message.contains("empty") || message.contains("Empty"))
                {
                    Toast.makeText(getApplicationContext(), "You are a weirdo"
                            , Toast.LENGTH_SHORT);
                    result.setEnabled(false);
                }
                else
                {
                    result.setTextSize(20);
                    result.setText(ChemicalBalancer.balanceEquation(message));
                }
                // test now
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);

        return true;
    }

    /**
     * Deals with the menu bar for opening diferent windows in the application
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Toast.makeText(this, "1st item", Toast.LENGTH_SHORT);
            return true;
        }
        else if(id == R.id.secondOption)
        {
            Toast.makeText(this, "2nd item" , Toast.LENGTH_SHORT);

            return true;
        }
        else if(id == R.id.oxidation_Numbers)
        {
            Intent intent = new Intent(this, OxidationStates.class);
            startActivity(intent);
        }
        else if(id == R.id.Reaction_Types)
        {

        }
        else if(id == R.id.Exit)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onGoogleButtonClick(View view)
    {
        // have it search wikipedia instead of google if you can
        final EditText inputField = (EditText) findViewById(R.id.inputBox); // the google button box
        String message = "balance equation " + inputField.getText().toString();
        if (message.contains("empty") || message.contains("Empty"))
        {
            Toast.makeText(MyActivity.this, "Use this button search google for an in-depth analysis about the element. ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent search = new Intent(Intent.ACTION_WEB_SEARCH); // change from google to wikipedia
            search.putExtra(SearchManager.QUERY,message);
            startActivity(search);
        }
    }

}