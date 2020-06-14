package org.itstack.demo.asm;

/**
 * @author Jenpin
 * @date 2020/6/14 11:44
 * @description TODO
 **/
public class RunCatAndDog {

    public static void main(String[] args) {
        Cat cat = new Cat("Tom");
        Dog dog = new Dog("H");
        cat.getName();
        dog.getName();
    }
}
