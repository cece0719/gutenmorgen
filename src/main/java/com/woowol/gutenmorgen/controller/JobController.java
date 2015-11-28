package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.JobBO;
import com.woowol.gutenmorgen.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private JobBO jobBO;

    @RequestMapping(value = "/register")
    public String register(Job job, Model model) {
        jobBO.register(job);
        return "redirect:/";
    }

    @RequestMapping(value = "/update")
    public String update(Job job, Model model) {
        jobBO.update(job);
        return "redirect:/";
    }

    @RequestMapping(value = "/remove/{jobKey}")
    public String remove(@PathVariable String jobKey, Model model) {
        jobBO.remove(jobKey);
        return "redirect:/";
    }

    @RequestMapping(value = "/execute/{jobKey}")
    public String execute(@PathVariable String jobKey, Model model) throws Exception {
        jobBO.execute(jobKey);
        return "redirect:/";
    }
}