package com.oaker.common.exception.file;

import com.oaker.common.exception.base.BaseException;

/**
 * 文件信息异常类
 *
 * @author `` 须尽欢 _____
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
