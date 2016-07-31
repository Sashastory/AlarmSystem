package org.sashastory.javadev.rest.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

public class AlarmDaoJPA2Impl {
	
	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public List<AlarmEntity> getAlarms() {
		String sqlString = "SELECT p FROM AlarmEntity p";	 
		TypedQuery<AlarmEntity> query = entityManager.createQuery(sqlString, AlarmEntity.class);		

		return query.getResultList();
	}
	
	public AlarmEntity getAlarmById(Long id) {
		
		try {
			String sqlString = "SELECT p FROM AlarmEntity p WHERE p.id = ?1";
			TypedQuery<AlarmEntity> query = entityManager.createQuery(sqlString, AlarmEntity.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	

	public void deleteAlarmById(Long id) {
		
		AlarmEntity alarm = entityManager.find(AlarmEntity.class, id);
		entityManager.remove(alarm);
		
	}

	public Long createAlarm(AlarmEntity alarm) {
		
		alarm.setDate(new Date());
		entityManager.merge(alarm);
		entityManager.flush();//force insert to receive the id of the alarm
		
		return alarm.getId();
	}

	public void updateAlarm(AlarmEntity alarm) {
		//TODO think about partial update and full update 
		entityManager.merge(alarm);		
	}
	
	public void deletePodcasts() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE alarms");		
		query.executeUpdate();
	}

}
