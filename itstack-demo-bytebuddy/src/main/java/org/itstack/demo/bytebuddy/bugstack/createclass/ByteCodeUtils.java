package org.itstack.demo.bytebuddy.bugstack.createclass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Jenpin
 * @date 2020/6/26 10:33
 * @description 输出字节码生成clazz: Byte buddy中默认提供了一个 dynamicType.saveIn() 方法
 **/
public class ByteCodeUtils {

    public static void outputClazz(byte[] bytes, String pathName) {
        FileOutputStream out = null;
        try {
//            String pathName = ApiTest.class.getResource("/").getPath() + "ByteBuddyHelloWorld.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("类输出路径：" + pathName);
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
