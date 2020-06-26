package org.itstack.demo.bytebuddy.bugstack;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author Jenpin
 * @date 2020/6/26 9:54
 * @description 生成helloworld
 **/
@Slf4j
public class HelloWorldTest {

    @lombok.SneakyThrows
    public static void main(String[] args) {
        String sb = new ByteBuddy()
                .subclass(Object.class)
                //通过 method(named("toString"))，找到 toString 方法
                .method(named("toString"))
                //通过拦截 intercept，设定此方法的返回值
                .intercept(FixedValue.value("Hello World,Jenpin"))
                .make()
                //加载生成后的 Class 和执行，以及调用方法 toString()
                .load(HelloWorldTest.class.getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();
      log.info(sb);
    }

}
