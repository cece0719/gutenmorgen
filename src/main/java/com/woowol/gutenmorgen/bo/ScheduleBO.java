package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.ScheduleDAO;
import com.woowol.gutenmorgen.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleBO {
    @Autowired
    private ScheduleDAO scheduleDAO;
    @Autowired
    private JobBO jobBO;

    public List<Schedule> getSchduleList() {
        return scheduleDAO.selectList();
    }

    public void register(Schedule schedule, String jobKey) {
        schedule.setJob(jobBO.getJobByKey(jobKey));
        scheduleDAO.persist(schedule);
    }

    public void remove(String scheduleKey) {
        Schedule schedule = new Schedule();
        schedule.setScheduleKey(scheduleKey);
        scheduleDAO.delete(schedule);
    }

    public void update(Schedule schdedule, String jobKey) {
        Schedule originSchedule = new Schedule();
        originSchedule.setScheduleKey(schdedule.getScheduleKey());
        originSchedule = scheduleDAO.selectOne(originSchedule);
        originSchedule.setName(schdedule.getName());
        originSchedule.setJob(jobBO.getJobByKey(jobKey));
        originSchedule.setTimeRegex(schdedule.getTimeRegex());
        scheduleDAO.update(originSchedule);
    }

    @Scheduled(fixedRate = 1000)
    public void checkScheduleAsync() {
        new Thread(new Runnable() {
            public void run() {
                for (Schedule schedule : scheduleDAO.selectList()) {
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    if (currentTime.matches(schedule.getTimeRegex())) {
                        jobBO.execute(schedule.getJob().getJobKey());
                    }
                }
            }
        }).start();
    }
}
