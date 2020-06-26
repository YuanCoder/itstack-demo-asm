package org.itstack.demo.bytebuddy.bugstack.monitor;

/**
 * @author Jenpin
 * @date 2020/6/26 15:28
 * @description TODO
 **/
public abstract class Repository<T> {

    public abstract T queryData(int id);

}
