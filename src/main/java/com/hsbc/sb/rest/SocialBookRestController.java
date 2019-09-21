package com.hsbc.sb.rest;

import com.hsbc.sb.core.User;
import com.hsbc.sb.memory.SocialBookInMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Deque;
import java.util.Map;

@RestController
public class SocialBookRestController {

    @Autowired
    private SocialBookInMemoryService socialBookInMemoryService;

    /**
     * Returns a newly created userId
     * @param userResponseModel
     * @return
     */
    @PostMapping(value="/user", consumes = "application/json")
    public ResponseEntity<String> create(@RequestBody UserRequestModel userResponseModel) {

        long id = socialBookInMemoryService.create(userResponseModel.getUserName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{userId}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Display users Wall
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponseModel> getWall(@PathVariable long userId) {

        User user = socialBookInMemoryService.getUser(userId);
        WallModel wallModel = new WallModel(user.displayListOfMessages());
        UserResponseModel userResponseModel = new UserResponseModel(user.getName(),wallModel);

        return ResponseEntity.ok(userResponseModel);
    }


    /**
     * userId can follow followedUserId
     * @param userId
     * @param followedUserId
     * @return
     */
    @GetMapping("/user/{userId}/follow/{followedUserId}")
    public ResponseEntity<String> followUser(@PathVariable long userId, @PathVariable long followedUserId){
        socialBookInMemoryService.followUser(userId,followedUserId);
        return ResponseEntity.ok().build();

    }

    /**
     * Post comment or multiple comments on own messages
     * @param userId
     * @param wallModel
     * @return
     */
    @PostMapping("/user/{userId}/")
    public ResponseEntity<String> postOnWall(@PathVariable long userId, @RequestBody WallModel wallModel) {

        wallModel.getMessages().forEach(message->socialBookInMemoryService.post(userId,message));
        return ResponseEntity.ok().build();
    }


    /**
     * Shows the current users timeline
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}/displaytimeline")
    public ResponseEntity<Map<String, Deque<String>>> displayTimeLine(@PathVariable long userId) {

        Map<String, Deque<String>> userNameToMessage = socialBookInMemoryService.viewTimeLine(userId);
        return ResponseEntity.ok(userNameToMessage);
    }

}
