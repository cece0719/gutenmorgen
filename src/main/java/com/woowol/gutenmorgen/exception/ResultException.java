package com.woowol.gutenmorgen.exception;

import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ReturnCode;

public class ResultException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Result result;

    public ResultException(ReturnCode resultCode) {
        this.result = new Result(resultCode);
    }

    public ResultException(ReturnCode returnCode, Object rtnMsg) {
        this.result = new Result(returnCode, rtnMsg);
    }

    public ResultException(Exception e) {
        super(e);
        if (e instanceof ResultException) {
            this.result = ((ResultException) e).getResult();
        } else {
            this.result = new Result(ReturnCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public String getMessage() {
        return "[" + result.getRtnCode() + "]" + result.getRtnMsg() + "-" + super.getMessage();
    }

    public Result getResult() {
        return result;
    }
}