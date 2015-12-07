package com.woowol.gutenmorgen.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Result {
    @Getter
    private String rtnCode;
    @Getter
    private Object rtnMsg;

    public Result(ReturnCode returnCode, Object rtnMsg) {
        this.rtnCode = returnCode.getRtnCode();
        this.rtnMsg = rtnMsg;
        if (!returnCode.equals(ReturnCode.SUCCESS)) {
            this.rtnMsg = returnCode.getRtnMsg() + " : " + this.rtnMsg;
        }
    }

    public Result(ReturnCode returnCode) {
        this.rtnCode = returnCode.getRtnCode();
        this.rtnMsg = returnCode.getRtnMsg();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum ReturnCode {
        SUCCESS("0", "SUCCESS"),
        UNKNOWN_ERROR("9999", "알수없는 오류"),
        ENVIRONMENT_ERROR("1", "현재 환경에선 지원되지 않습니다"),
        TIMEREGEX_ERROR("2", "잘못된 시간 정규표현식 입니다"),
        PARAMETER_ERROR("3", "파라미터 오류")
        ;

        @Getter
        private final String rtnCode;
        @Getter
        private final String rtnMsg;
    }
}