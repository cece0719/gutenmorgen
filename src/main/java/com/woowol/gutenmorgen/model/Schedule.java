package com.woowol.gutenmorgen.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String scheduleKey;
    private String name;
    @OneToOne
    @JoinColumn(name = "jobKey")
    private Job job;
    private String timeRegex;
}