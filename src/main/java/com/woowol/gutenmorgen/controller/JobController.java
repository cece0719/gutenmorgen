package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.JobBO;
import com.woowol.gutenmorgen.model.Job;
import com.woowol.gutenmorgen.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobBO jobBO;

    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Result save(Job job) {
        jobBO.save(job);
        return new Result(Result.ReturnCode.SUCCESS);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public Result delete(Job job) {
        jobBO.delete(job.getJobKey());
        return new Result(Result.ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/execute/{jobKey}")
    public String execute(@PathVariable String jobKey) throws Exception {
        jobBO.execute(jobKey);
        return "redirect:/";
    }
}