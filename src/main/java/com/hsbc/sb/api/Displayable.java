package com.hsbc.sb.api;

import java.util.List;

/**
 * Display Users wall
 */
public interface Displayable<T> {

   List<T> display();

}
