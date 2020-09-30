package com.android.todoeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Todo> todos;

    public TodoListAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todo_list_item_group, null);
        }
        initGroupItems(groupPosition, convertView);
        return convertView;
    }

    private void initGroupItems(int groupPosition, View convertView) {
        Todo todo = todos.get(groupPosition);

        TextView todoName = convertView.findViewById(R.id.todoName);
        todoName.setText(todo.getName());

        TextView todoExpiryDate = convertView.findViewById(R.id.todoExpiryDate);
        todoExpiryDate.setText(getDateOnly(todo.getExpiryDate()));

        TextView todoExpiryTime = convertView.findViewById(R.id.todoExpiryTime);
        todoExpiryTime.setText(getTimeOnly(todo.getExpiryDate()));

        Button finishButton = convertView.findViewById(R.id.finishButton);
        finishButton.setVisibility(todo.isFinished() ? View.GONE : View.VISIBLE);
    }

    private String getDateOnly(LocalDateTime expiryDate) {
        return expiryDate.format(DateTimeFormatter.ofPattern("dd-mm-yyyy"));
    }

    private String getTimeOnly(LocalDateTime expiryDate) {
        return expiryDate.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todo_list_item_child, null);
        }
        Todo todo = todos.get(groupPosition);

        TextView todoDescription = convertView.findViewById(R.id.todoDescription);
        todoDescription.setText(todo.getDescription());
        return convertView;
    }

    @Override
    public int getGroupCount() {
        return todos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.todos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.todos.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
