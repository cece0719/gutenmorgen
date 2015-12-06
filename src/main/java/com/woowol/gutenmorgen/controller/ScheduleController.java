package com.woowol.gutenmorgen.controller;

import com.woowol.gutenmorgen.bo.ScheduleBO;
import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Result;
import com.woowol.gutenmorgen.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleBO scheduleBO;

    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Result save(Schedule schedule, @RequestParam("jobKey") String jobKey) throws ResultException {
        scheduleBO.save(schedule, jobKey);
        return new Result(Result.ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/remove/{scheduleKey}")
    public String remove(@PathVariable String scheduleKey) {
        scheduleBO.remove(scheduleKey);
        return "redirect:/";
    }
}