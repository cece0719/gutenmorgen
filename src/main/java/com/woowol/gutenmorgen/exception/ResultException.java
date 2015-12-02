package com.woowol.gutenmorgen.exception;

import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ReturnCode;

public class ResultException extends Exception {
    private static final long serialVersionUID = 1L;

    private Result result;

    public ResultException(Result result) {
        this.result = result;
    }

    public ResultException(ReturnCode resultCode) {
        this.result = new Result(resultCode);
    }

    public ResultException(ReturnCode returnCode, Object rtnMsg) {
        this.result = new Result(returnCode, rtnMsg);
    }

    public Result getResult() {
        return result;
    }
}