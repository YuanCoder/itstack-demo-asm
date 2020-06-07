package org.itstack.demo.asm.treeapi;

import jdk.internal.org.objectweb.asm.tree.ClassNode;

/**
 * @author Jenpin
 * @date 2020/6/7 16:34
 * @description 添加和删除类就是在 ClassNode 对象的 fields 或 methods 列表中添加或删除元素
 **/
public class ClassTransformer {
    protected ClassTransformer ct;

    public ClassTransformer(ClassTransformer ct) {
        this.ct = ct;
    }

    public void transform(ClassNode cn) {
        if (ct != null) {
            ct.transform(cn);
        }
    }
}
