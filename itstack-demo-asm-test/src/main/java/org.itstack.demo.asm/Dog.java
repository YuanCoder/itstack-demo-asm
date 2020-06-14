package org.itstack.demo.asm;

/**
 * @author Jenpin
 * @date 2020/6/14 11:49
 * @description TODO
 **/
public class Dog {

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }

    private String name;

    private Integer age;

    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String say() {
        return "喵喵喵";
    }

}