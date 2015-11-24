package com.woowol.gutenmorgen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.woowol.gutenmorgen.bo.ScheduleBO;

@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {
	@Autowired private ScheduleBO scheduleBO;
	
	@RequestMapping(value = "/register")
	public String register(@RequestParam Map<String, String> param, Model model) {
		scheduleBO.register(param);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/update")
	public String update(@RequestParam Map<String, String> param, Model model) {
		scheduleBO.update(param);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/remove/{scheduleKey}")
	public String remove(@PathVariable String scheduleKey, Model model) {
		scheduleBO.remove(scheduleKey);
		return "redirect:/";
	}
}