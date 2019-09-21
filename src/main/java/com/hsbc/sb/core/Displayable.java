package com.hsbc.sb.core;

import java.util.Deque;

/**
 * Display Users wall, messages can be stored in reverse chronological order or chronological order
 *
 * @author Saumadip Mazumder
 */
public interface Displayable<T> {

   Deque<T> display();

}
