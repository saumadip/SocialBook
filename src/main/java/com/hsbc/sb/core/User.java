package com.hsbc.sb.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class User implements Followable{

    private final long id;

    private final String name;

    private final Set<User> following;

    private final Wall<String> stringWall;

    public User(long id, String name, Wall<String> wall) {
        this.id = id;
        this.name = name;
        this.stringWall = wall;
        this.following = new HashSet<>();
    }

    public void follow(User user) {
            following.add(user);
    }

    public void post(String message) {
        stringWall.post(message);
    }

    public Deque<String> displayListOfMessages() {
        return stringWall.display();
    }

    public String getName() {
        return name;
    }

    public Set<User> getFollowing() {
        return Collections.unmodifiableSet(following);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return new EqualsBuilder()
                .append(id, user.id)
                .append(getName(), user.getName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(getName())
                .toHashCode();
    }
}
