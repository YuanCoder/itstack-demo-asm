package org.itstack.demo.asm.treeapi;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

/**
 * @author Jenpin
 * @date 2020/6/7 16:33
 * @description TODO
 **/
public class RemoveMethodTransformer extends ClassTransformer {
    private String methodName;
    private String methodDesc;

    public RemoveMethodTransformer(ClassTransformer ct, String methodName, String methodDesc) {
        super(ct);
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public void transform(ClassNode cn) {
        Iterator<MethodNode> i = cn.methods.iterator();
        while (i.hasNext()) {
            MethodNode mn = i.next();
            if (methodName.equals(mn.name) && methodDesc.equals(mn.desc)) {
                i.remove();
            }
        }
        super.transform(cn);
    }
}