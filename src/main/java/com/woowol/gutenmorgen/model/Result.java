package com.woowol.gutenmorgen.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Result {
    @Getter
    private String retCode;
    @Getter
    private Object retMsg;

    public Result(ReturnCode returnCode, Object retMsg) {
        this.retCode = returnCode.getRetCode();
        this.retMsg = retMsg;
        if (!returnCode.equals(ReturnCode.SUCCESS)) {
            this.retMsg = returnCode.getRetMsg() + " : " + this.retMsg;
        }
    }

    public Result(ReturnCode returnCode) {
        this.retCode = returnCode.getRetCode();
        this.retMsg = returnCode.getRetMsg();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ReturnCode {
        SUCCESS("0", "SUCCESS"),
        UNKNOWN_ERROR("9999", "알수없는 오류"),
        ENVIRONMENT_ERROR("0001", "현재 환경에선 지원되지 않습니다.");

        @Getter
        private final String retCode;
        @Getter
        private final String retMsg;
    }
}