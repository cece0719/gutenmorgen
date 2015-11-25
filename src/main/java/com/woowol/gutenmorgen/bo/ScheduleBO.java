package com.woowol.gutenmorgen.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woowol.gutenmorgen.dao.ScheduleDAO;
import com.woowol.gutenmorgen.model.Schedule;

@Service
public class ScheduleBO {
	@Autowired private ScheduleDAO scheduleDAO;
	@Autowired private JobBO jobBO;

	private Thread scheduleCheckThread = new Thread(new ScheduleCheckRunnable());

	@PostConstruct
	public void postCon() {
		scheduleCheckThread.start();
	}

	@PreDestroy
	public void preDes() {
		scheduleCheckThread.interrupt();
	}

	public List<Schedule> getSchduleList() {
		return scheduleDAO.selectList();
	}

	public void register(Map<String, String> param) {
		Schedule schedule = new Schedule();
		schedule.setName(param.get("name"));
		schedule.setJob(jobBO.getJobByKey(param.get("jobKey")));
		schedule.setTimeRegex(param.get("timeRegex"));
		scheduleDAO.persist(schedule);
	}

	public void remove(String scheduleKey) {
		Schedule schedule = new Schedule();
		schedule.setScheduleKey(scheduleKey);
		scheduleDAO.delete(schedule);
	}

	public void update(Map<String, String> param) {
		Schedule schedule = new Schedule();
		schedule.setScheduleKey(param.get("scheduleKey"));
		schedule = scheduleDAO.selectOne(schedule);
		schedule.setName(param.get("name"));
		schedule.setJob(jobBO.getJobByKey(param.get("jobKey")));
		schedule.setTimeRegex(param.get("timeRegex"));
		scheduleDAO.update(schedule);
	}

	private class ScheduleCheckRunnable implements Runnable {
		private String recentCheckScheduleTime = "";

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				if (!currentTime.equals(recentCheckScheduleTime)) {
					recentCheckScheduleTime = currentTime;
					checkScheduleAsync(currentTime);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void checkScheduleAsync(final String currentTime) {
			new Thread(new Runnable() {
				public void run() {
					for (Schedule schedule : scheduleDAO.selectList()) {
						if (currentTime.matches(schedule.getTimeRegex())) {
							jobBO.execute(schedule.getJob().getJobKey());
						}
					}
				}
			}).start();
		}
	}
}
