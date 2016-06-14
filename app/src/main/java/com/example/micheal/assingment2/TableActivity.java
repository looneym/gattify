package com.example.micheal.assingment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity Class to display a TableLayout of Gatt entities and perform CRUD operations, sorting etc.
 */
public class TableActivity extends AppCompatActivity {
    int GLOBAL_TOUCH_POSITION_X = 0;
    int GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
    int GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;
    int GLOBAL_TOUCH_POSITION_Y = 0;

    private static final int LARGE_MOVE = 20;
    int cellWidth;
    TableLayout myTable;
    GestureDetector gestureDetector;
    private final int DELETE_ITEM = 1, DELETE_ALL = 2, SHARE = 3, COMPARE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        ScrollView sv = (ScrollView) findViewById(R.id.tableContainer);
        sv.setOnTouchListener(
                new ScrollView.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent m) {
                        handleTouch(m);
                        return true;
                    }
                });


        // Ensures TableCells take up one third of the screen
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        cellWidth = screenWidth / 3;

        // Prepare the TableLayout, retrieve the entities and call method to fill it
        myTable = (TableLayout) findViewById(R.id.myTable);

        populateTable(getGatts());

    }



    // Column names and click listeners for same
    private void populateTableHeaders() {


        // LEFT

        TextView tvLeft = new TextView(TableActivity.this);
        tvLeft.setText("Name");
        tvLeft.setTextSize(30);
        tvLeft.setMaxWidth(cellWidth);
        tvLeft.setPadding(50, 50, 0, 0);
        tvLeft.setGravity(Gravity.CENTER);
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sort Name");
            }
        });

        // CENTER

        TextView tvCenter = new TextView(TableActivity.this);
        tvCenter.setText("Price");
        tvCenter.setMaxWidth(cellWidth);
        tvCenter.setTextSize(30);
        tvCenter.setPadding(0, 50, 0, 0);
        tvCenter.setGravity(Gravity.CENTER);
        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sort Price");
            }
        });

        // RIGHT

        TextView tvRight = new TextView(TableActivity.this);
        tvRight.setText("Score");
        tvRight.setMaxWidth(cellWidth);
        tvRight.setTextSize(30);
        tvRight.setPadding(0, 50, 50, 0);
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sort Gattify Score");
            }
        });

        // Table row creation and insertion

        TableRow tr = new TableRow(TableActivity.this);
        tr.addView(tvLeft);
        tr.addView(tvCenter);
        tr.addView(tvRight);
        tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
        myTable.addView(tr);

    }


    // Dynamically create TableRows using DB entities
    private void populateTable(List<Gatt> gatts) {

        // Clear al views to prevent stacking
        myTable.removeAllViews();

        // Get column names from helper method
        populateTableHeaders();

        // Row creation
        for (Gatt gatt : gatts) {

            // Entity values
            String name = gatt.getName();
            String price = String.valueOf(gatt.getPrice());
            double x = gatt.getScore();
            int y = (int)Math.round(x);
            String gattScore = String.valueOf(y);

            // LEFT

            TextView tvLeft = new TextView(TableActivity.this);
            tvLeft.setText(name);
            tvLeft.setTag(gatt);
            tvLeft.setTextSize(20);
            tvLeft.setMaxWidth(cellWidth);
            tvLeft.setPadding(20, 30, 5, 5);
            tvLeft.setGravity(Gravity.CENTER_HORIZONTAL);
//            tvLeft.setPadding(50, 50, 0, 0);
//            tvLeft.setGravity(Gravity.CENTER);

            // CENTER

            TextView tvCenter = new TextView(TableActivity.this);
            tvCenter.setText(price);
            tvCenter.setMaxWidth(cellWidth);
            tvCenter.setTextSize(20);
            tvCenter.setPadding(5, 30, 5, 5);
            tvCenter.setGravity(Gravity.CENTER);
//            tvCenter.setPadding(0, 50, 0, 0);
//            tvCenter.setGravity(Gravity.CENTER);

            // RIGHT

            TextView tvRight = new TextView(TableActivity.this);
            tvRight.setText(gattScore);
            tvRight.setMaxWidth(cellWidth);
            tvRight.setTextSize(20);
            tvRight.setPadding(5, 30, 5, 5);
            tvRight.setGravity(Gravity.LEFT);

            // Table row creation and insertion

            final TableRow tr = new TableRow(TableActivity.this);
            tr.addView(tvLeft);
            tr.addView(tvCenter);
            tr.addView(tvRight);
            tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

            // Short click listener for each row
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // NO LOGIC IMPLEMENTED
                }
            });

            // Long click listener to trigger context menu and pass it entity values
            tr.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    // Get entity values
                    TextView tv = (TextView) tr.getChildAt(0);
                    String name = (tv.getText().toString());
                    Gatt g = (Gatt) tv.getTag();
                    Long id = g.getId();

                    // Make available for retrieval
                    setIntent(getIntent().putExtra("id", id));
                    setIntent(getIntent().putExtra("name", name));


                    // Voodoo magic
                    registerForContextMenu(v);
                    openContextMenu(v);
                    unregisterForContextMenu(v);
                    return true;

                }
            });

            // Add a horizontal line to each row for visual effect
            View v = new View(this);
            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
            v.setBackgroundColor(Color.rgb(51, 51, 51));
            v.setPadding(0, 20, 0, 10);

            // Add TableRow and line to the table
            myTable.addView(v);
            myTable.addView(tr);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View tr,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, tr, menuInfo);

        // Get entity values
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final Long id = intent.getLongExtra("id", 0);

        // Context menu header/title

        TextView gattDetails = new TextView(this);
        gattDetails.setText("Gatt Object \n" + "name: " + name + "\n" + "id: " + id.toString());
        gattDetails.setTextSize(20);
        gattDetails.setGravity(Gravity.CENTER);
        menu.setHeaderView(gattDetails);

        // Menu items, String values are handled by onContextItemSelected() method

        MenuItem item1 = menu.add(0, DELETE_ITEM, 0, "Delete");
        MenuItem item2 = menu.add(0, DELETE_ALL, 2, "Delete All");
        MenuItem item3 = menu.add(0, SHARE, 3, "Share");
        MenuItem item4 = menu.add(0, COMPARE, 3, "Compare");

    }

    // Handler logic for context menu items
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // Get entity values
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final Long id = intent.getLongExtra("id", 0);

        switch (item.getItemId()) {
            case DELETE_ITEM:
                System.out.println("Delete Item" + name);
                Gatt toDelete = Gatt.findById(Gatt.class, id);
                Gatt.delete(toDelete);
                populateTable(getGatts());
                return true;
            case DELETE_ALL:
                System.out.println("Delete All");
                Gatt.deleteAll(Gatt.class);
                populateTable(getGatts());
                return true;
            case SHARE:
                Gatt gatt = Gatt.findById(Gatt.class, id);
                share(gatt);
                return true;
            case COMPARE:
                Gatt gattToCompare = Gatt.findById(Gatt.class, id);
                compare(gattToCompare);
                return true;


        }
        return super.onContextItemSelected(item);
    }

    // Activity change - Main Page
    private void showMainPage() {
        System.out.println("showmain");
        Intent showMainActivity = new Intent(TableActivity.this, MainActivity.class);
        startActivity(showMainActivity);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // Gesture catcher methods

    private void moveRight() {
        // NO LOGIC YET
    }

    private void moveLeft() {
        showMainPage();
    }

    private List<Gatt> getGatts(){
        List<Gatt> gatts = Gatt.listAll(Gatt.class);
        return gatts;
    }

    private void sortGattify(){
        List<Gatt> gattsTmp = Gatt.listAll(Gatt.class);
        ArrayList<Gatt> gatts = new ArrayList<Gatt>(gattsTmp);
        // do comparator here
    }

    void handleTouch(MotionEvent m){
        //Number of touches
        int pointerCount = m.getPointerCount();
        if(pointerCount == 2){
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;
            switch (action)

            {
                case MotionEvent.ACTION_POINTER_UP:
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = (int) m.getY(1);
                    if (GLOBAL_TOUCH_POSITION_Y > GLOBAL_TOUCH_CURRENT_POSITION_Y) {
                        System.out.println("two finger swipe up");
                        clearTable();
                    } else {
                        System.out.println("two finger swipe down");

                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    GLOBAL_TOUCH_POSITION_X = (int) m.getX(1);
                    GLOBAL_TOUCH_POSITION_Y = (int) m.getY(1);
                    break;
                default:
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

    void clearTable(){
        Gatt.deleteAll(Gatt.class);
        Intent showTableActivity = new Intent(TableActivity.this, TableActivity.class);
        startActivity(showTableActivity);
    }

    void share(Gatt gatt){
        String gName = gatt.getName();
        String gScore = String.valueOf(gatt.getScore());
        String message = "Check out this awesome deal I found using Gattify! " + gName + " " + gScore;
        System.out.println(message);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Share your Gatt with the world"));
    }

    void compare(Gatt gatt){
        Intent showCompare = new Intent(TableActivity.this, DrawActivity.class);
        showCompare.putExtra("score", gatt.getScore());
        startActivity(showCompare);
    }



}




