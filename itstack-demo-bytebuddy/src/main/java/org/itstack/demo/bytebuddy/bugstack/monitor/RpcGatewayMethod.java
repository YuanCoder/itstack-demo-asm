package org.itstack.demo.bytebuddy.bugstack.monitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jenpin
 * @date 2020/6/26 15:35
 * @description 模拟网关方法注解
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RpcGatewayMethod {

    String methodName() default "";
    String methodDesc() default "";

}