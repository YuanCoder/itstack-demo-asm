package org.itstack.demo.bytebuddy.bugstack;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

/**
 * @author Jenpin
 * @date 2020/6/25 16:59
 * @description 内置类加载策略:
 *       WRAPPER：创建一个新的Wrapping类加载器
 *       CHILD_FIRST：类似上面，但是子加载器优先负责加载目标类
 *       INJECTION：利用反射机制注入动态类型
 **/
public class ClassLoadingStrategyTest {

    public void classLoadingStrategy() {
        Class<?> type = new ByteBuddy()
                .subclass(Object.class)
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }
}
