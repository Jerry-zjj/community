package com.zjj.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了,要不换一个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或者评论进行回复."),
    NO_LOGIN(2003,"当前操作需要登录,请先登录."),
    SYS_ERROR(2004,"服务冒烟了,要不然你稍后再试试."),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUNT(2006,"你回复的评论不在了,要不换一个试试?");

    private String message;
    private Integer code;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code=code;
        this.message = message;
    }

}
