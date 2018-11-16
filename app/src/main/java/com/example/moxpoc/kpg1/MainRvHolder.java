package com.example.moxpoc.kpg1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainRvHolder extends RecyclerView.ViewHolder {
        public final TextView firstNameView, secondNameView;
        public LinearLayout linearLayout;
        public MainRvHolder(View view){
            super(view);
            linearLayout = view.findViewById(R.id.linLayout);
            firstNameView = view.findViewById(R.id.firstNameView);
            secondNameView = view.findViewById(R.id.secondNameView);
        }
    }
