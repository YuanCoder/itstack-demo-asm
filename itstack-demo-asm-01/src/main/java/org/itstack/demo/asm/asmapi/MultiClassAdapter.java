package org.itstack.demo.asm.asmapi;

import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author Jenpin
 * @date 2020/6/7 15:03
 * @description MultiClassAdapter，将接收到的所有方法调用同时转发给几个 ClassVisitor
 **/
public class MultiClassAdapter extends ClassVisitor {
    protected ClassVisitor[] cvs;

    public MultiClassAdapter(ClassVisitor[] cvs) {
        super(ASM4);
        this.cvs = cvs;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        for (ClassVisitor cv : cvs) {
            cv.visit(version, access, name, signature, superName, interfaces);
        }
    }
}