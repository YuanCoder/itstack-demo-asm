package org.itstack.demo.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * 用字节码增强调用外部方法
 */
public class TestMonitor extends ClassLoader {

    public static void main(String[] args) throws Exception {

        ClassReader cr = new ClassReader(MyMethod.class.getName());
        //COMPUTE_MAXS 的作用是将自动计算本地变量表最大值和操作数栈最大值的任务托付给了ASM
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ProfilingClassAdapter(cw, MyMethod.class.getSimpleName());
        //读取 class 的时候需要同时展开栈映射帧（StackMap Frame），如果我们需要使用自定义的 MethodVisitor 去修改方法中的指令时必须要指定这个参数
        cr.accept(cv, ClassReader.EXPAND_FRAMES);

        byte[] bytes = cw.toByteArray();
        outputClazz(bytes);

        Class<?> clazz = new TestMonitor().defineClass("org.itstack.demo.asm.MyMethod", bytes, 0, bytes.length);
        // 反射获取 main 方法
        Method method = clazz.getMethod("sum", int.class, int.class);
        Object obj = method.invoke(clazz.newInstance(), 6, 2);
        System.out.println("结果：" + obj);

    }

    static class ProfilingClassAdapter extends ClassVisitor {

        public ProfilingClassAdapter(final ClassVisitor cv, String innerClassName) {
            super(ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {


            if (!"sum".equals(name)) return super.visitMethod(access, name, descriptor, signature, exceptions);

            System.out.println("access：" + access);
            System.out.println("name：" + name);
            System.out.println("desc：" + descriptor);
            System.out.println("signature：" + signature);

            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);

            return new ProfilingMethodVisitor(mv, access, name, descriptor);
        }
    }

    static class ProfilingMethodVisitor extends AdviceAdapter {

        private String name;

        protected ProfilingMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(ASM5, methodVisitor, access, name, descriptor);
            this.name = name;
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            super.visitVarInsn(opcode, var);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }

        @Override
        protected void onMethodEnter() {
            // 输出方法和参数
            //入栈
            mv.visitLdcInsn(name);
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitInsn(IASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitInsn(IASTORE);
            mv.visitMethodInsn(INVOKESTATIC, "org/itstack/demo/asm/MonitorLog", "info", "(Ljava/lang/String;[I)V", false);
        }

    }

    private static void outputClazz(byte[] bytes) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = TestMonitor.class.getResource("/").getPath() + "AsmTestMonitor.class";
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
