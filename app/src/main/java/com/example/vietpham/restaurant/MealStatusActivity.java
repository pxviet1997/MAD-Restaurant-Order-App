package com.example.vietpham.restaurant;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MealStatusActivity extends AppCompatActivity {

    ArrayList<Meal> orderedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_status);
        Log.d("mealStatusActivity: \n", String.valueOf(OrderedListActivity.getCheck()));

        if (OrderedListActivity.getCheck() == 1) {

            orderedList = OrderedListActivity.getTempList();


        } else orderedList = new ArrayList<>();



        Resources r = getResources();
        String name = "com.example.vietpham.restaurant";

        int[] resIDs = new int[orderedList.size()];
        Log.d("resID: \n", String.valueOf(resIDs.length));
        //Log.d("Before", "1st");
        for(int i = 0; i < resIDs.length; i++) {
            resIDs[i] = r.getIdentifier("meal_status" + String.format("%d", (i + 1)), "id", name);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(resIDs[i], StatusMeal.newInstance(
                            orderedList.get(i).getId(),
                            orderedList.get(i).getName(),
                            orderedList.get(i).getImageUrl(),
                            orderedList.get(i).getStatus(),
                            orderedList.get(i).getQuantity()))
                    .commit();

        }

    }
}
