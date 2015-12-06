package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.JobDAO;
import com.woowol.gutenmorgen.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobBO extends RepositoryBO<Job, String, JobDAO> {
    @Autowired
    private ProcessorBO processorBO;

    public void execute(String jobKey) throws Exception {
        Job job = super.findOne(jobKey);
        processorBO.process(job.getProcessor(), job.getParameter());
    }
}