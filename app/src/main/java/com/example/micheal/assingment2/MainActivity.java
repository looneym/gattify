package com.example.micheal.assingment2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.orm.dsl.Table;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView alcPercSeekValue;
    private SeekBar alcSeekBar;
    private String gattName;
    private double gattPrice;
    private double gattVolume;
    private String selectedGattType = "whatever";
    private int gattPercentage;
    private double gattUnits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        /////////////// Activity switch

        final Button showTable = (Button) findViewById(R.id.showTableButton);
        showTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTableActivity();
            }
        });

        //////////////// SEEK BAR

        alcPercSeekValue = (TextView) findViewById(R.id.alcPercSeekValue);
        alcSeekBar = (SeekBar) findViewById(R.id.alcSeekBar);
        alcSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) alcPercSeekValue.setText("Alcohol Percentage = " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) { // OnSeekBarChangeListener
            }

            public void onStopTrackingTouch(SeekBar seekBar) { // OnSeekBarChangeListener
            }
        });




        ///// BUTTON

        Button runIt = (Button) findViewById(R.id.runCalculation);

        runIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createGatt();


            }
        });



}


    public void createGatt(){





        //////////// volume

        EditText volumeInput = (EditText) findViewById(R.id.volumeInput);
        gattVolume = Double.parseDouble(volumeInput.getText().toString());



        ////////////////////// price

        EditText priceInput = (EditText) findViewById(R.id.priceInput);
        gattPrice = Double.parseDouble(priceInput.getText().toString());


        ////////////////////////// name

        EditText nameInput = (EditText) findViewById(R.id.nameInput);
        gattName = nameInput.getText().toString();

        // units

        EditText unitsInput = (EditText) findViewById(R.id.unitsInput);
        gattUnits = Double.parseDouble(unitsInput.getText().toString());




        // The seek bar doesn't have an onClick to update the variable when it's state changes so we grab it now
        gattPercentage = alcSeekBar.getProgress();

        Gatt userGatt = new Gatt(gattName, selectedGattType, gattPercentage, gattVolume , gattPrice,  gattUnits );

        System.out.println(userGatt);

        userGatt.save();
        System.out.println("Db saved ok");
        System.out.println("contents of DB:");
        List<Gatt> gatts =  Gatt.listAll(Gatt.class);
        System.out.println(gatts);


    }



    public void showTableActivity(){
        Intent showTableActivity = new Intent(MainActivity.this, TableActivity.class);
        startActivity(showTableActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void moveLeft(){}
    public void moveRight(){showTableActivity();}
}
