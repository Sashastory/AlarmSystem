package org.sashastory.javadev.rest.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.persistence.*;

import org.apache.commons.beanutils.BeanUtils;
import org.sashastory.javadev.rest.resource.alarm.Alarm;

@Entity
@Table(name = "alarms")
public class AlarmEntity implements Serializable {
	
	private static final long serialVersionUID = 1;
	
	/** id of the alarm */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	/** type of the alarm */
	@Column(name = "Type")
	private String type;
	
	/** alarm date and time */
	@Column(name = "AlarmDate")
	private Date date;
	
	/** description of the alarm */
	@Column(name = "Description")
	private String description;
	
	/** user id associated with the alarm */
	@Column(name = "UserID")
	private Long userId;
	
	/** severity of the alarm */
	@Column(name = "Severity")
	private String severity;
	
	public AlarmEntity() {}
	
	public AlarmEntity(String type, Date date, String description, 
			Long userId, String severity) {
		this.type = type;
		this.date = date;
		this.description = description;
		this.userId = userId;
		this.severity = severity;
	}
	
	public AlarmEntity(Alarm alarm) {
		try {
			BeanUtils.copyProperties(this, alarm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	
}	
