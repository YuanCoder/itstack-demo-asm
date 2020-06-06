package org.itstack.demo.asm;

import org.objectweb.asm.ClassReader;

import java.io.IOException;

/**
 * @author Jenpin
 * @date 2020/6/6 22:13
 * @description https://github.com/MyGitBooks/asm/blob/master/docs/notes/2.2%E6%8E%A5%E5%8F%A3%E5%92%8C%E7%BB%84%E4%BB%B6.md
 *    分析类
 * */
public class TestClassPrinter {

    public static void main(String[] args) throws IOException {
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(cp, 0);
    }
}
