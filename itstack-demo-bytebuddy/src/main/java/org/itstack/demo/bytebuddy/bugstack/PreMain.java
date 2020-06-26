package org.itstack.demo.bytebuddy.bugstack;

import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;

/**
 * @author Jenpin
 * @date 2020/6/26 20:49
 * @description TODO
 **/
@Slf4j
public class PreMain {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("premain execute");
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
        log.info("premain execute");
    }

}
