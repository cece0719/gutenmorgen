package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.ScheduleDAO;
import com.woowol.gutenmorgen.model.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@Slf4j
public class ScheduleBO {
    @Autowired
    private ScheduleDAO scheduleDAO;
    @Autowired
    private JobBO jobBO;
    @Autowired
    private EnvironmentBO environmentBO;

    private Iterable<Schedule> cashedScheduleList;

    public Iterable<Schedule> getScheduleList() {
        return scheduleDAO.findAll();
    }

    public void register(Schedule schedule, String jobKey) {
        schedule.setJob(jobBO.getJobByKey(jobKey));
        scheduleDAO.save(schedule);
    }

    public void remove(String scheduleKey) {
        scheduleDAO.delete(scheduleKey);
    }

    public void update(Schedule schedule, String jobKey) {
        Schedule originSchedule = scheduleDAO.findOne(schedule.getScheduleKey());
        originSchedule.setName(schedule.getName());
        originSchedule.setJob(jobBO.getJobByKey(jobKey));
        originSchedule.setTimeRegex(schedule.getTimeRegex());
        scheduleDAO.save(originSchedule);
    }

    @Scheduled(fixedRate = 60*1000)
    public void updateCashedScheduleList() {
        cashedScheduleList = scheduleDAO.findAll();
    }

    @Scheduled(fixedRate = 1000)
    public void checkScheduleAsync() {
        if (cashedScheduleList == null) {
            updateCashedScheduleList();
        }
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE", new Locale("ko", "KR")).format(new Date());

        for (Schedule schedule : cashedScheduleList) {
            if (currentTime.matches(schedule.getTimeRegex())) {
                try {
                    environmentBO.checkNotLocal();
                    jobBO.execute(schedule.getJob().getJobKey());
                } catch (Exception e) {
                    log.error("job 실행 오류", e);
                }
            }
        }
    }
}
