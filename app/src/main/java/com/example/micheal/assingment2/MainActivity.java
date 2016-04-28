package com.example.micheal.assingment2;

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
    Spinner alcoholType;
    private String gattName;
    private double gattPrice;
    private double gattVolume;
    private String selectedGattType;
    private int gattPercentage;
    private boolean ml;
    private  final String[] alcoholTypes = { "Beer", "Wine", "Spirit" };
    private static final int LARGE_MOVE = 60;
    private GestureDetector gestureDetector;

    @Override // onTouchEvent required for onFling to be invoked...
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("In onTouchEvent");
        return gestureDetector.onTouchEvent(event); // ...pass to gestureDetector above
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        //////// Gestures

        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        System.out.println("in onFling");
                        if (e1.getY() - e2.getY() > LARGE_MOVE) {
                            System.out.println("\nFling Up with velocity " + velocityY);
                            return true;

                        } else if (e2.getY() - e1.getY() > LARGE_MOVE) {
                            System.out.println("\nFling Down with velocity " + velocityY);
                            return true;

                        } else if (e1.getX() - e2.getX() > LARGE_MOVE) {

                            System.out.println("\nFling Left with velocity " + velocityX);
                            moveRight();
                            return true;

                        } else if (e2.getX() - e1.getX() > LARGE_MOVE) {
                            System.out.println("\nFling Right with velocity "
                                    + velocityX);

                            moveLeft();

                            return true;
                        }

                        return false; // works with true also
                    }
                });



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


        //////////////// SPINNER


        alcoholType = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_entry, alcoholTypes);

        mAdapter.setDropDownViewResource(R.layout.spinner_entry);
        alcoholType.setAdapter(mAdapter);





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


        ///////////////// Radio

        final RadioButton rbutton[] = {(RadioButton) findViewById(R.id.mlRB),
                (RadioButton) findViewById(R.id.clRB)};

        rbutton[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    ml = true;

                }
            }
        });

        rbutton[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    ml = false;

                }
            }
        });






        //////////// volume

        EditText volumeInput = (EditText) findViewById(R.id.volumeInput);
        gattVolume = Double.parseDouble(volumeInput.getText().toString());



        ////////////////////// price

        EditText priceInput = (EditText) findViewById(R.id.priceInput);
        gattPrice = Double.parseDouble(priceInput.getText().toString());


        ////////////////////////// name

        EditText nameInput = (EditText) findViewById(R.id.nameInput);
        gattName = nameInput.getText().toString();




        // The spinner doesn't have an onClick to update the variable when it's state changes so we grab it now
        selectedGattType = alcoholType.getSelectedItem().toString();
        // same for the seek bar
        gattPercentage = alcSeekBar.getProgress();

        Gatt userGatt = new Gatt(gattName, selectedGattType, gattPercentage, gattVolume , gattPrice, ml );

        System.out.println(userGatt);

        userGatt.save();
        System.out.println("Db saved ok");
        System.out.println("contents of DB:");
        List<Gatt> gatts =  Gatt.listAll(Gatt.class);
        System.out.println(gatts);
//        for (Gatt gatt : gatts){
//            System.out.println(gatt);
//        }

    }



    public void showTableActivity(){
        Intent showTableActivity = new Intent(MainActivity.this, TableActivity.class);
        startActivity(showTableActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void moveLeft(){}
    public void moveRight(){showTableActivity();}
}
