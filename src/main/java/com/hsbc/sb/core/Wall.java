package com.hsbc.sb.core;

import com.hsbc.sb.exception.ApplicationRuntimeException;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;

public class Wall<T> implements Postable<T>,Displayable {

    private final Deque<T> content;

    private Predicate<T> predicate;

    public Wall(Predicate<T> tPredicate) {
        content = new ConcurrentLinkedDeque<>();
        this.predicate = tPredicate;
    }

    @Override
    public Deque<T> display() {
        return content;
    }

    @Override
    public void post(T message) {
        if(!predicate.test(message))
            throw new ApplicationRuntimeException("Message doesn't meet the criteria");

        content.addFirst(message);
    }
}
