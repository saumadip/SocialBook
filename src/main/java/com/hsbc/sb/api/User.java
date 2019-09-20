package com.hsbc.sb.api;

import java.util.HashSet;
import java.util.List;
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

    public List<String> displayListOfMessages() {
        return stringWall.display();
    }

    public String getName() {
        return name;
    }

    public Set<User> getFollowing() {
        return following;
    }
}
