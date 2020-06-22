package org.itstack.demo.javassist;

import javassist.*;

import java.lang.reflect.Method;


public class GenerateClazzMethod {

    public static void main(String[] args) throws Exception{

        //1.创建 ClassPool，它是一个基于HashMap实现的 CtClass 对象容器。
        ClassPool pool = ClassPool.getDefault();

        //创建类 classname：创建类路径和名称
        //2.使用 CtClass，创建我们的类信息，也就是类的路径和名称。
        CtClass ctClass = pool.makeClass("org.itstack.demo.javassist.HelloWorld");

        // 添加方法
        //3.接下来就是给类添加方法。包括；方法的属性、类型、名称、入参、出参和方法体的内容。
        CtMethod mainMethod = new CtMethod(CtClass.voidType, "main", new CtClass[]{pool.get(String[].class.getName())}, ctClass);
        mainMethod.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        mainMethod.setBody("{System.out.println(\"hi javassist! Testing\");}");

        ctClass.addMethod(mainMethod);

        // 创建无参数构造方法
        //4.在方法创建好后还需要创建一个空的构造函数，每一个类都会在编译后生成这样一个构造函数。
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);

        // 输出类的内容
        //5.当方法创建完成后，我们使用 ctClass.writeFile() 进行输出方法的内容信息。也就可以看到通过我们使用 Javassist 生成类的样子。
        String pathName = GenerateClazzMethod.class.getResource("/").getPath();
        System.out.println("pathName:" + pathName);
        ctClass.writeFile(pathName);

        // 测试调用
        Class clazz = ctClass.toClass();
        Object obj = clazz.newInstance();

        Method main = clazz.getDeclaredMethod("main", String[].class);
        main.invoke(obj, (Object) new String[1]);
    }

}
