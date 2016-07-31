package org.sashastory.javadev.rest.service;

import java.util.List;

import org.sashastory.javadev.rest.errorhandling.AppException;
import org.sashastory.javadev.rest.errorhandling.CustomReasonPhraseException;
import org.sashastory.javadev.rest.resource.alarm.Alarm;

public interface AlarmService {
	
	/*
	 * ******************** Create related methods **********************
	 * */
	public Long createAlarm(Alarm alarm) throws AppException;
	public void createAlarms(List<Alarm> alarms) throws AppException;

		
	/*
	 ******************** Read related methods ********************
	  */ 	
	/**
	 *  
	 * @return list with alarms
	 * @throws AppException
	 */
	public List<Alarm> getAlarms() throws AppException;
	
	/**
	 * Returns a alarm given its id
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public Alarm getAlarmById(Long id) throws AppException;
	
	/*
	 * ******************** Update related methods **********************
	 * */	
	public void updateAlarm(Alarm alarm) throws AppException;
			
	/*
	 * ******************** Delete related methods **********************
	 * */
	public void deleteAlarmById(Long id);
	
	/** removes all alarms */
	public void deleteAlarms();

	/*
	 * ******************** Helper methods **********************
	 * */
	public Alarm verifyAlarmExistenceById(Long id);
	
	/**
	 * Empty method generating a Business Exception
	 * @throws CustomReasonPhraseException
	 */
	public void generateCustomReasonPhraseException() throws CustomReasonPhraseException;
	
}
