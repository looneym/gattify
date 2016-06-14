package com.example.micheal.assingment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


public class DrawBarChart extends View {
    private int gattScore;
    private int referenceGattscore;

    // constructor
    public DrawBarChart(Context context, int gattScore) {
        super(context);
        this.gattScore = gattScore / 100;
        referenceGattscore = 5000 / 100;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint(); //
        paint.setStyle(Paint.Style.FILL);


        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        // reference rectabgle
        paint.setColor(Color.GREEN);
        canvas.drawRect(200, referenceGattscore, 300, 1000, paint);


        canvas.drawRect(400, gattScore, 500, 1000, paint);

        paint.setColor(Color.BLACK);
        canvas.drawText("Reference Gatt (Pint of heineken for €4.50) : ", 200, 750, paint);
        canvas.drawText("Your Gatt (" +Integer.toString(gattScore) +")" , 400, 850, paint);





    }
}
