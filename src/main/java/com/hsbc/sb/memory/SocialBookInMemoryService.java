package com.hsbc.sb.memory;

import com.hsbc.sb.api.SocialBookService;
import com.hsbc.sb.api.User;
import com.hsbc.sb.api.Wall;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 *  In memory SocialBookInMemoryService provides application with inmemory storage of the user data.
 *  It exposes the following services

 * @author Saumadip Mazumder
 */
@Component
public class SocialBookInMemoryService implements SocialBookService {

    public static final int MAX_ALLOWED_MESSAGE_LENGTH = 140;
    private final Map<Long, User> longUserMap;
    private Random randomIdGenerator;

    public SocialBookInMemoryService() {
        this.longUserMap = new HashMap<>();
        this.randomIdGenerator = new Random();
    }


    @Override
    public long create(String name) {
        long id = randomIdGenerator.nextLong();
        longUserMap.put(id, new User(id,name,new Wall<>(s -> s.length() <= MAX_ALLOWED_MESSAGE_LENGTH)));
        return id;
    }

    @Override
    public User getUser(long id) {
        return longUserMap.get(id);
    }

    @Override
    public void post(long userId, String message) {

        if(!longUserMap.containsKey(userId))
            throw new RuntimeException("User doesn't exist");

        User user = longUserMap.get(userId);
        user.post(message);
    }

    @Override
    public void followUser(long userId, long userToFollowId) {

        if(!longUserMap.containsKey(userId) || !longUserMap.containsKey(userToFollowId))
            throw new RuntimeException("Action could not be performed");

        User user = longUserMap.get(userId);
        user.follow(longUserMap.get(userToFollowId));
    }


    @Override
    public Map<String,List<String>> viewTimeLine(long userId) {

        if(!longUserMap.containsKey(userId))
            throw new RuntimeException("User doesn't exist");

        User user = longUserMap.get(userId);
        Set<User> following = user.getFollowing();

        Map<String, List<String>> userNameToWallMap = new HashMap<>();
        for(User followedUser : following)
        {
            userNameToWallMap.put(followedUser.getName(),followedUser.displayListOfMessages());
        }
        return userNameToWallMap;
    }

}
