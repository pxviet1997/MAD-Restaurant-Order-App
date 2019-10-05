package com.example.vietpham.restaurant;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class OrderedListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private OLAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Socket s;
    private static ServerSocket ss;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter pw;
    private static String ip= "149.144.193.214";

    Button orderButton = null;
    private static int check = 0;
    ArrayList<Meal> orderedList;
    static ArrayList<Meal> temp = new ArrayList<>();
    String tableName = "TABLE 1";

    private static Menu menu = MainActivity.getMenu();

    public static int getCheck() {
        return check;
    }

    public static void resetTemp() { temp.clear(); }

    public static ArrayList<Meal> getTempList() {
        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_list);

        orderedList = menu.getOrderedList();
        Log.d("orderList: \n", orderedList.toString());

        final ArrayList<OrderedList> oL = new ArrayList<>();

        for (int i = 0; i < orderedList.size(); i++) {
            oL.add(new OrderedList(
                    orderedList.get(i).getImageUrl(),
                    orderedList.get(i).getName(),
                    orderedList.get(i).getQuantity()));
            temp.add(orderedList.get(i));
        }
        Log.d("temp size: \n", String.valueOf(temp.size()));

        orderButton = (Button) findViewById(R.id.orderButton);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderedList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please select a meal!", Toast.LENGTH_LONG).show();
                }
                else {
                    check = 1;
                    Toast.makeText(getApplicationContext(), "Order has been placed!", Toast.LENGTH_LONG).show();
                    String orderedListS = tableName + " JUST PLACED THE ORDER";
                    for (Meal item : orderedList) {
                        orderedListS +="\n" + String.format("%1$-20s\t\t%2$-20s" ,item.getName() ,("x"+item.getQuantity()));
                    }
                    FirebaseDatabase.getInstance().getReference().child("restaurant").push()
                            .setValue(new ChatMessage(orderedListS, tableName, "KITCHEN"));
                    menu.resetOrderedList();
                    finish();
                }
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OLAdapter(oL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OLAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int i) {
                oL.remove(i);
                orderedList.remove(i);
                temp.remove(i);
                
                mAdapter.notifyDataSetChanged();

            }
        });
    }
}
