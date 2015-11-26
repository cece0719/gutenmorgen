package com.woowol.gutenmorgen.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String jobKey;
    private String name;
    private String processor;
    private String parameter;
}
