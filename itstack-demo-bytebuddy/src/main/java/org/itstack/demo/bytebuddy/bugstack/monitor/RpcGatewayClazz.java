package org.itstack.demo.bytebuddy.bugstack.monitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jenpin
 * @date 2020/6/26 15:31
 * @description 模拟网关类注解
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcGatewayClazz {

    String clazzDesc() default "";
    String alias() default "";
    long timeOut() default 350;

}
