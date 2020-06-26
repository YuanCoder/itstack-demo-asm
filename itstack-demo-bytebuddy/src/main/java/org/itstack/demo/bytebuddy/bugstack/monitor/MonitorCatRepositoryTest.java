package org.itstack.demo.bytebuddy.bugstack.monitor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author Jenpin
 * @date 2020/6/26 15:39
 * @description 使用委托实现抽象类方法并注入自定义注解信息
 **/
@Slf4j
public class MonitorCatRepositoryTest {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("----构建动态类型（创建方法主体）----");
        DynamicType.Unloaded<?> dynamicType = buildDynamicType();

        log.info("----输出类信息到目标文件夹下----");
        String path = MonitorCatRepositoryTest.class.getResource("/").getPath();

        log.info("生成文件路径:" + path);
        dynamicType.saveIn(new File(path));

        log.info("----从目标文件夹下加载类信息----");
        //使用Class.forName，进行加载类信息 可以直接调用方法,
        // 也可以unloadedType.load(XXX.class.getClassLoader()) 的方式进行直接处理字节码 需要通过反射进行处理
        Class<?> repositoryClass = Class.forName("org.itstack.demo.bytebuddy.bugstack.monitor.CatRepository");

        log.info("----打印类信息----");
        printClassInfo(repositoryClass);
        Repository<String> catRepository = (Repository<String>) repositoryClass.newInstance();
        log.info(catRepository.queryData(11111111));
    }

    /**
     * 这部分基本是Byte-buddy的模板方法，通过核心API；subclass、name、method、intercept、annotateMethod、annotateType 的使用构建方法。
     *
     * @return
     */
    public static DynamicType.Unloaded<?> buildDynamicType() {
        return new ByteBuddy()
                //首先是定义复杂类型的自定义注解，设定为本方法的父类，这部分内容也就是抽象类。
                // Repository<T>，通过 TypeDescription.Generic.Builder.parameterizedType(Repository.class, String.class).build() 来构建。
                .subclass(
                        //创建复杂类型的泛型注解
                        TypeDescription.Generic.Builder.parameterizedType(Repository.class, String.class).build())
                // 添加类信息包括地址(设定类名称,concat 函数是字符串的连接符，替换 + 号)
                .name(Repository.class.getPackage()
                        .getName().concat(".")
                        .concat("CatRepository"))
                // 匹配处理的方法
                .method(ElementMatchers.named("queryData"))
                // 交给委托函数
                .intercept(MethodDelegation.to(CatRepositoryInterceptor.class))
                //定义方法注解，通过 define 设定值（可以多次使用）
                .annotateMethod(AnnotationDescription
                        .Builder.ofType(RpcGatewayMethod.class)
                        .define("methodName", "queryData")
                        .define("methodDesc","查询数据")
                        .build())
                //定义类注解，通过 define 设定值（可以多次使用）
                .annotateType(AnnotationDescription
                        .Builder.ofType(RpcGatewayClazz.class)
                        .define("clazzDesc", "查询数据信息.")
                        .define("alias","dataApi")
                        .define("timeOut", 350L).build())
                .make();

    }

    public static class CatRepositoryInterceptor {

        @RuntimeType
        public static String intercept(@Origin Method method, @AllArguments Object[] arguments) {
            return "委托方法：CatRepositoryInterceptor.intercept，入参" + arguments[0];
        }

    }

    /**
     * 打印类信息
     * @param repositoryClass
     */
    @SneakyThrows
    public static void printClassInfo(Class<?> repositoryClass) {
        // 获取类注解
        RpcGatewayClazz rpcGatewayClazz = repositoryClass.getAnnotation(RpcGatewayClazz.class);
        log.info("RpcGatewayClazz.clazzDesc：" + rpcGatewayClazz.clazzDesc());
        log.info("RpcGatewayClazz.alias：" + rpcGatewayClazz.alias());
        log.info("RpcGatewayClazz.timeOut：" + rpcGatewayClazz.timeOut());

        // 获取方法注解
        RpcGatewayMethod rpcGatewayMethod = repositoryClass.getMethod("queryData", int.class).getAnnotation(RpcGatewayMethod.class);
        log.info("RpcGatewayMethod.methodName：" + rpcGatewayMethod.methodName());
        log.info("RpcGatewayMethod.methodDesc：" + rpcGatewayMethod.methodDesc());
    }
}
