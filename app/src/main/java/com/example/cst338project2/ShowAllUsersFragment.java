package com.example.cst338project2;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cst338project2.DB.User;

import java.util.List;

public class ShowAllUsersFragment extends Fragment {

    private ShowAllUsersViewModel mViewModel;
    private RecyclerView recyclerView;
    private ShowAllUsersListAdapter adapter;

    public static ShowAllUsersFragment newInstance() {
        return new ShowAllUsersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_all_users, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShowAllUsersViewModel.class);

        // Initialize the adapter and set it to the RecyclerView
        adapter = new ShowAllUsersListAdapter(new ShowAllUsersListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Observe the data and update the RecyclerView when the data changes
        mViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                // Update the cached copy of the users in the adapter.
                adapter.submitList(users);
            }
        });
    }
}