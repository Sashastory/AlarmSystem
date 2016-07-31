package org.sashastory.javadev.rest.dao;

import java.util.List;

public interface AlarmDao {
	
	public List<AlarmEntity> getAlarms();

	/**
	 * Returns an alarm given its id
	 * 
	 * @param id
	 * @return
	 */
	public AlarmEntity getAlarmById(Long id);

	public void deleteAlarmById(Long id);

	public Long createAlarm(AlarmEntity alarm);

	public void updateAlarm(AlarmEntity alarm);

	/** removes all alarms */
	public void deleteAlarms();

}
