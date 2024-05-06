package com.example.cst338project2;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.cst338project2.DB.User;

public class ShowAllUsersListAdapter extends ListAdapter<User, ShowAllUsersViewHolder> {

    public ShowAllUsersListAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    public ShowAllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ShowAllUsersViewHolder.create(parent);
    }

    public void onBindViewHolder(@NonNull ShowAllUsersViewHolder holder, int position) {
        User current = getItem(position);
        Log.i("ShowAllUsersListAdapter", "onBindViewHolder: " + current.getUsername());
        holder.bind(current.getUsername());
    }

    static class UserDiff extends DiffUtil.ItemCallback<User> {

        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem == newItem;
        }

        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUsername().equals(newItem.getUsername());
        }
    }
}
