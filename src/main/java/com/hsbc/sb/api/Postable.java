package com.hsbc.sb.api;

/**
 * Post messages to users wall
 */
public interface Postable<T> {

    void post(T message);
}
