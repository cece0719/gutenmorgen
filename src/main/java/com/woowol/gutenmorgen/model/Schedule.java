package com.woowol.gutenmorgen.model;

import java.io.Serializable;

public class Schedule implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private String name;
	private String job;
	private String timeRegex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getTimeRegex() {
		return timeRegex;
	}
	public void setTimeRegex(String timeRegex) {
		this.timeRegex = timeRegex;
	}
}
