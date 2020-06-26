package org.itstack.demo.bytebuddy.bugstack.monitor;

import lombok.SneakyThrows;

import java.util.Random;

/**
 * @author Jenpin
 * @date 2020/6/25 16:05
 * @description 目标:当queryCatName执行时监控方法的各项信息；执行耗时、出入参信息等
 **/
public class Cat {

    @SneakyThrows
    public String queryCatName(Long id) {
        Thread.sleep(new Random().nextInt(500));
        return "this cat name is Tom, id:"+id;
    }
}
