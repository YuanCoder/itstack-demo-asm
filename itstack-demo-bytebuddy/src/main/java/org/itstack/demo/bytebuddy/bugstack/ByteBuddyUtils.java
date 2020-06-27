package org.itstack.demo.bytebuddy.bugstack;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author Jenpin
 * @date 2020/6/26 23:50
 * @description 工具类
 **/
public class ByteBuddyUtils {

    /**
     * createInstance
     *
     * @param entityClass
     * @param <E>
     * @return
     */
    public static <E> E createInstance(Class<E> entityClass) {
        Class<? extends E> loaded = new ByteBuddy()
                .subclass( entityClass )
                .method( ElementMatchers.named( "toString" ) )
                .intercept( FixedValue.value( "transformed" ) )
                .make()
                // we use our internal helper to get a class loading strategy suitable for the JDK used
                .load( entityClass.getClassLoader() )
                .getLoaded();
        try {
            return loaded.newInstance();
        } catch (Exception e) {
            throw new RuntimeException( "Unable to create new instance of " + entityClass.getSimpleName(), e );
        }
    }

    /**
     * 生成set方法
     *
     * @param builder
     * @param propertyName
     * @param type
     * @return
     */
    public static DynamicType.Builder<?> createSetter(DynamicType.Builder<?> builder, String propertyName, Class<?> type) {
        return builder
                .defineMethod(buildSetterName(propertyName), Void.TYPE, Visibility.PUBLIC)
                .withParameters(type)
                .intercept(FieldAccessor.ofBeanProperty());
    }

    private static String buildSetterName(String fieldName) {
        return cap("set", fieldName);
    }

    private static String cap(String prefix, String name) {
        final int plen = prefix.length();
        StringBuilder sb = new StringBuilder(plen + name.length());
        sb.append(prefix);
        sb.append(name);
        sb.setCharAt(plen, Character.toUpperCase(name.charAt(0)));
        return sb.toString();
    }

    /**
     * 生成get方法
     *
     * @param builder
     * @param propertyName
     * @param type
     * @return
     */
    public static DynamicType.Builder<?> createGetter(DynamicType.Builder<?> builder, String propertyName, Class<?> type) {
        return builder
                .defineMethod(buildGetterName(propertyName), type, Visibility.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty());
    }

    private static String buildGetterName(String fieldName) {
        return cap("get", fieldName);
    }

}
