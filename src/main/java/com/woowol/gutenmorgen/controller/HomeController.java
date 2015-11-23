package com.woowol.gutenmorgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woowol.gutenmorgen.bo.JobBO;
import com.woowol.gutenmorgen.bo.ProcessorBO;
import com.woowol.gutenmorgen.bo.ScheduleBO;

@Controller
public class HomeController {
	@Autowired private JobBO jobBO;
	@Autowired private ProcessorBO processorBO;
	@Autowired private ScheduleBO scheduleBO;
	
	@RequestMapping(value = "/")
	public String home(Model model) {
		model.addAttribute("processorMap", processorBO.getProcessorMap());
		model.addAttribute("jobMap", jobBO.getJobMap());		
		model.addAttribute("scheduleMap", scheduleBO.getSchduleMap());
		return "home";
	}
}