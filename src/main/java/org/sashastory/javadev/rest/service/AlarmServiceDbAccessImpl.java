package org.sashastory.javadev.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.sashastory.javadev.rest.dao.AlarmDao;
import org.sashastory.javadev.rest.dao.AlarmEntity;
import org.sashastory.javadev.rest.errorhandling.AppException;
import org.sashastory.javadev.rest.errorhandling.CustomReasonPhraseException;
import org.sashastory.javadev.rest.resource.alarm.Alarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AlarmServiceDbAccessImpl implements AlarmService {
	
	@Autowired
	AlarmDao alarmDao;
		
	/********************* Create related methods implementation ***********************/
	@Transactional("transactionManager")
	public Long createAlarm(Alarm alarm) throws AppException {
		
		validateInputForCreation(alarm);
		
		return alarmDao.createAlarm(new AlarmEntity(alarm));
	}

	private void validateInputForCreation(Alarm alarm) throws AppException {
		if(alarm.getType() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the type is properly generated/set", "No link availible");
		}
		if(alarm.getDate() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the alarm datetime is properly generated/set", "No link availible");
		}
		if(alarm.getUserId() == null) {
			throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Provided data not sufficient for insertion",
					"Please verify that the user id is properly generated/set", "No link availible");
		}
	}
	
	@Transactional("transactionManager")
	public void createAlarms(List<Alarm> alarms) throws AppException {
		for (Alarm alarm : alarms) {
			createAlarm(alarm);
		}		
	}	
	
	
	 // ******************** Read related methods implementation **********************		
	public List<Alarm> getAlarms() throws AppException {
		
		List<AlarmEntity> alarms = alarmDao.getAlarms();
		
		return getAlarmsFromEntities(alarms);
	}

	public Alarm getAlarmById(Long id) throws AppException {		
		AlarmEntity alarmById = alarmDao.getAlarmById(id);
		if(alarmById == null){
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 
					404, 
					"The alarm you requested with id " + id + " was not found in the database",
					"Verify the existence of the alarm with the id " + id + " in the database",
					"No link provided");			
		}
		
		return new Alarm(alarmDao.getAlarmById(id));
	}	

	private List<Alarm> getAlarmsFromEntities(List<AlarmEntity> alarmEntities) {
		List<Alarm> response = new ArrayList<Alarm>();
		for(AlarmEntity alarmEntity : alarmEntities){
			response.add(new Alarm(alarmEntity));					
		}
		
		return response;
	}

	
	/********************* UPDATE-related methods implementation ***********************/	
	@Transactional("transactionManager")
	public void updateAlarm(Alarm alarm) throws AppException {
		
		Alarm verifyAlarmExistenceById = verifyAlarmExistenceById(alarm.getId());
		if(verifyAlarmExistenceById == null){
			throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 
					404, 
					"The resource you are trying to update does not exist in the database",
					"Please verify existence of data in the database for the id - " + alarm.getId(),
					"No link specified");				
		}
				
		alarmDao.updateAlarm(new AlarmEntity(alarm));
	}
	
	/********************* DELETE-related methods implementation ***********************/
	@Transactional("transactionManager")
	public void deleteAlarmById(Long id) {
		alarmDao.deleteAlarmById(id);
	}
	
	@Transactional("transactionManager")	
	public void deleteAlarms() {
		alarmDao.deleteAlarms();		
	}

	public Alarm verifyAlarmExistenceById(Long id) {
		AlarmEntity alarmById = alarmDao.getAlarmById(id);
		if(alarmById == null){
			return null;
		} else {
			return new Alarm(alarmById);			
		}
	}


	@Override
	public void generateCustomReasonPhraseException() throws CustomReasonPhraseException {		
		throw new CustomReasonPhraseException(4000, "message attached to the Custom Reason Phrase Exception");		
	}

	public void setAlarmDao(AlarmDao alarmDao) {
		this.alarmDao = alarmDao;
	}

}
