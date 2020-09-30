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

import com.android.todoeasy.parcelable.TodoParcelable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.android.todoeasy.MainActivity.NEW_TODO;
import static com.android.todoeasy.MainActivity.NEW_TODO_HAS_BEEN_CREATED;

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
        initReceivers();
    }

    private void initReceivers() {
        TodoReceiver todoReceiver = new TodoReceiver();
        IntentFilter filter = new IntentFilter(NEW_TODO_HAS_BEEN_CREATED);
        getContext().registerReceiver(todoReceiver, filter);
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

    public class TodoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (Objects.requireNonNull(intent.getAction()).contains(NEW_TODO_HAS_BEEN_CREATED)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    TodoParcelable todoParcelable = extras.getParcelable(NEW_TODO);
                    Todo newTodo = Todo.builder()
                            .name(todoParcelable.getTaskName())
                            .expiryDate(readExpiryDate(todoParcelable))
                            .finished(false)
                            .build();
                    todoList.add(newTodo);
                    listAdapter.notifyDataSetChanged();
                }
            }

        }

        private LocalDateTime readExpiryDate(TodoParcelable todoParcelable) {
            LocalDate date = LocalDate.parse(todoParcelable.getDate());
            LocalTime time = LocalTime.parse(todoParcelable.getTime());
            return LocalDateTime.of(date.getYear(),
                    date.getMonth(),
                    date.getDayOfMonth(),
                    time.getHour(),
                    time.getMinute());
        }
    }

}