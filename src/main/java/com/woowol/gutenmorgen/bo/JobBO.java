package com.woowol.gutenmorgen.bo;

import com.woowol.gutenmorgen.dao.JobDAO;
import com.woowol.gutenmorgen.exception.ResultException;
import com.woowol.gutenmorgen.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobBO extends AbstractRepositoryBO<JobDAO, Job, String> {
    @Autowired
    private ProcessorBO processorBO;

    public void execute(String jobKey) throws Exception {
        Job job = findOne(jobKey);
        processorBO.process(job.getProcessor(), job.getParameter());
    }

    @Override
    public <S extends Job> S save(S job) {
        try {
            processorBO.validateParameter(job.getProcessor(), job.getParameter());
            return super.save(job);
        } catch (Exception e) {
            throw new ResultException(e);
        }
    }
}