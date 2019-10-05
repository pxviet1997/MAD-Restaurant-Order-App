package com.example.vietpham.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class SingleMeal extends Fragment {
    //RelativeLayout reLa = null;

    LinearLayout liLa = null;

    //FrameLayout frLa = null;

    ImageView imageView = null;

    Button addButton = null;
    Button lessButton = null;
    Button moreButton = null;

    int numberOfMeal = 0;

    TextView number = null;
    TextView description = null;
    TextView name = null;
    TextView price = null;

    String mealId = "";
    int orderedList = 0;
    String mealName = "";
    String mealDescription = "";
    int mealQuantity = 0;
    String mealCategory = "";
    String mealImageUrl = "";
    double mealPrice = 0;

    public static Menu menu = MainActivity.getMenu();

    /*public static Menu getMenu() {
        return menu;
    }*/

    public static SingleMeal newInstance(String id, String name, String description, String category, String imageUrl, double price) {
        SingleMeal sm = new SingleMeal();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("description", description);
        bundle.putString("category", category);
        bundle.putString("image", imageUrl);
        bundle.putDouble("price", price);
        sm.setArguments(bundle);
        return sm;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_meal, container, false);

        mealId = getArguments().getString("id");
        mealName = getArguments().getString("name");
        mealDescription = getArguments().getString("description");
        mealCategory = getArguments().getString("category");
        mealImageUrl = getArguments().getString("image");
        mealPrice = getArguments().getDouble("price");

        liLa = view.findViewById(R.id.meal);
        imageView = view.findViewById(R.id.imageView);

        addButton = (Button) view.findViewById(R.id.addButton);
        lessButton = (Button) view.findViewById(R.id.lessButton);
        moreButton = (Button) view.findViewById(R.id.moreButton);

        number = (TextView) view.findViewById(R.id.number);
        description = (TextView) view.findViewById(R.id.description);
        name = (TextView) view.findViewById(R.id.mealName);
        price = (TextView) view.findViewById(R.id.price);

        name.setText(mealName.toUpperCase());

        description.setText(mealDescription);

        price.setText(String.valueOf(mealPrice) + "$");

        Picasso.get().load(mealImageUrl).into(imageView);

        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOfMeal != 0) {
                    numberOfMeal--;
                    number.setText(Integer.toString(numberOfMeal));
                }
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfMeal++;
                number.setText(Integer.toString(numberOfMeal));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (numberOfMeal != 0) {
                    Meal meal = new Meal(mealId, menu.getOrderedListSize(), mealName, mealDescription, numberOfMeal, mealCategory, mealImageUrl, mealPrice);
                    menu.addToOrderedList(meal);
                    Log.d("menu SiM: \n", menu.menuList());

                    Log.d("meal", meal.toString());
                    //MealMenu.addMeal(meal, menu.getOrderedListSize()-1);
                    Toast.makeText(getActivity(), "Meal added to ordered list!", Toast.LENGTH_LONG).show();
                    numberOfMeal = 0;
                    number.setText("0");
                }
                else Toast.makeText(getActivity(), "Please choose quantity!", Toast.LENGTH_LONG).show();
                Log.d("ordered meal: \n", menu.getOrderedList().toString());
            }
        });

        return view;
    }
}
