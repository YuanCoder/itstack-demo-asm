package org.itstack.demo.bytebuddy.bugstack.monitor;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;

/**
 * @author Jenpin
 * @date 2020/6/25 16:02
 * @description 类型重定义:对一个已有的类添加属性和方法，或者删除已经存在的方法
 **/
public class MonitorCatTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //获取动态类型
        DynamicType.Unloaded<?> dynamicType = buildDynamicType();
        // 反射调用
        Class<?> clazz = dynamicType.load(MonitorCatTest.class.getClassLoader()).getLoaded();
        clazz.getMethod("queryCatName", Long.class, String.class)
                .invoke(clazz.newInstance(), 100L, "sdfasfasdfasdf");
    }

    /**
     * 构建 dynamicType
     * @return
     */
    public static DynamicType.Unloaded<?> buildDynamicType() {
        return new ByteBuddy()
                //定义需要被加载的类和方法
                .subclass(Cat.class)
                .method(ElementMatchers.named("queryCatName"))
                //委托；MethodDelegation.to(MonitorQueryCatName.class)，最终所有的监控操作都会被 MonitorQueryCatName.class 类中的方法进行处理
                .intercept(MethodDelegation.to(MonitorQueryCatName.class))
                .make();
    }

    @Slf4j
    public static class MonitorQueryCatName {

        /**
         * 注解说明:
         *          @RuntimeType 定义运行时的目标方法
         *          @SuperCall 用于调用父类版本的方法
         *
         * @param callable
         * @return
         * @throws Exception
         */
        @RuntimeType
        public static Object intercept01(@SuperCall Callable<?> callable) throws Exception {
            Stopwatch stopwatch = Stopwatch.createStarted();
            try {
                return callable.call();
            } finally {
                log.info("方法耗时：" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
            }
        }

        /**
         * 注解说明： @SuperCall 用于调用父类版本的方法
         *           @RuntimeType 定义运行时的目标方法
         *           @Origin 用于拦截原有方法，这样就可以获取到方法中的相关信息
         *
         * @param method
         * @param callable
         * @return
         * @throws Exception
         */
        @RuntimeType
        public static Object intercept02(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
            Stopwatch stopwatch = Stopwatch.createStarted();
            Object result = null;
            try {
                result = callable.call();
                return result;
            } finally {
                log.info("方法耗时：" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

                //方法
                log.info("方法所属类:" + method.getDeclaringClass());
                log.info("方法名称:" + method.getName());
                List<Pair<String, String>> pairs = Arrays.stream(method.getParameterTypes())
                        .map(e -> Pair.of(e.getTypeName(), e.getName()))
                        .collect(Collectors.toList());
                //入参
                log.info("入参个数:" + method.getParameterCount());
                log.info("入参类型:" + StringUtils.join(pairs, ","));

                //出参
                log.info("出参类型:" + method.getReturnType().getName());
                log.info("出参结果:" + result);
            }
        }


        /**
         * 注解说明： @SuperCall 用于调用父类版本的方法
         *           @RuntimeType 定义运行时的目标方法
         *           @Origin 用于拦截原有方法，这样就可以获取到方法中的相关信息
         *           @AllArguments 获取全部参数
         *           @Argumen 获取指定的参数
         *
         * @param method 获取方法信息
         * @param args 全部参数
         * @param arg 获取指定的参数
         * @param callable
         * @return
         * @throws Exception
         */
        @RuntimeType
        public static Object intercept03(@Origin Method method, @AllArguments Object[] args, @Argument(0) Object arg, @SuperCall Callable<?> callable) throws Exception {
            Stopwatch stopwatch = Stopwatch.createStarted();
            Object result = null;
            try {
                result = callable.call();
                return result;
            } finally {
                log.info("方法耗时：" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

                //方法
                log.info("方法所属类:" + method.getDeclaringClass());
                log.info("方法名称:" + method.getName());
                List<Pair<String, String>> pairs = Arrays.stream(method.getParameterTypes())
                        .map(e -> Pair.of(e.getTypeName(), e.getName()))
                        .collect(Collectors.toList());
                //入参
                log.info("入参个数:" + method.getParameterCount());
                log.info("入参类型:" + StringUtils.join(pairs, ","));
                log.info("全部入参内容：" + StringUtils.join(Arrays.stream(args).collect(Collectors.toList()), ", "));
                log.info("指定入参内容：" + arg);

                //出参
                log.info("出参类型:" + method.getReturnType().getName());
                log.info("出参结果:" + result);
            }
        }

    }
}
