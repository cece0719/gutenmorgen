package com.woowol.gutenmorgen.bo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woowol.gutenmorgen.model.Job;

@Service
public class JobBO {
	private static final String JOB_MAP_FILE_NAME = "jobMap.data";
	
	private Map<String, Job> jobMap = new HashMap<>();
	@Autowired private ProcessorBO processorBO;
	
	@PostConstruct
	public void postCon() {
		readFromFileJobMap();
	}

	@PreDestroy
	public void preDes() {
		writeToFileJobMap();
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	private void readFromFileJobMap() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(JOB_MAP_FILE_NAME));
			jobMap = (Map<String, Job>) objectInputStream.readObject();
		} catch (Exception e) {
		}
	}
	
	private void writeToFileJobMap() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(JOB_MAP_FILE_NAME));
			objectOutputStream.writeObject(jobMap);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Job> getJobMap() {
		return jobMap;
	}

	public void register(Map<String, String> param) {
		if(jobMap.get(param.get("name"))!=null) {
			return;
		}
		Job job = new Job();
		job.setName(param.get("name"));
		job.setProcessor(param.get("processor"));
		job.setParameter(param.get("parameter"));
		jobMap.put(param.get("name"), job);
	}
	
	public void remove(String jobName) {
		jobMap.remove(jobName);
		writeToFileJobMap();
	}

	public void execute(String jobName) {
		Job job = jobMap.get(jobName);
		processorBO.process(job.getProcessor(), job.getParameter());
	}

	public void update(Map<String, String> param) {
		Job job = jobMap.get(param.get("name"));
		job.setName(param.get("newName"));
		job.setProcessor(param.get("processor"));
		job.setParameter(param.get("parameter"));
		jobMap.remove(param.get("name"));
		jobMap.put(job.getName(), job);
	}
}