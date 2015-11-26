package com.woowol.gutenmorgen.model;

import lombok.Getter;

public class Result {
    @Getter
    private String retCode;
    @Getter
    private Object retMsg;

    public Result(ResultCode resultCode) {
        this.retCode = resultCode.getCode();
        this.retMsg = resultCode.getRetMsg();
    }

    public Result(ResultCode resultCode, Object retMsg) {
        this.retCode = resultCode.getCode();
        this.retMsg = retMsg;
        if (!resultCode.equals(ResultCode.SUCCESS)) {
            this.retMsg = resultCode.getRetMsg() + " : " + this.retMsg;
        }
    }


    public enum ResultCode {
        SUCCESS("0", "SUCCESS"),
        UNKNOWN_ERROR("9999", "알수없는 오류");

        private String retCode;
        private String retMsg;

        ResultCode(String retCode, String retMsg) {
            this.retCode = retCode;
            this.retMsg = retMsg;
        }

        public String getCode() {
            return retCode;
        }

        public String getRetCode() {
            return retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }
    }
}