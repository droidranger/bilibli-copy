package com.ranger.xyg.demos.design;

/**
 * Created by xingyugang on 2017/11/9.
 */

public class GumballMachine {
    private final String location;
    private final int count;

    public GumballMachine(String location, int count) {
        this.location = location;
        this.count = count;
    }

    public String getLocation() {
        return this.location;
    }

    public int getCount() {
        return count;
    }

    public String getState() {
        return null;
    }
}
