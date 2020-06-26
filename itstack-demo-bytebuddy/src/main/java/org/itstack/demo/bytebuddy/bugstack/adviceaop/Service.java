package org.itstack.demo.bytebuddy.bugstack.adviceaop;

/**
 * @author Jenpin
 * @date 2020/6/26 18:20
 * @description
 **/
public class Service {
    @Log
    public int foo(int value) {
        System.out.println("foo: " + value);
        return value;
    }

    @Log
    public int bar(int value) {
        System.out.println("bar: " + value);
        return value;
    }
}
