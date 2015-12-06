package com.woowol.gutenmorgen.dao;

import com.woowol.gutenmorgen.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

public interface JobDAO extends JpaRepository<Job, String> {
}