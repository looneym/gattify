package com.example.micheal.assingment2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Micheal on 18/05/16.
 */
public class SwipeActivity extends AppCompatActivity {

    int GLOBAL_TOUCH_POSITION_X = 0;
    int GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
    int GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;
    int GLOBAL_TOUCH_POSITION_Y = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);

        //Two-Finger Drag Gesture detection
        RelativeLayout TextLoggerLayout = (RelativeLayout)findViewById(R.id.wat);
        TextLoggerLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener(){

                    @Override
                    public boolean onTouch(View v, MotionEvent m) {
                        handleTouch(m);
                        return true;
                    }

                });
    }

    void handleTouch(MotionEvent m){
        //Number of touches
        int pointerCount = m.getPointerCount();
        if(pointerCount == 2){
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;
            TextView tv = (TextView) findViewById(R.id.testDiffText);
            switch (action)


            {
                case MotionEvent.ACTION_POINTER_UP:
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = (int) m.getY(1);
                    if (GLOBAL_TOUCH_POSITION_Y > GLOBAL_TOUCH_CURRENT_POSITION_Y) {
                        System.out.println("swipe up");
                    } else {
                        System.out.println("swipe down");
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    System.out.println("DOWN");
                    GLOBAL_TOUCH_POSITION_X = (int) m.getX(1);
                    actionString = "DOWN"+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    tv.setText(actionString);
                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("UP");
                    GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
                    actionString = "UP"+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    tv.setText(actionString);
                    break;
                case MotionEvent.ACTION_MOVE:
                    GLOBAL_TOUCH_CURRENT_POSITION_X = (int) m.getX(1);
                    int diff = GLOBAL_TOUCH_POSITION_X-GLOBAL_TOUCH_CURRENT_POSITION_X;
                    actionString = "Diff "+diff+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    tv.setText(actionString);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:

                    GLOBAL_TOUCH_POSITION_X = (int) m.getX(1);
                    GLOBAL_TOUCH_POSITION_Y = (int) m.getY(1);
                    actionString = "DOWN"+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    tv.setText(actionString);
                    break;
                default:
                    actionString = "";
            }

            pointerCount = 0;
        }
        else {
            GLOBAL_TOUCH_POSITION_X = 0;
            GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
            GLOBAL_TOUCH_POSITION_Y = 0;
            GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;
        }
    }



}
