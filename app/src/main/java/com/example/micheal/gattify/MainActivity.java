package com.example.micheal.gattify;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
        setContentView(com.example.micheal.gattify.R.layout.home);



        /////////////// Register buttons for swithing activities, menus etc.


        final Button showTable = (Button) findViewById(com.example.micheal.gattify.R.id.showTableButton);
        showTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTableActivity();
            }
        });

        // The activity needs to know about this "Button" but the oncliclk logic is handled
        // be registering it in XML and by the onMenuItemClick() and showMenu() methods
        mainActivityMenuButton = (TextView) findViewById(com.example.micheal.gattify.R.id.mainActivityMenuButton);

        final Button runIt = (Button) findViewById(com.example.micheal.gattify.R.id.createGattButton);
        runIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createGatt();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "All fields required" , Toast.LENGTH_SHORT).show();
                }
            }
        });


        //////////////// SEEK BAR

        alcPercSeek = (TextView) findViewById(com.example.micheal.gattify.R.id.alcPercSeekValue);

        alcSeekBar = (SeekBar) findViewById(com.example.micheal.gattify.R.id.alcSeekBar);
        alcSeekBar.setMax(1000);
        alcSeekBar.incrementProgressBy(1);
        alcSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String alcPercString;

                if (progress < 10) {
                    alcPercString = Integer.toString(progress);
                    alcPercString = alcPercString.substring(0, 1) + "." + alcPercString.substring(1, alcPercString.length());
                    alcPercSeek.setText("Alcohol Percentage = " + alcPercString + "%");
                }
                if (progress >= 10) {
                    alcPercString = Integer.toString(progress);
                    alcPercString = alcPercString.substring(0, 1) + "." + alcPercString.substring(1, alcPercString.length());
                    alcPercSeek.setText("Alcohol Percentage = " + alcPercString + "%");
                }
                if (progress >= 100) {
                    alcPercString = Integer.toString(progress);
                    alcPercString = alcPercString.substring(0, 2) + "." + alcPercString.substring(2, alcPercString.length());
                    alcPercSeek.setText("Alcohol Percentage = " + alcPercString + "%");
                }
                if (progress == 1000) {
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

        EditText volumeInput = (EditText) findViewById(com.example.micheal.gattify.R.id.volumeInput);
        gattVolume = Double.parseDouble(volumeInput.getText().toString());



        ////////////////////// price

        EditText priceInput = (EditText) findViewById(com.example.micheal.gattify.R.id.priceInput);
        gattPrice = Double.parseDouble(priceInput.getText().toString());


        ////////////////////////// name

        EditText nameInput = (EditText) findViewById(com.example.micheal.gattify.R.id.nameInput);
        gattName = nameInput.getText().toString();

        // units

        EditText unitsInput = (EditText) findViewById(com.example.micheal.gattify.R.id.unitsInput);
        gattUnits = Double.parseDouble(unitsInput.getText().toString());




        // The seek bar doesn't have an onClick to update the variable when it's state changes so we grab it now
        gattPercentage = alcSeekBar.getProgress();

        Gatt userGatt = new Gatt(gattName, selectedGattType, gattPercentage, gattVolume , gattPrice,  gattUnits );


        userGatt.save();
        Toast.makeText(this, "Gatt saved!" , Toast.LENGTH_SHORT).show();

    }



    public void showTableActivity(){
        Intent showTableActivity = new Intent(MainActivity.this, TableActivity.class);
        startActivity(showTableActivity);
        overridePendingTransition(com.example.micheal.gattify.R.anim.slide_in_right, com.example.micheal.gattify.R.anim.slide_out_left);
    }


    public void showMenu(View v) {

        System.out.println("Menu pressed");
        PopupMenu pm = new PopupMenu(getApplicationContext(), mainActivityMenuButton);
        MenuInflater mi = new MenuInflater(getApplicationContext());
        pm.setOnMenuItemClickListener(this);
        mi.inflate(com.example.micheal.gattify.R.menu.home_menu, pm.getMenu());
        pm.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case com.example.micheal.gattify.R.id.home_help:
                System.out.println("Help dialog");
                showHelpDialog();
                return true;
            case com.example.micheal.gattify.R.id.home_about:
                System.out.println("About dialog");
                showAboutDialog();
                return true;
            default:
                return false;
        }
    }

    public void showHelpDialog(){
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this ).create();
        final TextView message = (TextView)getLayoutInflater().inflate(R.layout.alert_dialog, null);
        String t = new String( "A Gatt = an alcoholic drink of any kind. \n" +
                        "\n"+
                "Gattify alows you to compare different alcohol products of varying " +
                        "strengths, prices and physical volumes to see which one " +
                        "provides the most alcohol for the least money. \n " +
                        " \n" +
                        "Just enter your Gatt's details on this page and hit 'Add Gatt' to save it. \n" +
                "\n" +
                "Compare different Gatts you have added by " +
                        "tapping the 'Show Gatts' button. The higher the Gattify score the better. \n" +
                        "\n " +
                        "Happy Gatting!  ");
        final SpannableString s =
                new SpannableString(t);
        Linkify.addLinks(s, Linkify.ALL);
        message.setText(s);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.setView(message);
        dialog.show();

    }

    public void showAboutDialog(){
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this ).create();
        final TextView message = (TextView)getLayoutInflater().inflate(R.layout.alert_dialog, null);
        String t = new String("Version 1.0.0 \n" +
                "\n" +
                "Developed with <3 by Micheál Looney \n" +
                "\n" +
                "Web: gattify.com \n" +
                "\n" +
                "Email: loonym.dev@gmail.com \n" +
                "\n" +
                "Github: github.com/looneym/ \n " +
                "\n" +
                "Linkedin: ie.linkedin.com/in/looneym \n " +
                "\n" +
                "Disclaimer : I am not responsible for ANYTHING you do as a result of using this App. This App is designed for easy comparison " +
                        "of consumer products. Drink responsibly.");
        final SpannableString s =
                new SpannableString(t);
        Linkify.addLinks(s, Linkify.ALL);
        message.setText(s);
        message.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.setView(message);
        dialog.show();

    }


    public void moveLeft(){}
    public void moveRight(){showTableActivity();}
}
