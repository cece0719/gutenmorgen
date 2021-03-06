package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.ScheduleBO;
import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleBO scheduleBO;

    @RequestMapping("/save.json")
    @ResponseBody
    public Result save(Schedule schedule, @RequestParam("jobKey") String jobKey) {
        scheduleBO.save(schedule, jobKey);
        return new Result(Result.ReturnCode.SUCCESS);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public Result delete(Schedule schedule) {
        scheduleBO.delete(schedule.getScheduleKey());
        return new Result(Result.ReturnCode.SUCCESS);
    }
}