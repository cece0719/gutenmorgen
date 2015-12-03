package com.woowol.gutenmorgen.dao;

import com.woowol.gutenmorgen.model.Job;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
public class JobDAO extends BaseDAO<Job> {
}