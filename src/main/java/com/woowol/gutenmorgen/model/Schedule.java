package com.woowol.gutenmorgen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Schedule {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private String scheduleKey;
	private String name;
	@OneToOne
	@JoinColumn(name = "jobKey")
	private Job job;
	private String timeRegex;
	
	public String getScheduleKey() {
		return scheduleKey;
	}
	public void setScheduleKey(String scheduleKey) {
		this.scheduleKey = scheduleKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getTimeRegex() {
		return timeRegex;
	}
	public void setTimeRegex(String timeRegex) {
		this.timeRegex = timeRegex;
	}
}
