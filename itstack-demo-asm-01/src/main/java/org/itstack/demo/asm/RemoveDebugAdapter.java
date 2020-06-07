package org.itstack.demo.asm;

import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author Jenpin
 * @date 2020/6/7 12:39
 * @description 移除类成员：
 *              下面的类适配器移除了有关外部类及内部类的信息，
 *              还删除了一个源文件的名字，也就是由其编译这个类的源文件（所得到的类仍然具有全部功能，因为删除的这些元素仅用于调试目的）。
 *              这一移除操作是通过在适当的访问方法中不转发任何内容而实现的：
 **/
public class RemoveDebugAdapter extends ClassVisitor {
    public RemoveDebugAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public void visitSource(String source, String debug) {
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }
}