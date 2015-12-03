package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.GutenmorgenApplication;
import com.woowol.gutenmorgen.bo.EnvironmentBO;
import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping(value = "/buildAndDeploy")
public class BuildAndDeployController {
    @Autowired
    private EnvironmentBO environmentBO;
    @Value("${buildAndDeploy.script}")
    private String script;

    private String status = "run";

    @RequestMapping(value = "/status.json")
    @ResponseBody
    public Result healthCheck() {
        return new Result(ReturnCode.SUCCESS, status);
    }

    @RequestMapping(value = "/go.json")
    @ResponseBody
    public synchronized Result go() throws IOException, InterruptedException, ResultException {
        environmentBO.checkNotLocal();

        if ("run".equals(status)) {
            new ProcessBuilder("bash", script).start();
            status = "build";
            GutenmorgenApplication.ctx.close();
        }

        return new Result(ReturnCode.SUCCESS);
    }
}