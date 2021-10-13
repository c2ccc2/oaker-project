package com.oaker.common.exception.user;

/**
 * 验证码失效异常类
 *
 * @author `` 须尽欢 _____
 */
public class CaptchaExpireException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("user.jcaptcha.expire", null);
    }
}
