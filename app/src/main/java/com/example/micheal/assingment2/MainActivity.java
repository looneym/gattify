package com.example.micheal.assingment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private TextView alcPercSeek;
    private SeekBar alcSeekBar;
    private String gattName;
    private double gattPrice;
    private double gattVolume;
    private String selectedGattType = "whatever";
    private int gattPercentage;
    private double gattUnits;
    private TextView mainActivityMenuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);



        /////////////// Register buttons for swithing activities, menus etc.


        final Button showTable = (Button) findViewById(R.id.showTableButton);
        showTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTableActivity();
            }
        });

        mainActivityMenuButton = (TextView) findViewById(R.id.mainActivityMenuButton);
//        mainActivityMenuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMenu();
//            }
//        });

        final Button runIt = (Button) findViewById(R.id.createGattButton);
        runIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGatt();
            }
        });













        //////////////// SEEK BAR

        alcPercSeek = (TextView) findViewById(R.id.alcPercSeekValue);

        alcSeekBar = (SeekBar) findViewById(R.id.alcSeekBar);
        alcSeekBar.setMax(1000);
        alcSeekBar.incrementProgressBy(1);
        alcSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String alcPercString;

                if (progress<10){
                    alcPercString = Integer.toString(progress);
                    alcPercString = alcPercString.substring(0, 1) + "." + alcPercString.substring(1, alcPercString.length());
                    alcPercSeek.setText("Alcohol Percentage = " + alcPercString + "%");
                }
                if (progress>=10){
                    alcPercString = Integer.toString(progress);
                    alcPercString = alcPercString.substring(0, 1) + "." + alcPercString.substring(1, alcPercString.length());
                    alcPercSeek.setText("Alcohol Percentage = " + alcPercString + "%");
                }
                if (progress>=100){
                    alcPercString = Integer.toString(progress);
                    alcPercString = alcPercString.substring(0, 2) + "." + alcPercString.substring(2, alcPercString.length());
                    alcPercSeek.setText("Alcohol Percentage = " + alcPercString + "%");
                }
                if (progress==1000){
                    alcPercString = Integer.toString(progress);
//                    alcPercString = alcPercString.substring(0, 3) + "." + alcPercString.substring(3, alcPercString.length());
                    alcPercString = alcPercString.substring(0, 3);
                    alcPercSeek.setText("Alcohol Percentage = " + alcPercString + "%");
                }



            }

            public void onStartTrackingTouch(SeekBar seekBar) { // OnSeekBarChangeListener
            }

            public void onStopTrackingTouch(SeekBar seekBar) { // OnSeekBarChangeListener
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

//    public void showMenu(){
//
//        System.out.println("Menu pressed");
//        PopupMenu pm = new PopupMenu(getApplicationContext(), mainActivityMenuButton);
//        MenuInflater mi = new MenuInflater(getApplicationContext());
//        mi.inflate(R.menu.test_menu, pm.getMenu());
//        pm.show();
//    }

    public void showMenu(View v) {

        System.out.println("Menu pressed");
        PopupMenu pm = new PopupMenu(getApplicationContext(), mainActivityMenuButton);
        MenuInflater mi = new MenuInflater(getApplicationContext());
        pm.setOnMenuItemClickListener(this);
        mi.inflate(R.menu.test_menu, pm.getMenu());
        pm.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.testMenuItem1:
                System.out.println("Menu item 1 pressed");
                return true;
            case R.id.testMenuItem2:
                System.out.println("Menu item 2 pressed");
                return true;
            default:
                return false;
        }
    }


    public void moveLeft(){}
    public void moveRight(){showTableActivity();}
}
