package com.hsbc.sb.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsbc.sb.api.User;
import com.hsbc.sb.memory.SocialBookInMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class SocialBookRestController {

    @Autowired
    private SocialBookInMemoryService socialBookInMemoryService;

    private final Gson gson;

    public SocialBookRestController() {
        this.gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();;
    }

    /**
     *  Returns a newly created userId
     * @param name
     * @return
     */
    @PostMapping("/user")
    public ResponseEntity<String> create(@RequestBody String name) {
        long id = socialBookInMemoryService.create(name);
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
    public ResponseEntity<String> getWall(@PathVariable long userId) {
        User user = socialBookInMemoryService.getUser(userId);
        String json = gson.toJson(user.displayListOfMessages());
        return ResponseEntity.ok(json);
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
     * Post comment on own wall
     * @param userId
     * @param message
     * @return
     */
    @PostMapping("/user/{userId}/")
    public ResponseEntity<String> postOnWall(@PathVariable long userId, @RequestBody String message) {
        socialBookInMemoryService.post(userId,message);
        return ResponseEntity.ok().build();
    }


    /**
     * Shows the current users timeline
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}/displaytimeline")
    public ResponseEntity<String> displayTimeLine(@PathVariable long userId) {
        Map<String, List<String>> stringListMap = socialBookInMemoryService.viewTimeLine(userId);
        return ResponseEntity.ok(gson.toJson(stringListMap));
    }

}
