package org.itstack.demo.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jenpin
 * @date 2020/6/14 15:21
 * @description 用ASM生成Cat类
 **/
public class TestCatAsm extends ClassLoader {


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        byte[]  bytes = generate();
        outputClazz(bytes);
        TestCatAsm testCatAsm = new TestCatAsm();
        Class<?> clazz = testCatAsm.defineClass("org.itstack.demo.asm.CatASM", bytes, 0, bytes.length);
        // 反射获取 main 方法
        Method method1 = clazz.getMethod("getName");
        Object obj = method1.invoke(clazz.newInstance());
        System.out.println("method1:"+obj);
    }

    private static byte[] generate() {
        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;

        // 定义对象头；版本号、修饰符、全类名、签名、父类、实现的接口
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "org/itstack/demo/asm/CatASM", null, "java/lang/Object", null);

        //字段
        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC, "name", "Ljava/lang/String;",null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "address", "Ljava/lang/String;",null, "china");
            fv.visitEnd();
        }

        //无参构造方法
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();

            //标签以及行号 (貌似可以胜率不影响，待验证)
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(16, l0);

            //初始化方法
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

            //标签以及行号(貌似可以胜率不影响，待验证)
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(17, l1);
            mv.visitInsn(Opcodes.RETURN);

            //标签以及行号(貌似可以胜率不影响，待验证)
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lorg/itstack/demo/asm/CatASM;",
                    null, l0, l2, 0);

            mv.visitMaxs(1,1);
            mv.visitEnd();
        }

        //有参构造方法
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();

            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(19, l0);

            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/Object", "<init>", "()V", false);

            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(20, l1);

            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitFieldInsn(Opcodes.PUTFIELD, "org/itstack/demo/asm/CatASM","name", "Ljava/lang/String;");

            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(21, l2);
            mv.visitInsn(Opcodes.RETURN);

            Label l3 = new Label();
            mv.visitLabel(l3);

            mv.visitLocalVariable("this", "Lorg/itstack/demo/asm/CatASM;", null, l0, l3, 0);
            mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l3, 1);

            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        //getName 方法
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
            mv.visitCode();

            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(28, l0);
            //加载this 到操作数栈
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, "org/itstack/demo/asm/CatASM", "name", "Ljava/lang/String;");
            mv.visitInsn(Opcodes.ARETURN);

            Label l1 = new Label();
            mv.visitLabel(l1);

            mv.visitLocalVariable("this", "Lorg/itstack/demo/asm/CatASM;", null, l0, l1, 0);
            mv.visitMaxs(1,1);
            mv.visitEnd();
        }

        //getAddress 方法
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "getAddress", "()Ljava/lang/String;", null, null);
            mv.visitCode();
            mv.visitLdcInsn("china");
            mv.visitInsn(Opcodes.ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    private static void outputClazz(byte[] bytes) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = TestCatAsm.class.getResource("/").getPath() + "CatASM.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("ASM类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
