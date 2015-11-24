package com.woowol.gutenmorgen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.woowol.gutenmorgen.bo.JobBO;

@Controller
@RequestMapping(value = "/job")
public class JobController {
	@Autowired private JobBO jobBO;
	
	@RequestMapping(value = "/register")
	public String register(@RequestParam Map<String, String> param, Model model) {
		jobBO.register(param);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/update")
	public String update(@RequestParam Map<String, String> param, Model model) {
		jobBO.update(param);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/remove/{jobKey}")
	public String remove(@PathVariable String jobKey, Model model) {
		jobBO.remove(jobKey);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/execute/{jobKey}")
	public String execute(@PathVariable String jobKey, Model model) {
		jobBO.execute(jobKey);
		return "redirect:/";
	}
}