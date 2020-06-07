package org.itstack.demo.asm.treeapi;


import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import static jdk.internal.org.objectweb.asm.Opcodes.*;


/**
 * @author Jenpin
 * @date 2020/6/7 16:27
 * @description 生成类
 **/
public class TreeWrite {

    public void generate() {
        ClassNode cn = new ClassNode();
        cn.version = V1_5;
        cn.access = ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE;
        cn.name = "pkg/Comparable"; cn.superName = "java/lang/Object"; cn.interfaces.add("pkg/Mesurable");
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, new Integer(-1)));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null, new Integer(0)));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I", null, new Integer(1)));
        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null, null));
    }
}
