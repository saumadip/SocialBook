package com.hsbc.sb.core;

/**
 * Start Following the user passed
 * @param <T>
 * @author Saumadip Mazumder
 */
public interface Followable<T extends User> {

    void follow(T user);
}
