package com.woowol.gutenmorgen.dao;

import com.woowol.gutenmorgen.model.Job;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class JobDAO extends OltpBaseDAO<Job> {

    @Override
    public Map<String, ?> createMapByObject(Job job) {
        @SuppressWarnings("serial")
        Map<String, Object> map = new HashMap<String, Object>() {
            public Object put(String key, Object value) {
                return value == null ? null : super.put(key, value);
            }
        };
        map.put("jobKey", job.getJobKey());
        map.put("name", job.getName());
        map.put("processor", job.getProcessor());
        map.put("parameter", job.getParameter());
        return map;
    }

}