package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping(value = "/buildAndDeploy")
public class BuildAndDeployController {
    @Value("${env}")
    private String env;

    @Value("${buildAndDeploy.script}")
    private String script;

    private String status = "run";

    @RequestMapping(value = "/status.json")
    @ResponseBody
    public Result healthCheck(Model model) {
        return new Result(ReturnCode.SUCCESS, status);
    }

    @RequestMapping(value = "/go.json")
    @ResponseBody
    public synchronized Result go() throws IOException, InterruptedException {
        if ("local".equals(env)) {
            return new Result(ReturnCode.ENVIRONMENT_ERROR);
        }
        if ("run".equals(status)) {
            new ProcessBuilder("bash", script).start();
            status = "build";
        }
        return new Result(ReturnCode.SUCCESS);
    }
}