package com.example.vietpham.restaurant;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Drinks extends Fragment {
    private Menu menu = MainActivity.getMenu();

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drinks_fragment, container, false);

        Resources r = getResources();
        String name = "com.example.vietpham.restaurant";

        ArrayList<Meal> drinksMenu = menu.getDrinksMenu();
        //Log.d(("menu :\n"), menu.getMenu().toString());

        int[] resIDs = new int[3];
        //Log.d("Before", "1st");
        for(int i = 0; i < 3; i++) {
            resIDs[i] = r.getIdentifier("drinks_fragment_container" + String.format("%d", (i + 1)), "id", name);



                getChildFragmentManager()
                        .beginTransaction()
                        .add(resIDs[i], SingleMeal.newInstance(drinksMenu.get(i).getId(),
                                drinksMenu.get(i).getName(),
                                drinksMenu.get(i).getDescription(),
                                drinksMenu.get(i).getCategory(),
                                drinksMenu.get(i).getImageUrl(),
                                drinksMenu.get(i).getPrice()))
                        .commit();

        }


        return view;
    }
}
