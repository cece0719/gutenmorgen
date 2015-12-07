package com.woowol.gutenmorgen.util;

import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class Validate {
    private static Environment env;

    @Autowired
    public Validate(Environment env) {
        Validate.env = env;
    }

    public static void checkNotLocal() throws ResultException {
        for (String profile : env.getActiveProfiles()) {
            if("local".equals(profile)) {
                throw new ResultException(Result.ReturnCode.ENVIRONMENT_ERROR);
            }
        }
    }

    public static void checkTimeRegex(String timeRegex) throws ResultException {
        try {
            (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE", new Locale("ko", "KR")).format(new Date())).matches(timeRegex);
        } catch(Exception e) {
            throw new ResultException(Result.ReturnCode.TIMEREGEX_ERROR);
        }
    }
}