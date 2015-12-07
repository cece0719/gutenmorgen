package com.woowol.gutenmorgen.dao;

import com.woowol.gutenmorgen.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDAO extends JpaRepository<Job, String> {
}