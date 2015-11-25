package com.woowol.gutenmorgen.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

	public void register(Schedule schedule, String jobKey) {
		schedule.setJob(jobBO.getJobByKey(jobKey));
		scheduleDAO.persist(schedule);
	}

	public void remove(String scheduleKey) {
		Schedule schedule = new Schedule();
		schedule.setScheduleKey(scheduleKey);
		scheduleDAO.delete(schedule);
	}

	public void update(Schedule schdedule, String jobKey) {
		Schedule originSchedule = new Schedule();
		originSchedule.setScheduleKey(schdedule.getScheduleKey());
		originSchedule = scheduleDAO.selectOne(originSchedule);
		originSchedule.setName(schdedule.getName());
		originSchedule.setJob(jobBO.getJobByKey(jobKey));
		originSchedule.setTimeRegex(schdedule.getTimeRegex());
		scheduleDAO.update(originSchedule);
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

		private void checkScheduleAsync(String currentTime) {
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
