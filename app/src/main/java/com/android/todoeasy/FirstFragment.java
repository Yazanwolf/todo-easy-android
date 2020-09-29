package com.android.todoeasy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private List<Todo> todos;
    private BaseExpandableListAdapter listAdapter;
    private ExpandableListView todoListView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        todos = new ArrayList<>();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Todo newtodo = Todo.builder()
                .name("Yesssssssss")
                .expiryDate(LocalDateTime.now())
                .finished(false)
                .description("this is an initial todo")
                .build();

        todos.add(newtodo);

        todoListView = getView().findViewById(R.id.todoListView);
        listAdapter = new TodoListAdapter(this.getContext(), this.todos);
        todoListView.setAdapter(listAdapter);
    }

}