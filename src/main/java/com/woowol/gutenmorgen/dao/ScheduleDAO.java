package com.woowol.gutenmorgen.dao;

import com.woowol.gutenmorgen.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDAO extends JpaRepository<Schedule, String> {
}