package com.ranger.xyg.demos.rxjava;

import android.content.Context;
import android.util.Log;

import com.ranger.xyg.demos.dbean.Course;
import com.ranger.xyg.demos.dbean.Student;
import com.ranger.xyg.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by xyg on 2017/5/22.
 */

public class RxJavaDemoUtils {

    private static final String TAG = RxJavaDemoUtils.class.getSimpleName();

    /**
     * Action是RxJava 的一个接口，常用的有Action0和Action1
     *
     */
    public static void utilsDemo1(final Context context) {
        Observable.just("hello", "world").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LogUtils.i("ygxing", s);
            }
        });
    }

    public static void utilsDemo2() {
        Observable<String> observable = Observable.just("hello", "world");

        /**
         * 定义三个对象，分别打包onNext(obj)、onError(error) 、onCompleted()。
         */

        // 处理onNext中的内容
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                LogUtils.i("ygxing", s);
            }
        };
        // 处理onError中的内容
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };
        // 处理onCompleted中的内容
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {

            }
        };

        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    private List<String> nameList = new ArrayList<>();

    public void utilsDemo3() {
        Student student1 = new Student();
        student1.setName("dddd");
        Student student2 = new Student();
        student2.setName("vvvvvv");
        Student student3 = new Student();
        student3.setName("aaaaa");
        Observable.just(student1, student2, student3)
                .map(new Func1<Student, String>() {// 得到name
                    @Override
                    public String call(Student student) {
                        String name = student.getName();
                        return name;
                    }
                })
                .map(new Func1<String, String>() {// name后加"-->"

                    @Override
                    public String call(String s) {
                        s += "-->";
                        return s;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        nameList.add(s);
                    }
                });
    }

    public void utilsDemo4() {
        final List<Student> studentList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();

        Course course1 = new Course();
        course1.setName("course1");
        course1.setId("1111");
        courseList.add(course1);
        Course course2 = new Course();
        course2.setName("course2");
        course2.setId("2222");
        courseList.add(course2);
        Course course3 = new Course();
        course3.setName("course3");
        course3.setId("3333");
        courseList.add(course3);

        Student student1 = new Student();
        student1.setName("dddd");
        student1.setCoursesList(courseList);

        Student student2 = new Student();
        student2.setName("vvvvvv");
        student2.setCoursesList(courseList);

        Student student3 = new Student();
        student3.setName("aaaaa");
        student3.setCoursesList(courseList);

        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        // action1有for循环
        Action1<List<Course>> action1 = new Action1<List<Course>>() {
            @Override
            public void call(List<Course> courses) {
                //遍历courses，输出cuouses的name
                for (int i = 0; i < courses.size(); i++){
                    Log.i(TAG, courses.get(i).getName());
                }
            }
        };
        Observable.from(studentList)
                .map(new Func1<Student, List<Course>>() {
                    @Override
                    public List<Course> call(Student student) {
                        return student.getCoursesList();
                    }
                })
                .subscribe(action1);

        // 不用for循环的写法
        Observable.from(studentList)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCoursesList());
                    }
                })
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        Log.i(TAG, course.getName());
                    }
                });
    }

}
