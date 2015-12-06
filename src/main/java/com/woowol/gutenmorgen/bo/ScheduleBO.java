package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.ScheduleDAO;
import com.woowol.gutenmorgen.exception.ResultException;
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
    private ValidateBO validateBO;

    private Iterable<Schedule> cashedScheduleList;

    public Iterable<Schedule> getScheduleList() {
        return scheduleDAO.findAll();
    }

    public void remove(String scheduleKey) {
        scheduleDAO.delete(scheduleKey);
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
                    validateBO.checkNotLocal();
                    jobBO.execute(schedule.getJob().getJobKey());
                } catch (Exception e) {
                    log.error("job 실행 오류", e);
                }
            }
        }
    }

    public void save(Schedule schedule, String jobKey) throws ResultException {
        validateBO.checkTimeRegex(schedule.getTimeRegex());
        schedule.setJob(jobBO.getJobByKey(jobKey));
        scheduleDAO.save(schedule);
    }
}
