package com.hsbc.sb.rest;

import java.io.Serializable;

/**
 * Used only by the rest layer to accept user requests
 * hence it is package private
 */
class UserRequestModel implements Serializable {

    private static final long serialVersionUID = -6845577614645765462L;

    private String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

}
