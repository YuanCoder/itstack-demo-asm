package org.itstack.demo.asm.asmapi;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author Jenpin
 * @date 2020/6/7 12:43
 * @description
 *          要移除字段或方法，不得转发方法调用，并向调用者返回 null。例如，下面的类适配器移除了一个方法，
 *          该方法由其名字及描述符指明（仅使用名字不足以标识一个方法，因为一个类中可能包含若干个具有不同参数的同名方法）
 **/
public class RemoveMethodAdapter extends ClassVisitor {
    private String mName;
    private String mDesc;

    public RemoveMethodAdapter(ClassVisitor cv, String mName, String mDesc) {
        super(ASM4, cv);
        this.mName = mName;
        this.mDesc = mDesc;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals(mName) && desc.equals(mDesc)) {
            // 不要委托至下一个访问器 -> 这样将移除该方法
            return null;
        }
        return cv.visitMethod(access, name, desc, signature, exceptions);
    }
}
