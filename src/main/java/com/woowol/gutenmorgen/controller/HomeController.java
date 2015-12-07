package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.JobBO;
import com.woowol.gutenmorgen.bo.ProcessorBO;
import com.woowol.gutenmorgen.bo.ScheduleBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    private JobBO jobBO;
    @Autowired
    private ProcessorBO processorBO;
    @Autowired
    private ScheduleBO scheduleBO;

    @RequestMapping("/")
    public String home(Model model) throws IOException {
        model.addAttribute("processorMap", processorBO.getProcessorMap());
        model.addAttribute("jobList", jobBO.findAll());
        model.addAttribute("scheduleList", scheduleBO.findAll());
        return "home";
    }
}