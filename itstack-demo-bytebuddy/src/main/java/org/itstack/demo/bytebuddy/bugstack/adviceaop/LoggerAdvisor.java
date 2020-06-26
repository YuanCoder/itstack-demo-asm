package org.itstack.demo.bytebuddy.bugstack.adviceaop;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Jenpin
 * @date 2020/6/26 18:23
 * @description TODO
 **/
public class LoggerAdvisor {

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        if (method.getAnnotation(Log.class) != null) {
            System.out.println("Enter " + method.getName() + " with arguments: " + Arrays.toString(arguments));
        }
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments, @Advice.Return Object ret) {
        if (method.getAnnotation(Log.class) != null) {
            System.out.println("Exit " + method.getName() + " with arguments: " + Arrays.toString(arguments) + " return: " + ret);
        }
    }
}
