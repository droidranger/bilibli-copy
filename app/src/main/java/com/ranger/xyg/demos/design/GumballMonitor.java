package com.ranger.xyg.demos.design;


import com.ranger.xyg.library.utils.LogUtils;

/**
 * Created by xingyugang on 2017/11/9.
 */

public class GumballMonitor {

    GumballMachine machine;

    public GumballMonitor(GumballMachine machine) {
        this.machine = machine;
    }

    public void report() {
        LogUtils.d("proxy_model_demo", "Gumball Machine: " + this.machine.getLocation());
        LogUtils.d("proxy_model_demo", "Current inventory: " + this.machine.getCount() + " gumballs");
        LogUtils.d("proxy_model_demo", "Current state: " + this.machine.getState());
    }
}
