package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.ScheduleDAO;
import com.woowol.gutenmorgen.model.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    private List<Schedule> cashedScheduleList;

    public List<Schedule> getScheduleList() {
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

    public void update(Schedule schedule, String jobKey) {
        Schedule originSchedule = new Schedule();
        originSchedule.setScheduleKey(schedule.getScheduleKey());
        originSchedule = scheduleDAO.selectOne(originSchedule);
        originSchedule.setName(schedule.getName());
        originSchedule.setJob(jobBO.getJobByKey(jobKey));
        originSchedule.setTimeRegex(schedule.getTimeRegex());
        scheduleDAO.update(originSchedule);
    }

    @Scheduled(fixedRate = 60*1000)
    public void updateCashedScheduleList() {
        cashedScheduleList = scheduleDAO.selectList();
    }

    @Scheduled(fixedRate = 1000)
    public void checkScheduleAsync() {
        if (cashedScheduleList == null) {
            updateCashedScheduleList();
        }
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE", new Locale("ko", "KR")).format(new Date());

        cashedScheduleList.stream().filter(schedule -> currentTime.matches(schedule.getTimeRegex())).forEach(schedule -> {
            try {
                environmentBO.checkNotLocal();
                jobBO.execute(schedule.getJob().getJobKey());
            } catch (Exception e) {
                log.error("job 실행 오류", e);
            }
        });
    }
}
