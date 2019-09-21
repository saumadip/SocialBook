package com.hsbc.sb.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Deque;

/**
 * Used only by the rest layer to create and display user messages
 * hence it is package private
 */
class WallModel implements Serializable {

    private static final long serialVersionUID = -2479114584941070131L;

    Deque<String> messages;

    @JsonCreator
    public WallModel(@JsonProperty("messages") Deque<String> messages) {
        this.messages = messages;
    }

    public Deque<String> getMessages() {
        return messages;
    }
}
