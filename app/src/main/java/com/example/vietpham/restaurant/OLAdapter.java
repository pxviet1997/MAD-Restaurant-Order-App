package com.example.vietpham.restaurant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OLAdapter extends RecyclerView.Adapter<OLAdapter.OLViewHolder> {
    private ArrayList<OrderedList> orderedList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class OLViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView nameView;
    public TextView quantityView;
    public Button button;

        public OLViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOL);
            nameView = itemView.findViewById(R.id.descriptionOL);
            quantityView = itemView.findViewById(R.id.quantityOL);
            button = itemView.findViewById(R.id.cancelButtonOL);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public OLAdapter(ArrayList<OrderedList> oL) {
        orderedList = oL;
    }

    @NonNull
    @Override
    public OLViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ordered_list_meal, viewGroup, false);
        OLViewHolder oLVH = new OLViewHolder(view, mListener);
        return oLVH;
    }

    @Override
    public void onBindViewHolder(@NonNull OLViewHolder olViewHolder, int i) {
        OrderedList currentOL = orderedList.get(i);

        Log.d("position: \n", String.valueOf(i));

        Picasso.get().load(currentOL.getImage()).into(olViewHolder.imageView);
        olViewHolder.nameView.setText(currentOL.getName());
        olViewHolder.quantityView.setText(" X" + currentOL.getQuantity());

    }

    @Override
    public int getItemCount() {
        Log.d("OLAdapter: \n", String.valueOf(orderedList.size()));
        return orderedList.size();
    }
}
