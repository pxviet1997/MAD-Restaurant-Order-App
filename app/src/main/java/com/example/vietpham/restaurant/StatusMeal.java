package com.example.vietpham.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class StatusMeal extends Fragment {
    LinearLayout statusMeal = null;
    ImageView mealImage = null;
    TextView mealDescription = null;
    TextView mealStatus = null;
    TextView mealQuantity = null;

    String mealId = "";
    String mealName = "";
    String mealImageUrl = "";

    public static StatusMeal newInstance(String id, String name, String imageUrl, String status, int quantity) {
        StatusMeal sm = new StatusMeal();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("image", imageUrl);
        bundle.putString("status", status);
        bundle.putInt("quantity", quantity);
        sm.setArguments(bundle);
        return sm;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_fragment, container, false);

        statusMeal = view.findViewById(R.id.statusMeal);
        mealImage = view.findViewById(R.id.mealStatusImage);
        mealDescription = view.findViewById(R.id.mealDescription);
        mealStatus = view.findViewById(R.id.status);
        mealQuantity = view.findViewById(R.id.quantity);

        mealId = getArguments().getString("id");
        mealName = getArguments().getString("name");
        mealImageUrl = getArguments().getString("image");

        Picasso.get().load(mealImageUrl).into(mealImage);
        mealDescription.setText(mealName );
        mealStatus.setText(getArguments().getString("status"));
        mealQuantity.setText(" X " + String.valueOf(getArguments().getInt("quantity")));



        return view;
    }
}
