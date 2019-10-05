package com.example.vietpham.restaurant;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Pasta extends Fragment {
    private Menu menu = MainActivity.getMenu();

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pasta_fragment, container, false);

        Resources r = getResources();
        String name = "com.example.vietpham.restaurant";

        ArrayList<Meal> pastaMenu = menu.getPastaMenu();
        //Log.d(("menu :\n"), menu.getMenu().toString());

        int[] resIDs = new int[5];
        //Log.d("Before", "1st");
        for(int i = 0; i < 5; i++) {
            resIDs[i] = r.getIdentifier("pasta_fragment_container" + String.format("%d", (i + 1)), "id", name);



                getChildFragmentManager()
                        .beginTransaction()
                        .add(resIDs[i], SingleMeal.newInstance(pastaMenu.get(i).getId(),
                                pastaMenu.get(i).getName(),
                                pastaMenu.get(i).getDescription(),
                                pastaMenu.get(i).getCategory(),
                                pastaMenu.get(i).getImageUrl(),
                                pastaMenu.get(i).getPrice()))
                        .commit();

        }


        return view;
    }
}
