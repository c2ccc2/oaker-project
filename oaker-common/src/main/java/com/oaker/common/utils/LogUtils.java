package com.oaker.common.utils;

/**
 * 处理并记录日志文件
 *
 * @author `` 须尽欢 _____
 */
public class LogUtils {
    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
