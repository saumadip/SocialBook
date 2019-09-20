package com.hsbc.sb.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Wall<T> implements Postable<T>,Displayable {

    private final List<T> content;

    private Predicate<T> predicate;

    public Wall(Predicate<T> tPredicate) {
        content = new ArrayList<>();
        this.predicate = tPredicate;
    }

    @Override
    public List<T> display() {
        return content;
    }

    @Override
    public void post(T message) {
        if(predicate.test(message)){
            content.add(message);
        }
    }
}
