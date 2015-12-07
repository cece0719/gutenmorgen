package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.JobBO;
import com.woowol.gutenmorgen.bo.ProcessorBO;
import com.woowol.gutenmorgen.bo.ScheduleBO;
import com.woowol.gutenmorgen.exception.ResultException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class HomeController {
    @Autowired
    private JobBO jobBO;
    @Autowired
    private ProcessorBO processorBO;
    @Autowired
    private ScheduleBO scheduleBO;

    @RequestMapping(value = "/")
    public String home(Model model) throws ResultException, IOException {
       model.addAttribute("processorMap", processorBO.getProcessorMap());
        model.addAttribute("jobList", jobBO.findAll());
        model.addAttribute("scheduleList", scheduleBO.findAll());
        return "home";
    }
}