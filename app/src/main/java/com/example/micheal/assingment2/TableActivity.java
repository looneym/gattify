package com.example.micheal.assingment2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Micheal on 26/04/16.
 */
public class TableActivity extends AppCompatActivity {
        int cellWidth;
        TableLayout myTable;
    private final int DELETE_ITEM = 1, DELETE_ALL = 2, ID_TEXT3 = 3;

        @Override
protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.table);


            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);


            int screenWidth = metrics.widthPixels;
            cellWidth = screenWidth / 3;

            myTable = (TableLayout) findViewById(R.id.myTable);
            populateTable();
        }

        private void populateTableHeaders() {

        TextView tvLeft = new TextView(TableActivity.this);
        tvLeft.setText("Name");
        tvLeft.setTextSize(20);
        tvLeft.setMaxWidth(cellWidth);
        tvLeft.setPadding(20, 5, 5, 5);
        tvLeft.setGravity(Gravity.LEFT);

        TextView tvCenter = new TextView(TableActivity.this);
        tvCenter.setText("Price");
        tvCenter.setMaxWidth(cellWidth);
        tvCenter.setTextSize(20);
//        tvCenter.setPadding(5, 5, 5, 5);
        tvCenter.setGravity(Gravity.CENTER);

        TextView tvRight = new TextView(TableActivity.this);
        tvRight.setText("Gattify Score");
//        tvRight.setMaxWidth(cellWidth);
        tvRight.setTextSize(20);
        tvRight.setPadding(0, 5, 20, 5);
//        tvRight.setGravity(Gravity.RIGHT);

        TableRow tr = new TableRow(TableActivity.this);
        tr.addView(tvLeft);
        tr.addView(tvCenter);
        tr.addView(tvRight);
        tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));


        myTable.addView(tr);
    }



        private void populateTable() {





            myTable.removeAllViews();
            populateTableHeaders();

                List<Gatt> gatts = Gatt.listAll(Gatt.class);
                for (Gatt gatt : gatts) {
                        String name = gatt.getName();
                        String price = String.valueOf(gatt.getPrice());
                        String gattScore = "2345";

                        TextView tvLeft = new TextView(TableActivity.this);
                        tvLeft.setText(name);
                        tvLeft.setTag(gatt);
                        tvLeft.setTextSize(20);
                        tvLeft.setMaxWidth(cellWidth);
                        tvLeft.setPadding(20, 5, 5, 5);
                        tvLeft.setGravity(Gravity.LEFT);

                        TextView tvCenter = new TextView(TableActivity.this);
                        tvCenter.setText(price);
                        tvCenter.setMaxWidth(cellWidth);
                        tvCenter.setTextSize(20);
//        tvCenter.setPadding(5, 5, 5, 5);
                        tvCenter.setGravity(Gravity.CENTER);

                        TextView tvRight = new TextView(TableActivity.this);
                        tvRight.setText(gattScore);
//        tvRight.setMaxWidth(cellWidth);
                        tvRight.setTextSize(20);
                        tvRight.setPadding(0, 5, 20, 5);
//        tvRight.setGravity(Gravity.RIGHT);

                        final TableRow tr = new TableRow(TableActivity.this);
                        tr.addView(tvLeft);
                        tr.addView(tvCenter);
                        tr.addView(tvRight);
                        tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

                        tr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                displayDetails(tr);
                            }
                        });

                        registerForContextMenu(tr);

                        tr.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                TextView tv = (TextView) tr.getChildAt(0);
                                String name = (tv.getText().toString());

                                Gatt g = (Gatt) tv.getTag();
                                Long id = g.getId();


                                setIntent(getIntent().putExtra("id", id));
                                setIntent(getIntent().putExtra("name", name));

                                registerForContextMenu(v);
                                openContextMenu(v);
                                unregisterForContextMenu(v);
                                return true;
//                                openContextMenu(tr);
//                                return true;
                            }
                        });

                        View v = new View(this);
                        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                        v.setBackgroundColor(Color.rgb(51, 51, 51));



                        myTable.addView(v);


                        myTable.addView(tr);


                }



        }

        private void displayDetails(TableRow tr){
                TextView tv = (TextView) tr.getChildAt(0);
                System.out.println(tv.getText().toString());
                Gatt gatt = (Gatt) tv.getTag();
                System.out.println(gatt);


        }

        public void showContextMenu(TableRow tr){

        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View tr,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, tr, menuInfo);


        Intent intent = getIntent();

        final String name = intent.getStringExtra("name");

        final Long id = intent.getLongExtra("id", 0);
        System.out.println("==============================");
        System.out.println(name);
        System.out.println(id);

        System.out.println("==============================");

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");

        MenuItem item1 = menu.add(0, DELETE_ITEM, 0, "Delete");
        MenuItem item2 = menu.add(0, DELETE_ALL, 2, "Delete All");




//            SubMenu textMenu = menu.addSubMenu("Delete");
//
//
//        textMenu.add(0, DELETE_ITEM, 0, "Delete");


        menu.setHeaderTitle("title");

        TextView gattDetails = new TextView(this);
        gattDetails.setText("Gatt Object \n" + "name: " + name + "\n" + "id: " + id.toString());
        gattDetails.setTextSize(20);
        gattDetails.setGravity(Gravity.CENTER);

        menu.setHeaderView(gattDetails);

        }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = getIntent();

        final String name = intent.getStringExtra("name");

        final Long id = intent.getLongExtra("id", 0);
        switch (item.getItemId()) {
            case DELETE_ITEM:
                System.out.println("Delete Item" + name);
                Gatt toDelete = Gatt.findById(Gatt.class, id);
                Gatt.delete(toDelete);
                populateTable();


                return true;
            case DELETE_ALL:
                System.out.println("Delete All");
                Gatt.deleteAll(Gatt.class);
                populateTable();
                return true;

        }
        return super.onContextItemSelected(item);
    }
    }




