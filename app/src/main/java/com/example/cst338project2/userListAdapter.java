package com.example.cst338project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class userListAdapter extends RecyclerView.Adapter<userListAdapter.userListViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context mContext;

    public userListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }
    @NonNull
    @Override
    public userListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.showusers_recycler_item, parent, false);
        userListViewHolder viewHolder = new userListViewHolder(itemView);
        return viewHolder;


    }


    @Override
    public void onBindViewHolder(@NonNull userListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class userListViewHolder extends RecyclerView.ViewHolder {

        private TextView userItemView;
        private int mPosition;

        public userListViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
