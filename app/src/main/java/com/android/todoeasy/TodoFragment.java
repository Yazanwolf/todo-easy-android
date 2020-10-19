package com.android.todoeasy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {

    public static final String NEW_TODO_CREATED = "todo is received";
    public static final String TODO_PARCEL = "todo parcelable";
    private List<Todo> todoList;
    private BaseExpandableListAdapter listAdapter;
    private ExpandableListView todoListView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        todoList = new ArrayList<>();
        registerReceivers();
        return inflater.inflate(R.layout.todo_list_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObjects(view);
    }

    private void initObjects(@NonNull View view) {
        TextView fragmentTitle = view.findViewById(R.id.allTodoTasks);
        fragmentTitle.setText(todoList.size() > 0 ? R.string.all_todo_tasks : R.string.no_tasks);
        View listHeaderLayout = view.findViewById(R.id.todoListHeader);
        listHeaderLayout.setVisibility(todoList.size() > 0 ? View.VISIBLE : View.GONE);
        todoListView = view.findViewById(R.id.todoListView);
        listAdapter = new TodoListAdapter(this.getContext(), this.todoList);
        todoListView.setAdapter(listAdapter);
    }

    private void registerReceivers() {
        TodoReceiver todoReceiver = new TodoReceiver();
        IntentFilter intentFilter = new IntentFilter(NEW_TODO_CREATED);
        getActivity().registerReceiver(todoReceiver, intentFilter);
    }

    private class TodoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NEW_TODO_CREATED)) {
                Todo todo = intent.getParcelableExtra(TODO_PARCEL);
                todoList.add(todo);
                listAdapter.notifyDataSetChanged();
            }
        }
    }

}