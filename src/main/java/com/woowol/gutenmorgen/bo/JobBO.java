package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.JobDAO;
import com.woowol.gutenmorgen.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobBO {
    @Autowired
    private JobDAO JobDAO;
    @Autowired
    private ProcessorBO processorBO;

    public List<Job> getJobList() {
        return JobDAO.selectList();
    }

    public void register(Job job) {
        JobDAO.persist(job);
    }

    public void remove(String jobKey) {
        Job job = new Job();
        job.setJobKey(jobKey);
        job = JobDAO.selectOne(job);
        JobDAO.delete(job);
    }

    public void execute(String jobKey) throws Exception {
        Job job = new Job();
        job.setJobKey(jobKey);
        job = JobDAO.selectOne(job);
        processorBO.process(job.getProcessor(), job.getParameter());
    }

    public void update(Job job) {
        Job originJob = new Job();
        originJob.setJobKey(job.getJobKey());
        originJob = JobDAO.selectOne(originJob);
        originJob.setName(job.getName());
        originJob.setProcessor(job.getProcessor());
        originJob.setParameter(job.getParameter());
        JobDAO.update(originJob);
    }

    public Job getJobByKey(String jobKey) {
        Job job = new Job();
        job.setJobKey(jobKey);
        return JobDAO.selectOne(job);
    }
}