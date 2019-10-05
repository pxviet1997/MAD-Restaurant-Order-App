package com.example.vietpham.restaurant;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Pizza extends Fragment {

    private Menu menu = MainActivity.getMenu();

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pizza_fragment, container, false);

        Resources r = getResources();
        String name = "com.example.vietpham.restaurant";

        ArrayList<Meal> pizzaMenu = menu.getPizzaMenu();

        int[] resIDs = new int[6];
        //Log.d("Before", "1st");
        for(int i = 0; i < 6; i++) {
            resIDs[i] = r.getIdentifier("pizza_fragment_container" + String.format("%d", (i + 1)), "id", name);

                getChildFragmentManager()
                        .beginTransaction()
                        .add(resIDs[i], SingleMeal.newInstance(
                                pizzaMenu.get(i).getId(),
                                pizzaMenu.get(i).getName(),
                                pizzaMenu.get(i).getDescription(),
                                pizzaMenu.get(i).getCategory(),
                                pizzaMenu.get(i).getImageUrl(),
                                pizzaMenu.get(i).getPrice()))
                        .commit();

        }


        return view;
    }

}
