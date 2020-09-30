package com.android.todoeasy;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Todo {

    private final String name;
    private final LocalDateTime expiryDate;
    private final boolean finished;
    private final String description;

    public Todo(String name, LocalDateTime expiryDate, boolean finished, String description) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.finished = finished;
        this.description = description;
    }
}


