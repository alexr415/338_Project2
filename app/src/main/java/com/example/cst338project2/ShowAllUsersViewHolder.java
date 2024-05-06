package com.example.cst338project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowAllUsersViewHolder extends RecyclerView.ViewHolder {
    private final TextView userTextView;

    public ShowAllUsersViewHolder(@NonNull View itemView) {
        super(itemView);
        userTextView = itemView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind (String text){
        userTextView.setText(text);
    }

    static ShowAllUsersViewHolder create (ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showusers_recycler_item, parent, false);
        return new ShowAllUsersViewHolder(view);
    }


}
