package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EnvironmentBO {
    @Value("${env}")
    String env;

    public void checkNotLocal() throws ResultException {
        if ("local".equals(env)) {
            throw new ResultException(Result.ReturnCode.ENVIRONMENT_ERROR);
        }
    }
}