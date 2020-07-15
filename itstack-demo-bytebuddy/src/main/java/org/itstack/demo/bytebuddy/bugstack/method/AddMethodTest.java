package org.itstack.demo.bytebuddy.bugstack.method;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import org.itstack.demo.bytebuddy.bugstack.Person;

/**
 * @author Jenpin
 * @date 2020/7/15 20:13
 * @description
 **/
public class AddMethodTest {

    /**
     * TODO
     * 通过 Redefine 实现 （通过生成子类实现）
     *    生成类回继承原有类，不影响原有类，
     * @return
     */
    public static DynamicType.Unloaded<Person> buildDynamicTypeByRedefine() {
        return new ByteBuddy(ClassFileVersion.JAVA_V8)
                .redefine(Person.class)
                .name(Person.class.getPackage().getName().concat(".").concat("PersonByRedefineAddMethod"))
                .defineMethod("buildAddress", Long.class, Visibility.PUBLIC)
                .intercept(MethodDelegation.to(InterceptMeth.class))
                .make();
    }

}
