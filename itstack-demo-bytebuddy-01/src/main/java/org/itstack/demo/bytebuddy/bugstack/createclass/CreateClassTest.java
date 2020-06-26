package org.itstack.demo.bytebuddy.bugstack.createclass;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

import java.io.File;


/**
 * @author Jenpin
 * @date 2020/6/25 15:38
 * @description 创建类
 **/
public class CreateClassTest {

    public static void main(String[] args) {
        createClass();
        createClassWithNamingStrategyAbstractBase();
    }

    public static void createClass() {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("com.jenpin.type.HelloWorld")
                .make();
        // 输出类字节码
        String pathName = CreateClassTest.class.getResource("/").getPath() +"ByteBuddyHelloWorld.class";
        ByteCodeUtils.outputClazz(dynamicType.getBytes(), pathName);
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

}
