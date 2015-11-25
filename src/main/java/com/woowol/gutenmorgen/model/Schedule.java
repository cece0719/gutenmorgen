package com.woowol.gutenmorgen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Schedule {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String scheduleKey;
	private String name;
	@OneToOne
	@JoinColumn(name = "jobKey")
	private Job job;
	private String timeRegex;
}