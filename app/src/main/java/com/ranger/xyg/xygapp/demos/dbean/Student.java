package com.ranger.xyg.xygapp.demos.dbean;

import java.util.List;

/**
 * Created by xyg on 2017/5/23.
 */
public class Student {
    private String name;
    private List<Course> coursesList;//所修的课程

    public List<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

