package com.woowol.gutenmorgen.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woowol.gutenmorgen.dao.JobDAO;
import com.woowol.gutenmorgen.model.Job;

@Service
public class JobBO {
	@Autowired private JobDAO JobDAO;
	@Autowired private ProcessorBO processorBO;
	
	public List<Job> getJobList() {
		return JobDAO.selectList();
	}

	public synchronized void register(Map<String, String> param) {
		Job job = new Job();
		job.setName(param.get("name"));
		job.setProcessor(param.get("processor"));
		job.setParameter(param.get("parameter"));
		JobDAO.persist(job);
	}
	
	public void remove(String jobKey) {
		Job job = new Job();
		job.setJobKey(jobKey);
		job = JobDAO.selectOne(job);
		JobDAO.delete(job);
	}

	public void execute(String jobKey) {
		Job job = new Job();
		job.setJobKey(jobKey);
		job = JobDAO.selectOne(job);
		processorBO.process(job.getProcessor(), job.getParameter());
	}

	public void update(Map<String, String> param) {
		Job job = new Job();
		job.setJobKey(param.get("jobKey"));
		JobDAO.selectOne(job);
		job.setName(param.get("name"));
		job.setProcessor(param.get("processor"));
		job.setParameter(param.get("parameter"));
		JobDAO.update(job);
	}

	public Job getJobByKey(String jobKey) {
		Job job = new Job();
		job.setJobKey(jobKey);
		return JobDAO.selectOne(job);
	}
}