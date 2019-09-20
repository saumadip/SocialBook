package com.hsbc.sb.api;

/**
 * Start Following the user passed
 * @param <T>
 */
public interface Followable<T extends User> {

    void follow(T user);
}
