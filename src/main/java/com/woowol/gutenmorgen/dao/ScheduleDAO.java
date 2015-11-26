package com.woowol.gutenmorgen.dao;

import com.woowol.gutenmorgen.model.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class ScheduleDAO extends OltpBaseDAO<Schedule> {

    @Override
    public Map<String, ?> createMapByObject(Schedule schedule) {
        @SuppressWarnings("serial")
        Map<String, Object> map = new HashMap<String, Object>() {
            public Object put(String key, Object value) {
                return value == null ? value : super.put(key, value);
            }
        };
        map.put("scheduleKey", schedule.getScheduleKey());
        map.put("name", schedule.getName());
        map.put("timeRegex", schedule.getTimeRegex());
        return map;
    }

}