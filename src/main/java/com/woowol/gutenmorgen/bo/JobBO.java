package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.woowol.gutenmorgen.dao.JobDAO;

@Service
public class JobBO {
    @Autowired
    private JobDAO jobDAO;
    @Autowired
    private ProcessorBO processorBO;

    public Iterable<Job> getJobList() {
        return jobDAO.findAll();
    }

    public void delete(String jobKey) {
        jobDAO.delete(jobKey);
    }

    public void execute(String jobKey) throws Exception {
        Job job = jobDAO.findOne(jobKey);
        processorBO.process(job.getProcessor(), job.getParameter());
    }

    public Job getJobByKey(String jobKey) {
        return jobDAO.findOne(jobKey);
    }

    public void save(Job job) {
        jobDAO.save(job);
    }
}