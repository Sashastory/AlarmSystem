package org.sashastory.javadev.rest.resource.alarm;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import org.apache.commons.beanutils.BeanUtils;
import org.sashastory.javadev.rest.dao.AlarmEntity;
import org.sashastory.javadev.rest.helpers.DateISO8601Adapter;

@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Alarm implements Serializable {
	
	private static final long serialVersionUID = 1;
	
	/** id of the alarm */
	@XmlElement(name = "id")
	private Long id;
	
	/** type of the alarm */
	@XmlElement(name = "type")
	private String type;
	
	/** alarm date and time */
	@XmlElement(name = "date")
	@XmlJavaTypeAdapter(DateISO8601Adapter.class)
	@AlarmDetailedView
	private Date date;
	
	/** description of the alarm */
	@XmlElement(name = "description")
    @AlarmDetailedView
	private String description;
	
	/** user id associated with the alarm */
	@XmlElement(name = "userId")
	private Long userId;
	
	/** severity of the alarm */
	@XmlElement(name = "severity")
	private String severity;

	public Alarm(AlarmEntity alarmEntity) {
		try {
			BeanUtils.copyProperties(this, alarmEntity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public Alarm(String type, Date date, String description,
			Long userId, String severity) {
		this.type = type;
		this.date = date;
		this.description = description;
		this.userId = userId;
		this.severity = severity;
	}
	
	public Alarm() {}

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
