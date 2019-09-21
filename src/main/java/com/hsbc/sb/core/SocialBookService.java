package com.hsbc.sb.core;

import java.util.Deque;
import java.util.Map;

/**
 * Implement SocialBookService to create new SocialNetwork
 *
 * Contains the must have functionality of a service
 *
 * @author Saumadip Mazumder
 */
public interface SocialBookService {

    /**
     * Create user and return the id
     * @param name
     * @return
     */
    long create(String name);


    /**
     * Returns user
     * @param id
     * @return
     */
    User getUser(long id);

    /**
     * Post message on wall
     * @param id
     * @param message
     */
     void post(long id,String message);

    /**
     * A user should be able to follow another user.
     * Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.
     * @param userID
     * @param userToFollowId
     */
     void followUser(long userID, long userToFollowId);


    /**
     * TimeLine A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.
     * @param userId
     */
    Map<String, Deque<String>> viewTimeLine(long userId);

}
