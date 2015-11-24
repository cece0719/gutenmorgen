package com.woowol.gutenmorgen.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/buildAndDeploy")
public class BuildAndDeployController {
	@Value("${buildAndDeploy.script.path}") private String scriptPath;
	
	private String status = "run";
	
	@RequestMapping(value = "/status")
	@ResponseBody
	public Map<String, Object> healthCheck(Model model) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		return map;
	}

	@RequestMapping(value = "/go")
	@ResponseBody
	public synchronized Map<String, Object> go(Model model) throws IOException {
		if ("run".equals(status)) {
			status = "build";
			new ProcessBuilder("bash", scriptPath).start();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
}