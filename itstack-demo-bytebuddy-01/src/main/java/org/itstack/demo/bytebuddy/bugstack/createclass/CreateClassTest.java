package org.itstack.demo.bytebuddy.bugstack.createclass;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.File;
import java.lang.reflect.Modifier;


/**
 * @author Jenpin
 * @date 2020/6/25 15:38
 * @description 创建类:创建类和定义类名，如果不写类名会自动生成要给类名。
 **/
public class CreateClassTest {

    public static void main(String[] args) {
        createClass01();
        createClass02();
        createClassWithNamingStrategyAbstractBase();
    }

    public static void createClass01() {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("com.jenpin.type.HelloWorld")
                //定义方法；名称、返回类型、属性public static
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                //定义参数；参数类型、参数名称
                .withParameter(String[].class, "args")
                //拦截设置返回值，但此时还能满足我们的要求
                .intercept(FixedValue.value("Hello World!"))
                .make();
        // 输出类字节码
        String pathName = CreateClassTest.class.getResource("/").getPath() +"ByteBuddyHelloWorld01.class";
        ByteCodeUtils.outputClazz(dynamicType.getBytes(), pathName);
    }

    /**
     * 使用到委托: MethodDelegation.to(Hi.class) 真正去执行输出的是另外的函数方法。
     *   MethodDelegation，需要是 public 类
     *   被委托的方法与需要与原方法有着一样的入参、出参、方法名，否则不能映射上
     */
    @SneakyThrows
    public static void createClass02() {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("com.jenpin.type.HelloWorld")
                //定义方法；名称、返回类型、属性public static
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                //定义参数；参数类型、参数名称
                .withParameter(String[].class, "args")
                //为了能让我们使用字节码编程创建的方法去输出一段 Hello World ，那么这里需要使用到委托
                .intercept(MethodDelegation.to(Hi.class))
                .make();
        // 输出类字节码
        String pathName = CreateClassTest.class.getResource("/").getPath() +"ByteBuddyHelloWorld02.class";
        ByteCodeUtils.outputClazz(dynamicType.getBytes(), pathName);

        //为了可以让整个方法运行起来，我们需要添加字节码加载和反射调用的代码块
        //加载类
        Class<?> clazz = dynamicType.load(CreateClassTest.class.getClassLoader()).getLoaded();
        // 反射调用
        clazz.getMethod("main", String[].class).invoke(clazz.newInstance(), (Object) new String[1]);
    }

    @SneakyThrows
    public static void createClassWithNamingStrategyAbstractBase() {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                        return "i.love.ByteBuddy." + superClass.getSimpleName();
                    }
                })
                .subclass(Object.class)
                .make();
        File file = new File(CreateClassTest.class.getResource("/").getPath());
        dynamicType.saveIn(file);
    }

    public static class Hi {
        public static void main(String[] args) {
            System.out.println("helloWorld, byte buddy!");
        }
    }

}
