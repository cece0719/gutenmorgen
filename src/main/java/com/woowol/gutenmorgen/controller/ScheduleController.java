package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.ScheduleBO;
import com.woowol.gutenmorgen.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleBO scheduleBO;

    @RequestMapping(value = "/register")
    public String register(@RequestParam("jobKey") String jobKey, Schedule schedule) {
        scheduleBO.register(schedule, jobKey);
        return "redirect:/";
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam("jobKey") String jobKey, Schedule schedule) {
        scheduleBO.update(schedule, jobKey);
        return "redirect:/";
    }

    @RequestMapping(value = "/remove/{scheduleKey}")
    public String remove(@PathVariable String scheduleKey) {
        scheduleBO.remove(scheduleKey);
        return "redirect:/";
    }
}