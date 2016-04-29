package com.example.micheal.assingment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity Class to display a TableLayout of Gatt entities and perform CRUD operations, sorting etc.
 */
public class TableActivity extends AppCompatActivity {
    private static final int LARGE_MOVE = 20;
    int cellWidth;
    TableLayout myTable;
    GestureDetector gestureDetector;
    private final int DELETE_ITEM = 1, DELETE_ALL = 2, ID_TEXT3 = 3;

    // Required to register swipe gestures while using a scrollable view container
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }

    // onTouchEvent required for onFling to be invoked (gestures)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event); // ...pass to gestureDetector above
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

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



}




