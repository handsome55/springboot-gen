package com.example.springbootgen.utils;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 11:24
 */
public class IdGen {

    public static Long nextId(){
        SnowFlakeIdWorker idWorker = new SnowFlakeIdWorker(1, 3);
        return idWorker.nextId();
    }
}
