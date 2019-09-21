package com.hsbc.sb.memory;

import com.hsbc.sb.core.SocialBookService;
import com.hsbc.sb.core.User;
import com.hsbc.sb.core.Wall;
import com.hsbc.sb.exception.ApplicationRuntimeException;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.UUID.randomUUID;

/**
 *  In memory SocialBookInMemoryService provides application with inmemory storage of the user data.
 *  It exposes the following services
 *
 * @author Saumadip Mazumder
 */
@Component
public class SocialBookInMemoryService implements SocialBookService {

    public static final int MAX_ALLOWED_MESSAGE_LENGTH = 140;
    private final Map<Long, User> longUserMap;


    public SocialBookInMemoryService() {
        this.longUserMap = new ConcurrentHashMap<>();
    }


    @Override
    public long create(String name) {
        long id = randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        longUserMap.put(id, new User(id,name,new Wall<>(s -> s.length() <= MAX_ALLOWED_MESSAGE_LENGTH)));
        return id;
    }

    @Override
    public User getUser(long id) {

        isValidUser(id);
        return longUserMap.get(id);
    }

    @Override
    public void post(long userId, String message) {

        isValidUser(userId);
        User user = longUserMap.get(userId);
        user.post(message);
    }

    @Override
    public void followUser(long userId, long userToFollowId) {

        if(!longUserMap.containsKey(userId) || !longUserMap.containsKey(userToFollowId))
            throw new ApplicationRuntimeException("Action could not be performed");

        if(userId == userToFollowId)
            throw new ApplicationRuntimeException("You seem to be self obsessed!!");

        User user = longUserMap.get(userId);
        user.follow(longUserMap.get(userToFollowId));
    }


    @Override
    public Map<String,Deque<String>> viewTimeLine(long userId) {

        isValidUser(userId);

        User user = longUserMap.get(userId);
        Set<User> following = user.getFollowing();

        Map<String, Deque<String>> userNameToWallMap = new HashMap<>();
        for(User followedUser : following)
        {
            userNameToWallMap.put(followedUser.getName(),followedUser.displayListOfMessages());
        }
        return userNameToWallMap;
    }

    private void isValidUser(long id) {
        if (!longUserMap.containsKey(id))
            throw new ApplicationRuntimeException("User doesn't exist");
    }

}
