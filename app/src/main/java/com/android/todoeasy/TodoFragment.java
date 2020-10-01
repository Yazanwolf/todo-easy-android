package com.android.todoeasy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {

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
        return inflater.inflate(R.layout.todo_list_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Todo newTodo = Todo.builder()
                .name("this here is only temporary")
                .expiryDate(LocalDateTime.now())
                .finished(false)
                .description("this is an initial todo")
                .build();

        todoList.add(newTodo);

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

}