package org.itstack.demo.asm;

import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Jenpin
 * @date 2020/6/6 22:36
 * @description 生成类

 *
 **/
public class ClassWrite {

    /** 实现
     * @return*/
    public static byte[] write() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object", new String[]{"pkg/Mesurable"});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     *
     *    需求生产以下类：
     *     package pkg;
     *     public interface Comparable extends Mesurable {
     *          int LESS = -1;
     *          int EQUAL = 0;
     *          int GREATER = 1;
     *          int compareTo(Object o);
     *     }
     */


    public static void main(String[] args) {
        byte[] bytes = write();
        outputClazz(bytes);
    }

    /**
     * 输出
     * @param bytes
     */
    private static void outputClazz(byte[] bytes) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = HelloWorld.class.getResource("/").getPath() + "Comparable.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("ASM类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
