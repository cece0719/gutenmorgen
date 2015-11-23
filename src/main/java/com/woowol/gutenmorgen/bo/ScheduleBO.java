package com.woowol.gutenmorgen.bo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woowol.gutenmorgen.model.Schedule;

@Service
public class ScheduleBO {
	private static final String SCHEDULE_MAP_FILE_NAME = "scheduleMap.data";

	@Autowired
	private JobBO jobBO;
	private Map<String, Schedule> scheduleMap = new HashMap<>();

	private Thread scheduleCheckThread = new Thread(new ScheduleCheckRunnable());

	@PostConstruct
	public void postCon() {
		readFromFileScheduleMap();
		//scheduleCheckThread.start();
	}

	@PreDestroy
	public void preDes() {
		writeToFileScheduleMap();
		scheduleCheckThread.interrupt();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	private void readFromFileScheduleMap() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(SCHEDULE_MAP_FILE_NAME));
			scheduleMap = (Map<String, Schedule>) objectInputStream.readObject();
		} catch (Exception e) {
		}
	}

	private void writeToFileScheduleMap() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new FileOutputStream(SCHEDULE_MAP_FILE_NAME));
			objectOutputStream.writeObject(scheduleMap);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Schedule> getSchduleMap() {
		return scheduleMap;
	}

	public void register(Map<String, String> param) {
		if (scheduleMap.get(param.get("name")) != null) {
			return;
		}
		Schedule schedule = new Schedule();
		schedule.setName(param.get("name"));
		schedule.setJob(param.get("job"));
		schedule.setTimeRegex(param.get("timeRegex"));
		scheduleMap.put(param.get("name"), schedule);
	}

	public void remove(String scheduleName) {
		scheduleMap.remove(scheduleName);
	}

	public void update(Map<String, String> param) {
		Schedule schedule = scheduleMap.get(param.get("name"));
		schedule.setName(param.get("newName"));
		schedule.setJob(param.get("job"));
		schedule.setTimeRegex(param.get("timeRegex"));
		scheduleMap.remove(param.get("name"));
		scheduleMap.put(schedule.getName(), schedule);
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
					for (Schedule schedule : scheduleMap.values()) {
						if (currentTime.matches(schedule.getTimeRegex())) {
							jobBO.execute(schedule.getJob());
						}
					}
				}
			}).start();
		}
	}
}
