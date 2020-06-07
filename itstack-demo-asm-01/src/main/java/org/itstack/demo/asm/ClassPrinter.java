package org.itstack.demo.asm;

import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author Jenpin
 * @date 2020/6/6 22:08
 * @description https://github.com/MyGitBooks/asm/blob/master/docs/notes/2.2%E6%8E%A5%E5%8F%A3%E5%92%8C%E7%BB%84%E4%BB%B6.md
 *             分析类:在分析一个已经存在的类时，惟一必需的组件是 ClassReader 组件
 **/
public class ClassPrinter extends ClassVisitor {

    public ClassPrinter() {
        super(ASM4);
    }

    /**
     *
     * @param version java版本号
     * @param access  Java 修饰符(private public protect、final 和 static 等等)
     * @param name    以内部形式规定了类的名字（内部名、类名）
     * @param signature  对应于泛型（不使用泛型时为null）
     * @param superName  内部形式超类
     * @param interfaces  被扩展的接口，这些接口由其内部名指定。
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + " {");
    }

    @Override
    public void visitSource(String source, String debug) {
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    @Override
    public void visitAttribute(Attribute attr) {
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }

    /**
     *
     * @param access Java 修饰符(private public protect、final 和 static 等等)
     * @param name   字段的名字
     * @param desc   字段的类型
     * @param signature   泛型（不使用泛型时为null）
     * @param value  字段的常量值：：这个参数必须仅 用于真正的常量字段，也就是 final static 字段。对于其他字段，它必须为 null
     * @return
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println(" " + desc + " " + name);
        return null;
    }

    /**
     *
     * @param access Java 修饰符(private public protect、final 和 static 等等)
     * @param name 方法名
     * @param desc 方法描述符
     * @param signature 泛型（不使用泛型时为null）
     * @param exceptions 该方法抛出的异常
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(" " + name + desc);
        return null;
    }

    /**
     * 为了通知 ：这个类已经结束
     */
    @Override
    public void visitEnd() {
        System.out.println("}");
    }

    /**
     * 分析类
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(cp, 0);
    }
}