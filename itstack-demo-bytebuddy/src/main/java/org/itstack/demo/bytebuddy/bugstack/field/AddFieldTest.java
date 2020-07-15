package org.itstack.demo.bytebuddy.bugstack.field;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import org.itstack.demo.bytebuddy.bugstack.ByteBuddyUtils;
import org.itstack.demo.bytebuddy.bugstack.Person;

import java.io.File;

/**
 * @author Jenpin
 * @date 2020/6/26 20:17
 * @description
 *          redefine:(重定义) 对一个已有的类添加属性和方法，或者删除已经存在的方法实现。新添加的方法，如果签名和原有方法一致，则原有方法会消失。
 *          subclass:(创建子类) 生成继承原类的子类，不影响原类。
 *          rebase:(重定基底) 类似于redefine，但是原有的方法不会消失，而是被重命名，添加后缀 $original，
 *                  这样，就没有实现会被丢失。重定义的方法可以继续通过它们重命名过的名称调用原来的方法。
 **/
public class AddFieldTest {

    @SneakyThrows
    public static void main(String[] args) {
        DynamicType.Unloaded<Person> dynamicType = (DynamicType.Unloaded<Person>) buildDynamicTypeByRedefine03();
        dynamicType.saveIn(new File(Person.class.getResource("/").getPath()));
//        Class<Person> personClass = (Class<Person>) dynamicType.load(AddFieldTest.class.getClassLoader()).getLoaded();
    }

    /**
     * 通过subclass 实现 （通过生成子类实现）
     *    生成类回继承原有类，不影响原有类，
     * @return
     */
    public static DynamicType.Unloaded<Person> buildDynamicTypeBySubClass() {
        return new ByteBuddy(ClassFileVersion.JAVA_V8)
                .subclass(Person.class)
                .name(Person.class.getPackage().getName().concat(".").concat("PersonBySubClass"))
//                .method(isGetter().or(isSetter())).intercept(FieldAccessor.ofBeanProperty())
                .defineField("address", String.class, Visibility.PRIVATE)
                .defineMethod("getAddress", String.class, Visibility.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty())
                .defineMethod("setAddress", void.class, Visibility.PUBLIC)
                .withParameter(String.class)
                .intercept(FieldAccessor.ofBeanProperty())
                .make();
    }

    /**
     * 通过 rebase 实现
     * @return
     */
    public static DynamicType.Unloaded<Person> buildDynamicTypeByRebase() {
        return new ByteBuddy(ClassFileVersion.JAVA_V8)
                .rebase(Person.class)
                .name(Person.class.getPackage().getName().concat(".").concat("PersonByRebase"))
                .defineField("address", String.class, Visibility.PRIVATE)
                .defineMethod("setAddress", String.class, Visibility.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty())
                .make();
    }

    /**
     * 通过 Redefine 实现 （通过生成子类实现）
     *    生成类回继承原有类，不影响原有类，
     * @return
     */
    public static DynamicType.Unloaded<Person> buildDynamicTypeByRedefine() {
        return new ByteBuddy(ClassFileVersion.JAVA_V8)
                .redefine(Person.class)
                .name(Person.class.getPackage().getName().concat(".").concat("PersonByRedefine"))
                .defineField("address", String.class, Visibility.PRIVATE)
                .defineMethod("setAddress", String.class, Visibility.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty())
                .make();
    }

    /**
     * 通过 Redefine 实现 （通过生成子类实现）
     *    生成类回继承原有类，不影响原有类，
     * @return
     */
    public static DynamicType.Unloaded<?> buildDynamicTypeByRedefine02() {
        DynamicType.Builder<?> builder = new ByteBuddy(ClassFileVersion.JAVA_V8)
                .redefine(Person.class)
                .name(Person.class.getPackage().getName().concat(".").concat("PersonByRedefine02"))
                .defineField("address", String.class, Visibility.PRIVATE);
        builder = ByteBuddyUtils.createSetter(builder, "address", String.class);
        return ByteBuddyUtils.createGetter(builder, "address", String.class).make();
    }


    /**
     * 通过 Redefine 实现 （通过生成子类实现）
     *    生成类回继承原有类，不影响原有类，
     * @return
     */
    public static DynamicType.Unloaded<?> buildDynamicTypeByRedefine03() {
        String fieldName = "address";
        String setterName = "setAddress";
        return new ByteBuddy(ClassFileVersion.JAVA_V8)
                .redefine(Person.class)
                .name(Person.class.getPackage().getName().concat(".").concat("PersonByRedefine03"))
                .defineField(fieldName, String.class, Visibility.PRIVATE)
                .defineMethod(setterName, String.class, Visibility.PUBLIC)
                .intercept(FieldAccessor.ofField( fieldName ))
                .make();
    }
}
