package com.woowol.gutenmorgen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.woowol.gutenmorgen.bo.JobBO;

@Controller
@RequestMapping(value = "/job")
public class JobController {
	@Autowired private JobBO jobBO;
	
	@RequestMapping(value = "/remove")
	public String remove(@RequestParam Map<String, String> param, Model model) {
		jobBO.remove(param.get("name"));
		return "redirect:/";
	}
	
	@RequestMapping(value = "/register")
	public String register(@RequestParam Map<String, String> param, Model model) {
		jobBO.register(param);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/execute")
	public String execute(@RequestParam Map<String, String> param, Model model) {
		jobBO.execute(param.get("name"));
		return "redirect:/";
	}
	
	@RequestMapping(value = "/update")
	public String update(@RequestParam Map<String, String> param, Model model) {
		jobBO.update(param);
		return "redirect:/";
	}
}