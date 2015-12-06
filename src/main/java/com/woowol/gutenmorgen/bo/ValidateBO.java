package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
@Service
public class ValidateBO {
    @Autowired
    Environment env;

    public void checkNotLocal() throws ResultException {
        for (String profile : env.getActiveProfiles()) {
            if("local".equals(profile)) {
                throw new ResultException(Result.ReturnCode.ENVIRONMENT_ERROR);
            }
        }
    }

    public void checkTimeRegex(String timeRegex) throws ResultException {
        try {
            (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE", new Locale("ko", "KR")).format(new Date())).matches(timeRegex);
        } catch(Exception e) {
            throw new ResultException(Result.ReturnCode.TIMEREGEX_ERROR);
        }
    }
}