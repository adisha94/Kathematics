package KathaMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kathaMain.adish.tutorial.R;

/**
 * Created by Adish on 5/2/16.
 */
public class OxidationStates extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); // for now
        final Button OxidationButton = (Button) findViewById(R.id.oxidation_button);
        final Button ReturnMain = (Button) findViewById(R.id.home_Screen);


        ReturnMain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                startActivity(intent);
            }
        });
        setContentView(R.layout.oxidation_no_calculator);
    }


    public void Calculate_Oxidation_Numbers_For_Formula(View view)
    {
        // leave for others to complete
        TextView statusMessage = (TextView)findViewById(R.id.Oxidation_Result);
        statusMessage.setEnabled(true);
        statusMessage.setText("This concept will come in a future update");

    }

    /**
     * Goes back to the main menu screen
     * @param view
     */
    public void GoToMain(View view)
    {
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }


}
