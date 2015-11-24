package com.woowol.gutenmorgen.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Result.ResultCode;

@Controller
@RequestMapping(value = "/buildAndDeploy")
public class BuildAndDeployController {
	@Value("${buildAndDeploy.script.path}") private String scriptPath;
	
	private String status = "run";
	
	@RequestMapping(value = "/status.json")
	@ResponseBody
	public Result healthCheck(Model model) {
		return new Result(ResultCode.SUCCESS, status);
	}

	@RequestMapping(value = "/go.json")
	@ResponseBody
	public synchronized Result go() throws IOException {
		if ("run".equals(status)) {
			new ProcessBuilder("bash", scriptPath).start();
			status = "build";
		}
		return new Result(ResultCode.SUCCESS);
	}
}