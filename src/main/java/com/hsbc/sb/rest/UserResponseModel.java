package com.hsbc.sb.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Used only by the rest layer to send Back response
 * hence it is package private
 */
class UserResponseModel implements Serializable {

    private static final long serialVersionUID = 1224825191681233789L;

    @JsonProperty("userName")
    private String UserName;

    @JsonProperty("wall")
    private WallModel wallModel;

    @JsonCreator
    public UserResponseModel(String userName, WallModel wallModel) {
        UserName = userName;
        this.wallModel = wallModel;
    }
}
