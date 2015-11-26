package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
@RequestMapping(value = "/buildAndDeploy")
public class BuildAndDeployController {
    @Value("${buildAndDeploy.script.gradle}")
    private String gradleScript;
    @Value("${buildAndDeploy.script.gitPull}")
    private String gitPullScript;

    private String status = "run";

    @RequestMapping(value = "/status.json")
    @ResponseBody
    public Result healthCheck(Model model) {
        return new Result(ResultCode.SUCCESS, status);
    }

    @RequestMapping(value = "/go.json")
    @ResponseBody
    public synchronized Result go() throws IOException, InterruptedException {
        if ("run".equals(status)) {
            new ProcessBuilder("bash", gitPullScript).start().waitFor();
            new ProcessBuilder("bash", gradleScript).start();
            status = "build";
        }
        return new Result(ResultCode.SUCCESS);
    }
}