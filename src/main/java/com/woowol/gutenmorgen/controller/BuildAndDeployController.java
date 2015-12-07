package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.GutenmorgenApplication;
import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ReturnCode;
import com.woowol.gutenmorgen.util.Validate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/buildAndDeploy")
public class BuildAndDeployController {
    @Value("${buildAndDeploy.script}")
    private String script;

    private String status = "run";

    @RequestMapping("/status.json")
    @ResponseBody
    public Result healthCheck() {
        return new Result(ReturnCode.SUCCESS, status);
    }

    @RequestMapping("/shutdown")
    @ResponseBody
    public String shutdown(HttpServletRequest request) {
        GutenmorgenApplication.ctx.close();
        log.info(request.getRemoteAddr());
        return "OK";
    }

    @RequestMapping("/go.json")
    @ResponseBody
    public synchronized Result go() throws IOException, InterruptedException {
        Validate.checkNotLocal();

        if ("run".equals(status)) {
            new ProcessBuilder("bash", script).start();
            status = "build";
        }

        return new Result(ReturnCode.SUCCESS);

    }
}