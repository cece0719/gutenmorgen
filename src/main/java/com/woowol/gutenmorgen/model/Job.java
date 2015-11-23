package com.woowol.gutenmorgen.model;

import java.io.Serializable;

public class Job implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private String name;
	private String processor;
	private String parameter;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
