package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.ScheduleDAO;
import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Schedule;
import com.woowol.gutenmorgen.util.Validate;
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
public class ScheduleBO extends RepositoryBO<Schedule, String, ScheduleDAO> {
    @Autowired
    private JobBO jobBO;

    private List<Schedule> cashedScheduleList;

    public List<Schedule> findAll() {
        return super.findAll();
    }

    @Scheduled(fixedRate = 60*1000)
    public void updateCashedScheduleList() {
        cashedScheduleList = super.findAll();
    }

    @Scheduled(fixedRate = 1000)
    public void checkScheduleAsync() {
        if (cashedScheduleList == null) {
            updateCashedScheduleList();
        }
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE", new Locale("ko", "KR")).format(new Date());

        cashedScheduleList.stream().filter(schedule -> currentTime.matches(schedule.getTimeRegex())).forEach(schedule -> {
            try {
                Validate.checkNotLocal();
                jobBO.execute(schedule.getJob().getJobKey());
            } catch (Exception e) {
                log.error("job 실행 오류", e);
            }
        });
    }

    public void save(Schedule schedule, String jobKey) throws ResultException {
        Validate.checkTimeRegex(schedule.getTimeRegex());
        schedule.setJob(jobBO.findOne(jobKey));
        super.save(schedule);
    }
}
