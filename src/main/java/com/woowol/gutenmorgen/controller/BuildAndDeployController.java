package com.woowol.gutenmorgen.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BuildAndDeployController {
	private String status = "run";
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/status")
	@ResponseBody
	public Map<String, Object> healthCheck(Model model) {
		return new HashMap<String, Object>(){{
			put("status", status);
		}};
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/buildAndDeploy")
	@ResponseBody
	public synchronized Map<String, Object> buildAndDeploy(Model model) throws IOException {
		if ("run".equals(status)) {
			status = "build";
			new ProcessBuilder("bash", "/home/webservice/workspace/gutenmorgen/goDaemon.sh").start();
		}
		return new HashMap<String, Object>(){{
			put("result", "success");
		}};
	}
}