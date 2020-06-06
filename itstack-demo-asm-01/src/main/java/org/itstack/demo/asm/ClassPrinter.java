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

    @Override
    public FieldVisitor visitField(int access, String name, String desc,
                                   String signature, Object value) {
        System.out.println(" " + desc + " " + name);
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(" " + name + desc);
        return null;
    }

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