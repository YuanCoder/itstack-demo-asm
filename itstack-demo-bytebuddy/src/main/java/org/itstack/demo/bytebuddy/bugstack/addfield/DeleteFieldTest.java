package org.itstack.demo.bytebuddy.bugstack.addfield;

import net.bytebuddy.ByteBuddy;
import org.itstack.demo.bytebuddy.bugstack.Person;

/**
 * @author Jenpin
 * @date 2020/6/27 16:59
 * @description 删除字段
 **/
public class DeleteFieldTest {

    public static void main(String[] args) {

    }


    public void buildDeleteFiledDynamicType() {
        new ByteBuddy().redefine(Person.class);
    }
}
