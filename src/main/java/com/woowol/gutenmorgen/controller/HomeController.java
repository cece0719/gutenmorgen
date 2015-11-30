package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.JobBO;
import com.woowol.gutenmorgen.bo.ProcessorBO;
import com.woowol.gutenmorgen.bo.ScheduleBO;
import com.woowol.gutenmorgen.exception.ResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private JobBO jobBO;
    @Autowired
    private ProcessorBO processorBO;
    @Autowired
    private ScheduleBO scheduleBO;

    @RequestMapping(value = "/")
    public String home(Model model) throws ResultException {
        model.addAttribute("processorMap", processorBO.getProcessorMap());
        model.addAttribute("jobList", jobBO.getJobList());
        model.addAttribute("scheduleList", scheduleBO.getSchduleList());
        return "home";
    }
}