package com.hsbc.sb.core;

/**
 * Post messages to users wall
 *
 * @author Saumadip Mazumder
 */
public interface Postable<T> {

    void post(T message);
}
