package com.woowol.gutenmorgen.exception;

import com.woowol.gutenmorgen.model.Result;

public class ResultException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private Result result;
	
	ResultException(Result result) {
		this.result = result;
	}
	public Result getResult() {
		return result;
	}
}